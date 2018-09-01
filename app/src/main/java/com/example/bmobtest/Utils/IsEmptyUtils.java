package com.example.bmobtest.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bmobtest.Bean.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by 戚春阳 on 2017/12/10.
 */

public class IsEmptyUtils {
    public static void isEmpty(Context ctx, String content, String hint) {
        if (TextUtils.isEmpty(content)) {
            Toast.show(ctx, hint, 0);

        }
    }


    public static void judeg_is_null(TextView v, String key, String bef_info, String aft_info, EditText update_v) {
        String value = (String) BmobUser.getObjectByKey(key);
        if (value != null) {
            v.setText(aft_info);
            update_v.setText(value);
        } else {
            v.setText(bef_info);
            update_v.setText("");

        }
    }

    public static String get_st_id() {
        User user = BmobUser.getCurrentUser(User.class);
        String id = user.getSt_id();
        return id;
    }
}
