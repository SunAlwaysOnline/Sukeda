package com.example.bmobtest.Utils.Education;

import android.util.Log;

import com.example.bmobtest.Bean.Education;

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

public class EducationListUtil {
    public static OkHttpClient client = new OkHttpClient();

    //根据url获取公告等,并获取总数
    public static void get_list(String url, final get_listCall listCall) {
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
                String html = new String(response.body().bytes(), "gb2312");
                Document document = Jsoup.parse(html);
                Elements elements = document.select("section[id=list-body]");
                String number = elements.select("p").text().split("共")[1].split("条")[0];
                int all = Integer.parseInt(number);
                Elements elements1 = elements.select("li");
                Education education = null;
                List<Education> list = new ArrayList<Education>();
                for (Element element : elements1) {
                    education = new Education();
                    education.setUrl("http://jwch.usts.edu.cn/newweb/" + element.select("a[href]").attr("href"));
                    education.setTitle("" + element.select("span").text());
                    education.setTime("" + element.select("time").text());
                    list.add(education);

                }
                listCall.success(list, all);
            }
        });

    }

    public interface get_listCall {
        void success(List<Education> list, int all);

        void fail();
    }
}
