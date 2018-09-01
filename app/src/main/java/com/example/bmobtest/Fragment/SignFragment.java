package com.example.bmobtest.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.bmobtest.Activity.AoLanActivity;
import com.example.bmobtest.Activity.LibraryActivity;

import com.example.bmobtest.Activity.MainActivity;
import com.example.bmobtest.Activity.NewsInfoActivity;
import com.example.bmobtest.Activity.NewsInformListActivity;
import com.example.bmobtest.Adapter.NewsInfromPagerAdapter;
import com.example.bmobtest.Bean.Sign;
import com.example.bmobtest.Bean.SlideShow;
import com.example.bmobtest.Fragment.Entry.EntryCategoryFragment;
import com.example.bmobtest.Fragment.SetMidFragment.BrInUsFragment;
import com.example.bmobtest.Fragment.SetMidFragment.CardFragment;
import com.example.bmobtest.Fragment.SetMidFragment.GradeFragment;
import com.example.bmobtest.Fragment.SetMidFragment.KwxfFragment;
import com.example.bmobtest.Fragment.SetMidFragment.NewsInformListFragment;

import com.example.bmobtest.Fragment.SetMidFragment.PhoneFragment;
import com.example.bmobtest.Fragment.SetMidFragment.SmallUtilFragment;
import com.example.bmobtest.Fragment.Volunteer.VolunteerLoginFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.CallBackListener;
import com.example.bmobtest.Utils.DateUtils;
import com.example.bmobtest.Utils.FunctionStateUtil;
import com.example.bmobtest.Utils.GlideImageLoader;
import com.example.bmobtest.Utils.ResoruceObjectUtil;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.example.bmobtest.Utils.SlideShowUtil;
import com.example.bmobtest.Utils.Toast;
import com.vondear.rxtools.view.RxToast;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 戚春阳 on 2017/12/9.
 */

public class SignFragment extends Fragment {
    private String[] name = {
            "学校概况",
            "一卡通",
            "图书馆",
            "成绩查询",
            "学分查询",
            "奥蓝系统",
            "小工具",
            "志愿者时间",
            "办公电话",
            "苏科词条"
    };
    private GridView gridView;

    /**
     * 未格式化的日期
     */
    private Date date;
    /**
     * 格式化后的日期,包含年月日时分秒
     */
    private String currentDate0;
    /**
     * 格式化后的日期,包含年月日
     */
    private String currentDate1;
    private TextView tv_title;

    private List<SlideShow> mslideShowList;
    //缓存Fragment View

    private Banner banner;

    private PagerSlidingTabStrip news_inform_tab;
    private ViewPager news_inform_vp;

    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复加载
        View v = null;
        if (rootView == null) {
            v = inflater.inflate(R.layout.fragment_sign, container, false);
            rootView = v;
            initViews(v);
            initGrdView(v);
            initNewsInformLayout(v);

        }


