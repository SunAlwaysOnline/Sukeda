package com.example.bmobtest.Fragment.SetMinFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bmobtest.Activity.NewsInfoActivity;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.NewsReset;
import com.example.bmobtest.Utils.Toast;

/**
 * Created by 戚春阳 on 2018/1/22.
 */

public class BrInUsItemFragment extends Fragment {
    private WebView webView;
    private String url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_brinus, container, false);
        Bundle bundle = getArguments();
        this.url = bundle.getString("url");
        Log.e("tag56", "url" + url);
        initViews(v);
        return v;
    }


    public static BrInUsItemFragment newInstance(String url) {
        BrInUsItemFragment fragment = new BrInUsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initViews(final View v) {

        webView = (WebView) v.findViewById(R.id.wv_brinus);
        webView.loadUrl(url);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //启动缓存
        mWebSettings.setAppCacheEnabled(true);
        //缓存模式，入如果本地有，就加载本地缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);android4.4之后无效
        //mWebSettings.setUseWideViewPort(true);
        //mWebSettings.setLoadWithOverviewMode(true);
        //mWebSettings.setTextSize(WebSettings.TextSize.SMALLER);
        //设置垂直滚动条不显示
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                NewsReset.imgReset(webView);

            }
        });
    }
}
