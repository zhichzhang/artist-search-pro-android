package com.zhichengzhang.artistsearchandroid.ui.components.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.category.CategoryResult
import com.zhichengzhang.artistsearchandroid.ui.components.loading.LoadingCircle

@Composable
fun CategoryDialog(
    categoryResults: List<CategoryResult>,
    isLoading: Boolean,
    onDismiss: () -> Unit
){
    Dialog(onDismissRequest = { onDismiss() }) {
    val isNight = isSystemInDarkTheme()

    val buttonBackgroundColor = if (!isNight) colorResource(R.color.button_background_day) else colorResource(R.color.button_background_night)
    val buttonTextColor = if (!isNight) colorResource(R.color.button_text_day) else colorResource(R.color.button_text_night)
    val dialogTextColor = if (!isNight) colorResource(R.color.category_dialog_text_day) else colorResource(R.color.category_dialog_text_night)
    val dialogBackgroundColor = if (!isNight) colorResource(R.color.category_dialog_background_day) else colorResource(R.color.category_dialog_background_night)


    Surface(
        shape = RoundedCornerShape(16.dp),
        color = dialogBackgroundColor,
        tonalElevation = 8.dp,
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .clipToBounds()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Categories",
                fontSize = 20.sp,
                modifier = Modifier.padding(start= 10.dp, top = 12.dp, bottom = 20.dp).align(Alignment.Start),
                color = dialogTextColor
            )

            if (isLoading) {
                LoadingCircle()
            } else{
                if (categoryResults.isEmpty()){
                    Text(text="No categories available", color = dialogTextColor, modifier = Modifier.padding(16.dp))
                } else{
                    CategoriesRow(categoryResults)
                }

            }


            Button(
                modifier = Modifier.padding(top = 16.dp, end = 8.dp).width(80.dp).height(40.dp).background(
                    Color.Transparent).align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBackgroundColor,
                    contentColor = buttonTextColor
                ),
                onClick = { onDismiss() }) {
                Text(text = "Close", color = buttonTextColor, fontSize = 12.sp)
            }
        }
    }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryDialog(){
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

//    CategoryDialog(sampleCategoryResults)
}
