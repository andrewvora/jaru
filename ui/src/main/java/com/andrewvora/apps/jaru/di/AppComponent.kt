package com.andrewvora.apps.jaru.di

import android.app.Application
import com.andrewvora.apps.jaru.di.usecases.UseCaseModule
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelActivity
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelModule
import com.andrewvora.apps.jaru.quiz.QuizFragment
import com.andrewvora.apps.jaru.tts.TextToSpeechHelper
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, UseCaseModule::class])
interface AppComponent {

    fun textToSpeechHelper(): TextToSpeechHelper

    fun inject(fragment: ViewModelFragment)
    fun inject(activity: ViewModelActivity)
    fun inject(fragment: QuizFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}