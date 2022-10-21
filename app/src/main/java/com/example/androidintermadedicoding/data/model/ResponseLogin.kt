package com.example.androidintermadedicoding.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseLogin(
    @Json(name = "error")
    var error: Boolean? = null,
    @Json(name = "loginResult")
    var loginResult: LoginResult? = null,
    @Json(name = "message")
    var message: String? = null
)