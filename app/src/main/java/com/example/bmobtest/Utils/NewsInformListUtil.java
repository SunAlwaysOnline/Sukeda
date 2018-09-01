package com.example.bmobtest.Utils;

import android.util.Log;

import com.example.bmobtest.Bean.NewsInform;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.*;

/**
 * Created by 戚春阳 on 2018/1/28.
 */

public class NewsInformListUtil {
    private static OkHttpClient client = new OkHttpClient();
    private static String[] categories_url = {
            "http://news.usts.edu.cn/news/news_more.asp?lm2=1",
            "http://news.usts.edu.cn/news/news_more.asp?lm2=2"};
    private static List<NewsInform> newsInformList1 = new ArrayList<>();
    private static List<NewsInform> newsInformList2 = new ArrayList<>();
    private static List<NewsInform> newsInformListall = new ArrayList<>();
    public static HashMap<String, String> first_pic_url_map = new HashMap<>();
    //当前处于第几页
    public static int page = 1;
    //当前的地址
    public static String current_url = "";


    public static String[] url_tag = {"http://news.usts.edu.cn", "http://notify.usts.edu.cn"};


    /**
     * 获取苏科要闻前五条中有图片的新闻，校园快讯前五条中有图片的快讯
     * 获取两者的列表
     */
    public static void get_news_inform_pic_url(two_categories_listCall listCall) {

        NewsInformUtil.get_list(categories_url[0], new NewsInformUtil.NewsinformCall() {
            @Override
            public void success(List<NewsInform> list) {


                for (NewsInform newsInform : list) {
                    newsInformList1.add(newsInform);
                }
                for (int i = 0; i < 4; i++) {
                    get_first_pic_url(list.get(i).getUrl(), new get_first_pic_urlCall() {
                        @Override
                        public void success(String url, String pic_url) {
                            first_pic_url_map.put(url, pic_url);
                            Log.e("tag", first_pic_url_map.size() + "list1");
                        }
                    });
                }


            }
        });
        NewsInformUtil.get_list(categories_url[1], new NewsInformUtil.NewsinformCall() {
            @Override
            public void success(List<NewsInform> list) {
                for (NewsInform newsInform : list) {
                    newsInformList2.add(newsInform);
                }
                for (int i = 0; i < 4; i++) {
                    get_first_pic_url(list.get(i).getUrl(), new get_first_pic_urlCall() {

                        @Override
                        public void success(String url, String pic_url) {
                            first_pic_url_map.put(url, pic_url);
                            Log.e("tag", first_pic_url_map.size() + "list2");
                        }
                    });
                }

            }
        });
//        while (true) {
//            if (i == 2) {
//                listCall.success();
//                break;
//            }
//        }
        while (true) {
            if (first_pic_url_map.size() == 8) {
                Log.e("tag", "success");
                listCall.success();

                for (String key : first_pic_url_map.keySet()) {
                    Log.e("tag", "map-----" + key + "---" + first_pic_url_map.get(key));
                }
                break;
            }

        }

    }

    public static List<NewsInform> merge_have_pic_list() {
        List<NewsInform> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            newsInformListall.add(newsInformList1.get(i));
            newsInformListall.add(newsInformList2.get(i));
        }
        for (int i = 0; i < 8; i++) {
            for (String key : first_pic_url_map.keySet()) {
                if (newsInformListall.get(i).getUrl().equals(key) && !first_pic_url_map.get(key).equals("")) {
                    newsInformListall.get(i).setFirst_pic_url(first_pic_url_map.get(key));
                    list.add(newsInformListall.get(i));
                }
            }
        }

        return list;
    }


    public static void get_first_pic_url(final String url, final get_first_pic_urlCall urlCall) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                String un_pic_url = "";
                String pic_url = "";
                try {
                    un_pic_url = document.select("div[class=content]").select("a[href]").first().attr("href");
                } catch (NullPointerException e) {
                    pic_url = "";
                }
                if (un_pic_url.endsWith("jpg")) {
                    if (un_pic_url.contains("http")) {
                        pic_url = un_pic_url;
                    } else {
                        pic_url = "http://news.usts.edu.cn/" + un_pic_url;
                    }
                }
                urlCall.success(url, pic_url);
            }
        });
    }

    //由当前页数获得列表并解析
    public static void get_list_by_page(String url, final get_list_by_pageCall pageCall) {
        String true_url = "";
        if (page == 1) {
            true_url = url;
        } else if (!current_url.contains("page")) {
            true_url = url + "&page=" + page;
        } else if (current_url.contains("page")) {
            true_url = current_url.split("&")[0] + "&page=" + page;
        }
        page++;
        current_url = true_url;
        Log.e("tag", current_url);
        NewsInformUtil.get_list(true_url, new NewsInformUtil.NewsinformCall() {
            @Override
            public void success(List<NewsInform> list) {
                pageCall.success(list);
            }
        });


    }


    public interface two_categories_listCall {
        void success();
    }

    public interface get_first_pic_urlCall {
        void success(String url, String pic_url);
    }

    public interface get_list_by_pageCall {
        void success(List<NewsInform> list);
    }


}


