package com.example.androidintermadedicoding.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDetail(
    @Json(name = "error")
    var error: Boolean? = false,
    @Json(name = "message")
    var message: String? = "",
    @Json(name = "story")
    var story: Story? = Story()
)