package com.zhichengzhang.artistsearchandroid.data.network.api

import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/{q}")
    suspend fun searchArtists(
        @Path("q") q: String
    ): SearchResponse
}