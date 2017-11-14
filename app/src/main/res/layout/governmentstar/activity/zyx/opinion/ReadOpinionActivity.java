package com.lanwei.governmentstar.activity.zyx.opinion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Opinion;
import com.lanwei.governmentstar.bean.OpinionList;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.List;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/31.
 */

//阅办意见
public class ReadOpinionActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private TextView name;
    private TextView time;
    private TextView evopinion;
    private TextView ren;
    private TextView sj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        //设置沉浸式状态栏,必须在setContentView方法之后执行
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        }


        title = (TextView) findViewById(R.id.tv_address);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        ren = (TextView) findViewById(R.id.ren);
        sj = (TextView) findViewById(R.id.sj);


        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        evopinion = (TextView) findViewById(R.id.opinion);


        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);

        back.setOnClickListener(this);

        // TODO: opId 和 flowStatus 要等到数据完善以后根据不同的条目写成动态的
//        String opid = "70bbec48-69c4-4344-97de-b506e5210c7a";
        Intent intent = getIntent();
        String opid = intent.getStringExtra("opId");


        String flowStatus = intent.getStringExtra("flowStatus");
//        String flowStatus = "1";
        switch (flowStatus){
            case "1":
                title.setVisibility(View.VISIBLE);
                title.setText("查看收文");
                ren.setText("收文人 ： ");
                sj.setText("收文时间 ： ");
                break;
            case "2":
                title.setVisibility(View.VISIBLE);
                title.setText("拟办意见");
                ren.setText("拟办人 ： ");
                sj.setText("拟办时间 ： ");
                break;
            case "3":
                title.setVisibility(View.VISIBLE);
                title.setText("批示意见");
                ren.setText("批示人 ： ");
                sj.setText("批示时间 ： ");
                break;
            case "4":
                title.setVisibility(View.VISIBLE);
                title.setText("阅办意见");
                ren.setText("阅办人 ：");
                sj.setText("阅办时间 ： ");
                break;
//            case "5":
//                title.setVisibility(View.VISIBLE);
//                title.setText("承办意见");
//                ren.setText("承办人 ： ");
//                sj.setText("承办时间 ： ");
//                break;
//            case "6":
//                title.setVisibility(View.VISIBLE);
//                title.setText("办理意见");
//                ren.setText("办理人 ： ");
//                sj.setText("办理时间 ： ");
//                break;
        }
        getdata(opid,flowStatus);

    }
    private void getdata(String opid, String flowStatus) {

        //获取收文传阅的数据
        RetrofitHelper.getInstance().getOpinionInfo(opid, flowStatus, new CallBackYSAdapter() {

            @Override
            protected void showErrorMessage(String message) {
                Log.e("mes", message);
            }

            @Override
            protected void parseJson(String data) {
                Log.e("data", data);
                if (data != null) {
                    Gson gson = new Gson();
                    OpinionList opinionList = gson.fromJson(data, OpinionList.class);
                    List<Opinion> data1 = opinionList.getData();
                    if (data1.size() < 1){

                        return;
                    }
                    Opinion opinion = data1.get(0);

                    showData(opinion);
                }
            }
        });
    }

    /**
     * 展示数据
     * @param opinion
     */
    private void showData(Opinion opinion) {
        name.setText(opinion.getOpCreateName());
        time.setText(opinion.getOpCreateTime());
        evopinion.setText(opinion.getOpOpinion());
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
