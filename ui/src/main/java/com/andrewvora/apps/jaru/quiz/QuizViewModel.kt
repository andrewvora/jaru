package com.andrewvora.apps.jaru.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewvora.apps.domain.models.QuestionSet
import com.andrewvora.apps.domain.usecases.GetQuestionSetUseCase
import javax.inject.Inject

class QuizViewModel
@Inject
constructor(
    private val getQuestionSetUseCase: GetQuestionSetUseCase
) : ViewModel() {

    val questionSet = MutableLiveData<QuestionSet>()

    fun loadQuestions(setId: String) {
        getQuestionSetUseCase.execute(
            params = setId,
            onResult = {
                questionSet.value = it
            },
            onError = {

            }
        )
    }

    fun answerQuestion() {

    }

    override fun onCleared() {
        getQuestionSetUseCase.cancel()
    }
}