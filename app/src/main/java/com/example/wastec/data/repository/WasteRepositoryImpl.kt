package com.example.wastec.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.wastec.data.datasource.local.ml.WasteClassifierHelper
import com.example.wastec.domain.model.ClassificationResult
import com.example.wastec.domain.repository.WasteRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class WasteRepositoryImpl(
    private val context: Context
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
}