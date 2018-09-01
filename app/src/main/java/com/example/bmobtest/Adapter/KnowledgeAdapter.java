package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.Knowledge;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/17.
 */

public class KnowledgeAdapter extends BaseQuickAdapter<Knowledge, BaseViewHolder> {
    public KnowledgeAdapter(@LayoutRes int layoutResId, @Nullable List<Knowledge> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Knowledge item) {
        helper.setText(R.id.tv_knowledge_title, item.getTitle())
                .setText(R.id.tv_knowledge_time, item.getTime())
                .setText(R.id.tv_knowledge_category, item.getCategory());
        ImageView iv = helper.getView(R.id.iv_knowledge);
        if (TextUtils.isEmpty(item.getPic_url())) {
            iv.setVisibility(View.GONE);
        } else {
            iv.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getPic_url()).centerCrop().into(iv);
        }
    }
}
