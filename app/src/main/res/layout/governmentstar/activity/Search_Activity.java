package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/28.
 */

public class Search_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private TextView search;
    private LinearLayout condition;

    private RelativeLayout apartment;
    private RelativeLayout type;
    private RelativeLayout theme;
    private RelativeLayout start;
    private RelativeLayout end;

    private TextView result_apartment;
    private TextView result_type;
    private TextView result_theme;
    private TextView result_start;
    private TextView result_end;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_mohu);

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

        back=(ImageView) findViewById(R.id.back);
        search=(TextView) findViewById(R.id.search);
        apartment=(RelativeLayout) findViewById(R.id.apartment);
        type=(RelativeLayout) findViewById(R.id.type);
        theme=(RelativeLayout) findViewById(R.id.theme);
        start=(RelativeLayout) findViewById(R.id.begin);
        end=(RelativeLayout) findViewById(R.id.end);

        back.setOnClickListener(this);
        search.setOnClickListener(this);
        apartment.setOnClickListener(this);
        type.setOnClickListener(this);
        theme.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);

        result_apartment=(TextView) findViewById(R.id.result_apartment);
        result_type=(TextView) findViewById(R.id.result_type);
        result_theme=(TextView) findViewById(R.id.result_theme);
        result_start=(TextView) findViewById(R.id.result_start);
        result_end=(TextView) findViewById(R.id.result_end);

        result_apartment.setVisibility(View.INVISIBLE);
        result_type.setVisibility(View.INVISIBLE);
        result_theme.setVisibility(View.INVISIBLE);
        result_start.setVisibility(View.INVISIBLE);
        result_end.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(final View v) {

        String title =null;
        Intent intent;

        switch (v.getId()) {
            case R.id.back:

                finish();

                break;

            case R.id.begin:

                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        result_start.setText(""+new SimpleDateFormat("yyyy-MM-dd").format(date));
                        result_start.setVisibility(View.VISIBLE);
                    }
                })
                        .setTitleText("开始时间")
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                        .setRange(Calendar.getInstance().get(Calendar.YEAR) - 5, Calendar.getInstance().get(Calendar.YEAR) + 5)
                        .setLabel("年","月","日","时","分","秒")
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();

                break;

            case R.id.end:

                //时间选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        result_end.setText(""+new SimpleDateFormat("yyyy-MM-dd").format(date));
                        result_end.setVisibility(View.VISIBLE);
                    }
                })
                        .setTitleText("开始时间")
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                        .setLabel("年","月","日","时","分","秒")
                        .build();
                pvTime2.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime2.show();


                break;


            case R.id.search:

                // todo 获取模糊条件 跳转查找结果



                if(result_start.getText().equals("") || result_end.getText().equals("")){

                    Toast.makeText(Search_Activity.this,"请选择正确的起始时间",Toast.LENGTH_SHORT).show();
                    return;
                  }

                long diff=0;
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d_begin=df.parse(result_start.getText().toString());
                    Date d_end=df.parse(result_end.getText().toString());

                    diff=d_end.getTime()-d_begin.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                    diff=0;
                }

                if(diff>0){
                    Intent intent1=new Intent(this,Result_Mohu_Activity.class);
                    startActivity(intent1);
                }else{
                    Toast.makeText(this,"请选择正确的起始时间",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.apartment:

                title="按来文单位查找";

                intent=new Intent(this,SearchItem_Activity.class);
                intent.putExtra("theme",title);
                startActivityForResult(intent,1);


                break;

            case R.id.type:

                title="按公文类型查找";

                intent=new Intent(this,SearchItem_Activity.class);
                intent.putExtra("theme",title);
                startActivityForResult(intent,2);

                break;

            case R.id.theme:

                title="按公文主题查找";

                intent=new Intent(this,SearchItem_Activity.class);
                intent.putExtra("theme",title);
                startActivityForResult(intent,3);

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && resultCode ==2){
            result_apartment.setText(data.getStringExtra("selection"));
            result_apartment.setVisibility(View.VISIBLE);
        }

        if(requestCode==2 && resultCode ==2){
            result_type.setText(data.getStringExtra("selection"));
            result_type.setVisibility(View.VISIBLE);
        }

        if(requestCode==3 && resultCode ==2){
            result_theme.setText(data.getStringExtra("selection"));
            result_theme.setVisibility(View.VISIBLE);
        }

        if(resultCode ==0){
            return;
        }



    }
}




