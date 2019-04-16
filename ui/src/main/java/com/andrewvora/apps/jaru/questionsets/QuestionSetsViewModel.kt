package com.andrewvora.apps.jaru.questionsets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewvora.apps.domain.models.QuestionSet
import com.andrewvora.apps.domain.usecases.GetQuestionSetsUseCase
import com.andrewvora.apps.jaru.downloader.LearningSetDownloader
import javax.inject.Inject

class QuestionSetsViewModel
@Inject
constructor(
    private val useCase: GetQuestionSetsUseCase,
    private val downloader: LearningSetDownloader
): ViewModel(), LearningSetDownloader.Subscriber {

    val questionSets = MutableLiveData<List<QuestionSet>>()
    val error = MutableLiveData<String>()

    init {
        downloader.subscribe(this)
    }

    fun loadQuestionSets() {
        useCase.execute(onResult = {
            questionSets.value = it
        }, onError = {
            error.value = it.message
        })
    }

    override fun onCleared() {
        useCase.cancel()
        downloader.unsubscribe(this)
    }

    override fun onComplete() {
        loadQuestionSets()
    }
}