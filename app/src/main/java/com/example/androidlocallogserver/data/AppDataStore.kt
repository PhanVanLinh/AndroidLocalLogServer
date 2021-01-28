package com.example.androidlocallogserver.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.androidlocallogserver.model.LocalLog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

class AppDataStore(private val dataStore: DataStore<Preferences>, private val gson: Gson) {
    private val localLogs = stringPreferencesKey("local_logs")

    suspend fun saveLog(appPackage: String, log: LocalLog) {
        dataStore.edit { settings ->
            val curLocalLogs = settings[localLogs]
            val curLocalLogsMap: HashMap<String, List<LocalLog>> = if (curLocalLogs == null) {
                hashMapOf()
            } else {
                val type: Type = object : TypeToken<HashMap<String, List<LocalLog>>>() {}.type
                gson.fromJson(curLocalLogs, type)
            }

            val appLocalLogs = curLocalLogsMap[appPackage]?.toMutableList() ?: mutableListOf()
            appLocalLogs.add(log)

            Log.i("TAG", "size "+appLocalLogs.size)

            curLocalLogsMap[appPackage] = appLocalLogs
            settings[localLogs] = gson.toJson(curLocalLogsMap)
        }
    }

    suspend fun getApps(): Flow<List<String>> {
        return dataStore.data.map {
            it[localLogs]
        }.map {
            if (it == null){
                return@map listOf()
            }
            val type: Type = object : TypeToken<HashMap<String, List<LocalLog>>>() {}.type
            val map: HashMap<String, List<LocalLog>> = gson.fromJson(it, type)
            map.keys.toList()
        }
    }

    suspend fun getLogs(appPackage: String): Flow<List<LocalLog>> {
        return dataStore.data.map {
            it[localLogs]
        }.map {
            val type: Type = object : TypeToken<HashMap<String, List<LocalLog>>>() {}.type
            val map: HashMap<String, List<LocalLog>> = gson.fromJson(it, type)
            map[appPackage] ?: listOf()
        }
    }

    suspend fun getLogCount(app: String): Int {
        return getLogs(app).first().count()
    }

}