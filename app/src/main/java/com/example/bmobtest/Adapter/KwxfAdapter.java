package com.example.bmobtest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bmobtest.Bean.Kwxf;
import com.example.bmobtest.R;


import java.util.List;


/**
 * Created by 戚春阳 on 2017/12/13.
 */

public class KwxfAdapter extends ArrayAdapter<Kwxf> {
    private CardView cardView;
    private Context mContext;
    private int resourceId;
    private List<Kwxf> list;

    public KwxfAdapter(Context context, int resource, List<Kwxf> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
        this.list = objects;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder viewHolder;
        Kwxf kwxf = getItem(position);
        if (convertView == null) {
            v = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_kwxf_time = (TextView) v.findViewById(R.id.tv_kwxf_time);
            viewHolder.tv_kwxf_name = (TextView) v.findViewById(R.id.tv_kwxf_name);
            viewHolder.tv_kwxf_grade = (TextView) v.findViewById(R.id.tv_kwxf_grade);
            viewHolder.tv_kwxf_pass = (TextView) v.findViewById(R.id.tv_kwxf_pass);
            v.setTag(viewHolder);
        } else {
            v = convertView;
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.tv_kwxf_time.setText(kwxf.getTime());
        viewHolder.tv_kwxf_name.setText(kwxf.getName());
        viewHolder.tv_kwxf_grade.setText(String.valueOf(kwxf.getGrade()));
        viewHolder.tv_kwxf_pass.setText(kwxf.getPass());
        return v;
    }

    class ViewHolder {
        TextView tv_kwxf_time;
        TextView tv_kwxf_name;
        TextView tv_kwxf_grade;
        TextView tv_kwxf_pass;
    }
}
