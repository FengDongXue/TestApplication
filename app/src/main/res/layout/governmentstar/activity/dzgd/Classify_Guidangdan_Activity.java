package com.lanwei.governmentstar.activity.dzgd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Guidang;
import com.lanwei.governmentstar.bean.Return_Nizhidan;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/8.
 */

public class Classify_Guidangdan_Activity extends BaseActivity implements View.OnClickListener{

    private Logging_Success bean;
    private GovernmentApi api;
    private ImageView back;
    private TextView gwbt;
    private TextView gwzh;
    private TextView gwbh;
    private TextView gwlx;
    private TextView gwzt;

    private TextView jjr;
    private TextView nbr;
    private TextView psr;
    private TextView ybr;
    private TextView cbr;
    private TextView bbl;
    private TextView cyr;
    private TextView gdr;
    private TextView bllx;
    private TextView bcnx;
    private TextView qsrq;
    private TextView bjrq;
    private TextView gdrq;
    private LinearLayout classify_addtion;
    private LinearLayout flow_consult;
    private String content = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouwendan_layout);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        initweights();
    }


    void initweights(){

        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();

        Call<Return_Guidang> call=api.dzgdDealedMenu(bean.getData().getOpId(),getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<Return_Guidang>() {
            @Override
            public void onResponse(Call<Return_Guidang> call, Response<Return_Guidang> response) {


                if(response.body() != null){

                    gwbt.setText(getIntent().getStringExtra("title"));
                    gwzh.setText(response.body().getGwzh());
                    gwbh.setText(response.body().getGwbh());
                    gwlx.setText(response.body().getBllx());
                    gwzt.setText(response.body().getGwzt());

                    jjr.setText(response.body().getJsrName());
                    nbr.setText(response.body().getNbrName());
                    psr.setText(response.body().getPsrName());
                    ybr.setText(response.body().getYbrName());
                    cbr.setText(response.body().getCbrName());
                    bbl.setText(response.body().getBsrName());
                    cyr.setText(response.body().getCyrName());
                    gdr.setText(response.body().getGdrName());
                    bllx.setText(response.body().getBllx());
                    bcnx.setText(response.body().getBcnx());
                    qsrq.setText(response.body().getQsrq());
                    gdr.setText(response.body().getGdrName());
                    bjrq.setText(response.body().getBjrq());
                    gdrq.setText(response.body().getGdrq());
                    content = response.body().getGdyj();

                }

            }

            @Override
            public void onFailure(Call<Return_Guidang> call, Throwable t) {

                Toast.makeText(Classify_Guidangdan_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();


            }
        });

        back = (ImageView) findViewById(R.id.back);
        gwbt = (TextView) findViewById(R.id.gwbt);
        gwzh = (TextView) findViewById(R.id.gwzh);
        gwbh = (TextView) findViewById(R.id.gwbh);
        gwlx = (TextView) findViewById(R.id.gwlx);
        gwzt = (TextView) findViewById(R.id.gwzt);

        jjr = (TextView) findViewById(R.id.jjr);
        nbr = (TextView) findViewById(R.id.nbr);
        psr = (TextView) findViewById(R.id.psr);
        ybr = (TextView) findViewById(R.id.ybr);
        cbr = (TextView) findViewById(R.id.cbr);
        bbl = (TextView) findViewById(R.id.bbl);
        cyr = (TextView) findViewById(R.id.cyr);
        gdr = (TextView) findViewById(R.id.gdr);
        bllx = (TextView) findViewById(R.id.bllx);
        bcnx = (TextView) findViewById(R.id.bcnx);
        qsrq = (TextView) findViewById(R.id.qsrq);
        bjrq = (TextView) findViewById(R.id.bjrq);
        gdrq = (TextView) findViewById(R.id.gdrq);

        classify_addtion = (LinearLayout) findViewById(R.id.classify_addtion);
        flow_consult = (LinearLayout) findViewById(R.id.flow_consult);

        back.setOnClickListener(this);
        classify_addtion.setOnClickListener(this);
        flow_consult.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:
                finish();
                break;

            case R.id.classify_addtion:

                Intent intent1= new Intent(this,Share_Instruction_Activity.class);
                intent1.putExtra("content",content);
                startActivity(intent1);

                break;

            case R.id.flow_consult:

                Intent intent= new Intent(this,Convey_Files_Activity.class);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                startActivity(intent);

                break;

        }
    }
}
