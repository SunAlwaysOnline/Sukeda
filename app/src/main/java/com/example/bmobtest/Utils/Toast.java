package com.example.bmobtest.Utils;

import android.content.Context;
import android.graphics.Color;

import com.vondear.rxtools.view.RxToast;

/**
 * Created by 戚春阳 on 2017/12/9.
 */

public class Toast {
    public static void show(Context ctx, String msg, int time) {
        if (time == 0) {
            android.widget.Toast.makeText(ctx, msg, android.widget.Toast.LENGTH_SHORT).show();
        } else if (time == 1) {
            android.widget.Toast.makeText(ctx, msg, android.widget.Toast.LENGTH_LONG).show();
        }
    }

    public static void show_info(Context context, String message) {
        RxToast.custom(context, message, com.vondear.rxtools.R.drawable.ic_info_outline_white_48dp, Color.parseColor("#FFFFFF"), Color.parseColor("#4992c3"), 0, true, true).show();
    }

    public static void show_success(Context context, String message) {
        RxToast.custom(context, message, com.vondear.rxtools.R.drawable.ic_check_white_48dp, Color.parseColor("#FFFFFF"), Color.parseColor("#4992c3"), 0, true, true).show();
    }
}


