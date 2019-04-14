package com.example.bmobtest.Fragment.Education;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.EducationAdapter;
import com.example.bmobtest.Bean.Education;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Education.EducationListUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/24.
 */

public class EducationListFragment extends Fragment {
    private TextView tv_info;
    private LinearLayout ly_back;
    private RecyclerView rcv_education_list;
    private int current_page = 1;
    private int shown = 0;
    private int all_number = 0;
    private String current_url;
    private String category;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_education_list, container, false);
            current_url = getArguments().getString("url");
            category = getArguments().getString("category");
            rootView = v;
            initView(v);
        }
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        return rootView;
    }

    private void initView(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        tv_info.setText("" + category);
        initRecyclerView(v);

        EducationListUtil.get_list(get_true_url(), new EducationListUtil.get_listCall() {
            @Override
            public void success(final List<Education> list, final int all) {
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
                RxToast.error("获取失败！");
            }
        });

    }

    private void initRecyclerView(View v) {
        rcv_education_list = (RecyclerView) v.findViewById(R.id.rcv_education_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_education_list.setLayoutManager(manager);
    }

    private void initData(final List<Education> list) {
        final EducationAdapter adapter = new EducationAdapter(R.layout.item_education, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                EducationWebFragment fragment = new EducationWebFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", list.get(position).getUrl());
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
                    //再次获取数据
                    //此处应该是判断网络情况，默认良好

                    EducationListUtil.get_list(get_true_url(), new EducationListUtil.get_listCall() {
                        @Override
                        public void success(final List<Education> list, int all) {
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
                            adapter.loadMoreFail();
                        }
                    });

                }

            }
        }, rcv_education_list);
        rcv_education_list.setAdapter(adapter);

    }

    private String get_true_url() {
        String true_url = "";
        if (current_page == 1) {
            true_url = current_url;
        } else {
            //总页数,每页18条信息
            int all_page = (all_number % 18) == 0 ? (all_number / 18) : (all_number / 18 + 1);
            String suffix_url = "/" + (all_page + 1 - current_page) + ".htm";

            //公告动态模块
            if (current_url.contains("/xszl/xxswzn")) {
                true_url = "http://jwch.usts.edu.cn/xszl/xxswzn" + suffix_url;
            } else if (current_url.contains("/xszl/xjgl")) {
                true_url = "http://jwch.usts.edu.cn/xszl/xjgl" + suffix_url;
            } else if (current_url.contains("/xszl/ksxz")) {
                true_url = "http://jwch.usts.edu.cn/xszl/ksxz" + suffix_url;
            } else if (current_url.contains("/xszl/xsbgxz")) {
                true_url = "http://jwch.usts.edu.cn/xszl/xsbgxz" + suffix_url;
            }


            //信息公开模块
            else if (current_url.contains("/fwzn/fwzn")) {
                true_url = "http://jwch.usts.edu.cn/fwzn/fwzn" + suffix_url;
            } else if (current_url.contains("/fwzn/xnxl")) {
                true_url = "http://jwch.usts.edu.cn/fwzn/xnxl" + suffix_url;
            } else if (current_url.contains("/fwzn/kbcx")) {
                true_url = "http://jwch.usts.edu.cn/fwzn/kbcx" + suffix_url;
            } else if (current_url.contains("dwksxx")) {
                true_url = "http://jwch.usts.edu.cn/fwzn/dwksxx" + suffix_url;
            }

        }
        Log.e("true_page", true_url);
        return true_url;
    }
}
