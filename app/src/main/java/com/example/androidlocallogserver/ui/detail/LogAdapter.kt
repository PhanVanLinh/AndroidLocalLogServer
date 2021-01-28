package com.example.androidlocallogserver.ui.detail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlocallogserver.R
import com.example.androidlocallogserver.model.LocalLog

class LogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<LogItem>()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (holder is ContentViewHolder) {
            holder.bind(item)
        }
    }

    internal inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: LogItem) {
            itemView.findViewById<TextView>(R.id.text_message).text = item.localLog.message
            if (item.localLog.type == "e") {
                itemView.findViewById<TextView>(R.id.text_message).setTextColor(Color.RED)
            } else {
                itemView.findViewById<TextView>(R.id.text_message).setTextColor(Color.BLACK)
            }
        }
    }

    class LogItem(val localLog: LocalLog)
}