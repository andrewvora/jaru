package com.andrewvora.apps.jaru.downloader

import android.content.Intent
import androidx.core.app.JobIntentService
import com.andrewvora.apps.domain.usecases.DownloadLearningSetUseCase
import com.andrewvora.apps.jaru.di.Injector
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

/**
 * Created on 2/1/2019.
 */
class LearningSetDownloadService: JobIntentService() {

    @Inject lateinit var useCase: DownloadLearningSetUseCase
    @Inject lateinit var downloader: LearningSetDownloader

    override fun onHandleWork(intent: Intent) {
        Injector.inject(this)

        val latch = CountDownLatch(1)
        useCase.execute(onResult = {
            latch.countDown()
            downloader.complete()
        }, onError = {
            Timber.e(it)
            latch.countDown()
            downloader.complete()
        })
        latch.await()
    }
}