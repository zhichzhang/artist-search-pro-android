package com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResult

@Composable
fun SearchResultsColumn(
    searchResults: List<SearchResult>,
    onSearchResultsColumnClick: (artistId: String, artistName: String) -> Unit,
    onFavoriteChanged:(action: String) -> Unit){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Color.Transparent
            ))
    {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            items(searchResults){
                searchResult -> SearchResultCard(searchResult, onClick = onSearchResultsColumnClick, onFavoriteChanged = onFavoriteChanged)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchResultCard(){
    val sampleSearchResult1 = SearchResult(
        artistId = "1",
        artistName = "Vincent van Gogh",
        artistThumbnailHref = "/assets/shared/missing_image.png"
    )
    val sampleSearchResult2 = SearchResult(
        artistId = "2",
        artistName = "Vincent van Gogh",
        artistThumbnailHref = "/assets/shared/missing_image.png"
    )

    val searchResults  = listOf(sampleSearchResult1, sampleSearchResult2)
//    SearchResultsColumn(searchResults)
}

