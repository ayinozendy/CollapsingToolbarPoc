package com.gamalinda.android.poc.testing.collapsingtoolbar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamalinda.android.poc.testing.collapsingtoolbar.R;
import com.gamalinda.android.poc.testing.collapsingtoolbar.view.TextItemViewHolder;

public class MockListAdapter extends RecyclerView.Adapter<TextItemViewHolder> {

    String[] items;

    public MockListAdapter(String[] items) {
        this.items = items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mock, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextItemViewHolder holder, int position) {
        if (position == items.length+1) {
            holder.hideSeparator(true);
        }
        holder.bind(items[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
