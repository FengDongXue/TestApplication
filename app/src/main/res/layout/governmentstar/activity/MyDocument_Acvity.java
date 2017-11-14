package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.MyDocument;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/20.
 */

public class MyDocument_Acvity extends BaseActivity implements View.OnClickListener{

    private ArrayList<MyDocument> data = null;
    private RecyclerView rv;
    private Adapter_Document adaper;
    private RelativeLayout process;
    private RelativeLayout  mark;
    private ImageView back;
    private EditText search;
    private ImageView mohu;
    private PopupWindow popupWindow;
    private LinearLayout condition;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mydocument);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        //        Set<String> tags=new HashSet<>();
//
//        JPushInterface.setAliasAndTags(this, "zhouruibo", tags, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//
//            }
//        });

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

        // 初始化控件
        process=(RelativeLayout) findViewById(R.id.process);
        mark=(RelativeLayout) findViewById(R.id.mark);
        rv=(RecyclerView) findViewById(R.id.recycler_view);
        back=(ImageView) findViewById(R.id.back);
//        mohu=(ImageView) findViewById(R.id.mohu);
        search=(EditText) findViewById(R.id.search);


        process.setOnClickListener(this);
        mark.setOnClickListener(this);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
//        mohu.setOnClickListener(this);


        process.setSelected(true);
        mark.setSelected(false);

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setVisibility(View.INVISIBLE);
        // todo 网络请求，获取正在办理文件的数据给adapter的数据源赋值

        MyDocument myDocument=new MyDocument("大方向","2016-12-21","未处理");
        data=new ArrayList<>();
        data.add(myDocument);
        data.add(myDocument);

        adaper=new Adapter_Document(data);
        rv.setAdapter(adaper);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back:

                finish();
                break;
            case R.id.shift:


                break;

            case R.id.condation:


                break;

            case R.id.cancel:


                break;

            case R.id.mohu:

                Intent intent= new Intent(this, Search_Activity.class);
                startActivity(intent);

                break;


            case R.id.process:

                    process.setSelected(true);
                    mark.setSelected(false);

                    // todo 网络请求获取  正在办理  的文件，适配到adapter上，并刷新RecyclerView的显示

                    MyDocument myDocument2=new MyDocument("我的我iu","2016-12-21","未处理");
                    data=new ArrayList<>();
                    data.add(myDocument2);
                    data.add(myDocument2);
                    data.add(myDocument2);
                    adaper=new Adapter_Document(data);
                    rv.setAdapter(adaper);
                    adaper.notifyDataSetChanged();

                break;

            case R.id.mark:

                    process.setSelected(false);
                    mark.setSelected(true);

                    MyDocument myDocument3=new MyDocument("是否听歌向","2016-12-21","未处理");
                    data=new ArrayList<>();
                    data.add(myDocument3);
                    data.add(myDocument3);
                    data.add(myDocument3);
                    adaper=new Adapter_Document(data);
                    rv.setAdapter(adaper);
                    adaper.notifyDataSetChanged();

                    // todo 网络请求获取  标记  的文件，适配到adapter上，并刷新RecyclerView的显示


                break;
        }
    }
int  x,y;
    class Adapter_Document extends RecyclerView.Adapter<Adapter_Document.MyViewHolder> {

        private View view = null;
        private ArrayList<MyDocument> datas = null;
        private int position_before=-1;


        public Adapter_Document(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_document, parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            // 长按item触发的方法
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    // todo 弹出并处理此item的三个选项

                    View view=getLayoutInflater().inflate(R.layout.long_press,null);
                    popupWindow=new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);

                    // 初始化popupwindow的点击控件
                    TextView shift=(TextView) view.findViewById(R.id.shift);
                    TextView condation=(TextView) view.findViewById(R.id.condation);
                    TextView cancle=(TextView) view.findViewById(R.id.cancle);

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    // 注册popupwindow里面的点击事件
                    shift.setOnClickListener(MyDocument_Acvity.this);
                    condation.setOnClickListener(MyDocument_Acvity.this);
                    cancle.setOnClickListener(MyDocument_Acvity.this);

                    int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    view.measure(width,height);
                    int height2=view.getMeasuredHeight();
                    int width2=view.getMeasuredWidth();
                    System.out.println(view.getWidth()+"**"+view.getHeight());
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,x-(width2/2),y-(height2));

                    return true;
                }
            });
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 加载popupwindow的布局
                    x= (int) event.getRawX();
                    y= (int) event.getRawY();
                    return false;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(position_before==-1){
                        datas.get(position).setIsSelected("true");
                    }else if(position_before!=position){
                        datas.get(position_before).setIsSelected("false");
                        adaper.notifyItemChanged(position_before);
                    }
                    holder.hasRead.setVisibility(View.VISIBLE);
                    position_before=position;

                    Intent intent=new Intent(MyDocument_Acvity.this,Details_Document_Activity.class);
                    // todo 跳转添加数据
                    startActivity(intent);

                }
            });
            holder.theme.setText(datas.get(position).getTheme());
            holder.time.setText(datas.get(position).getTime());
            holder.status.setText(datas.get(position).getStatus());

            if(datas.get(position).getIsSelected().equals("true")){
                holder.hasRead.setVisibility(View.VISIBLE);
            }else{
                holder.hasRead.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView theme;
            private TextView time;
            private TextView status;
            private View hasRead;

            public MyViewHolder(View itemView) {

                super(itemView);
                theme = (TextView) itemView.findViewById(R.id.theme);
                time = (TextView) itemView.findViewById(R.id.time);
                status = (TextView) itemView.findViewById(R.id.status);
                hasRead =  itemView.findViewById(R.id.hasRead);
            }
        }
    }

}
