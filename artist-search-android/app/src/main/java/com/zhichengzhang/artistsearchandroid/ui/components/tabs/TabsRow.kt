package com.zhichengzhang.artistsearchandroid.ui.components.tabs

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.artists.ArtistResult
import com.zhichengzhang.artistsearchandroid.session.UserSession

@Composable
fun TabRow(artistId: String, onTabSelected: (String) -> Unit){

    val isNight = isSystemInDarkTheme()

    val isLoginState = UserSession.isLogin
    val isLogin by rememberUpdatedState(isLoginState.value)
//    val userInfoState = UserSession.userInfo
//    val userInfo by rememberUpdatedState(userInfoState.value)
//    val favoritesState = UserSession.favorites
//    val favorites by rememberUpdatedState(favoritesState.value)
    var selectedTab = remember { mutableStateOf("Details") }

    val bottomBorderLineColor = if(isNight) colorResource(R.color.loading_circle_text_day) else colorResource(R.color.loading_circle_text_night)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(8.dp).drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = bottomBorderLineColor,
                    start = Offset(0f, size.height - strokeWidth / 2),
                    end = Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabTemplate(
            R.drawable.details_tab_icon,
            "Details",
            isSelected = selectedTab.value == "Details",
            onClick = {
                Log.e("TabTemplate", "${artistId}'s ${selectedTab.value}")
                selectedTab.value = "Details"
                onTabSelected("Details")
            },
            modifier = Modifier.weight(1f)
        )
        TabTemplate(
            R.drawable.artworks_tab_icon,
            "Artworks",
            isSelected = selectedTab.value == "Artworks",
            onClick = {
                Log.e("TabTemplate", "${artistId}'s ${selectedTab.value}")
                selectedTab.value = "Artworks"
                onTabSelected("Artworks")
            },
            modifier = Modifier.weight(1f)
        )
        if (isLogin){
            TabTemplate(
                R.drawable.similar_tab_icon,
                "Similar",
                isSelected = selectedTab.value == "Similar",
                onClick = {
                    Log.e("TabTemplate", "${artistId}'s ${selectedTab.value}")
                    selectedTab.value = "Similar"
                    onTabSelected("Similar")
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTabsRow(){
    val artistId = "1"
    TabRow(artistId, onTabSelected = {selectTab -> Log.e("TabRow", "Select ${selectTab}")})
}