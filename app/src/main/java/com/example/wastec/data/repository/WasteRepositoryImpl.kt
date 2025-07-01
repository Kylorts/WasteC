package com.example.wastec.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.wastec.data.datasource.local.db.entity.HistoryEntity
import com.example.wastec.data.datasource.local.db.room.HistoryDao
import com.example.wastec.data.datasource.local.ml.WasteClassifierHelper
import com.example.wastec.data.datasource.local.pref.SettingsDataStore
import com.example.wastec.data.datasource.remote.ApiService
import com.example.wastec.data.mapper.toDomain
import com.example.wastec.data.mapper.toDomainList
import com.example.wastec.domain.model.ClassificationResult
import com.example.wastec.domain.model.WasteCategory
import com.example.wastec.domain.repository.WasteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class WasteRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val historyDao: HistoryDao,
    private val apiService: ApiService,
    private val settingsDataStore: SettingsDataStore
) : WasteRepository {

    override suspend fun classifyImage(bitmap: Bitmap): ClassificationResult? =
        suspendCancellableCoroutine { continuation ->
            val wasteClassifierHelper = WasteClassifierHelper(
                context = context,
                classifierListener = object : WasteClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        continuation.resume(null)
                    }

                    override fun onResults(result: ClassificationResult?) {
                        continuation.resume(result)
                    }
                }
            )
            wasteClassifierHelper.classify(bitmap)
        }

    override suspend fun saveHistory(result: ClassificationResult, image: Bitmap) {
        val imagePath = saveBitmapToFile(image)

        val historyEntity = HistoryEntity(
            label = result.label,
            confidence = result.confidence,
            imagePath = imagePath
        )
        historyDao.insertHistory(historyEntity)
    }

    private fun saveBitmapToFile(bitmap: Bitmap): String {
        val fileName = "history_${System.currentTimeMillis()}.jpg"
        val directory = context.getDir("history_images", Context.MODE_PRIVATE)
        val file = File(directory, fileName)

        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, it)
        }
        return file.absolutePath
    }

    override fun getHistory(): Flow<List<HistoryEntity>> {
        return historyDao.getAllHistory()
    }

    override suspend fun getEducationCategories(): List<WasteCategory> {
        val response = apiService.getAllCategories()
        if (response.isSuccessful) {
            return response.body()?.toDomainList() ?: emptyList()
        } else {
            throw IOException("Error: ${response.code()} - ${response.message()}")
        }
    }

    override suspend fun getEducationCategoryDetail(id: Int): WasteCategory? {
        val response = apiService.getCategoryDetail(id)
        if (response.isSuccessful) {
                return response.body()?.toDomain()
        } else {
            throw IOException("Error: ${response.code()} - ${response.message()}")
        }
    }

    override fun getThemeSetting(): Flow<Boolean> {
        return settingsDataStore.getThemeSetting
    }

    override suspend fun saveThemeSetting(isDarkMode: Boolean) {
        settingsDataStore.saveThemeSetting(isDarkMode)
    }
}