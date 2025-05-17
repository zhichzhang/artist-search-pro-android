package com.zhichengzhang.artistsearchandroid.ui.components.favorites

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.user.favoriteList.FavoriteItem
import com.zhichengzhang.artistsearchandroid.data.model.user.favorites.FavoritesResult
import com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay.SearchResultCard
import com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay.goToArtistDetailsScreen
import kotlinx.coroutines.delay

fun parseDate(dateString: String): Long {
    return try {
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.getDefault())
        formatter.timeZone = java.util.TimeZone.getTimeZone("UTC")
        formatter.parse(dateString)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 30
    val years = days / 365

    return when {
        seconds < 60 -> "$seconds second${if (seconds == 1L) "" else "s"} ago"
        minutes < 60 -> "$minutes minute${if (minutes == 1L) "" else "s"} ago"
        hours < 24 -> "$hours hour${if (hours == 1L) "" else "s"} ago"
        days < 30 -> "$days day${if (days == 1L) "" else "s"} ago"
        months < 12 -> "$months month${if (months == 1L) "" else "s"} ago"
        else -> "$years year${if (years == 1L) "" else "s"} ago"
    }
}

@Composable
fun FavoriteCard(
    favoriteItem: FavoriteItem,
    onClick: (artistId: String, artistName: String) -> Unit){
    val isNight = isSystemInDarkTheme()

    val cardBackgroundColor = Color.Transparent
    val cardTextColor = if (!isNight) colorResource(R.color.favorite_card_text_day) else colorResource(R.color.favorite_card_text_night)

    val createdAt = remember { parseDate(favoriteItem.createdAt) }
    val timeCounter = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val currentTime = System.currentTimeMillis()
            val diffInSeconds = (currentTime - createdAt) / 1000
            timeCounter.value = formatTime(diffInSeconds)
            delay(1000L)
        }
    }

    Card(
        modifier = Modifier
            .padding(top = 2.dp, bottom = 2.dp)
            .fillMaxWidth()
            .clickable { onClick(favoriteItem.artistId, favoriteItem.artistName) },
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(0)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = favoriteItem.artistName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = cardTextColor,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = listOfNotNull(
                        favoriteItem.nationality.takeIf { it.isNotEmpty() },
                        favoriteItem.birthday.takeIf { it.isNotEmpty() }
                    ).joinToString(", "),
                    color = cardTextColor,
                    fontSize = 12.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = timeCounter.value,
                    color = cardTextColor,
                    modifier = Modifier.padding(end = 4.dp),
                    fontSize = 12.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.right_arrow_icon),
                    contentDescription = "Get artist details",
                    tint = cardTextColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteCard(){
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
//
//    Column {
//        FavoriteCard (favoriteItem = sampleFavoriteItem, onClick = { artistId -> goToArtistDetailsScreen(artistId) })
//        FavoriteCard (favoriteItem = sampleFavoriteItem1, onClick = { artistId -> goToArtistDetailsScreen(artistId) })
//    }
}