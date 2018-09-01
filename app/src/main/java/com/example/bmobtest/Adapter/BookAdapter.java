package com.example.bmobtest.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobtest.Bean.Book;
import com.example.bmobtest.R;

import java.util.List;

/**
 * Created by 戚春阳 on 2018/1/23.
 */

public class BookAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {
    public BookAdapter(@LayoutRes int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        helper.setText(R.id.tv_book_name, item.getName())
                .setText(R.id.tv_book_author, item.getAuthor())
                .setText(R.id.tv_book_press, item.getPress())
                .setText(R.id.tv_book_language, item.getLanguage())
                .setText(R.id.tv_book_museum_collection, item.getMuseum_collection())
                .setText(R.id.tv_book_available, item.getAvailable());

    }
}
