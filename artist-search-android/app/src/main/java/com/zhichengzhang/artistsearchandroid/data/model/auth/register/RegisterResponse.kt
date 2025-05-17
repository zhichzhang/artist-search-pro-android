package com.zhichengzhang.artistsearchandroid.data.model.auth.register

import com.zhichengzhang.artistsearchandroid.data.model.auth.login.UserInfo

class RegisterResponse (
    val success: Boolean,
    val message: String,
    val data: UserInfo?
)