package com.example.bmobtest.Fragment.SetMidFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bmobtest.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 戚春阳 on 2018/1/24.
 */

public class BookItemFragment extends Fragment {
    private WebView webView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_item, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        webView = (WebView) v.findViewById(R.id.wv_book);
        Bundle bundle = getArguments();
        String referer_url = bundle.getString("referer_url");
        String item_url = bundle.getString("item_url");
        initWebView();
        //隐藏搜索框

        get_html(referer_url, item_url);
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //settings.setDefaultTextEncodingName("utf-8");
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //settings.setUseWideViewPort(true);
        //settings.setLoadWithOverviewMode(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        //禁止webview点击
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


    }

    private void get_html(String referer_url, String item_url) {
        Log.e("tag", referer_url);
        Log.e("tag", item_url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(item_url)
                .addHeader("Referer", referer_url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = response.body().string();
                Document document = Jsoup.parse(html);
                final String book_title = "<h2>书目信息</h2>";
                final Elements elements = document.select("dl[class=booklist]");
                //馆藏信息头
                final String museum_title = "<br><br><h2>馆藏信息</h2>";
                final Elements elements1 = document.select("table[id=item]");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadDataWithBaseURL(null, book_title + elements.toString() + museum_title + elements1.toString(), "text/html", "utf-8", null);
                        getActivity().findViewById(R.id.sv).setVisibility(View.GONE);
                        getActivity().findViewById(R.id.rv_book).setVisibility(View.INVISIBLE);
                    }
                });


            }
        });


    }


}
