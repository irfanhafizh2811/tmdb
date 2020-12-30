package com.android.myapplication.movies.ui.genre

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.databinding.ItemGenreBinding
import com.android.myapplication.movies.models.Genre
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.ViewPreloadSizeProvider
import kotlinx.android.synthetic.main.item_genre.view.*
import java.util.Collections

class GenreRecyclerAdapter(private val onGenreClickListener: (Genre, Boolean) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Genre, GenreRecyclerAdapter.GenreViewHolder>(
        GenreDiffUtil()
    ), ListPreloader.PreloadModelProvider<String> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder.getInstance(parent, onGenreClickListener)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }

    class GenreViewHolder private constructor(
        private val binding: ItemGenreBinding,
        val onGenreClickListener: (Genre, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            private const val TAG = "GenreViewHolder"
            fun getInstance(
                parent: ViewGroup,
                onGenreClickListener: (Genre, Boolean) -> Unit
            ): GenreViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGenreBinding.inflate(inflater, parent, false)
                return GenreViewHolder(
                    binding,
                    onGenreClickListener
                )
            }
        }

        fun bind(genre: Genre) {
            binding.genre = genre
            binding.viewHolder = this
            binding.executePendingBindings()
            binding.viewHolder?.itemView?.checkbox?.setOnCheckedChangeListener { _, isChecked ->
                onGenreClickListener.invoke(genre, isChecked)
            }
        }

    }

    class GenreDiffUtil : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

    }

    override fun getPreloadItems(position: Int): MutableList<String> {
        val id = getItem(position).id.toString()
        return if (TextUtils.isEmpty(id)) {
            Collections.emptyList()
        } else {
            Collections.singletonList(id)
        }
    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*>? = null
}