package com.example.bmobtest.Fragment.Entry;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.EntryCategoryAdapter;
import com.example.bmobtest.Bean.EntryCategory;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.EntryCategoryUtil;
import com.example.bmobtest.Utils.Entry.EntryUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by 戚春阳 on 2018/2/1.
 */

public class EntryCategoryFragment extends Fragment {
    private LinearLayout ly_back;
    private RecyclerView recyclerView;
    //词条分类的总数
    private int all_number = 0;
    //当前已经显示的词条数目
    private int shown = 0;
    private View rootView;
    private SearchView searchView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_entry_category, container, false);
            rootView = v;
            initView(v);
            //存储时间不为当天则删除
            EntryUtil.delete_entry_id();
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
        initRecyclerView(v);
        initSearchView(v);
    }

    private void initSearchView(View v) {
        searchView = (SearchView) v.findViewById(R.id.sv_entry_category);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                EntryCategoryUtil.get_entry_category_mohu(s, new EntryCategoryUtil.mohu_categoryCall() {
                    @Override
                    public void success(final List<EntryCategory> list) {
                        initSearchViewData(list);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initSearchViewData(final List<EntryCategory> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new ArrayList<String>();
                for (EntryCategory entryCategory : list) {
                    list1.add(entryCategory.getName() + "      " + entryCategory.getNumber() + "条知识");
                }
                new MaterialDialog.Builder(getActivity())
                        .title("选择词条分类")
                        .negativeText("取消")
                        .items(list1)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                change_to_entryFragment(list, position);
                            }
                        })
                        .show();
            }
        });
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_entry_category);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        EntryCategoryUtil.get_entry_category_number(new EntryCategoryUtil.numberCall() {
            @Override
            public void success(int number) {
                all_number = number;
                EntryCategoryUtil.get_all_entry_category(new EntryCategoryUtil.categoryCall() {
                    public void success(final List<EntryCategory> entryCategoryList) {
                        shown = shown + entryCategoryList.size();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecyclerViewData(entryCategoryList, false);
                            }
                        });

                    }

                    public void fail() {

                    }
                });

            }
        });

    }

    private void initRecyclerViewData(final List<EntryCategory> list, boolean isSearch) {
        final EntryCategoryAdapter adapter = new EntryCategoryAdapter(R.layout.item_entry_category, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                change_to_entryFragment(list, position);
            }
        });
        if (!isSearch) {
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (shown >= all_number) {
                        adapter.loadMoreEnd();
                    } else {
                        EntryCategoryUtil.get_all_entry_category(new EntryCategoryUtil.categoryCall() {
                            @Override
                            public void success(final List<EntryCategory> entryCategoryList) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (entryCategoryList.size() == 0) {
                                            adapter.loadMoreEnd();
                                            return;
                                        }
                                        adapter.addData(entryCategoryList);
                                        adapter.loadMoreComplete();
                                        shown = shown + entryCategoryList.size();
                                    }
                                });
                            }

                            public void fail() {
                                adapter.loadMoreFail();
                            }
                        });
                    }
                }
            }, recyclerView);
        }
        recyclerView.setAdapter(adapter);
    }

    private void change_to_entryFragment(List<EntryCategory> list, int position) {
        EntryFragment fragment = new EntryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", list.get(position).getObjectId());
        bundle.putString("category", list.get(position).getName());
        fragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                .replace(R.id.fl_content, fragment)
                .addToBackStack("EntryFragment")
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EntryCategoryUtil.current_page = 0;
    }
}
