package com.example.bmobtest.Utils.Entry;

import android.util.Log;

import com.example.bmobtest.Bean.EntryCategory;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 戚春阳 on 2018/2/2.
 * 读取数据库里的词条分类的工具类
 */

public class EntryCategoryUtil {
    //当前页数
    public static int current_page = 0;


    /**
     * 读取词条分类的总数
     */
    public static void get_entry_category_number(final numberCall numberCall) {
        BmobQuery<EntryCategory> query = new BmobQuery<>();
        query.count(EntryCategory.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                numberCall.success(integer);
            }
        });
    }

    /**
     * 获取具体的词条分类，使用分页查询
     */
    public static void get_all_entry_category(final categoryCall categoryCall) {
        Log.e("tag", current_page + "current_page");
        BmobQuery<EntryCategory> query = new BmobQuery<>();
        //限制10条作为一页
        query.setLimit(10).setSkip(10 * current_page).order("-number");
        query.findObjects(new FindListener<EntryCategory>() {
            @Override
            public void done(List<EntryCategory> list, BmobException e) {
                if (e == null) {
                    categoryCall.success(list);
                    current_page++;
                } else {
                    categoryCall.fail();
                }
            }
        });

    }

    //模糊查询词条分类
    public static void get_entry_category_mohu(String category, final mohu_categoryCall mohu_categoryCall) {
        BmobQuery<EntryCategory> query = new BmobQuery<>();
        query.addWhereContains("name", category);
        query.order("-number");
        query.setLimit(20);
        query.findObjects(new FindListener<EntryCategory>() {
            @Override
            public void done(List<EntryCategory> list, BmobException e) {
                mohu_categoryCall.success(list);
            }
        });

    }

    public interface numberCall {
        void success(int number);
    }

    public interface categoryCall {
        void success(List<EntryCategory> list);

        void fail();
    }

    public interface mohu_categoryCall {
        void success(List<EntryCategory> list);
    }
}
