package com.zhichengzhang.artistsearchandroid.viewmodel.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import com.zhichengzhang.artistsearchandroid.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel(){
    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isAuthorized = MutableStateFlow<Boolean>(false)
    val isAuthorized: StateFlow<Boolean> = _isAuthorized

    private val _registerError = MutableStateFlow<Boolean>(false)
    val registerError: StateFlow<Boolean> = _registerError

    fun onFullNameChanged(newFullName: String) {
        _fullName.value = newFullName
    }

    fun onEmailChanged(newEmail: String) {
        _registerError.value = false
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun register() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.e("RegisterViewModel", "${_fullName.value}, ${_email.value}, ${_password.value}")
                val response = RetrofitClient.authApiService.register(
                    fullName = _fullName.value,
                    email = _email.value,
                    password = _password.value
                )
                Log.e("RegisterViewModel", response.data.toString())
                if (response.success) {
                    val userInfo = response.data

                    if (userInfo != null) {
                        val favoriteListResponse = RetrofitClient.userApiService.getFavoriteList(userInfo.userId)
                        val favorites = favoriteListResponse.data.embedded ?: emptyList()
                        UserSession.login(userInfo = userInfo, favorites = emptyList())
                    }
                    _registerError.value = false
                    _isAuthorized.value = true
                    Log.e("RegisterViewModel", "Success")
                } else {
                    Log.e("RegisterViewModel", "Failed")
                    _registerError.value = true
                    _isAuthorized.value = false
                }
            } catch (e: Exception) {
                _registerError.value = true
                _isAuthorized.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}