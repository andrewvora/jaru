package com.andrewvora.apps.jaru.downloader

import com.andrewvora.apps.domain.usecases.DownloadLearningSetUseCase
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LearningSetDownloader
@Inject
constructor(
    private val downloadUseCase: DownloadLearningSetUseCase
) {
    private val subscribers: MutableList<Subscriber> = mutableListOf()
        @Synchronized get

    fun load() {
        downloadUseCase.execute(onResult = {
            complete()
        }, onError = {
            complete()
        })
    }

    private fun complete() {
        subscribers.forEach {
            it.onComplete()
        }
    }

    fun subscribe(sub: Subscriber) {
        subscribers.add(sub)
    }

    fun unsubscribe(sub: Subscriber) {
        subscribers.remove(sub)
    }

    interface Subscriber {
        fun onComplete()
    }
}