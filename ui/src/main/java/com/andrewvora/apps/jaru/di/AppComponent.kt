package com.andrewvora.apps.jaru.di

import android.app.Application
import com.andrewvora.apps.jaru.di.usecases.UseCaseModule
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelActivity
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelModule
import com.andrewvora.apps.jaru.downloader.LearningSetDownloadService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created on 1/27/2019.
 */
@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, UseCaseModule::class])
interface AppComponent {

    fun inject(fragment: ViewModelFragment)
    fun inject(activity: ViewModelActivity)
    fun inject(service: LearningSetDownloadService)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}