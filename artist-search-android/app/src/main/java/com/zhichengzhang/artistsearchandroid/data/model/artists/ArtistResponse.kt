package com.zhichengzhang.artistsearchandroid.data.model.artists

data class ArtistResponse (
    val success: Boolean,
    val message: String,
    val data: ArtistResult
)