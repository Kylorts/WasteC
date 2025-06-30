package com.example.wastec.presentation.viewmodel.education.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastec.domain.usecase.GetEducationCategoriesUseCase
import com.example.wastec.presentation.mapper.toUiList
import com.example.wastec.presentation.views.education.list.EducationListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EducationViewModel @Inject constructor(
    private val getEducationCategoriesUseCase: GetEducationCategoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(EducationListUiState())
    val uiState: StateFlow<EducationListUiState> = _uiState.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val categoriesDomain = getEducationCategoriesUseCase()
                val categoriesUi = categoriesDomain.toUiList()
                _uiState.update { it.copy(isLoading = false, categories = categoriesUi, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Gagal memuat data: ${e.message}") }
            }
        }
    }
}