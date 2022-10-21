package com.example.androidintermadedicoding.data.view_model


import androidx.lifecycle.ViewModel
import com.example.androidintermadedicoding.data.StoryRepository

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun postRegister(name: String, email: String, password: String) =
        storyRepository.postRegister(name, email, password)

    fun postLogin(email: String, password: String) = storyRepository.postLogin(email, password)

    fun getAllStories() = storyRepository.getAllStories()
}