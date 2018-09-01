package com.example.bmobtest.Utils;

import android.util.Log;

import com.example.bmobtest.Bean.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;
import okhttp3.Call;

/**
 * Created by 戚春阳 on 2018/1/23.
 */

public class BookUtil {
    public static OkHttpClient client = new OkHttpClient();
    public static String url = "http://210.29.8.31:8080/opac/openlink.php?strSearchType=title&strText=";
    //当前关键字下共有多少本书
    public static int all_books = 0;
    public static String page_url = "";
    public static String referer_url;
    //http://210.29.8.31:8080/opac/openlink.php?location=ALL&title=%E4%BD%A0%E7%9A%84&
// doctype=ALL&lang_code=ALL&match_flag=forward&displaypg=20&showmode=list&orderby=
// DESC&sort=CATA_DATE&onlylendable=no&count=306&with_ebook=&page=2
    //记录当前的页数
    public static int page = 1;

    public static void get_book_html(String str) {
        Request request = new Request.Builder()
                .url(url + URLEncoder.encode(str))
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //get_book_info(response.body().string());
            }
        });

    }

    public static void get_book_info(String str, final list_complete list_call) {
        final List<Book> list = new ArrayList<>();
        String true_url = "";
        if (page == 1) {
            true_url = url + URLEncoder.encode(str);
            page++;
        } else {
            true_url = "http://210.29.8.31:8080/opac/openlink.php?location=ALL&title=" +
                    URLEncoder.encode(str) +
                    "&doctype=ALL&lang_code=ALL&match_flag=forward&displaypg=20&showmode=list&orderby=" +
                    "DESC&sort=CATA_DATE&onlylendable=no&count=306&with_ebook=&page=" + page;
            page++;
        }
        referer_url = true_url;
        Request request = new Request.Builder()
                .url(true_url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = response.body().string();
                Document document = Jsoup.parse(html);
                get_pages(document);
                Elements elements = document.select("li[class=book_list_info]");
                for (Element element : elements) {
                    Book book = new Book();
                    String language = element.select("span").first().text();
                    String url = "http://210.29.8.31:8080/opac/" +
                            element.select("a").first().attr("href");
                    String name = element.select("a[href]").first().text();
                    String museum_collection = element.select("span").get(1).text().split("可")[0];
                    String available = "可" + element.select("span").get(1).text().split("可")[1];
                    String author_press = element.select("p").text();
                    String arrays[] = author_press.split(" ");
                    String author = "";
                    String press = "";
                    if (arrays.length > 6) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int a = 2; a < arrays.length - 3; a++) {
                            stringBuilder.append(" " + arrays[a]);
                        }
                        author = stringBuilder.toString();
                        press = arrays[arrays.length - 3];
                    } else if (arrays.length < 6) {
                        author = "";
                        press = arrays[2];
                    } else {
                        author = arrays[2];
                        press = arrays[3];
                    }
                    book.setName(name);
                    book.setAuthor(author);
                    book.setPress(press);
                    book.setLanguage(language);
                    book.setMuseum_collection(museum_collection);
                    book.setAvailable(available);
                    book.setUrl(url);
                    list.add(book);
                }
                list_call.success(list);
            }
        });

    }

    /**
     * 拿到总页数
     */
    public static void get_pages(Document document) {
        //应对无数据时的情况
        try {
            //只取第一次情况下的总数
            if (all_books == 0) {
                all_books = Integer.parseInt(document.select("strong[class=red]").text());
            }
        } catch (NumberFormatException e) {

        }
    }

    public interface list_complete {
        void success(List<Book> list);
    }
}
