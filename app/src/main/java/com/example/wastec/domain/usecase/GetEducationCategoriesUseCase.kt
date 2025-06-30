package com.example.wastec.domain.usecase

import com.example.wastec.domain.model.WasteCategory
import com.example.wastec.domain.repository.WasteRepository
import javax.inject.Inject

class GetEducationCategoriesUseCase @Inject constructor(
    private val repository: WasteRepository
) {
    suspend operator fun invoke(): List<WasteCategory> = repository.getEducationCategories()
}