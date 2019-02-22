package com.andrewvora.apps.domain.usecases

import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.domain.mappers.QuestionMapper
import com.andrewvora.apps.domain.models.Question
import com.andrewvora.apps.domain.models.QuestionSet


class GetQuestionsUseCase
constructor(
    private val jaruRepository: JaruRepository,
    private val questionMapper: QuestionMapper,
    contextProvider: CoroutineContextProvider
): UseCase<List<Question>, QuestionSet>(contextProvider = contextProvider) {

    override suspend fun doWork(): List<Question> {
        return params?.let { set ->
            runAsync {
                jaruRepository.fetchQuestions(set.id).map {
                    questionMapper.from(it)
                }
            }.await()
        } ?: emptyList()
    }
}