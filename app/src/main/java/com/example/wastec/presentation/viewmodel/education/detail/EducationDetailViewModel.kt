package com.example.wastec.presentation.viewmodel.education.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastec.domain.usecase.GetEducationCategoryDetailUseCase
import com.example.wastec.presentation.views.education.detail.EducationDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EducationDetailViewModel @Inject constructor(
    private val getEducationCategoryDetailUseCase: GetEducationCategoryDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(EducationDetailUiState())
    val uiState: StateFlow<EducationDetailUiState> = _uiState.asStateFlow()

    private val categoryId: Int = savedStateHandle.get<Int>("categoryId")!!

    init {
        fetchCategoryDetail()
    }

    private fun fetchCategoryDetail() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val category = getEducationCategoryDetailUseCase(categoryId)
                _uiState.update { it.copy(isLoading = false, category = category, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Gagal memuat detail: ${e.message}") }
            }
        }
    }
}