package com.example.wastec.presentation.views.history

import com.example.wastec.data.datasource.local.db.entity.HistoryEntity

data class HistoryUiState (
    val historyList: List<HistoryEntity> = emptyList(),
    val error: String? = null
)
