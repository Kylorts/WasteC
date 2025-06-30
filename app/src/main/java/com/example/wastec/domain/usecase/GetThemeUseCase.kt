package com.example.wastec.domain.usecase

import com.example.wastec.domain.repository.WasteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(private val repository: WasteRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getThemeSetting()
}