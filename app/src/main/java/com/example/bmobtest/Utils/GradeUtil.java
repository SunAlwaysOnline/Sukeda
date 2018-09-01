package com.example.bmobtest.Utils;

import android.util.Log;

import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by 戚春阳 on 2018/1/29.
 */

public class GradeUtil {
    public static String headers[] = {"学年", "学期", "课程性质", "课程性质"};
    public static List<String> xueNian;
    public static String xueQi[] = {"不限", "1", "2"};
    public static List<String> courseNumber;
    public static List<String> courseText;
    public static String query[] = {"学期成绩", "学年成绩", "历年成绩", "课程最高成绩", "未通过成绩"};
    public static HashMap<String, String> queryMap;

    public static void initDropDownMenuListData() {
        initDropDownMenuXueNian();
        initDropDownMenuCourse();
        initQueryData();
    }

    private static void initDropDownMenuXueNian() {
        xueNian = new ArrayList<>();
        xueNian.add("不限");
        String grade = null;
        try {
            grade = BmobUser.getObjectByKey("grade").toString();
        } catch (NullPointerException e) {
            RxToast.error("需要先验证学生身份！或由于未完成教师质量评价，无法获得当前所在年级！");
            return;
        }
        int start_year = Integer.parseInt(grade);
        int current_years = Calendar.getInstance().get(Calendar.YEAR);
        int current_month = Calendar.getInstance().get(Calendar.MONTH);
        int end_year;
        if (current_month >= 9) {
            end_year = current_years + 1;
        } else {
            end_year = current_years;
        }
        int temp = start_year;
        for (int i = 0; i < end_year - start_year; i++) {
            xueNian.add(temp + "-" + (temp + 1));
            temp++;
        }

    }

    private static void initDropDownMenuCourse() {
        courseNumber = new ArrayList<>();
        courseNumber.add("00");
        courseNumber.add("09");
        for (int i = 10; i <= 28; i++) {
            courseNumber.add("" + i);
        }
        courseText = new ArrayList<>();
        courseText.add("不限");
        courseText.add("综合选修课");
        courseText.add("素质拓展");
        courseText.add("必修课");
        courseText.add("限选课");
        courseText.add("任选课");
        courseText.add("跨选课");
        courseText.add("公共选修课");
        courseText.add("辅修课");
        courseText.add("不计绩点");
        courseText.add("学科竞赛");
        courseText.add("方向课");
        courseText.add("素质必修课");
        courseText.add("通识必修课");
        courseText.add("通识限选课");
        courseText.add("跨学科任选课");
        courseText.add("核心必修课");
        courseText.add("一般必修课");
        courseText.add("方向限选课");
        courseText.add("学科任选课");
        courseText.add("综合必修课");

    }

    public static void initQueryData() {
        queryMap = new HashMap<>();
        queryMap.put("学期成绩", "btn_xq");
        queryMap.put("学年成绩", "btn_xn");
        queryMap.put("历年成绩", "btn_zcj");
        queryMap.put("课程最高成绩", "btn_zg");
        queryMap.put("未通过成绩", "Button2");
    }
}
