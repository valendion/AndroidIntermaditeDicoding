package com.example.androidintermadedicoding.utils

sealed class Status<out R>{
    data class Success<out T>(val data: T): Status<T>()
    data class Error(val error: String): Status<Nothing>()
    object Loading : Status<Nothing>()
}
