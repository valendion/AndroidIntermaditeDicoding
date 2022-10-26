package com.example.androidintermadedicoding.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.androidintermadedicoding.data.model.ResponseAllStory
import com.example.androidintermadedicoding.data.model.ResponseDetail
import com.example.androidintermadedicoding.data.model.ResponseLogin
import com.example.androidintermadedicoding.data.model.ResponseRegister
import com.example.androidintermadedicoding.data.network.ApiServiceStory
import com.example.androidintermadedicoding.utils.Constans
import com.example.androidintermadedicoding.utils.Status
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart


class StoryRepository(
    private val apiServiceStory: ApiServiceStory,
) {
    fun postRegister(
        name: String,
        email: String,
        password: String
    ): LiveData<Status<ResponseRegister>> {
        return liveData {
            emit(Status.Loading)

            try {
                val responseRegister = apiServiceStory.postRegister(name, email, password)
                emit(Status.Success(responseRegister))
            } catch (e: Exception) {
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun postLogin(email: String, password: String): LiveData<Status<ResponseLogin>> {
        return liveData {
            emit(Status.Loading)

            try {
                val responseLogin = apiServiceStory.postLogin(email, password)
                emit(Status.Success(responseLogin))
            } catch (e: Exception) {
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun getAllStories(token: String): LiveData<Status<ResponseAllStory>> {
        return liveData {
            emit(Status.Loading)

            try {
                val responseGetAll = apiServiceStory.getAllStory("${Constans.BEARER} $token")
                emit(Status.Success(responseGetAll))
            } catch (e: Exception) {
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun getDetailStories(token: String, id: String): LiveData<Status<ResponseDetail>> {
        return liveData {
            emit(Status.Loading)

            try {
                val responsDetail = apiServiceStory.getDetailStory("${Constans.BEARER} $token", id)
                emit(Status.Success(responsDetail))
            } catch (e: Exception) {
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun postStory(token: String, description: RequestBody, file: MultipartBody.Part): LiveData<Status<ResponseRegister>>{
        return liveData {
            emit(Status.Loading)

            try{
                val responseAddStory = apiServiceStory.postStory("${Constans.BEARER} $token", file, description)
                emit(Status.Success(responseAddStory))
            }catch (e: Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }
}