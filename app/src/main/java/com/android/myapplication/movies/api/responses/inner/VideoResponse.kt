package com.android.myapplication.movies.api.responses.inner

import com.android.myapplication.movies.models.Video
import com.google.gson.annotations.SerializedName

class VideoResponse {
    @SerializedName("results")
    val videos:List<Video>?=null
}