package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener{

    private ViewPager vp;
    private Adapter adapter;
    private Listener listener;
    private TextView skip;
    private ImageView number1;
    private ImageView number2;
    private ImageView number3;
    private ArrayList<View> list;
    private TextView tv;
    private Integer isFirst;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 永久内存保存状态，以便判断状态加载不同界面
        sharedPreferences=getSharedPreferences("isFirst",MODE_PRIVATE);
        isFirst=sharedPreferences.getInt("isFirst",0);

        // 判断是否是首次登录，如果是加载欢迎界面
         if(isFirst==0){
             setContentView(R.layout.activity_main);

             // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
             if (Build.VERSION.SDK_INT >= 21) {
                 View decorView = getWindow().getDecorView();
                 int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                         | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                         | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                 decorView.setSystemUiVisibility(option);
                 getWindow().setNavigationBarColor(Color.TRANSPARENT);
                 getWindow().setStatusBarColor(Color.TRANSPARENT);
             }

            //  StatusBarUtils.compat(this, Color.parseColor("#5CADE4"));
             initweight();
             // 初始化viewpager的item布局
             View view1=getLayoutInflater().inflate(R.layout.first,null);
             View view2=getLayoutInflater().inflate(R.layout.second,null);
             View view3=getLayoutInflater().inflate(R.layout.third,null);

             // 给viewpager添加item布局
             list.add(view1);
             list.add(view2);
             list.add(view3);

             // 初始化viewpager和布局效果
             adapter=new Adapter(list);
             vp.setAdapter(adapter);
             vp.setCurrentItem(0);
             number1.setImageResource(R.drawable.guide01);
             tv.setVisibility(View.INVISIBLE);

             listener=new Listener();
             vp.addOnPageChangeListener(listener);
         }else{
             // 如果不是直接跳转登录界面

             Intent intent=new Intent(this,LoggingActivity.class);
             startActivity(intent);
             finish();
         }
    }

    // 初始化控件
    void initweight() {

        skip = (TextView) findViewById(R.id.skip);
        number1 = (ImageView) findViewById(R.id.number1);
        number2 = (ImageView) findViewById(R.id.number2);
        number3 = (ImageView) findViewById(R.id.number3);
        vp=(ViewPager) findViewById(R.id.vp);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        skip.setOnClickListener(this);
        list=new ArrayList<>();
        tv=(TextView) findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

       switch (v.getId()){

           case R.id.skip:

                   //首次登录跳转后，修改标志，以便二次登录直接跳转
                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putInt("isFirst",1);
                   editor.commit();
                   finish();
                   Intent intent1=new Intent(this,LoggingActivity.class);
                   startActivity(intent1);

               break;

           case R.id.number1:

               vp.setCurrentItem(0);
               tv.setVisibility(View.GONE);

               break;

           case R.id.number2:

               vp.setCurrentItem(1);
               tv.setVisibility(View.GONE);

               break;

           case R.id.number3:

               vp.setCurrentItem(2);
               tv.setVisibility(View.VISIBLE);

               break;

           case R.id.tv:

                   //首次登录跳转后，修改标志，以便二次登录直接跳转
                   SharedPreferences.Editor editor2=sharedPreferences.edit();
                   editor2.putInt("isFirst",1);
                   editor2.commit();
                   finish();
                   Intent intent2=new Intent(this,LoggingActivity.class);
                   startActivity(intent2);

               break;
       }
    }


    // viewpager的监听器
    class Listener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        // viewpager的item选定后触发的方法
        @Override
        public void onPageSelected(int position) {

            number1.setImageResource(R.drawable.guide02);
            number2.setImageResource(R.drawable.guide02);
            number3.setImageResource(R.drawable.guide02);

            switch (position){
                case 0:
                number1.setImageResource(R.drawable.guide01);
                    tv.setVisibility(View.GONE);
                    skip.setVisibility(View.VISIBLE);
                    break;

                case 1:
                    number2.setImageResource(R.drawable.guide01);
                    tv.setVisibility(View.GONE);
                    skip.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    number3.setImageResource(R.drawable.guide01);
                    tv.setVisibility(View.VISIBLE);
                    skip.setVisibility(View.GONE);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    // viewpager的适配器，提供数据原
    class Adapter extends PagerAdapter{


        ArrayList<View> list1 =new ArrayList<>();

        public Adapter(ArrayList list) {
            list1.addAll(list);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(list.get(position));

            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView(list.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
