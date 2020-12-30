package com.android.myapplication.movies.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.myapplication.movies.models.Genre
import com.android.myapplication.movies.models.Movie
import com.android.myapplication.movies.persistence.dao.GenreDao
import com.android.myapplication.movies.persistence.dao.MovieAndDetailDao

@Database(
    entities = [Movie::class, Genre::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
abstract class MovieDB : RoomDatabase() {

    abstract val movieDao: MovieAndDetailDao

    abstract val genreDao: GenreDao

}