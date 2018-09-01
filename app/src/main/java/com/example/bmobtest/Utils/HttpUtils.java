package com.example.bmobtest.Utils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 戚春阳 on 2017/12/13.
 */

public class HttpUtils {
    public static OkHttpClient client;

    public static void sendGetRequest(String url, okhttp3.Callback callback) {
        client = ClientInstance.getInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendPostRequest(String url, RequestBody body, okhttp3.Callback callback) {
        client = ClientInstance.getInstance();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static String encode(String url)
    {


        try {

            Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
            int count = 0;
            while (matcher.find()) {
                //System.out.println(matcher.group());
                String tmp=matcher.group();
                url=url.replaceAll(tmp,java.net.URLEncoder.encode(tmp,"UTF-8"));
            }
            // System.out.println(count);
            //url = java.net.URLEncoder.encode(url,"gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return url;
    }

}
