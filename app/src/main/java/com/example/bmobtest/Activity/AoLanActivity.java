package com.example.bmobtest.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.bmobtest.Constant.UstsValue;
import com.example.bmobtest.R;

/**
 * Created by 戚春阳 on 2017/12/31.
 */

public class AoLanActivity extends AppCompatActivity {
    private LinearLayout ly_back;
    private WebView mWebView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_aolan);
        initViews();
    }

    private void initViews() {
        ly_back = (LinearLayout) findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mWebView = (WebView) findViewById(R.id.wv_aolan);
        //设置WebView支持JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置允许JS弹出Alert对话框
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl(UstsValue.stu_aolan);


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

                //mWebView.loadUrl(url);
                //使用false使得那些已经重定向过的url不再进行重定向
                return false;
            }

        });
        //设置对话框
        mWebView.setWebChromeClient(new WebChromeClient() {

        });
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            startActivity(new Intent(AoLanActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            super.onBackPressed();
        }
    }
}
