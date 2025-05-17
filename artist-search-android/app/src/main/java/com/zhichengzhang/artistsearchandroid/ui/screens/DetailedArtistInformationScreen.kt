package com.zhichengzhang.artistsearchandroid.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.data.model.artists.ArtistResult
import com.zhichengzhang.artistsearchandroid.session.UserSession
import com.zhichengzhang.artistsearchandroid.ui.components.detailsArtistInformationScreenContent.ArtworksContent
import com.zhichengzhang.artistsearchandroid.ui.components.detailsArtistInformationScreenContent.DetailsContent
import com.zhichengzhang.artistsearchandroid.ui.components.detailsArtistInformationScreenContent.SimilarContent
import com.zhichengzhang.artistsearchandroid.ui.components.loading.LoadingCircle
import com.zhichengzhang.artistsearchandroid.ui.components.tabs.TabRow
import com.zhichengzhang.artistsearchandroid.viewmodel.screens.DetailedArtistInformationViewModal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedArtistInformationScreen(
    artistId: String,
    artistName: String,
    onSimilarArtistClick: (artistId: String, artistName: String) -> Unit,
    navController: NavController,
    detailedArtistInformationViewModal: DetailedArtistInformationViewModal = viewModel()
){
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val isLoginState = UserSession.isLogin
    val isLogin by rememberUpdatedState(isLoginState.value)
    val userInfoState = UserSession.userInfo
    val userInfo by rememberUpdatedState(userInfoState.value)
    val favoritesState = UserSession.favorites
    val favorites by rememberUpdatedState(favoritesState.value)
    val isFavorite = UserSession.isFavorite(artistId)

    val isDetailsLoading by detailedArtistInformationViewModal.isDetailsLoading.collectAsState()
    val isArtworksLoading by detailedArtistInformationViewModal.isArtworksLoading.collectAsState()
    val isSimilarLoading by detailedArtistInformationViewModal.isSimilarLoading.collectAsState()
    val artistDetails by detailedArtistInformationViewModal.artistDetails.collectAsState()
    val artistArtworks by detailedArtistInformationViewModal.artistArtworks.collectAsState()
    val artistSimilarArtists by detailedArtistInformationViewModal.artistSimilarArtists.collectAsState()

    val isNight = isSystemInDarkTheme()
    val selectedTab = remember { mutableStateOf("Details") }
    val artistDetail = remember { mutableStateOf<ArtistResult?>(null) }
    val favoriteStar = if (isFavorite) painterResource(id = R.drawable.star_icon) else painterResource(id = R.drawable.star_border_icon)
    val backIcon = painterResource(R.drawable.back_icon)
    val topAppBarColor = if (!isNight) colorResource(R.color.top_bar_day) else colorResource(R.color.top_bar_night)
    val backgroundColor = if (!isNight) colorResource(R.color.background_day) else colorResource(R.color.background_night)
    val titleColor = if (!isNight) colorResource(R.color.text_day) else colorResource(R.color.text_night)
    val iconTint = if (!isNight) colorResource(R.color.icon_day) else colorResource(R.color.icon_night)



    LaunchedEffect(selectedTab.value) {
        when(selectedTab.value){
            "Details" -> {
                detailedArtistInformationViewModal.fetchDetailsById(artistId)
            }
            "Artworks" -> {
                detailedArtistInformationViewModal.fetchArtworksById(artistId)
            }
            "Similar" -> {
                detailedArtistInformationViewModal.fetchSimilarArtistsById(artistId)
            }
        }
    }

    Scaffold(
        containerColor = backgroundColor,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = artistName, color = titleColor, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = backIcon,
                            contentDescription = "Back",
                            tint = iconTint
                        )
                    }
                },
                actions = {
                    if (isLogin){
                        IconButton(onClick={
                            userInfo?.let { detailedArtistInformationViewModal.toggleFavorite(it.userId, artistId) }
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    when (isFavorite) {
                                        true -> "Removed from Favorites"
                                        false -> "Added to Favorites"
                                    }
                                )
                            }
                        }) {
                            Icon(
                                painter = favoriteStar,
                                contentDescription = "Favorite Star",
                                tint = iconTint
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarColor
                )
            )
        }
    ){ paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(artistId = artistId, onTabSelected = {selectedTab.value = it})

            when (selectedTab.value) {
                "Details" -> {
                            if (isDetailsLoading){
                                LoadingCircle()
                            } else{
                                DetailsContent(artistDetails)
                            }

                }
                "Artworks" -> {
                    if (isArtworksLoading){
                        LoadingCircle()
                    } else{
                        ArtworksContent(artistArtworks)
                    }
                }
                "Similar" -> {
                    if (isSimilarLoading){
                        LoadingCircle()
                    } else{
                        SimilarContent(artistSimilarArtists, onSimilarContentClick = onSimilarArtistClick, onFavoriteChanged = {
                            action ->
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    when (action) {
                                        "Add" -> "Added to Favorites"
                                        "Remove" -> "Removed from Favorites"
                                        else -> ""
                                    }
                                )
                            }
                        })
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DetailedArtistInformationScreenPreview() {
//
    val sampleArtistResult = ArtistResult("Van", "1923","1967", "Spanish", "AAAAAAAAAAAAAAAA")
//    DetailedArtistInformationScreen("1", sampleArtistResult)
}