package com.example.androidintermadedicoding.data.network

import com.example.androidintermadedicoding.BuildConfig
import com.example.androidintermadedicoding.utils.Constans
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { Moshi.Builder().build() }
    factory {
        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }
    factory { provideOkHttp(get()) }
    single { provideRetrofit(get(), get()) }
    factory { provideService(get()) }
}

private fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
    return  OkHttpClient().newBuilder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit{
    return Retrofit.Builder()
        .baseUrl(Constans.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

fun provideService(retrofit: Retrofit): ApiServiceStory{
    return retrofit.create(ApiServiceStory::class.java)
}

