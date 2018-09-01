package com.example.bmobtest.Fragment.SetMinFragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.example.bmobtest.Activity.MainActivity;
import com.example.bmobtest.Bean.User;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by 戚春阳 on 2017/12/10.
 */

public class UpdatePasswordFragment extends Fragment {
    private LinearLayout ly_back;
    private LinearLayout ly_old_password;
    private LinearLayout ly_new_password;
    private EditText et_old_password;
    private EditText et_new_password;
    private Button btn_sure_to0;
    private Button btn_sure_to1;
    String oldpwd;
    String newpwd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_password, container, false);
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
        ly_old_password = (LinearLayout) v.findViewById(R.id.ly_old_password);
        ly_new_password = (LinearLayout) v.findViewById(R.id.ly_new_password);
        ly_new_password.setVisibility(View.INVISIBLE);
        et_old_password = (EditText) v.findViewById(R.id.et_old_password);
        et_new_password = (EditText) v.findViewById(R.id.et_new_password);
        btn_sure_to0 = (Button) v.findViewById(R.id.btn_sure_to0);
        btn_sure_to0.setOnClickListener(new verify_Password());
        btn_sure_to1 = (Button) v.findViewById(R.id.btn_sure_to1);
        btn_sure_to1.setVisibility(View.INVISIBLE);
        btn_sure_to1.setOnClickListener(new update_password());
    }


    class verify_Password implements View.OnClickListener {
        public void onClick(View v) {
            oldpwd = et_old_password.getText().toString().trim();
            if (TextUtils.isEmpty(oldpwd)) {
                Toast.show(getActivity(), "密码为空!", 1);
                return;
            } else {
                verifyPassword(oldpwd);
                if (TextUtils.isEmpty(newpwd)) {
                }
            }

        }
    }

    class update_password implements View.OnClickListener {
        public void onClick(View v) {
            newpwd = et_new_password.getText().toString().trim();
            if (TextUtils.isEmpty(newpwd)) {
                Toast.show(getActivity(), "新密码不能为空！", 0);
                return;
            } else {
                updatePassword(newpwd);
            }

        }
    }

    public void verifyPassword(String oldpwd) {
        BmobUser user = BmobUser.getCurrentUser();
        String id = user.getObjectId();
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", id);
        query.addWhereEqualTo("password", oldpwd);
        query.count(User.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    if (integer == 1) {
                        Toast.show(getActivity(), "旧密码验证成功！", 0);
                        ly_old_password.setVisibility(View.INVISIBLE);
                        btn_sure_to0.setVisibility(View.INVISIBLE);
                        ly_new_password.setVisibility(View.VISIBLE);
                        btn_sure_to1.setVisibility(View.VISIBLE);
                    } else {
                        Toast.show(getActivity(), "旧密码错误！", 0);
                    }
                } else {
                    Toast.show(getActivity(), "系统错误！" + e.getMessage(), 0);
                }
            }
        });

    }

    public void updatePassword(final String newpwd) {
        BmobUser user = BmobUser.getCurrentUser();
        String id = user.getObjectId();
        BmobUser bmobUser = new BmobUser();
        bmobUser.setPassword(newpwd);
        bmobUser.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.show(getActivity(), "密码重置成功！", 0);
                    Toast.show(getActivity(), "新密码" + newpwd, 0);
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } else {
                    Toast.show(getActivity(), "系统错误！" + e.getMessage(), 0);
                }
            }
        });
    }
}
