package com.gamalinda.android.poc.testing.collapsingtoolbar.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gamalinda.android.poc.testing.collapsingtoolbar.R;
import com.gamalinda.android.poc.testing.collapsingtoolbar.fragment.ConsolesMockListFragment;
import com.gamalinda.android.poc.testing.collapsingtoolbar.fragment.GamesMockListFragment;
import com.gamalinda.android.poc.testing.collapsingtoolbar.fragment.TextContentMockFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    List<Fragment> fragments = Collections.emptyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
        }
        setSupportActionBar(toolbar);

        populateFragmentList();

        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.setFragments(fragments);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
        }
    }

    private void populateFragmentList() {
        fragments = new ArrayList<>();
        fragments.add(new TextContentMockFragment());
        fragments.add(new ConsolesMockListFragment());
        fragments.add(new GamesMockListFragment());
    }

    class FragmentsAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments = Collections.emptyList();

        public FragmentsAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setFragments(List<Fragment> fragments) {
            this.fragments.clear();
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
