package com.andrewvora.apps.domain.mappers

import com.andrewvora.apps.data.dtos.QuestionDto
import com.andrewvora.apps.domain.models.Question
import com.andrewvora.apps.domain.models.QuestionType

object QuestionMapper {

    fun from(questionDto: QuestionDto): Question {
        return Question(
            id = questionDto.id ?: "",
            type = QuestionType.from(questionDto.type),
            text = questionDto.text ?: "",
            transcript = questionDto.transcript ?: "",
            answerId = questionDto.correctAnswerId ?: "",
            answers = questionDto.answers?.map {
                AnswerMapper.from(it)
            } ?: emptyList()
        )
    }
}