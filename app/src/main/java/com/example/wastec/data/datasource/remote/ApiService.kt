package com.example.wastec.data.datasource.remote

import com.example.wastec.data.datasource.remote.dto.WasteCategoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("waste-categories")
    suspend fun getAllCategories(): Response<List<WasteCategoryDto>>

    @GET("waste-categories/{category_id}")
    suspend fun getCategoryDetail(
        @Path("category_id") categoryId: Int
    ): Response<WasteCategoryDto>
}