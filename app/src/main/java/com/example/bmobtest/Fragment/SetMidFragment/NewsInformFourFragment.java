package com.example.bmobtest.Fragment.SetMidFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Activity.NewsInfoActivity;
import com.example.bmobtest.Adapter.NewsInformAdapter;
import com.example.bmobtest.Bean.NewsInform;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.NewsInformUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 戚春阳 on 2018/1/26.
 */

public class NewsInformFourFragment extends Fragment {
    private String url;
    private RecyclerView recyclerView;
    //virepager里的fragment切换时，将会导致前几次创建fagment销毁，fragment的生命周期将会返回调用，
    //但其成员变量并没有销毁，将服务器获取的数据存储到这些变量中，不为空，则直接用\
    private View rootView;
    private List<NewsInform> rootList;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        if (rootView == null) {
            v = inflater.inflate(R.layout.fragment_item_news_inform, container, false);
            rootView = v;
            url = getArguments().getString("url");
            initRecyclerView(v);
            get_four();
            Log.e("tag", "start");
        }
        return rootView;
    }

    public static NewsInformFourFragment getInstance(String url) {
        NewsInformFourFragment fragment = new NewsInformFourFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }


    private void get_four() {
        NewsInformUtil.get_list(url, new NewsInformUtil.NewsinformCall() {
            @Override
            public void success(final List<NewsInform> list) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<NewsInform> newsInforms = new ArrayList<NewsInform>();
                        for (int i = 0; i < 4; i++) {
                            newsInforms.add(list.get(i));
                        }
                        rootList = newsInforms;
                        initDatas(newsInforms);
                    }
                });

            }
        });

    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.rcv_news_inform);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);

    }

    private void initDatas(List<NewsInform> list) {
        NewsInformAdapter adapter = new NewsInformAdapter(R.layout.item_news_inform, list);
        //添加动画
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //该适配器提供了5种动画效果（渐显、缩放、从下到上，从左到右、从右到左）
        // ALPHAIN SCALEIN SLIDEIN_BOTTOM SLIDEIN_LEFT SLIDEIN_RIGHT

        //设置重复执行动画
        adapter.isFirstOnly(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(getActivity(), NewsInfoActivity.class);
                i.putExtra("url", rootList.get(position).getUrl());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

//
//        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                adapter.loadMoreEnd();
//            }
//        });

        recyclerView.setAdapter(adapter);
    }

}
