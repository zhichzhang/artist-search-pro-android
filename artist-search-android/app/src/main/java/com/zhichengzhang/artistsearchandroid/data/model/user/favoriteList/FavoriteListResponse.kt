package com.zhichengzhang.artistsearchandroid.data.model.user.favoriteList

import com.google.gson.annotations.SerializedName

data class FavoriteListResponse(
    val message: String,
    val success: Boolean,
    val data: EmbeddedData
)

data class EmbeddedData(
    @SerializedName("_embedded")
    val embedded: List<FavoriteItem>
)