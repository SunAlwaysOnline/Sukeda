package com.example.bmobtest.Fragment.Education;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bmobtest.Bean.EducationOptionalCourse;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 戚春阳 on 2018/2/24.
 */

public class EducationWebFragment extends Fragment {
    private LinearLayout ly_back;
    private WebView wv_education;
    private String url;
    private LinearLayout ly_education_info;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_education_web, container, false);
        url = getArguments().getString("url");
        Log.e("web_url", url);
        initView(v);
        return v;
    }

    private void initView(View v) {
        ly_education_info = (LinearLayout) v.findViewById(R.id.ly_education_info);
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        initWebView(v);

    }

    private void initWebView(View v) {
        wv_education = (WebView) v.findViewById(R.id.wv_education);
        WebSettings settings = wv_education.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setTextZoom(250);
        get_html();
    }

    private void get_html() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = new String(response.body().bytes(), "utf-8");
                Document document = Jsoup.parse(html);
                Elements elements = document.select("div[class=content fl]");
                //删除下一条
                elements.select("div[class=content-sxt fl]").remove();
                final String content = elements.toString();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wv_education.loadDataWithBaseURL("http://jwch.usts.edu.cn/", content, "text/html", "utf-8", null);
                    }
                });
            }
        });
    }
}
