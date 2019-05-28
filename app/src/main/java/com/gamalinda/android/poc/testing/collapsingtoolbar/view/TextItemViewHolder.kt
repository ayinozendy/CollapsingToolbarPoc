package com.gamalinda.android.poc.testing.collapsingtoolbar.view

import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.gamalinda.android.poc.testing.collapsingtoolbar.R

class TextItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView = itemView.findViewById(R.id.list_item)
    private val separator: View = itemView.findViewById(R.id.separator)

    fun bind(text: String) {
        textView.text = text
    }

    fun hideSeparator(hide: Boolean) {
        if (hide) {
            separator.visibility = View.GONE
        } else {
            separator.visibility = View.VISIBLE
        }
    }
}
