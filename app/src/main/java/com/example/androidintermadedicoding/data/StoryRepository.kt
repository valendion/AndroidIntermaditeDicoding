package com.example.androidintermadedicoding.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.androidintermadedicoding.data.model.ResponseAllStory

import com.example.androidintermadedicoding.data.model.ResponseLogin
import com.example.androidintermadedicoding.data.model.ResponseRegister
import com.example.androidintermadedicoding.data.network.ApiServiceStory
import com.example.androidintermadedicoding.utils.Constans
import com.example.androidintermadedicoding.utils.Status
import com.example.androidintermadedicoding.utils.preference.PreferenceData


class StoryRepository(
private val apiServiceStory: ApiServiceStory,
private val pref: PreferenceData
) {
    fun postRegister(name: String, email: String, password: String): LiveData<Status<ResponseRegister>>{
        return liveData{
            emit(Status.Loading)

            try {
                val responseRegister = apiServiceStory.postRegister(name, email, password)
                emit(Status.Success(responseRegister))
            }catch (e: Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun postLogin( email: String, password: String): LiveData<Status<ResponseLogin>>{
        return liveData{
            emit(Status.Loading)

            try {
                val responseLogin = apiServiceStory.postLogin(email, password)
                pref.saveBearerData(responseLogin.loginResult?.token.toString())
                emit(Status.Success(responseLogin))
            }catch (e: Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }

    fun getAllStories(): LiveData<Status<ResponseAllStory>>{
        return liveData {
            emit(Status.Loading)

            try {
                val responseGetAll = apiServiceStory.getAllStory("${Constans.BEARER} ${pref.getBearerData()}")
                emit(Status.Success(responseGetAll))
            }catch (e: Exception){
                emit(Status.Error(e.message.toString()))
            }
        }
    }
}