package com.andrewvora.apps.domain.models


data class Glossary(
    val id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val items: List<GlossaryItem> = listOf()
)