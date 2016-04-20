package com.gamalinda.android.poc.testing.collapsingtoolbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gamalinda.android.poc.testing.collapsingtoolbar.R;

public class CollapsingProfileHeaderView extends LinearLayout {

    private String profileName;
    private String subtitle;
    private String misc;

    private TextView profileNameTextView;
    private TextView subtitleTextView;
    private TextView miscTextView;

    public CollapsingProfileHeaderView(Context context) {
        super(context);
    }

    public CollapsingProfileHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CollapsingProfileHeaderView,
                0, 0);

        try {
            profileName = a.getString(R.styleable.CollapsingProfileHeaderView_profileName);
            subtitle = a.getString(R.styleable.CollapsingProfileHeaderView_profileSubtitle);
            misc = a.getString(R.styleable.CollapsingProfileHeaderView_profileMisc);
        } finally {
            a.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.view_collapsing_profile_header, this, true);

        loadViews();
        applyAttributes();
    }

    private void loadViews() {
        profileNameTextView = (TextView) this.findViewById(R.id.profileName);
        subtitleTextView = (TextView) this.findViewById(R.id.profileSubtitle);
        miscTextView = (TextView) this.findViewById(R.id.profileMisc);
    }

    private void applyAttributes() {
        profileNameTextView.setText(profileName);
        subtitleTextView.setText(subtitle);
        miscTextView.setText(misc);
    }
}
