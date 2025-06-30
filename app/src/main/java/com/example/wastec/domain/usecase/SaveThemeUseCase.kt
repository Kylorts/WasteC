package com.example.wastec.domain.usecase

import com.example.wastec.domain.repository.WasteRepository
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(private val repository: WasteRepository) {
    suspend operator fun invoke(isDarkMode: Boolean) = repository.saveThemeSetting(isDarkMode)
}