package com.andrewvora.apps.domain.models

/**
 * Created on 1/16/2019.
 */
data class Question(
    val id: String = "",
    val text: String = "",
    val transcript: String = "",
    val answerId: String = "",
    val type: QuestionType = QuestionType.UNKNOWN,
    val answers: List<Answer> = listOf()
)