package com.apkdownloader.usecases

import com.apkdownloader.models.AppData
import com.apkdownloader.remote.NetworkResult
import com.apkdownloader.repos.AppRepository
import javax.inject.Inject

class GetAppData @Inject constructor(private val appRepository: AppRepository) :
    GeneralUseCase<GetAppData.RequestValues, NetworkResult<AppData>>() {

    data class RequestValues(val authToken: String) : GeneralUseCase.RequestValues

    override suspend fun executeUseCase(requestValues: RequestValues) =
        appRepository.getAppData(requestValues.authToken)
}