package com.zhichengzhang.artistsearchandroid.viewmodel.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import com.zhichengzhang.artistsearchandroid.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchResultCardViewModel: ViewModel() {

    fun toggleFavorite(userId: String, artistId: String){
        viewModelScope.launch {
            try{
                val response = RetrofitClient.userApiService.toggleFavorite(
                    userId = userId,
                    artistId = artistId
                )
                Log.e("SearchResultCardViewModel", response.toString())
                if (response.success){
                    val favoriteListResponse = RetrofitClient.userApiService.getFavoriteList(userId)
                    Log.e("LoginViewModel", favoriteListResponse.toString())
                    val favorites = favoriteListResponse.data.embedded ?: emptyList()
                    UserSession.updateFavorites(favorites)
                    Log.e("SearchResultCardViewModel", "Success")
                }

            } catch (e: Exception){
                Log.e("SearchResultCardViewModel", e.message.toString())
            } finally {

            }

        }
    }

}