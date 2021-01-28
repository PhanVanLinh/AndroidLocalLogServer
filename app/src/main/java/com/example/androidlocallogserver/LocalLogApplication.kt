package com.example.androidlocallogserver

import android.app.Application
import androidx.datastore.preferences.createDataStore
import com.example.androidlocallogserver.data.AppDataStore
import com.google.gson.Gson

class LocalLogApplication : Application() {
    private lateinit var appDataStore: AppDataStore

    override fun onCreate() {
        super.onCreate()
        appDataStore = AppDataStore(
            this.createDataStore(
                name = "LocalLogStorage"
            ), provideGson()
        )


    }

    private fun provideGson(): Gson {
        return Gson()
    }

    fun getAppDataStore(): AppDataStore {
        return appDataStore
    }
}