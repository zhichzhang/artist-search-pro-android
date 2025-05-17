package com.zhichengzhang.artistsearchandroid.data.network.api

import com.zhichengzhang.artistsearchandroid.data.model.artists.ArtistResponse
import com.zhichengzhang.artistsearchandroid.data.model.artists.similar.SimilarArtistsResponse
import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistsApiService {
    @GET("artists/{artistId}")
    suspend fun getArtistDetails(
        @Path("artistId") artistId: String
    ): ArtistResponse

    @GET("artists/similar/{similarToArtistId}")
    suspend fun getSimilarArtists(
        @Path("similarToArtistId") artistId: String
    ): SearchResponse
}