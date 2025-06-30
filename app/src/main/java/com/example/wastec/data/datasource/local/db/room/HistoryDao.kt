package com.example.wastec.data.datasource.local.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wastec.data.datasource.local.db.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history_table ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<HistoryEntity>>
}