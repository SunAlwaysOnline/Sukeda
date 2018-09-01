package com.example.bmobtest.Utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by 戚春阳 on 2017/12/14.
 */

public class ShowDialogUtil {
    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Activity activity, String msg) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(msg);
        //点击屏幕不消失，点击返回键消失
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
