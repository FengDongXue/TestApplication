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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.fragment.DialogFragment_Zwdb;
import com.lanwei.governmentstar.view.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 蓝威科技-技术开发1 on 2017/10/23.
 */

public class Condition_Push_Activity extends BaseActivity {


    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.finish)
    TextView finish;
    @InjectView(R.id.horizonalScrollView)
    HorizontalScrollView horizonalScrollView;
    @InjectView(R.id.all_selected)
    ImageView allSelected;
    @InjectView(R.id.feedback)
    TextView feedback;
    @InjectView(R.id.cancel)
    TextView cancel;
    @InjectView(R.id.deal_layout)
    LinearLayout dealLayout;
    @InjectView(R.id.search_layout)
    LinearLayout search_layout;
    @InjectView(R.id.search)
    EditText search;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @InjectView(R.id.not_loading)
    ProgressBar notLoading;
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
        swipeLayout.setEnabled(false);

    }


    void initweights(){

        title.setText("督办状态查询");
        dealLayout.setVisibility(View.VISIBLE);
        finish.setVisibility(View.GONE);
        search_layout.setVisibility(View.GONE);
        horizonalScrollView.setVisibility(View.GONE);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter_handdown = new Adapter_Handdown();
        recyclerview.setAdapter(adapter_handdown);

    }

    @OnClick({R.id.back, R.id.finish, R.id.all_selected, R.id.feedback, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.finish:
                break;
            case R.id.all_selected:

                break;
            case R.id.feedback:

                new DialogFragment_Zwdb().show(getSupportFragmentManager(),"dialog");

                break;
            case R.id.cancel:
                break;
        }
    }


    class Adapter_Handdown extends RecyclerView.Adapter<Adapter_Handdown.My_ViewHolder>{

        View view;

        @Override
        public Adapter_Handdown.My_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view= LayoutInflater.from(Condition_Push_Activity.this).inflate(R.layout.item_condittion_push ,parent ,false);

            return new Adapter_Handdown.My_ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Handdown.My_ViewHolder holder, final int position) {


            holder.name_push.setText("被督办：张高丽");
            holder.time.setText("处理时间：2017/01/02 12：00");
            holder.condition.setText("当前状态：已查收、已反馈、已处理");

            if(position == 0){
                holder.decoration.setVisibility(View.INVISIBLE);
            }else{
                holder.decoration.setVisibility(View.VISIBLE);
            }
            holder.selected.setTag(0);
            if(((int)holder.selected.getTag()) == 1){
                holder.selected.setSelected(true);
            }else{
                holder.selected.setSelected(false);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((int)holder.selected.getTag()) == 0){
                        holder.selected.setTag(1);
                        holder.selected.setSelected(true);

                    }else{
                        holder.selected.setTag(0);
                        holder.selected.setSelected(false);
                    }
                }
            });




        }

        @Override
        public int getItemCount() {

            return 100;
        }

        class My_ViewHolder extends RecyclerView.ViewHolder{

            TextView name_push;
            TextView time;
            ImageView selected;
            View decoration;
            TextView condition;

            public My_ViewHolder(View itemView) {

                super(itemView);
                name_push = (TextView) itemView.findViewById(R.id.name_push);
                time = (TextView) itemView.findViewById(R.id.time);
                condition =  (TextView)itemView.findViewById(R.id.condition);
                selected = (ImageView) itemView.findViewById(R.id.selected);
                decoration = itemView.findViewById(R.id.decoration);
            }

        }

    }
}
