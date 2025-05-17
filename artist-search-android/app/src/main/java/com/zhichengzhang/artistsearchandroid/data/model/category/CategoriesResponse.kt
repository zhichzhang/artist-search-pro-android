package com.zhichengzhang.artistsearchandroid.data.model.category

import com.google.gson.annotations.SerializedName

class CategoryResponse (
    val success: Boolean,
    val message: String,
    val data: EmbeddedData
    )

data class EmbeddedData(
    @SerializedName("_embedded")
    val embedded: List<CategoryResult>
)