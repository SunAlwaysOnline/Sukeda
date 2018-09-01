package com.example.bmobtest.Fragment.SetMidFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.example.bmobtest.Fragment.SetMinFragment.DocumentDownloadFragment;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.FunctionStateUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;

/**
 * Created by 戚春阳 on 2018/1/31.
 */

public class SmallUtilFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ly_back;
    private LinearLayout ly_document;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_small_util, container, false);
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
        ly_document = (LinearLayout) v.findViewById(R.id.ly_document);
        ly_document.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_document:
                if (FunctionStateUtil.BaiDuDocumentDownload) {
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                    transaction.replace(R.id.fl_content, new DocumentDownloadFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    RxToast.info("该功能正在调整中！");
                }
        }

    }
}
