package com.zhichengzhang.artistsearchandroid.viewmodel.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent

    fun logout() {
        viewModelScope.launch {
            try{
                val response = RetrofitClient.authApiService.logout()
                if (response.success){
                    RetrofitClient.clearCookies()
                    _uiEvent.emit("logout_success")
                    Log.e("HomeViewModel", "Log out successfully")
                }
            } catch (e: Exception){
                Log.e("HomeViewModel", e.message.toString())
            } finally {

            }
        }
    }

    fun delete(userId: String) {
        viewModelScope.launch {
            try{
                val response = RetrofitClient.userApiService.deleteUser(userId)
                if (response.success){
                    RetrofitClient.clearCookies()
                    _uiEvent.emit("delete_success")
                    Log.e("HomeViewModel", "Delete successfully")
                }
            } catch (e: Exception){
                Log.e("HomeViewModel", e.message.toString())
            } finally {

            }
        }
    }
}