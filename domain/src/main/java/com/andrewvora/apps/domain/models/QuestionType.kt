package com.andrewvora.apps.domain.models

/**
 * Created on 1/16/2019.
 */
enum class QuestionType {
    SINGLE_INPUT,
    MULTIPLE_CHOICE,
    FREE_FORM,
    UNKNOWN;

    companion object {
        fun from(value: String?): QuestionType {
            return when (value) {
                "single_input" -> SINGLE_INPUT
                "multiple_choice" -> MULTIPLE_CHOICE
                "free_form" -> FREE_FORM
                else -> UNKNOWN
            }
        }
    }
}