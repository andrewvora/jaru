package com.andrewvora.apps.domain.usecases

import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.domain.mappers.QuestionSetMapper
import com.andrewvora.apps.domain.models.QuestionSet


class GetQuestionSetsUseCase
constructor(
    private val jaruRepository: JaruRepository,
    private val questionSetMapper: QuestionSetMapper,
    contextProvider: CoroutineContextProvider
): UseCase<List<QuestionSet>, Unit>(contextProvider = contextProvider) {

    override suspend fun doWork(): List<QuestionSet> {
        return runAsync {
            jaruRepository.fetchQuestionSets().map {
                questionSetMapper.from(it)
            }
        }.await()
    }
}