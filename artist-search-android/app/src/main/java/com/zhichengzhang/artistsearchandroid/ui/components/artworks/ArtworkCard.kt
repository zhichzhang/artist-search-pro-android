package com.zhichengzhang.artistsearchandroid.ui.components.artworks

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.artists.artworks.ArtworkResult
import com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay.goToArtistDetailsScreen
import com.zhichengzhang.artistsearchandroid.ui.components.categories.CategoryDialog
import com.zhichengzhang.artistsearchandroid.ui.components.loading.LoadingCircle
import com.zhichengzhang.artistsearchandroid.viewmodel.components.ArtworkCardViewModel

@Composable
fun ArtworkCard(artworkResult: ArtworkResult, artworkCardViewModel: ArtworkCardViewModel = viewModel()){

    val isLoading by artworkCardViewModel.isLoading.collectAsState()
    val categories by artworkCardViewModel.categories.collectAsState()
    val isNight = isSystemInDarkTheme()

    var showCategoryDialog = remember { mutableStateOf(false) }

    val buttonBackgroundColor = if (!isNight) colorResource(R.color.button_background_day) else colorResource(R.color.button_background_night)
    val buttonTextColor = if (!isNight) colorResource(R.color.button_text_day) else colorResource(R.color.button_text_night)
    val artworkCardTextColor = if (!isNight) colorResource(R.color.artwork_card_text_day) else colorResource(R.color.artwork_card_text_night)
    val artworkCardBackgroundColor = if (!isNight) colorResource(R.color.artwork_card_background_day) else colorResource(
        R.color.artwork_card_background_night)

    val artworkCardTitle = "${artworkResult.artworkTitle}, ${artworkResult.artworkDate}"
    val defaultBackgroundImage = painterResource(id = R.drawable.artsy_logo)
    val isMissingImage = artworkResult.artworkThumbnailHref.contains("missing_image")
    val imageUrl =  if (isMissingImage) null else artworkResult.artworkThumbnailHref
    val contentScale = ContentScale.FillWidth

    LaunchedEffect(showCategoryDialog.value) {
        if (showCategoryDialog.value) {
            artworkCardViewModel.fetchCategories(artworkResult.artworkId)
        }
    }

    if (showCategoryDialog.value) {
            CategoryDialog(
                categoryResults = categories,
                isLoading = isLoading,
                onDismiss = { showCategoryDialog.value = false }
            )
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = artworkCardBackgroundColor),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Search Result Card",
                placeholder = defaultBackgroundImage,
                error = defaultBackgroundImage,
                fallback = defaultBackgroundImage,
                contentScale = contentScale,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = artworkCardTitle,
                modifier = Modifier.padding(8.dp),
                color = artworkCardTextColor,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp).height(40.dp)
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBackgroundColor,
                    contentColor = buttonTextColor
                ),
                onClick = {
                    showCategoryDialog.value = true
                    artworkCardViewModel.fetchCategories(artworkResult.artworkId)
                }
            ) {
                Text(text = "View Categories", color = buttonTextColor)
            }
        }
    }

}


//fun emitCategoriesDialog(artworkId: String){
//    Log.e("ArtworkCard", "Click ${artworkId}")
//    /* TODO get the artist's details and go to the artist Details Screen*/
//}

@Preview(showBackground = true)
@Composable
fun PreviewArtworkCard(){
    val sampleArtworkResult = ArtworkResult(artworkId = "1", artworkTitle = "Vague", artworkDate = "1988", artworkThumbnailHref = "missing_image")

    Column(){
//        ArtworkCard(sampleArtworkResult, onClick = { artworkId ->  emitCategoriesDialog(artworkId)})
    }
}