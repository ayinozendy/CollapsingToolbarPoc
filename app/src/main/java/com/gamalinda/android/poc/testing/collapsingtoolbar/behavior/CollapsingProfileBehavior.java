package com.gamalinda.android.poc.testing.collapsingtoolbar.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gamalinda.android.poc.testing.collapsingtoolbar.R;

@SuppressWarnings("unused")
public class CollapsingProfileBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private static final String TAG = "BehaviorPOC";
    private static final boolean DEBUG_LOG_HEIGHTS = false;
    private static final boolean DEBUG_LOG_YPOS = true;
    private static final boolean DEBUG_LOG_PROFILE_IMAGE = false;
    private static final boolean DEBUG_LOG_PROFILE_IMAGE_LOC = true;

    private Context context;

    private View appBar;
    private View headerProfile;
    private View profileImage;

    private int appBarHeight;
    private int profileImageSizeSmall;
    private int profileImageSizeBig;
    private int toolBarHeight;

    public CollapsingProfileBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        profileImageSizeSmall = (int) context.getResources().getDimension(R.dimen.profile_small_size);
        profileImageSizeBig = (int) context.getResources().getDimension(R.dimen.profile_big_size);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        appBar = dependency;
        headerProfile = child;
        appBarHeight = appBar.getHeight();
        profileImage = headerProfile.findViewById(R.id.profileImage);

        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        toolBarHeight = (appBar.findViewById(R.id.toolbar)).getHeight();
        updateOffset();
        updateProfileImageSize();
        updateProfileImageMargins();
        return true;
    }

    private void updateOffset() {
        headerProfile.setY(appBar.getY());
        logValues();
    }

    private void updateProfileImageSize() {
        float x = appBar.getY();
        float m = calculateSlopeForImageSizeOffset();
        float b = profileImageSizeBig;
        float y = m * x + b; //Equation of a line

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) profileImage.getLayoutParams();
        lp.height = (int) y;
        lp.width = (int) y;
        profileImage.setLayoutParams(lp);
    }

    private float calculateSlopeForImageSizeOffset() {
        int x1 = 0;
        int x2 = toolBarHeight - appBarHeight;
        int y1 = profileImageSizeBig;
        int y2 = profileImageSizeSmall;


        //slope = (y2 - y1) over (x2 - x1)
        return (float) (y2 - y1) / (float) (x2 - x1);
    }

    private void updateProfileImageMargins() {
        float x = appBar.getY();
        float m = calculateSlopeForImageMargins();
        float b = 0;
        float y = m * x + b; //Equation of a line

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) profileImage.getLayoutParams();
        lp.bottomMargin = (int) y;
        lp.leftMargin = (int) y;
        lp.rightMargin = (int) y;
        profileImage.setLayoutParams(lp);
    }

    private float calculateSlopeForImageMargins() {
        int x1 = 0;
        int x2 = toolBarHeight - appBarHeight;
        int y1 = 0;
        int y2 = calculateProfileImageSmallMargin();

        //slope = (y2 - y1) over (x2 - x1)
        return (float) (y2 - y1) / (float) (x2 - x1);
    }

    private int calculateProfileImageSmallMargin() {
        int halfToolbarHeight = toolBarHeight / 2;
        int halfProfileImageSmall = profileImageSizeSmall / 2;
        return halfToolbarHeight - halfProfileImageSmall;
    }

    private void logValues() {
        String logText;
        if (DEBUG_LOG_HEIGHTS) {
            logText = "Dependency H: " + appBar.getHeight() + ", Child H: " + headerProfile.getHeight();
            Log.i(TAG, logText);
        }
        if (DEBUG_LOG_YPOS) {
            logText = "Dependency Y: " + appBar.getY() + ", Child Y: " + headerProfile.getY();
            Log.i(TAG, logText);
        }
        if (DEBUG_LOG_PROFILE_IMAGE_LOC) {
            Log.d(TAG, "Profile Image Loc: " + profileImage.getY());
        }
    }
}
