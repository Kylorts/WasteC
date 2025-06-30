package com.example.wastec.domain.usecase

import com.example.wastec.data.datasource.local.db.entity.HistoryEntity
import com.example.wastec.domain.repository.WasteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val wasteRepository: WasteRepository
) {
    operator fun invoke(): Flow<List<HistoryEntity>> {
        return wasteRepository.getHistory()
    }
}