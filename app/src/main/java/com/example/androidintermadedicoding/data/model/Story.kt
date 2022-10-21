package com.example.androidintermadedicoding.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Story(
    @Json(name = "createdAt")
    var createdAt: String? = null,
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "id")
    var id: String? = null,
    @Json(name = "lat")
    var lat: Double? = null,
    @Json(name = "lon")
    var lon: Double? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "photoUrl")
    var photoUrl: String? = null
)