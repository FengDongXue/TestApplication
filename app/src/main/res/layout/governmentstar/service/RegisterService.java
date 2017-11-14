package com.lanwei.governmentstar.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.LoggingActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.personState.PersonState;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.ShortcutBadger;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技—技术部2 on 2017/5/5.
 */

public class RegisterService extends Service {

    public static boolean isForeground = false;
    private JSONObject dataJson;
    private int allNum;
    private boolean threadDisable = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isForeground = true;
        initm();
        registerMessageReceiver();
//        JpushUtil.setAlias(this, "normal");
        setNotification();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!threadDisable) {
//                    try {
//                        Thread.sleep(1000000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    allNum();
////                    count++;
//                }
//            }
//        }).start();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent localIntent = new Intent();
        localIntent.setClass(this, RegisterService.class);
        this.startService(localIntent);

        unregisterReceiver(mMessageReceiver);
        threadDisable = true;
        allNum = 0;
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void initm() {
        JPushInterface.init(getApplicationContext());
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.lanwei.governmentstar.activity.LoggingActivity";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);

    }

    private void allNum() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
//                            final Intent intent = getIntent();
//                            String userId = intent.getStringExtra("userId");
        String userId = new GetAccount(RegisterService.this).opId();
        Call<JsonObject> call1 = api.getTotalNum(userId);
        call1.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                                    popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("~~~~~", String.valueOf(jsonObject));
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        Log.e("~~~~~", dataJson.toString());
                        int gwnz_num = (int) dataJson.get("gwnz_num");
                        int gwnzNum = dataJson.getInt("gwnz_num");
                        Log.e("~~~~~", gwnzNum + "============" + gwnz_num);
                        int manageNum = dataJson.getInt("manage_num");
                        int ggtzNum = dataJson.getInt("ggtz_num");
                        int dbsxNum = dataJson.getInt("dbsx_num");
                        int swcyNum = dataJson.getInt("swcy_num");

                        Log.e("~~~~~", gwnzNum + manageNum + ggtzNum + dbsxNum + swcyNum + "");
                        allNum = gwnzNum + manageNum + ggtzNum + dbsxNum + swcyNum;
//                        ShortcutBadger.applyCount(getApplicationContext(), allNum);// 发送未读消息数目广播

                        //发送广播
                        Intent intent = new Intent();
                        intent.putExtra("allNum", allNum);
                        intent.setAction("com.lanwei.governmentstar.service");
                        sendBroadcast(intent);

                    }
//                                        Toast.makeText(DocumentSHActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                                    popupWindowUtil.dismiss();
            }
        });
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            CustomPushNotificationBuilder builder = new
                    CustomPushNotificationBuilder(RegisterService.this,
                    R.layout.customer_notitfication_layout,
                    R.id.icon,
                    R.id.title,
                    R.id.text);
            // 指定定制的 Notification Layout
            builder.statusBarDrawable = R.drawable.icon;
            // 指定最顶层状态栏小图标
            builder.layoutIconDrawable = R.drawable.icon;
            // 指定下拉状态栏时显示的通知图标
            JPushInterface.setPushNotificationBuilder(2, builder);

            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!PersonState.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            } else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
                Log.i("Register", "用户点击打开了通知");
                // 在这里可以自己写代码去定义用户点击后的行为
                Intent i = new Intent(context, LoggingActivity.class);  //自定义打开的界面
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }


    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setNotification() {

    }

}