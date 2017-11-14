package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/15.
 */

public class AnimActivity extends BaseActivity {

    private ImageView welcome;
    private ImageView image;
    private LinearLayout header;
    private TextView version;
    private TextView welcome_word;
    private CircleImageView header2;
    private SharedPreferences userSettings;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlayout);

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

        //  StatusBarUtils.compat(this, Color.parseColor("#F5F5F5"));
        welcome=(ImageView) findViewById(R.id.welcome);
        header=(LinearLayout)findViewById(R.id.header);
        welcome_word=(TextView) findViewById(R.id.welcome_word);
        version=(TextView) findViewById(R.id.version);
        image=(ImageView)findViewById(R.id.image);
        header2=(CircleImageView) findViewById(R.id.header2);
        header.setVisibility(View.INVISIBLE);


        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version_string = info.versionName;
        version.setText("当前版本："+version_string);

        // todo 给版本设置字样

        //  初始化动画效果
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.alpha);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(AnimActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        userSettings = getSharedPreferences("user_changed", 0);
        String isChange=userSettings.getString("change","false");

        if(isChange.equals("false")){
            // 给图像添加动画效果
            image.startAnimation(animation);
        }else{
            String defString = PreferencesManager.getInstance(AnimActivity.this,"accountBean").get("jsonStr");
            Gson gson=new Gson();
            Logging_Success bean=gson.fromJson(defString,Logging_Success.class);

            if(!bean.getData().getAccountlink().equals("") && bean.getData().getAccountlink() != null){
                image.setVisibility(View.INVISIBLE);
                header.setVisibility(View.VISIBLE);
                Picasso.with(AnimActivity.this).load(bean.getData().getAccountlink()).memoryPolicy(MemoryPolicy.NO_CACHE).into(header2);
                welcome_word.setText(bean.getData().getOpName());
                header2.startAnimation(animation);
            }else{

                // 给图像添加动画效果
                image.startAnimation(animation);
            }
        }

    }
}
