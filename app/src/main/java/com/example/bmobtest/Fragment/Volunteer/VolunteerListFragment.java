package com.example.bmobtest.Fragment.Volunteer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.VolunteerAdapter;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.example.bmobtest.Utils.Volunteer.VolunteerUtil;

import org.w3c.dom.Text;

/**
 * Created by 戚春阳 on 2018/2/5.
 */

public class VolunteerListFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_name;
    private TextView tv_time;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_volunteer_list, container, false);
        initView(v);
        initRecyclerView(v);
        return v;
    }

    private void initView(View v) {
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        tv_name = (TextView) v.findViewById(R.id.tv_volunteer_name);
        tv_time = (TextView) v.findViewById(R.id.tv_volunteer_time);
        tv_name.setText(VolunteerUtil.name);
        tv_time.setText(VolunteerUtil.time);
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_volunteer_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        initRecyclerViewData();

    }

    private void initRecyclerViewData() {
        VolunteerAdapter adapter = new VolunteerAdapter(R.layout.item_volunteer, VolunteerUtil.list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.isFirstOnly(false);
        recyclerView.setAdapter(adapter);
    }
}
