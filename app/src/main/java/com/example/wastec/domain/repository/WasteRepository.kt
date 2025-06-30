package com.example.wastec.domain.repository

import android.graphics.Bitmap
import com.example.wastec.domain.model.ClassificationResult

interface WasteRepository {
    suspend fun classifyImage(bitmap: Bitmap): ClassificationResult?
}