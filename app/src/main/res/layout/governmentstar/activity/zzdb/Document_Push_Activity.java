package com.lanwei.governmentstar.activity.zzdb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 蓝威科技-技术开发1 on 2017/10/23.
 */

public class Document_Push_Activity extends BaseActivity {


    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.finish)
    TextView finish;
    @InjectView(R.id.wjdb)
    TextView wjdb;
    @InjectView(R.id.lsdc)
    TextView lsdc;
    @InjectView(R.id.jbdc)
    TextView jbdc;
    @InjectView(R.id.gztz)
    TextView gztz;
    @InjectView(R.id.dcsx)
    TextView dcsx;
    @InjectView(R.id.jxqk)
    TextView jxqk;
    @InjectView(R.id.search)
    EditText search;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @InjectView(R.id.not_loading)
    ProgressBar notLoading;
    private int pageCount_return =1;
    private int pageNo_return =1;
    private Adapter_Handdown adapter_handdown;

    ArrayList<TextView> list=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_zwdb);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        ButterKnife.inject(this);
        initArray();
        initweights();

    }


    void onLoadmore(){



    }


    void refresh(){



    }


    void initArray(){
        list.add(wjdb);
        list.add(lsdc);
        list.add(jbdc);
        list.add(gztz);
        list.add(dcsx);
        list.add(jxqk);
    }


    void initweights(){

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter_handdown = new Adapter_Handdown();
        recyclerview.setAdapter(adapter_handdown);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        Mugen.with(recyclerview, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                onLoadmore();
            }

            @Override
            public boolean isLoading() {
                return notLoading.getVisibility() == View.VISIBLE;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return pageCount_return <= pageNo_return;
            }
        }).start();

    }

    void setSelected( int position){
        for(int i=0;i<list.size();i++){
            if(position==i){
                list.get(i).setSelected(true);
            }else{
                list.get(i).setSelected(false);
            }
        }
    }

    @OnClick({R.id.back, R.id.finish, R.id.wjdb, R.id.lsdc, R.id.jbdc, R.id.gztz, R.id.dcsx, R.id.jxqk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.finish:
                startActivity(new Intent(Document_Push_Activity.this,Records_Push_Activity.class));
                break;
            case R.id.wjdb:
                setSelected(0);
                break;

            case R.id.lsdc:
                setSelected(1);
                break;
            case R.id.jbdc:
                setSelected(2);
                break;
            case R.id.gztz:
                setSelected(3);
                break;
            case R.id.dcsx:
                setSelected(4);
                break;
            case R.id.jxqk:
                setSelected(5);
                break;

        }
    }

    class Adapter_Handdown extends RecyclerView.Adapter<Adapter_Handdown.My_ViewHolder>{

        View view;
        int position_click = -1;

        @Override
        public Adapter_Handdown.My_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view= LayoutInflater.from(Document_Push_Activity.this).inflate(R.layout.layout_push_item ,parent ,false);

            return new Adapter_Handdown.My_ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Handdown.My_ViewHolder holder, final int position) {

            holder.title.setText("关于天津市农改委发布2018年工作计划的通知的内容");
            holder.name.setText("政务类型：政务文件催办");
            holder.state.setText("处理：正在督办");
            holder.date.setText("到期日期：2017/09/03 12：10");


            if(position == 0){
                holder.decoration.setVisibility(View.INVISIBLE);
            }else{
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position == position_click){
                holder.line.setVisibility(View.VISIBLE);
            }else{
                holder.line.setVisibility(View.INVISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    position_click =position;
                    adapter_handdown.notifyDataSetChanged();
                    startActivity(new Intent(Document_Push_Activity.this,Condition_Push_Activity.class));

                }
            });


        }

        @Override
        public int getItemCount() {

            return 100;
        }

        class My_ViewHolder extends RecyclerView.ViewHolder{

            TextView title;
            TextView name;
            TextView state;
            TextView line;
            ImageView urgent;
            View decoration;
            TextView date;

            public My_ViewHolder(View itemView) {

                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                name = (TextView) itemView.findViewById(R.id.name);
                state = (TextView) itemView.findViewById(R.id.state);
                line = (TextView) itemView.findViewById(R.id.line);
                date = (TextView) itemView.findViewById(R.id.time);
                urgent = (ImageView) itemView.findViewById(R.id.urgent);
                decoration = itemView.findViewById(R.id.decoration);
            }

        }

    }
}
