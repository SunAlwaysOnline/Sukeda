package com.example.bmobtest.Fragment.SetMidFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.bmobtest.Adapter.CardAdapter;
import com.example.bmobtest.Bean.Card;
import com.example.bmobtest.Constant.UstsValue;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.CookieManager;
import com.example.bmobtest.Utils.HttpUtils;
import com.example.bmobtest.Utils.IsEmptyUtils;
import com.example.bmobtest.Utils.LocalCookieJar;
import com.example.bmobtest.Utils.SSLSocketClient;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.example.bmobtest.Utils.Toast;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 戚春阳 on 2017/12/28.
 */

public class CardFragment extends Fragment {
    private LinearLayout ly_back;
    private Boolean isLogin = false;
    private ImageButton ib_back;
    private EditText et_card_code;
    private ImageView iv_card_code;
    private Button btn_card_get_code;
    private Button btn_card_select;
    private OkHttpClient client = new OkHttpClient.Builder()
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .followRedirects(false)
            .followSslRedirects(false)
            .build();
    private List<Card> list;
    private ExpandableListView expand_lv;
    private LinearLayout ly_card_select;
    //继续工作时的url
    private String work_url;
    //继续工作的字符串
    private String work_str;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private Button btn_again;
    private TimePickerDialog timePickerDialog;
    private int date_type;
    private SimpleDateFormat true_sf;
    private Date start_date;
    private Date end_date;
    //默认的身份证后六位密码已经不是一卡通查询密码
    private String true_pd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card, container, false);
        initViews(v);
        initDataPicker();
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
                getActivity().onBackPressed();
                logout();
            }
        });
        et_card_code = (EditText) v.findViewById(R.id.et_card_code);
        iv_card_code = (ImageView) v.findViewById(R.id.iv_card_code);
        btn_card_get_code = (Button) v.findViewById(R.id.btn_card_get_code);
        btn_card_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(IsEmptyUtils.get_st_id())) {
                    RxToast.success("请先到设置中进行学生身份验证！");
                    return;
                }
                if (start_date == null || end_date == null) {
                    RxToast.warning("开始时间和结束时间不能为空！");
                    return;
                }
                if (end_date.getTime() < start_date.getTime()) {
                    RxToast.warning("结束时间不能早于开始时间！");
                    return;
                }
                test_vpn();
            }
        });
        btn_card_select = (Button) v.findViewById(R.id.btn_card_select);
        btn_card_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //手动隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                ShowDialogUtil.showProgressDialog(getActivity(), "正在查询一卡通消费记录...");
                if (TextUtils.isEmpty(et_card_code.getText().toString().trim())) {
                    ToastUtils.showShort("请输入验证码！");
                    return;
                }
                String st_id = BmobUser.getObjectByKey("st_id").toString();
                String st_identity = BmobUser.getObjectByKey("st_identity").toString();
                String last_six = "";
                if (TextUtils.isEmpty(true_pd)) {
                    last_six = st_identity.substring(st_identity.length() - 6, st_identity.length());
                } else {
                    last_six = true_pd;
                }
                Log.e("tag", last_six + " last_code");
                String code = et_card_code.getText().toString().trim();
                try_login_card(st_id, last_six, code);
            }
        });
        expand_lv = (ExpandableListView) v.findViewById(R.id.expand_lv);
        ly_card_select = (LinearLayout) v.findViewById(R.id.ly_card_select);
        btn_again = (Button) v.findViewById(R.id.btn_again);
        btn_again.setVisibility(View.GONE);
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_history();
            }
        });
        tv_start_date = (TextView) v.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) v.findViewById(R.id.tv_end_date);
        tv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_type = 0;
                timePickerDialog.show(getActivity().getSupportFragmentManager(), "null");
            }
        });
        tv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_type = 1;
                timePickerDialog.show(getActivity().getSupportFragmentManager(), "null");
            }
        });

    }

    private void initDataPicker() {
        final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        true_sf = new SimpleDateFormat("yyyyMMdd");
        timePickerDialog = new TimePickerDialog.Builder().
                setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        Date date = new Date(millseconds);
                        if (date_type == 0) {
                            tv_start_date.setText(sf.format(date));
                            start_date = new Date(millseconds);
                        } else {
                            tv_end_date.setText(sf.format(date));
                            end_date = new Date(millseconds);
                        }
                    }
                })
                .setCancelStringId("取消")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.main_color))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.text_light))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.text))
                .setWheelItemTextSize(18)
                .build();


    }

    private void test_vpn() {
        in_ui_thread_show_dialog("正在连接校园VPN...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //绕过CA证书验证
                Request request = new Request.Builder()
                        .url(UstsValue.ssl_vpn)
                        .addHeader("Cookie", "lastRealm=ustsUsers-Realm; DSSIGNIN=url_default; DSSignInURL=/")
                        .addHeader("Host", "vpn.usts.edu.cn")
                        .addHeader("DNT", "1")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                        .build();
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    public void onFailure(Call call, IOException e) {
                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        login_vpn();

                    }
                });

            }
        }).start();
    }

    /**
     * 登陆vpn
     */
    private void login_vpn() {
        String st_id = BmobUser.getObjectByKey("st_id").toString();
        String st_identity = BmobUser.getObjectByKey("st_identity").toString();
        Log.e("tag", st_identity);
        String last_six = "";//针对没有进行学生身份验证的同学
        try {
            last_six = st_identity.substring(st_identity.length() - 6, st_identity.length());
        } catch (StringIndexOutOfBoundsException e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowDialogUtil.closeProgressDialog();
                    Toast.show(getActivity(), "您还没有进行学生身份验证！", 1);
                }
            });
            return;
        }
        Log.e("tag", "id:" + st_id + "  last:" + last_six);
        RequestBody requestBody = new FormBody.Builder()
                .add("btnSubmit", "Sign+In%28%E7%99%BB%E5%BD%95%29")
                .add("password", last_six)
                .add("realm", "ustsUsers-Realm")
                .add("tz_offset", "480")
                .add("username", st_id)
                .build();

        Request request = new Request.Builder()
                .url(UstsValue.ssl_login_post)
                .post(requestBody)
                .addHeader("Content-Length", "114")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Cookie", "lastRealm=ustsUsers-Realm; DSSIGNIN=url_default; DSSignInURL=/")
                .addHeader("Referer", "https://vpn.usts.edu.cn/dana-na/auth/url_default/welcome.cgi")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                String url = response.header("location");
                if (!url.contains("index")) {
                    // work_url = url;
                    get_continue_work_str(url);
                } else {
                    CookieManager.subCookie(response);
                    CookieAll.DSASSERTREF = "x";
                    CookieAll.DSFirstAccess = CookieManager.get_single_cookie("DSFirstAccess");
                    CookieAll.DSID = CookieManager.get_single_cookie("DSID");
                    Log.e("tag", "CookieAll.DSFirstAccess=" + CookieAll.DSFirstAccess);
                    Log.e("tag", "CookieAll.DSID=" + CookieAll.DSID);
                    isLogin = true;
                    go_to_ssl_index();
                }
            }
        });
    }

    /**
     * 上次意外退出，本次继续工作,需要先拿到还有上次登录时间的页面内的字符串的值，最后作为post提交选项
     */
    private void get_continue_work_str(final String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", "lastRealm=ustsUsers-Realm; DSSIGNIN=url_default")
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Referer", "https://vpn.usts.edu.cn/dana-na/auth/url_default/welcome.cgi")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                String workString = document.select("input[id=DSIDFormDataStr]").attr("value");
                //work_str = workString;
                continue_work(url, workString);
            }
        });
    }

    /**
     * 登陆的post表单发生变化，再次模拟登录
     */
    private void continue_work(String work_url, String work_str) {
        RequestBody requestBody = new FormBody.Builder()
                .add("btnContinue", "%E7%B9%BC%E7%BA%8C%E5%B7%A5%E4%BD%9C%E9%9A%8E%E6%AE%B5")
                .add("FormDataStr", work_str)
                .build();

        Request request = new Request.Builder()
                .url(UstsValue.ssl_login_post)
                .post(requestBody)
                //.addHeader("Content-Length", "114")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Cookie", "lastRealm=ustsUsers-Realm; DSSIGNIN=url_default; DSSignInURL=/")
                .addHeader("Referer", work_url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CookieManager.subCookie(response);
                CookieAll.DSASSERTREF = "x";
                CookieAll.DSFirstAccess = CookieManager.get_single_cookie("DSFirstAccess");
                CookieAll.DSID = CookieManager.get_single_cookie("DSID");
                Log.e("tag", "CookieAll.DSFirstAccess=" + CookieAll.DSFirstAccess);
                Log.e("tag", "CookieAll.DSID=" + CookieAll.DSID);
                isLogin = true;
                go_to_ssl_index();
            }
        });

    }

    /**
     * 进入vpn主页
     */
    private void go_to_ssl_index() {
        String cookieString = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess;
        Log.e("tag", cookieString);
        Request request1 = new Request.Builder()
                .url(UstsValue.ssl_vpn_index)
                .addHeader("Cookie", cookieString)
                .build();
        client.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //此时ssl_vpn登陆成功，重定向请求也成功
                //Log.e("tag", response.body().string());
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                //Log.e("tag", response.body().string());
                Log.e("tag", "--------------即将进入login---------");
                get_ssl_logo();
            }
        });

    }

    /**
     * 获取图片中的Cookie
     */
    public void get_ssl_logo() {
        String cookieString = "DSSIGNIN=" + CookieAll.DSSIGNIN + "; DSSignInURL=" + CookieAll.DSSignInURL
                + "; DSID=" + CookieAll.DSID + "; DSFirstAccess=" + CookieAll.DSFirstAccess
                + "; DSLastAccess=" + CookieAll.DSLastAccess;
        // Log.e("tag", cookieString);
        Request request = new Request.Builder()
                .url(UstsValue.ssl_vpn_index_logo)
                .addHeader("Cookie", cookieString)
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Referer", "https://vpn.usts.edu.cn/dana/home/index.cgi")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                go_to_sub_system();
            }
        });

    }

    /**
     * 进入金龙卡金融化一卡通网站查询子系统
     */
    public void go_to_sub_system() {
        String cookieString = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(UstsValue.card_login_1)
                .addHeader("Accept", "text/html, application/xhtml+xml, */*")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Cookie", cookieString)
                .addHeader("DNT", "1")
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {

            public void onFailure(Call call, IOException e) {
                Log.e("tag", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                //重定向开始
                String cookieString1 = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                        + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
                Request request1 = new Request.Builder()
                        .url(UstsValue.card_login_2)
                        .addHeader("Accept", "text/html, application/xhtml+xml, */*")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
                        .addHeader("Connection", "Keep-Alive")
                        .addHeader("Cookie", cookieString1)
                        .addHeader("DNT", "1")
                        .addHeader("Host", "vpn.usts.edu.cn")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                        .build();
                client.newCall(request1).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CookieManager.subCookie(response);
                        CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                        //此时即将进入真正的一卡通查询界面
                        card_login();
                    }
                });

            }
        });
    }

    public void card_login() {
        //此时请求一卡通登陆网址
        String cookieString = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request2 = new Request.Builder()
                .url(UstsValue.card_login_url)
                .addHeader("Cookie", cookieString)
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("DNT", "1")
                .addHeader("Referer", "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn,SSO=U+")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request2).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.e("tag", response.body().string());
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                //校园卡查询系统进入成功
                get_code();
            }
        });
    }

    /**
     * 获取验证码
     */
    public void get_code() {
        String cookieString = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(UstsValue.card_login_code)
                .addHeader("Cookie", cookieString)
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("DNT", "1")
                .addHeader("Referer", "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+homeLogin.action")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_card_code.setImageBitmap(bitmap);
                        et_card_code.setText("");
                        ShowDialogUtil.closeProgressDialog();
                    }
                });
            }
        });
    }

    /**
     * 验证码获取完毕，此时尝试登陆
     */
    public void try_login_card(String st_id, String last_six, String code) {
        if (TextUtils.isEmpty(code)) {
            return;
        }
        RequestBody requestBody = new FormBody.Builder()
                .add("imageField.x", "27")
                .add("imageField.y", "13")
                .add("loginType", "2")
                .add("name", st_id)
                .add("passwd", last_six)
                .add("rand", code)
                .add("userType", "1")
                .build();
        String cookieString = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(UstsValue.card_post)
                .post(requestBody)
                .addHeader("Cookie", cookieString)
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Referer", "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+homeLogin.action")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Log.e("tag", "-----" + response.header("Set-Cookie"));
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                Log.e("tag", "--登陆--");
                go_to_left_frmae();
            }
        });
    }

    /**
     * 进入侧边栏
     */
    public void go_to_left_frmae() {
        String cookieString = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(UstsValue.card_left_frame)
                .addHeader("Cookie", cookieString)
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Referer", "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+loginstudent.action")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("tag", "++++__" + response.header("Set-Cookie"));
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                card_history();
            }
        });

    }

    /**
     * 进入一卡通历史流水页面
     */
    public void card_history() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ShowDialogUtil.closeProgressDialog();
                ShowDialogUtil.showProgressDialog(getActivity(), "正在查询...");
            }
        });

        String cookieString = "DSSignInURL=" + CookieAll.DSSignInURL + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(UstsValue.card_history)
                .addHeader("Cookie", cookieString)
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Referer", "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+accleftframe.action")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("tag", "++++++++" + response.header("Set-Cookie"));
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                get_card_no_continue(response.body().string());
            }
        });

    }

    /**
     * 获取一卡通账号以及点击确定后出现链接的continue地址
     */
    public void get_card_no_continue(String html) {

        Document document = Jsoup.parse(html);
        Elements elements = document.select("select[id=account]");
        Log.e("tag", elements.toString());
        //若密码错误，会进入当前异常捕获中,并且会重置验证码
        String account = null;
        try {
            account = elements.select("option[value]").first().attr("value");
        } catch (NullPointerException e) {
            RxToast.error("一卡通获取密码已经不为初始的身份证后六位，因此发生查询失败！");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowDialogUtil.closeProgressDialog();
                    final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(getActivity());//提示弹窗
                    rxDialogEditSureCancel.setTitle("(1)如一卡通查询密码不为身份证后六位，那么输入自新的一卡通查询密码" + "\n"
                            + "(2)要么就是验证码错误！");
                    rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String pd = rxDialogEditSureCancel.getEditText().getText().toString();
                            true_pd = pd;
                            Log.e("tag", true_pd + "  true_pd");
                            rxDialogEditSureCancel.cancel();
                        }
                    });
                    rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rxDialogEditSureCancel.cancel();
                        }
                    });
                    rxDialogEditSureCancel.show();
                }
            });
            go_to_ssl_index();
            return;
        }
        //一卡通账号获取完毕
        Elements elements1 = document.select("form[id=accounthisTrjn]");
        String __continue = elements1.attr("action").split("continue=")[1];
        Log.e("tag", __continue);
        //continue地址获取完毕
        card_select(account, __continue);


    }

    /**
     * 账号和地址获取完毕，开始点击查询，但此时未进入查询日期范围的页面,此方法运行完，进入日期范围页面
     */
    public void card_select(String account, String __continue) {
        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .add("inputObject", "all")
                .add("Submit", "+%C8%B7+%B6%A8+")
                .build();
        String url = UstsValue.card_history_continue + __continue;
        CookieAll.TEMPURL = url;
        String cookieString = "DSID=" + CookieAll.DSID + "; DSFirstAccess=" + CookieAll.DSFirstAccess
                + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Cookie", cookieString)
                .addHeader("Referer", "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+accounthisTrjn.action")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("tag", "brfore__ok" + response.header("Set-Cookie"));
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                String start = true_sf.format(start_date);
                String end = true_sf.format(end_date);
                card_history_last(response.body().string(), start, end);
            }
        });
    }

    /**
     * 此时还未进入最后的流水信息展示页面，而是处于加载页面
     */
    public void card_history_last(String html, String start_date, String end_date) {
        //先获取地址后的continue字符串
        Document document = Jsoup.parse(html);
        String __continue = document.select("form[id=accounthisTrjn]").attr("action").split("continue=")[1];
        RequestBody requestBody = new FormBody.Builder()
                .add("inputEndDate", end_date)
                .add("inputStartDate", start_date)
                .build();
        String url = UstsValue.card_history_continue + __continue;
        CookieAll.TEMPURL = url;
        String cookieString = "DSID=" + CookieAll.DSID + "; DSFirstAccess=" + CookieAll.DSFirstAccess
                + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Cookie", cookieString)
                .addHeader("Referer", CookieAll.TEMPURL)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                get_card_info(response.body().string());
            }
        });

    }

    /**
     * 截取最后的入口地址continue
     */
    public void get_card_info(String html) {
        Document document = Jsoup.parse(html);
        String __continue = document.select("form[name=form1]").attr("action").split("continue=")[1];
        RequestBody requestBody = new FormBody.Builder()
                .add("t", "")
                .build();
        String url = UstsValue.card_history_continue + __continue;
        String cookieString = "DSID=" + CookieAll.DSID + "; DSFirstAccess=" + CookieAll.DSFirstAccess
                + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Cookie", cookieString)
                .addHeader("Referer", CookieAll.TEMPURL)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handle_card_history_html(response.body().string(), new call() {
                    @Override
                    public void success(final List<Card> list) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowDialogUtil.closeProgressDialog();
                                expand_lv.setAdapter(new CardAdapter(getActivity(), list));
                                ly_card_select.setVisibility(View.GONE);
                                btn_again.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });

            }
        });
    }

    /**
     * 处理返回html中的数据
     */
    public void handle_card_history_html(String html, call call) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table[id=tables]").select("td");
        Log.e("tag", elements.toString());
        list = new ArrayList<>();
        Card card = null;
        int i = 0;
        for (Element element : elements) {
            if (i > 8) {
                if (i % 9 == 0) {
                    card = new Card();
                    card.setTime(element.text());
                }
                if (i % 9 == 3) {
                    card.setType(element.text());
                }
                if (i % 9 == 4) {
                    card.setSubSystem(element.text());
                }
                if (i % 9 == 5) {
                    card.setPrice(element.text());
                }
                if (i % 9 == 6) {
                    card.setRemain(element.text());
                }
                if (i % 9 == 7) {
                    card.setTimes(element.text());
                }
                if (i % 9 == 8) {
                    card.setState(element.text());
                    list.add(card);
                }

            }
            i = i + 1;
        }
        call.success(list);

    }

    /**
     * 退出vpn
     */
    public void logout() {
        if (!isLogin) {
            return;
        }
        String cookieString = "DSSIGNIN=" + CookieAll.DSSIGNIN + "; DSID=" + CookieAll.DSID
                + "; DSFirstAccess=" + CookieAll.DSFirstAccess + "; DSLastAccess=" + CookieAll.DSLastAccess;
        Request request = new Request.Builder()
                .url(UstsValue.vpn_logout)
                .addHeader("Cookie", cookieString)
                .addHeader("Host", "vpn.usts.edu.cn")
                .addHeader("Referer", "https://vpn.usts.edu.cn/dana/home/index.cgi")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CookieManager.subCookie(response);
                CookieAll.DSLastAccess = CookieManager.get_single_cookie("DSLastAccess");
                CookieAll.TEMPLOCATION = "https://vpn.usts.edu.cn" + response.header("location");
                //重定向开始
                String cookieString = "lastRealm=" + CookieAll.lastRealm + "; DSSIGNIN=" + CookieAll.DSSIGNIN + "; DSLastAccess=" + CookieAll.DSLastAccess;
                Request request = new Request.Builder()
                        .url(CookieAll.TEMPLOCATION)
                        .addHeader("Cookie", cookieString)
                        .addHeader("Host", "vpn.usts.edu.cn")
                        .addHeader("Referer", "https://vpn.usts.edu.cn/dana/home/index.cgi")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                        .build();
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("tag", response.body().string());
                    }
                });

            }
        });

    }

    public void in_ui_thread_show_dialog(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ShowDialogUtil.showProgressDialog(getActivity(), msg);
            }
        });
    }


    public void onDestroy() {
        logout();
        super.onDestroy();

    }
}

class CookieAll {
    public static String DSSIGNIN = "url_default";
    public static String DSSignInURL = "/";
    public static String lastRealm = "ustsUsers-Realm";
    public static String DSASSERTREF = "x";
    public static String DSFirstAccess;
    public static String DSID;
    public static String DSLastAccess;
    public static String TEMPURL;
    public static String TEMPLOCATION;
}

interface call {
    void success(List<Card> list);
}