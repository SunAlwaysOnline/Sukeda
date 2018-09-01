package com.example.bmobtest.Fragment.SetMidFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bmobtest.Activity.HomeActivity;
import com.example.bmobtest.Fragment.SetMinFragment.UpdatePhoneFragment;
import com.example.bmobtest.Fragment.SetMinFragment.UpdateQQFragment;
import com.example.bmobtest.Fragment.SetMinFragment.UpdateWeChatFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;


import cn.bmob.v3.BmobUser;

/**
 * Created by 戚春阳 on 2017/12/14.
 */

public class BindingFragment extends Fragment {
    private LinearLayout ly_back;
    private FragmentManager fragmentManager;
    private LinearLayout ly_phone;
    private LinearLayout ly_qq;
    private LinearLayout ly_wechat;
    private TextView tv_phone;
    private TextView tv_qq;
    private TextView tv_wechat;
    private ImageButton ib_back;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_binding, container, false);
        initViews(v);
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
        tv_phone = (TextView) v.findViewById(R.id.tv_phone);
        changeColor(tv_phone, "mobilePhoneNumber");
        ly_phone = (LinearLayout) v.findViewById(R.id.ly_phone);
        ly_phone.setOnClickListener(new phoneOnClickListener());
        ly_qq = (LinearLayout) v.findViewById(R.id.ly_qq);
        ly_qq.setOnClickListener(new updateQQOnClickListener());
        tv_qq = (TextView) v.findViewById(R.id.tv_qq);
        changeColor(tv_qq, "qq");
        ly_wechat = (LinearLayout) v.findViewById(R.id.ly_wechat);
        ly_wechat.setOnClickListener(new updateWeChatOnClickListener());
        tv_wechat = (TextView) v.findViewById(R.id.tv_wechat);
        changeColor(tv_wechat, "weChat");
        ib_back = (ImageButton) v.findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }


    private void changeColor(TextView t, String key) {
        String value = (String) BmobUser.getObjectByKey(key);
        if (value != null) {
            t.setText(value);
            t.setTextColor(Color.parseColor("#A8A8A8"));
        }

    }

    private void changeFragment(int content, Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                .replace(content, fragment)
                .addToBackStack(null)
                .commit();
    }

    private class phoneOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new UpdatePhoneFragment());
        }
    }

    private class updateQQOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new UpdateQQFragment());
        }
    }

    private class updateWeChatOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new UpdateWeChatFragment());
        }
    }
}
