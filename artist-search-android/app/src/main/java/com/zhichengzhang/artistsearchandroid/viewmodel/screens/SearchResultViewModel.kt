package com.zhichengzhang.artistsearchandroid.viewmodel.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResult
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchResultViewModel : ViewModel() {
    private val _q = MutableStateFlow("")
    val q: StateFlow<String> = _q

    private val _searchResults = mutableStateOf<List<SearchResult>>(emptyList())
    val searchResults: State<List<SearchResult>> = _searchResults

    init {
        viewModelScope.launch {
            _q
                .debounce(300)
                .filter { it.length > 3 }
                .distinctUntilChanged()
                .collect { q ->
                    Log.e("SearchViewModel", "Trigger searching with: $q")
                    val results = search(q)
                    _searchResults.value = results
                }
        }
    }

    fun onClear(){
        _q.value = ""
    }

    fun onQChanged(newQ: String){
        _q.value = newQ
    }

    suspend fun search(q: String): List<SearchResult> {
        return try {
            val res = RetrofitClient.searchApiService.searchArtists(q)
            if (res.success) {
                Log.e("SearchViewModel", "Success")
                res.data.embedded ?: emptyList()
            } else {
                Log.e("API", "Error: ${res.message}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Exception: ${e.localizedMessage}", e)
            emptyList()
        }
    }

}