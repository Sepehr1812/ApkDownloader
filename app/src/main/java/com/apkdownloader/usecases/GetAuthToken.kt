package com.apkdownloader.usecases

import com.apkdownloader.remote.NetworkResult
import com.apkdownloader.repos.AuthRepository
import javax.inject.Inject

class GetAuthToken @Inject constructor(private val authRepository: AuthRepository) :
    GeneralUseCase<GetAuthToken.RequestValues, NetworkResult<String>>() {

    data class RequestValues(val answer: Int) : GeneralUseCase.RequestValues

    override suspend fun executeUseCase(requestValues: RequestValues) =
        authRepository.getAuthToken(requestValues.answer)
}