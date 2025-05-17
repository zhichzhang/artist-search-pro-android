package com.zhichengzhang.artistsearchandroid.ui.components.loading

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MiniLoadingCircle(){
    CircularProgressIndicator(
        color = Color.White,
        strokeWidth = 2.dp,
        modifier = Modifier.padding(5.dp).size(20.dp)
    )
}

@Preview
@Composable
fun PreviewMiniLoadingCircle(){
    MiniLoadingCircle()
}