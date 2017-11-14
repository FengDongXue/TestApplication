package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.MyDocument;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/27.
 */

public class Details_Document_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private TextView more;
    private PopupWindow popupWindow;
    private RecyclerView rv;
    private Adapter_Addtion adapter;
    private ArrayList<String> data = null;
    private WebView webView;
    private LinearLayout condition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_document);

        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
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
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        back=(ImageView)findViewById(R.id.back);
        more=(TextView) findViewById(R.id.more);
        rv=(RecyclerView) findViewById(R.id.rv);
        webView=(WebView)findViewById(R.id.wv);

        back.setOnClickListener(this);
        more.setOnClickListener(this);

        data=new ArrayList<>();
        data.add("20152352.jpg");
        data.add("20152352.jpg");
        data.add("20152352.jpg");
//        adapter=new DetailsAdapter();

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter=new Adapter_Addtion(data);

        rv.setAdapter(adapter);


    }

    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;
        private ArrayList<String> datas = null;

        public Adapter_Addtion(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent,false);

            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            holder.addtion.setText("附件"+(position+1)+" : ");

            if(position==datas.size()-1){
                holder.line.setVisibility(View.GONE);

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    Intent intent=new Intent(Details_Document_Activity.this,Addtion_Details_Activity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) view.findViewById(R.id.addtion);
                addtional = (TextView) view.findViewById(R.id.addtional);
                line =  view.findViewById(R.id.line);
            }
        }
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){

            finish();
        }

        if(v.getId()==R.id.save){
            Toast.makeText(this,"点击了保存文件",Toast.LENGTH_SHORT).show();
        }

        if(v.getId()==R.id.switch1){
            Toast.makeText(this,"点击了转发文件",Toast.LENGTH_SHORT).show();
        }

        if(v.getId()==R.id.see){
            Toast.makeText(this,"点击了查看流程",Toast.LENGTH_SHORT).show();
        }



        if(v.getId()==R.id.more){

            // 弹出popupwindow前，调暗屏幕的透明度
            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
            lp2.alpha=(float) 0.8;
            getWindow().setAttributes(lp2);

            // 加载popupwindow的布局
            View view=getLayoutInflater().inflate(R.layout.item_three,null);
            popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);

            // 初始化popupwindow的点击控件
            TextView save=(TextView) view.findViewById(R.id.save);
            TextView switch1=(TextView) view.findViewById(R.id.switch1);
            TextView see=(TextView) view.findViewById(R.id.see);

            // 点击屏幕之外的区域可否让popupwindow消失
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOnDismissListener(new PoponDismissListener());
            // 注册popupwindow里面的点击事件
            save.setOnClickListener(this);
            switch1.setOnClickListener(this);
            see.setOnClickListener(this);

            // 设置popupwindow的显示位置
//            popupWindow.showAsDropDown(more,-240,35);
            popupWindow.showAtLocation(more, Gravity.RIGHT | Gravity.TOP, 30, more.getMeasuredHeight() * 3 /2);
        }

    }

    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha=(float) 1;
            getWindow().setAttributes(p);
        }

    }



}
