package com.example.bmobtest.Utils.Education;


import android.util.Log;

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
            "http://jwch.usts.edu.cn/newweb/news_more_gxk_new.asp?zlmid=5&lmid=36",
            "http://jwch.usts.edu.cn/newweb/news_more_gxk_new.asp?zlmid=5&lmid=21",
            "http://jwch.usts.edu.cn/newweb/news_more_gxk_new.asp?zlmid=5&lmid=37"};

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
                String html = new String(response.body().bytes(), "gb2312");
                Document document = Jsoup.parse(html);
                //获取总数
                String all_number = document.select("section[id=list-body]").select("p[class=MsoNormal]").text();
                String temp = all_number.split("共")[1].split("条")[0];
                int all = Integer.parseInt(temp);
                Elements elements = document.select("table[class=gxk]").select("tr").select("td");
                List<EducationOptionalCourse> list = new ArrayList<EducationOptionalCourse>();
                EducationOptionalCourse course = null;
                int i = 0;
                for (Element element : elements) {
                    if (i > 7) {
                        if (i % 8 == 0) {
                            course = new EducationOptionalCourse();
                            course.setName("" + element.text());
                            course.setUrl("http://jwch.usts.edu.cn/newweb/" + element.select("a[href]").get(0).attr("href"));
                        } else if (i % 8 == 1) {
                            course.setXueNian_xueQi("" + element.text());
                        } else if (i % 8 == 2) {
                            course.setCategory("" + element.text());
                        } else if (i % 8 == 3) {
                            course.setLimit("" + element.text());
                        } else if (i % 8 == 4) {
                            course.setCourse_time("" + element.text());
                        } else if (i % 8 == 5) {
                            course.setTeacher("" + element.text());
                        } else if (i % 8 == 6) {
                            course.setDepartment("" + element.text());
                        } else if (i % 8 == 7) {
                            course.setIssure_time("" + element.text());
                            list.add(course);
                        }
                    }
                    i++;
                }
                listCall.success(list,all);
            }
        });

    }

    public interface get_ListCall {
        void success(List<EducationOptionalCourse> list,int all);

        void fail();
    }
}
