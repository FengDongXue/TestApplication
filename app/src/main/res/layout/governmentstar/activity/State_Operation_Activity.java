package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.MyDocument;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/27.
 */

public class State_Operation_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private LinearLayout question1;
    private LinearLayout question2;
    private RelativeLayout question_logging;
    private LinearLayout desktop_question;
    private LinearLayout person_question;

    private ExpandableListView logging_question;
    private ExpandableListView question_operation;
    private ExpandableListView question_person;

    private RecyclerView rv;
    private ArrayList<String> data;
    private Adapter_State adaper;
    private LinearLayout condition;

    // ExpandableListView的标题右侧的箭头
    private ImageView logging_oritation;
    private ImageView destop_oritation;
    private ImageView question_oritation;

    private ExpandInfoAdapter adapter_logging;
    private ExpandInfoAdapter adapter_operation;
    private ExpandInfoAdapter adapter_person;
    ArrayList<String> list_logging_group;
    ArrayList<ArrayList<String>> list_logging_children;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_operation);

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
        if (Build.VERSION.SDK_INT >= 21){
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }


        initweight();

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));

        // todo 网络请求，获取正在办理文件的数据给adapter的数据源赋值

        data=new ArrayList<>();
        data.add("如何获取用户账号？");

        adaper=new Adapter_State(data);
        rv.setAdapter(adaper);

    }

    void initweight(){
        back=(ImageView) findViewById(R.id.back);
        rv=(RecyclerView) findViewById(R.id.rv);

        question1=(LinearLayout) findViewById(R.id.question1);
        question2=(LinearLayout) findViewById(R.id.question2);
        question_logging=(RelativeLayout) findViewById(R.id.question_logging);
        desktop_question=(LinearLayout) findViewById(R.id.desktop_question);
        person_question=(LinearLayout) findViewById(R.id.person_question);

        logging_question=(ExpandableListView)findViewById(R.id.logging_question);
        question_operation=(ExpandableListView)findViewById(R.id.question_operation);
        question_person=(ExpandableListView)findViewById(R.id.question_person);

        logging_question.setGroupIndicator(null);
        question_operation.setGroupIndicator(null);
        question_person.setGroupIndicator(null);

        // 标题的箭头方向
        logging_oritation=(ImageView)findViewById(R.id.logging_oritation);
        destop_oritation=(ImageView)findViewById(R.id.destop_oritation);
        question_oritation=(ImageView)findViewById(R.id.question_oritation);

        back.setOnClickListener(this);
        question_logging.setOnClickListener(this);
        desktop_question.setOnClickListener(this);
        person_question.setOnClickListener(this);

        list_logging_group=new ArrayList<>();
        list_logging_group.add("吃饭十分出色的");
        list_logging_group.add("微软few发");
        list_logging_group.add("微软few发");

        Log.e("发v第三方的v",""+list_logging_group.toString());

        list_logging_children =new ArrayList<>();
        list_logging_children.add(list_logging_group);
        list_logging_children.add(list_logging_group);
        list_logging_children.add(list_logging_group);

        Log.e("发v第三方的v",""+list_logging_children.toString());

        adapter_logging=new ExpandInfoAdapter();

        logging_question.setAdapter(adapter_logging);
        question1.setOnClickListener(this);
        question2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:

                finish();
                break;

            case R.id.question1:

                Intent intent=new Intent(this,Step_Operation.class);
                intent.putExtra("theme","如何获取用户账号？");
                startActivity(intent);

                break;


            case R.id.question2:

                Intent intent2=new Intent(this,Step_Operation.class);
                intent2.putExtra("theme","为什么我不能发布领导指示？");
                startActivity(intent2);

                break;

            case R.id.question_logging:

                // 适配ExpandableListView里面的数据
                if(logging_question.getVisibility()==View.GONE){
                    logging_question.setVisibility(View.VISIBLE);
                    logging_oritation.setSelected(true);

                }else{
                    logging_question.setVisibility(View.GONE);
                    logging_oritation.setSelected(false);
                }
                break;

            case R.id.desktop_question:

                if(question_operation.getVisibility()==View.GONE){
                    question_operation.setVisibility(View.VISIBLE);
                    destop_oritation.setSelected(true);

                }else{
                    question_operation.setVisibility(View.GONE);
                    destop_oritation.setSelected(false);
                }

                break;

            case R.id.person_question:

                // 适配ExpandableListView里面的数据
                if(question_person.getVisibility()==View.GONE){
                    question_person.setVisibility(View.VISIBLE);
                    question_oritation.setSelected(true);

                }else{
                    question_person.setVisibility(View.GONE);
                    question_oritation.setSelected(false);
                }

                break;

        }
    }

        class Adapter_State extends RecyclerView.Adapter<Adapter_State.MyViewHolder> {

        private View view = null;
        private ArrayList<String> datas = null;

        public Adapter_State(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_match, null);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent4=new Intent(State_Operation_Activity.this,Step_Operation.class);
                    intent4.putExtra("theme",datas.get(position));
                    startActivity(intent4);

                }
            });

            holder.item_content.setText(datas.get(position));

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView item_content;

            public MyViewHolder(View itemView) {

                super(itemView);
                item_content = (TextView) view.findViewById(R.id.item_content);

            }
        }
    }

    public class ExpandInfoAdapter extends BaseExpandableListAdapter{

        private Handler handler;

        public ExpandInfoAdapter() {

            handler = new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    notifyDataSetChanged();
                    super.handleMessage(msg);
                }
            };

        }

        public void refresh() {
            handler.sendMessage(new Message());
        }


        @Override
        public int getGroupCount() {
            return list_logging_group.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list_logging_children.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return list_logging_group.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return list_logging_children.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.item_expandable,parent,false);
            }

            TextView content=(TextView) convertView.findViewById(R.id.content);
            content.setText(list_logging_group.get(groupPosition));

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.item_children,parent,false);
            }
            TextView content=(TextView) convertView.findViewById(R.id.content);
            content.setText(list_logging_children.get(groupPosition).get(childPosition));

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }



}




