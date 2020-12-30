package com.android.myapplication.movies.models

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Video(
    @SerializedName("id")
    val id: String = UUID.randomUUID().toString(),
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("site")
    val site: String? = null
)