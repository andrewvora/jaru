package com.andrewvora.apps.data.sources

import com.andrewvora.apps.data.database.*
import com.andrewvora.apps.data.dtos.*
import com.andrewvora.apps.data.mappers.*

/**
 * Created on 1/19/2019.
 */
internal class LearningSetRoomSource(
    private val jaruDb: JaruDb,
    private val questionSetMapper: QuestionSetMapper,
    private val questionMapper: QuestionMapper,
    private val answerMapper: AnswerMapper,
    private val glossaryMapper: GlossaryMapper,
    private val glossaryItemMapper: GlossaryItemMapper
) {
    fun getLearningSet(): LearningSetDto? {
        return LearningSetDto(
            questionSets = jaruDb.questionSetDao()
                .getQuestionSets()
                .mapNotNull {
                    val questions = jaruDb.questionDao().getQuestions(it.setId).mapNotNull { question ->
                        val answers = jaruDb.answerDao().getAnswers(question.questionId).mapNotNull { answer ->
                            answerMapper.entityToDto(answer)
                        }
                        questionMapper.entityToDto(question)?.copy(answers = answers)
                    }
                    questionSetMapper.entityToDto(it)?.copy(questions = questions)
                },
            glossary = jaruDb.glossaryDao()
                .getGlossary()
                .mapNotNull {
                    val items = jaruDb.glossaryItemDao().getGlossaryItems(it.glossaryId).mapNotNull { item ->
                        glossaryItemMapper.entityToDto(item)
                    }
                    glossaryMapper.entityToDto(it)?.copy(items = items)
                })
    }

    fun getQuestionSet(questionSetId: String): QuestionSetDto {
        val setEntity = jaruDb.questionSetDao().get(questionSetId)
        val setDto = questionSetMapper.entityToDto(setEntity) ?: QuestionSetDto()
        val questionDtos = jaruDb.questionDao().getQuestions(setEntity.setId).map {
            questionMapper.entityToDto(it)
        }
        return setDto.copy(questions = questionDtos.mapNotNull { question ->
            return@mapNotNull question?.copy(
                answers = jaruDb
                    .answerDao()
                    .getAnswers(question.id ?: "")
                    .mapNotNull {
                        answerMapper.entityToDto(it)
                    })
        })
    }

    fun getQuestions(questionSetId: String): List<QuestionDto> {
        return jaruDb.questionDao()
            .getQuestions(setId = questionSetId)
            .mapNotNull { question ->
                questionMapper.entityToDto(
                    entity = question
                )?.copy(answers = jaruDb.answerDao().getAnswers(question.questionId).mapNotNull { answer ->
                    answerMapper.entityToDto(answer)
                })
            }
    }

    fun getAnswers(questionId: String): List<AnswerDto> {
        return jaruDb.answerDao()
            .getAnswers(questionId = questionId)
            .mapNotNull {
                answerMapper.entityToDto(
                    entity = it
                )
            }
    }

    fun saveLearningSet(learningSetDto: LearningSetDto) {
        // save the entire set
        val (sets, questions, answers) = saveQuestionSet(learningSetDto.questionSets)
        val (glossaries, glossaryItems) = saveGlossary(learningSetDto.glossary)

        jaruDb.questionSetDao().insert(*sets.toTypedArray())
        jaruDb.questionDao().insert(*questions.toTypedArray())
        jaruDb.answerDao().insert(*answers.toTypedArray())
        jaruDb.glossaryDao().insert(*glossaries.toTypedArray())
        jaruDb.glossaryItemDao().insert(*glossaryItems.toTypedArray())
    }

    private fun saveGlossary(glossaries: List<GlossaryDto>?): GlossarySaveResult {
        val glossaryEntities = mutableListOf<GlossaryEntity>()
        val glossaryItemEntities = mutableListOf<GlossaryItemEntity>()

        glossaries?.map { glossaryDto ->
            glossaryMapper.dtoToEntity(glossaryDto)?.let { glossaryEntity ->
                glossaryEntities.add(glossaryEntity)

                glossaryDto.items?.map { itemDto ->
                    glossaryItemMapper.dtoToEntity(glossaryEntity.glossaryId, itemDto)?.let {
                        glossaryItemEntities.add(it)
                    }
                }
            }
        }

        return GlossarySaveResult(
            glossaries = glossaryEntities,
            glossaryItems = glossaryItemEntities
        )
    }

    private fun saveQuestionSet(sets: List<QuestionSetDto>?): QuestionSetSaveResult {
        val setEntities = mutableListOf<QuestionSetEntity>()
        val questionEntities = mutableListOf<QuestionEntity>()
        val answerEntities = mutableListOf<AnswerEntity>()

        // save the entire set
        sets?.map { setDto ->
            questionSetMapper.dtoToEntity(setDto)?.let { setEntity ->
                // save each set if it can be mapped
                setEntities.add(setEntity)

                setDto.questions?.map {  questionDto ->
                    questionMapper.dtoToEntity(setEntity.setId, questionDto)?.let { questionEntity ->
                        // save questions if question can be mapped
                        questionEntities.add(questionEntity)

                        questionDto.answers?.map { answerDto ->
                            answerMapper.dtoToEntity(questionEntity.questionId, answerDto)?.let {
                                // save answers if answer can be mapped
                                answerEntities.add(it)
                            }
                        }
                    }
                }
            }
        }

        return QuestionSetSaveResult(
            sets = setEntities,
            questions = questionEntities,
            answers = answerEntities
        )
    }

    fun clear() {
        jaruDb.clearAllTables()
    }

    private data class QuestionSetSaveResult(
        val sets: List<QuestionSetEntity>,
        val questions: List<QuestionEntity>,
        val answers: List<AnswerEntity>)

    private data class GlossarySaveResult(
        val glossaries: List<GlossaryEntity>,
        val glossaryItems: List<GlossaryItemEntity>
    )
}