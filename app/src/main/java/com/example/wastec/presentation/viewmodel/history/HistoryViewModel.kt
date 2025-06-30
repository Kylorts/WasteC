package com.example.wastec.presentation.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastec.domain.usecase.GetHistoryUseCase
import com.example.wastec.presentation.views.history.HistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {
    private  val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        fetchHistory()
    }

    private fun fetchHistory() {
        viewModelScope.launch {
            getHistoryUseCase()
                .catch { throwable ->
                    _uiState.update { it.copy(error = throwable.message) }
                }
                .collect { historyList ->
                    _uiState.update { it.copy(historyList = historyList) }

                }
        }
    }
}