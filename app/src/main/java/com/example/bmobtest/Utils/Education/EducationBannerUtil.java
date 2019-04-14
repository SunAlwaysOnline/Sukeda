package com.example.bmobtest.Utils.Education;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;
import okhttp3.Call;

/**
 * Created by 戚春阳 on 2018/2/23.
 */

public class EducationBannerUtil {
    private static OkHttpClient client = new OkHttpClient();
    //教务系统基地址
    public static String url = "http://jwch.usts.edu.cn/";

    //获取轮播图地址
    public static void get_banner_list(final get_bannerCall bannerCall) {
        Request request = new Request.Builder()
                .url("http://jwch.usts.edu.cn/")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<String> list = new ArrayList<String>();
                Document document = Jsoup.parse(response.body().string());
                Elements elements = document.select("div[class=slider]").select("div[class=bd]").select("li").select("img");
                for (Element element : elements) {
                    list.add(url + element.attr("src"));
                }
                bannerCall.success(list);
            }
        });
    }

    public interface get_bannerCall {
        void success(List<String> list);
    }
}
