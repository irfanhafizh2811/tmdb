package com.android.myapplication.movies.ui.genre

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.myapplication.movies.models.Genre
import com.android.myapplication.movies.persistence.PreferencesStorage
import com.android.myapplication.movies.util.Category
import com.android.myapplication.movies.util.Resource
import com.android.myapplication.movies.repository.GenreRepository

class GenreListViewModel(private val repository: GenreRepository, val app: Application) :
    AndroidViewModel(app) {

    companion object {
        private const val TAG = "GenreListViewModel"
    }

    var query: String? = PreferencesStorage.getStoredQuery(app.applicationContext)
    var category: Category = PreferencesStorage.getStoredCategory(app.applicationContext)

    private val _genres = MediatorLiveData<Resource<List<Genre>>>()
    val genres: LiveData<Resource<List<Genre>>>
        get() = _genres

    fun getList() {
        executeRequest()
    }

    private fun executeRequest() {
        registerMediatorLiveData(repository.getGenres())
    }

    fun registerMediatorLiveData(repositorySource: LiveData<Resource<List<Genre>>>) {
        _genres.addSource(repositorySource) { resourceListGenre ->
            if (resourceListGenre != null) {
                _genres.value = resourceListGenre
                if (resourceListGenre is Resource.Success || resourceListGenre is Resource.Error) {
                    unregisterMediatorLiveData(repositorySource)
                    resourceListGenre.data?.let {
                        Log.d(TAG, "registerMediatorLiveData: ${it.size}")
                    }
                }
            } else {
                unregisterMediatorLiveData(repositorySource)
            }
        }
    }

    private fun unregisterMediatorLiveData(repositorySource: LiveData<Resource<List<Genre>>>) {
        _genres.removeSource(repositorySource)
    }
}
