package com.example.bmobtest.Fragment.MyMessage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bmobtest.Bean.Message;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;

/**
 * Created by 戚春阳 on 2018/2/25.
 */

public class MessageDetailFragment extends Fragment {
    private LinearLayout ly_back;
    private Message message;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_time;
    private TextView tv_author;
    private LinearLayout ly_phone;
    private LinearLayout ly_qq;
    private LinearLayout ly_weChat;
    private TextView tv_phone;
    private TextView tv_qq;
    private TextView tv_weChat;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message_detail, container, false);
        initData();
        initViews(v);
        return v;
    }

    private void initData() {
        message = (Message) getArguments().get("item");

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
        tv_title = (TextView) v.findViewById(R.id.tv_message_title);
        tv_content = (TextView) v.findViewById(R.id.tv_message_content);
        tv_time = (TextView) v.findViewById(R.id.tv_message_time);
        tv_author = (TextView) v.findViewById(R.id.tv_message_author);
        tv_title.setText("" + message.getTitle());
        tv_time.setText("" + message.getCreatedAt());
        tv_author.setText("" + message.getAuthor());
        tv_content.setText("\t\t\t\t" + message.getContent());
        ly_phone = (LinearLayout) v.findViewById(R.id.ly_message_phone);
        ly_qq = (LinearLayout) v.findViewById(R.id.ly_message_qq);
        ly_weChat = (LinearLayout) v.findViewById(R.id.ly_message_weChat);
        tv_phone = (TextView) v.findViewById(R.id.tv_message_phone);
        tv_qq = (TextView) v.findViewById(R.id.tv_message_qq);
        tv_weChat = (TextView) v.findViewById(R.id.tv_message_weChat);
        if (TextUtils.isEmpty(message.getPhone())) {
            ly_phone.setVisibility(View.GONE);
        } else {
            tv_phone.setText("" + message.getPhone());
        }
        if (TextUtils.isEmpty(message.getQq())) {
            ly_qq.setVisibility(View.GONE);
        } else {
            tv_qq.setText("" + message.getQq());
        }
        if (TextUtils.isEmpty(message.getWeChat())) {
            ly_weChat.setVisibility(View.GONE);
        } else {
            tv_weChat.setText("" + message.getWeChat());
        }
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + message.getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

    }
}
