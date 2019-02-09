package com.andrewvora.apps.domain.mappers

import com.andrewvora.apps.data.dtos.QuestionSetDto
import com.andrewvora.apps.domain.models.Difficulty
import com.andrewvora.apps.domain.models.QuestionSet

object QuestionSetMapper {

    fun from(questionSetDto: QuestionSetDto): QuestionSet {
        return QuestionSet(
            id = questionSetDto.id ?: "",
            difficulty = Difficulty.from(questionSetDto.difficulty),
            title = questionSetDto.title ?: "",
            description = questionSetDto.description ?: "",
            questions = questionSetDto.questions?.map {
                QuestionMapper.from(it)
            } ?: emptyList()
        )
    }
}