package com.example.bmobtest.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bmobtest.Fragment.Education.EducationFourFragment;
import com.example.bmobtest.Utils.Education.EducationOptionalCourseListUtil;

/**
 * Created by 戚春阳 on 2018/1/26.
 */

public class EducationPagerAdapter extends FragmentPagerAdapter {
    private String title[] = {"石湖校区", "江枫校区", "天平校区"};

    public EducationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        EducationFourFragment fragment = null;
        switch (position) {
            case 0:
                fragment = EducationFourFragment.getInstance(EducationOptionalCourseListUtil.url[0]);
                break;
            case 1:
                fragment = EducationFourFragment.getInstance(EducationOptionalCourseListUtil.url[1]);
                break;
            case 2:
                fragment = EducationFourFragment.getInstance(EducationOptionalCourseListUtil.url[2]);
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
