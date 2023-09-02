package com.apkdownloader.remote

import retrofit2.HttpException
import retrofit2.Response


/**
 * A general function to execute API requests.
 *
 * @param execute the API function defined in the API interface
 * @param caster casts the body of the successful response to the desired type
 * @param onError will be called whenever exceptions occur or API responses with error code
 *
 * @return [NetworkResult]
 */
suspend fun <T : Any, R : Any> executeApi(
    execute: suspend () -> Response<T>,
    caster: (T) -> R,
    onError: (String?) -> Unit = {}
): NetworkResult<R> = try {
    val response = execute()
    val body = response.body()

    if (response.isSuccessful && body != null) NetworkResult.Success(caster(body))
    else {
        onError(response.message())
        NetworkResult.Error(code = response.code(), message = response.message())
    }
} catch (e: HttpException) {
    onError(e.message)
    NetworkResult.Error(code = e.code(), message = e.message())
} catch (e: Throwable) {
    onError(e.message)
    NetworkResult.Exception(e)
}