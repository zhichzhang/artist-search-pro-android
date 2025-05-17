package com.zhichengzhang.artistsearchandroid

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.zhichengzhang.artistsearchandroid.data.network.RetrofitClient
import com.zhichengzhang.artistsearchandroid.navigation.AppNavigationHost
import com.zhichengzhang.artistsearchandroid.session.UserSession

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RetrofitClient.init(applicationContext)
        val isNight = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        UserSession.restoreSession(applicationContext)
        setStatusBar(isNight)

        setContent {
            AppNavigationHost()
        }
    }

    private fun setStatusBar(isNight: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)

        if (!isNight) {
            window.statusBarColor = resources.getColor(R.color.top_bar_day)
            insetsController.isAppearanceLightStatusBars = false
            window.navigationBarColor = resources.getColor(R.color.background_day)
        } else {
            window.statusBarColor = resources.getColor(R.color.top_bar_night)
            insetsController.isAppearanceLightStatusBars = true
            window.navigationBarColor = resources.getColor(R.color.background_night)
        }
    }
}