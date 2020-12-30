package com.android.myapplication.movies.di

import BASE_URL
import DATABASE_NAME
import androidx.room.Room
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.models.Movie
import com.android.myapplication.movies.persistence.MovieDB
import com.android.myapplication.movies.repository.GenreRepository
import com.android.myapplication.movies.repository.MovieDetailRepository
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.ui.detail.fragments.DetailFragmentViewModel
import com.android.myapplication.movies.ui.genre.GenreListViewModel
import com.android.myapplication.movies.ui.list.MovieListViewModel
import com.android.myapplication.movies.util.AppExecutors
import com.android.myapplication.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val apiModule = module {
    single { AppExecutors() }
    single<MoviesApi> {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(MoviesApi::class.java)
    }
}

val viewModelModule = module {
    viewModel {
        val repository: MoviesRepository = get()
        MovieListViewModel(
            repository,
            androidApplication()
        )
    }
    viewModel { (movie: Movie) ->
        val repository: MovieDetailRepository = get()
        DetailFragmentViewModel(
            androidApplication(),
            repository, movie
        )
    }
    viewModel {
        val repository: GenreRepository = get()
        GenreListViewModel(repository, androidApplication())
    }
}

val repositoryModule = module {
    single {
        val movieDB: MovieDB = get()
        val appExecutors: AppExecutors = get()
        val moviesApi: MoviesApi = get()
        MoviesRepository(movieDB.movieDao, appExecutors, moviesApi)
    }

    single {
        val moviesApi: MoviesApi = get()
        MovieDetailRepository(moviesApi)
    }
    single {
        val movieDB: MovieDB = get()
        val appExecutors: AppExecutors = get()
        val moviesApi: MoviesApi = get()
        GenreRepository(movieDB.genreDao, appExecutors, moviesApi)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MovieDB::class.java,
            DATABASE_NAME
        ).build()
    }
}