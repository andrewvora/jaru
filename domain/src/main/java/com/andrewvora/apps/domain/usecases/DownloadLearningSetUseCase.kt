package com.andrewvora.apps.domain.usecases

import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.domain.mappers.GlossaryMapper
import com.andrewvora.apps.domain.mappers.QuestionSetMapper
import com.andrewvora.apps.domain.models.LearningSet

/**
 * Created on 1/21/2019.
 */
class DownloadLearningSetUseCase
constructor(
    private val repository: JaruRepository,
    private val questionSetMapper: QuestionSetMapper,
    private val glossaryMapper: GlossaryMapper,
    contextProvider: CoroutineContextProvider
) : UseCase<LearningSet, Unit>(contextProvider = contextProvider) {

    override suspend fun doWork(): LearningSet {
        return runAsync {
            return@runAsync repository.downloadFullLearningSet().let { learningSet ->
                LearningSet(
                    glossaries = learningSet.glossary?.map { glossaryMapper.from(it) } ?: emptyList(),
                    sets = learningSet.questionSets?.map { questionSetMapper.from(it) } ?: emptyList()
                )
            }
        }.await()
    }
}