package com.example.androidintermadedicoding.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.androidintermadedicoding.data.model.*
import com.example.androidintermadedicoding.data.network.ApiServiceStory
import com.example.androidintermadedicoding.data.paging.StoryPagingSource
import com.example.androidintermadedicoding.utils.Constans
import com.example.androidintermadedicoding.utils.Status
import okhttp3.MultipartBody
import okhttp3.RequestBody


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

    fun getAllStoryyPaging(token: String): LiveData<PagingData<Story>> {
        return Pager(
                    config = PagingConfig(pageSize = 5),
                    pagingSourceFactory = {
                        StoryPagingSource(apiServiceStory, "${Constans.BEARER} $token")
                    }
                ).liveData
    }

    fun getAllLocation(token: String): LiveData<Status<ResponseAllStory>> {
        return liveData {
            emit(Status.Loading)

            try {
                val responseGetAll = apiServiceStory.getAllLocation("${Constans.BEARER} $token")
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