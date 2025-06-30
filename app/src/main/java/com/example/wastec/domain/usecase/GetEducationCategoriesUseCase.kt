package com.example.wastec.domain.usecase

import com.example.wastec.domain.repository.WasteRepository
import com.example.wastec.presentation.model.WasteCategoryUi
import javax.inject.Inject

class GetEducationCategoriesUseCase @Inject constructor(
    private val repository: WasteRepository
) {
    suspend operator fun invoke(): List<WasteCategoryUi> = repository.getEducationCategories()
}