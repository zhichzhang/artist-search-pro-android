package com.zhichengzhang.artistsearchandroid.ui.components.loading

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
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

@Composable
fun LoadingCircle(){
    val isNight = isSystemInDarkTheme()

    val loadingCircleFrontColor = if (!isNight) colorResource(R.color.loading_circle_front_day) else colorResource(R.color.loading_circle_front_night)
    val loadingCircleBackColor = if (!isNight) colorResource(R.color.loading_circle_back_day) else colorResource(R.color.loading_circle_back_night)
    val loadingCircleTextColor = if (!isNight) colorResource(R.color.loading_circle_text_day) else colorResource(R.color.loading_circle_text_night)


    Column(
        modifier = Modifier.padding(10.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                progress = { 1f },
                color = loadingCircleBackColor,
                strokeWidth = 4.dp,
                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                modifier = Modifier.size(48.dp)
            )

            CircularProgressIndicator(
                color = loadingCircleFrontColor,
                strokeWidth = 4.dp,
                modifier = Modifier.size(48.dp)
            )
        }

        Text(
            text="Loading...",
            color = loadingCircleTextColor,
            style = TextStyle(
                fontSize = 16.sp,
                color = loadingCircleTextColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingCircle(){
    LoadingCircle()
}