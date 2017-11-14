package com.lanwei.governmentstar.activity.dzgd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_Activity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_List_Actitivty;
import com.lanwei.governmentstar.activity.gwxf.DocuHnaddown_Status_Activity;
import com.lanwei.governmentstar.bean.Data_Handdown;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Retrun_Classify;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/1.
 */

public class Classify_Document_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private ImageView mohu;
    private TextView title;
    private TextView draw;
    private TextView convey;
    private TextView renshigui;
    private EditText search;
    private RecyclerView recyclerview;
    private SwipeRefreshLayout swipe_layout;
    private ProgressBar not_loading;
    private Adapter_Handdown adapter_handdown;
    private Logging_Success bean;
    private GovernmentApi api;
    private String state = "1";
    private int pageCount_return =1;
    private int pageNo_return =1;
    private List<Retrun_Classify.DataBean> data =new ArrayList<>();
    private int position_click=-1;
    private LinearLayout search_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_classify);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweights();
        onfresh();
        swipe_layout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    onfresh();

            }
        });

        Mugen.with(recyclerview, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                onloadmore();
            }

            @Override
            public boolean isLoading() {
                return not_loading.getVisibility() == View.VISIBLE;
            }

            @Override
            public boolean hasLoadedAllItems() {

                return pageCount_return <= pageNo_return;
            }
        }).start();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:

                finish();

                break;

            case R.id.mohu:

                Intent intent =new Intent(this,Mohu_Search_Activity.class);
                startActivity(intent);

                break;

            case R.id.renshigui:

                search_layout.setVisibility(View.VISIBLE);
                convey.setTextColor(getResources().getColor(R.color.color_23));
                draw.setTextColor(getResources().getColor(R.color.color_23));
                if(data != null && !data.equals("")){
                    data.clear();
                    adapter_handdown.notifyDataSetChanged();
                }

                if(state.equals("4")){
                    state="1";
                    renshigui.setTextColor(getResources().getColor(R.color.color_23));
                    onfresh();
                }else{
                    state="4";
                    data=new ArrayList<>();
                    Retrun_Classify.DataBean dataBean= new Retrun_Classify.DataBean("冯东雪","男","群众党员","单位：天津市蓟州区发展与改革委员会","部门：党政办公室","","",1);
                    data.add(dataBean);
                    data.add(dataBean);

                    renshigui.setTextColor(getResources().getColor(R.color.blue));
                    adapter_handdown.notifyDataSetChanged();
                }


                break;

            case R.id.draw:
                search_layout.setVisibility(View.GONE);
                pageNo_return=1;
                convey.setTextColor(getResources().getColor(R.color.color_23));
                renshigui.setTextColor(getResources().getColor(R.color.color_23));
                if(data != null && !data.equals("")){
                    data.clear();
                    adapter_handdown.notifyDataSetChanged();
                }

                if(state.equals("2")){
                    state="1";
                    draw.setTextColor(getResources().getColor(R.color.color_23));
                }else{
                    state="2";
                    draw.setTextColor(getResources().getColor(R.color.blue));
                }

                onfresh();

                break;

            case R.id.convey:
                search_layout.setVisibility(View.GONE);
                pageNo_return=1;
                if(data != null && !data.equals("")){
                    data.clear();
                    adapter_handdown.notifyDataSetChanged();
                }
                draw.setTextColor(getResources().getColor(R.color.color_23));
                renshigui.setTextColor(getResources().getColor(R.color.color_23));
                if(state.equals("3")){
                    state="1";
                    convey.setTextColor(getResources().getColor(R.color.color_23));
                }else{
                    state="3";
                    convey.setTextColor(getResources().getColor(R.color.blue));
                }

                onfresh();

                break;

        }

    }


    void onfresh(){
        position_click = -1;
        Call<Retrun_Classify> call=api.list_classify(bean.getData().getOpId(),1+"" ,state);

        call.enqueue(new Callback<Retrun_Classify>() {
            @Override
            public void onResponse(Call<Retrun_Classify> call, Response<Retrun_Classify> response) {

                if(response.body() != null || !response.body().equals("")){
                    if(data !=null){
                        data.clear();
                    }
                    data =response.body().getData();
                    adapter_handdown.notifyDataSetChanged();
                    pageCount_return =response.body().getPageCount();
                    pageNo_return =response.body().getPageNo();
                    recyclerview.setVisibility(View.VISIBLE);
                }else{
                    recyclerview.setVisibility(View.VISIBLE);
                }
                not_loading.setVisibility(View.INVISIBLE);
                swipe_layout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Retrun_Classify> call, Throwable t) {
                not_loading.setVisibility(View.INVISIBLE);
                swipe_layout.setRefreshing(false);
                Toast.makeText(Classify_Document_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });


    }

    void onloadmore(){

        not_loading.setVisibility(View.VISIBLE);
        Call<Retrun_Classify> call=api.list_classify(bean.getData().getOpId(),pageNo_return+1+"" ,state);

        call.enqueue(new Callback<Retrun_Classify>() {
            @Override
            public void onResponse(Call<Retrun_Classify> call, Response<Retrun_Classify> response) {

                if(response.body() != null || !response.body().equals("")){
                    data.addAll(response.body().getData());
                    adapter_handdown.notifyDataSetChanged();
                    pageCount_return =response.body().getPageCount();
                    pageNo_return =response.body().getPageNo();
                    recyclerview.setVisibility(View.VISIBLE);

                }else{
                   recyclerview.setVisibility(View.INVISIBLE);
                }
                not_loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Retrun_Classify> call, Throwable t) {
                not_loading.setVisibility(View.INVISIBLE);
                Toast.makeText(Classify_Document_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });


    }


    void initweights(){

        back = (ImageView) findViewById(R.id.back);
        mohu = (ImageView) findViewById(R.id.mohu);
        back.setOnClickListener(this);
        mohu.setOnClickListener(this);
        search = (EditText) findViewById(R.id.search);
        title = (TextView) findViewById(R.id.title);
        draw = (TextView) findViewById(R.id.draw);
        convey = (TextView) findViewById(R.id.convey);
        renshigui = (TextView) findViewById(R.id.renshigui);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        search_layout.setVisibility(View.GONE);
        draw.setOnClickListener(this);
        convey.setOnClickListener(this);
        renshigui.setOnClickListener(this);
        renshigui.setVisibility(View.GONE);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        swipe_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        not_loading = (ProgressBar) findViewById(R.id.not_loading);
        adapter_handdown = new Adapter_Handdown();
        // 为RecyclerView设置默认动画和线性布局管理器
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter_handdown);
        // 获取bean;
        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);


        if(state.equals("2")){

            if(resultCode==510){

                data.get(position_click).setGxState(data2.getStringExtra("type_share"));
                adapter_handdown.notifyDataSetChanged();
                Log.e("data2.(\"type_share\")",position_click+data2.getStringExtra("type_share"));

            }else if(resultCode==520){

                onfresh();

            }

        }else if(state.equals("1")){

            if(resultCode == 520){

                data.remove(position_click);
                position_click=-1;
                adapter_handdown.notifyDataSetChanged();

            }

        }else if(state.equals("3")){

            if(resultCode == 520){

                onfresh();

            }

        }



    }

    class Adapter_Handdown extends RecyclerView.Adapter<Adapter_Handdown.My_ViewHolder>{

        View view;

        @Override
        public Adapter_Handdown.My_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view= LayoutInflater.from(Classify_Document_Activity.this).inflate(R.layout.item_classify ,parent ,false);

            return new Adapter_Handdown.My_ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Handdown.My_ViewHolder holder, final int position) {


            if(position == data.size()-1){
                holder.decoration.setVisibility(View.INVISIBLE);
            }else{
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position == position_click){
                holder.line.setVisibility(View.VISIBLE);
            }else{
                holder.line.setVisibility(View.INVISIBLE);
            }

            if(state.equals("1")){

                holder.all.setVisibility(View.GONE);
                holder.up_item.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);  // , 1是可选写的
                lp.setMargins(0, 0, 0, 16);
                holder.state_type_title.setLayoutParams(lp);

                holder.amount.setVisibility(View.GONE);
                holder.state_type_title.setVisibility(View.VISIBLE);
                holder.state_type_title.setTextSize(13);
                holder.state_type_title.setTextColor(getResources().getColor(R.color.color_line));
                holder.time.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(data.get(position).getDocTitle());
                holder.state_type_title.setText("文件类型："+data.get(position).getDocType());
                holder.time.setText("办结日期："+data.get(position).getOpCreateTime());
                holder.title.setTextColor(getResources().getColor(R.color.blue));

                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(data.get(position).getDocType().equals("拟制公文")){
                            position_click = position;
                            Intent intent =new Intent(Classify_Document_Activity.this,Classify_Nizhi_Activity.class);
                            intent.putExtra("opId",data.get(position).getOpId());
                            intent.putExtra("state","拟制归档");
                            startActivityForResult(intent,001);
                        }else{
                            position_click = position;
                            Intent intent =new Intent(Classify_Document_Activity.this,Classify_Shouwen_Activity.class);
                            intent.putExtra("opId",data.get(position).getOpId());
                            intent.putExtra("state","收文归档");
                            startActivityForResult(intent,002);
                        }
                        adapter_handdown.notifyDataSetChanged();
                    }
                });

            }else if(state.equals("2")){
                holder.all.setVisibility(View.GONE);
                holder.up_item.setVisibility(View.VISIBLE);
                if(data.get(position).getOpState()==0){

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);  // , 1是可选写的
                    lp.setMargins(0, 20, 0, 20);
                    holder.state_type_title.setLayoutParams(lp);
                    holder.title.setVisibility(View.GONE);
                    holder.amount.setVisibility(View.GONE);
                    holder.state_type_title.setTextSize(15);
                    holder.state_type_title.setText(data.get(position).getDocTitle());
                    holder.time.setText("完成时间："+data.get(position).getOpCreateTime());
                    holder.state_type_title.setTextColor(getResources().getColor(R.color.blue));

                    holder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            position_click = position;
                            Intent intent =new Intent(Classify_Document_Activity.this,Classify_Nizhi_Activity.class);
                            intent.putExtra("opId",data.get(position).getOpId());
                            intent.putExtra("state","拟制归档");
                            startActivityForResult(intent,03);
                            adapter_handdown.notifyDataSetChanged();

                        }
                    });


                }else{

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);  // , 1是可选写的
                    lp.setMargins(0, 0, 0, 16);
                    holder.state_type_title.setLayoutParams(lp);
                    holder.title.setVisibility(View.VISIBLE);
                    holder.amount.setVisibility(View.VISIBLE);
                    holder.amount.setText("借阅次数："+data.get(position).getJyNum()+"次");
                    holder.state_type_title.setTextSize(13);
                    holder.title.setText(data.get(position).getDocTitle());
                    holder.state_type_title.setTextColor(getResources().getColor(R.color.color_line));
                    holder.state_type_title.setText("共享状态："+data.get(position).getGxState());
                    holder.time.setText("归档时间："+data.get(position).getOpCreateTime());
                    holder.title.setTextColor(getResources().getColor(R.color.color_23));

                    holder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            position_click = position;
                            Log.e("position_click",position_click+"   "+position);

                            Intent intent =new Intent(Classify_Document_Activity.this,Classify_Nizhidan_Activity.class);
                            intent.putExtra("opId",data.get(position).getOpId());
                            intent.putExtra("gxState",data.get(position).getGxState());
                            startActivityForResult(intent,04);
                            adapter_handdown.notifyDataSetChanged();

                        }
                    });

                }

            } else if (state.equals("3")){
                holder.all.setVisibility(View.GONE);
                holder.up_item.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);  // , 1是可选写的
                lp.setMargins(0, 0, 0, 16);
                holder.state_type_title.setLayoutParams(lp);
                holder.state_type_title.setTextSize(13);
                holder.state_type_title.setTextColor(getResources().getColor(R.color.color_line));

                if(data.get(position).getOpState()==0){

                    holder.title.setVisibility(View.VISIBLE);
                    holder.title.setText(data.get(position).getDocTitle());
                    holder.amount.setVisibility(View.GONE);
                    holder.state_type_title.setText("来文单位："+data.get(position).getDocLwdw());
                    holder.time.setText("归档时间："+data.get(position).getOpCreateTime());
                    holder.title.setTextColor(getResources().getColor(R.color.blue));
                    holder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            position_click = position;
                            Intent intent =new Intent(Classify_Document_Activity.this,Classify_Shouwen_Activity.class);
                            intent.putExtra("opId",data.get(position).getOpId());
                            intent.putExtra("state","收文归档");
                            startActivityForResult(intent,05);
                            adapter_handdown.notifyDataSetChanged();

                        }
                    });

                }else{
                    holder.title.setVisibility(View.VISIBLE);
                    holder.title.setText(data.get(position).getDocTitle());
                    holder.amount.setVisibility(View.GONE);
                    holder.state_type_title.setText("来文单位："+data.get(position).getDocLwdw());
                    holder.time.setText("归档时间："+data.get(position).getOpCreateTime());
                    holder.title.setTextColor(getResources().getColor(R.color.color_23));
                    holder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            position_click = position;
                            Log.e("position_click",position_click+"   "+position);

                            Intent intent =new Intent(Classify_Document_Activity.this,Classify_Guidangdan_Activity.class);
                            intent.putExtra("opId",data.get(position).getOpId());
                            intent.putExtra("title",data.get(position).getDocTitle());
                            intent.putExtra("gxState",data.get(position).getGxState());
                            startActivity(intent);
                            adapter_handdown.notifyDataSetChanged();
                        }
                    });


                }


            }else if(state.equals("4")){

                holder.up_item.setVisibility(View.GONE);
                holder.all.setVisibility(View.VISIBLE);


                holder.name.setText(data.get(position).getOpId());
                holder.sex.setText(data.get(position).getDocTitle());
                holder.rank.setText(data.get(position).getDocType());
                holder.department.setText(data.get(position).getOpCreateTime());
                holder.danwei.setText(data.get(position).getJyNum());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent =new Intent(Classify_Document_Activity.this,Renshi_Details_Activity.class);
                        startActivity(intent);


                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            if(data == null){
                recyclerview.setVisibility(View.INVISIBLE);
                return 0;
            }
            recyclerview.setVisibility(View.VISIBLE);
            return data.size();
        }

        class My_ViewHolder extends RecyclerView.ViewHolder{

            private TextView line;
            private TextView title;
            private TextView amount;
            private TextView state_type_title;
            private TextView time;
            private View decoration;
            private LinearLayout item;
            private LinearLayout all;
            private LinearLayout up_item;
            private TextView name;
            private TextView sex;
            private TextView rank;
            private TextView department;
            private TextView danwei;

            public My_ViewHolder(View itemView) {

                super(itemView);
                line = (TextView) itemView.findViewById(R.id.line);
                title = (TextView) itemView.findViewById(R.id.title);
                amount = (TextView) itemView.findViewById(R.id.amount);
                state_type_title = (TextView) itemView.findViewById(R.id.state_type_title);
                time = (TextView) itemView.findViewById(R.id.time);
                decoration =  itemView.findViewById(R.id.decoration);
                item = (LinearLayout) itemView.findViewById(R.id.item);
                all = (LinearLayout) itemView.findViewById(R.id.all);
                up_item = (LinearLayout) itemView.findViewById(R.id.up_item);
                name = (TextView) itemView.findViewById(R.id.name);
                sex = (TextView) itemView.findViewById(R.id.sex);
                rank = (TextView) itemView.findViewById(R.id.rank);
                danwei = (TextView) itemView.findViewById(R.id.danwei);
                department = (TextView) itemView.findViewById(R.id.department);
            }

        }

    }

}
