package com.example.bmobtest.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.bmobtest.Bean.Knowledge;
import com.example.bmobtest.Bean.KnowledgeTab;
import com.example.bmobtest.Fragment.Knowledge.KnowledgeListFragment;
import com.example.bmobtest.Utils.Knowledge.KnowledgeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/17.
 */

public class KnowledgePagerAdapter extends FragmentPagerAdapter {
    private List<KnowledgeTab> tabList = new ArrayList<>();

    public KnowledgePagerAdapter(FragmentManager fm) {
        super(fm);
        delete_two();
    }

    //去除校花与经验标签
    public void delete_two() {
        for (int i = 0; i < KnowledgeUtil.tabList.size(); i++) {
            if (i != 10 && i != 13) {
                tabList.add(KnowledgeUtil.tabList.get(i));
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        KnowledgeListFragment fragment = KnowledgeListFragment.getInstance(tabList.get(position).getUrl());
        return fragment;
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position).getName();
    }


}
