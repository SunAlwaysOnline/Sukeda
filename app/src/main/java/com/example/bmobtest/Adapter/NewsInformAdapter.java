package com.example.bmobtest.Adapter;

import android.app.Application;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.NewsInform;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/1/26.
 */

public class NewsInformAdapter extends BaseQuickAdapter<NewsInform, BaseViewHolder> {
    public NewsInformAdapter(@LayoutRes int layoutResId, @Nullable List<NewsInform> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInform item) {
        helper.setText(R.id.tv_news_inform_title, item.getTitle())
                .setText(R.id.tv_news_inform_time, item.getTime().substring(1, item.getTime().length() - 1))
                .setText(R.id.tv_news_inform_read, item.getRead());
        //.setImageResource(R.id.iv_news_inform, R.mipmap.ic_launcher);
        if (item.getFirst_pic_url() == "") {
            helper.getView(R.id.iv_news_inform).setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(item.getFirst_pic_url()).crossFade().into((ImageView) helper.getView(R.id.iv_news_inform));
        }


    }

}
