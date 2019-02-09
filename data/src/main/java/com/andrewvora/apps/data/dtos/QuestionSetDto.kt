package com.andrewvora.apps.data.dtos

import com.squareup.moshi.Json

/**
 * Created on 1/16/2019.
 */
data class QuestionSetDto(
    @Json(name="id") val id: String? = null,
    @Json(name="difficulty") val difficulty: String? = null,
    @Json(name="title") val title: String? = null,
    @Json(name="description") val description: String? = null,
    @Json(name="questions") val questions: List<QuestionDto>? = null
)