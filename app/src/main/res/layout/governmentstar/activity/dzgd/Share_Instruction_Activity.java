package com.lanwei.governmentstar.activity.dzgd;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/4.
 */

public class Share_Instruction_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nizhi_share);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        back = (ImageView) findViewById(R.id.back);
        content = (TextView) findViewById(R.id.content);

        back.setOnClickListener(this);
        content.setText(getIntent().getStringExtra("content"));

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){
            finish();
        }
    }
}
