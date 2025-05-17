package com.zhichengzhang.artistsearchandroid.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhichengzhang.artistsearchandroid.ui.components.cardDisplay.SearchResultCard
import com.zhichengzhang.artistsearchandroid.viewmodel.screens.SearchResultViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.ui.components.noResults.NoResultsCard
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchResultScreen(
    navController: NavController,
    onSearchResultCardClick: (artistId: String, artistName: String) -> Unit,
    searchViewModel: SearchResultViewModel = viewModel()){

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val searchResults by searchViewModel.searchResults
    val q by searchViewModel.q.collectAsState()

    val isNight = isSystemInDarkTheme()

    val topAppBarColor = if (!isNight) colorResource(R.color.top_bar_day) else colorResource(R.color.top_bar_night)
    val backgroundColor = if (!isNight) colorResource(R.color.background_day) else colorResource(R.color.background_night)
    val iconTint = if (!isNight) colorResource(R.color.icon_day) else colorResource(R.color.icon_night)
    val placeholderColor = if (!isNight) colorResource(R.color.placeholder_color_day) else colorResource(R.color.placeholder_color_night)
    val textFieldBackgroundColor = Color.Transparent

    val closeIcon = painterResource(R.drawable.close_icon)
    val searchIcon = painterResource(R.drawable.search_icon)

    Scaffold(
        snackbarHost = {SnackbarHost(snackBarHostState)},
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        TextField(
                            value = q,
                            onValueChange = { searchViewModel.onQChanged(it) },
                            placeholder = {
                                Text(
                                    "Search artists...",
                                    color = placeholderColor,
                                    fontSize = 16.sp
                                )
                            },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = iconTint
                            ),
                            colors = TextFieldDefaults.colors(
                                cursorColor = placeholderColor,
                                focusedTextColor = iconTint,
                                unfocusedTextColor = iconTint,
                                focusedPlaceholderColor = placeholderColor,
                                unfocusedPlaceholderColor = placeholderColor,
                                focusedContainerColor = textFieldBackgroundColor,
                                unfocusedContainerColor = textFieldBackgroundColor,
                                disabledContainerColor = textFieldBackgroundColor,
                                focusedIndicatorColor = textFieldBackgroundColor,
                                unfocusedIndicatorColor = textFieldBackgroundColor,
                                disabledIndicatorColor = textFieldBackgroundColor,
                            ),
                            modifier = Modifier
                                .fillMaxWidth().height(60.dp)
                                .background(textFieldBackgroundColor)
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        painter = searchIcon,
                        contentDescription = "Search icon",
                        tint = iconTint,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                actions = {
                    IconButton(onClick = {
                        searchViewModel.onClear()
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = closeIcon,
                            contentDescription = "Clear and close",
                            tint = iconTint
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarColor
                )
            )
        },
        content = { innerPadding ->
            if (searchResults.isEmpty()){
                NoResultsCard("No Result Found")
            }else{
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(searchResults) { result ->
                        SearchResultCard(
                            searchResult = result,
                            onClick = onSearchResultCardClick,
                            onFavoriteChanged = {
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
                            }
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchResultScreen(){
}
