package com.example.bmobtest.Fragment.SetMinFragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.bmobtest.Activity.HomeActivity;
import com.example.bmobtest.Activity.NewsInfoActivity;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.NewsReset;
import com.example.bmobtest.Utils.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by 戚春阳 on 2018/1/26.
 */

public class NewsInformItemFragment extends Fragment {
    private WebView mWebView;
    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_click_rate;
    private String url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_news_inform, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        mWebView = (WebView) v.findViewById(R.id.wv_news_info);
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_date = (TextView) v.findViewById(R.id.tv_date);
        tv_click_rate = (TextView) v.findViewById(R.id.tv_click_rate);
        url = getArguments().getString("url");
        initWebView(v);


    }

    private void initWebView(View v) {
        final WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //启动缓存
        mWebSettings.setAppCacheEnabled(true);
        //缓存模式，入如果本地有，就加载本地缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);android4.4之后无效

        get_html();
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }



        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                NewsReset.imgReset(mWebView);
               // NewsReset.deleteContent(mWebView);
                NewsReset.click(mWebView);
            }
        });

    }


    private void get_html() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).timeout(6000).get();
                    final String title = document.select("div[class=title]").text();
                    String un_date = document.select("div[class=author]").text().split("信")[0];
                    final String date = un_date.substring(3, un_date.length() - 2);
                    String init_times = document.select("div[class=author]").eq(1).text();
                    String un_times = init_times.split("编")[0];
                    final String times = un_times.substring(0, un_times.length() - 1);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_title.setText(title);
                            tv_date.setText(date);
                            tv_click_rate.setText(times);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



}
