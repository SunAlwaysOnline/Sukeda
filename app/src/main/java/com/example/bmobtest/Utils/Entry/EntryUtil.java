package com.example.bmobtest.Utils.Entry;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.example.bmobtest.Bean.Entry;
import com.example.bmobtest.Bean.EntryCategory;
import com.example.bmobtest.Bean.User;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.likeview.RxShineButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 戚春阳 on 2018/2/3.
 */

public class EntryUtil {
    private static String server_time = "";
    //设置一个状态值，如果是用户新增完词条后，自动返回并刷新词条页面
    //true  需要刷新
    //false 不需要刷新
    public static boolean isShouldRefersh=false;

    //获得词条列表,获取所属分类，标题，内容，作者，点赞的数目
    public static void get_entry_list(String categoryId, int current_page, final get_entry_listCall call) {
        BmobQuery<Entry> query = new BmobQuery<>();
        EntryCategory category = new EntryCategory();
        category.setObjectId(categoryId);
        //查询指定分类下的所有词条
        query.addWhereEqualTo("category", new BmobPointer(category));
        //并把其所对应的作者的名称及objectId
        query.include("author[objectId|username|file]");
        query.order("-stars");
        //使用分页查询
        //Log.e("tag",current_page+"current");
        query.setLimit(5).setSkip(5 * current_page);
        query.findObjects(new FindListener<Entry>() {
            @Override
            public void done(List<Entry> list, BmobException e) {
                if (e == null) {
                    call.success(list);
                } else {
                    call.fail();
                }
            }
        });
    }

    //获取当前词条分类的词条总数
    public static void get_entry_size(String categoryId, final get_entry_sizeCall call) {
        BmobQuery<EntryCategory> query = new BmobQuery<>();
        query.getObject(categoryId, new QueryListener<EntryCategory>() {
            @Override
            public void done(EntryCategory entryCategory, BmobException e) {
                call.success(entryCategory.getNumber());
            }
        });

    }

    //模糊查找词条
    public static void get_entry_mohu(String categoryId, String title, final get_entry_mohuCall call) {
        BmobQuery<Entry> query = new BmobQuery<>();
        EntryCategory category = new EntryCategory();
        category.setObjectId(categoryId);
        //查询指定分类下的所有词条
        query.addWhereEqualTo("category", new BmobPointer(category));
        //并把其所对应的作者的名称及objectId
        query.include("author[objectId|username|file]");
        query.order("-stars");
        query.addWhereContains("title", title);
        query.setLimit(20);
        query.findObjects(new FindListener<Entry>() {
            @Override
            public void done(List<Entry> list, BmobException e) {
                call.success(list);
            }
        });
    }

    //点赞后，增加1
    public static void increment_stars(String categoryId) {
        Entry entry = new Entry();
        entry.setObjectId(categoryId);
        Log.e("tag", "++");
        entry.increment("stars", 1);
        entry.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });
    }

    //撤销后点赞后，减少1
    public static void decrese_stars(String categoryId) {
        Entry entry = new Entry();
        entry.setObjectId(categoryId);
        Log.e("tag", "--");
        entry.increment("stars", -1);
        entry.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }

    //点赞后，将点赞后的词条id记录 key:当前词条id  value:当前词条id
    public static void record_light(String entry_id) {
        SPUtils.getInstance("entry_id_record").put("server_time", server_time);
        String record_time = SPUtils.getInstance("entry_id_record").getString("server_time", "00");
        Log.e("tag", record_time + "alerady_add");
        SPUtils.getInstance("entry_id_record").put(entry_id, entry_id);
    }

    //撤销点赞后，删除当前的id记录
    public static void delete_record(String entry_id) {
        SPUtils.getInstance("entry_id_record").remove(entry_id);

    }

    //在记录中查找是否存在此id，若存在，则将点赞按钮点亮，否则仍然为暗
    public static boolean state(String entry_id) {
        //默认未选中
        Boolean state = SPUtils.getInstance("entry_id_record").contains(entry_id);
        if (state) {
            //说明存在
            return true;
        } else {
            return false;
        }
    }

    //若sp内存储时间不为当天
    public static void delete_entry_id() {
        String record_time = SPUtils.getInstance("entry_id_record").getString("server_time", "00");
        Log.e("tag", record_time + "  record");
        Log.e("tag", server_time + "  server");
        if (!record_time.equals(server_time)) {
            //若时间不为当天
            Log.e("tag", "时间不为当天");
            SPUtils.getInstance("entry_id_record").clear();
        }
    }

    //获取服务器时间
    public static void get_current_time() {
        Bmob.getServerTime(new QueryListener<Long>() {
            @Override
            public void done(Long time, BmobException e) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                server_time = sdf.format(new Date(time * 1000L));
                Log.e("tag", server_time + "already");
            }
        });
    }

    public interface get_entry_sizeCall {
        void success(int size);
    }

    public interface get_entry_listCall {
        void success(List<Entry> list);

        void fail();

    }

    public interface get_entry_mohuCall {
        void success(List<Entry> list);
    }

}