        //缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        //ViewGroup parent = (ViewGroup) rootView.getParent();
        //if (parent != null) {
        //  parent.removeView(rootView);
        //Log.e("tag","1224234324");
        // }
        return rootView;
    }

    private void initViews(View v) {
        get_info(v);

    }

    private void initGrdView(View v) {
        gridView = (GridView) v.findViewById(R.id.gv);

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", getObject("university"));//添加图像资源的ID
        map.put("ItemText", name[0]);//按序号做ItemText
        lstImageItem.add(map);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("ItemImage", getObject("ecard"));//添加图像资源的ID
        map1.put("ItemText", name[1]);//按序号做ItemText
        lstImageItem.add(map1);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("ItemImage", getObject("library"));//添加图像资源的ID
        map2.put("ItemText", name[2]);//按序号做ItemText
        lstImageItem.add(map2);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("ItemImage", getObject("grade"));//添加图像资源的ID
        map3.put("ItemText", name[3]);//按序号做ItemText
        lstImageItem.add(map3);
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("ItemImage", getObject("credit"));//添加图像资源的ID
        map4.put("ItemText", name[4]);//按序号做ItemText
        lstImageItem.add(map4);
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        map5.put("ItemImage", getObject("aolan"));//添加图像资源的ID
        map5.put("ItemText", name[5]);//按序号做ItemText
        lstImageItem.add(map5);
        HashMap<String, Object> map6 = new HashMap<String, Object>();
        map6.put("ItemImage", getObject("util"));//添加图像资源的ID
        map6.put("ItemText", name[6]);//按序号做ItemText
        lstImageItem.add(map6);
        HashMap<String, Object> map7 = new HashMap<String, Object>();
        map7.put("ItemImage", getObject("volunteer"));//添加图像资源的ID
        map7.put("ItemText", name[7]);//按序号做ItemText
        lstImageItem.add(map7);
        HashMap<String, Object> map8 = new HashMap<String, Object>();
        map8.put("ItemImage", getObject("phone"));//添加图像资源的ID
        map8.put("ItemText", name[8]);//按序号做ItemText
        lstImageItem.add(map8);
        HashMap<String, Object> map9 = new HashMap<String, Object>();
        map9.put("ItemImage", getObject("entry"));//添加图像资源的ID
        map9.put("ItemText", name[9]);//按序号做ItemText
        lstImageItem.add(map9);
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,//数据来源
                R.layout.item_grid,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.iv_grid, R.id.tv_grid});

        //添加并且显示
        gridView.setAdapter(saImageItems);
        //添加消息处理
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                        fragmentTransaction.replace(R.id.fl_content, new BrInUsFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                        fragmentTransaction1.replace(R.id.fl_content, new CardFragment());
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), LibraryActivity.class));
                        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case 3:
                        FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                        fragmentTransaction2.replace(R.id.fl_content, new GradeFragment());
                        fragmentTransaction2.addToBackStack(null);
                        fragmentTransaction2.commit();
                        break;
                    case 4:
                        FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                        fragmentTransaction3.replace(R.id.fl_content, new KwxfFragment());
                        fragmentTransaction3.addToBackStack(null);
                        fragmentTransaction3.commit();
                        break;
                    case 5:
                        getActivity().startActivity(new Intent(getActivity(), AoLanActivity.class));
                        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case 6:
                        FragmentManager fragmentManager4 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                        fragmentTransaction4.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                        fragmentTransaction4.replace(R.id.fl_content, new SmallUtilFragment());
                        fragmentTransaction4.addToBackStack(null);
                        fragmentTransaction4.commit();
                        break;
                    case 7:
                        FragmentManager fragmentManager5 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction5 = fragmentManager5.beginTransaction();
                        fragmentTransaction5.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                        fragmentTransaction5.replace(R.id.fl_content, new VolunteerLoginFragment());
                        fragmentTransaction5.addToBackStack(null);
                        fragmentTransaction5.commit();
                        break;
                    case 8:
                        FragmentManager fragmentManager6 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction6 = fragmentManager6.beginTransaction();
                        fragmentTransaction6.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                        fragmentTransaction6.replace(R.id.fl_content, new PhoneFragment());
                        fragmentTransaction6.addToBackStack(null);
                        fragmentTransaction6.commit();
                        break;
                    case 9:
                        if (FunctionStateUtil.Entry) {
                            FragmentManager fragmentManager7 = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction7 = fragmentManager7.beginTransaction();
                            fragmentTransaction7.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                            fragmentTransaction7.replace(R.id.fl_content, new EntryCategoryFragment());
                            fragmentTransaction7.addToBackStack(null);
                            fragmentTransaction7.commit();
                        } else {
                            RxToast.info("苏科词条正在调整中！");
                        }
                        break;

                }
            }
        });
    }

    public Object getObject(String name) {
        Resources res = getResources();
        return res.getIdentifier(name, "drawable", getActivity().getPackageName());
    }

    private void get_info(final View v) {
        //获取轮播图的图片地址，标题与详细内容的url
        SlideShowUtil.get_slideShow_info(new CallBackListener() {
            @Override
            public void onSuccess(List<SlideShow> list) {
                mslideShowList = new ArrayList<>();
                mslideShowList = SlideShowUtil.slideShowsList;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initBanner(v);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.show(getActivity(), "加载失败！", 1);
                    }
                });
            }
        });
    }


    private void initBanner(View v) {
        banner = (Banner) v.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> img_url = new ArrayList<>();
        List<String> title = new ArrayList<>();
        final List<String> detail_url = new ArrayList<>();
        for (SlideShow slideShow : mslideShowList) {
            img_url.add(slideShow.getImg_url());
            title.add(slideShow.getTitle());
            detail_url.add(slideShow.getDetail_url());
        }
        banner.setImages(img_url);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(title);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //此时需要暂停轮播。否则返回时与当前点击的轮播页面不一致
                banner.stopAutoPlay();
                Intent i = new Intent(getActivity(), NewsInfoActivity.class);
                i.putExtra("url", detail_url.get(position));
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            //返回时，开启轮播
            banner.startAutoPlay();
        }
    }

    /**
     * 初始化PagerSlidingTabStrip以及ViewPager
     */
    private void initNewsInformLayout(View v) {
        news_inform_vp = (ViewPager) v.findViewById(R.id.news_inform_vp);
        news_inform_tab = (PagerSlidingTabStrip) v.findViewById(R.id.news_inform_tab);
        news_inform_vp.setAdapter(new NewsInfromPagerAdapter(getChildFragmentManager()));
        news_inform_tab.setViewPager(news_inform_vp);


        TextView tv_more_news_inform = (TextView) v.findViewById(R.id.tv_more_news_inform);
        tv_more_news_inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogUtil.showProgressDialog(getActivity(), "正在拉取新闻...");
                startActivity(new Intent(getActivity(), NewsInformListActivity.class));
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }
}
