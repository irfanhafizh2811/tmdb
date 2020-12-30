package com.android.myapplication.movies.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.android.myapplication.movies.databinding.DialogGenreListBinding
import com.android.myapplication.movies.models.Genre
import com.android.myapplication.movies.util.RecyclerViewDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModel<GenreListViewModel>()
    private lateinit var adapter: GenreRecyclerAdapter
    private lateinit var binding: DialogGenreListBinding
    private var genreIds = mutableListOf<String>()

    lateinit var onApplyListener: (List<String>) -> Unit

    companion object {
        const val TAG = "GenreDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogGenreListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.buttonFilter.setOnClickListener {
            dismiss()
            onApplyListener.invoke(genreIds)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
            this@GenreDialogFragment.adapter = GenreRecyclerAdapter(
                onGenreClickListener = { genre, isCheked ->
                    when {
                        isCheked -> genreIds.add(genre.id.toString())
                        else -> genreIds.remove(genre.id.toString())
                    }
                }
            )
            addItemDecoration(RecyclerViewDecoration())
            adapter = this@GenreDialogFragment.adapter
        }
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        super.showNow(manager, tag)
        viewModel.getList()
    }

}