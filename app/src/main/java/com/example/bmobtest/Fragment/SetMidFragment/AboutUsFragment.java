package com.example.bmobtest.Fragment.SetMidFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bmobtest.Bean.User;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.PersonalUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 戚春阳 on 2018/2/7.
 */

public class AboutUsFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_phone;

    private TextView tv_description;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        tv_phone = (TextView) v.findViewById(R.id.tv_about_phone);
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:18806210604"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        tv_description = (TextView) v.findViewById(R.id.tv_about_description);
        tv_description.setText("\t\t\t\t"+"此款APP从开始构思到初步完成经历了两个多月的时间，弥补了教务系统在手机上使用不便的缺陷。"
        +"\n"+"\t\t\t\t"+"若在使用过程中遇到问题，可在设置界面中反馈建议，或直接使用上方提供的联系方式。"
                +"\n"+"\t\t\t\t"+"之所以开发这个项目，是因为借此来提升自己独立开发项目的能力。如果有想法一致的同学，欢迎联系我们，一起合作，一同进步，打造出更好更适用更多元化的应用出来！");
    }

}
