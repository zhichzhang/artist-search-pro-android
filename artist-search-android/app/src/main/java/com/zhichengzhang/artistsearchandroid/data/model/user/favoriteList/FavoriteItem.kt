package com.zhichengzhang.artistsearchandroid.data.model.user.favoriteList

data class FavoriteItem (
    val artistId: String,
    val createdAt: String,
    val artistName: String,
    val birthday: String,
    val deathday: String,
    val nationality: String,
    val artistThumbnailHref: String
)