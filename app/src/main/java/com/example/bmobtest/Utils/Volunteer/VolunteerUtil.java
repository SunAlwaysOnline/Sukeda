package com.example.bmobtest.Utils.Volunteer;

import com.example.bmobtest.Bean.Volunteer;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/5.
 */

public class VolunteerUtil {
    public static String name = "";
    public static String time = "";
    public static List<Volunteer> list;

    //处理登陆后的提示的json字符串，返回操作状态
    public static String handle_login_state(String json) {
        String state = "";
        try {
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("result");
            state = object1.getString("desc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state;
    }

    //处理登陆后的提示的json字符串，返回操作状态码
    public static int handle_login_code(String json) {
        //默认为登陆不成功  1代表成功  0代表失败
        int code = 0;
        try {
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("result");
            code = object1.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void get_list(String html, listCall listCall) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table[id=table_box]").select("td");
        int i = 0;
        list = new ArrayList<>();
        Volunteer volunteer = null;
        for (Element element : elements) {
            if (i % 4 == 0) {
                volunteer = new Volunteer();
                volunteer.setNumber(element.text());
            }
            if (i % 4 == 1) {
                volunteer.setActivity(element.text());
            }
            if (i % 4 == 2) {
                volunteer.setTime(element.text());
            }
            if (i % 4 == 3) {
                volunteer.setState(element.text());
                list.add(volunteer);
            }
            i++;
        }
        listCall.success();
    }

    public interface listCall {
        void success();
    }
}
