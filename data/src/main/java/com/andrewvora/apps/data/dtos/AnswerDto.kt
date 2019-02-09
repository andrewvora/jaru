package com.andrewvora.apps.data.dtos

import com.squareup.moshi.Json


data class AnswerDto(
    @Json(name="id") val id: String? = null,
    @Json(name="text") val text: String? = null
)