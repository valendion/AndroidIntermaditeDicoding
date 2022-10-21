package com.example.androidintermadedicoding.data.network

import com.example.androidintermadedicoding.data.model.ResponseAllStory
import com.example.androidintermadedicoding.data.model.ResponseLogin
import com.example.androidintermadedicoding.data.model.ResponseRegister
import com.example.androidintermadedicoding.utils.Constans
import retrofit2.http.*

interface ApiServiceStory {
    @FormUrlEncoded
    @POST(Constans.REGISTER_URL)
    suspend fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST(Constans.LOGIN_URL)
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseLogin

    @GET(Constans.STORY_URL)
    suspend fun getAllStory(@Header(Constans.AUTHORIZATION) token: String): ResponseAllStory


}