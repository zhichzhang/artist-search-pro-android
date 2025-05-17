package com.zhichengzhang.artistsearchandroid.data.model.auth.me

import com.zhichengzhang.artistsearchandroid.data.model.auth.login.UserInfo

class MeResponse (
    val success: Boolean,
    val message: String,
    val data: UserInfo
)