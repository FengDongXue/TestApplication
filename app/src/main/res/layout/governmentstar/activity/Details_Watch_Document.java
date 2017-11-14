package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/10.
 */

public class Details_Watch_Document extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private TextView drawer;
    private TextView time;
    private TextView type;
    private TextView theme;
    private TextView advice;
    private TextView signer;
    private LinearLayout condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_see2);

        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            condition = (LinearLayout) findViewById(R.id.condition);
            condition.setVisibility(View.GONE);
        }

        initweight();

    }

    void initweight() {
        back = (ImageView) findViewById(R.id.back);
        drawer = (TextView) findViewById(R.id.drawer);
        time = (TextView) findViewById(R.id.time);
        type = (TextView) findViewById(R.id.type);
        theme = (TextView) findViewById(R.id.theme);
        advice = (TextView) findViewById(R.id.advice);
        signer = (TextView) findViewById(R.id.signer);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                finish();
                break;


        }
    }



}
