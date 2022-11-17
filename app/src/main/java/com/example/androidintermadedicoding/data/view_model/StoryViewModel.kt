package com.example.androidintermadedicoding.data.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.androidintermadedicoding.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun postRegister(name: String, email: String, password: String) =
        storyRepository.postRegister(name, email, password)

    fun postLogin(email: String, password: String) = storyRepository.postLogin(email, password)


    fun getAllStoryPaging(token: String) = storyRepository.getAllStoryyPaging(token).cachedIn(viewModelScope)

    fun getDetailStories(token: String, id: String) = storyRepository.getDetailStories(token, id)

    fun postStory(token: String, description: RequestBody, file: MultipartBody.Part) =
        storyRepository.postStory(token, description, file)

    fun getAllLocation(token:String) = storyRepository.getAllLocation(token)
}