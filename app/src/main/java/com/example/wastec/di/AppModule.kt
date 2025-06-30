package com.example.wastec.di

import android.content.Context
import com.example.wastec.data.repository.WasteRepositoryImpl
import com.example.wastec.domain.repository.WasteRepository
import com.example.wastec.domain.usecase.ClassifyWasteUseCase
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
    fun provideWasteRepository(@ApplicationContext context: Context): WasteRepository {
        return WasteRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideClassifyWasteUseCase(repository: WasteRepository): ClassifyWasteUseCase {
        return ClassifyWasteUseCase(repository)
    }
}