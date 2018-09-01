package com.example.bmobtest.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.astuetz.PagerSlidingTabStrip;
import com.example.bmobtest.Adapter.KnowledgePagerAdapter;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Knowledge.KnowledgeUtil;


/**
 * Created by 戚春阳 on 2017/12/9.
 */

public class CommunityFragment extends Fragment {
    private PagerSlidingTabStrip psts_knowledge;
    private ViewPager vp_knowledge;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_community, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        initKnowledgeLayout(v);
    }


    private void initKnowledgeLayout(View v) {
        psts_knowledge = (PagerSlidingTabStrip) v.findViewById(R.id.psts_knowledge);
        //设置标签文字的大小
        psts_knowledge.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 17, getResources().getDisplayMetrics()));
        //设置字体
        psts_knowledge.setTypeface(null, Typeface.NORMAL);
        //设置字体颜色
        psts_knowledge.setTextColor(getResources().getColor(R.color.black));
        vp_knowledge = (ViewPager) v.findViewById(R.id.vp_knowledge);
        KnowledgeUtil.get_all_tabs(new KnowledgeUtil.get_tabsCall() {
            @Override
            public void success() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                vp_knowledge.setAdapter(new KnowledgePagerAdapter(getChildFragmentManager()));
                                psts_knowledge.setViewPager(vp_knowledge);
                            } catch (IllegalStateException e) {
                                //用户快速切换导致fragment还没有附着到activity上
                            }
                        }
                    });
                } catch (Exception e) {
                    //用户快速切换导致空指针
                }

            }
        });
    }
}
