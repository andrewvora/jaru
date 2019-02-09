package com.andrewvora.apps.data.mappers

import com.andrewvora.apps.data.database.AnswerEntity
import com.andrewvora.apps.data.dtos.AnswerDto

internal object AnswerMapper {

    fun entityToDto(entity: AnswerEntity?): AnswerDto? {
        return entity?.let {
            AnswerDto(
                id = it.answerId,
                text = it.text
            )
        }
    }

    fun dtoToEntity(questionId: String, dto: AnswerDto): AnswerEntity? {
        return dto.takeIf {
            !dto.id.isNullOrBlank()
        }?.let {
            AnswerEntity(
                answerId = dto.id!!,
                text = dto.text,
                questionId = questionId
            )
        }
    }
}