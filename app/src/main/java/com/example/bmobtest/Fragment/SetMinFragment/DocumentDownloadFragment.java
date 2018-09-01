package com.example.bmobtest.Fragment.SetMinFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.bmobtest.Bean.FunctionState;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.DocumentUtil;
import com.example.bmobtest.Utils.FunctionStateUtil;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 戚春阳 on 2018/1/31.
 */

public class DocumentDownloadFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ly_back;
    private WebView webView;
    private EditText et_document;
    private Button btn_clear;
    private Button btn_download;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_document_download, container, false);
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
        et_document = (EditText) v.findViewById(R.id.et_document);
        btn_clear = (Button) v.findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        btn_download = (Button) v.findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
        initWebView(v);

    }

    private void initWebView(View v) {
        webView = (WebView) v.findViewById(R.id.wv_document);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    webView.loadUrl(url);
                    et_document.setText(url);
                    return false;
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return false;
                }

            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://wk.baidu.com/?pcf=2");
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                et_document.setText("");
                break;
            case R.id.btn_download:
                if (TextUtils.isEmpty(et_document.getText().toString())) {
                    Toast.show_info(getActivity(), "当前地址栏中无地址！");
                    return;
                }
                ShowDialogUtil.showProgressDialog(getActivity(), "正在下载");
                DocumentUtil.documentDownload(et_document.getText().toString().trim(), getActivity());
                break;
        }
    }


}
