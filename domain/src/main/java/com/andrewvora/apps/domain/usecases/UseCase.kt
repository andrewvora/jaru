package com.andrewvora.apps.domain.usecases

import com.andrewvora.apps.domain.CoroutineContextProvider
import kotlinx.coroutines.*

/**
 * Generic use case class that relies on coroutines and can take a parameter.
 */
abstract class UseCase<Type, Params>
internal constructor(
    contextProvider: CoroutineContextProvider
) {
    private var compositeJob = Job()
    private val backgroundContext = contextProvider.computationContext()
    private val foregroundContext = contextProvider.uiContext()

    protected var params: Params? = null
    protected abstract suspend fun doWork(): Type

    /**
     * Runs the use case logic and handles the coroutine scopes.
     */
    fun execute(params: Params? = null, onResult: (Type) -> Unit, onError: (Throwable) -> Unit) {
        // set the current execution parameters
        this.params = params

        // cancel already existing jobs to prevent zombie coroutines
        cancel()

        // create new job to track work
        compositeJob = Job()

        // define exception behavior
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }

        // create a scope that calls back on the foreground
        CoroutineScope(foregroundContext + compositeJob + exceptionHandler).launch {
            val result = withContext(backgroundContext) {
                doWork()
            }
            onResult(result)
        }
    }

    /**
     * Cancels all coroutines that have been launched
     */
    fun cancel() {
        compositeJob.cancelChildren()
        compositeJob.cancel()
    }

    /**
     * Creates a new scope and coroutine that runs async
     */
    protected suspend fun <ReturnType> runAsync(
        block: suspend () -> ReturnType
    ): Deferred<ReturnType> {
        return CoroutineScope(backgroundContext + compositeJob).async {
            return@async block()
        }
    }
}