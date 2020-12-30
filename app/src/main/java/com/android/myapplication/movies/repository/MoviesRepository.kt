package com.android.myapplication.movies.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.api.responses.ApiResponse
import com.android.myapplication.movies.persistence.dao.MovieAndDetailDao
import com.android.myapplication.movies.util.AppExecutors
import com.android.myapplication.movies.util.Category
import com.android.myapplication.movies.util.NetworkBoundResource
import com.android.myapplication.movies.util.Resource
import com.android.myapplication.movies.models.Movie
import com.android.myapplication.movies.api.responses.MoviesResponse

class MoviesRepository(
    private val movieDao: MovieAndDetailDao,
    private val appExecutors: AppExecutors,
    private val movieApi: MoviesApi
) {
    companion object {
        private const val TAG = "MoviesRepository"
    }

    fun getListMovie(
        pageNumber: Int,
        genres: String,
        category: Category
    ): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MoviesResponse>(appExecutors) {
            override fun saveCallResult(item: MoviesResponse?) {
                item?.let {
                    val list: ArrayList<Movie>? = (item.movies)?.let { ArrayList(it) }
                    val newList: ArrayList<Movie>? = ArrayList()
                    list?.forEach {
                        val movie = it.copy(categoryType = category)
                        Log.d(TAG, "saveCallResult: ${movie}")
                        newList?.add(movie)
                    }
                    newList?.let {
                        movieDao.insertMovies(*newList.toTypedArray())
                    }
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override fun loadFromDb(): LiveData<List<Movie>> =
                movieDao.getMovies(pageNumber, category)

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> = when (category) {
                Category.TOPRATED -> movieApi.getTopRatedMovies(pageNumber)
                Category.UPCOMING -> movieApi.geUpcomingMovies(pageNumber)
                Category.POPULAR -> movieApi.getPopularMovies(pageNumber)
                else -> movieApi.getMoviesByGenres(genres, pageNumber)
            }

        }.asLiveData()
    }

    fun searchListMovie(pageNumber: Int, query: String): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MoviesResponse>(appExecutors) {
            override fun saveCallResult(item: MoviesResponse?) {
                item?.let {
                    val list: ArrayList<Movie>? = (item.movies)?.let { ArrayList(it) }
                    list?.let {
                        movieDao.insertMovies(*list.toTypedArray())
                    }
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override fun loadFromDb(): LiveData<List<Movie>> =
                movieDao.searchListMovie(query, pageNumber)

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> =
                movieApi.searchMovies(
                    query, pageNumber
                )
        }.asLiveData()
    }
}