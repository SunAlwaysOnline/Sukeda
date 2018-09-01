package com.example.bmobtest.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.bmobtest.Bean.Card;
import com.example.bmobtest.Bean.Grade;
import com.example.bmobtest.R;

import java.util.List;


/**
 * Created by 戚春阳 on 2017/12/13.
 */

public class CardAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Card> list;

    public CardAdapter(Context context, List<Card> list) {
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
            view = View.inflate(context, R.layout.group_card, null);
            groupHolder = new GroupHolder();
            groupHolder.tv_card_price = (TextView) view.findViewById(R.id.tv_card_price);
            groupHolder.tv_card_time = (TextView) view.findViewById(R.id.tv_card_time);
            view.setTag(groupHolder);
        } else {
            view = convertView;
            groupHolder = (GroupHolder) view.getTag();
        }
        groupHolder.tv_card_price.setText(list.get(i).getPrice());
        groupHolder.tv_card_time.setText(list.get(i).getTime());
        return view;
    }


    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        View view;
        ChildHolder childHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.child_card, null);
            childHolder = new ChildHolder();
            childHolder.tv_card_remain = (TextView) view.findViewById(R.id.tv_card_remain);
            childHolder.tv_card_type = (TextView) view.findViewById(R.id.tv_card_type);
            childHolder.tv_card_subSystem = (TextView) view.findViewById(R.id.tv_card_subSystem);
            childHolder.tv_card_times = (TextView) view.findViewById(R.id.tv_card_times);
            childHolder.tv_card_state = (TextView) view.findViewById(R.id.tv_card_state);
            view.setTag(childHolder);

        } else {
            view = convertView;
            childHolder = (ChildHolder) view.getTag();
        }
        childHolder.tv_card_remain.setText("当前余额:" + list.get(i).getRemain());
        childHolder.tv_card_type.setText("交易类型:" + list.get(i).getType());
        childHolder.tv_card_subSystem.setText("消费地点:" + list.get(i).getSubSystem());
        childHolder.tv_card_times.setText("消费次数:" + list.get(i).getTimes());
        childHolder.tv_card_state.setText("消费状态:" + list.get(i).getState());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class GroupHolder {
        TextView tv_card_price;
        TextView tv_card_time;
    }

    static class ChildHolder {
        TextView tv_card_remain;
        TextView tv_card_type;
        TextView tv_card_subSystem;
        TextView tv_card_times;
        TextView tv_card_state;


    }
}
