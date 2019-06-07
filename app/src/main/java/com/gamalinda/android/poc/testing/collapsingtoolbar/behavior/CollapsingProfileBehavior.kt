package com.gamalinda.android.poc.testing.collapsingtoolbar.behavior

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gamalinda.android.poc.testing.collapsingtoolbar.R
import com.google.android.material.appbar.AppBarLayout

class CollapsingProfileBehavior(private val context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<LinearLayout>(context, attrs) {

    private lateinit var appBar: View
    private lateinit var headerProfile: View
    private lateinit var profileImage: View
    private lateinit var profileTextContainer: View
    private lateinit var profileName: View
    private lateinit var profileSubtitle: View
    private lateinit var profileMisc: View

    private lateinit var windowSize: Point
    private var appBarHeight: Int = 0
    private val profileImageSizeSmall: Int
    private val profileImageSizeBig: Int
    private val profileImageMaxMargin: Int
    private var toolBarHeight: Int = 0
    private var profileTextContainerMaxHeight: Int = 0
    private var profileNameHeight: Int = 0

    private var normalizedRange: Float = 0.toFloat()

    private val displaySize: Point
        get() {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size
        }

    init {
        normalizedRange = 0f
        profileImageSizeSmall = context.resources.getDimension(R.dimen.profile_small_size).toInt()
        profileImageSizeBig = context.resources.getDimension(R.dimen.profile_big_size).toInt()
        profileImageMaxMargin = context.resources.getDimension(R.dimen.profile_image_margin_max).toInt()
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        val isDependencyAnAppBar = dependency is AppBarLayout
        if (isDependencyAnAppBar) {
            initialize(child, dependency)
        }
        return isDependencyAnAppBar
    }

    private fun initialize(child: LinearLayout, dependency: View) {
        windowSize = displaySize
        appBar = dependency
        appBarHeight = appBar.height
        headerProfile = child
        profileImage = headerProfile.findViewById(R.id.profileImage)
        profileImage.pivotX = 0f
        profileImage.pivotY = 0f
        profileTextContainer = headerProfile.findViewById(R.id.profileTextContainer)
        profileTextContainer.pivotX = 0f
        profileTextContainer.pivotY = 0f
        profileName = profileTextContainer.findViewById(R.id.profileName)
        profileNameHeight = profileName.height
        profileSubtitle = profileTextContainer.findViewById(R.id.profileSubtitle)
        profileMisc = profileTextContainer.findViewById(R.id.profileMisc)
        val profileSubtitleMaxHeight = calculateMaxHeightFromTextView((profileSubtitle as TextView?)!!)
        val profileMiscMaxHeight = calculateMaxHeightFromTextView((profileMisc as TextView?)!!)
        profileTextContainerMaxHeight = profileNameHeight + profileSubtitleMaxHeight + profileMiscMaxHeight
    }

    private fun calculateMaxHeightFromTextView(textView: TextView): Int {
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(windowSize.x, View.MeasureSpec.AT_MOST)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        textView.measure(widthMeasureSpec, heightMeasureSpec)
        return textView.measuredHeight
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        val isDependencyAnAppBar = dependency is AppBarLayout
        if (isDependencyAnAppBar) {
            toolBarHeight = appBar.findViewById<View>(R.id.toolbar).height
            updateNormalizedRange()
            updateOffset()
        }
        return isDependencyAnAppBar
    }

    private fun updateNormalizedRange() {
        val abl = appBar as AppBarLayout
        normalizedRange = normalize(
                currentValue = appBar.y + abl.totalScrollRange,
                minValue = 0f,
                maxValue = abl.totalScrollRange.toFloat()
        )

        normalizedRange = 1f - normalizedRange
    }

    private fun normalize(currentValue: Float, minValue: Float, maxValue: Float): Float {
        val dividend = currentValue - minValue
        val divisor = maxValue - minValue
        return dividend / divisor
    }

    private fun updateOffset() {
        updateHeaderProfileOffset()
        updateProfileImageSize()
        updateProfileImageMargins()
        updateProfileTextContainerHeight()
        updateProfileTextMargin()
        updateSubtitleAndMiscAlpha()
    }

    private fun updateHeaderProfileOffset() {
        headerProfile.y = appBar.y
    }

    private fun updateProfileImageSize() {
        val updatedValue = getUpdatedInterpolatedValue(profileImageSizeBig.toFloat(), profileImageSizeSmall.toFloat()).toInt()

        val lp = profileImage.layoutParams as LinearLayout.LayoutParams
        lp.height = updatedValue
        lp.width = updatedValue
        profileImage.layoutParams = lp
    }

    private fun updateProfileImageMargins() {
        val targetOpenAppbarValue = calculateProfileImageSmallMargin().toFloat()
        val updatedValue = getUpdatedInterpolatedValue(profileImageMaxMargin.toFloat(), targetOpenAppbarValue).toInt()

        val lp = profileImage.layoutParams as LinearLayout.LayoutParams
        lp.bottomMargin = updatedValue
        lp.leftMargin = updatedValue
        lp.rightMargin = updatedValue
        profileImage.layoutParams = lp
    }

    private fun calculateProfileImageSmallMargin(): Int {
        val halfToolbarHeight = toolBarHeight / 2
        val halfProfileImageSmall = profileImageSizeSmall / 2
        return halfToolbarHeight - halfProfileImageSmall
    }

    private fun updateProfileTextContainerHeight() {
        val updatedValue = getUpdatedInterpolatedValue(profileTextContainerMaxHeight.toFloat(), toolBarHeight.toFloat()).toInt()

        val lp = profileTextContainer.layoutParams as LinearLayout.LayoutParams
        lp.height = updatedValue
        profileTextContainer.layoutParams = lp
    }

    private fun updateProfileTextMargin() {
        val targetOpenAppbarValue = calculateProfileTextMargin().toFloat()
        val updatedValue = getUpdatedInterpolatedValue(0f, targetOpenAppbarValue).toInt()

        val lp = profileName.layoutParams as RelativeLayout.LayoutParams
        lp.topMargin = updatedValue
        profileName.layoutParams = lp
    }

    private fun calculateProfileTextMargin(): Int {
        val halfToolbarHeight = toolBarHeight / 2
        val halfProfileTextHeight = profileNameHeight / 2
        return halfToolbarHeight - halfProfileTextHeight
    }

    private fun updateSubtitleAndMiscAlpha() {
        val updatedValue = getUpdatedInterpolatedValue(1f, 0f)
        val poweredValue = Math.pow(updatedValue.toDouble(), 6.0).toFloat()

        profileSubtitle.alpha = poweredValue
        profileMisc.alpha = poweredValue
    }

    private fun getIntercept(m: Float, x: Float, b: Float): Float {
        return m * x + b
    }

    private fun getUpdatedInterpolatedValue(openSizeTarget: Float, closedSizeTarget: Float): Float {
        return getIntercept(closedSizeTarget - openSizeTarget, normalizedRange, openSizeTarget)
    }
}
