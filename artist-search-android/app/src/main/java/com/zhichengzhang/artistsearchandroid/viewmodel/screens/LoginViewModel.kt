package com.zhichengzhang.artistsearchandroid.viewmodel.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import com.zhichengzhang.artistsearchandroid.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel(){
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isAuthorized = MutableStateFlow<Boolean>(false)
    val isAuthorized: StateFlow<Boolean> = _isAuthorized

    private val _loginError  = MutableStateFlow<Boolean>(false)
    val loginError: StateFlow<Boolean> = _loginError

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        _loginError.value = false
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        _loginError.value = false
    }

    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.authApiService.login(
                    email = _email.value,
                    password = _password.value
                )

                if (response.success) {
                    Log.e("LoginViewModel", response.message)
                    Log.e("LoginViewModel", response.data.toString())
                    val userInfo = response.data

                    val favoriteListResponse = RetrofitClient.userApiService.getFavoriteList(userInfo.userId)
                    Log.e("LoginViewModel", favoriteListResponse.toString())
                    val favorites = favoriteListResponse.data.embedded ?: emptyList()

                    Log.e("LoginViewModel", favorites.toString())
                    UserSession.login(userInfo = userInfo, favorites = favorites)

                    _isAuthorized.value = true
                } else {
                    Log.e("LoginViewModel", response.message)
                    _isAuthorized.value = false
                    _loginError.value = true
                }
            } catch (e: Exception) {
                _isAuthorized.value = false
                _loginError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}