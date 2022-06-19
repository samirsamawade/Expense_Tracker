package com.samawade.expensetracker.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "my_data_store"
        )
    }

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences->
            preferences[KEY_TOKEN]
//            preferences[KEY_ID]
        }

    val authId: Flow<String?>
        get() = dataStore.data.map { preferences->
//            preferences[KEY_TOKEN]
            preferences[KEY_ID]
        }

    suspend fun saveAuthToken(authToken: String, authId: String){
        dataStore.edit { preferences->
            preferences[KEY_TOKEN] = authToken
            preferences[KEY_ID] = authId
        }
    }

    suspend fun clear(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    companion object{
        private val KEY_TOKEN=  preferencesKey<String>("key_token")
        private val KEY_ID =  preferencesKey<String>("key_id")
    }


}