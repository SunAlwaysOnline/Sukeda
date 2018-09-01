package com.example.bmobtest.Fragment.Knowledge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.KnowledgeAdapter;
import com.example.bmobtest.Bean.Knowledge;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Knowledge.KnowledgeUtil;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/17.
 */

public class KnowledgeListFragment extends Fragment {
    private String category_url;
    private RecyclerView rv_knowledge;
    //此标签下共有多少条知识,默认为1000条
    private int all_knowledge = 1000;
    //已经展示多少
    private int shown = 0;
    //当前所在页数
    private int current_page = 1;
    private View rootView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_knowledge_list, container, false);
            category_url = getArguments().getString("url");
            initView(v);
            rootView = v;
        }
        return rootView;
    }

    public static KnowledgeListFragment getInstance(String url) {
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        Bundle b = new Bundle();
        b.putString("url", url);
        fragment.setArguments(b);
        return fragment;
    }

    private void initView(View v) {
        rv_knowledge = (RecyclerView) v.findViewById(R.id.rv_knowledge);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_knowledge.setLayoutManager(manager);
        KnowledgeUtil.get_knowledge_list(category_url, new KnowledgeUtil.get_knowledge_listCall() {
            @Override
            public void success(final List<Knowledge> list) {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initRecyclerViewData(list);
                            shown = shown + list.size();
                            current_page++;
                        }
                    });
                } catch (Exception e) {
                    //用户快速切换导致空指针
                }

            }

            @Override
            public void fail() {
                RxToast.error("获取失败！");
            }
        });
    }


    private void initRecyclerViewData(final List<Knowledge> list) {
        final KnowledgeAdapter adapter = new KnowledgeAdapter(R.layout.item_knowledge, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KnowledgeFragment fragment = new KnowledgeFragment();
                Bundle b = new Bundle();
                b.putString("url", list.get(position).getUrl());
                b.putString("category", list.get(position).getCategory());
                fragment.setArguments(b);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (category_url.equals("http://news.daxues.cn/")) {
                    adapter.loadMoreEnd();
                    return;
                }
                if (shown >= all_knowledge) {
                    adapter.loadMoreEnd();
                } else {
                    KnowledgeUtil.get_knowledge_list(category_url + "index_" + current_page + ".html", new KnowledgeUtil.get_knowledge_listCall() {
                        @Override
                        public void success(final List<Knowledge> list) {
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
                            RxToast.error("获取失败！");
                        }
                    });

                }
            }
        }, rv_knowledge);
        rv_knowledge.setAdapter(adapter);
    }

}
