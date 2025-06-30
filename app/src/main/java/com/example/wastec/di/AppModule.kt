package com.example.wastec.di

import android.content.Context
import androidx.room.Room
import com.example.wastec.data.datasource.local.db.AppDatabase
import com.example.wastec.data.datasource.local.db.room.HistoryDao
import com.example.wastec.data.repository.WasteRepositoryImpl
import com.example.wastec.domain.repository.WasteRepository
import com.example.wastec.domain.usecase.ClassifyWasteUseCase
import com.example.wastec.domain.usecase.GetHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWasteRepository(@ApplicationContext context: Context, historyDao: HistoryDao): WasteRepository {
        return WasteRepositoryImpl(context, historyDao)
    }

    @Provides
    @Singleton
    fun provideClassifyWasteUseCase(repository: WasteRepository): ClassifyWasteUseCase {
        return ClassifyWasteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "wastec_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }

    @Provides
    @Singleton
    fun provideGetHistoryUseCase(repository: WasteRepository): GetHistoryUseCase {
        return GetHistoryUseCase(repository)
    }
}