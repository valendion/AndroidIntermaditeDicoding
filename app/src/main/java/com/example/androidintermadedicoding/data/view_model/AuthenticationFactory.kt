package com.example.androidintermadedicoding.data.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidintermadedicoding.data.StoryRepository

class AuthenticationFactory(private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)){
            return StoryViewModel(storyRepository) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class : ${modelClass.name}")
    }
}