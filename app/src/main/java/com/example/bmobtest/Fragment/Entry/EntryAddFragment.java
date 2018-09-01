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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.EntryAddUtil;
import com.example.bmobtest.Utils.Entry.EntryUtil;
import com.vondear.rxtools.view.RxToast;

/**
 * Created by 戚春阳 on 2018/2/7.
 */

public class EntryAddFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_entry_add_category;
    private EditText et_entry_add_title;
    private EditText et_entry_add_content;
    private Button btn_entry_add;
    private String categoryId;
    private String category;
    private String entryId;
    private String update_title;
    private String update_content;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry_add, container, false);
        initFragmentData();
        initViews(v);
        return v;
    }

    private void initFragmentData() {
        this.categoryId = getArguments().getString("categoryId");
        this.category = getArguments().getString("category");
        this.update_title = getArguments().getString("title", "");
        this.update_content = getArguments().getString("content", "");
        this.entryId = getArguments().getString("entryId", "");
    }

    private void initViews(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        tv_entry_add_category = (TextView) v.findViewById(R.id.tv_entry_add_category);
        et_entry_add_title = (EditText) v.findViewById(R.id.et_entry_add_title);
        et_entry_add_content = (EditText) v.findViewById(R.id.et_entry_add_content);
        btn_entry_add = (Button) v.findViewById(R.id.btn_entry_add);
        tv_entry_add_category.setText(category);
        btn_entry_add.setOnClickListener(new OnEntryAddClickListener());
        et_entry_add_title.setText(update_title);
        et_entry_add_content.setText(update_content);


    }

    private class OnEntryAddClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String title = et_entry_add_title.getText().toString();
            String content = et_entry_add_content.getText().toString();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                RxToast.error("词条名称或词条内容不能为空！");
                return;
            }
            if (TextUtils.isEmpty(entryId)) {
                //说明是第一次添加的
                EntryAddUtil.addEntry(categoryId, title, content, new EntryAddUtil.addCall() {
                    @Override
                    public void success() {
                        RxToast.success("添加成功！");
                        EntryUtil.isShouldRefersh = true;
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void fail() {
                        RxToast.error("添加失败！");
                    }
                });
            } else {
                //是编辑转过来的
                EntryAddUtil.update_entry(entryId, title, content, new EntryAddUtil.updateCall() {
                    @Override
                    public void success() {
                        RxToast.success("编辑成功！");
                        EntryUtil.isShouldRefersh = true;
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.popBackStackImmediate("EntryDetailFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }

                    @Override
                    public void fail() {
                        RxToast.error("编辑失败！");
                    }
                });
            }
        }
    }
}
