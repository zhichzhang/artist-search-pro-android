package com.zhichengzhang.artistsearchandroid.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val message: String,
    val success: Boolean,
    val data: EmbeddedData
)

data class EmbeddedData(
    @SerializedName("_embedded")
    val embedded: List<SearchResult>
)
