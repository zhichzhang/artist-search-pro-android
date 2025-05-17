package com.zhichengzhang.artistsearchandroid.viewmodel.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhichengzhang.artistsearchandroid.data.model.artists.ArtistResult
import com.zhichengzhang.artistsearchandroid.data.model.artists.artworks.ArtworkResult
import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResult
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import com.zhichengzhang.artistsearchandroid.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailedArtistInformationViewModal: ViewModel() {
    private val _artistDetails = MutableStateFlow<ArtistResult?>(null)
    val artistDetails: StateFlow<ArtistResult?> = _artistDetails

    private val _artistArtworks = MutableStateFlow<List<ArtworkResult>>(emptyList())
    val artistArtworks: StateFlow<List<ArtworkResult>> = _artistArtworks

    private val _artistSimilarArtists = MutableStateFlow<List<SearchResult>>(emptyList())
    val artistSimilarArtists: StateFlow<List<SearchResult>> = _artistSimilarArtists

    private val _isDetailsLoading = MutableStateFlow(false)
    val isDetailsLoading: StateFlow<Boolean> = _isDetailsLoading
    private val _isArtworksLoading = MutableStateFlow(false)
    val isArtworksLoading: StateFlow<Boolean> = _isArtworksLoading
    private val _isSimilarLoading = MutableStateFlow(false)
    val isSimilarLoading: StateFlow<Boolean> = _isSimilarLoading

    fun fetchDetailsById(artistId: String) {
        viewModelScope.launch {
            searchDetailsById(artistId)
        }
    }

    fun fetchArtworksById(artistId: String){
        viewModelScope.launch {
            searchArtworksById(artistId)
        }
    }

    fun fetchSimilarArtistsById(artistId: String){
        viewModelScope.launch {
            searchSimilarArtistsById(artistId)
        }
    }

    private suspend fun searchDetailsById(artistId: String) {
        _isDetailsLoading.value = true

        try{
            val response = RetrofitClient.artistsApiService.getArtistDetails(artistId)
            if (response.success){
                _artistDetails.value = response.data
            }
        } catch (e: Exception){
            Log.e("DetailedArtistInformationViewModal", e.message.toString())
        } finally {
            _isDetailsLoading.value = false
        }
        RetrofitClient.searchApiService.searchArtists(artistId)
    }

    private suspend fun searchArtworksById(artistId: String) {
        _isArtworksLoading.value = true

        try {
            val response = RetrofitClient.artworksApiService.getArtworksByArtist(artistId)
            if (response.success){
                _artistArtworks.value = response.data.embedded
            }
        } catch(e: Exception){
            Log.e("DetailedArtistInformationViewModal", e.message.toString())
        } finally {
            _isArtworksLoading.value = false
        }
    }

    private suspend fun searchSimilarArtistsById(artistId: String) {
        _isSimilarLoading.value = true

        try{
            val response = RetrofitClient.artistsApiService.getSimilarArtists(artistId)
            if (response.success){
                _artistSimilarArtists.value = response.data.embedded
            }
        } catch (e: Exception){
            Log.e("DetailedArtistInformationViewModal", e.message.toString())
        } finally {
            _isSimilarLoading.value = false
        }

    }

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