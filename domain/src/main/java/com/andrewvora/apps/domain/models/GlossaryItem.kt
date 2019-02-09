package com.andrewvora.apps.domain.models


data class GlossaryItem(
    val itemId: String = "",
    val text: String = "",
    val transcript: String = "",
    val displayOrder: Int = 0
)