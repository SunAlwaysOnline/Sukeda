package com.example.bmobtest.Fragment.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bmobtest.Bean.User;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.PersonalUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 戚春阳 on 2018/2/7.
 */

public class PersonalFragment extends Fragment {
    private LinearLayout ly_back;
    private String authorId;
    private CircleImageView civ_head;
    private TextView tv_name;
    private TextView tv_academy;
    private TextView tv_grade;
    private TextView tv_major;
    private TextView tv_description;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, container, false);
        initFragmentData();
        initView(v);
        get_data();
        return v;
    }

    private void initFragmentData() {
        this.authorId = getArguments().getString("authorId");
        Log.e("tag", authorId + "authorId");
    }

    private void initView(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        civ_head = (CircleImageView) v.findViewById(R.id.civ_personal_head);
        tv_name = (TextView) v.findViewById(R.id.tv_personal_name);
        tv_academy = (TextView) v.findViewById(R.id.tv_personal_academy);
        tv_grade = (TextView) v.findViewById(R.id.tv_personal_grade);
        tv_major = (TextView) v.findViewById(R.id.tv_personal_majory);
        tv_description = (TextView) v.findViewById(R.id.tv_personal_description);
    }

    //获取数据
    private void get_data() {
        PersonalUtil.get_personal_info(authorId, new PersonalUtil.get_personal_infoCall() {
            @Override
            public void success(List<User> list) {
                User user = list.get(0);
                String name = user.getUsername();
                String academy = "";
                if (TextUtils.isEmpty(user.getAcademy())) {
                    academy = "暂未获取";
                } else {
                    academy = user.getAcademy();
                }
                String major = "";
                if (TextUtils.isEmpty(user.getMajor())) {
                    major = "暂未获取";
                } else {
                    major = user.getMajor();
                }
                String grade = "";
                if (user.getGrade() == null || user.getGrade() == 0) {
                    grade = "暂未获取";
                } else {
                    grade = user.getGrade() + "";
                }
                String descritpion = "";
                if (TextUtils.isEmpty(user.getDescription())) {
                    descritpion = "没有描述";
                } else {
                    descritpion = user.getDescription();
                }
                tv_name.setText(name + "");
                tv_academy.setText(academy + "");
                tv_major.setText(major + "");
                tv_grade.setText(grade + "");
                tv_description.setText(descritpion + "");
                setHead(user);

            }

            @Override
            public void fail() {

            }
        });
    }


    //设置头像
    private void setHead(User user) {
        BmobFile file = user.getFile();
        String url = "";
        try {
            url = file.getFileUrl();
        } catch (NullPointerException e) {
            //当前用户无头像
        }
        if (!TextUtils.isEmpty(url)) {
            Glide.with(getActivity()).load(url).into(civ_head);
        } else {
            civ_head.setImageResource(R.mipmap.ic_launcher_round);
        }
    }
}
