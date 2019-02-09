package com.andrewvora.apps.domain.usecases

import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.domain.mappers.GlossaryMapper
import com.andrewvora.apps.domain.models.Glossary

class GetGlossariesUseCase
constructor(
    private val jaruRepository: JaruRepository,
    private val glossaryMapper: GlossaryMapper,
    contextProvider: CoroutineContextProvider
) : UseCase<List<Glossary>, Unit>(contextProvider = contextProvider) {

    override suspend fun doWork(): List<Glossary> {
        return runAsync {
            jaruRepository.fetchGlossaries().map {
                    glossaryMapper.from(it)
                }
        }.await()
    }
}