package com.android.myapplication.movies.ui.genre

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.models.Genre
import com.android.myapplication.movies.models.Movie
import com.android.myapplication.movies.util.Resource

@BindingAdapter("genreList")
fun RecyclerView.submitGenreList(genres: List<Genre>?) {
    val adapter = this.adapter as GenreRecyclerAdapter
    genres?.let {
        adapter.submitList(genres)
    }
}

@BindingAdapter("rvVisibility")
fun RecyclerView.checkVisibility(repoResult: Resource<List<Genre>>?) {
    repoResult?.let {
        repoResult.data?.let {
            if (repoResult.data.isNullOrEmpty()) {
                this.visibility = View.GONE
            } else {
                this.visibility = View.VISIBLE
            }
        }
    }
}