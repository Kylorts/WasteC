package com.example.wastec.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastec.domain.usecase.ClassifyWasteUseCase
import com.example.wastec.presentation.views.scan.ScanUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor (
    private val classifyWasteUseCase: ClassifyWasteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScanUiState())

    val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

    fun onImageSelected(uri: Uri) {
        _uiState.update {
            it.copy(selectedImageUri = uri, result = null, error = null)
        }
    }

    fun classifyImage(bitmap: Bitmap) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                val result = classifyWasteUseCase(bitmap)
                _uiState.update { currentState ->
                    currentState.copy(isLoading = false, result = result, error = null)
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(isLoading = false, result = null, error = e.message)
                }
            }
        }
    }
}