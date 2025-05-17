package com.zhichengzhang.artistsearchandroid.ui.components.noResults

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhichengzhang.artistsearchandroid.R


@Composable
fun NoResultsCard(slogan: String) {
    val isNight = isSystemInDarkTheme()

    val cardBackgroundColor =
        if (!isNight) colorResource(R.color.top_bar_day) else colorResource(R.color.top_bar_night)
    val cardTextColor = if (!isNight) colorResource(R.color.card_title_text_day) else colorResource(R.color.card_title_text_night)

    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = cardBackgroundColor)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = slogan, color = cardTextColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoResultsCard(){
    val noArtworksSlogan = "No Artworks"
    val noFavoritesSlogan = "No Favorites"
    val noArtistsSlogan = "No Artists"

    Column {
        NoResultsCard(noArtworksSlogan)
        NoResultsCard(noFavoritesSlogan)
        NoResultsCard(noArtistsSlogan)
    }
}