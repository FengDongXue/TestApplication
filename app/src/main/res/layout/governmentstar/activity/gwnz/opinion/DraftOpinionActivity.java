package com.lanwei.governmentstar.activity.gwnz.opinion;

import android.content.Intent;
import android.graphics.Color;
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

//起草意见
public class DraftOpinionActivity extends BaseActivity implements View.OnClickListener {

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
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff

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
//        String opid = "5352554c-bad2-482e-82a2-f14b9df01b3a";
//        String opid = (String) SharedPreferencesUtil.getData(this, "itemId", "5352554c-bad2-482e-82a2-f14b9df01b3a");
//        String flowStatus = (String) SharedPreferencesUtil.getData(this, "flowStatus", "2");
        Intent intent = getIntent();
        String opid = intent.getStringExtra("opId");
        String flowStatus = intent.getStringExtra("flowStatus");
        switch (flowStatus){
            case "2":
                title.setVisibility(View.VISIBLE);
                title.setText("查看核稿意见");
                ren.setText("核稿人 ： ");
                sj.setText("时间 ： ");
                break;
            case "3":
                title.setVisibility(View.VISIBLE);
                title.setText("查看审阅意见");
                ren.setText("审阅人 ： ");
                sj.setText("时间 ： ");
                break;
            case "4":
                title.setVisibility(View.VISIBLE);
                title.setText("查看校对意见");
                ren.setText("校对人 ： ");
                sj.setText("时间 ： ");
                break;
            case "5":
                title.setVisibility(View.VISIBLE);
                title.setText("查看签发意见");
                ren.setText("签发人 ： ");
                sj.setText("时间 ： ");
                break;
//            case "6":
//                title.setVisibility(View.VISIBLE);
//                title.setText("会签意见");
//                ren.setText("会签人 ： ");
//                sj.setText("会签时间 ： ");
//                break;
            case "7":
                title.setVisibility(View.VISIBLE);
                title.setText("查看核发意见");
                ren.setText("核发人 ： ");
                sj.setText("时间 ： ");
                break;
            case "8":
                title.setVisibility(View.VISIBLE);
                title.setText("查看终止意见");
                ren.setText("终止人 ： ");
                sj.setText("时间 ： ");
                break;
        }
        getdata(opid,flowStatus);

    }
    private void getdata(String opid, String flowStatus) {

        //获取公文拟制的数据
        RetrofitHelper.getInstance().getNZOpinionInfo(opid, flowStatus, new CallBackYSAdapter() {

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
