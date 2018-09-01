package com.example.bmobtest.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.NewsInformAdapter;
import com.example.bmobtest.Bean.Book;
import com.example.bmobtest.Bean.NewsInform;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.BookUtil;
import com.example.bmobtest.Utils.GlideImageLoader;

import com.example.bmobtest.Utils.NewsInformListUtil;

import com.example.bmobtest.Utils.ShowDialogUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsInformListActivity extends AppCompatActivity {
    private LinearLayout ly_back;
    private Banner banner;
    private GridView gridView;
    private RecyclerView recyclerView;
    private STATE abl_state;

    //展开、折叠、中间
    private enum STATE {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    private AppBarLayout abl_news_inform_list;
    private String categories_url[] = {
            "http://news.usts.edu.cn/news/news_more.asp?lm2=1",
            "http://notify.usts.edu.cn/news/news_more.asp?lm2=1",
            "http://notify.usts.edu.cn/news/news_more.asp?lm2=2",
            "http://news.usts.edu.cn/news/news_more.asp?lm2=2"};

    //设置各类新闻最多400条
    private int all_numbers = 400;
    //当前已经显示的新闻条数
    private int shown = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTop();
        setContentView(R.layout.activity_news_inform_list);
        initViews();
    }

    private void hideTop() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initViews() {
        ly_back = (LinearLayout) findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initGridView();
        initBannerData();
        initCollapsingToolBarlayout();
        initRecyclerView();


    }


    private void initBanner(List<NewsInform> list_banner) {
        banner = (Banner) findViewById(R.id.banner_news_inform_list);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> title = new ArrayList<>();
        final List<String> url = new ArrayList<>();
        List<String> pic_url = new ArrayList<>();
        for (int i = 0; i < list_banner.size(); i++) {
            title.add(list_banner.get(i).getTitle());
            url.add(list_banner.get(i).getUrl());
            pic_url.add(list_banner.get(i).getFirst_pic_url());
        }
        banner.setImages(pic_url);
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
                Intent i = new Intent(NewsInformListActivity.this, NewsInfoActivity.class);
                i.putExtra("url", url.get(position));
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    private void initBannerData() {
        NewsInformListUtil.get_news_inform_pic_url(new NewsInformListUtil.two_categories_listCall() {
            @Override
            public void success() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.closeProgressDialog();
                    }
                });
                initBanner(NewsInformListUtil.merge_have_pic_list());
            }
        });
    }

    private void initGridView() {
        gridView = (GridView) findViewById(R.id.gv_news_inform_list);
        String text[] = {"苏科要闻", "通知公告", "学术动态", "校园快讯"};
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("image", getObject("important"));
        map.put("text", text[0]);
        arrayList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("image", getObject("inform"));
        map1.put("text", text[1]);
        arrayList.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("image", getObject("academic"));
        map2.put("text", text[2]);
        arrayList.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("image", getObject("message"));
        map3.put("text", text[3]);
        arrayList.add(map3);

        SimpleAdapter adapter = new SimpleAdapter(this,
                arrayList,
                R.layout.item_grid,
                new String[]{"image", "text"},
                new int[]{R.id.iv_grid, R.id.tv_grid});

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        NewsInformListUtil.current_url = categories_url[0];
                        break;
                    case 1:
                        NewsInformListUtil.current_url = categories_url[1];
                        break;
                    case 2:
                        NewsInformListUtil.current_url = categories_url[2];
                        break;
                    case 3:
                        NewsInformListUtil.current_url = categories_url[3];
                        break;
                }
                NewsInformListUtil.page = 1;
                NewsInformListUtil.get_list_by_page(NewsInformListUtil.current_url, new NewsInformListUtil.get_list_by_pageCall() {
                    @Override
                    public void success(final List<NewsInform> list) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecyclerViewData(list);
                            }
                        });
                    }
                });
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_news_inform_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);

    }

    private void initRecyclerViewData(final List<NewsInform> list) {
        final NewsInformAdapter adapter = new NewsInformAdapter(R.layout.item_news_inform, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(NewsInformListActivity.this, NewsInfoActivity.class);
                i.putExtra("url", list.get(position).getUrl());
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (shown >= all_numbers) {
                    adapter.loadMoreEnd();
                } else {
                    //再次获取数据
                    //此处应该是判断网络情况，默认良好
                    if (true) {
                        NewsInformListUtil.get_list_by_page(NewsInformListUtil.current_url, new NewsInformListUtil.get_list_by_pageCall() {
                            @Override
                            public void success(final List<NewsInform> list) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.addData(list);
                                        adapter.loadMoreComplete();
                                    }
                                });
                            }
                        });
                    } else {
                        adapter.loadMoreFail();
                    }
                }

            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void initCollapsingToolBarlayout() {
        abl_news_inform_list = (AppBarLayout) findViewById(R.id.abl_news_inform_list);
        abl_news_inform_list.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (abl_state != STATE.EXPANDED) {
                        abl_state = STATE.EXPANDED;//修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (abl_state != STATE.COLLAPSED) {
                        abl_state = STATE.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (abl_state != STATE.INTERNEDIATE) {
                        abl_state = STATE.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });


    }

    public Object getObject(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());

    }

    public void setAblExpanded(Boolean b) {
        if (b) {
            abl_news_inform_list.setExpanded(true);
            abl_state = STATE.EXPANDED;
        } else {
            abl_news_inform_list.setExpanded(false);
            abl_state = STATE.COLLAPSED;
        }

    }

    public void onBackPressed() {
        if (abl_state != STATE.EXPANDED) {
            setAblExpanded(true);
            return;
        }
        startActivity(new Intent(NewsInformListActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        super.onBackPressed();
    }
}
