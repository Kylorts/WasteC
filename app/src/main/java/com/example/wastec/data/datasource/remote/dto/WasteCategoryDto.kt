package com.example.wastec.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class WasteCategoryDto (
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon_url")
    val iconUrl: String,

    @SerializedName("examples")
    val examples: List<String>,

    @SerializedName("recycling_info")
    val recyclingInfo: String
)
