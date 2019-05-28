package com.gamalinda.android.poc.testing.collapsingtoolbar.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.gamalinda.android.poc.testing.collapsingtoolbar.R
import com.gamalinda.android.poc.testing.collapsingtoolbar.fragment.ConsolesMockListFragment
import com.gamalinda.android.poc.testing.collapsingtoolbar.fragment.GamesMockListFragment
import com.gamalinda.android.poc.testing.collapsingtoolbar.fragment.TextContentMockFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar?.title = ""
        setSupportActionBar(toolbar)

        populateFragmentList()

        val adapter = FragmentsAdapter(supportFragmentManager)
        adapter.setFragments(fragments)

        viewPager = findViewById(R.id.viewPager)
        viewPager?.adapter = adapter

        tabLayout = findViewById(R.id.content_tabs)
        tabLayout?.setupWithViewPager(viewPager)

        setTabIndicatorIcons()
    }

    private fun populateFragmentList() {
        fragments.clear()
        fragments.add(TextContentMockFragment())
        fragments.add(ConsolesMockListFragment())
        fragments.add(GamesMockListFragment())
    }

    private fun setTabIndicatorIcons() {
        //The tabs are fixed, let's just set the icons manually
        tabLayout?.let {
            it.getTabAt(0)?.setIcon(android.R.drawable.btn_star_big_off)
            it.getTabAt(1)?.setIcon(android.R.drawable.star_big_on)
            it.getTabAt(2)?.setIcon(android.R.drawable.btn_star_big_on)
        }
    }

    internal inner class FragmentsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var tabTitles = arrayOf("Long Text", "Consoles", "Games")
        private var fragments = mutableListOf<Fragment>()

        fun setFragments(fragments: List<Fragment>) {
            this.fragments.clear()
            this.fragments.addAll(fragments)
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        /*
        Use this to set tab titles
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
        */
    }

}
