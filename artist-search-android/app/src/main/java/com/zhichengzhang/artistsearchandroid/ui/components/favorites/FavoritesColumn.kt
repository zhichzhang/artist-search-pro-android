package com.zhichengzhang.artistsearchandroid.ui.components.favorites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.zhichengzhang.artistsearchandroid.data.model.user.favoriteList.FavoriteItem
import com.zhichengzhang.artistsearchandroid.ui.components.artworks.ArtworkCard
import com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay.SearchResultCard
import com.zhichengzhang.artistsearchandroid.ui.components.noResults.NoResultsCard

@Composable
fun FavoritesColumn(
    favoriteItems: List<FavoriteItem>,
    onItemClick: (artistId: String, artistName: String) -> Unit){

    if (favoriteItems.isEmpty()){
        NoResultsCard("No favorites")
    } else{
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(favoriteItems) {
                    favoriteItem -> FavoriteCard(favoriteItem, onClick = onItemClick)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFavoritesColumn(){
    val sampleFavoriteItem = FavoriteItem(
        artistId = "12345",
        createdAt = "2024-04-30T12:34:56.000Z",
        artistName = "Vincent van Gogh",
        birthday = "1853",
        deathday = "1890",
        nationality = "Dutch",
        artistThumbnailHref = "https://example.com/images/van_gogh.jpg"
    )

    val sampleFavoriteItem1 = FavoriteItem(
        artistId = "12348",
        createdAt = "2025-05-03T20:40:59.045358",
        artistName = "Vincent",
        birthday = "1853",
        deathday = "1890",
        nationality = "Dutch",
        artistThumbnailHref = "https://example.com/images/van_gogh.jpg"
    )

    val sampleFavorites = listOf(sampleFavoriteItem, sampleFavoriteItem1)

//    FavoritesColumn(sampleFavorites)
}