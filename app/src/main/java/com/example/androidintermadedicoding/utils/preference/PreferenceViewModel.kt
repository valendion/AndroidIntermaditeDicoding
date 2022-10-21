package com.example.androidintermadedicoding.utils.preference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PreferenceViewModel(private val data: PreferenceData): ViewModel() {
//    fun getBearerKey(): LiveData<String>{
//        return data.getBearerData().asLiveData()
//    }
//
//    fun saveBearerKey(token: String){
//        viewModelScope.launch{
//            data.saveBearerData(token)
//        }
//    }

    fun deleteBearerKey(){
        viewModelScope.launch {
            data.deleteBearerData()
        }
    }
}