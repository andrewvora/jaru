package com.andrewvora.apps.domain.models

/**
 * Created on 1/31/2019.
 */
data class LearningSet(
    val glossaries: List<Glossary>,
    val sets: List<QuestionSet>
)