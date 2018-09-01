package com.example.bmobtest.Fragment.SetMidFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.example.bmobtest.Adapter.GradeAdapter;

import com.example.bmobtest.Bean.Grade;
import com.example.bmobtest.Constant.UstsValue;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ClientInstance;

import com.example.bmobtest.Utils.GradeUtil;
import com.example.bmobtest.Utils.HttpUtils;
import com.example.bmobtest.Utils.IsEmptyUtils;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 戚春阳 on 2017/12/29.
 */

public class GradeFragment extends Fragment {
    private LinearLayout ly_back;
    //点击教务系统网络地址的随机数值
    private String random_string;
    private ImageButton ib_back;
    private EditText et_grade_code;
    private ImageView iv_grade_code;
    private Button btn_grade_get_code;
    private Button btn_grade_select;
    private ExpandableListView expanda_lv;
    private List<Grade> list;
    private String grade_url;
    private LinearLayout ly_grade_select;
    private OkHttpClient client;
    private String VIEWSTATE;

    private LinearLayout ly_multiply;
    private Button btn_again;
    private TextView tv_xueNian;
    private TextView tv_xueQi;
    private TextView tv_course;
    private TextView tv_query;
    private String xueNian = "";
    private String xueQi = "";
    private String course = "";
    private String query = "";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grade, container, false);
        initViews(v);
        initChoose(v);
        return v;
    }

    private void initViews(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        ib_back = (ImageButton) v.findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtils.client = null;
                getActivity().onBackPressed();
            }
        });
        ly_grade_select = (LinearLayout) v.findViewById(R.id.ly_grade_select);
        et_grade_code = (EditText) v.findViewById(R.id.et_grade_code);
        iv_grade_code = (ImageView) v.findViewById(R.id.iv_grade_code);
        btn_grade_get_code = (Button) v.findViewById(R.id.btn_grade_get_code);
        btn_grade_get_code.setOnClickListener(new get_codeOnClickListener());
        btn_grade_select = (Button) v.findViewById(R.id.btn_grade_select);
        btn_grade_select.setOnClickListener(new loginOnClickListener());
        expanda_lv = (ExpandableListView) v.findViewById(R.id.expand_lv);
        btn_again = (Button) v.findViewById(R.id.btn_again);
        btn_again.setVisibility(View.GONE);
        btn_again.setOnClickListener(new OnAgainClickListener());
    }


    private class OnAgainClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            get_grade_info_detail(VIEWSTATE);
        }
    }

    private void initChoose(View v) {
        ly_multiply = (LinearLayout) v.findViewById(R.id.ly_multiply);
        tv_xueNian = (TextView) v.findViewById(R.id.tv_xuenian);
        tv_xueQi = (TextView) v.findViewById(R.id.tv_xueqi);
        tv_course = (TextView) v.findViewById(R.id.tv_course);
        tv_query = (TextView) v.findViewById(R.id.tv_query);
        GradeUtil.initDropDownMenuListData();
        tv_xueNian.setOnClickListener(new OnXueNianClickListener());
        tv_xueQi.setOnClickListener(new OnXueQiClickListener());
        tv_course.setOnClickListener(new OnCourseClickListener());
        tv_query.setOnClickListener(new OnQueryClickListener());
    }


    private class get_codeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(IsEmptyUtils.get_st_id())) {
                RxToast.success("请先到设置中进行学生身份验证！");
                return;
            }
            get_url_string();

        }
    }


    private class loginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //手动隐藏软键盘
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            String code = et_grade_code.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                ToastUtils.showShort("请输入验证码！");
                return;
            }
            login(code);
        }
    }

    /**
     * 获取点击教务管理系统后的重定向的地址，以此作为基地址
     */
    public void get_url_string() {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UstsValue.two_options_click)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                //提取随机的字符串的值
                String url_string = response.request().url().toString();
                String a = url_string.split("\\(")[1];
                String b = a.split("\\)")[0];
                random_string = b;
                getVerifCode();
            }
        });

    }

    /**
     * 获取验证码
     */
    private void getVerifCode() {
        String code_url = "http://jw.usts.edu.cn/(" + random_string + ")/CheckCode.aspx";
        HttpUtils.sendGetRequest(code_url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.closeProgressDialog();
                        iv_grade_code.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    /**
     * 尝试登录
     */
    private void login(String code) {
        String message = "正在查询" + query + "...";
        ShowDialogUtil.showProgressDialog(getActivity(), message);
        String id = BmobUser.getObjectByKey("st_id").toString();
        String pwd = BmobUser.getObjectByKey("st_pd").toString();
        Log.e("tag", id + "     " + pwd);
        RequestBody body = new FormBody.Builder()
                .add("__VIEWSTATE", "dDwxNTMxMDk5Mzc0Ozs+8SKE1eIxEXhQ+rQzyLaGuxFtQgA=")
                .add("__VIEWSTATEGENERATOR", "92719903")
                .add("Button1", "")
                .add("hidPdrs", "")
                .add("hidsc", "")
                .add("lbLanguage", "")
                .add("RadioButtonList1", "%D1%A7%C9%FA")
                .add("Textbox1", "")
                .add("TextBox2", pwd)
                .add("txtSecretCode", code)
                .add("txtUserName", id)
                .build();
        String login_url = "http://jw.usts.edu.cn/(" + random_string + ")/default2.aspx";
        HttpUtils.sendPostRequest(login_url, body, new okhttp3.Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                UstsValue.login_success_url = response.request().url().toString();
                Log.e("tag", UstsValue.login_success_url);
                UstsValue.login_success_main_html = response.body().string();
                Log.e("tag", UstsValue.login_success_main_html);
                get_grade_url();
                get_grade_info_big();
                //get_grade_info_detail();
            }
        });
    }

    //获取成绩查询的地址
    private void get_grade_url() {
        Document document = Jsoup.parse(UstsValue.login_success_main_html);
        Elements elements = document.select("a[href]");
        Log.e("tag", elements.toString());
        for (Element element : elements) {
            if (element.text().equals("成绩查询")) {
                grade_url = "http://jw.usts.edu.cn/(" + random_string + ")/" + element.attr("href");
            }
        }
        if (TextUtils.isEmpty(grade_url)) {
            ToastUtils.showShort("验证码错误");
            getVerifCode();
            return;
        }
        Log.e("tag", grade_url);

    }

    /**
     * 获取个人的VIEWSTATE的值，很长，但不获取不行
     */
    private void get_grade_info_big() {
        OkHttpClient client = ClientInstance.getInstance();
        while (true) {
            if (grade_url != null) {
                break;
            }
        }
        Request request = new Request.Builder()
                .url(grade_url)
                .addHeader("Host", "jw.usts.edu.cn")
                .addHeader("Referer", UstsValue.login_success_url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //从返回的html中获取viewstate的值
                Document document = Jsoup.parse(response.body().string());
                VIEWSTATE = document.select("input[name=__VIEWSTATE]").attr("value");
                get_grade_info_detail(VIEWSTATE);

            }
        });
    }

    private void get_grade_info_detail(String VIEWSTATE) {
        //VIEWSTATEs是一个超长的字符串，里面存有当前html中各个控件的状态，每个人有独特的VIEWSTATE，
        // 只要知道这个值，可以在不登陆的情况下查询任何人的成绩
        String key = GradeUtil.queryMap.get(query);
        String value = HttpUtils.encode(query);
        if (query == "") {
            //什么都不选时，默认查询的是历年成绩
            query = GradeUtil.query[2];
            key = GradeUtil.queryMap.get(query);
            value = HttpUtils.encode(query);
        }
        RequestBody requestBody = new FormBody.Builder()
                .add("__EVENTARGUMENT", "")
                .add("__EVENTTARGET", "")
                .add("__VIEWSTATE", VIEWSTATE)

                //.add("__VIEWSTATEGENERATOR", "9727EB43")
                .add(key, value)
                .add("ddlXN", xueNian)
                .add("ddlXQ", xueQi)
                .add("ddl_kcxz", course)
                .add("hidLanguage", "")
                .build();
        OkHttpClient client = ClientInstance.getInstance();
        while (true) {
            if (grade_url != null) {
                break;
            }
        }
        Request request = new Request.Builder()
                .url(grade_url)
                .post(requestBody)
                .addHeader("Host", "jw.usts.edu.cn")
                .addHeader("Referer", HttpUtils.encode(grade_url))
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String grade_html = response.body().string();
                Log.e("tag", grade_html);
                handle_grade_html(grade_html, new call() {
                    @Override
                    public void success(List<Grade> l) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                expanda_lv.setAdapter(new GradeAdapter(getActivity(), list));
                                ShowDialogUtil.closeProgressDialog();
                                if (list.size() == 0) {
                                    ToastUtils.showShort("没有查到记录！");
                                }
                                ly_grade_select.setVisibility(View.GONE);
                                btn_again.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                });

            }
        });


    }

    /**
     * 处理html
     */
    private void handle_grade_html(String html, call call) {
        Document document = Jsoup.parse(html);
        Elements elemnts = document.getElementsByAttributeValue("class", "datelist").select("td");
        Log.e("tag", elemnts.toString());
        list = new ArrayList<>();
        int i = 0;
        Grade grade = null;
        for (Element e : elemnts) {
            if (i > 14) {
                if (i % 15 == 0) {
                    grade = new Grade();
                    grade.setXn(e.text());
                }
                if (i % 15 == 1) {
                    grade.setXq(e.text());
                }
                if (i % 15 == 2) {
                    grade.setCourse_code(e.text());
                }
                if (i % 15 == 3) {
                    grade.setCourse_name(e.text());
                }
                if (i % 15 == 4) {
                    grade.setCourse_nature(e.text());
                }
                if (i % 15 == 5) {
                    grade.setCourse_belong(e.text());
                }
                if (i % 15 == 6) {
                    grade.setCredit(e.text());
                }
                if (i % 15 == 7) {
                    grade.setGpa(e.text());
                }
                if (i % 15 == 8) {
                    grade.setMark(e.text());
                }
                if (i % 15 == 9) {
                    grade.setMinor_mark(e.text());
                }
                if (i % 15 == 10) {
                    grade.setScore_make_up(e.text());
                }
                if (i % 15 == 11) {
                    grade.setScore_rebuild(e.text());
                }
                if (i % 15 == 12) {
                    grade.setCollege(e.text());
                }
                if (i % 15 == 13) {
                    grade.setRemark(e.text());
                }
                if (i % 15 == 14) {
                    grade.setMark_rebuild(e.text());
                    list.add(grade);
                }


            }
            i = i + 1;
        }
        call.success(list);
    }

    private class OnXueNianClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new MaterialDialog.Builder(getActivity())
                    .title("选择学年")
                    .negativeText("取消")
                    .items(GradeUtil.xueNian)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            String select = GradeUtil.xueNian.get(position);
                            tv_xueNian.setText(select);
                            xueNian = select;
                            if (select.equals("不限")) {
                                xueNian = "";
                            }
                        }
                    }).show();
            Log.e("tag", "click");
        }
    }

    private class OnXueQiClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new MaterialDialog.Builder(getActivity())
                    .title("选择学期")
                    .negativeText("取消")
                    .items(Arrays.asList(GradeUtil.xueQi))
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            String select = GradeUtil.xueQi[position];
                            tv_xueQi.setText(select);
                            xueQi = select;
                            if (select.equals("不限")) {
                                xueQi = "";
                            }
                        }
                    }).show();
        }
    }

    private class OnCourseClickListener implements View.OnClickListener {
        public void onClick(View v) {
            new MaterialDialog.Builder(getActivity())
                    .title("选择课程性质")
                    .negativeText("取消")
                    .items(GradeUtil.courseText)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            String select = GradeUtil.courseText.get(position);
                            tv_course.setText(select);
                            course = GradeUtil.courseNumber.get(position);
                            if (select.equals("不限")) {
                                course = "";
                            }
                        }
                    }).show();
        }
    }

    private class OnQueryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new MaterialDialog.Builder(getActivity())
                    .title("选择查询性质")
                    .negativeText("取消")
                    .items(Arrays.asList(GradeUtil.query))
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            String select = GradeUtil.query[position];
                            tv_query.setText(select);
                            query = select;
                        }
                    }).show();
        }
    }

    public interface call {
        void success(List<Grade> l);
    }


}
