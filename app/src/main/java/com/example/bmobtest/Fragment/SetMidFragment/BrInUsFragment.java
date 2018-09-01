package com.example.bmobtest.Fragment.SetMidFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.example.bmobtest.Adapter.BrInUsPagerAdapter;

import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;


/**
 * Created by 戚春阳 on 2018/1/21.
 */

public class BrInUsFragment extends Fragment {
    private LinearLayout ly_back;
    private ViewPager vp;
    private PagerSlidingTabStrip tabs;
    private ImageView iv_brinus;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brinus, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());

        // 初始化ViewPager并且添加适配器
        vp = (ViewPager) v.findViewById(R.id.vp);
        //避免第二次进入时，WebView不加载页面的问题
        vp.setAdapter(new BrInUsPagerAdapter(getChildFragmentManager()));
        //向ViewPager绑定PagerSlidingTabStrip
        tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        tabs.setViewPager(vp);

        iv_brinus = (ImageView) v.findViewById(R.id.iv_brinus);
        Glide.with(getActivity()).load("http://www.usts.edu.cn/static/images/ggtu1.jpg").centerCrop().into(iv_brinus);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String url = "";
                if (position <= 3) {
                    url = "http://www.usts.edu.cn/static/images/ggtu" + (position + 1) + ".jpg";
                } else if (position == 4) {
                    url = "https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=2248b54a69061d95694b3f6a1a9d61b4/e4dde71190ef76c62ca637599a16fdfaaf516748.jpg";
                } else if (position == 5) {
                    url = "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=fd6657c4ce177f3e0439f45f11a650a2/1c950a7b02087bf4267f929bf5d3572c11dfcf35.jpg";
                } else if (position == 6) {
                    url = "https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=a758f2d78a0a19d8df0e8c575293e9ee/2f738bd4b31c8701ca6c19802d7f9e2f0608fffa.jpg";
                } else if (position == 7) {
                    url = "https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=c0a0fcb1d0ca7bcb6976cf7ddf600006/b2de9c82d158ccbfde2f70481ed8bc3eb1354135.jpg";
                }
                Glide.with(getActivity()).load(url).centerCrop().into(iv_brinus);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}