package com.example.bmobtest.Utils;

import android.app.Application;
import android.util.Log;

import com.example.bmobtest.Bean.Message;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 戚春阳 on 2018/2/25.
 */

public class MyMessageUtil {
    //当前页数
    public static int current_page = 0;
    //当前总数
    public static int all = 0;
    //已经查看的消息总数
    public static int shown = 0;

    //获取总数
    public static void get_all(final get_allCall allCall) {
        BmobQuery<Message> query = new BmobQuery<>();
        query.count(Message.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    all = integer;
                    allCall.success();
                   Log.e("tag","总数"+all);
                } else {
                    RxToast.error("获取消息总数失败！");
                }
            }
        });
    }

    //分页降序获取消息
    public static void get_message_list(final get_messageCall messageCall) {
        BmobQuery<Message> query = new BmobQuery<>();
        query.setLimit(10);
        query.setSkip(10 * current_page);
        query.order("-createdAt");
        query.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        RxToast.info("当前无消息！");
                    }
                    messageCall.success(list);
                    shown = shown + list.size();
                    current_page++;
                } else {
                    messageCall.fail();
                    RxToast.error("获取消息失败！");
                }
            }
        });
    }

    public interface get_allCall {
        void success();
    }

    public interface get_messageCall {
        void success(List<Message> list);

        void fail();
    }
}
