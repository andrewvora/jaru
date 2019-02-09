package com.andrewvora.apps.data.apis

import com.andrewvora.apps.data.dtos.LearningSetDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created on 1/19/2019.
 */
internal interface JaruApi {

    @GET("/api/v1/full")
    fun getFullLearningSet(@Query("locale") locale: String = "en-US"): Call<LearningSetDto>
}