package com.lanwei.governmentstar.activity.dzgd;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.view.View;
import android.widget.ImageView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/13.
 */

public class Renshi_Details_Activity extends BaseActivity {

    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_renshigui);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        initweights();
    }

    void initweights(){

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}
