package com.andrewvora.apps.data

import com.andrewvora.apps.data.dtos.*

/**
 * Created on 1/20/2019.
 */
interface JaruRepository {

    fun downloadFullLearningSet(): LearningSetDto
    fun fetchQuestionSets(): List<QuestionSetDto>
    fun fetchQuestions(setId: String): List<QuestionDto>
    fun fetchAnswers(questionId: String): List<AnswerDto>
    fun fetchGlossaries(): List<GlossaryDto>

}