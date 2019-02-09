package com.andrewvora.apps.data.dtos

import com.squareup.moshi.Json


data class LearningSetDto(
    @Json(name="sets") val questionSets: List<QuestionSetDto>? = null,
    @Json(name="glossary") val glossary: List<GlossaryDto>? = null
)