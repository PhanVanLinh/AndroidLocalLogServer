package com.example.androidlocallogserver.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlocallogserver.R


class AppAdapter(private val onItemClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<AppItem>()


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        if (holder is ContentViewHolder) {
            holder.bind(item)
        }
    }

    internal inner class ContentViewHolder(
        itemView: View,
        private val onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bind(item: AppItem) {
            itemView.findViewById<TextView>(R.id.text_name).text = item.appPackage
        }
    }

    class AppItem(val appPackage: String)
}