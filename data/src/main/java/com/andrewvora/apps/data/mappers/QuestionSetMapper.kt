package com.andrewvora.apps.data.mappers

import com.andrewvora.apps.data.database.QuestionSetEntity
import com.andrewvora.apps.data.dtos.QuestionSetDto

internal object QuestionSetMapper {

    fun entityToDto(entity: QuestionSetEntity?): QuestionSetDto? {
        return entity?.let {
            QuestionSetDto(
                id = it.setId,
                difficulty = it.difficulty,
                title = it.title,
                description = it.description,
                questions = null
            )
        }
    }

    fun dtoToEntity(dto: QuestionSetDto): QuestionSetEntity? {
        return dto.takeIf {
            !dto.id.isNullOrBlank()
        }?.let {
            QuestionSetEntity(
                setId = dto.id!!,
                difficulty = dto.difficulty,
                title = dto.title,
                description = dto.description
            )
        }
    }
}