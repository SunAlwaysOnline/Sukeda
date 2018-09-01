package com.example.bmobtest.Fragment.Knowledge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bmobtest.R;
import com.example.bmobtest.Utils.NewsReset;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 戚春阳 on 2018/2/17.
 */

public class KnowledgeFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_info;
    private TextView tv_knowledge_title;
    private TextView tv_knowledge_author;
    private WebView wv_knowledge;
    private String url;
    private String category;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_knowledge, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        url = getArguments().getString("url");
        category = getArguments().getString("category");
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        tv_knowledge_title = (TextView) v.findViewById(R.id.tv_knowledge_title);
        tv_knowledge_author = (TextView) v.findViewById(R.id.tv_knowledge_author);
        tv_info.setText(category);
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        initWebView(v);
    }

    private void initWebView(View v) {
        wv_knowledge = (WebView) v.findViewById(R.id.wv_knowledge);
        WebSettings settings = wv_knowledge.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setTextZoom(115);
        wv_knowledge.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                NewsReset.imgReset(wv_knowledge);
                deleteContent();
            }
        });
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
                RxToast.error("加载失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                final String title = document.select("div[class=title]").select("h1").get(0).text();
                final String author = document.select("div[class=title]").select("span").get(0).text();
                String conten = null;
                String temp_content1 = document.select("div[class=question-answer]").toString();
                String temp_content2 = document.select("div[class=content]").toString();
                if (!TextUtils.isEmpty(temp_content1)) {
                    conten = temp_content1;
                } else {
                    conten = temp_content2;
                }
                final String finalConten = conten;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_knowledge_title.setText(title);
                        tv_knowledge_author.setText(author);
                        wv_knowledge.loadDataWithBaseURL(null, finalConten, "text/html", "utf-8", null);
                    }
                });

            }
        });
    }

    public void deleteContent() {
        wv_knowledge.loadUrl("javascript:function deleteSearch(){" +
                "document.getElementsByClassName('show_page')[0].remove();" +
                "document.getElementsByClassName('link_url')[0].remove();" +
                " }  javascript:deleteSearch();");
    }
}
