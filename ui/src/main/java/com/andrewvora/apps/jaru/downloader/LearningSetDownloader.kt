package com.andrewvora.apps.jaru.downloader

import android.app.Application
import android.content.Intent
import androidx.core.app.JobIntentService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LearningSetDownloader
@Inject
constructor(
    private val app: Application
) {
    private val subscribers: MutableList<Subscriber> = mutableListOf()
        @Synchronized get

    fun load() {
        val jobId = 100
        val intent = Intent()
        JobIntentService.enqueueWork(
            app,
            LearningSetDownloadService::class.java,
            jobId,
            intent)
    }

    fun complete() {
        subscribers.forEach {
            it.onComplete()
        }
        subscribers.clear()
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