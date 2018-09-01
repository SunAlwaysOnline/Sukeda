package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.EntryCategory;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/2.
 */

public class EntryCategoryAdapter extends BaseQuickAdapter<EntryCategory, BaseViewHolder> {
    public EntryCategoryAdapter(@LayoutRes int layoutResId, @Nullable List<EntryCategory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EntryCategory item) {
        helper.setText(R.id.tv_entry_category, item.getName())
                .setText(R.id.tv_entry_category_number, item.getNumber() + "条");

    }
}
