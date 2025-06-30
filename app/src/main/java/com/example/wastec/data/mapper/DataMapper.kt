package com.example.wastec.data.mapper

import com.example.wastec.data.datasource.remote.dto.WasteCategoryDto
import com.example.wastec.domain.model.WasteCategory

fun WasteCategoryDto.toDomain(): WasteCategory {
    return WasteCategory(
        id = this.id,
        name = this.name,
        description = this.description,
        iconUrl = this.iconUrl,
        examples = this.examples,
        recyclingInfo = this.recyclingInfo
    )
}

fun List<WasteCategoryDto>.toDomainList(): List<WasteCategory> {
    return this.map { it.toDomain() }
}