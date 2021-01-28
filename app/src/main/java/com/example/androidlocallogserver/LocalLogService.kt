package com.example.androidlocallogserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.androidlocallogserver.data.AppDataStore
import com.example.androidlocallogserver.model.LocalLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LocalLogService : Service() {
    lateinit var appDataStore: AppDataStore

    override fun onCreate() {
        super.onCreate()
        appDataStore = (application as LocalLogApplication).getAppDataStore()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val binder = object : ILocalLogService.Stub() {

        override fun getLogCount(app: String?): Int {
            return runBlocking {
                appDataStore.getLogCount(app ?: "")
            }
        }

        override fun writeLog(appPackage: String?, log: LocalLog?) {
            Log.i("TAG", "server receive log from $appPackage")
            GlobalScope.launch {
                if (appPackage != null && log != null) {
                    appDataStore.saveLog(appPackage, log)
                }
            }
        }

    }
}