package com.samawade.expensetracker.data

import android.app.Application
import android.content.Context

private const val KEY_PREFERENCES = "expense_preferences"
private const val KEY_TOKEN = "token"
private const val KEY_ID = "id"

class App: Application() {
    companion object {
        private lateinit var instance: App

        private val preferences by lazy {
            instance.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        }

        fun saveToken(token: String, id: String) {
            preferences.edit()
                .putString(KEY_TOKEN, token)
                .putString(KEY_ID, id)
                .apply()
        }

        fun getToken() = preferences.getString(KEY_TOKEN, "") ?: ""
        fun getId() = preferences.getString(KEY_ID, "") ?: ""

        fun clear(){
            preferences.edit()
                .clear()
                .apply()
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}