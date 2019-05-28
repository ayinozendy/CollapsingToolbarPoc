package com.gamalinda.android.poc.testing.collapsingtoolbar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gamalinda.android.poc.testing.collapsingtoolbar.R
import com.gamalinda.android.poc.testing.collapsingtoolbar.adapter.MockListAdapter

class ConsolesMockListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.fragment_mock_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = resources.getStringArray(R.array.mock_items_consoles)
        val adapter = MockListAdapter(items)
        recyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}
