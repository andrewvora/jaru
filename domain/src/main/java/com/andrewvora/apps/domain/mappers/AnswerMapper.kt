package com.andrewvora.apps.domain.mappers

import com.andrewvora.apps.data.dtos.AnswerDto
import com.andrewvora.apps.domain.models.Answer

object AnswerMapper {

    fun from(answerDto: AnswerDto): Answer {
        return Answer(
            id = answerDto.id ?: "",
            text = answerDto.text ?: ""
        )
    }
}