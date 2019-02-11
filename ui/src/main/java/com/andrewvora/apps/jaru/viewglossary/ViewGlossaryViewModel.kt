package com.andrewvora.apps.jaru.viewglossary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewvora.apps.domain.models.Glossary
import com.andrewvora.apps.domain.usecases.GetGlossaryUseCase
import javax.inject.Inject

class ViewGlossaryViewModel
@Inject
constructor(
    private val getGlossaryUseCase: GetGlossaryUseCase
): ViewModel() {

    val glossary = MutableLiveData<Glossary>()

    fun loadGlossary(id: String) {
        getGlossaryUseCase.execute(
            params = id,
            onResult = {
                glossary.value = it
            }, onError = {

            })
    }

    override fun onCleared() {
        getGlossaryUseCase.cancel()
    }
}