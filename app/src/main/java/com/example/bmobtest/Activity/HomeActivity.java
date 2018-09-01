package com.example.bmobtest.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.example.bmobtest.Fragment.CommunityFragment;
import com.example.bmobtest.Fragment.EducationFragment;
import com.example.bmobtest.Fragment.SettingFragment;
import com.example.bmobtest.Fragment.SignFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.EntryUtil;
import com.example.bmobtest.Utils.FunctionStateUtil;
import com.vondear.rxtools.RxTool;

import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


/**
 *
 */
public class HomeActivity extends AppCompatActivity {
    private Fragment signFragment;
    private Fragment educationFragment;
    private Fragment communityFragment;
    private Fragment settingFragment;
    private FragmentManager fragmentManager;
    private NavigationController mNavigationController;
    private PageNavigationView pageBottomTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_home);
        Utils.init(getApplication());
        initViews();
        initTab();
        initFunctionState();
        //获取服务器时间
        EntryUtil.get_current_time();

    }

    private void initViews() {
        signFragment = new SignFragment();
        educationFragment = new EducationFragment();
        communityFragment = new CommunityFragment();
        settingFragment = new SettingFragment();
        fragmentManager = getSupportFragmentManager();
        //设置默认显示的Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_content, signFragment).addToBackStack(null).commit();
        //tv_home_top.setText("我要签到");

    }

    private void initTab() {
        pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);
        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.home, "主页", getResources().getColor(R.color.light_blue))
                .addItem(R.drawable.education, "教务", getResources().getColor(R.color.tan))
                .addItem(R.drawable.community, "交流", getResources().getColor(R.color.ori_main_color))
                .addItem(R.drawable.setting, "设置", 0xFF455A64)
                .setDefaultColor(0x89FFFFFF)//未选中状态的颜色
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR | MaterialMode.HIDE_TEXT)//这里可以设置样式模式，总共可以组合出4种效果
                .build();
        //设置Item选中事件的监听
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (index) {
                    case 0:
                        fragmentTransaction.replace(R.id.fl_content, signFragment).commit();
                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.fl_content, educationFragment).commit();
                        break;
                    case 2:
                        fragmentTransaction.replace(R.id.fl_content, communityFragment).commit();
                        break;
                    case 3:
                        fragmentTransaction.replace(R.id.fl_content, settingFragment).commit();
                        break;
                }
            }

            @Override
            public void onRepeat(int index) {
            }
        });

    }


    public void hidden_home_bottom() {
        pageBottomTabLayout.setVisibility(View.GONE);
    }

    public void show_home_bottom() {
        pageBottomTabLayout.setVisibility(View.VISIBLE);
    }

    private void initFunctionState() {
        FunctionStateUtil.get_function_state_document_download();
        FunctionStateUtil.get_function_state_entry();
        FunctionStateUtil.get_function_state_phone_verify();

    }

    public void onBackPressed() {
        show_home_bottom();
        int fragment_count = getSupportFragmentManager().getBackStackEntryCount();
        if (fragment_count == 1) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            //moveTaskToBack(false);
        } else {
            super.onBackPressed();
        }
    }
}
