package com.example.androidintermadedicoding.utils.preference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PreferenceFactory(private val data: PreferenceData): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferenceViewModel::class.java)){
            return PreferenceViewModel(data) as T
        }
        throw IllegalAccessException("Unknown ViewModel class : ${modelClass.name}")
    }
}