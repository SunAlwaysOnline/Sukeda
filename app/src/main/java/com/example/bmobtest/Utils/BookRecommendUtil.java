package com.example.bmobtest.Utils;

import android.util.Log;

import com.example.bmobtest.Bean.Book_recommend;

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
 * Created by 戚春阳 on 2018/1/25.
 */

public class BookRecommendUtil {
    private static String url = "http://210.29.8.31:8080/top/top_lend.php";
    private static OkHttpClient client = new OkHttpClient();
    public static List<String> categories;
    public static List<String> categories_url;
    public static List<Book_recommend> list;

    public static void get_book_recommend(String url, final call_recommend call_recommend) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handle_book(response.body().string(), call_recommend);
            }
        });
    }

    public static void handle_book(String html, call_recommend call_recommend) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("td[class=whitetext]");
        list = new ArrayList<>();
        Book_recommend book;
        for (int i = 0; i < elements.size(); i++) {
            if ((i + 1) % 8 == 0) {
                book = new Book_recommend();
                book.setName(elements.get(i - 6).select("a[href]").text());
                book.setUrl("http://210.29.8.31:8080/" + elements.get(i - 6).select("a[href]").attr("href"));
                book.setAuthor(elements.get(i - 5).text());
                book.setPress(elements.get(i - 4).text());
                book.setNumber(elements.get(i - 3).text());
                book.setCollection(elements.get(i - 2).text());
                book.setLend(elements.get(i - 1).text());
                book.setRate(elements.get(i).text());
                list.add(book);
            }
        }
        call_recommend.success(list);


    }

    public static void get_recommend_list(final call_categories call_categories) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handle_categories(response.body().string(), call_categories);
            }
        });

    }

    public static void handle_categories(String html, call_categories call) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div[id=underlinemenu]").select("li");
        categories = new ArrayList<>();
        categories_url = new ArrayList<>();
        for (Element element : elements) {
            categories.add(element.text());
            categories_url.add(url + element.select("a[href]").attr("href"));
        }
        call.success(categories);

    }

    public interface call_categories {
        void success(List<String> list);
    }

    public interface call_recommend {
        void success(List<Book_recommend> list);
    }
}
