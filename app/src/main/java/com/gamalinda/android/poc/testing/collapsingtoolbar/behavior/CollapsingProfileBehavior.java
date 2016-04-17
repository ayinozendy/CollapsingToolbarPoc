package com.gamalinda.android.poc.testing.collapsingtoolbar.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

@SuppressWarnings("unused")
public class CollapsingProfileBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private static final String TAG = "BehaviorPOC";
    private static final boolean DEBUG_LOG_HEIGHTS = true;
    private static final boolean DEBUG_LOG_YPOS = true;

    private Context context;

    public CollapsingProfileBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        updateOffset(parent, child, dependency);
        return true;
    }

    private void updateOffset(CoordinatorLayout parent, LinearLayout child, View dependency) {
        child.setY(dependency.getY());
        logValues(parent, child, dependency);
    }

    private void logValues(CoordinatorLayout parent, LinearLayout child, View dependency) {
        String logText;
        if (DEBUG_LOG_HEIGHTS) {
            logText = "Dependency H: " + dependency.getHeight() + ", Child H: " + child.getHeight();
            Log.i(TAG, logText);
        }
        if (DEBUG_LOG_YPOS) {
            logText = "Dependency Y: " + dependency.getY() + ", Child Y: " + child.getY();
            Log.i(TAG, logText);
        }
    }
}
