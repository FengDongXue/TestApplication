package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/20.
 */

public class Feedback_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private TextView submit;
    private TextView content;
    private LinearLayout condition;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_feedback);

        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果,只是将系统状态栏变透明，我们的布局会被状态栏掩盖，因此注意小心布局的掩盖问题
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else {
//            condition=(LinearLayout) findViewById(R.id.condition);
//            condition.setVisibility(View.GONE);
//        }


        if (Build.VERSION.SDK_INT >= 21){
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff

        }

        initweight();

        // todo 获取用户将要反馈的建议内容

    }

    void initweight(){

        back=(ImageView) findViewById(R.id.back);
        submit=(TextView) findViewById(R.id.submit);
        content=(TextView) findViewById(R.id.content);

        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back:

                finish();
                break;

            case R.id.submit:

                // todo 提交用户反馈内容

                Toast.makeText(this,"提交成功",Toast.LENGTH_SHORT).show();

                break;
        }

    }
}
