package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.HomeActivity;
import com.lanwei.governmentstar.bean.Temporary_File;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/18.
 */

public class TemporaryActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView rv;
    private Adapter_Temporaryfile adapter;
    private ArrayList<Temporary_File> datas_source=null;
    private ImageView back;
    private LinearLayout condition;
    private PopupWindow popupWindow;

    //  todo RecyclerView中item的左划提示删除的效果

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_temporary);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
//        版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
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
    }

    // 初始化控件
    void initweight(){

        rv=(RecyclerView) findViewById(R.id.rv);
        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setVisibility(View.INVISIBLE);
        // todo 网络请求，获取数据给adapter的数据源赋值

        Temporary_File file=new Temporary_File("我的的出发点","2013-4-12");
        datas_source=new ArrayList<>();
        datas_source.add(file);
        datas_source.add(file);
        adapter=new Adapter_Temporaryfile(datas_source);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.back){
            finish();
        }
    }

    int  x,y;
    //  RecyclerView的adapter,提供数据来源和监听事件
    class Adapter_Temporaryfile extends RecyclerView.Adapter<Adapter_Temporaryfile.MyViewHolder>{

        private View view =null;
        private ArrayList<Temporary_File> datas=null;
        private int position_before=-1;

        public Adapter_Temporaryfile(ArrayList datas) {
            this.datas = datas;
        }

        // 加载布局，返回viewholder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view=getLayoutInflater().inflate(R.layout.item_search,parent,false);

            return new MyViewHolder(view);
        }

        // 绑定控件
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.title.setText(datas.get(position).getTitle());
            holder.time.setText(datas.get(position).getTime());
            if(datas.get(position).getIsSelected().equals("true")){
                holder.hasRead.setVisibility(View.VISIBLE);
            }else{
                holder.hasRead.setVisibility(View.INVISIBLE);
            }
            holder.more_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View view1 = getLayoutInflater().inflate(R.layout.layout_twomenu, null);
                    popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOnDismissListener(new PoponDismissListener());

                    int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    view1.measure(width,height);
                    int height2=view1.getMeasuredHeight();
                    int width2=view1.getMeasuredWidth();

//                    y= (int) holder.more_layout.getY();
//                    x= (int) holder.more_layout.getX();
//                    x= (int) holder.more_layout.getPivotX();
//                    y= (int) holder.more_layout.getPivotY();
//                    System.out.println(x+"**"+y);
//                    System.out.println(width2+"**"+height2);
                    // 设置popupwindow的显示位置
//                    popupWindow.showAtLocation(view1, Gravity.NO_GRAVITY,x-(width2/2),y-(height2));
                    popupWindow.showAsDropDown(holder.title,-width2+20,0,Gravity.RIGHT);

                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(position_before==-1){
                        datas.get(position).setIsSelected("true");
                    }else if(position_before!=position){
                        datas.get(position_before).setIsSelected("false");
                        adapter.notifyItemChanged(position_before);
                    }

                    holder.hasRead.setVisibility(View.VISIBLE);
                    position_before=position;

                    Intent intent=new Intent(TemporaryActivity.this,Temporary_Activity.class);
                    startActivity(intent);

                }
            });

        }

        // 返回数据源的大小
        @Override
        public int getItemCount() {
            return datas.size();
        }

        // 自定义符合item的viewholder
        class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView title;
            private TextView time;
            private View hasRead;
            private ImageView more3;
            private LinearLayout more_layout;

            public MyViewHolder(View itemView) {

                super(itemView);
                title=(TextView) view.findViewById(R.id.title);
                time=(TextView) view.findViewById(R.id.time);
                hasRead= view.findViewById(R.id.hasRead);
                more3=(ImageView) view.findViewById(R.id.more3);
                more_layout=(LinearLayout) view.findViewById(R.id.more_layout);
            }
        }
    }



    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha = (float) 1;
            getWindow().setAttributes(p);
        }
    }
}
