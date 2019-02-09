package com.andrewvora.apps.data.dtos

import com.squareup.moshi.Json

/**
 * Created on 1/16/2019.
 */
data class QuestionDto(
    @Json(name="id") val id: String? = null,
    @Json(name="text") val text: String? = null,
    @Json(name="transcription") val transcript: String? = null,
    @Json(name="correctAnswerId") val correctAnswerId: String? = null,
    @Json(name="type") val type: String? = null,
    @Json(name="answers") val answers: List<AnswerDto>? = null
)