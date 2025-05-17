package com.zhichengzhang.artistsearchandroid.ui.components.tabs

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhichengzhang.artistsearchandroid.R
import kotlinx.coroutines.selects.select

@Composable
fun TabTemplate(
    iconResourceId: Int,
    tabTitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier){
    val isNight = isSystemInDarkTheme()

    val iconTint = if(!isNight) colorResource(R.color.tab_day) else colorResource(R.color.tab_night)
    val tabTitleColor = if (!isNight) colorResource(R.color.tab_title_day) else colorResource(R.color.tab_title_night)
    val selectedIndicatorColor = if (isSelected) iconTint else Color.Transparent

    val icon = painterResource(iconResourceId)

    Box(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxHeight()
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                drawLine(
                    color = selectedIndicatorColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            },
        contentAlignment = Alignment.Center
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = "Icon",
            tint = iconTint
        )
        Text(
            text = tabTitle,
            color = tabTitleColor,
            modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally)
        )
    }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabTemplate(){
    val detailsIconResourceId = R.drawable.details_tab_icon
    val detailsTabString = "Details"

    var selectedTab = remember { mutableStateOf("Details") }
    var isLogin = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabTemplate(
            R.drawable.details_tab_icon,
            "Details",
            isSelected = selectedTab.value == "Details",
            onClick = {
                selectedTab.value = "Details"
                Log.e("TabTemplate", selectedTab.value)
                      },
            modifier = Modifier.weight(1f)
        )
        TabTemplate(
            R.drawable.artworks_tab_icon,
            "Artworks",
            isSelected = selectedTab.value == "Artworks",
            onClick = {
                selectedTab.value = "Artworks"
                Log.e("TabTemplate", selectedTab.value)
                      },
            modifier = Modifier.weight(1f)
        )
        if (isLogin.value){
            TabTemplate(
                R.drawable.similar_tab_icon,
                "Similar",
                isSelected = selectedTab.value == "Similar",
                onClick = {
                    selectedTab.value = "Similar"
                    Log.e("TabTemplate", selectedTab.value)
                },
                modifier = Modifier.weight(1f)
            )
        }

    }
}

