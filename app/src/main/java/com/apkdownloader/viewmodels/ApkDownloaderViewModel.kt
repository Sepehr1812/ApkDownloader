package com.apkdownloader.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apkdownloader.remote.NetworkResult
import com.apkdownloader.usecases.GetAuthData
import com.apkdownloader.usecases.GetAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApkDownloaderViewModel @Inject constructor(
    private val getAuthData: GetAuthData,
    private val getAuthToken: GetAuthToken
) : ViewModel() {

    private val _errorState = MutableStateFlow("")
    val errorState = _errorState.asStateFlow()

    init {
        getAuthData()
    }

    private fun getAuthData() {
        viewModelScope.launch {
            getAuthData.executeUseCase(GetAuthData.RequestValues()).also {

                when (it) {
                    is NetworkResult.Success -> {
                        val answer = calculateMaxRevenue(it.data)
                        getAuthToken(answer)
                    }

                    is NetworkResult.Error ->
                        _errorState.update { _ -> "Network Error: Code ${it.code}, ${it.message}" }

                    is NetworkResult.Exception ->
                        _errorState.update { _ -> "Network Exception: ${it.e.message}" }
                }
            }
        }
    }

    private fun getAuthToken(answer: Int) {
        viewModelScope.launch {
            getAuthToken.executeUseCase(GetAuthToken.RequestValues(answer)).also {

                when (it) {
                    is NetworkResult.Success -> {
                        Log.d("ApkDownloaderViewModel::getAuthToken", it.data)
                        // TODO: call app api
                    }

                    is NetworkResult.Error ->
                        _errorState.update { _ -> "Network Error: Code ${it.code}, ${it.message}" }

                    is NetworkResult.Exception ->
                        _errorState.update { _ -> "Network Exception: ${it.e.message}" }
                }
            }
        }
    }

    /**
     * Calculates the maximum revenue due to price list.
     *
     * @param arr the price list
     * @return the maximum revenue
     */
    private fun calculateMaxRevenue(arr: List<Int>): Int {
        var min = arr.first()
        var rev = 0
        arr.forEach {
            if (it > min) rev += it.minus(min)
            min = it
        }

        return rev
    }
}