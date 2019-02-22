package com.andrewvora.apps.domain.usecases

import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.domain.mappers.QuestionSetMapper
import com.andrewvora.apps.domain.models.QuestionSet

class GetQuestionSetUseCase
constructor(
    private val jaruRepository: JaruRepository,
    private val mapper: QuestionSetMapper,
    contextProvider: CoroutineContextProvider
) : UseCase<QuestionSet, String>(contextProvider) {

    override suspend fun doWork(): QuestionSet {
        return runAsync {
            val id = params ?: ""
            val setDto = jaruRepository.fetchQuestionSet(id)
            mapper.from(setDto)
        }.await()
    }

}