package com.example.bmobtest.Utils.Knowledge;


import android.text.TextUtils;
import android.util.Log;

import com.example.bmobtest.Bean.Knowledge;
import com.example.bmobtest.Bean.KnowledgeTab;

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
 * Created by 戚春阳 on 2018/2/17.
 */

public class KnowledgeUtil {
    //大学生知识网的首页
    public static String knowledge_url = "http://news.daxues.cn";
    public static OkHttpClient client = new OkHttpClient();
    public static List<KnowledgeTab> tabList;


    /**
     * 获取所有标签以及对应的地址
     */
    public static void get_all_tabs(final get_tabsCall tabsCall) {
        Request request = new Request.Builder()
                .url(knowledge_url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                Elements elements = document.select("ul").get(0).select("a");
                tabList = new ArrayList<>();
                for (Element element : elements) {
                    KnowledgeTab tab = new KnowledgeTab();
                    tab.setName(element.text());
                    tab.setUrl(knowledge_url + element.attr("href"));
                    tabList.add(tab);
                }
                tabsCall.success();
            }
        });
    }

    //根据标签地址，获取当前标签下所有的知识内容以及总数
    public static void get_knowledge_list(String url, final get_knowledge_listCall listCall) {
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
                Document document = Jsoup.parse(response.body().string());
                //获取内容
                Elements elements = document.select("div[class=list_box]").select("dl");
                List<Knowledge> knowledgeList = new ArrayList<Knowledge>();
                for (Element element : elements) {
                    Knowledge knowledge = new Knowledge();
                    Element element1 = element.select("dt").select("div[class=tit]").get(0);
                    Element element2 = element1.select("a").get(0);
                    String title = element2.text();
                    String url = knowledge_url + element2.attr("href");
                    String category = element1.select("span").select("a").text();
                    String time = element1.select("span").text().split("·")[1];

                    //图片
                    String pic_url;
                    Elements element3 = element.select("dd");
                    if (element3 == null) {
                        pic_url = "";
                    } else {
                        String temp = element3.select("a").select("img").attr("data-original");
                        if (!TextUtils.isEmpty(temp)) {
                            pic_url = temp;
                        } else {
                            pic_url = element3.select("a").select("img").attr("src");
                        }
                    }
                    knowledge.setTitle(title);
                    knowledge.setUrl(url);
                    knowledge.setCategory(category);
                    knowledge.setTime(time);
                    knowledge.setPic_url(pic_url);
                    knowledgeList.add(knowledge);
                }
                listCall.success(knowledgeList);

            }
        });

    }


    public interface get_tabsCall {
        void success();
    }

    public interface get_knowledge_listCall {
        void success(List<Knowledge> list);

        void fail();
    }
}
