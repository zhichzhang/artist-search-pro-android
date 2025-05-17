package com.zhichengzhang.artistsearchandroid.ui.components.detailsArtistInformationScreenContent

import androidx.compose.runtime.Composable
import com.zhichengzhang.artistsearchandroid.data.model.artists.artworks.ArtworkResult
import com.zhichengzhang.artistsearchandroid.ui.components.artworks.ArtworksColumn
import com.zhichengzhang.artistsearchandroid.ui.components.noResults.NoResultsCard
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ArtworksContent(artworkResults: List<ArtworkResult>){

    if (artworkResults.isEmpty()){
        NoResultsCard("No Artwork")
    } else{
        ArtworksColumn(artworkResults)
    }
}

