package com.andrewvora.apps.data.mappers

import com.andrewvora.apps.data.database.QuestionEntity
import com.andrewvora.apps.data.dtos.QuestionDto

internal object QuestionMapper {

    fun entityToDto(entity: QuestionEntity?): QuestionDto? {
        return entity?.let {
            QuestionDto(
                id = it.questionId,
                text = it.text,
                transcript = it.transcript,
                correctAnswerId = it.answerId,
                type = it.type,
                answers = null
            )
        }
    }

    fun dtoToEntity(setId: String, dto: QuestionDto): QuestionEntity? {
        return dto.takeIf {
            !dto.id.isNullOrBlank()
        }?.let {
            QuestionEntity(
                questionId = dto.id!!,
                text = dto.text,
                transcript = dto.transcript,
                answerId = dto.correctAnswerId,
                type = dto.type,
                setId = setId
            )
        }
    }
}