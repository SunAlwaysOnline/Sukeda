package com.example.bmobtest.Fragment;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.bmobtest.Activity.MainActivity;
import com.example.bmobtest.Bean.User;
import com.example.bmobtest.Bean.Version;
import com.example.bmobtest.Fragment.Advice.AdviceFragment;
import com.example.bmobtest.Fragment.SetMidFragment.AboutUsFragment;
import com.example.bmobtest.Fragment.SetMidFragment.BindingFragment;
import com.example.bmobtest.Fragment.MyMessage.MyMessageFragment;
import com.example.bmobtest.Fragment.SetMidFragment.SetAccountFragment;
import com.example.bmobtest.Fragment.SetMidFragment.StudentVerifyFragment;
import com.example.bmobtest.Fragment.SetMinFragment.UpdateVersionFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ShareUtil;
import com.example.bmobtest.Utils.SharedPreferenceUtils;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.Toast;
import com.vondear.rxtools.RxFileTool;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogScaleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * Created by 戚春阳 on 2017/12/9.
 */

public class SettingFragment extends Fragment {
    private FragmentManager fragmentManager;
    private CircleImageView civ_head;
    private TextView tv_username;
    private TextView tv_verify;
    private LinearLayout ly_authentication;
    private LinearLayout ly_message;
    private LinearLayout ly_binding;
    private LinearLayout ly_set_account;
    private LinearLayout ly_about_us;
    private LinearLayout ly_feedback;
    private LinearLayout ly_update;
    private Button btn_exit;
    //本地头像地址
    private View rootView = null;
    private String orign_head_url;
    private ImageButton ib_share;
    private ImageView iv_update_info;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        fragmentManager = getActivity().getSupportFragmentManager();
        tv_username = (TextView) v.findViewById(R.id.tv_username);
        getUsername();
        ly_authentication = (LinearLayout) v.findViewById(R.id.ly_authentication);
        ly_authentication.setOnClickListener(new VerifyStudentOnClickListener());
        tv_verify = (TextView) v.findViewById(R.id.tv_verify);
        changeColor(tv_verify, "st_id");
        ly_message = (LinearLayout) v.findViewById(R.id.ly_message);
        ly_message.setOnClickListener(new messageOnClickListener());
        ly_binding = (LinearLayout) v.findViewById(R.id.ly_binding);
        ly_binding.setOnClickListener(new bindingOnClickListener());
        ly_set_account = (LinearLayout) v.findViewById(R.id.ly_set_account);
        ly_set_account.setOnClickListener(new setAccountOnClickListener());
        ly_about_us = (LinearLayout) v.findViewById(R.id.ly_about_us);
        ly_about_us.setOnClickListener(new aboutUsOnClickListener());
        ly_feedback = (LinearLayout) v.findViewById(R.id.ly_feedback);
        ly_feedback.setOnClickListener(new feedBackOnClickListener());
        ly_update = (LinearLayout) v.findViewById(R.id.ly_update);
        ly_update.setOnClickListener(new updateOnClickListener());
        btn_exit = (Button) v.findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new exitOnClickListener());
        civ_head = (CircleImageView) v.findViewById(R.id.civ_head);
        civ_head.setOnClickListener(new headOnClickListener());
        initShare(v);
        initUpdateIfo(v);
        query_head();


    }


    private void initShare(View v) {
        ib_share = (ImageButton) v.findViewById(R.id.ib_share);
        ib_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                //intent.setType("image/png");
                //Bitmap bitmap = ShareUtil.getImageFromAssetsFile(getActivity(), "screenshot.png");
                //ShareUtil.saveBitmap(bitmap, getApplicationContext(), "screenshot");
                //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("/storage/emulated/0/Android/data/com.example.bmobtest/cache/screenshot.png"));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "\t\t\t\t苏科大安卓app发布啦,再也不用冒着眼瞎的危险上教务系统查看成绩啦，" +
                        "这里还可以快捷地查询一卡通的消费记录、课外学分、图书馆的书籍资料、办公电话等。\n" +
                        "\t\t\t\t实时浏览新闻、教务处的发文、公选课的介绍、学校年历、选课通知、对外考试等。\n" +
                        "\t\t\t\t包含多种知识频道，诸如奇闻、历史、常识、文学、教育、校园、实践、情感等等。\n" +
                        "\t\t\t\t更有免下载券下载百度会员文档、自动选课的黑科技。\n" +
                        "\t\t\t\t官网地址  http://sukeda.bmob.site/");
                startActivity(Intent.createChooser(intent, "分享到"));
            }
        });
    }

    private void initUpdateIfo(View v) {
        iv_update_info = (ImageView) v.findViewById(R.id.iv_update_info);
        //获取当前的版本号
        final int code = getVersionCode();
        //获取数据库中的版本
        BmobQuery<Version> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Version>() {
            @Override
            public void done(List<Version> list, BmobException e) {
                int new_code = list.get(0).getVersion();
                if (code < new_code) {
                    iv_update_info.setVisibility(View.VISIBLE);
                } else {
                    iv_update_info.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 根据缓存获取当前用户名
     */
    public void getUsername() {
        String username = (String) BmobUser.getObjectByKey("st_name");
        if (TextUtils.isEmpty(username)) {
            tv_username.setText("暂未获取到姓名");

        } else {
            tv_username.setText(username);
        }
    }

    private void changeColor(TextView t, String key) {
        String value = (String) BmobUser.getObjectByKey(key);
        if (value != null) {
            t.setText("已验证");
            t.setTextColor(Color.parseColor("#A8A8A8"));
        }

    }

    /**
     * 切换Fragment
     *
     * @param content
     * @param fragment
     */
    private void changeFragment(int content, Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                .replace(content, fragment)
                .addToBackStack(null)
                .commit();
    }

    //学生身份验证
    private class VerifyStudentOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new StudentVerifyFragment());
        }
    }

    //我的消息
    private class messageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new MyMessageFragment());
        }
    }

    //账号绑定
    private class bindingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new BindingFragment());
        }
    }

    //账号设置
    private class setAccountOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new SetAccountFragment());
        }
    }

    //关于我们
    private class aboutUsOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new AboutUsFragment());
        }
    }


    //反馈建议
    private class feedBackOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new AdviceFragment());
        }
    }

    //版本更新
    private class updateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ShowDialogUtil.showProgressDialog(getActivity(), "正在查询版本...");
            //获取当前的版本号
            final int code = getVersionCode();
            //获取数据库中的版本

            BmobQuery<Version> query = new BmobQuery<>();
            query.order("-createdAt");
            query.findObjects(new FindListener<Version>() {
                @Override
                public void done(List<Version> list, BmobException e) {
                    int new_code = list.get(0).getVersion();
                    if (code < new_code) {
                        changeFragment(R.id.fl_content, new UpdateVersionFragment());
                    } else {
                        Toast.show_info(getActivity(), "当前已经是最新版本!");
                    }
                    ShowDialogUtil.closeProgressDialog();


                }

            });
        }
    }

    /**
     * 退出登录的事件监听器
     */
    class exitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("确定要退出吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //清除缓存
                    BmobUser.logOut();
                    SharedPreferenceUtils.delete(getActivity());
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }


    private class headOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            List<String> list = new ArrayList<>();
            list.add("相册");
            list.add("照相机");
            if (orign_head_url != null) {
                list.add("查看大图");
            }
            new MaterialDialog.Builder(getActivity())
                    .title("选择图片来源")
                    .negativeText("取消")
                    .items(list)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            switch (position) {
                                case 0:
                                    RxPhotoTool.openLocalImage(SettingFragment.this);
                                    break;
                                case 1:
                                    //请求打开相机的权限
                                    PermissionUtils.permission(PermissionConstants.CAMERA).callback(new PermissionUtils.FullCallback() {
                                        @Override
                                        public void onGranted(List<String> permissionsGranted) {
                                            RxPhotoTool.openCameraImage(SettingFragment.this);
                                        }

                                        @Override
                                        public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                            RxToast.error("您拒绝了对打开相机的授权！");
                                        }
                                    }).request();
                                    break;

                                case 2:
                                    final RxDialogScaleView rxDialogScaleView = new RxDialogScaleView(getActivity());
                                    look_big_head(new look_big_headCall() {
                                        @Override
                                        public void success(final Bitmap bitmap) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    rxDialogScaleView.setImageBitmap(bitmap);
                                                    rxDialogScaleView.show();
                                                }
                                            });
                                        }
                                    });

                                    break;


                            }
                        }
                    }).show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null && RxPhotoTool.imageUriFromCamera == null) {
            return;
        }
        switch (requestCode) {
            //打开相机后的转向
            case 5001:
                RxPhotoTool.cropImage(SettingFragment.this, RxPhotoTool.imageUriFromCamera);
                break;
            //打开相册后选取图片后的转向
            case 5002:
                RxPhotoTool.cropImage(SettingFragment.this, data.getData());
                break;
            //裁剪后的转向
            case 5003:
                Glide.with(getActivity()).
                        load(RxPhotoTool.cropImageUri).
                        into(civ_head);
                //获得文件的真实路径
                String path = RxPhotoTool.getRealFilePath(getActivity(), RxPhotoTool.cropImageUri);
                Long l = RxFileTool.getFileAllSize(path);
                Log.e("tag", l + "");
                upLoadHead(path);
                break;
        }
    }

    private void query_head() {
        //查询此用户的头像
        try {
            JSONObject jsonObject = (JSONObject) BmobUser.getObjectByKey("file");
            String head_url = jsonObject.getString("url");
            orign_head_url = head_url;
            Glide.with(getActivity()).load(head_url).into(civ_head);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            //说明此用户还没有头像
        }
    }

    //上传头像
    private void upLoadHead(String path) {

        final BmobFile file = new BmobFile(new File(path));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                User user = BmobUser.getCurrentUser(User.class);
                user.setFile(file);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        RxToast.success("头像上传成功！");
                        delete_orign_head(file.getFileUrl());
                    }
                });
            }
        });


    }

    //一旦不是第一次上传头像时，则删掉之前的头像文件
    private void delete_orign_head(final String later_head_url) {
        if (orign_head_url != null) {
            BmobFile file = new BmobFile();
            file.setUrl(orign_head_url);
            file.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    //之前的头像删除成功
                    //保存当前的头像url
                    orign_head_url = later_head_url;
                }
            });
        } else {
            //保存当前的头像url
            orign_head_url = later_head_url;
        }
    }

    //查看大图操作
    private void look_big_head(final look_big_headCall call) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(getActivity())
                            .load(orign_head_url).asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (Exception e) {
                    RxToast.error("查看大图失败！");
                } finally {
                    if (bitmap != null) {
                        call.success(bitmap);
                    }
                }
            }
        }).start();

    }

    //获取当前版本号
    public int getVersionCode() {
        int code = 0;
        try {
            code = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public interface look_big_headCall {
        void success(Bitmap bitmap);
    }


}
