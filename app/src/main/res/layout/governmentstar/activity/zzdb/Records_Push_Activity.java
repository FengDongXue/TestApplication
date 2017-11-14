package com.lanwei.governmentstar.activity.zzdb;

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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 蓝威科技-技术开发1 on 2017/10/23.
 */

public class Records_Push_Activity extends BaseActivity {


    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.finish)
    TextView finish;
    @InjectView(R.id.horizonalScrollView)
    HorizontalScrollView horizonalScrollView;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_zwdb);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        ButterKnife.inject(this);
        initweights();


    }


    void initweights(){

        title = (TextView) findViewById(R.id.title);
        title.setText("催办督办记录簿");
        horizonalScrollView.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);
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

    void onLoadmore(){



    }


    void refresh(){



    }

    @OnClick({R.id.back,  R.id.finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.finish:
                break;
        }
    }

    class Adapter_Handdown extends RecyclerView.Adapter<Adapter_Handdown.My_ViewHolder>{

        View view;

        @Override
        public Adapter_Handdown.My_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view= LayoutInflater.from(Records_Push_Activity.this).inflate(R.layout.layout_item_cbdb ,parent ,false);

            return new Adapter_Handdown.My_ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Handdown.My_ViewHolder holder, final int position) {

            holder.title.setText("关于天津市农改委发布2018年工作计划的通知的内容");
            holder.name.setText("时间范围：2017/05至2017/06");
            holder.date.setText("等级数量：152");


            if(position == 0){
                holder.decoration.setVisibility(View.INVISIBLE);
            }else{
                holder.decoration.setVisibility(View.VISIBLE);
            }

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





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
            ImageView more;
            ImageView urgent;
            View decoration;
            TextView date;

            public My_ViewHolder(View itemView) {

                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                name = (TextView) itemView.findViewById(R.id.name);
                more = (ImageView) itemView.findViewById(R.id.more);
                date = (TextView) itemView.findViewById(R.id.time);
                urgent = (ImageView) itemView.findViewById(R.id.urgent);
                decoration = itemView.findViewById(R.id.decoration);
            }

        }

    }

}
