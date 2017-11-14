package com.lanwei.governmentstar.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/22.
 */

public class AddCalendar_Activity extends BaseActivity implements View.OnClickListener{


    private TextView time_begin;
    private TextView time_end;
    private TextView importance_setting;
    private TextView way_repeate;
    private ImageView back;
    private TextView ok;
    private EditText theme;
    private EditText content;
    private TextView begin;
    private TextView end;
    private TextView importance;
    private PopupWindow popupWindow;
    private PopupWindow popupWindow_repeate;
    private TextView repeate;
    private LinearLayout condition;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_calender);

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

        time_begin=(TextView) findViewById(R.id.time_begin);
        begin=(TextView) findViewById(R.id.begin);

        time_end=(TextView) findViewById(R.id.time_end);
        end=(TextView) findViewById(R.id.end);

        importance_setting=(TextView) findViewById(R.id.importance_setting);
        importance=(TextView) findViewById(R.id.importance);

        way_repeate=(TextView) findViewById(R.id.way_repeate);
        repeate=(TextView) findViewById(R.id.repeate);


        back=(ImageView) findViewById(R.id.back);
        ok=(TextView) findViewById(R.id.add);

        time_begin.setOnClickListener(this);
        time_end.setOnClickListener(this);
        importance_setting.setOnClickListener(this);
        back.setOnClickListener(this);
        ok.setOnClickListener(this);
        way_repeate.setOnClickListener(this);

        end.setText(" "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        begin.setText(" "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back:



                new DialogUtil(this,new Summit()).showConfirm("退出编辑", "您确定要退出编辑？(退出编辑将不保存已经编辑的信息)", "确定", "点错了");

//                // 弹出popupwindow前，调暗屏幕的透明度
//                WindowManager.LayoutParams lp4 = getWindow().getAttributes();
//                lp4.alpha=(float) 0.8;
//                getWindow().setAttributes(lp4);
//
//                // 加载popupwindow的布局
//                View view4=getLayoutInflater().inflate(R.layout.define_edit,hardwork);
//                popupWindow_repeate=new PopupWindow(view4,800, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//
//                TextView touch_wrong=(TextView) view4.findViewById(R.id.touch_wrong);
//                TextView define=(TextView) view4.findViewById(R.id.define);
//
//                // 点击屏幕之外的区域可否让popupwindow消失
//                popupWindow_repeate.setFocusable(true);
//                popupWindow_repeate.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow_repeate.setOnDismissListener(new PoponDismissListener());
//
//                define.setOnClickListener(this);
//                touch_wrong.setOnClickListener(this);
//
//                View rootview4 = LayoutInflater.from(this).inflate(R.layout.add_calender, hardwork);
//                // 设置popupwindow的显示位置
//                popupWindow_repeate.showAtLocation(rootview4, Gravity.CENTER,0,0);



                break;

//            case R.id.touch_wrong:
//
//                popupWindow_repeate.dismiss();
//
//                break;

            case R.id.add:

                long diff=0;
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date d_begin=df.parse(begin.getText().toString());
                    Date d_end=df.parse(end.getText().toString());

                    diff=d_end.getTime()-d_begin.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(diff>0){
                    Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this,"请选择正确的起始时间",Toast.LENGTH_SHORT).show();
                }


                break;

//            case R.id.define:
//
//                finish();
//                break;

            case R.id.day:

                popupWindow_repeate.dismiss();
                repeate.setText(" 每天提醒一次");
                // todo  设置提醒方式
                break;

            case R.id.week:

                popupWindow_repeate.dismiss();
                repeate.setText(" 每周提醒一次");
                // todo  设置提醒方式
                break;

            case R.id.month:

                popupWindow_repeate.dismiss();
                repeate.setText(" 每月提醒一次");
                // todo  设置提醒方式

                break;

            case R.id.general:

                // todo  设置重要度
                popupWindow.dismiss();
                importance.setText(" 一般提醒");

                break;

            case R.id.very:

                // todo  设置重要度
                popupWindow.dismiss();
                importance.setText(" 重要提醒");
                break;


            case R.id.time_begin:

                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        begin.setText(" "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
                    }
                })
                        .setTitleText("开始时间")
                        .setSubmitColor(Color.parseColor("#00a7e4"))
                        .setCancelColor(Color.parseColor("#00a7e4"))
                        .setTitleBgColor(Color.WHITE)
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                        .setRange(Calendar.getInstance().get(Calendar.YEAR) - 5, Calendar.getInstance().get(Calendar.YEAR) + 5)
                        .setLabel("年","月","日","时","分","秒")
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();


                break;

            case R.id.time_end:

                //时间选择器
                TimePickerView endTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        end.setText(" "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
                    }
                })
                        .setTitleText("结束时间")
                        .setSubmitColor(Color.parseColor("#00a7e4"))
                        .setCancelColor(Color.parseColor("#00a7e4"))
                        .setTitleBgColor(Color.WHITE)
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                        .setLabel("年","月","日","时","分","秒")
                        .build();
                endTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                endTime.show();

                break;

            case R.id.importance_setting:

                 // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=(float) 0.8;
                getWindow().setAttributes(lp);

                // 加载popupwindow的布局
                View view=getLayoutInflater().inflate(R.layout.importance_reminder,null);
                popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 初始化popupwindow的点击控件
                TextView general=(TextView) view.findViewById(R.id.general);
                TextView very=(TextView) view.findViewById(R.id.very);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());

                // 注册popupwindow里面的点击事件
                general.setOnClickListener(this);
                very.setOnClickListener(this);
                View rootview = LayoutInflater.from(this).inflate(R.layout.add_calender, null);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);

                break;

            case R.id.way_repeate:

                // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha=(float) 0.8;
                getWindow().setAttributes(lp2);

                // 加载popupwindow的布局
                View view2=getLayoutInflater().inflate(R.layout.way_repeat,null);
                popupWindow_repeate=new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 初始化popupwindow的点击控件
                TextView day=(TextView) view2.findViewById(R.id.day);
                TextView week=(TextView) view2.findViewById(R.id.week);
                TextView month=(TextView) view2.findViewById(R.id.month);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow_repeate.setFocusable(true);
                popupWindow_repeate.setBackgroundDrawable(new BitmapDrawable());
                popupWindow_repeate.setOnDismissListener(new PoponDismissListener());

                // 注册popupwindow里面的点击事件
                day.setOnClickListener(this);
                week.setOnClickListener(this);
                month.setOnClickListener(this);

                View rootview2 = LayoutInflater.from(this).inflate(R.layout.add_calender, null);
                // 设置popupwindow的显示位置
                popupWindow_repeate.showAtLocation(rootview2, Gravity.BOTTOM,0,0);

                break;

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


    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {
            finish();
        }

        @Override
        public void noClick() {}

        @Override
        public void onSingleClick() {

        }
    }

}
