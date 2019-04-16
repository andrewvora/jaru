package com.andrewvora.apps.jaru.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewvora.apps.domain.models.Answer
import com.andrewvora.apps.domain.models.Question
import com.andrewvora.apps.domain.models.QuestionSet
import com.andrewvora.apps.domain.models.QuestionType
import com.andrewvora.apps.domain.usecases.GetQuestionSetUseCase
import timber.log.Timber
import javax.inject.Inject

class QuizViewModel
@Inject
constructor(
    private val getQuestionSetUseCase: GetQuestionSetUseCase
) : ViewModel() {

    val questionSet = MutableLiveData<QuestionSet>()
    val answerResult = MutableLiveData<AnswerResult>()

    private var correctAnswerCount = 0

    fun loadQuestions(setId: String) {
        getQuestionSetUseCase.execute(
            params = setId,
            onResult = {
                questionSet.value = it.copy(questions = it.questions.shuffled())
            },
            onError = {
                Timber.e(it)
            }
        )
    }

    fun getScore(): String {
        return "$correctAnswerCount / ${questionSet.value?.questions?.size ?: 0}"
    }

    fun answerQuestion(question: Question, answer: Answer) {
        val isCorrect = when (question.type) {
            QuestionType.SINGLE_INPUT -> question.answers.any { it.text == answer.text.trim() }
            QuestionType.MULTIPLE_CHOICE -> question.answerId == answer.id
            QuestionType.FREE_FORM -> true
            else -> false
        }
        correctAnswerCount += if (isCorrect) 1 else 0

        answerResult.value = AnswerResult(question.answers, isCorrect)
    }

    override fun onCleared() {
        getQuestionSetUseCase.cancel()
    }

    data class AnswerResult(val answers: List<Answer>, val wasCorrect: Boolean)
}