package com.zhichengzhang.artistsearchandroid.viewmodel.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhichengzhang.artistsearchandroid.data.model.category.CategoryResult
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtworkCardViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _categories  = MutableStateFlow<List<CategoryResult>>(emptyList())
    val categories: StateFlow<List<CategoryResult>> = _categories

    fun fetchCategories(artworkId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val response = RetrofitClient.categoryApiService.getArtworkCategories(artworkId)
                if (response.success){
                    _categories.value = response.data.embedded
                }
            } catch (e: Exception){
                Log.e("ArtworkCardViewModel", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }
}