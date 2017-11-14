package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Item_Collection;
import com.lanwei.governmentstar.bean.Item_Reminder;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/22.
 */

public class Reminder_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    private ImageView add;
    private RecyclerView rv;
    private ArrayList<Item_Reminder> data = null;
    private Adapter_Reminder adapter = null;
    private LinearLayout condition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_reminder);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
//        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else {
//            condition=(LinearLayout) findViewById(R.id.condition);
//            condition.setVisibility(View.GONE);
//        }

        initweight();

        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));
        data=new ArrayList<>();
        Item_Reminder item=new Item_Reminder("渔阳镇人民政府关于政务之星办公自动化系统启用的批示","rffd","2016-8-20  16:00");
        data.add(item);
        data.add(item);
        adapter=new Adapter_Reminder(data);
        rv.setAdapter(adapter);
    }

    void initweight(){

        back=(ImageView) findViewById(R.id.back);
        add=(ImageView) findViewById(R.id.add);
        rv=(RecyclerView) findViewById(R.id.rv);

        back.setOnClickListener(this);
        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.add){
            // todo 添加每日提醒item到网络
            Intent intent=new Intent(this,AddCalendar_Activity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.back){
            finish();
        }

    }

    class Adapter_Reminder extends RecyclerView.Adapter<Adapter_Reminder.MyViewHolder> {

        private View view = null;
        private ArrayList<Item_Reminder> datas = null;
        private int position_before=-1;

        public Adapter_Reminder(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_reminder,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // todo 点击item,跳转到相应item的详细信息界面
                    if(position_before==-1){
                        datas.get(position).setIsSelected("true");
                    }else if(position_before!=position){
                        datas.get(position_before).setIsSelected("false");
                        adapter.notifyItemChanged(position_before);
                    }

                    holder.hasRead.setVisibility(View.VISIBLE);
                    position_before=position;
                    Intent intent=new Intent(Reminder_Activity.this,Look_Calendar_Activity.class);
                    // todo 传入数据
                    startActivity(intent);
                }
            });


            // 长按item触发的方法
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    // todo 弹出并处理此item的三个选项

                    return true;
                }
            });
            holder.header.setText(datas.get(position).getHeader());
            holder.time.setText(datas.get(position).getTime());
            if(datas.get(position).getIsSelected().equals("true")){
                holder.hasRead.setVisibility(View.VISIBLE);
            }else{
                holder.hasRead.setVisibility(View.INVISIBLE);
            }

            // todo 拿到图像地址后，网络请求加载到item的header上

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView header;
            private TextView time;
            private View hasRead;

            public MyViewHolder(View itemView) {

                super(itemView);
                header = (TextView) view.findViewById(R.id.theme);
                time = (TextView) view.findViewById(R.id.time);
                hasRead= view.findViewById(R.id.hasRead);
            }
        }
    }


}
