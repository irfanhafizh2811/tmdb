package com.android.myapplication.movies.api.responses.inner

import com.android.myapplication.movies.models.Cast
import com.google.gson.annotations.SerializedName

class CreditsResponse {
    @SerializedName("cast")
    val casts:List<Cast>?=null
}