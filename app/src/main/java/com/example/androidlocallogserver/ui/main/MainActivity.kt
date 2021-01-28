package com.example.androidlocallogserver.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlocallogserver.LocalLogApplication
import com.example.androidlocallogserver.R
import com.example.androidlocallogserver.data.AppDataStore
import com.example.androidlocallogserver.ui.detail.AppLogActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var appAdapter: AppAdapter
    lateinit var appDataStore: AppDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appDataStore = (application as LocalLogApplication).getAppDataStore()

        recyclerView = findViewById(R.id.recycler_app)
        recyclerView.layoutManager = LinearLayoutManager(this)

        appAdapter = AppAdapter { itemClicked ->
            openAppLog(appAdapter.data[itemClicked].appPackage)
        }
        recyclerView.adapter = appAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        getApps()
    }

    private fun getApps() {
        GlobalScope.launch {
            val apps = appDataStore.getApps().first().map {
                AppAdapter.AppItem(it)
            }

            runOnUiThread {
                appAdapter.data = apps
                appAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun openAppLog(appPackage: String) {
        val intent = Intent(this, AppLogActivity::class.java)
        intent.putExtra(AppLogActivity.EXTRA_APP_PACKAGE, appPackage)
        startActivity(intent)
    }
}

