package com.zhichengzhang.artistsearchandroid.session

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import com.zhichengzhang.artistsearchandroid.data.model.auth.login.UserInfo
import com.zhichengzhang.artistsearchandroid.data.model.user.favoriteList.FavoriteItem
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import com.zhichengzhang.artistsearchandroid.data.network.api.AuthApiService
import com.zhichengzhang.artistsearchandroid.data.network.api.UserApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull


object UserSession {
    private val _userInfo = mutableStateOf<UserInfo?>(null)
    val userInfo: State<UserInfo?> = _userInfo
    private const val BASE_URL = "https://final-zhicheng-zhang.wl.r.appspot.com/"

    private val _favorites = mutableStateOf<List<FavoriteItem>>(emptyList())
    val favorites: State<List<FavoriteItem>> = _favorites

    val isLogin: State<Boolean> = derivedStateOf { _userInfo.value != null }

    fun login(userInfo: UserInfo, favorites: List<FavoriteItem>) {
        _userInfo.value = userInfo
        _favorites.value = favorites
    }

    fun logout() {
        _userInfo.value = null
        _favorites.value = emptyList()
    }

    fun delete(){
        _userInfo.value = null
        _favorites.value = emptyList()
    }


    fun updateFavorites(newFavorites: List<FavoriteItem>){
        _favorites.value = newFavorites
    }

    fun isFavorite(artistId: String): Boolean {
        return _favorites.value.any { it.artistId == artistId }
    }

    fun restoreSession(context: Context) {

        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
        val client = RetrofitClient.getInstance()
        val authApi = client.create(AuthApiService::class.java)
        val userApi = client.create(UserApiService::class.java)

        val baseUrl = BASE_URL.toHttpUrlOrNull()!!
        val cookies = cookieJar.loadForRequest(baseUrl)
        cookies.forEach { cookie ->
            Log.d("UserSession", "Restored Cookie: ${cookie.name} = ${cookie.value}")
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authApi.getCurrentUser()
                if (response.success) {
                    val userInfoData = response.data

                    val favoriteListResponse = userApi.getFavoriteList(userInfoData.userId)
                    val favorites = favoriteListResponse.data.embedded ?: emptyList()

                    login(
                        UserInfo(
                            userId = userInfoData.userId,
                            fullName = userInfoData.fullName,
                            email = userInfoData.email,
                            profileImageUrl = userInfoData.profileImageUrl
                        ),
                        favorites
                    )
                    Log.d("UserSession", "Session restored successfully.")
                } else {
                    Log.w("UserSession", "Session invalid or expired.")
                    logout()
                }
            } catch (e: Exception) {
                Log.e("UserSession", "Failed to restore session: ${e.message}")
                logout()
            }
        }.start()
    }
}