package com.example.wastec.domain.mapper

import com.example.wastec.data.datasource.remote.dto.WasteCategoryDto
import com.example.wastec.presentation.model.WasteCategoryUi

fun WasteCategoryDto.toWasteCategoryUI(): WasteCategoryUi {
    return WasteCategoryUi(
        id = this.id,
        name = this.name,
        description = this.description,
        iconUrl = this.iconUrl,
        examples = this.examples,
        recyclingInfo = this.recyclingInfo
    )
}

fun List<WasteCategoryDto>.toWasteCategoryUIList(): List<WasteCategoryUi> {
    return this.map { it.toWasteCategoryUI() }
}