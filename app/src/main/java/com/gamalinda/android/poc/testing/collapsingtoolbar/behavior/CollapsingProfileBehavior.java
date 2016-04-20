package com.gamalinda.android.poc.testing.collapsingtoolbar.behavior;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private View profileTextContainer;
    private View profileName;
    private View profileSubtitle;
    private View profileMisc;

    private Point windowSize;
    private int appBarHeight;
    private int profileImageSizeSmall;
    private int profileImageSizeBig;
    private int profileImageMaxMargin;
    private int toolBarHeight;
    private int profileTextContainerMaxHeight;
    private int profileNameHeight;
    private int profileNameMaxMargin;

    private float normalizedRange;

    public CollapsingProfileBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        normalizedRange = 0f;
        profileImageSizeSmall = (int) context.getResources().getDimension(R.dimen.profile_small_size);
        profileImageSizeBig = (int) context.getResources().getDimension(R.dimen.profile_big_size);
        profileImageMaxMargin = (int) context.getResources().getDimension(R.dimen.profile_image_margin_max);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        initialize(child, dependency);
        return dependency instanceof AppBarLayout;
    }

    private void initialize(LinearLayout child, View dependency) {
        windowSize = getDisplaySize();
        appBar = dependency;
        appBarHeight = appBar.getHeight();
        headerProfile = child;
        profileImage = headerProfile.findViewById(R.id.profileImage);
        profileImage.setPivotX(0);
        profileImage.setPivotY(0);
        profileTextContainer = headerProfile.findViewById(R.id.profileTextContainer);
        profileTextContainer.setPivotX(0);
        profileTextContainer.setPivotY(0);
        profileName = profileTextContainer.findViewById(R.id.profileName);
        profileNameHeight = profileName.getHeight();
        profileSubtitle = profileTextContainer.findViewById(R.id.profileSubtitle);
        profileMisc = profileTextContainer.findViewById(R.id.profileMisc);
        int profileSubtitleMaxHeight = calculateMaxHeightFromTextView((TextView) profileSubtitle);
        int profileMiscMaxHeight = calculateMaxHeightFromTextView((TextView) profileMisc);
        profileTextContainerMaxHeight = profileNameHeight + profileSubtitleMaxHeight + profileMiscMaxHeight;
    }

    private Point getDisplaySize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return size;
    }

    private int calculateMaxHeightFromTextView(TextView textView) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(windowSize.x, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        toolBarHeight = (appBar.findViewById(R.id.toolbar)).getHeight();
        updateNormalizedRange();
        updateOffset();
        return true;
    }

    private void updateNormalizedRange() {
        float dividend = appBar.getY();
        float divisor = toolBarHeight - appBarHeight;
        normalizedRange = dividend / divisor;
    }

    private void updateOffset() {
        updateHeaderProfileOffset();
        updateProfileImageSize();
        updateProfileImageMargins();
        updateProfileTextContainerHeight();
        updateProfileTextMargin();
        updateSubtitleAndMiscAlpha();
    }

    private void updateHeaderProfileOffset() {
        headerProfile.setY(appBar.getY());
        logValues();
    }

    private void updateProfileImageSize() {
        float x = appBar.getY();
        float m = calculateSlopeForImageSizeOffset();
        float b = profileImageSizeBig;
        float y = getIntercept(m, x, b);

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

        return slopeCalculator(x1, y1, x2, y2);
    }

    private void updateProfileImageMargins() {
        float x = appBar.getY();
        float m = calculateSlopeForImageMargins();
        float b = profileImageMaxMargin;
        float y = getIntercept(m, x, b);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) profileImage.getLayoutParams();
        lp.bottomMargin = (int) y;
        lp.leftMargin = (int) y;
        lp.rightMargin = (int) y;
        profileImage.setLayoutParams(lp);
    }

    private float calculateSlopeForImageMargins() {
        int x1 = 0;
        int x2 = toolBarHeight - appBarHeight;
        int y1 = profileImageMaxMargin;
        int y2 = calculateProfileImageSmallMargin();

        return slopeCalculator(x1, y1, x2, y2);
    }

    private int calculateProfileImageSmallMargin() {
        int halfToolbarHeight = toolBarHeight / 2;
        int halfProfileImageSmall = profileImageSizeSmall / 2;
        return halfToolbarHeight - halfProfileImageSmall;
    }

    private void updateProfileTextContainerHeight() {
        float x = appBar.getY();
        float m = calculateSlopeForProfileTextContainerOffset();
        float b = profileTextContainerMaxHeight;
        float y = getIntercept(m, x, b);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) profileTextContainer.getLayoutParams();
        lp.height = (int) y;
        profileTextContainer.setLayoutParams(lp);
    }

    private float calculateSlopeForProfileTextContainerOffset() {
        int x1 = 0;
        int x2 = toolBarHeight - appBarHeight;
        int y1 = profileTextContainerMaxHeight;
        int y2 = toolBarHeight;

        return slopeCalculator(x1, y1, x2, y2);
    }

    private void updateProfileTextMargin() {
        float x = appBar.getY();
        float m = calculateSlopeForProfileTextMarginOffset();
        float b = 0;
        float y = getIntercept(m, x, b);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) profileName.getLayoutParams();
        lp.topMargin = (int) y;
//        lp.bottomMargin = (int) y;
        profileName.setLayoutParams(lp);
    }

    private float calculateSlopeForProfileTextMarginOffset() {
        int x1 = 0;
        int x2 = toolBarHeight - appBarHeight;
        int y1 = 0;
        int y2 = calculateProfileTextMargin();

        return slopeCalculator(x1, y1, x2, y2);
    }

    private int calculateProfileTextMargin() {
        int halfToolbarHeight = toolBarHeight / 2;
        int halfProfileTextHeight = profileNameHeight / 2;
        return halfToolbarHeight - halfProfileTextHeight;
    }

    private void updateSubtitleAndMiscAlpha() {
        float x = appBar.getY();
        float m = 1f / (appBarHeight - toolBarHeight);
        float b = 1f;
        float y = getIntercept(m, x, b);

        profileSubtitle.setAlpha(y);
        profileMisc.setAlpha(y);
    }

    private float slopeCalculator(final float x1, final float y1, final float x2, final float y2) {
        //slope = (y2 - y1) over (x2 - x1)
        return (y2 - y1) / (x2 - x1);
    }

    private float getIntercept(float m, float x, float b) {
        return m * x + b;
    }

    private float getUpdatedInterpolatedValue(float openSizeTarget, float closedSizeTarget) {
        return getIntercept(closedSizeTarget - openSizeTarget,normalizedRange,openSizeTarget);
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
