package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/17.
 */

public class VertifyActivity extends BaseActivity implements View.OnClickListener{


    private TextView prevent;
    private TextView vertify;
    private TextView time;
    private TextView ip;
    private TextView machine;
    private ImageView back;
    private PopupWindow popupWindow;
    private LinearLayout condition;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vertify);

        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            condition=(LinearLayout) findViewById(R.id.condition);
            condition.setVisibility(View.GONE);
        }

        initweight();
    }

    void initweight(){
        prevent=(TextView) findViewById(R.id.prevent);
        vertify=(TextView) findViewById(R.id.vertify);
        time=(TextView) findViewById(R.id.time);
        back=(ImageView) findViewById(R.id.back);
        ip=(TextView) findViewById(R.id.ip);
        machine=(TextView) findViewById(R.id.machine);

        vertify.setOnClickListener(this);
        prevent.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:

                finish();
                break;

            case R.id.vertify:
                // todo  验证登陆

                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=(float) 0.8;
                getWindow().setAttributes(lp);

                View view=getLayoutInflater().inflate(R.layout.sendvertify,null);
                popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                TextView notsend=(TextView) view.findViewById(R.id.notsend);
                TextView vertify2=(TextView) view.findViewById(R.id.vertify2);

                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());
                notsend.setOnClickListener(this);
                vertify2.setOnClickListener(this);
                View rootview = LayoutInflater.from(this).inflate(R.layout.layout_vertify, null);
                popupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);

                break;

            case R.id.prevent:
                // todo 阻止登陆


                WindowManager.LayoutParams lp9 = getWindow().getAttributes();
                lp9.alpha=(float) 0.8;
                getWindow().setAttributes(lp9);

                // 加载popupwindow的布局
                View view9=getLayoutInflater().inflate(R.layout.dial_popup,null);
                popupWindow=new PopupWindow(view9, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 初始化popupwindow的点击控件
                TextView dial_wrong=(TextView) view9.findViewById(R.id.dial_wrong);
                TextView dial_atonce=(TextView) view9.findViewById(R.id.dial_atonce);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());

                // 注册popupwindow里面的点击事件
                dial_wrong.setOnClickListener(this);
                dial_atonce.setOnClickListener(this);
                View rootview9 = LayoutInflater.from(this).inflate(R.layout.layout_vertify, null);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(rootview9, Gravity.CENTER,0,0);


//                // 设置主屏幕的透明度
//                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
//                lp2.alpha=(float) 0.8;
//                getWindow().setAttributes(lp2);
//
//                // 初始化加载的popupwindow
//                View view2=getLayoutInflater().inflate(R.layout.acountdismiss,hardwork);
//                popupWindow=new PopupWindow(view2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//                // 监听popupwindow的点击事件
//                TextView know=(TextView) view2.findViewById(R.id.know);
//                popupWindow.setFocusable(true);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setOnDismissListener(new PoponDismissListener());
//                know.setOnClickListener(this);
//                // 显示popupwindow
//                View rootview2 = LayoutInflater.from(this).inflate(R.layout.layout_vertify, hardwork);
//                popupWindow.showAtLocation(rootview2, Gravity.CENTER,0,0);
                break;

            case R.id.vertify2:
                WindowManager.LayoutParams l = getWindow().getAttributes();
                l.alpha=(float) 1;
                getWindow().setAttributes(l);
                popupWindow.dismiss();

                // todo 发送网页登陆

                break;

            case R.id.notsend:
                WindowManager.LayoutParams p = getWindow().getAttributes();
                p.alpha=(float) 1;
                getWindow().setAttributes(p);
                popupWindow.dismiss();

                break;

            case R.id.know:

                WindowManager.LayoutParams p2 = getWindow().getAttributes();
                p2.alpha=(float) 1;
                getWindow().setAttributes(p2);
                popupWindow.dismiss();

                break;
        }
    }

    // 监听popupwindow消失后执行Z
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
