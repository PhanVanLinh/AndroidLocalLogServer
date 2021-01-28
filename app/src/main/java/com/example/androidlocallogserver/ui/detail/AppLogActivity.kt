package com.example.androidlocallogserver.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlocallogserver.LocalLogApplication
import com.example.androidlocallogserver.R
import com.example.androidlocallogserver.data.AppDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AppLogActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var logAdapter: LogAdapter
    lateinit var appDataStore: AppDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_log)
        appDataStore = (application as LocalLogApplication).getAppDataStore()

        recyclerView = findViewById(R.id.recycler_logs)
        recyclerView.layoutManager = LinearLayoutManager(this)

        logAdapter = LogAdapter()
        recyclerView.adapter = logAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val appPackage = intent.getStringExtra(EXTRA_APP_PACKAGE)
        appPackage?.let {
            supportActionBar?.title = appPackage
            getLogs(it)
        }
    }

    private fun getLogs(appPackage:String) {
        GlobalScope.launch {
            val apps = appDataStore.getLogs(appPackage).first().map {
                LogAdapter.LogItem(it)
            }

            runOnUiThread {
                logAdapter.data = apps
                logAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val EXTRA_APP_PACKAGE = "EXTRA_APP_PACKAGE"
    }
}