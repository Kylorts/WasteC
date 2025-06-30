package com.example.wastec.presentation.viewmodel.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastec.domain.usecase.GetThemeUseCase
import com.example.wastec.domain.usecase.SaveThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase
) : ViewModel() {

    val isDarkMode: StateFlow<Boolean> = getThemeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            saveThemeUseCase(isDarkMode)
        }
    }
}