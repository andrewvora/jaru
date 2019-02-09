package com.andrewvora.apps.domain.models


data class QuestionSet(
    val id: String = "",
    val difficulty: Difficulty = Difficulty.UNRATED,
    val title: String = "",
    val description: String = "",
    val questions: List<Question> = listOf()
)