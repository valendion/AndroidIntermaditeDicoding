package com.example.androidintermadedicoding.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseAllStory(
    @Json(name = "error")
    var error: Boolean? = null,
    @Json(name = "listStory")
    var listStory: ArrayList<Story?>? = null,
    @Json(name = "message")
    var message: String? = null
)