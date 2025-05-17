package com.zhichengzhang.artistsearchandroid.ui.components.detailsArtistInformationScreenContent

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.artists.ArtistResult
import kotlinx.coroutines.flow.StateFlow


fun detailsSubTitlesClean(nationality: String, birthday: String, deathday: String): String{
    val parts = mutableListOf<String>()

    if (nationality.isNotBlank()) {
        parts.add(nationality)
    }

    val datePart = when {
        birthday.isNotBlank() && deathday.isNotBlank() -> "$birthday - $deathday"
        birthday.isNotBlank() -> birthday
        deathday.isNotBlank() -> deathday
        else -> ""
    }

    if (datePart.isNotBlank()) {
        parts.add(datePart)
    }

    return parts.joinToString(", ")
}

fun biographyClean(biography: String): List<String>{
    return biography.replace("\u0096", "–").split("\n")
}

@Composable
fun DetailsContent(artistResult: ArtistResult?){
    val isNight = isSystemInDarkTheme()
    val textColor = if (!isNight) colorResource(R.color.text_day) else colorResource(R.color.text_night)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        if (artistResult != null) {
            Text(
                text = artistResult.name,
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = detailsSubTitlesClean(artistResult.nationality, artistResult.birthday, artistResult.deathday),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            biographyClean(artistResult.biography).forEach { paragraph ->
                if (paragraph.isNotBlank()) {
                    Text(
                        text = paragraph.trim(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            color = textColor,
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsContentPreview(){
    val biography = "Claude Monet, born Oscar Claude Monet (14 November 1840 – 5 December 1926), was a founder of French impressionist painting, and the most consistent and prolific practitioner of the movement's philosophy of expressing one's perceptions before nature, especially as applied to plein-air landscape painting. The term Impressionism is derived from the title of his painting *Impression, Sunrise* (Impression, soleil levant).\nClaude Monet, born Oscar Claude Monet (14 November 1840 – 5 December 1926), was a founder of French impressionist painting, and the most consistent and prolific practitioner of the movement's philosophy of expressing one's perceptions before nature, especially as applied to plein-air landscape painting. The term Impressionism is derived from the title of his painting *Impression, Sunrise* (Impression, soleil levant).\n"
    val artistName = "Claude Monet"
    val birthday = "1840"
    val deathday = "1926"
    val nationality = "French"
    val artistId = "4d8b92774eb68a1b2c000134"

    val sampleArtistResult = ArtistResult(artistName, birthday, deathday, nationality, biography)

    DetailsContent(sampleArtistResult)
}