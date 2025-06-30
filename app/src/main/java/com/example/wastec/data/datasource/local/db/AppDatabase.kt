package com.example.wastec.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wastec.data.datasource.local.db.entity.HistoryEntity
import com.example.wastec.data.datasource.local.db.room.HistoryDao

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}