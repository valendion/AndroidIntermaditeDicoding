package com.example.androidintermadedicoding.utils.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class PreferenceData(
    private val dataStore: DataStore<Preferences>
) {
    private val BEARER_KEY = stringPreferencesKey("bearer_data")

    fun getBearerData(): Flow<String>{
        return dataStore.data.map { value: Preferences ->
            value[BEARER_KEY]?: ""
        }
    }

    suspend fun saveBearerData(token: String){
        dataStore.edit { value: MutablePreferences ->
            value[BEARER_KEY] = token
        }
    }

    suspend fun deleteBearerData(){
        dataStore.edit { value: MutablePreferences ->
            value.remove(BEARER_KEY)
        }
    }
}