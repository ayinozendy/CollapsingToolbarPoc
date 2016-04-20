package com.gamalinda.android.poc.testing.collapsingtoolbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gamalinda.android.poc.testing.collapsingtoolbar.R;

public class CollapsingProfileHeaderView extends LinearLayout {

    private int profileDrawable;
    private String profileName;
    private String subtitle;
    private String misc;
    private int miscIcon;

    private ImageView profileImage;
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
            profileDrawable = a.getResourceId(R.styleable.CollapsingProfileHeaderView_profileImage, 0);
            profileName = a.getString(R.styleable.CollapsingProfileHeaderView_profileName);
            subtitle = a.getString(R.styleable.CollapsingProfileHeaderView_profileSubtitle);
            misc = a.getString(R.styleable.CollapsingProfileHeaderView_profileMisc);
            miscIcon = a.getResourceId(R.styleable.CollapsingProfileHeaderView_profileMiscIcon, 0);
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
        profileImage = (ImageView) this.findViewById(R.id.profileImage);
        profileNameTextView = (TextView) this.findViewById(R.id.profileName);
        subtitleTextView = (TextView) this.findViewById(R.id.profileSubtitle);
        miscTextView = (TextView) this.findViewById(R.id.profileMisc);
    }

    private void applyAttributes() {
        profileImage.setImageResource(profileDrawable);
        profileNameTextView.setText(profileName);
        subtitleTextView.setText(subtitle);
        miscTextView.setText(misc);
        miscTextView.setCompoundDrawablesWithIntrinsicBounds(miscIcon, 0, 0, 0);
    }
}
