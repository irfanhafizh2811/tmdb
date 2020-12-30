package com.android.myapplication.movies.api.responses.inner

import com.android.myapplication.movies.models.Review
import com.google.gson.annotations.SerializedName

class ReviewsResponse {
    @SerializedName("results")
    val reviews:List<Review>?=null
}