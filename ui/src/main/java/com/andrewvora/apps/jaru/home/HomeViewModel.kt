package com.andrewvora.apps.jaru.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewvora.apps.jaru.downloader.LearningSetDownloader
import javax.inject.Inject

/**
 * Created on 2/1/2019.
 */
class HomeViewModel
@Inject
constructor(
       private val downloader: LearningSetDownloader
): ViewModel(), LearningSetDownloader.Subscriber {

    val downloadingLearningSet = MutableLiveData<Boolean>()

    init {
        downloader.subscribe(this)
    }

    fun downloadLearningSet() {
        downloadingLearningSet.value = true
        downloader.load()
    }

    override fun onComplete() {
        downloadingLearningSet.value = false
    }

    override fun onCleared() {
        downloader.unsubscribe(this)
    }
}