package com.andrewvora.apps.jaru.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andrewvora.apps.jaru.glossary.GlossaryViewModel
import com.andrewvora.apps.jaru.viewglossary.ViewGlossaryViewModel
import com.andrewvora.apps.jaru.home.HomeViewModel
import com.andrewvora.apps.jaru.questionsets.QuestionSetsViewModel
import com.andrewvora.apps.jaru.quiz.QuizViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds @IntoMap @ViewModelKey(QuestionSetsViewModel::class)
    internal abstract fun bindQuestionSetsViewModel(model: QuestionSetsViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(GlossaryViewModel::class)
    internal abstract fun bindGlossaryViewModel(model: GlossaryViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(model: HomeViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(ViewGlossaryViewModel::class)
    internal abstract fun bindGlossaryViewerViewModel(model: ViewGlossaryViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(QuizViewModel::class)
    internal abstract fun bindQuizViewModel(model: QuizViewModel): ViewModel
}