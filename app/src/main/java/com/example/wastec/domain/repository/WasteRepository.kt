package com.example.wastec.domain.repository

import android.graphics.Bitmap
import com.example.wastec.data.datasource.local.db.entity.HistoryEntity
import com.example.wastec.domain.model.ClassificationResult
import com.example.wastec.domain.model.WasteCategory
import kotlinx.coroutines.flow.Flow

interface WasteRepository {
    suspend fun classifyImage(bitmap: Bitmap): ClassificationResult?

    suspend fun saveHistory(result: ClassificationResult, image: Bitmap)

    fun getHistory(): Flow<List<HistoryEntity>>

    suspend fun getEducationCategories(): List<WasteCategory>

    suspend fun getEducationCategoryDetail(id: Int): WasteCategory?

    fun getThemeSetting(): Flow<Boolean>

    suspend fun saveThemeSetting(isDarkMode: Boolean)
}