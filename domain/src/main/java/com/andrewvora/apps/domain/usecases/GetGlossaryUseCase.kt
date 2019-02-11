package com.andrewvora.apps.domain.usecases

import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.domain.mappers.GlossaryMapper
import com.andrewvora.apps.domain.models.Glossary

class GetGlossaryUseCase
constructor(
    private val jaruRepository: JaruRepository,
    private val glossaryMapper: GlossaryMapper,
    contextProvider: CoroutineContextProvider
) : UseCase<Glossary, String>(
    contextProvider = contextProvider) {

    override suspend fun doWork(): Glossary {
        return runAsync { jaruRepository.fetchGlossaries().find {
                it.id == params
            }?.let {
                glossaryMapper.from(it)
            } ?: throw Exception("Could not find glossary with ID: $params")
        }.await()
    }
}