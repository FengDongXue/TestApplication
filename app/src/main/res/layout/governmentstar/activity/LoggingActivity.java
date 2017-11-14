package com.lanwei.governmentstar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.HomeActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.PreferencesManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.ViewGroup.LayoutParams;

import org.json.JSONObject;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/15.
 */

public class LoggingActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MyReceiver";

    private EditText name;
    private EditText password;
    private TextView forget;
    private TextView agree;
    private TextView logging;
    private TextView service;
    private ImageView web;
    private ImageView phone;
    private ImageView page;
    private FrameLayout shape;
    private ImageView right;
    private LinearLayout mn;
    private PopupWindow popupWindow;
    private GovernmentApi api;
    private String name_remember;
    private String password_remember;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS2 = 2;
    // 获取系统sharedpreferences，获取用户的
    private SharedPreferences userSettings;
    private SharedPreferences.Editor editor;
    private JSONObject dataJson;
    private int allNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);//必须放在setContentView方法前面
//        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED ：窗口锁屏显示
//        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON ：屏幕点亮

        // 是否之前登录的标志
        userSettings = getSharedPreferences("user_changed", 0);
        String isChange = userSettings.getString("change", "false");
        if (isChange.equals("true")) {
            Intent it = new Intent(LoggingActivity.this, HomeActivity.class);
            startActivity(it); // 执行
            finish();
        } else {
            setContentView(R.layout.logginglayout);
            editor = userSettings.edit();

            intweight();
            // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }


    void intweight() {

        name = (EditText) findViewById(R.id.et);
        password = (EditText) findViewById(R.id.et2);

        name.setSelection(name.getText().length());
        password.setSelection(password.getText().length());

        forget = (TextView) findViewById(R.id.forget);
        agree = (TextView) findViewById(R.id.agree);
        logging = (TextView) findViewById(R.id.logging);
        service = (TextView) findViewById(R.id.service);
        web = (ImageView) findViewById(R.id.web);
        phone = (ImageView) findViewById(R.id.phone);
        page = (ImageView) findViewById(R.id.page);
        shape = (FrameLayout) findViewById(R.id.shape);
        right = (ImageView) findViewById(R.id.right);
        mn = (LinearLayout) findViewById(R.id.mn);

        phone.setVisibility(View.INVISIBLE);
        mn.setVisibility(View.INVISIBLE);
        logging.setSelected(true);

        forget.setOnClickListener(this);
        agree.setOnClickListener(this);
        page.setOnClickListener(this);
        service.setOnClickListener(this);
        web.setOnClickListener(this);
        phone.setOnClickListener(this);
        page.setOnClickListener(this);
        shape.setOnClickListener(this);
        right.setOnClickListener(this);
        logging.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.logging:

                // 加载popupwindow的布局

                View view = getLayoutInflater().inflate(R.layout.loading, null);
                popupWindow = new PopupWindow(view, 500, LayoutParams.WRAP_CONTENT, true);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(false);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

                View rootview = LayoutInflater.from(LoggingActivity.this).inflate(R.layout.logginglayout, null);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

                // 加载动画效果
                Animation animation = AnimationUtils.loadAnimation(LoggingActivity.this, R.anim.loading_anim);
                ImageView rotate = (ImageView) view.findViewById(R.id.rotate);
                rotate.startAnimation(animation);

                api = HttpClient.getInstance().getGovernmentApi();

                final String name_string = name.getText().toString().trim();
                final String password_string = password.getText().toString().trim();

                if (name_string.equals("") || password_string.equals("")) {

                    Toast.makeText(LoggingActivity.this, "请输入账号或密码！", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                } else {

                    Call<Logging_Success> call = api.logging(name_string, password_string);

                    call.enqueue(new Callback<Logging_Success>() {
                        @Override
                        public void onResponse(Call<Logging_Success> call, Response<Logging_Success> response) {
                            if (response.body().getData() == null) {
                                Toast.makeText(LoggingActivity.this, "账号不存在，请重新输入！", Toast.LENGTH_SHORT).show();
                            } else {
                                name_remember = name_string;
                                password_remember = password_string;

                                Gson gson = new Gson();
                                PreferencesManager.getInstance(LoggingActivity.this, "accountBean").put("jsonStr", gson.toJson(response.body()));

                                Intent it = new Intent(LoggingActivity.this, HomeActivity.class);
                                startActivity(it); // 执行

                                // 设置登录成功标记，以便二次登录
                                editor.putString("change", "true");
                                editor.commit();

                                finish();
                            }
                            popupWindow.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Logging_Success> call, Throwable t) {

                            popupWindow.dismiss();

                            Toast.makeText(LoggingActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                            Log.d("LOG", t.toString());

                        }
                    });
                }


                break;

            case R.id.forget:
                //忘记密码
                Intent intent = new Intent(this, Forget_Activity.class);
                startActivity(intent);

                break;

            case R.id.agree:
                //服务协议
                Intent intent2 = new Intent(this, ServiceCenter_Activity.class);
                startActivity(intent2);

                break;

            case R.id.web:
//                //访问产品网站
                Intent intent3 = new Intent(this, Web_Activity.class);
                startActivity(intent3);

                break;

            case R.id.page:
                //访问维护页面
                Intent intent5 = new Intent(this, Produce_Activity.class);
                startActivity(intent5);

                break;

            case R.id.phone:
                if (ContextCompat.checkSelfPermission(LoggingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoggingActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS2);

                } else {
                    callPhone();
                }

                break;
            case R.id.service:

                if (mn.getVisibility() == View.VISIBLE) {
                    mn.setVisibility(View.INVISIBLE);
                    phone.setVisibility(View.INVISIBLE);
                } else {
                    mn.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.shape:
            case R.id.right:
                if (right.getVisibility() == View.VISIBLE) {
                    right.setVisibility(View.INVISIBLE);
                    logging.setSelected(false);
                    logging.setClickable(false);

                } else {
                    right.setVisibility(View.VISIBLE);
                    logging.setSelected(true);
                    logging.setClickable(true);
                }

                break;
        }
    }


    // 拨打电话权限的申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_CONTACTS2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    callPhone();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(LoggingActivity.this, "您已拒绝了授权打电话权限,如需重新授权请到手机设置里更改", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    //拨打电话操作
    public void callPhone() {
        Intent intent2 = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "4006970960");
        intent2.setData(data);
        try {
            startActivity(intent2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    private boolean copyApkFromAssets(Context context, String fileName, String targetPath) {
        boolean isCopySuccess = false;

        try {
            //从assets下创建输入流
            InputStream is = context.getAssets().open(fileName);

            //新建保存路径下的file，构建输出流
            File file = new File(targetPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(file);

            //读写
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
                os.flush();
            }
            os.close();
            is.close();

            isCopySuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isCopySuccess;
    }

}
