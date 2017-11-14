package com.lanwei.governmentstar.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.LoggingActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentActivity;
import com.lanwei.governmentstar.activity.zwyx.EmailActivity;
import com.lanwei.governmentstar.activity.zyx.HomeActivity;
import com.lanwei.governmentstar.activity.zyx.MyhandActivity;
import com.lanwei.governmentstar.activity.zyx.application.GovApplication;
import com.lanwei.governmentstar.personState.PersonState;
import com.lanwei.governmentstar.service.RegisterService;
import com.lanwei.governmentstar.utils.ShortcutBadger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.StringTokenizer;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/12.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "LLL";
    private HashMap<String, String> jsonMap = new HashMap<>();
    private int num;
    private Intent num1;
    private SharedPreferences amount_ShortcutBadger;


    /**
     * JPushInterface.EXTRA_EXTRA
     * *保存服务器推送下来的附加字段。这是个 JSON 字符串。
     * *对应 API 消息内容的 extras 字段。
     * *对应 Portal 推送消息界面上的“可选设置”里的附加字段。
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();//获取通过intent传递数据  niq
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了通知");
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.d("极光推送", json);
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("manageNum")) {
                    String manageNum = jsonObject.getString("manageNum");
//                    title = jsonObject.getString("title");
                    num = Integer.parseInt(manageNum);
                    ShortcutBadger.applyCount(context, num);
                    amount_ShortcutBadger = context.getSharedPreferences("amount_ShortcutBadger", 0);
                    amount_ShortcutBadger.edit().putInt("number", num).commit();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            Log.i(TAG, "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
//            if (title != null && title.contains("邮件")) {
//                Intent i = new Intent(context, EmailActivity.class);  //自定义打开的界面
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            } else if (title != null && title.contains("拟制")) {
//                Intent i = new Intent(context, DocumentActivity.class);  //自定义打开的界面
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            } else if (title != null && title.contains("传阅")) {
//                Intent i = new Intent(context, MyhandActivity.class);  //自定义打开的界面
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            }
            Intent i = new Intent(context, LoggingActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }

    }

//    public static void setNotification() {
////        Context context = null;
////        NotificationManager manager = (NotificationManager) context
////                .getSystemService(Context.NOTIFICATION_SERVICE);
////
////        // 使用notification
////        // 使用广播或者通知进行内容的显示
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(
////                context);
////        builder.setDefaults(Notification.DEFAULT_SOUND);
////        manager.notify(1,builder.build());
//
//        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
//                LoggingActivity.this, R.layout.customer_notitfication_layout,
//                R.id.icon, R.id.title, R.id.text);
//// 指定定制的 Notification Layout
//        builder.statusBarDrawable = R.drawable.icon;
//// 指定最顶层状态栏小图标
////        builder2.layoutIconDrawable = R.drawable.icon;
//// 指定下拉状态栏时显示的通知图标
//        JPushInterface.setPushNotificationBuilder(2, builder);
//
//
////        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
////                App.getAppContext(),
////                null, R.id.icon, R.id.title,
////                R.id.text);
////        builder.layoutIconDrawable = R.mipmap.ic_launcher;
////        builder.developerArg0 = "developerArg2";
////        JPushInterface.setPushNotificationBuilder(2, builder);
//    }


    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (RegisterService.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(RegisterService.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(RegisterService.KEY_MESSAGE, message);
            if (!PersonState.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(RegisterService.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            context.sendBroadcast(msgIntent);
        }
    }
}
