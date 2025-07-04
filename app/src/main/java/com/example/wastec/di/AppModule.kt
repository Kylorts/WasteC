package com.example.wastec.di

import android.content.Context
import androidx.room.Room
import com.example.wastec.data.datasource.local.db.AppDatabase
import com.example.wastec.data.datasource.local.db.room.HistoryDao
import com.example.wastec.data.datasource.local.pref.SettingsDataStore
import com.example.wastec.data.datasource.remote.ApiService
import com.example.wastec.data.repository.WasteRepositoryImpl
import com.example.wastec.domain.repository.WasteRepository
import com.example.wastec.domain.usecase.ClassifyWasteUseCase
import com.example.wastec.domain.usecase.GetEducationCategoriesUseCase
import com.example.wastec.domain.usecase.GetHistoryUseCase
import com.example.wastec.domain.usecase.GetThemeUseCase
import com.example.wastec.domain.usecase.SaveThemeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWasteRepository(
        @ApplicationContext context: Context,
        historyDao: HistoryDao,
        apiService: ApiService,
        settingsDataStore: SettingsDataStore
    ): WasteRepository {
        return WasteRepositoryImpl(context, historyDao, apiService, settingsDataStore)
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

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.180.7:8000/") //Sesuaikan dengan IP lokal PC/Laptop Anda untuk koneksi dengan FastAPi
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetEducationCategoriesUseCase(repository: WasteRepository): GetEducationCategoriesUseCase {
        return GetEducationCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetThemeUseCase(repository: WasteRepository): GetThemeUseCase {
        return GetThemeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveThemeUseCase(repository: WasteRepository): SaveThemeUseCase {
        return SaveThemeUseCase(repository)
    }

}