package com.android.myapplication.movies.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "genre",
    indices = [Index("id")],
    primaryKeys = ["id"]
)
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) : Parcelable