package com.android.myapplication.movies.api.responses


import com.android.myapplication.movies.api.responses.inner.CreditsResponse
import com.android.myapplication.movies.api.responses.inner.ReviewsResponse
import com.android.myapplication.movies.api.responses.inner.VideoResponse
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("videos")
    val videoResponse: VideoResponse? = null,

    @SerializedName("reviews")
    val reviewResponse: ReviewsResponse? = null,

    @SerializedName("credits")
    val creditsResponse: CreditsResponse? = null
)