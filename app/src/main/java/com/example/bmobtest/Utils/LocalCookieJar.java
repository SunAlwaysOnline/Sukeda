package com.example.bmobtest.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动化处理Cookie
 * Created by 戚春阳 on 2017/12/30.
 */

public class LocalCookieJar implements CookieJar {
    public static List<Cookie> cookies;

    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (cookies != null) {
            return cookies;
        }
        return new ArrayList<Cookie>();
    }

    public static void show() {
        for (int i = 0; i < cookies.size(); i++) {
            Log.e("tag", cookies.get(i).toString());
        }
    }

}
