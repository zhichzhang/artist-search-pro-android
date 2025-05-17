package com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResult
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.zhichengzhang.artistsearchandroid.session.UserSession
import com.zhichengzhang.artistsearchandroid.viewmodel.screens.SearchResultCardViewModel

@Composable
fun SearchResultCard(
    searchResult: SearchResult,
    onClick: (artistId: String, artistName: String) -> Unit,
    onFavoriteChanged: (action: String) -> Unit,
    searchResultCardViewModel: SearchResultCardViewModel = SearchResultCardViewModel()
){
    val isLoginState = UserSession.isLogin
    val isLogin by rememberUpdatedState(isLoginState.value)
    val userInfoState = UserSession.userInfo
    val userInfo by rememberUpdatedState(userInfoState.value)
    val favoritesState = UserSession.favorites
    val favorites by rememberUpdatedState(favoritesState.value)
    val isFavorite by remember(favorites, searchResult.artistId) {
        derivedStateOf {
            favorites.any { it.artistId == searchResult.artistId }
        }
    }

    val isNight = isSystemInDarkTheme()
    val artistId = searchResult.artistId

    val cardBackgroundColor = if (!isNight) colorResource(R.color.card_background_day) else colorResource(R.color.card_background_night)
    val cardTitleBackgroundColor = if (!isNight) colorResource(R.color.card_title_background_day) else colorResource(R.color.card_title_background_night)
    val cardTitleText = if (!isNight) colorResource(R.color.card_title_text_day) else colorResource(R.color.card_title_text_night)
    val cardStarCircleBackground = if (!isNight) colorResource(R.color.card_title_background_day) else colorResource(R.color.card_title_background_night)

    val defaultBackgroundImage = painterResource(id = R.drawable.artsy_logo)
    val isMissingImage = searchResult.artistThumbnailHref.contains("missing_image")
    val imageUrl =  if (isMissingImage) null else searchResult.artistThumbnailHref
    val contentScale = if (isMissingImage) ContentScale.Inside else ContentScale.Crop
    var favoriteStar = if (isFavorite) painterResource(id = R.drawable.star_icon) else painterResource(id = R.drawable.star_border_icon)


    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onClick(searchResult.artistId, searchResult.artistName) },
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Search Result Card",
                placeholder = defaultBackgroundImage,
                error = defaultBackgroundImage,
                fallback = defaultBackgroundImage,
                contentScale = contentScale,
                modifier = Modifier.matchParentSize()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = cardTitleBackgroundColor)
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = searchResult.artistName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = cardTitleText,
                    modifier = Modifier.weight(1f).padding(3.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.right_arrow_icon),
                    contentDescription = "Get the artist's Details",
                    modifier = Modifier.padding(end = 4.dp),
                    tint = cardTitleText
                )
            }

            if (isLogin){
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(36.dp)
                        .background(
                            color = cardStarCircleBackground,
                            shape = CircleShape
                        )
                        .align(Alignment.TopEnd)
                        .clickable {
                            Log.e("SearchResultCard", "Favorite ${artistId}")
                            /* TODO onFavoriteClick(searchResult.artistId) */
                            userInfo?.let { searchResultCardViewModel.toggleFavorite(it.userId, artistId) }
                            onFavoriteChanged(if (isFavorite) "Remove" else "Add")
                        }
                ) {
                    Icon(
                        painter = favoriteStar,
                        contentDescription = "Favorite Star",
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        tint = cardTitleText
                    )
                }
            }
        }
    }
}

fun goToArtistDetailsScreen(artistId: String){
    Log.e("SearchResultCard", "Click ${artistId}")
    /* TODO get the artist's details and go to the artist Details Screen*/
}

@SuppressLint("SuspiciousIndentation")
@Preview(showBackground = true)
@Composable
fun SearchResultCardPreview() {
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

//    Column {
//        SearchResultCard(searchResult = sampleSearchResult1, onClick = { artistId -> goToArtistDetailsScreen(sampleSearchResult1.artistId) })
//        SearchResultCard(searchResult = sampleSearchResult2, onClick = { artistId -> goToArtistDetailsScreen(sampleSearchResult1.artistId) })
//    }

}