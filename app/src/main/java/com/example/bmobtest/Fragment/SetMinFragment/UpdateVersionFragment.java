package com.example.bmobtest.Fragment.SetMinFragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;

/**
 * Created by 戚春阳 on 2018/2/25.
 */

public class UpdateVersionFragment extends Fragment {
    private LinearLayout ly_back;
    private WebView webView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_version, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
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
        webView = (WebView) v.findViewById(R.id.wv_update_version);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //网页地址为http,图片地址为https,需要设置混合模式
        //settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //settings.setBlockNetworkImage(false);
        webView.loadUrl("http://sukeda.bmob.site/");
    }
}
