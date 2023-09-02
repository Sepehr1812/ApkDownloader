package com.apkdownloader.usecases

import com.apkdownloader.remote.NetworkResult
import com.apkdownloader.repos.AuthRepository
import javax.inject.Inject

class GetAuthData @Inject constructor(private val authRepository: AuthRepository) :
    GeneralUseCase<GetAuthData.RequestValues, NetworkResult<List<Int>>>() {

    class RequestValues : GeneralUseCase.RequestValues

    override suspend fun executeUseCase(requestValues: RequestValues) =
        authRepository.getAuthData()
}