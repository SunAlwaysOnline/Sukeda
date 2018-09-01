package com.example.bmobtest.Utils.Entry;

import com.example.bmobtest.Bean.Entry;
import com.example.bmobtest.Bean.EntryCategory;
import com.example.bmobtest.Bean.User;
import com.vondear.rxtools.view.RxToast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 戚春阳 on 2018/2/6.
 */

public class EntryAddUtil {
    public static String baidu = "lgPX8558";
    public static String tieba = "b8e83fd174";
    public static String zhidao = "";
    public static String zhihu = "c129f28fad";

    //增加词条，并设定其star初始值为0
    public static void addEntry(final String categoryId, String title, String content, final addCall addCall) {
        User user = BmobUser.getCurrentUser(User.class);
        EntryCategory entryCategory = new EntryCategory();
        entryCategory.setObjectId(categoryId);
        Entry entry = new Entry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setAuthor(user);
        entry.setCategory(entryCategory);
        entry.setStars(0);
        try {
            entry.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        addCall.success();
                        //添加成功后，使得该词条分类下的词条数加1
                        increaseOne_category(categoryId);
                    } else {
                        addCall.fail();
                    }
                }
            });
        } catch (NullPointerException e) {
            RxToast.error("空指针错误！");
        }

    }

    public static void update_entry(String entryId, String title, String content, final updateCall updateCall) {
        Entry entry = new Entry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.update(entryId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updateCall.success();
                } else {
                    updateCall.fail();
                }
            }
        });

    }

    //使得词条分类下的词条数目+1
    public static void increaseOne_category(String categoryId) {
        EntryCategory entryCategory = new EntryCategory();
        entryCategory.setObjectId(categoryId);
        entryCategory.increment("number", 1);
        entryCategory.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });

    }

    //使得词条分类下的词条数目-1
    public static void decreaseOne_category(String categoryId) {
        EntryCategory entryCategory = new EntryCategory();
        entryCategory.setObjectId(categoryId);
        entryCategory.increment("number", -1);
        entryCategory.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });

    }


    public static void add() {
        Entry entry = new Entry();
        entry.setTitle("苏州科技大学各校区");
        entry.setContent("  校区问题：\n" +
                "江枫校区：建筑与城市规划学院，土木工程学院，视觉与传媒艺术学院(包括天平的艺术生）\n" +
                "石湖校区：环境科学与工程学院，人文学院，商学院，电子学院，外国语学院，化学生物与材料工程学院，数理学院，国际教育学院，\n" +
                "天平校区：机械工程学院，天平学院");
        entry.setStars(0);
        User user = new User();
        user.setObjectId(tieba);
        entry.setAuthor(user);
        EntryCategory category = new EntryCategory();
        category.setObjectId("kyYbUHHU");
        entry.setCategory(category);
        entry.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

    }

    public interface addCall {
        void success();

        void fail();
    }

    public interface updateCall {
        void success();

        void fail();
    }


}
