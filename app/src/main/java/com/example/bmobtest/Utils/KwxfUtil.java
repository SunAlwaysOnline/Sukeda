package com.example.bmobtest.Utils;

import android.graphics.Bitmap;

import okhttp3.OkHttpClient;

/**
 * Created by 戚春阳 on 2017/12/17.
 */

public class KwxfUtil {
    public static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClient == null) {
            return new OkHttpClient();
        } else {
            return okHttpClient;
        }
    }

    public Bitmap get_kwxf_code_img() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        return null;
    }
}
