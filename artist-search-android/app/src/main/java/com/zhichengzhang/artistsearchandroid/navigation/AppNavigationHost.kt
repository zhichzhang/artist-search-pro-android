package com.zhichengzhang.artistsearchandroid.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhichengzhang.artistsearchandroid.ui.screens.DetailedArtistInformationScreen
import com.zhichengzhang.artistsearchandroid.ui.screens.HomeScreen
import com.zhichengzhang.artistsearchandroid.ui.screens.LoginScreen
import com.zhichengzhang.artistsearchandroid.ui.screens.RegisterScreen
import com.zhichengzhang.artistsearchandroid.ui.screens.SearchResultScreen

@Composable
fun AppNavigationHost(navController: NavHostController = rememberNavController()) {
//    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(onSearchClick = {
                navController.navigate("search")
                                       },
                onLoginClick = {
                    navController.navigate("login")
                               },
                navController = navController,
                onFavoriteColumnClick = { artistId, artistName ->
                    val encodedName = Uri.encode(artistName)
                    navController.navigate("details/$artistId/$encodedName")
                })
        }

        composable("search") {
            SearchResultScreen(navController, onSearchResultCardClick = {
                    artistId, artistName ->
                val encodedName = Uri.encode(artistName)
                navController.navigate("details/$artistId/$encodedName")
            })
        }

        composable("login"){
            LoginScreen(onSuccessLogin = {
                navController.navigate("home")
            }, onRegisterLogin = {
                navController.navigate("register")
            }, navController = navController)
        }

        composable("register"){
            RegisterScreen(onSuccessRegister = {
                navController.navigate("home")
            }, onLogin = {
                navController.navigate("login")
            }, navController = navController)
        }

        composable(
            "details/{id}/{name}"
        ){
            redirectInfoEntry ->
            val artistId = redirectInfoEntry.arguments?.getString("id") ?: ""
            val artistName = redirectInfoEntry.arguments?.getString("name") ?: ""
            DetailedArtistInformationScreen(artistId, artistName, onSimilarArtistClick = { similarArtistId, similarArtistName ->
                val encodedName = Uri.encode(similarArtistName)
                navController.navigate("details/$similarArtistId/$encodedName")
            }, navController = navController)
        }
    }
}