package com.zhichengzhang.artistsearchandroid.ui.components.categories

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.category.CategoryResult

fun descriptionClean(description: String): String {
    val noLinks = description.replace(Regex("""\[(.*?)]\(.*?\)"""), "$1")
    val noRefsNoLinks = noLinks.replace(Regex("""_(.*?)_"""), "$1")
    return noRefsNoLinks
}

@Composable
fun CategoryCard(categoryResult: CategoryResult){
    val isNight = isSystemInDarkTheme()

    val scrollState = rememberScrollState()

    val cardBackgroundColor = if (!isNight) colorResource(R.color.category_card_background_day) else colorResource(R.color.category_card_background_night)
    val cardTextColor = if (!isNight) colorResource(R.color.card_title_text_day) else colorResource(R.color.card_title_text_night)

    val isMissingImage = categoryResult.categoryThumbnailHref.contains("missing_image")
    val imageUrl =  if (isMissingImage) null else categoryResult.categoryThumbnailHref

    val defaultBackgroundImage = painterResource(id = R.drawable.artsy_logo)

    val contentScale = ContentScale.Crop

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .height(400.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Category Card",
                placeholder = defaultBackgroundImage,
                error = defaultBackgroundImage,
                fallback = defaultBackgroundImage,
                contentScale = contentScale,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = categoryResult.categoryName,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = cardTextColor,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = descriptionClean(categoryResult.categoryDescription),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = cardTextColor,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryCard() {
    val sampleCategoryResults = listOf(
        CategoryResult(
            categoryName = "Impressionism",
            categoryDescription = "A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. ",
            categoryThumbnailHref = "issing_image"
        ),
        CategoryResult(
            categoryName = "Cubism",
            categoryDescription = "An early 20th-century avant-garde movement that revolutionized European painting and sculpture.",
            categoryThumbnailHref = "issing_image"
        ),
        CategoryResult(
            categoryName = "Surrealism",
            categoryDescription = "A cultural movement known for visual artworks and writings with dream-like and irrational elements.",
            categoryThumbnailHref = "issing_image"
        )
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(sampleCategoryResults) { category ->
            CategoryCard(category)
        }
    }
}