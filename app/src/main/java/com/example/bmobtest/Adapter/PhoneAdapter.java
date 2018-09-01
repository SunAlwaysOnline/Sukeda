package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.DepartmentPhone;
import com.example.bmobtest.Bean.Volunteer;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/5.
 */

public class PhoneAdapter extends BaseQuickAdapter<DepartmentPhone, BaseViewHolder> {

    public PhoneAdapter(@LayoutRes int layoutResId, @Nullable List<DepartmentPhone> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentPhone item) {
        helper.setText(R.id.tv_position, item.getPosition())
                .setText(R.id.tv_phone, item.getPhone());
    }
}
