package com.example.bmobtest.Fragment.SetMidFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bmobtest.Adapter.PhoneAdapter;
import com.example.bmobtest.Bean.DepartmentPhone;
import com.example.bmobtest.R;
import com.example.bmobtest.Utils.Entry.PersonalUtil;
import com.example.bmobtest.Utils.PhoneUtil;
import com.example.bmobtest.Utils.ShowDialogUtil;
import com.example.bmobtest.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 戚春阳 on 2018/2/16.
 */

public class PhoneFragment extends Fragment {
    private LinearLayout ly_back;
    private SearchView sv_phone;
    private TextView tv_department;
    private TextView tv_position;
    private Button btn_query;
    private RecyclerView rv_phone;
    //当前所选的部门或所属单位
    private String current_deparment;
    //当前所属的职位或办公地点
    private String current_position;
    //当前的电话号码
    private String current_phone;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phone, container, false);
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
        tv_department = (TextView) v.findViewById(R.id.tv_department);
        tv_position = (TextView) v.findViewById(R.id.tv_position);
        tv_department.setOnClickListener(new OnDepartmentClickListener());
        tv_position.setOnClickListener(new OnPositionClickListener());
        btn_query = (Button) v.findViewById(R.id.btn_query);
        btn_query.setOnClickListener(new OnQueryClickListener());
        initSearchView(v);
        initRecyclerView(v);

    }

    private void initSearchView(View v) {
        sv_phone = (SearchView) v.findViewById(R.id.sv_phone);
        sv_phone.setSubmitButtonEnabled(true);
        sv_phone.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sv_phone.clearFocus();
                ShowDialogUtil.showProgressDialog(getActivity(), "正在查询...");
                PhoneUtil.get_phone(query, new PhoneUtil.getPhone_mohuCall() {
                    @Override
                    public void success(final List<DepartmentPhone> list) {
                        ShowDialogUtil.closeProgressDialog();
                        new MaterialDialog.Builder(getActivity())
                                .title("点击即可跳转到拨号界面")
                                .negativeText("取消")
                                .items(list)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.get(position).getPhone()));
                                        getActivity().startActivity(i);
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void fail() {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    private void initRecyclerView(View v) {
        rv_phone = (RecyclerView) v.findViewById(R.id.rv_phone);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_phone.setLayoutManager(manager);
    }

    private void initRecyclerViewData(final List<DepartmentPhone> list) {
        PhoneAdapter adapter = new PhoneAdapter(R.layout.item_phone, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.isFirstOnly(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String phone = list.get(position).getPhone();
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                getActivity().startActivity(i);
            }
        });
        rv_phone.setAdapter(adapter);
    }

    private class OnDepartmentClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ShowDialogUtil.showProgressDialog(getActivity(), "正在查询部门或所属单位...");
            if (current_position != null) {
                initRecyclerViewData(null);
            }
            current_position = null;
            tv_position.setText("职位或办公地点>");
            PhoneUtil.getDepartment(new PhoneUtil.getDepartmentCall() {
                @Override
                public void success(List<DepartmentPhone> list) {
                    final List<String> Department = new ArrayList<String>();
                    for (DepartmentPhone departmentPhone : list) {
                        Department.add(departmentPhone.getDepartment());
                    }
                    ShowDialogUtil.closeProgressDialog();
                    new MaterialDialog.Builder(getActivity())
                            .title("选择部门或所属单位")
                            .negativeText("取消")
                            .items(Department)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    current_deparment = Department.get(position);
                                    tv_department.setText(current_deparment);
                                }
                            })
                            .show();
                }

                @Override
                public void fail() {
                    RxToast.error("查询失败！");
                }
            });
        }
    }

    private class OnPositionClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (current_deparment == null) {
                RxToast.warning("请先选择部门或所属单位！");
                return;
            }
            ShowDialogUtil.showProgressDialog(getActivity(), "正在查询职位或办公地点...");
            PhoneUtil.get_position_by_department(current_deparment, new PhoneUtil.getPositionCall() {
                @Override
                public void success(final List<DepartmentPhone> list) {
                    final List<String> Position = new ArrayList<String>();
                    for (DepartmentPhone departmentPhone : list) {
                        Position.add(departmentPhone.getPosition());
                    }
                    ShowDialogUtil.closeProgressDialog();
                    new MaterialDialog.Builder(getActivity())
                            .title("选择职位或办公地点")
                            .negativeText("取消")
                            .items(Position)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    current_position = Position.get(position);
                                    current_phone = list.get(position).getPhone();
                                    tv_position.setText(current_position);

                                }
                            })
                            .show();
                }

                @Override
                public void fail() {
                    RxToast.error("查询失败！");
                }
            });
        }
    }

    private class OnQueryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (current_deparment == null) {
                RxToast.warning("请至少选择一个部门或所属单位！");
                return;
            }
            if (current_deparment != null && current_position != null) {
                List<DepartmentPhone> list = new ArrayList<>();
                DepartmentPhone item = new DepartmentPhone();
                item.setDepartment(current_deparment);
                item.setPosition(current_position);
                item.setPhone(current_phone);
                list.add(item);
                initRecyclerViewData(list);
            }
            if (current_deparment != null && current_position == null) {
                PhoneUtil.get_position_by_department(current_deparment, new PhoneUtil.getPositionCall() {
                    @Override
                    public void success(List<DepartmentPhone> list) {
                        initRecyclerViewData(list);
                    }

                    @Override
                    public void fail() {

                    }
                });
            }

        }

    }
}
