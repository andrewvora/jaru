package com.andrewvora.apps.domain.models

/**
 * Created on 1/16/2019.
 */
enum class Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    UNRATED;

    companion object {
        fun from(value: String?): Difficulty {
            return when (value) {
                "beginner" -> BEGINNER
                "intermediate" -> INTERMEDIATE
                "advanced" -> ADVANCED
                else -> UNRATED
            }
        }
    }
}