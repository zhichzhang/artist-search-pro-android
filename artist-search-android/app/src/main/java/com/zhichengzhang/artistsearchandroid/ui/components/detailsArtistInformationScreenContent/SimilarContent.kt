package com.zhichengzhang.artistsearchandroid.ui.components.detailsArtistInformationScreenContent

import androidx.compose.runtime.Composable
import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResult
import com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay.SearchResultsColumn
import com.zhichengzhang.artistsearchandroid.ui.components.noResults.NoResultsCard

@Composable
fun SimilarContent(
    similarArtists: List<SearchResult>,
    onSimilarContentClick: (artistId: String, artistName: String) -> Unit,
    onFavoriteChanged: (action: String) -> Unit){
    if (similarArtists.isEmpty()){
        NoResultsCard("No Similar Artists")
    } else {
        SearchResultsColumn(similarArtists, onSearchResultsColumnClick = onSimilarContentClick, onFavoriteChanged = onFavoriteChanged)
    }

}