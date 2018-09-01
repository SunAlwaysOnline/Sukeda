package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.Education;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/24.
 */

public class EducationAdapter extends BaseQuickAdapter<Education, BaseViewHolder> {
    public EducationAdapter(@LayoutRes int layoutResId, @Nullable List<Education> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Education item) {
        helper.setText(R.id.tv_education_title, item.getTitle())
                .setText(R.id.tv_education_time, item.getTime());

    }
}
