package com.example.bmobtest.Utils.Education;


import android.util.Log;

import com.example.bmobtest.Bean.Education;
import com.example.bmobtest.Bean.EducationOptionalCourse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 戚春阳 on 2018/2/24.
 */

public class EducationOptionalCourseListUtil {
    public static OkHttpClient client = new OkHttpClient();
    public static String[] url = {
            "http://jwch.usts.edu.cn/xszl/shxqgxkjj.htm",//石湖
            "http://jwch.usts.edu.cn/xszl/jfxqgxkjj.htm",//江枫
            "http://jwch.usts.edu.cn/xszl/tpxqgxkjj.htm"};//天平

    //根据url获取对应的校区选修课简介
    public static void get_list(String url, final get_ListCall listCall) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listCall.fail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = new String(response.body().bytes(), "utf-8");
                Document document = Jsoup.parse(html);
                //获取总数
                Elements elements = document.select("div[class=list fl]");
                String number = elements.select("span[class=p_t]").text().split("共")[1].split("条")[0];
                int all = Integer.parseInt(number);
                Elements elements1 = elements.select("ul").select("li");
                EducationOptionalCourse education = null;
                List<EducationOptionalCourse> list = new ArrayList<>();
                for (Element element : elements1) {
                    education = new EducationOptionalCourse();
                    education.setUrl("http://jwch.usts.edu.cn/xszl/" + element.select("a[href]").attr("href"));
                    education.setTitle("" + element.select("a[href]").text());
                    education.setTime("" + element.select("i").text());
                    list.add(education);

                }
                listCall.success(list, all);
            }
        });

    }

    public interface get_ListCall {
        void success(List<EducationOptionalCourse> list, int all);

        void fail();
    }
}
