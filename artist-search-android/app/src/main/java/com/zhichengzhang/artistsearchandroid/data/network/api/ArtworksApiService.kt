package com.zhichengzhang.artistsearchandroid.data.network.api

import com.zhichengzhang.artistsearchandroid.data.model.artists.artworks.ArtworksResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtworksApiService {
    @GET("artworks/{artistId}")
    suspend fun getArtworksByArtist(
        @Path("artistId") artistId: String
    ): ArtworksResponse
}