package com.example.bmobtest.Fragment.SetMidFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.example.bmobtest.Adapter.KwxfAdapter;
import com.example.bmobtest.Bean.Kwxf;
import com.example.bmobtest.Constant.UstsValue;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.HttpUtils;
import com.example.bmobtest.Utils.IsEmptyUtils;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.example.bmobtest.Utils.Toast;
import com.vondear.rxtools.view.RxToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 戚春阳 on 2017/12/17.
 */

public class KwxfFragment extends Fragment {
    private LinearLayout ly_back;
    private EditText et_kwxf_code;
    private ImageView iv_kwxf_code;
    private Button btn_kwxf_get_code;
    private Button btn_kwxf_select;
    private LinearLayout ly_kwxf_select;
    private ListView lv_kwxf_list;
    private List<Kwxf> kwxf_list;
    private String cookie;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kwxf, container, false);
        intiViews(v);
        return v;
    }

    private void intiViews(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        et_kwxf_code = (EditText) v.findViewById(R.id.et_kwxf_code);
        iv_kwxf_code = (ImageView) v.findViewById(R.id.iv_kwxf_code);
        btn_kwxf_get_code = (Button) v.findViewById(R.id.btn_kwxf_get_code);
        btn_kwxf_get_code.setOnClickListener(new GetCodeOnClickListener());
        btn_kwxf_select = (Button) v.findViewById(R.id.btn_kexf_select);
        btn_kwxf_select.setOnClickListener(new SelectOnClickListener());
        ly_kwxf_select = (LinearLayout) v.findViewById(R.id.ly_kwxf_select);
        lv_kwxf_list = (ListView) v.findViewById(R.id.lv_kwxf);

    }


    private class GetCodeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(IsEmptyUtils.get_st_id())) {
                RxToast.success("请先到设置中进行学生身份验证！");
                return;
            }
            getVerifyCode();

        }
    }

    private class SelectOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //手动隐藏软键盘
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            ShowDialogUtil.showProgressDialog(getActivity(), "正在查询...");
            login();
        }
    }

    private void getVerifyCode() {
        ShowDialogUtil.showProgressDialog(getActivity(), "正在获取验证码");
        HttpUtils.sendGetRequest(UstsValue.kwxfcx_code, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                String cookie0 = response.header("Set-Cookie");
                cookie = cookie0.substring(0, cookie0.length() - 8);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.closeProgressDialog();
                        iv_kwxf_code.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private void login() {
        String st_id = (String) BmobUser.getObjectByKey("st_id");
        String st_pd = (String) BmobUser.getObjectByKey("st_pd");
        String st_code = et_kwxf_code.getText().toString().trim();
        if (TextUtils.isEmpty(st_code)) {
            ShowDialogUtil.closeProgressDialog();
            RxToast.error("请先输入验证码！");
            return;
        }
        final RequestBody requestBody = new FormBody.Builder()
                .add("__VIEWSTATE", "dDw1NzgzNTg2OTc7Oz6jcqWq650/zED0qcB+UtqbyQB/Cg==")
                .add("__VIEWSTATEGENERATOR", "FCD7F2DE")
                .add("code_id", st_code)
                .add("Password", st_pd)
                .add("Submit1", "%E7%99%BB++%E5%BD%95")
                .add("UserName", st_id)
                .build();
        final Request request = new Request.Builder()
                .url(UstsValue.kwxfcx)
                .post(requestBody)
                .addHeader("Cookie", cookie)
                .build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ShowDialogUtil.closeProgressDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.request().url().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (res.equals(UstsValue.kwxfcx_main)) {
                            Toast.show(getActivity(), "登陆成功！", 0);
                            get_detail_info_html();
                        } else {
                            Toast.show(getActivity(), "登陆失败！", 0);
                        }
                    }
                });


            }
        });
    }

    private void get_detail_info_html() {
        final Request request = new Request.Builder()
                .url(UstsValue.kwxfcx_detail)
                .addHeader("Cookie", cookie)
                .build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                final Document document = Jsoup.parse(res);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        get_detail_info(document);
                        ly_kwxf_select.setVisibility(View.GONE);
                        show_info();

                    }
                });

            }
        });

    }

    private void get_detail_info(Document document) {
        Elements element = document.getElementsByAttributeValue("class", "datelist").select("td");
        kwxf_list = new ArrayList<>();
        int i = 0;
        Kwxf k = null;
        StringBuilder s = null;
        for (Element e : element) {
            if (i > 7) {
                Log.e("tag", e.toString());
                if (i % 8 == 0) {
                    k = new Kwxf();
                    s = new StringBuilder();
                    s.append(e.text());
                }
                if (i % 8 == 1) {
                    s.append("-" + e.text());
                    k.setTime(s.toString());
                }
                if (i % 8 == 4) {
                    s = new StringBuilder();
                    s.append(e.text());
                }
                if (i % 8 == 5) {
                    s.append("-" + e.text());
                    k.setName(s.toString());
                }
                if (i % 8 == 6) {
                    s = new StringBuilder();
                    if (e.text().startsWith(".")) {
                        s.append("0" + e.text());
                    } else {
                        s.append(e.text());
                    }
                    k.setGrade(Double.parseDouble(s.toString()));
                }
                if (i % 8 == 7) {
                    s = new StringBuilder();
                    s.append(e.text());
                    k.setPass(e.text());
                    kwxf_list.add(k);
                }

            }

            i = i + 1;
        }


    }

    private void show_info() {
        ShowDialogUtil.closeProgressDialog();
        lv_kwxf_list.setAdapter(new KwxfAdapter(getActivity(), R.layout.item_kwxf, kwxf_list));
    }

}
