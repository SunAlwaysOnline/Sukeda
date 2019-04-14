package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.EducationOptionalCourse;
import com.example.bmobtest.Bean.NewsInform;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/1/26.
 */

public class EducationOptionalCourseAdapter extends BaseQuickAdapter<EducationOptionalCourse, BaseViewHolder> {
    public EducationOptionalCourseAdapter(@LayoutRes int layoutResId, @Nullable List<EducationOptionalCourse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EducationOptionalCourse item) {
        helper.setText(R.id.tv_education_title, item.getTitle())
                .setText(R.id.tv_education_time, item.getTime());
    }

}
