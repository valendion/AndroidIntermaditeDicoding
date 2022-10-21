package com.example.androidintermadedicoding.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResult(
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "token")
    var token: String? = null,
    @Json(name = "userId")
    var userId: String? = null
)