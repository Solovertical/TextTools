package com.corphish.quicktools.viewmodels

import androidx.lifecycle.ViewModel
import com.corphish.quicktools.repository.AppMode
import com.corphish.quicktools.repository.ContextMenuOptionsRepository
import com.corphish.quicktools.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val contextOptionsRepository: ContextMenuOptionsRepository,
) : ViewModel() {
    private val _onBoardingDone = MutableStateFlow(settingsRepository.getOnboardingDone())
    val onBoardingDone = _onBoardingDone.asStateFlow()

    fun setOnBoardingDone(done: Boolean) {
        settingsRepository.setOnboardingDone(done)
        _onBoardingDone.value = done
    }

    fun setAppMode(mode: AppMode) {
        contextOptionsRepository.setCurrentAppMode(mode)
    }
}