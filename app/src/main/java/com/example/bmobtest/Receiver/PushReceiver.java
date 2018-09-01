package com.example.bmobtest.Receiver;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import com.example.bmobtest.Activity.HomeActivity;
import com.example.bmobtest.Activity.MainActivity;
import com.example.bmobtest.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by 戚春阳 on 2017/12/4.
 */

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "";
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            String msg = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            try {
                JSONObject object = new JSONObject(msg);
                message = object.getString("alert");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            PendingIntent contentIndent = PendingIntent.getActivity(context, 0, new Intent(context, HomeActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIndent)
                    .setSmallIcon(R.drawable.ic_info_outline_white_48dp)// 必须设置图片,否则无法正常推送
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.xiaohui))//设置状态栏里面的图标（小图标） 　　　　　　　　　　　　　　　　　　　　.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.i5))//下拉下拉列表里面的图标（大图标） 　　　　　　　.setTicker("this is bitch!") //设置状态栏的显示的信息
                    .setWhen(System.currentTimeMillis())//设置时间发生时间
                    .setAutoCancel(true)//设置可以清除
                    .setContentTitle("消息提示")//设置下拉列表里的标题
                    .setContentText(message);//设置上下文内容
            Notification notification = builder.getNotification();
            //加i是为了显示多条Notification
            notificationManager.notify(0, notification);
        }
    }
}

