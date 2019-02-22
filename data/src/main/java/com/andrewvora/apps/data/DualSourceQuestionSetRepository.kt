package com.andrewvora.apps.data

import com.andrewvora.apps.data.dtos.*
import com.andrewvora.apps.data.sources.LearningSetRoomSource
import com.andrewvora.apps.data.sources.LearningSetNetworkSource

/**
 * Repository implementation that uses a Room-backed source
 * and a Retrofit-backed source.
 */
class DualSourceQuestionSetRepository
internal constructor(
    private val localSource: LearningSetRoomSource,
    private val remoteSource: LearningSetNetworkSource
): JaruRepository {

    override fun downloadFullLearningSet(): LearningSetDto {
        val downloadedLearningSet = remoteSource.getContent()
        downloadedLearningSet?.let {
            localSource.saveLearningSet(it)
        }

        return downloadedLearningSet ?: LearningSetDto()
    }

    override fun fetchQuestionSets(): List<QuestionSetDto> {
        return localSource.getLearningSet()?.questionSets ?: emptyList()
    }

    override fun fetchQuestionSet(id: String): QuestionSetDto {
        return localSource.getQuestionSet(id)
    }

    override fun fetchQuestions(setId: String): List<QuestionDto> {
        return localSource.getQuestions(questionSetId = setId)
    }

    override fun fetchAnswers(questionId: String): List<AnswerDto> {
        return localSource.getAnswers(questionId = questionId)
    }

    override fun fetchGlossaries(): List<GlossaryDto> {
        return localSource.getLearningSet()?.glossary ?: emptyList()
    }
}