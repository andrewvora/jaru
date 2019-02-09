package com.andrewvora.apps.data.dtos

import com.squareup.moshi.Json


data class GlossaryDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "subtitle") val subtitle: String? = null,
    @Json(name = "items") val items: List<GlossaryItemDto>? = null
)