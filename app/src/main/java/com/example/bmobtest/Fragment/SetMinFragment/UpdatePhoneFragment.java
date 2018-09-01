package com.example.bmobtest.Fragment.SetMinFragment;

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
import android.widget.TextView;

import com.example.bmobtest.R;
import com.example.bmobtest.Utils.FunctionStateUtil;
import com.example.bmobtest.Utils.IsEmptyUtils;
import com.example.bmobtest.Utils.PhoneUtils;
import com.example.bmobtest.Utils.TimeCountUtil;
import com.example.bmobtest.Utils.Toast;
import com.vondear.rxtools.view.RxToast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 戚春阳 on 2017/12/10.
 */

public class UpdatePhoneFragment extends Fragment {
    private LinearLayout ly_back;
    /**
     * 返回键右边的具体操作对象名
     */
    private TextView tv_info;

    /**
     * 具体操作对象名
     */
    private EditText et_object;

    /**
     * 验证码输入
     */
    private EditText et_verify_code;
    /**
     * 获取验证码
     */
    private Button btn_get_verify_code;

    /**
     * 获取验证码的按钮倒计时功能
     */
    private TimeCountUtil timeCountUtil;
    /**
     * 中间的提示
     */
    private TextView tv_reminder;
    /**
     * 确定绑定还是确定修改
     */
    private Button btn_sure_to;

    private String phoneNumber;
    private String verify_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_phone, container, false);
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
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        et_object = (EditText) v.findViewById(R.id.et_object);
        et_object.setHint("手机号");
        IsEmptyUtils.judeg_is_null(tv_info, "mobilePhoneNumber", "绑定手机", "修改绑定的手机", et_object);
        et_verify_code = (EditText) v.findViewById(R.id.et_verify_code);
        btn_get_verify_code = (Button) v.findViewById(R.id.btn_get_verify_code);
        timeCountUtil = new TimeCountUtil(btn_get_verify_code, getActivity(), 60 * 1000, 1000);
        btn_get_verify_code.setOnClickListener(new getVerifyCodeOnClickListener());
        tv_reminder = (TextView) v.findViewById(R.id.tv_reminder);
        tv_reminder.setText("在此绑定您的手机号，可以在以后为您节省时间，并会去验证是否是您本人的手机号");
        btn_sure_to = (Button) v.findViewById(R.id.btn_sure_to);
        btn_sure_to.setText(" 确 认 绑 定 ");
        btn_sure_to.setOnClickListener(new verify_CodeOnClickListener());
    }


    class getVerifyCodeOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!FunctionStateUtil.PhoneVerifyCode) {
                RxToast.info("该功能正在调整！");
                return;
            }
            timeCountUtil.start();
            String phone = et_object.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                RxToast.warning("手机号码为空!");
                return;
            }
            if (!PhoneUtils.isMobileNO(phone)) {
                RxToast.warning("手机号码格式不正确");
                return;
            }
            phoneNumber = phone;
            getVerifyCode(phoneNumber);
        }
    }

    /**
     * 获取验证码
     *
     * @param phone 需绑定的手机号码
     */
    public void getVerifyCode(String phone) {
        BmobSMS.requestSMSCode(phone, "sendSMS", new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {//验证码发送成功
                    //Toast.show(getActivity(), "" + smsId, 0);
                } else {
                    Toast.show(getActivity(), "系统错误！" + ex.getMessage(), 0);
                    return;
                }
            }
        });
    }

    class verify_CodeOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            verify_code = et_verify_code.getText().toString();
            if (TextUtils.isEmpty(verify_code)) {
                Toast.show(getActivity(), "请先输入验证码！", 1);
                return;
            } else {
                verifyCode(phoneNumber, verify_code);
            }
        }
    }

    private void verifyCode(String phone, String code) {
        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
            public void done(BmobException ex) {
                if (ex == null) {//短信验证码已验证成功
                    Toast.show(getActivity(), "验证通过！", 0);
                    String objectId = (String) BmobUser.getObjectByKey("objectId");
                    BmobUser user = new BmobUser();
                    user.setMobilePhoneNumber(phoneNumber);
                    user.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.show(getActivity(), "手机号绑定成功！", 0);
                                getActivity().onBackPressed();
                            } else {
                                Toast.show(getActivity(), "绑定发生错误!" + e.getMessage(), 0);
                            }
                        }
                    });
                } else {
                    Toast.show(getActivity(), "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage(), 1);
                }
            }
        });
    }
}
