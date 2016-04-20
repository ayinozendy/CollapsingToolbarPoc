package com.gamalinda.android.poc.testing.collapsingtoolbar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.gamalinda.android.poc.testing.collapsingtoolbar.R;

public class CollapsingProfileHeaderView extends LinearLayout {
    public CollapsingProfileHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.view_collapsing_profile_header, this, true);
    }
}
