package com.gamalinda.android.poc.testing.collapsingtoolbar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.gamalinda.android.poc.testing.collapsingtoolbar.R

class TextContentMockFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.content_main, container, false)
    }
}
