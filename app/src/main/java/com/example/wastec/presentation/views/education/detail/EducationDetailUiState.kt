package com.example.wastec.presentation.views.education.detail

import com.example.wastec.presentation.model.WasteCategoryUi

data class EducationDetailUiState(
    val isLoading: Boolean = false,
    val category: WasteCategoryUi? = null,
    val error: String? = null
)