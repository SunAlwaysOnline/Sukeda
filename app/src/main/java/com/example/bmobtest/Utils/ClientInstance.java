package com.example.bmobtest.Utils;

import okhttp3.OkHttpClient;

/**
 * Created by 戚春阳 on 2017/12/13.
 */

public class ClientInstance {
    public static OkHttpClient client;

    public static OkHttpClient getInstance() {
        return client != null ? client : new OkHttpClient();
    }
}
