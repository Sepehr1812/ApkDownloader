package com.apkdownloader.usecases

/**
 * A general use-case that only calls repository methods.
 */
abstract class GeneralUseCase<in Q : GeneralUseCase.RequestValues, R> {

    /**
     * Data passed to a request
     */
    interface RequestValues

    abstract suspend fun executeUseCase(requestValues: Q): R
}