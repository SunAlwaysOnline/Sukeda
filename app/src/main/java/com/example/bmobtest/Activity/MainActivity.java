package com.example.bmobtest.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.bmobtest.Constant.AppValue;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.SharedPreferenceUtils;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.Toast;
import com.vondear.rxtools.RxTool;
import com.vondear.rxtools.view.RxToast;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_account;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        //初始化Bmob基础数据的SDK,并开启数据统计功能
        Bmob.initialize(this, AppValue.APPLICATION_ID, "bmob");
        initPush();
        RxTool.init(this);
        initViews();
        avoid_login();
        // test_login();


    }

    private void test_login() {
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    private void avoid_login() {
        final String account = SharedPreferenceUtils.spGetString(MainActivity.this, "avoid_login", "account");
        final String password = SharedPreferenceUtils.spGetString(MainActivity.this, "avoid_login", "password");
        if (account.equals("-1") && password.equals("-1")) {
            return;
        } else {
//            BmobUser bmobUser = new BmobUser();
//            bmobUser.loginByAccount(account, password, new LogInListener<BmobUser>() {
//                @Override
//                public void done(BmobUser bmobUser, BmobException e) {
//                    if (bmobUser != null) {
//                        Toast.show(MainActivity.this, "登录成功！", 0);
//                        //SharedPreferenceUtils.save(account, password, MainActivity.this);
//                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
//                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//                        finish();
//                    } else {
//                        Toast.show(MainActivity.this, "用户名或密码错误！" + e.getMessage(), 0);
//                    }
//
//                }
//            });
            et_account.setText(account);
            et_password.setText(password);
        }
    }

    private void initViews() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }


    public void onClick(View v) {
        final String account = et_account.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_register:
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    RxToast.warning("用户名或密码为空！");
                    return;
                } else {
                    ShowDialogUtil.showProgressDialog(MainActivity.this, "正在注册...");
                    BmobUser bmobUser = new BmobUser();
                    bmobUser.setUsername(account);
                    bmobUser.setPassword(password);
                    bmobUser.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                RxToast.success("注册成功！");
                                SharedPreferenceUtils.save(account, password, MainActivity.this);
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                finish();
                            } else {
                                RxToast.info("当前用户名已存在！");
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ShowDialogUtil.closeProgressDialog();
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    RxToast.warning("用户名或密码为空！");
                    return;
                } else {
                    ShowDialogUtil.showProgressDialog(MainActivity.this, "正在登录...");
                    BmobUser bmobUser = new BmobUser();
                    bmobUser.loginByAccount(account, password, new LogInListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (bmobUser != null) {
                                Toast.show_success(MainActivity.this, "登录成功！");
                                SharedPreferenceUtils.save(account, password, MainActivity.this);
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                finish();
                            } else {
                                RxToast.error("用户名或密码错误！");
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ShowDialogUtil.closeProgressDialog();
                                }
                            });
                        }
                    });
                }
                break;
        }
    }



  /*


  /*  public void QueryName(View v) {
        String name = et_queryName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "名字不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        BmobQuery<Feedback> query = new BmobQuery<Feedback>();
        query.addWhereEqualTo("name", name);
        query.findObjects(new FindListener<Feedback>() {
            @Override
            public void done(List<Feedback> list, BmobException e) {
                if (e == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("所有数据");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Feedback feedback : list) {
                        stringBuilder.append(feedback.getName() + ":" + feedback.getFeedback() + "\n");
                    }
                    builder.setMessage(stringBuilder);
                    builder.create().show();
                } else {
                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }*/


   /* public void Push(View v) {
        BmobPushManager manager = new BmobPushManager();
        manager.pushMessageAll("test123", new PushListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "推送成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }*/

    //初始化推送服务
    private void initPush() {
        //初始化数据服务SDK、初始化设备信息并启动推送服务
        // 初始化BmobSDK,已经完成
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        //启动推送服务
        BmobPush.startWork(this);
    }
}
