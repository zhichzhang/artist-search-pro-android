package com.zhichengzhang.artistsearchandroid.ui.components.artworks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhichengzhang.artistsearchandroid.data.model.artists.artworks.ArtworkResult

@Composable
fun ArtworksColumn(artworkResults: List<ArtworkResult>){

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Color.Transparent
            ))
    {

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 33.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            items(artworkResults){
                artwork -> ArtworkCard(artwork)
            }

        }
}
}

@Preview(showBackground = true)
@Composable
fun PreviewArtworksColumn(){
    val sampleArtworkResults = listOf(
        ArtworkResult(artworkId = "1", artworkTitle = "Vague", artworkDate = "1988", artworkThumbnailHref = "missing_image"),
        ArtworkResult(artworkId = "1", artworkTitle = "Vague", artworkDate = "1988", artworkThumbnailHref = "missing_image"),
        ArtworkResult(artworkId = "1", artworkTitle = "Vague", artworkDate = "1988", artworkThumbnailHref = "missing_image")
    )
//    ArtworksColumn(sampleArtworkResults)
}