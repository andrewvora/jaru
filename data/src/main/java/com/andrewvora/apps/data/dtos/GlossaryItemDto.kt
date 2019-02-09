package com.andrewvora.apps.data.dtos

import com.squareup.moshi.Json


data class GlossaryItemDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "text") val text: String? = null,
    @Json(name = "transcript") val transcript: String? = null,
    @Json(name = "order") val order: Int = 0
)