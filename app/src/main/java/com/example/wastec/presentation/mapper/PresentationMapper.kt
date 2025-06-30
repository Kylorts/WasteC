package com.example.wastec.presentation.mapper

import com.example.wastec.domain.model.WasteCategory
import com.example.wastec.presentation.model.WasteCategoryUi

fun WasteCategory.toUi(): WasteCategoryUi {
    return WasteCategoryUi(
        id = this.id,
        name = this.name,
        description = this.description,
        iconUrl = this.iconUrl,
        examples = this.examples,
        recyclingInfo = this.recyclingInfo
    )
}

fun List<WasteCategory>.toUiList(): List<WasteCategoryUi> {
    return this.map { it.toUi() }
}