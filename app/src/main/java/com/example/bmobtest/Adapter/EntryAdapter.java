package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.Entry;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.EntryUtil;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.likeview.RxShineButton;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 戚春阳 on 2018/2/3.
 */

public class EntryAdapter extends BaseQuickAdapter<Entry, BaseViewHolder> {
    public EntryAdapter(@LayoutRes int layoutResId, @Nullable List<Entry> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Entry item) {
        helper.setText(R.id.tv_entry_title, item.getTitle())
                .setText(R.id.tv_entry_content, item.getContent())
                .setText(R.id.tv_entry_author, item.getAuthor().getUsername())
                .setText(R.id.tv_entry_time, item.getCreatedAt())
                .setText(R.id.tv_entry_stars, item.getStars() + "");
        try {
            BmobFile file = item.getAuthor().getFile();
            String url = file.getFileUrl();

            Glide.with(mContext).load(url).into((ImageView) helper.getView(R.id.civ_entry_head));
        } catch (NullPointerException e) {
            //当前用户无头像
            helper.setImageResource(R.id.civ_entry_head, R.mipmap.ic_launcher_round);
        }
    }


}
