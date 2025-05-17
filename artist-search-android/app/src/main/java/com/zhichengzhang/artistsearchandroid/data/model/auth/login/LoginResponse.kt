package com.zhichengzhang.artistsearchandroid.data.model.auth.login

data class LoginResponse (
    val success: Boolean,
    val message: String,
    val data: UserInfo
)