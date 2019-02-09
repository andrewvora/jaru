package com.andrewvora.apps.jaru.glossary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewvora.apps.domain.models.Glossary
import com.andrewvora.apps.domain.usecases.GetGlossariesUseCase
import javax.inject.Inject

class GlossaryViewModel
@Inject
constructor(
    private val useCase: GetGlossariesUseCase
): ViewModel() {

    val glossaries = MutableLiveData<List<Glossary>>()

    fun loadGlossaries() {
        useCase.execute(onResult = {
            glossaries.value = it
        }, onError = {})
    }

    override fun onCleared() {
        useCase.cancel()
    }
}