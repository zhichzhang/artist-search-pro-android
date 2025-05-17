package com.zhichengzhang.artistsearchandroid.data.model.artists.artworks

import com.google.gson.annotations.SerializedName

data class ArtworksResponse (
    val success: Boolean,
    val message: String,
    val data: EmbeddedData
)

data class EmbeddedData(
    @SerializedName("_embedded")
    val embedded: List<ArtworkResult>
)