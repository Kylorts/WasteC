package com.example.wastec.presentation.views.scan

import android.net.Uri
import com.example.wastec.domain.model.ClassificationResult

data class ScanUiState(
    val isLoading: Boolean = false,
    val result: ClassificationResult? = null,
    val error: String? = null,
    val selectedImageUri: Uri? = null
)