package com.zhichengzhang.artistsearchandroid.data.network.api

import com.zhichengzhang.artistsearchandroid.data.model.user.deletes.DeletesResult
import com.zhichengzhang.artistsearchandroid.data.model.user.favoriteList.FavoriteItem
import com.zhichengzhang.artistsearchandroid.data.model.user.favoriteList.FavoriteListResponse
import com.zhichengzhang.artistsearchandroid.data.model.user.favorites.FavoritesResult
import com.zhichengzhang.artistsearchandroid.session.UserSession
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("user/deletes/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: String
    ): DeletesResult

    @GET("user/favorites/{userId}/{artistId}")
    suspend fun toggleFavorite(
        @Path("userId") userId: String,
        @Path("artistId") artistId: String
    ): FavoritesResult

    @GET("user/favorite-list/{userId}")
    suspend fun getFavoriteList(
        @Path("userId") userId: String
    ): FavoriteListResponse
}