package com.example.bmobtest.Fragment.Entry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.bmobtest.Bean.Entry;
import com.example.bmobtest.Bean.User;
import com.example.bmobtest.Fragment.Personal.PersonalFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.EntryDetailUtil;
import com.example.bmobtest.Utils.Entry.EntryUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.likeview.RxShineButton;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 戚春阳 on 2018/2/6.
 */

public class EntryDetailFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_entry_title;
    private CircleImageView civ_entry_head;
    private TextView tv_entry_author;
    private TextView tv_entry_time;
    private TextView tv_entry_stars;
    private TextView tv_entry_content;
    private RxShineButton rxShineButton;
    private String entryId;
    private String authorId;
    private String categoryId;
    private String category;
    private Button btn_entry_detail_update;
    private Button btn_entry_detail_delete;
    private String title;
    private String content;
    private int stars;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry_detail, container, false);
        initView(v);
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
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
        tv_entry_title = (TextView) v.findViewById(R.id.tv_entry_title);
        civ_entry_head = (CircleImageView) v.findViewById(R.id.civ_entry_head);
        tv_entry_author = (TextView) v.findViewById(R.id.tv_entry_author);
        tv_entry_time = (TextView) v.findViewById(R.id.tv_entry_time);
        tv_entry_stars = (TextView) v.findViewById(R.id.tv_entry_stars);
        tv_entry_content = (TextView) v.findViewById(R.id.tv_entry_content);
        initFragmentData();
        updateFragmentData(entryId);
        initRxShineButton(v);
        civ_entry_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalFragment fragment = new PersonalFragment();
                Bundle bundle = new Bundle();
                bundle.putString("authorId", authorId);
                fragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fl_content, fragment).addToBackStack(null).commit();
            }
        });

        btn_entry_detail_update = (Button) v.findViewById(R.id.btn_entry_detail_update);
        btn_entry_detail_delete = (Button) v.findViewById(R.id.btn_entry_detail_delete);
        isDisplayTwoButton();
        update_delete_onClickListener();

    }

    private void initFragmentData() {
        Bundle b = getArguments();
        String authorId = b.getString("authorId");
        String entryId = b.getString("entryId");
        String categoryId = b.getString("categoryId");
        String category = b.getString("category");
        String author = b.getString("author");
        title = b.getString("title");
        content = b.getString("content");
        stars = b.getInt("stars");
        String time = b.getString("time");
        String head_url = b.getString("head_url");
        this.authorId = authorId;
        this.entryId = entryId;
        this.categoryId = categoryId;
        this.category = category;
        tv_entry_title.setText(title);
        tv_entry_author.setText(author);
        tv_entry_content.setText("\t\t\t\t" + content);
        tv_entry_stars.setText("" + stars);
        tv_entry_time.setText(time);
        //设置头像
        initHead(head_url);
    }


    private void updateFragmentData(String entryId) {
        EntryDetailUtil.update_entry(entryId, new EntryDetailUtil.update_entryCall() {
            @Override
            public void success(List<Entry> list) {
                Entry entry = list.get(0);
                String author = entry.getAuthor().getUsername();
                title = entry.getTitle();
                content = entry.getContent();
                stars = entry.getStars();
                //获取头像
                String head_url = "";
                try {
                    BmobFile file = entry.getAuthor().getFile();
                    head_url = file.getFileUrl();
                } catch (NullPointerException e) {
                    //当前用户无头像
                }

                tv_entry_title.setText(title);
                tv_entry_author.setText(author);
                tv_entry_content.setText("\t\t\t\t" + content);
                tv_entry_stars.setText("" + stars);
                //设置头像
                initHead(head_url);
            }


            public void fail() {
            }
        });

    }

    private void initRxShineButton(View v) {
        rxShineButton = (RxShineButton) v.findViewById(R.id.rsb);
        rxShineButton.setChecked(EntryUtil.state(entryId));
        rxShineButton.setOnCheckStateChangeListener(new RxShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean b) {
                int original_stars = Integer.parseInt(tv_entry_stars.getText().toString());
                if (b) {
                    int current_stars = original_stars + 1;
                    tv_entry_stars.setText(current_stars + "");
                    EntryUtil.increment_stars(entryId);
                    //增加记录
                    EntryUtil.record_light(entryId);
                } else {
                    int current_stars = original_stars - 1;
                    tv_entry_stars.setText(current_stars + "");
                    EntryUtil.decrese_stars(entryId);
                    //删除当前记录
                    EntryUtil.delete_record(entryId);
                }
            }
        });

    }

    //设置头像
    private void initHead(String head_url) {
        if (!TextUtils.isEmpty(head_url)) {
            Glide.with(getActivity()).load(head_url).into(civ_entry_head);
        } else {
            civ_entry_head.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    //根据当前的用户是否是作者来决定编辑和删除这两个按钮是否显示
    private void isDisplayTwoButton() {
        User user = BmobUser.getCurrentUser(User.class);
        String login_id = user.getObjectId();
        if (login_id.equals(authorId)) {
            btn_entry_detail_update.setVisibility(View.VISIBLE);
            btn_entry_detail_delete.setVisibility(View.VISIBLE);
        } else {
            btn_entry_detail_update.setVisibility(View.INVISIBLE);
            btn_entry_detail_delete.setVisibility(View.INVISIBLE);
        }
    }

    //两个按钮的点击事件
    private void update_delete_onClickListener() {
        btn_entry_detail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("确定删除吗？")
                        .negativeText("否")
                        .positiveText("是")
                        .positiveColor(getResources().getColor(R.color.red))
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                EntryDetailUtil.delete_entry(categoryId, entryId, new EntryDetailUtil.deleteCall() {
                                    @Override
                                    public void success() {
                                        RxToast.success("删除成功！");
                                        EntryUtil.isShouldRefersh = true;
                                        getActivity().onBackPressed();
                                    }

                                    @Override
                                    public void fail() {
                                        RxToast.error("删除失败！");
                                    }
                                });
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                            }
                        }).show();

            }
        });


        btn_entry_detail_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntryAddFragment fragment = new EntryAddFragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                bundle.putString("categoryId", categoryId);
                bundle.putString("entryId", entryId);
                bundle.putString("title", title);
                bundle.putString("content", content);
                fragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();


            }
        });

    }

}
