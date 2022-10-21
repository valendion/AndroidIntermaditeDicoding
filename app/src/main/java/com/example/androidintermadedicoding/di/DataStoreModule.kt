package com.example.androidintermadedicoding.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.androidintermadedicoding.utils.Constans
import com.example.androidintermadedicoding.utils.preference.PreferenceData
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataStoreModule = module {
    factory { provideProtoData(get())}
    factory { PreferenceData(get()) }
    single { PreferenceFactory(get()) }
    viewModel { PreferenceViewModel(get()) }
}

fun provideProtoData(appContext: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        migrations = listOf(SharedPreferencesMigration(appContext,Constans.DATA_PREFERENCE)),
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        produceFile = { appContext.preferencesDataStoreFile(Constans.DATA_PREFERENCE) }
    )
}