package com.example.androidintermadedicoding.di

import android.app.Application
import com.example.androidintermadedicoding.data.StoryRepository
import com.example.androidintermadedicoding.data.network.networkModule
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.ui.list_story.StorisAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single {
        StoryRepository(get())
    }
    factory { StorisAdapter() }
    viewModel { StoryViewModel(get()) }
    single { AuthenticationFactory(get()) }
}

class MyAplication :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyAplication)
            modules(listOf(appModule,networkModule,dataStoreModule))
        }
    }
}