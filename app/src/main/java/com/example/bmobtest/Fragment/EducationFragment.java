package com.example.bmobtest.Fragment;

import android.content.res.Resources;
import android.os.Bundle;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.bmobtest.Adapter.EducationPagerAdapter;
import com.example.bmobtest.Fragment.Education.EducationListFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Education.EducationBannerUtil;
import com.example.bmobtest.Utils.GlideImageLoader;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.example.bmobtest.View.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 戚春阳 on 2017/12/9.
 */

public class EducationFragment extends Fragment {
    private TextView tv_more_education;
    private Banner banner;
    private MyGridView gv_gonggao;
    private MyGridView gv_info_gongkai;
    private PagerSlidingTabStrip tab_education;
    private ViewPager vp_education;
    private View rootView;
    private STATE abl_state;

    //展开、折叠、中间
    private enum STATE {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    private AppBarLayout abl_education;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_education, container, false);
            rootView = v;
            initViews(v);
        }
        //避免快速点击教务时，隐藏底部按钮的问题
        //ShowOrHiddenUtil.show_home_bottom(getActivity());

        return rootView;
    }

    public void initViews(final View v) {
        EducationBannerUtil.get_banner_list(new EducationBannerUtil.get_bannerCall() {
            @Override
            public void success(final List<String> list) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initBanner(v, list);
                    }
                });
            }
        });
        initGrdView_gonggao(v);
        initGrdView_info_gongkai(v);
        initEducationLayout(v);
        initAppBarLayout(v);
        tv_more_education = (TextView) v.findViewById(R.id.tv_more_education);
        tv_more_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv_more_education.getText().toString();
                if (text.equals("回到顶部")) {
                    setAblExpanded(true);
                } else {
                    setAblExpanded(false);
                }
            }
        });


    }

    //初始化banner
    private void initBanner(View v, List<String> list) {
        banner = (Banner) v.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                com.example.bmobtest.Utils.Toast.show_info(getActivity(), "该图片不包括内容！");

            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    //初始化公告GridView
    private void initGrdView_gonggao(View v) {
        gv_gonggao = (MyGridView) v.findViewById(R.id.gv_gonggao);
        String img[] = new String[]{"choose_course", "notice_online", "education_dynamic", "document_download"};
        final String name[] = new String[]{"选课通知", "公告在线", "教务动态", "文档下载"};
        //生成动态数组，并且转入数据

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < img.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", getObject(img[i]));//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,//数据来源
                R.layout.item_grid,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.iv_grid, R.id.tv_grid});

        //添加并且显示
        gv_gonggao.setAdapter(saImageItems);
        //添加消息处理
        final String url[] = {
                "http://jwch.usts.edu.cn/newweb/news_more_new.asp?zlmid=1&lmid=32",
                "http://jwch.usts.edu.cn/newweb/news_more_new.asp?zlmid=1&lmid=61",
                "http://jwch.usts.edu.cn/newweb/news_more_new.asp?zlmid=1&lmid=62",
                "http://jwch.usts.edu.cn/newweb/news_more_new.asp?zlmid=1&lmid=63"};
        gv_gonggao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                EducationListFragment fragment = new EducationListFragment();
                Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        bundle.putString("url", url[0]);
                        bundle.putString("category", name[0]);
                        break;
                    case 1:
                        bundle.putString("url", url[1]);
                        bundle.putString("category", name[1]);
                        break;
                    case 2:
                        bundle.putString("url", url[2]);
                        bundle.putString("category", name[2]);
                        break;
                    case 3:
                        bundle.putString("url", url[3]);
                        bundle.putString("category", name[3]);
                        break;
                }
                fragment.setArguments(bundle);
                transaction
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    //初始化信息公开GridView
    private void initGrdView_info_gongkai(View v) {
        gv_info_gongkai = (MyGridView) v.findViewById(R.id.gv_info_gongkai);
        String img[] = new String[]{"article_browse", "calendar", "course_query", "external_exam"};
        final String name[] = new String[]{"发文一览", "学校年历", "课表查询", "对外考试"};
        //生成动态数组，并且转入数据

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < img.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", getObject(img[i]));//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,//数据来源
                R.layout.item_grid,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.iv_grid, R.id.tv_grid});

        //添加并且显示
        gv_info_gongkai.setAdapter(saImageItems);
        //添加消息处理
        final String url[] = {
                "http://jwch.usts.edu.cn/newweb/news_more_fw_new.asp?zlmid=3&lmid=11",
                "http://jwch.usts.edu.cn/newweb/news_more_new.asp?zlmid=3&lmid=67",
                "http://jwch.usts.edu.cn/newweb/news_more_new.asp?zlmid=3&lmid=69",
                "http://jwch.usts.edu.cn/newweb/news_more_new.asp?zlmid=3&lmid=85"};
        gv_info_gongkai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                EducationListFragment fragment = new EducationListFragment();
                Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        bundle.putString("url", url[0]);
                        bundle.putString("category", name[0]);
                        break;
                    case 1:
                        bundle.putString("url", url[1]);
                        bundle.putString("category", name[1]);
                        break;
                    case 2:
                        bundle.putString("url", url[2]);
                        bundle.putString("category", name[2]);
                        break;
                    case 3:
                        bundle.putString("url", url[3]);
                        bundle.putString("category", name[3]);
                        break;
                }
                fragment.setArguments(bundle);
                transaction
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    /**
     * 初始化PagerSlidingTabStrip以及ViewPager
     */
    private void initEducationLayout(View v) {
        vp_education = (ViewPager) v.findViewById(R.id.vp_education);
        tab_education = (PagerSlidingTabStrip) v.findViewById(R.id.tab_education);
        vp_education.setAdapter(new EducationPagerAdapter(getActivity().getSupportFragmentManager()));
        tab_education.setViewPager(vp_education);


    }

    private void initAppBarLayout(View v) {
        abl_education = (AppBarLayout) v.findViewById(R.id.abl_education);
        abl_education.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (abl_state != STATE.EXPANDED) {
                        abl_state = STATE.EXPANDED;//修改状态标记为展开
                        tv_more_education.setText("展开");
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (abl_state != STATE.COLLAPSED) {
                        abl_state = STATE.COLLAPSED;//修改状态标记为折叠
                        tv_more_education.setText("回到顶部");
                    }
                } else {
                    if (abl_state != STATE.INTERNEDIATE) {
                        abl_state = STATE.INTERNEDIATE;//修改状态标记为中间

                    }
                }
            }
        });

    }

    //设置展开或伸缩
    public void setAblExpanded(Boolean b) {
        if (b) {
            abl_education.setExpanded(true);
            abl_state = STATE.EXPANDED;
        } else {
            abl_education.setExpanded(false);
            abl_state = STATE.COLLAPSED;
        }

    }

    public Object getObject(String name) {
        Resources res = getResources();
        return res.getIdentifier(name, "drawable", getActivity().getPackageName());
    }


    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }
}
