package com.aayar94.foodrecipes.ui.onboarding

import androidx.lifecycle.ViewModel
import com.aayar94.foodrecipes.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val readLandingFinished = dataStoreRepository.readLandingFinished


    suspend fun saveLandingFinished(isFinished: Boolean) {
        dataStoreRepository.landingFinished(isFinished)
    }

}