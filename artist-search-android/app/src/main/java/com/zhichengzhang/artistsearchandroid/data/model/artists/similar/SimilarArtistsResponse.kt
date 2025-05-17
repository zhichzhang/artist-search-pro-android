package com.zhichengzhang.artistsearchandroid.data.model.artists.similar

import com.google.gson.annotations.SerializedName

data class SimilarArtistsResponse (
    val success: Boolean,
    val message: String,
    val data: EmbeddedData
    )

data class EmbeddedData (
    @SerializedName("_embedded")
    val embedded: List<SimilarArtistResult>
)