package com.andrewvora.apps.data

import com.andrewvora.apps.data.dtos.*


interface JaruRepository {

    fun downloadFullLearningSet(): LearningSetDto
    fun fetchQuestionSets(): List<QuestionSetDto>
    fun fetchQuestionSet(id: String): QuestionSetDto
    fun fetchQuestions(setId: String): List<QuestionDto>
    fun fetchAnswers(questionId: String): List<AnswerDto>
    fun fetchGlossaries(): List<GlossaryDto>

}