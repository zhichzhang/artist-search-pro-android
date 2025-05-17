package com.zhichengzhang.artistsearchandroid.ui.components.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.category.CategoryResult
import kotlinx.coroutines.launch
import androidx.compose.material3.Button as Button

@Composable
fun CategoriesRow(categories: List<CategoryResult>) {
    val isNight = isSystemInDarkTheme()

    val listState = rememberLazyListState()
    var currentIndex = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    val arrowForward = painterResource(id = R.drawable.left_arrow_icon)
    val arrowBackward = painterResource(id = R.drawable.right_arrow_icon)

    val categoriesRowBackgroundColor = Color.Transparent
    val iconTint = if (!isNight) colorResource(R.color.icon_day) else colorResource(R.color.icon_night)

    Box(
        modifier = Modifier.width(265.dp).height(400.dp).clip(RoundedCornerShape(16.dp)).background(categoriesRowBackgroundColor)) {

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 33.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            userScrollEnabled = false
        ) {
            itemsIndexed(categories) { _, category ->
                CategoryCard(category)
            }
        }

        IconButton(
            onClick = {
                if (currentIndex.value > 0) {
                    currentIndex.value--
                    coroutineScope.launch {
                        listState.animateScrollToItem(currentIndex.value)
                    }
                }
            },
            enabled = currentIndex.value > 0,
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            Icon(arrowForward, contentDescription = "Previous", tint = iconTint)
        }

        IconButton(
            onClick = {
                if (currentIndex.value < categories.size - 1) {
                    currentIndex.value++
                    coroutineScope.launch {
                        listState.animateScrollToItem(currentIndex.value)
                    }
                }
            },
            enabled = currentIndex.value < categories.size - 1,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
             Icon(painter = arrowBackward, contentDescription = "Next", tint = iconTint)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoriesRow(){
    val sampleCategoryResults = listOf(
        CategoryResult(
            categoryName = "Impressionism",
            categoryDescription = "A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. A 19th-century art movement characterized by small, thin brush strokes and emphasis on light. ",
            categoryThumbnailHref = "missing_image"
        ),
        CategoryResult(
            categoryName = "Cubism",
            categoryDescription = "An early 20th-century avant-garde movement that revolutionized European painting and sculpture.",
            categoryThumbnailHref = "missing_image"
        ),
        CategoryResult(
            categoryName = "Surrealism",
            categoryDescription = "A cultural movement known for visual artworks and writings with dream-like and irrational elements.",
            categoryThumbnailHref = "missing_image"
        )
    )

    val isNight = isSystemInDarkTheme()
    val buttonBackgroundColor = if (!isNight) colorResource(R.color.button_background_day) else colorResource(R.color.button_background_night)
    val buttonTextColor = if (!isNight) colorResource(R.color.button_text_day) else colorResource(R.color.button_text_night)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier
            .width(300.dp)
            .height(550.dp).border(
                width = 1.dp,
                color = Color.Red,
                shape = RoundedCornerShape(12.dp)
            )
            .clipToBounds()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Category",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.Start)
            )

            CategoriesRow(sampleCategoryResults)

            Button(
                modifier = Modifier.padding(top = 16.dp, end = 8.dp).width(80.dp).height(40.dp).background(Color.Transparent).align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBackgroundColor,
                    contentColor = buttonTextColor
                ),
                onClick = { /* TODO Close action */ }) {
                Text(text = "Close", color = buttonTextColor, fontSize = 12.sp)
            }
        }
    }

}

