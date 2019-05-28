package com.gamalinda.android.poc.testing.collapsingtoolbar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gamalinda.android.poc.testing.collapsingtoolbar.R
import com.gamalinda.android.poc.testing.collapsingtoolbar.view.TextItemViewHolder

class MockListAdapter(private var items: Array<String>) : RecyclerView.Adapter<TextItemViewHolder>() {

    fun setItems(items: Array<String>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_mock, parent, false)
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        if (position == items.size + 1) {
            holder.hideSeparator(true)
        }
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
