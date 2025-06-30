package com.example.wastec.domain.usecase

import com.example.wastec.domain.repository.WasteRepository
import com.example.wastec.presentation.model.WasteCategoryUi
import javax.inject.Inject

class GetEducationCategoryDetailUseCase @Inject constructor(
    private val repository: WasteRepository
) {
    suspend operator fun invoke(id: Int): WasteCategoryUi? = repository.getEducationCategoryDetail(id)
}