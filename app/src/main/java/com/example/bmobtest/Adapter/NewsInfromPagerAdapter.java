package com.example.bmobtest.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bmobtest.Fragment.SetMidFragment.NewsInformFourFragment;

/**
 * Created by 戚春阳 on 2018/1/26.
 */

public class NewsInfromPagerAdapter extends FragmentPagerAdapter {
    private String title[] = {"苏科要闻", "通知公告", "学术动态", "校园快讯"};

    public NewsInfromPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NewsInformFourFragment fragment = null;
        switch (position) {
            case 0:
                fragment = NewsInformFourFragment.getInstance("http://news.usts.edu.cn/news/news_more.asp?lm2=1");
                break;
            case 1:
                fragment = NewsInformFourFragment.getInstance("http://notify.usts.edu.cn/news/news_more.asp?lm2=1");
                break;
            case 2:
                fragment = NewsInformFourFragment.getInstance("http://notify.usts.edu.cn/news/news_more.asp?lm2=2");
                break;
            case 3:
                fragment = NewsInformFourFragment.getInstance("http://news.usts.edu.cn/news/news_more.asp?lm2=2");
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
