package com.example.wastec.presentation.views.education.list

import com.example.wastec.presentation.model.WasteCategoryUi

data class EducationListUiState(
    val isLoading: Boolean = false,
    val categories: List<WasteCategoryUi> = emptyList(),
    val error: String? = null
)