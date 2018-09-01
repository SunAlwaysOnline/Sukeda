package com.example.bmobtest.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.example.bmobtest.Fragment.SetMidFragment.BrInUsFragment;
import com.example.bmobtest.Fragment.SetMinFragment.BrInUsItemFragment;

/**
 * Created by 戚春阳 on 2018/1/22.
 */

public class BrInUsPagerAdapter extends FragmentPagerAdapter {
    private String[] title = {"学校简介", "校区介绍", "现任领导", "校歌校徽","历史沿革","办学条件","文化传统","知名校友"};

    public BrInUsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BrInUsItemFragment fragment = null;
        switch (position) {
            case 0:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus1.html");
                break;
            case 1:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus2.html");
                break;
            case 2:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus3.html");
                break;
            case 3:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus4.html");
                break;
            case 4:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/history.html");
                break;
            case 5:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/runschool.html");
                break;
            case 6:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/culture.html");
                break;
            case 7:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/alumnus.html");
                break;


        }
        return fragment;

    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
