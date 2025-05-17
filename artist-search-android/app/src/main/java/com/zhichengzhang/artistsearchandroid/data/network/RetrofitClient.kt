package com.zhichengzhang.artistsearchandroid.data.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.zhichengzhang.artistsearchandroid.data.network.api.ArtistsApiService
import com.zhichengzhang.artistsearchandroid.data.network.api.ArtworksApiService
import com.zhichengzhang.artistsearchandroid.data.network.api.AuthApiService
import com.zhichengzhang.artistsearchandroid.data.network.api.CategoryApiService
import retrofit2.converter.gson.GsonConverterFactory
import com.zhichengzhang.artistsearchandroid.data.network.api.SearchApiService
import com.zhichengzhang.artistsearchandroid.data.network.api.UserApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.CookieCache
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

//@SuppressLint("StaticFieldLeak")
object RetrofitClient {
    private const val BASE_URL = "https://final-zhicheng-zhang.wl.r.appspot.com/"

    private var retrofit: Retrofit? = null
    private lateinit var cookieJar: PersistentCookieJar

    fun init(context: Context) {
        if (!::cookieJar.isInitialized) {
            cookieJar = PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(context)
            )
        }

        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            printCookies()
        }
    }

    fun getInstance(): Retrofit {
        return retrofit ?: throw IllegalStateException("RetrofitClient not initialized")
    }

    private fun printCookies() {
        if (::cookieJar.isInitialized) {
            val cookies: List<Cookie> = cookieJar.loadForRequest(BASE_URL.toHttpUrlOrNull()!!)
            cookies.forEach { cookie ->
                Log.e("RetrofitClient", "Cookie: ${cookie.name} = ${cookie.value}")
            }
        }
    }

    fun clearCookies() {
        if (::cookieJar.isInitialized) {
            cookieJar.clear()
            Log.d("RetrofitClient", "Cookies cleared")
        }
    }

    val searchApiService: SearchApiService by lazy {
        retrofit?.create(SearchApiService::class.java) ?: throw IllegalStateException("Retrofit not initialized")
    }

    val authApiService: AuthApiService by lazy {
        retrofit?.create(AuthApiService::class.java) ?: throw IllegalStateException("Retrofit not initialized")
    }

    val userApiService: UserApiService by lazy {
        retrofit?.create(UserApiService::class.java) ?: throw IllegalStateException("Retrofit not initialized")
    }

    val artistsApiService: ArtistsApiService by lazy {
        retrofit?.create(ArtistsApiService::class.java) ?: throw IllegalStateException("Retrofit not initialized")
    }

    val artworksApiService: ArtworksApiService by lazy {
        retrofit?.create(ArtworksApiService::class.java) ?: throw IllegalStateException("Retrofit not initialized")
    }

    val categoryApiService: CategoryApiService by lazy {
        retrofit?.create(CategoryApiService::class.java) ?: throw IllegalStateException("Retrofit not initialized")
    }
}