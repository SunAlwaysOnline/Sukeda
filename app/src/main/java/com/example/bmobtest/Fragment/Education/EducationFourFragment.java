package com.example.bmobtest.Fragment.Education;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.EducationOptionalCourseAdapter;
import com.example.bmobtest.Bean.EducationOptionalCourse;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Education.EducationOptionalCourseListUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.example.bmobtest.Utils.Toast;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/24.
 */

public class EducationFourFragment extends Fragment {
    private View rootView;
    private String url;
    private RecyclerView rcv_education;
    private int all_number;
    private int shown = 0;
    private int current_page = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_item_education, container, false);
            rootView = v;
            url = getArguments().getString("url");
            initRecyclerView(v);
            initView(v);
        }
        return rootView;

    }

    public static EducationFourFragment getInstance(String url) {
        EducationFourFragment fragment = new EducationFourFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;

    }

    private void initView(View v) {
        EducationOptionalCourseListUtil.get_list(url, new EducationOptionalCourseListUtil.get_ListCall() {
            @Override
            public void success(final List<EducationOptionalCourse> list, final int all) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData(list);
                        all_number = all;
                        shown = shown + list.size();
                        current_page++;
                    }
                });

            }

            @Override
            public void fail() {
                RxToast.error("公选课简介获取失败！");
            }
        });
    }

    private void initRecyclerView(View v) {
        rcv_education = (RecyclerView) v.findViewById(R.id.rcv_education);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_education.setLayoutManager(manager);
    }

    private void initData(final List<EducationOptionalCourse> list) {
        final EducationOptionalCourseAdapter adapter = new EducationOptionalCourseAdapter(R.layout.item_education_optional_course, list);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                EducationWebFragment fragment = new EducationWebFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", list.get(position).getUrl());
                bundle.putSerializable("item",list.get(position));
                fragment.setArguments(bundle);
                transaction
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (shown >= all_number) {
                    adapter.loadMoreEnd();
                } else {
                    EducationOptionalCourseListUtil.get_list(get_true_url(), new EducationOptionalCourseListUtil.get_ListCall() {
                        @Override
                        public void success(final List<EducationOptionalCourse> list, int all) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.addData(list);
                                    adapter.loadMoreComplete();
                                    shown = shown + list.size();
                                    current_page++;
                                }
                            });
                        }

                        @Override
                        public void fail() {
                            RxToast.error("拉取公选课简介失败！");
                        }
                    });
                }
            }
        }, rcv_education);
        rcv_education.setAdapter(adapter);
    }

    private String get_true_url() {
        String true_url = "http://jwch.usts.edu.cn/newweb/news_more_gxk_new.asp?page=" + current_page + "&" + url.split("\\?")[1];
        return true_url;

    }
}
