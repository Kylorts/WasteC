package com.example.wastec.domain.usecase

import android.graphics.Bitmap
import com.example.wastec.domain.repository.WasteRepository

class ClassifyWasteUseCase(private val wasteRepository: WasteRepository) {
    suspend operator fun invoke(bitmap: Bitmap) = wasteRepository.classifyImage(bitmap)
}