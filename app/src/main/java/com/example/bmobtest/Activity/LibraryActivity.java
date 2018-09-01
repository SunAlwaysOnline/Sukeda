package com.example.bmobtest.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.BookAdapter;
import com.example.bmobtest.Adapter.BookRecommendAdapter;
import com.example.bmobtest.Bean.Book;
import com.example.bmobtest.Bean.Book_recommend;
import com.example.bmobtest.Bean.SlideShow;
import com.example.bmobtest.Fragment.SetMidFragment.BookItemFragment;
import com.example.bmobtest.Fragment.SetMidFragment.LibraryInfoFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.BookRecommendUtil;
import com.example.bmobtest.Utils.BookUtil;
import com.example.bmobtest.Utils.GlideImageLoader;
import com.example.bmobtest.Utils.LibraryPictureUtil;
import com.example.bmobtest.Utils.ResoruceObjectUtil;
import com.example.bmobtest.Utils.Toast;
import com.example.bmobtest.View.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    private LinearLayout ly_back;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Book> book_list;
    //已经显示过的数目
    private int shown = 0;
    //显示的全部list集合
    private List<Book> all_book_list;


    private Banner banner;
    private STATE abl_state;

    //展开、折叠、中间
    private enum STATE {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE

    }

    private AppBarLayout abl_library;
    private MyGridView gridView;

    //回退标记
    public static int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_library);
        initBanner();
        initViews();
        initCollapsingToolBarlayout();
        initGridView();
    }

    private void initViews() {
        ly_back = (LinearLayout) findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchView = (SearchView) findViewById(R.id.sv);
        searchView.setSubmitButtonEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.rv_book);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //book_list=null;
                BookUtil.page = 1;
                BookUtil.all_books = 0;
                initData(s);
                setAblExpanded(false);
                //搜索后失去焦点
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void initData(final String str) {
        book_list = new ArrayList<>();
        all_book_list = new ArrayList<>();
        BookUtil.get_book_info(str, new BookUtil.list_complete() {
            @Override
            public void success(List<Book> list) {
                book_list = list;
                all_book_list.addAll(book_list);
                shown += book_list.size();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initRecycleView(str);
                    }
                });

            }
        });

    }


    private void initRecycleView(final String str) {
        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final BookAdapter bookAdapter = new BookAdapter(R.layout.item_book, book_list);

        bookAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //如果状态不为折叠时，统一改为折叠
                if (abl_state == STATE.EXPANDED || abl_state == STATE.INTERNEDIATE) {
                    setAblExpanded(false);
                }
                Bundle bundle = new Bundle();
                bundle.putString("referer_url", BookUtil.referer_url);
                bundle.putString("item_url", all_book_list.get(position).getUrl());
                BookItemFragment fragment = new BookItemFragment();
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_book, fragment).addToBackStack("detail").commit();


            }
        });

        //添加动画
        bookAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //该适配器提供了5种动画效果（渐显、缩放、从下到上，从左到右、从右到左）
        // ALPHAIN SCALEIN SLIDEIN_BOTTOM SLIDEIN_LEFT SLIDEIN_RIGHT

        //设置重复执行动画
        bookAdapter.isFirstOnly(false);

        //bookAdapter.disableLoadMoreIfNotFullPage();
        //上拉加载（设置这个监听就表示有上拉加载功能了）
        bookAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (shown >= BookUtil.all_books) {
                    //数据全部加载完毕
                    bookAdapter.loadMoreEnd();

                } else {
                    //成功获取数据
                    if (true) {
                        BookUtil.get_book_info(str, new BookUtil.list_complete() {
                            @Override
                            public void success(final List<Book> list) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shown = shown + list.size();
                                        all_book_list.addAll(list);
                                        bookAdapter.addData(list);
                                        bookAdapter.loadMoreComplete();
                                    }
                                });
                            }
                        });

                    } else {
                        //获取更多数据失败
                        bookAdapter.loadMoreFail();
                    }
                }
            }
        }, recyclerView);


        recyclerView.setAdapter(bookAdapter);

    }


    private void initBanner() {
        banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> title = LibraryPictureUtil.get_library_pic_title();
        List<String> url = LibraryPictureUtil.get_library_picture_url();

        banner.setImages(url);
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

        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    private void initCollapsingToolBarlayout() {
        abl_library = (AppBarLayout) findViewById(R.id.abl_library);
        abl_library.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                        if (abl_state == STATE.COLLAPSED) {
                            //playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        // collapsingToolbarLayout.setTitle("INTERNEDIATE");//设置title为INTERNEDIATE
                        abl_state = STATE.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });


    }

    public Object getObject(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());

    }

    private void initGridView() {
        gridView = (MyGridView) findViewById(R.id.gv_library);
        String text[] = {"图书馆简介", "开放时间", "馆藏布局", "规章制度", "联系我们", "热门推荐"};
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("image", getObject("library_general"));
        map.put("text", text[0]);
        arrayList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("image", getObject("library_time"));
        map1.put("text", text[1]);
        arrayList.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("image", getObject("library_layout"));
        map2.put("text", text[2]);
        arrayList.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("image", getObject("library_rules"));
        map3.put("text", text[3]);
        arrayList.add(map3);
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("image", getObject("library_phone"));
        map4.put("text", text[4]);
        arrayList.add(map4);
        HashMap<String, Object> map5 = new HashMap<>();
        map5.put("image", getObject("library_recommend"));
        map5.put("text", text[5]);
        arrayList.add(map5);


        //适配器
        SimpleAdapter adapter = new SimpleAdapter(this,
                arrayList,
                R.layout.item_grid,
                new String[]{"image", "text"},
                new int[]{R.id.iv_grid, R.id.tv_grid});


        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
                LibraryInfoFragment fragment = new LibraryInfoFragment();
                Bundle b = new Bundle();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_book, fragment).addToBackStack(null);
                //如果状态不为折叠时，统一改为折叠
                if (abl_state == STATE.EXPANDED || abl_state == STATE.INTERNEDIATE) {
                    setAblExpanded(false);
                }
                switch (postion) {
                    case 0:
                        String url = "http://library.usts.edu.cn/?q=node/1";
                        b.putString("url", url);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 1:
                        String url1 = "http://library.usts.edu.cn/?q=node/3";
                        b.putString("url", url1);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 2:
                        String url2 = "http://library.usts.edu.cn/?q=node/30";
                        b.putString("url", url2);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 3:
                        String url3 = "http://library.usts.edu.cn/?q=node/31";
                        b.putString("url", url3);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 4:
                        String url4 = "http://library.usts.edu.cn/?q=node/85";
                        b.putString("url", url4);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 5:
                        BookRecommendUtil.get_recommend_list(new BookRecommendUtil.call_categories() {
                            @Override
                            public void success(final List<String> list) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new MaterialDialog.Builder(LibraryActivity.this)
                                                .title("选择分类")
                                                .content("统计范围：2个月　统计方式：借阅次数")
                                                .negativeText("取消")
                                                .items(list)
                                                .itemsCallback(new MaterialDialog.ListCallback() {
                                                    @Override
                                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                                        BookRecommendUtil.get_book_recommend(BookRecommendUtil.categories_url.get(position), new BookRecommendUtil.call_recommend() {
                                                            @Override
                                                            public void success(final List<Book_recommend> list) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        initRecycleView_recommend(list);
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            }
                        });


                        break;

                }
            }
        });

    }


    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            String tag = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            super.onBackPressed();
            searchView.setVisibility(View.VISIBLE);
            searchView.clearFocus();
            recyclerView.setVisibility(View.VISIBLE);

            if (tag != null && tag.equals("detail")) {
                return;
            }
            if (abl_library != null) {
                setAblExpanded(true);
            }
            return;
        }
        if (count == 0) {
            if (abl_state != STATE.EXPANDED) {
                setAblExpanded(true);
            } else {
                startActivity(new Intent(LibraryActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                super.onBackPressed();
            }

        }


    }

    private void initRecycleView_recommend(List<Book_recommend> list) {
        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final BookRecommendAdapter bookRecommendAdapter = new BookRecommendAdapter(R.layout.item_book_recommend, list);

        bookRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //如果状态不为折叠时，统一改为折叠
                if (abl_state == STATE.EXPANDED || abl_state == STATE.INTERNEDIATE) {
                    setAblExpanded(false);
                }
                Bundle bundle = new Bundle();
                bundle.putString("referer_url", BookRecommendUtil.categories_url.get(position));
                bundle.putString("item_url", BookRecommendUtil.list.get(position).getUrl());
                BookItemFragment fragment = new BookItemFragment();
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_book, fragment).addToBackStack("detail").commit();


            }
        });

        //添加动画
        bookRecommendAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //该适配器提供了5种动画效果（渐显、缩放、从下到上，从左到右、从右到左）
        // ALPHAIN SCALEIN SLIDEIN_BOTTOM SLIDEIN_LEFT SLIDEIN_RIGHT

        //设置重复执行动画
        bookRecommendAdapter.isFirstOnly(false);

        //bookAdapter.disableLoadMoreIfNotFullPage();
        //上拉加载（设置这个监听就表示有上拉加载功能了）
        bookRecommendAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                // if (shown >= BookUtil.all_books) {
                //数据全部加载完毕
                bookRecommendAdapter.loadMoreEnd();

//                } else {
//                    //成功获取数据
//                    if (true) {
//                        BookUtil.get_book_info(str, new BookUtil.list_complete() {
//                            @Override
//                            public void success(final List<Book> list) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        shown = shown + list.size();
//                                        all_book_list.addAll(list);
//                                        bookAdapter.addData(list);
//                                        bookAdapter.loadMoreComplete();
//                                    }
//                                });
//                            }
//                        });
//
//                    } else {
//                        //获取更多数据失败
//                        bookAdapter.loadMoreFail();
//                    }
//                }
            }
        }, recyclerView);
        recyclerView.setAdapter(bookRecommendAdapter);
    }

    public void setAblExpanded(Boolean b) {
        if (b) {
            abl_library.setExpanded(true);
            abl_state = STATE.EXPANDED;
        } else {
            abl_library.setExpanded(false);
            abl_state = STATE.COLLAPSED;
        }

    }
}