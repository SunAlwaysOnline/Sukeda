package com.example.bmobtest.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Response;

/**
 * Created by 戚春阳 on 2017/12/30.
 */

public class CookieManager {
    public static HashMap<String, String> cookieHashMap = new HashMap<String, String>();

    public static void subCookie(Response response) {
        List<String> cookieString = new ArrayList<>();
        cookieString = response.headers("Set-Cookie");
        if (cookieString == null) {
            return;
        }
        for (int i = 0; i < cookieString.size(); i++) {
            String key_value = cookieString.get(i).split(";")[0];
            String key = key_value.split("=")[0];
            String value = key_value.split("=")[1].length() > 0 ? key_value.split("=")[1] : "";
            cookieHashMap.put(key, value);
        }
    }

    public static String get_single_cookie(String cookieName) {
        if (cookieHashMap.size() == 0) {
            return "";
        }
        Set<String> keySet = cookieHashMap.keySet();
        Iterator<String> itor = keySet.iterator();
        String value = "";
        while (itor.hasNext()) {
            String key = itor.next();
            if (cookieName.equals(key)) {
                value = cookieHashMap.get(key);
            }
        }
        return value;
    }

}
