package com.example.wastec.domain.model

data class WasteCategory(
    val id: Int,
    val name: String,
    val description: String,
    val iconUrl: String,
    val examples: List<String>,
    val recyclingInfo: String
)