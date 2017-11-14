package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/28.
 */

public class Temporary_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private LinearLayout condition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temporary_file);

        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){
            finish();
        }
    }
}
