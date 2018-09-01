package com.example.bmobtest.Fragment.Entry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.EntryAdapter;
import com.example.bmobtest.Bean.Entry;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.EntryUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.likeview.RxShineButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 戚春阳 on 2018/2/3.
 */

public class EntryFragment extends Fragment {
    private LinearLayout ly_back;
    private RecyclerView recyclerView;
    //所属词条分类的objectId
    private String categoryId;
    //所属词条分类的名称
    private String category;
    //当前页数
    private int current_page = 0;
    //当前词条总数
    private int all_number = 0;
    //已经显示的词条数目
    private int shown = 0;
    private SearchView searchView;
    private View rootView;
    private Button btn_add;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (EntryUtil.isShouldRefersh) {
            rootView = null;
            current_page = 0;
            shown = 0;
            all_number = 0;
        }
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_entry, container, false);
            rootView = v;
            initView(v);
            EntryUtil.isShouldRefersh = false;
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
        this.categoryId = getArguments().getString("categoryId");
        this.category = getArguments().getString("category");
        initRecyclerView(v);
        initSearchView(v);
        btn_add = (Button) v.findViewById(R.id.btn_entry_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntryAddFragment fragment = new EntryAddFragment();
                Bundle bundle = new Bundle();
                bundle.putString("categoryId", categoryId);
                bundle.putString("category", category);
                fragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void initSearchView(View v) {
        searchView = (SearchView) v.findViewById(R.id.sv_entry);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EntryUtil.get_entry_mohu(categoryId, query, new EntryUtil.get_entry_mohuCall() {
                    @Override
                    public void success(List<Entry> list) {
                        initSearchViewData(list);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initSearchViewData(final List<Entry> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new ArrayList<String>();
                for (Entry entry : list) {
                    list1.add(entry.getTitle() + "     " + entry.getStars() + "赞");
                }
                new MaterialDialog.Builder(getActivity())
                        .title("选择词条标题")
                        .negativeText("取消")
                        .items(list1)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                Entry entry = list.get(position);
                                changeToDetailFragment(entry);
                            }
                        }).show();
            }
        });
    }


    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_entry);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        //手动初始化第一次加载,先获取总数
        EntryUtil.get_entry_size(categoryId, new EntryUtil.get_entry_sizeCall() {
            @Override
            public void success(int size) {
                all_number = size;
                //第一次加载
                EntryUtil.get_entry_list(categoryId, current_page, new EntryUtil.get_entry_listCall() {
                    public void success(final List<Entry> list) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecyclerViewData(list);
                            }
                        });
                        shown = shown + list.size();
                        current_page++;
                    }

                    public void fail() {
                        RxToast.error("加载失败!");
                    }
                });
            }
        });

    }

    private void initRecyclerViewData(final List<Entry> list) {
        final EntryAdapter adapter = new EntryAdapter(R.layout.item_entry, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(true);
        //item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Entry entry = list.get(position);
                changeToDetailFragment(entry);
            }
        });
        //item子控件的点击事件
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                RxShineButton button = (RxShineButton) view;
//                button.setOnCheckStateChangeListener(new RxShineButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(View view, boolean b) {
//                        RxToast.error("" + b);
//                    }
//                });
//            }
//        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (shown >= all_number) {
                    adapter.loadMoreEnd();
                } else {
                    EntryUtil.get_entry_list(categoryId, current_page, new EntryUtil.get_entry_listCall() {
                        @Override
                        public void success(final List<Entry> list) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (list.size() == 0) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
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
        }, recyclerView);

        recyclerView.setAdapter(adapter);

    }

    private void changeToDetailFragment(Entry entry) {
        EntryDetailFragment fragment = new EntryDetailFragment();
        Bundle b = new Bundle();
        b.putString("entryId", entry.getObjectId());
        b.putString("category", category);
        b.putString("categoryId", categoryId);
        b.putString("title", entry.getTitle());
        b.putSerializable("content", entry.getContent());
        b.putString("author", entry.getAuthor().getUsername());
        b.putString("authorId", entry.getAuthor().getObjectId());
        b.putInt("stars", entry.getStars());
        b.putString("time", entry.getCreatedAt());
        //获取头像
        String url = "";
        try {
            BmobFile file = entry.getAuthor().getFile();
            url = file.getFileUrl();
        } catch (NullPointerException e) {
            //当前用户无头像
        }
        b.putString("head_url", url);
        fragment.setArguments(b);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                .replace(R.id.fl_content, fragment)
                .addToBackStack("EntryDetailFragment")
                .commit();
    }
}
