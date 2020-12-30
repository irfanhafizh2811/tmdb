package com.android.myapplication.movies.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.myapplication.movies.models.Genre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(vararg genre: Genre): LongArray

    @Query("SELECT * FROM genre ")
    fun getGenres(): LiveData<List<Genre>>

}