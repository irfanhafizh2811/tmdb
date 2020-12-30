package com.android.myapplication.movies.api.responses.inner

import com.android.myapplication.movies.models.Genre
import com.google.gson.annotations.SerializedName

class GenreResponse {
    @SerializedName("genres")
    val genres:List<Genre>?=null
}