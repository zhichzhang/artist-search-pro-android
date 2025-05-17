package com.zhichengzhang.artistsearchandroid.ui.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.session.UserSession
import com.zhichengzhang.artistsearchandroid.ui.components.favorites.FavoritesColumn
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhichengzhang.artistsearchandroid.viewmodel.screens.HomeViewModel
import kotlinx.coroutines.launch


fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
    return currentDate.format(formatter)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onLoginClick: () -> Unit,
    navController: NavController,
    homeViewModel: HomeViewModel = HomeViewModel(),
    onFavoriteColumnClick: (artistId: String, artistName: String) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val logInSuccess = currentBackStackEntry?.savedStateHandle?.get<Boolean>("login_success")
    val registerSuccess = currentBackStackEntry?.savedStateHandle?.get<Boolean>("register_success")

    val isLoginState = UserSession.isLogin
    val isLogin by rememberUpdatedState(isLoginState.value)
    val userInfoState = UserSession.userInfo
    val userInfo by rememberUpdatedState(userInfoState.value)
    val favoritesState = UserSession.favorites
    val favorites by rememberUpdatedState(favoritesState.value)

    val isNight = isSystemInDarkTheme()
    val currentDate = remember { getCurrentDate() }

    val expanded = remember { mutableStateOf(false) }
    val artsyHyperlink = "https://www.artsy.net/"

    val context = LocalContext.current

    val buttonBackgroundColor = if (!isNight) colorResource(R.color.button_background_day) else colorResource(R.color.button_background_night)
    val buttonTextColor = if (!isNight) colorResource(R.color.button_text_day) else colorResource(R.color.button_text_night)

    val searchIcon = painterResource(id = R.drawable.search_icon)
    val userIcon = painterResource(id = R.drawable.user_icon)

    val topAppBarColor = if (!isNight) colorResource(R.color.top_bar_day) else colorResource(R.color.top_bar_night)
    val iconTint = if (!isNight) colorResource(R.color.icon_day) else colorResource(R.color.icon_night)
    val dropdownMenuBackgroundColor = if (!isNight) colorResource(R.color.home_screen_dropdown_menu_background_day) else colorResource(R.color.home_screen_dropdown_menu_background_night)
    val dropdownMenuDeleteAccountTextColor = if (!isNight) colorResource(R.color.home_screen_dropdown_menu_delete_account_text_day) else colorResource(R.color.home_screen_dropdown_menu_delete_account_text_night)
    val dropdownMenuLogoutTextColor = if (!isNight) colorResource(R.color.home_screen_dropdown_menu_logout_text_day) else colorResource(R.color.home_screen_dropdown_menu_logout_text_night)
    val currentDateTextColor = if (!isNight) colorResource(R.color.home_screen_current_date_text_color_day) else colorResource(R.color.home_screen_current_date_text_color_night)
    val favoritesTextColor = if (!isNight) colorResource(R.color.home_screen_favorites_text_color_day) else colorResource(R.color.home_screen_favorites_text_color_night)
    val favoritesBackgroundColor = if (!isNight) colorResource(R.color.home_screen_favorites_background_color_day) else colorResource(R.color.home_screen_favorites_background_color_night)
    val backgroundColor = if (!isNight) colorResource(R.color.background_day) else colorResource(R.color.background_night)
    val titleColor = if (!isNight) colorResource(R.color.text_day) else colorResource(R.color.text_night)

    LaunchedEffect(logInSuccess) {
        if (logInSuccess == true) {
            snackBarHostState.showSnackbar("Logged in successfully")
            currentBackStackEntry.savedStateHandle.remove<Boolean>("login_success")
        }
    }

    LaunchedEffect(registerSuccess) {
        if (registerSuccess == true) {
            snackBarHostState.showSnackbar("Registered successfully")
            currentBackStackEntry.savedStateHandle.remove<Boolean>("register_success")
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.uiEvent.collect { event ->
            when (event) {
                "logout_success" -> {
                    snackBarHostState.showSnackbar("Logged out successfully")
                }
                "delete_success" -> {
                    snackBarHostState.showSnackbar("Deleted user successfully")
                }
            }
        }
    }

    Scaffold(
        containerColor = backgroundColor,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Artist Search", color = titleColor, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                    IconButton(onClick = { onSearchClick() }) {
                        Icon(
                            painter = searchIcon,
                            contentDescription = "Search",
                            tint = iconTint)
                    }
                    if(!isLogin){
                        IconButton(onClick = { onLoginClick() }) {
                            Icon(
                                painter = userIcon,
                                contentDescription = "User",
                                tint = iconTint
                            )
                        }
                    }else{
                        IconButton(onClick = { expanded.value = !expanded.value }) {
                            AsyncImage(
                                model = userInfo?.profileImageUrl,
                                contentDescription = "User Avatar",
                                placeholder = painterResource(R.drawable.artsy_logo),
                                error = painterResource(R.drawable.artsy_logo),
                                modifier = Modifier.size(30.dp).clip(CircleShape)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier.background(dropdownMenuBackgroundColor)
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "Logout", color = dropdownMenuLogoutTextColor) },
                                modifier = Modifier.padding(4.dp).align(Alignment.Start),
                                onClick = {
                                    Log.e("HomeScreen", "Log out")
                                    /* TODO clear Cookies and UserInfo*/
                                    homeViewModel.logout()
                                    UserSession.logout()
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Delete account", color = dropdownMenuDeleteAccountTextColor)},
                                modifier = Modifier.padding(4.dp).align(Alignment.Start),
                                onClick = {
                                    Log.e("HomeScreen", "Delete account")
                                    /* TODO delete account*/
                                    userInfo?.let { homeViewModel.delete(it.userId) }
                                    UserSession.delete()
                                } )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarColor
                )
            )
        },
        content = {innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxWidth().wrapContentHeight()
            ) {
                    Text(text = currentDate, color = currentDateTextColor, modifier = Modifier.fillMaxWidth().background(
                        Color.Transparent).padding(top=8.dp, bottom=8.dp, start=16.dp).wrapContentWidth(Alignment.Start))

                    Text(
                        text = "Favorites",
                        color = favoritesTextColor,
                        modifier = Modifier.fillMaxWidth().background(favoritesBackgroundColor).padding(4.dp).wrapContentWidth(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                if (!isLogin) {
                    Button(
                        modifier = Modifier.fillMaxWidth().padding(8.dp).wrapContentWidth(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonBackgroundColor,
                            contentColor = buttonTextColor
                        ),
                        onClick = {
                            /* TODO go to LoginScreen */
                            onLoginClick()
                            Log.e("HomeScreen", "Go to LoginScreen") }
                    ) {
                        Text(
                            text = "Log in to see favorites",
                        )
                    }
                } else {
                        FavoritesColumn(favorites, onItemClick = onFavoriteColumnClick)
                }
                    Text(
                        text = "Powered by Artsy",
                        color = currentDateTextColor,
                        style = TextStyle(
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .clickable {
                                Log.e("HomeScreen", "Go to Artsy.net")
                                val intent = Intent(Intent.ACTION_VIEW,
                                    artsyHyperlink.toUri())
                                context.startActivity(intent)
                            }
                    )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

}