package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Expanable_Bean;
import com.lanwei.governmentstar.demo.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/23.
 */

public class Operation_State_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    // ExpandableListView的标题右侧的箭头
    private ImageView logging_oritation;
    private ImageView destop_oritation;
    private ImageView question_oritation;

    //  查询最多的问题
    private LinearLayout question1;
    private LinearLayout question2;

    // ExpandableListView的标题
    private RelativeLayout question_logging;
    private LinearLayout desktop_question;
    private LinearLayout person_question;


    private ExpandableListView logging_question;
    private ExpandableListView question_operation;
    private ExpandableListView question_person;
    private LinearLayout condition;

    // 初始化ExpandableListView的数据源
    private List<String> groupList_logging;//外层的数据源
    private List<List<String>> childList_logging;//里层的数据源
    private MyAdapter adapter;

    private List<String> groupList_destop;//外层的数据源
    private List<List<String>> childList_destop;//里层的数据源

    private List<String> groupList_person;//外层的数据源
    private List<List<String>> childList_person;//里层的数据源

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_operation);

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

        back=(ImageView)findViewById(R.id.back);

        // 标题的箭头方向
        logging_oritation=(ImageView)findViewById(R.id.logging_oritation);
        destop_oritation=(ImageView)findViewById(R.id.destop_oritation);
        question_oritation=(ImageView)findViewById(R.id.question_oritation);
        back=(ImageView)findViewById(R.id.back);

        // 询问最多的问题
        question1=(LinearLayout) findViewById(R.id.question1);
        question2=(LinearLayout) findViewById(R.id.question2);

        // 标题栏的内容
        question_logging=(RelativeLayout) findViewById(R.id.question_logging);
        desktop_question=(LinearLayout) findViewById(R.id.desktop_question);
        person_question=(LinearLayout) findViewById(R.id.person_question);

        //  ExpandableListView的控件
        logging_question=(ExpandableListView) findViewById(R.id.logging_question);
        question_operation=(ExpandableListView) findViewById(R.id.question_operation);
        question_person=(ExpandableListView) findViewById(R.id.question_person);

        // 取消ExpandableListView的默认箭头
        logging_question.setGroupIndicator(null);
        question_operation.setGroupIndicator(null);
        question_person.setGroupIndicator(null);

        // 初始化ExpandableListView的可见性，默认不可见
        logging_question.setVisibility(View.GONE);
        question_operation.setVisibility(View.GONE);
        question_person.setVisibility(View.GONE);

        // 监听
        question1.setOnClickListener(this);
        question2.setOnClickListener(this);
        question_logging.setOnClickListener(this);
        desktop_question.setOnClickListener(this);
        person_question.setOnClickListener(this);

        // todo 设置content
        back.setOnClickListener(this);

        initDatas();
    }


    void initDatas(){

        groupList_logging=new ArrayList<>();
        groupList_logging.add("用户账号");

        childList_logging=new ArrayList<>();
        ArrayList<String> list=new ArrayList();
        list.add("用户账号的获取");
        list.add("账号丢失");
        childList_logging.add(list);
        adapter=new MyAdapter(groupList_logging,childList_logging);
        logging_question.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back:

                finish();
                break;
            case R.id.question1:
            case R.id.question2:
                Intent intent=new Intent(this,Step_Operation.class);
                startActivity(intent);

                break;

            case R.id.question_logging:

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

    class MyAdapter  extends BaseExpandableListAdapter {

        private List<String> groupList;//外层的数据源
        private List<List<String>> childList;//里层的数据源
        private Handler handler;

        public MyAdapter(List<String> groupList, List<List<String>> childList) {
            this.groupList = groupList;
            this.childList = childList;

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
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
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
            return true;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.expandable_item_out,parent,false);
            }
            final TextView theme=(TextView) convertView.findViewById(R.id.theme);
            theme.setText(groupList.get(groupPosition));

            return convertView;
        }


        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if(convertView==null){

                convertView=getLayoutInflater().inflate(R.layout.expandable_item_in,parent,false);
            }

            final TextView item_china=(TextView) convertView.findViewById(R.id.item_china);
            item_china.setText(childList.get(groupPosition).get(childPosition));
            item_china.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(Operation_State_Activity.this,"点击了"+item_china.getText().toString(),Toast.LENGTH_SHORT).show();

                }
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }




}
