package com.andrewvora.apps.jaru.di.usecases

import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.domain.mappers.GlossaryMapper
import com.andrewvora.apps.domain.mappers.QuestionMapper
import com.andrewvora.apps.domain.mappers.QuestionSetMapper
import com.andrewvora.apps.domain.usecases.*
import dagger.Module
import dagger.Provides


@Module
class UseCaseModule {

    @Provides
    fun downloadLearningSetUseCase(
        jaruRepository: JaruRepository,
        contextProvider: CoroutineContextProvider
    ): DownloadLearningSetUseCase {
        return DownloadLearningSetUseCase(
            repository = jaruRepository,
            questionSetMapper = QuestionSetMapper,
            glossaryMapper = GlossaryMapper,
            contextProvider = contextProvider
        )
    }

    @Provides
    fun getGlossariesUseCase(
        jaruRepository: JaruRepository,
        contextProvider: CoroutineContextProvider
    ): GetGlossariesUseCase {
        return GetGlossariesUseCase(
            jaruRepository = jaruRepository,
            glossaryMapper = GlossaryMapper,
            contextProvider = contextProvider
        )
    }

    @Provides
    fun getGlossaryUseCase(
        jaruRepository: JaruRepository,
        contextProvider: CoroutineContextProvider
    ): GetGlossaryUseCase {
        return GetGlossaryUseCase(
            jaruRepository = jaruRepository,
            glossaryMapper = GlossaryMapper,
            contextProvider = contextProvider
        )
    }

    @Provides
    fun getQuestionSetsUseCase(
        jaruRepository: JaruRepository,
        contextProvider: CoroutineContextProvider
    ): GetQuestionSetsUseCase {
        return GetQuestionSetsUseCase(
            jaruRepository = jaruRepository,
            questionSetMapper = QuestionSetMapper,
            contextProvider = contextProvider
        )
    }

    @Provides
    fun getQuestionSetUseCase(
        jaruRepository: JaruRepository,
        contextProvider: CoroutineContextProvider
    ) : GetQuestionSetUseCase {
        return GetQuestionSetUseCase(
            jaruRepository = jaruRepository,
            mapper = QuestionSetMapper,
            contextProvider = contextProvider
        )
    }

    @Provides
    fun getQuestionsUseCase(
        jaruRepository: JaruRepository,
        contextProvider: CoroutineContextProvider
    ): GetQuestionsUseCase {
        return GetQuestionsUseCase(
            jaruRepository = jaruRepository,
            questionMapper = QuestionMapper,
            contextProvider = contextProvider
        )
    }
}