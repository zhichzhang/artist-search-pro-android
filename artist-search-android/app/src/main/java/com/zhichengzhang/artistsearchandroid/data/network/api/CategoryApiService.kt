package com.zhichengzhang.artistsearchandroid.data.network.api

import com.zhichengzhang.artistsearchandroid.data.model.category.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiService {
    @GET("category/{artworkId}")
    suspend fun getArtworkCategories(
        @Path("artworkId") artworkId: String
    ): CategoryResponse
}