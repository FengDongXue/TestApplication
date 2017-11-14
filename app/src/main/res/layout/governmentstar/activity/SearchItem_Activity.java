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
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.MyDocument;
import com.lanwei.governmentstar.bean.Search_item;
import com.lanwei.governmentstar.demo.BaseActivity;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/28.
 */

public class SearchItem_Activity extends BaseActivity implements View.OnClickListener{


    private RecyclerView rv;
    private ImageView back;
    private TextView title;
    private ArrayList<Search_item> data;
    private Adapter_SearchItem adapter;
    private LinearLayout condition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_item);

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


        back=(ImageView) findViewById(R.id.back);
        title=(TextView) findViewById(R.id.title);
        rv=(RecyclerView) findViewById(R.id.rv);

        back.setOnClickListener(this);

        title.setText(getIntent().getStringExtra("theme"));

        // todo  判断来源，适配数据

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));

        data=new ArrayList<>();
        Search_item item=new Search_item("渔阳镇人民政府");
        data.add(item);
        data.add(item);
        data.add(item);
        adapter=new Adapter_SearchItem(data);

        rv.setAdapter(adapter);

    }


    class Adapter_SearchItem extends RecyclerView.Adapter<Adapter_SearchItem.MyViewHolder> {

        private View view = null;
        private ArrayList<Search_item> datas = null;

        public Adapter_SearchItem(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_search_layout, parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final String content=datas.get(position).getTheme();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent();
                    intent.putExtra("selection",content);
                    setResult(2,intent);
                    finish();

                }
            });
            holder.theme.setText(content);

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView theme;

            public MyViewHolder(View itemView) {

                super(itemView);
                theme = (TextView) itemView.findViewById(R.id.theme);

            }
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back:

                Intent intent2=new Intent();
                intent2.putExtra("selection","");
                setResult(0,intent2);
                finish();

                break;

        }

    }
}
