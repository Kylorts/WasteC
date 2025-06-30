package com.example.wastec.domain.usecase

import android.graphics.Bitmap
import com.example.wastec.domain.model.ClassificationResult
import com.example.wastec.domain.repository.WasteRepository
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val wasteRepository: WasteRepository
) {
    suspend operator fun invoke(result: ClassificationResult, image: Bitmap) {
        wasteRepository.saveHistory(result, image)
    }
}