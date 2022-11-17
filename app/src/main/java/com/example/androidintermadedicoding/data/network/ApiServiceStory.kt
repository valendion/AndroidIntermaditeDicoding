package com.example.androidintermadedicoding.data.network

import com.example.androidintermadedicoding.data.model.ResponseAllStory
import com.example.androidintermadedicoding.data.model.ResponseDetail
import com.example.androidintermadedicoding.data.model.ResponseLogin
import com.example.androidintermadedicoding.data.model.ResponseRegister
import com.example.androidintermadedicoding.utils.Constans
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    suspend fun getAllStoryPaging(@Header(Constans.AUTHORIZATION) token: String,
    @Query("page")page: Int, @Query("size") size:Int): ResponseAllStory

    @GET("${Constans.STORY_URL}/{id}")
    suspend fun getDetailStory(
        @Header(Constans.AUTHORIZATION) token: String,
        @Path("id") id: String
    ): ResponseDetail

    @Multipart
    @POST(Constans.STORY_URL)
    suspend fun postStory(
        @Header(Constans.AUTHORIZATION) token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): ResponseRegister

    @GET(Constans.STORY_URL)
    suspend fun getAllLocation(@Header(Constans.AUTHORIZATION) token: String,
    @Query("location") param:Int = 1): ResponseAllStory
}