package com.example.wastec.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.wastec.data.datasource.local.db.entity.HistoryEntity
import com.example.wastec.data.datasource.local.db.room.HistoryDao
import com.example.wastec.data.datasource.local.ml.WasteClassifierHelper
import com.example.wastec.data.datasource.remote.ApiService
import com.example.wastec.domain.mapper.toWasteCategoryUI
import com.example.wastec.domain.mapper.toWasteCategoryUIList
import com.example.wastec.domain.model.ClassificationResult
import com.example.wastec.domain.repository.WasteRepository
import com.example.wastec.presentation.model.WasteCategoryUi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileOutputStream
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class WasteRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val historyDao: HistoryDao,
    private val apiService: ApiService
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

    override suspend fun getEducationCategories(): List<WasteCategoryUi> {
        return try {
            val response = apiService.getAllCategories()
            if (response.isSuccessful) {
                response.body()?.toWasteCategoryUIList() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getEducationCategoryDetail(id: Int): WasteCategoryUi? {
        return try {
            val response = apiService.getCategoryDetail(id)
            if (response.isSuccessful) {
                response.body()?.toWasteCategoryUI()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}