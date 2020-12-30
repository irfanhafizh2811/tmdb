package com.android.myapplication.movies.api

import androidx.lifecycle.LiveData
import com.android.myapplication.movies.BuildConfig
import com.android.myapplication.movies.api.responses.ApiResponse
import com.android.myapplication.movies.api.responses.MovieDetailsResponse
import com.android.myapplication.movies.api.responses.MoviesResponse
import com.android.myapplication.movies.api.responses.inner.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MoviesResponse>>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MoviesResponse>>

    @GET("movie/upcoming")
    fun geUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MoviesResponse>>

    @GET("discover/movie")
    fun getMoviesByGenres(
        @Query("with_genres") genres: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MoviesResponse>>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String? = null,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MoviesResponse>>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Long,
        @Query("append_to_response") details: String = "videos,credits,reviews",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MovieDetailsResponse>>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String = BuildConfig.API_KEY): LiveData<ApiResponse<GenreResponse>>

}