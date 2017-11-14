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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Item_Collection;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/21.
 */

public class MyCollection_Activity extends BaseActivity implements View.OnClickListener{


    private TextView marked;
    private ImageView back;
    private ImageView mohu;
    private RecyclerView rv;
    private Adapter_Collection adapter;
    private ArrayList<Item_Collection> data = null;
    private LinearLayout condition;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mycollect);
        //设置沉浸式状态栏,必须在setContentView方法之前执行
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
//            if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else {
//                condition=(LinearLayout) findViewById(R.id.condition);
//                condition.setVisibility(View.GONE);
//            }

        marked=(TextView) findViewById(R.id.marked);
        back=(ImageView) findViewById(R.id.back);
        rv=(RecyclerView) findViewById(R.id.rv);
        mohu=(ImageView) findViewById(R.id.mohu);

        marked.setOnClickListener(this);
        back.setOnClickListener(this);
        mohu.setOnClickListener(this);

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setVisibility(View.INVISIBLE);
        // todo 网络请求获取收藏的item,并适配上recyclerview上

        Item_Collection collection=new Item_Collection("丰富","颠三倒四");
        data=new ArrayList<>();
        data.add(collection);
        data.add(collection);
        adapter=new Adapter_Collection(data);
        rv.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.marked){
            // todo 在RecyclerView中只展现marked(标记过)的item

            Item_Collection collection2=new Item_Collection("丰富","w微软");
            adapter.addItem(collection2);
            adapter.notifyDataSetChanged();

        }
        if(v.getId()==R.id.back){
            finish();
        }

        if(v.getId()==R.id.shift){
            // 转发
        }

        if(v.getId()==R.id.condation){
            // 状态
        }

        if(v.getId()==R.id.cancel){
            // 标记
        }


        if(v.getId()==R.id.mohu){
            Intent intent=new Intent(this,Search_Activity.class);
            startActivity(intent);
        }
    }

     class Adapter_Collection extends RecyclerView.Adapter<Adapter_Collection.MyViewHolder> {

        private View view = null;
        private ArrayList<Item_Collection> datas = null;
         private int position_before=-1;
         private PopupWindow popupWindow;

        public Adapter_Collection(ArrayList datas) {
            this.datas = datas;
        }

        void removeitem(int i){
            datas.remove(i);
        }
        void addItem(Item_Collection item){
            datas.add(item);
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_collection,parent,false);

            return new MyViewHolder(view);
        }

         int  x,y;
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
                    shift.setOnClickListener(MyCollection_Activity.this);
                    condation.setOnClickListener(MyCollection_Activity.this);
                    cancle.setOnClickListener(MyCollection_Activity.this);

                    int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    view.measure(width,height);
                    int height2=view.getMeasuredHeight();
                    int width2=view.getMeasuredWidth();
                    System.out.println(view.getWidth()+"**"+view.getHeight());
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,x-(width2/2),y-(height2));

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
                        adapter.notifyItemChanged(position_before);
                    }

                    holder.hasRead.setVisibility(View.VISIBLE);
                    position_before=position;

                    Intent intent=new Intent(MyCollection_Activity.this,Cpllection_Activity.class);
                    startActivity(intent);
                }
            });


            holder.theme.setText(datas.get(position).getTheme());
            holder.time.setText(datas.get(position).getTime());
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
            private View hasRead;

            public MyViewHolder(View itemView) {

                super(itemView);
                theme = (TextView) view.findViewById(R.id.theme);
                time = (TextView) view.findViewById(R.id.time);
                hasRead= view.findViewById(R.id.hasRead);
            }
        }
    }

}
