package com.gamalinda.android.poc.testing.collapsingtoolbar.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gamalinda.android.poc.testing.collapsingtoolbar.R

class CollapsingProfileHeaderView : LinearLayout {

    private var profileDrawable: Int = 0
    private var profileName: String? = null
    private var subtitle: String? = null
    private var misc: String? = null
    private var miscIcon: Int = 0
    private var profileNameTextSize: Int = 0
    private var profileSubtitleTextSize: Int = 0
    private var profileMiscTextSize: Int = 0

    private var profileImage: ImageView? = null
    private var profileNameTextView: TextView? = null
    private var subtitleTextView: TextView? = null
    private var miscTextView: TextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CollapsingProfileHeaderView,
                0, 0)

        try {
            profileDrawable = a.getResourceId(R.styleable.CollapsingProfileHeaderView_profileImage, 0)
            profileName = a.getString(R.styleable.CollapsingProfileHeaderView_profileName)
            subtitle = a.getString(R.styleable.CollapsingProfileHeaderView_profileSubtitle)
            misc = a.getString(R.styleable.CollapsingProfileHeaderView_profileMisc)
            miscIcon = a.getResourceId(R.styleable.CollapsingProfileHeaderView_profileMiscIcon, 0)
            profileNameTextSize = a.getResourceId(R.styleable.CollapsingProfileHeaderView_profileNameTextSizeSp, 20)
            profileSubtitleTextSize = a.getResourceId(R.styleable.CollapsingProfileHeaderView_profileSubtitleTextSizeSp, 12)
            profileMiscTextSize = a.getResourceId(R.styleable.CollapsingProfileHeaderView_profileMiscTextSizeSp, 15)
        } finally {
            a.recycle()
        }

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.view_collapsing_profile_header, this, true)

        loadViews()
        applyAttributes()
    }

    private fun loadViews() {
        profileImage = this.findViewById(R.id.profileImage)
        profileNameTextView = this.findViewById(R.id.profileName)
        subtitleTextView = this.findViewById(R.id.profileSubtitle)
        miscTextView = this.findViewById(R.id.profileMisc)
    }

    private fun applyAttributes() {
        profileImage?.setImageResource(profileDrawable)
        profileNameTextView?.text = profileName
        profileNameTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, profileNameTextSize.toFloat())
        subtitleTextView?.text = subtitle
        subtitleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, profileSubtitleTextSize.toFloat())
        miscTextView?.text = misc
        miscTextView?.setCompoundDrawablesWithIntrinsicBounds(miscIcon, 0, 0, 0)
        miscTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, profileMiscTextSize.toFloat())
    }

    fun getProfileDrawable(): Int {
        return profileDrawable
    }

    fun setProfileDrawable(profileDrawable: Int) {
        this.profileDrawable = profileDrawable
        profileImage!!.setImageResource(profileDrawable)
    }

    fun getProfileName(): String? {
        return profileName
    }

    fun setProfileName(profileName: String) {
        this.profileName = profileName
        profileNameTextView?.text = profileName
    }

    fun getSubtitle(): String? {
        return subtitle
    }

    fun setSubtitle(subtitle: String) {
        this.subtitle = subtitle
        subtitleTextView?.text = subtitle
    }

    fun getMisc(): String? {
        return misc
    }

    fun setMisc(misc: String) {
        this.misc = misc
        miscTextView?.text = misc
    }

    fun getMiscIcon(): Int {
        return miscIcon
    }

    fun setMiscIcon(miscIcon: Int) {
        this.miscIcon = miscIcon
        miscTextView?.setCompoundDrawablesWithIntrinsicBounds(miscIcon, 0, 0, 0)
    }
}
