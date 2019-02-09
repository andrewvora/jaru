package com.andrewvora.apps.domain

import kotlin.coroutines.CoroutineContext

/**
 * Created on 1/22/2019.
 */
interface CoroutineContextProvider {
    fun computationContext(): CoroutineContext
    fun uiContext(): CoroutineContext
}