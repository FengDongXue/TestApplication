package com.lanwei.governmentstar.activity.dzgd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.icarus.entity.Image;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Cpllection_Activity;
import com.lanwei.governmentstar.activity.MyCollection_Activity;
import com.lanwei.governmentstar.adapter.TagAdapter2;
import com.lanwei.governmentstar.adapter.TagAdapter3;
import com.lanwei.governmentstar.bean.Item_Collection;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.utils.FlowTagLayout;
import com.lanwei.governmentstar.utils.OnTagClickListener;
import com.lanwei.governmentstar.utils.OnTagSelectListener;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/13.
 *
 */

public class Mohu_Search_Activity extends BaseActivity implements View.OnClickListener{

    private RecyclerView recycler_view;
    private ImageView back;
    private TextView search;
    private Adapter_Collection adapter_collection;
    private ArrayList<Data_Item> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohu_search);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        initweight();

    }



    void initweight(){

        recycler_view =  (RecyclerView) findViewById(R.id.recycler_view);
        back =  (ImageView) findViewById(R.id.back);
        search =  (TextView) findViewById(R.id.search);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        // 为RecyclerView设置默认动画和线性布局管理器

        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        adapter_collection = new Adapter_Collection();

        list = new ArrayList<>();

        for(int i=0;i<10;i++){
            TagAdapter3 tagAdapter2=new TagAdapter3(this);
            ArrayList<String> list2 =new ArrayList();
            list2.add("中共党员");
            list2.add("工青团员");
            list2.add("群众");
            list2.add("中共预备党员");
            list2.add("共产党员");
            tagAdapter2.onlyAddAll(list2);
            Data_Item data_item=new Data_Item("政治面貌","中共党员",false,tagAdapter2,-1);
            list.add(data_item);
        }

        recycler_view.setAdapter(adapter_collection);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){


            case R.id.back:
                finish();
                break;

            case R.id.search:

                break;


        }

    }


    class Data_Item{

        private String theme ="";
        private String content ="";
        private Boolean is_turn = false;
        private TagAdapter3 tagAdapter2 =new TagAdapter3(Mohu_Search_Activity.this);
        private int position_click;
        public Data_Item(String theme, String content, Boolean is_turn, TagAdapter3 tagAdapter2,int position_click) {
            this.theme = theme;
            this.content = content;
            this.is_turn = is_turn;
            this.tagAdapter2 = tagAdapter2;
            this.position_click = position_click;
        }

        public int getPosition_click() {
            return position_click;
        }

        public void setPosition_click(int position_click) {
            this.position_click = position_click;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Boolean getIs_turn() {
            return is_turn;
        }

        public void setIs_turn(Boolean is_turn) {
            this.is_turn = is_turn;
        }

        public TagAdapter3 getTagAdapter2() {
            return tagAdapter2;
        }

        public void setTagAdapter2(TagAdapter3 tagAdapter2) {
            this.tagAdapter2 = tagAdapter2;
        }
    }


    class Adapter_Collection extends RecyclerView.Adapter<Adapter_Collection.MyViewHolder> {

        private View view = null;

        @Override
        public Adapter_Collection.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_mohu,parent,false);

            return new Adapter_Collection.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Collection.MyViewHolder holder, final int position) {

            holder.theme.setText(list.get(position).getTheme());
//            holder.content.setText(list.get(position).getContent());
            list.get(position).getTagAdapter2().setPosition(-11);
            holder.flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
            holder.flowTagLayout.setAdapter(list.get(position).getTagAdapter2());
            list.get(position).getTagAdapter2().notifyDataSetChanged();

            holder.flowTagLayout.setOnTagClickListener(new OnTagClickListener() {
                @Override
                public void onItemClick(FlowTagLayout parent, View view, int position2) {

                    if(list.get(position).getPosition_click()!=position2){
                        list.get(position).setPosition_click(position2);
                        holder.content.setText((String)list.get(position).getTagAdapter2().getItem(position2));
                    }else{
                        list.get(position).setPosition_click(-11);
                        holder.content.setText("");
                    }
                   list.get(position).getTagAdapter2().setPosition(list.get(position).getPosition_click());
                   list.get(position).getTagAdapter2().notifyDataSetChanged();


                    Log.e("click or not","yes or no");
                }
            });

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.flowTagLayout.getVisibility()==View.VISIBLE){
                        holder.flowTagLayout.setVisibility(View.GONE);
                        holder.arrow.setImageDrawable(getResources().getDrawable(R.drawable.down01));
                    }else{
                        holder.flowTagLayout.setVisibility(View.VISIBLE);
                        holder.arrow.setImageDrawable(getResources().getDrawable(R.drawable.up01));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {

            if(list == null){
                return 0;
            }
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private FlowTagLayout flowTagLayout;
            private TextView theme;
            private TextView content;
            private LinearLayout linearLayout;
            private ImageView arrow;

            public MyViewHolder(View itemView) {
                super(itemView);
                flowTagLayout = (FlowTagLayout) itemView.findViewById(R.id.flowTagLayout);
                theme = (TextView) itemView.findViewById(R.id.theme);
                content = (TextView) itemView.findViewById(R.id.content);
                arrow = (ImageView) itemView.findViewById(R.id.arrow);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            }
        }
    }

}
