package com.example.wastec.data.datasource.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity (
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "confidence") val confidence: Float,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "image_path") val imagePath: String
)
