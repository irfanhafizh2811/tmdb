package com.android.myapplication.movies.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.api.responses.ApiResponse
import com.android.myapplication.movies.api.responses.inner.GenreResponse
import com.android.myapplication.movies.models.Genre
import com.android.myapplication.movies.persistence.dao.GenreDao
import com.android.myapplication.movies.util.AppExecutors
import com.android.myapplication.movies.util.NetworkBoundResource
import com.android.myapplication.movies.util.Resource

class GenreRepository(
    private val genreDao: GenreDao,
    private val appExecutors: AppExecutors,
    private val movieApi: MoviesApi
) {

    companion object {
        private const val TAG = "GenresRepository"
    }

    fun getGenres(): LiveData<Resource<List<Genre>>> {

        return object : NetworkBoundResource<List<Genre>, GenreResponse>(appExecutors) {

            override fun saveCallResult(item: GenreResponse?) {
                item?.let {
                    val list: ArrayList<Genre>? = (item.genres)?.let { ArrayList(it) }
                    val newList: ArrayList<Genre>? = ArrayList()
                    list?.forEach {
                        val genre = it.copy()
                        Log.d(TAG, "saveCallResult: ${genre}")
                        newList?.add(genre)
                        genreDao.insertGenres(*list.toTypedArray())
                    }
                    newList?.let {
                        genreDao.getGenres()
                    }
                }
            }

            override fun shouldFetch(data: List<Genre>?): Boolean = true

            override fun loadFromDb(): LiveData<List<Genre>> = genreDao.getGenres()

            override fun createCall(): LiveData<ApiResponse<GenreResponse>> = movieApi.getGenres()

        }.asLiveData()
    }

}