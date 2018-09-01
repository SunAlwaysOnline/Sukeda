package com.example.bmobtest.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.Volunteer;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/5.
 */

public class VolunteerAdapter extends BaseQuickAdapter<Volunteer, BaseViewHolder> {
    public VolunteerAdapter(int layoutResId, List<Volunteer> data) {
        super(layoutResId, data);
    }

    protected void convert(BaseViewHolder helper, Volunteer item) {
        helper.setText(R.id.tv_volunteer_item_number, item.getNumber())
                .setText(R.id.tv_volunteer_item_name, item.getActivity())
                .setText(R.id.tv_volunteer_item_time, item.getTime())
                .setText(R.id.tv_volunteer_item_state, item.getState());
    }
}
