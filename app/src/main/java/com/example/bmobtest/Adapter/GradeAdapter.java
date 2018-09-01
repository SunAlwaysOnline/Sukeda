package com.example.bmobtest.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bmobtest.Bean.Grade;
import com.example.bmobtest.Bean.Kwxf;
import com.example.bmobtest.R;

import java.util.List;


/**
 * Created by 戚春阳 on 2017/12/13.
 */

public class GradeAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Grade> list;

    public GradeAdapter(Context context, List<Grade> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        View view;
        GroupHolder groupHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.group_grade, null);
            groupHolder = new GroupHolder();
            groupHolder.tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
            groupHolder.tv_mark = (TextView) view.findViewById(R.id.tv_mark);
            view.setTag(groupHolder);
        } else {
            view = convertView;
            groupHolder = (GroupHolder) view.getTag();
        }
        groupHolder.tv_course_name.setText(list.get(i).getCourse_name());
        groupHolder.tv_mark.setText(list.get(i).getMark());
        return view;
    }


    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        View view;
        ChildHolder childHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.child_grade, null);
            childHolder = new ChildHolder();
            childHolder.tv_xn = (TextView) view.findViewById(R.id.tv_xn);
            childHolder.tv_xq = (TextView) view.findViewById(R.id.tv_xq);
            childHolder.tv_course_code = (TextView) view.findViewById(R.id.tv_course_code);
            childHolder.tv_course_nature = (TextView) view.findViewById(R.id.tv_course_nature);
            childHolder.tv_course_belong = (TextView) view.findViewById(R.id.tv_course_belong);
            childHolder.tv_credit = (TextView) view.findViewById(R.id.tv_credit);
            childHolder.tv_gpa = (TextView) view.findViewById(R.id.tv_gpa);
            childHolder.tv_minor_mark = (TextView) view.findViewById(R.id.tv_minor_mark);
            childHolder.tv_score_make_up = (TextView) view.findViewById(R.id.tv_score_make_up);
            childHolder.tv_score_rebuild = (TextView) view.findViewById(R.id.tv_score_rebuild);
            childHolder.tv_college = (TextView) view.findViewById(R.id.tv_college);
            childHolder.tv_remark = (TextView) view.findViewById(R.id.tv_remark);
            childHolder.tv_mark_rebuild = (TextView) view.findViewById(R.id.tv_mark_rebuild);
            view.setTag(childHolder);

        } else {
            view = convertView;
            childHolder = (ChildHolder) view.getTag();
        }
        childHolder.tv_xn.setText("学年:" + list.get(i).getXn());
        childHolder.tv_xq.setText("学期:" + list.get(i).getXq());
        childHolder.tv_course_code.setText("课程代码:" + list.get(i).getCourse_code());
        childHolder.tv_course_nature.setText("课程性质:" + list.get(i).getCourse_nature());
        childHolder.tv_course_belong.setText("课程归属:" + list.get(i).getCourse_belong());
        childHolder.tv_credit.setText("学分:" + list.get(i).getCredit());
        childHolder.tv_gpa.setText("绩点:" + list.get(i).getGpa());
        childHolder.tv_minor_mark.setText("辅修标记:" + list.get(i).getMinor_mark());
        childHolder.tv_score_make_up.setText("补考成绩:" + list.get(i).getScore_make_up());
        childHolder.tv_score_rebuild.setText("重修成绩:" + list.get(i).getScore_rebuild());
        childHolder.tv_college.setText("开课学院:" + list.get(i).getCollege());
        childHolder.tv_remark.setText("备注:" + list.get(i).getRemark());
        childHolder.tv_mark_rebuild.setText("重修标记:" + list.get(i).getMark_rebuild());


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class GroupHolder {
        TextView tv_course_name;
        TextView tv_mark;
    }

    static class ChildHolder {
        TextView tv_xn;
        TextView tv_xq;
        TextView tv_course_code;
        TextView tv_course_nature;
        TextView tv_course_belong;
        TextView tv_credit;
        TextView tv_gpa;
        TextView tv_minor_mark;
        TextView tv_score_make_up;
        TextView tv_score_rebuild;
        TextView tv_college;
        TextView tv_remark;
        TextView tv_mark_rebuild;
    }
}
