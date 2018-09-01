package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.Message;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/25.
 */

public class MyMessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    public MyMessageAdapter(@LayoutRes int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        helper.setText(R.id.tv_message_title, item.getTitle())
                .setText(R.id.tv_message_time, item.getCreatedAt())
                .setText(R.id.tv_message_author, item.getAuthor());

    }


}
