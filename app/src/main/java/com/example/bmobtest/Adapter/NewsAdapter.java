package com.example.bmobtest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bmobtest.Bean.News;
import com.example.bmobtest.R;

import java.util.List;


/**
 * Created by 戚春阳 on 2017/12/13.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private Context mContext;
    private int resourceId;
    private List<News> list;

    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
        this.list = objects;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder viewHolder;
        News news = getItem(position);
        if (convertView == null) {
            v = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_department = (TextView) v.findViewById(R.id.tv_department);
            viewHolder.tv_title = (TextView) v.findViewById(R.id.tv_title);
            viewHolder.tv_date = (TextView) v.findViewById(R.id.tv_date);
            viewHolder.tv_read = (TextView) v.findViewById(R.id.tv_read);
            v.setTag(viewHolder);
        } else {
            v = convertView;
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.tv_department.setText(news.getDepartment());
        viewHolder.tv_title.setText(news.getTitle());
        if (news.getRed() == true) {
            viewHolder.tv_title.setTextColor(Color.RED);
        } else {
            viewHolder.tv_title.setTextColor(Color.BLACK);
        }
        viewHolder.tv_date.setText(news.getDate());
        viewHolder.tv_read.setText(news.getRead());
        return v;
    }

    class ViewHolder {
        TextView tv_department;
        TextView tv_title;
        TextView tv_read;
        TextView tv_date;
    }
}
