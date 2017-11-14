package com.lanwei.governmentstar.activity.dzgd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.AnimActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Nizhi;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/5.
 */

public class Chooses_Receivers_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private TextView title;
    private TextView finish;
    private ExpandableListView expandableListView;
    private MyAdapter myAdapter= new MyAdapter();
    private Logging_Success bean;
    private GovernmentApi api;
    private Call<Return_Nizhi> call;
    private List<Return_Nizhi.DeptMapBean> deptMap;
    private ArrayList<GroupBean> groupList = new ArrayList<>();
    private String zhusong = "";
    private String[] zhusong_array;
    private ArrayList<String> list_zhu =new ArrayList<>();
    private String caosong = "";
    private String[] caosong_array ;
    private ArrayList<String> list_cao =new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweights();
        parse();

        if(getIntent().getStringExtra("type").equals("zhu")){
            title.setText("主送机关");
        }else{
            title.setText("抄送机关");
        }

        // 获取bean;
        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();

        call =api.nizhi_comin(bean.getData().getOpId(),getIntent().getStringExtra("opId"));

        call.enqueue(new Callback<Return_Nizhi>() {
            @Override
            public void onResponse(Call<Return_Nizhi> call, Response<Return_Nizhi> response) {
                if(response.body().getData() != null){

                    deptMap =response.body().getDeptMap();

                    for(int i=0;i<deptMap.size();i++){

                        GroupBean groupBean = new GroupBean();
                        groupBean.setOpId(deptMap.get(i).getOpId());
                        groupBean.setOpName(deptMap.get(i).getOpName());

                        ArrayList<ChildrenBean> childernList=new ArrayList<>();
                        for(int j=0;j<deptMap.get(i).getChildList().size();j++){

                            for (Map.Entry<String, String> entry : deptMap.get(i).getChildList().get(j).entrySet()) {
                                ChildrenBean childrenBean = new ChildrenBean();
                                childrenBean.setOpId(entry.getKey());
                                childrenBean.setOpName(entry.getValue());
                                childernList.add(childrenBean);
                                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                            }

                        }

                        groupBean.setChildList(childernList);
                        groupList.add(groupBean);
                    }

                    if(getIntent().getStringExtra("type").equals("zhu")){

                        for(int i=0;i<list_zhu.size();i++){

                            for(int j=0;j<groupList.size();j++){

                                for(int w=0;w<groupList.get(j).getChildList().size();w++){

                                    if(groupList.get(j).getChildList().get(w).getOpId().equals(list_zhu.get(i))){

                                        groupList.get(j).getChildList().get(w).setChoosed(true);
                                    }
                                }
                            }
                        }
                    }else{
                        for(int i=0;i<list_cao.size();i++){

                            for(int j=0;j<groupList.size();j++){

                                for(int w=0;w<groupList.get(j).getChildList().size();w++){

                                    if(groupList.get(j).getChildList().get(w).getOpId().equals(list_cao.get(i))){

                                        groupList.get(j).getChildList().get(w).setChoosed(true);
                                    }
                                }
                            }
                        }

                    }

                    expandableListView.setAdapter(myAdapter);

                    // 检测点击，展开或收缩group，改变相应控件
                    expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                            if (!groupList.get(groupPosition).getTurn()) {
                                groupList.get(groupPosition).setTurn(true);
                                v.findViewById(R.id.shiftLeaders).setVisibility(View.GONE);
                                v.findViewById(R.id.shiftLeaders_down).setVisibility(View.VISIBLE);

                            } else {
                                groupList.get(groupPosition).setTurn(false);
                                v.findViewById(R.id.shiftLeaders).setVisibility(View.VISIBLE);
                                v.findViewById(R.id.shiftLeaders_down).setVisibility(View.GONE);

                            }

                            return false;
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<Return_Nizhi> call, Throwable t) {

                Toast.makeText(Chooses_Receivers_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:

                finish();
                break;

            case R.id.finish:

                if(getIntent().getStringExtra("type").equals("zhu")){
                    zhusong="";
                }else{
                    caosong="";
                }

                for(int i=0;i<groupList.size();i++){

                    for(int j=0;j<groupList.get(i).getChildList().size();j++){

                        if(groupList.get(i).getChildList().get(j).getChoosed()){

                            if(getIntent().getStringExtra("type").equals("zhu")){

                                zhusong = zhusong+groupList.get(i).getChildList().get(j).getOpId()+",";

                            }else{

                                caosong = caosong +groupList.get(i).getChildList().get(j).getOpId()+",";

                        }
                    }
                }
             }

                if (getIntent().getStringExtra("type").equals("zhu")){

                    if(!zhusong.equals("")){

                        if(zhusong.substring(zhusong.length()-1,zhusong.length()).equals(",")){
                            zhusong=zhusong.substring(0,zhusong.length()-1);
                        }

                    }
                    Log.e("zhusong",zhusong);

                }else{

                    if(!caosong.equals("")){

                        if(caosong.substring(caosong.length()-1,caosong.length()).equals(",")){
                            caosong=caosong.substring(0,caosong.length()-1);
                        }
                    }
                    Log.e("caosong",caosong);
                }

                Intent intent = new Intent();
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                setResult(520,intent);
                finish();

                break;



        }
    }

    void parse(){

        zhusong = getIntent().getStringExtra("zhusong");
        caosong = getIntent().getStringExtra("caosong");

        if(!zhusong.equals("")){
            zhusong_array = zhusong.split(",");
            // 数组转为Arraylist
            list_zhu = new ArrayList<>(Arrays.asList(zhusong_array));
        }

        if(!caosong.equals("")) {
            caosong_array = caosong.split(",");
            // 数组转为Arraylist
            list_cao = new ArrayList<>(Arrays.asList(caosong_array));
        }

    }

    void initweights(){

        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        finish = (TextView) findViewById(R.id.finish);
        expandableListView = (ExpandableListView) findViewById(R.id.expandablelistview);
        expandableListView.setGroupIndicator(null);
        expandableListView.setDivider(null);

        back.setOnClickListener(this);
        finish.setOnClickListener(this);

      }

    class MyAdapter extends BaseExpandableListAdapter {

        private Handler handler;

        public MyAdapter() {

            handler = new Handler() {

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
            return groupList.get(groupPosition).getChildList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groupList.get(groupPosition).getChildList().get(childPosition);
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


        // 返回group子视图布局
        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.item_out, parent, false);

            ImageView shiftLeaders = (ImageView) convertView.findViewById(R.id.shiftLeaders);
            ImageView shiftLeaders_down = (ImageView) convertView.findViewById(R.id.shiftLeaders_down);
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText(groupList.get(groupPosition).getOpName());

            if (!groupList.get(groupPosition).getTurn()) {
                shiftLeaders.setVisibility(View.VISIBLE);
                shiftLeaders_down.setVisibility(View.GONE);
            } else {
                shiftLeaders.setVisibility(View.GONE);
                shiftLeaders_down.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        // 返回children子视图布局
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.item_in, parent, false);
            ImageView isChoose = (ImageView) convertView.findViewById(R.id.isChoose);
            isChoose.setVisibility(View.VISIBLE);
            TextView name = (TextView) convertView.findViewById(R.id.name);

            final LinearLayout all_layoutc = (LinearLayout) convertView.findViewById(R.id.all_layoutc);
            name.setText(groupList.get(groupPosition).getChildList().get(childPosition).getOpName());
            name.setTextColor(getResources().getColor(R.color.color_23));
            if (!groupList.get(groupPosition).getChildList().get(childPosition).getChoosed()) {
                isChoose.setSelected(false);
            } else {
                isChoose.setSelected(true);
            }

            all_layoutc.setTag(0);


            if(getIntent().getStringExtra("type").equals("zhu")){

                for (int i = 0; i < list_cao.size(); i++) {
                    if (list_cao.get(i).equals(groupList.get(groupPosition).getChildList().get(childPosition).getOpId())) {
                        all_layoutc.setTag(1);
                        isChoose.setVisibility(View.INVISIBLE);
                        name.setTextColor(getResources().getColor(R.color.color_aaa));
                        name.setText(groupList.get(groupPosition).getChildList().get(childPosition).getOpName()+"(不可选择)");
                        break;
                    }
                }


            }else{


                for (int i = 0; i < list_zhu.size(); i++) {
                    if (list_zhu.get(i).equals(groupList.get(groupPosition).getChildList().get(childPosition).getOpId())) {
                        all_layoutc.setTag(1);
                        isChoose.setVisibility(View.INVISIBLE);
                        name.setTextColor(getResources().getColor(R.color.color_aaa));
                        name.setText(groupList.get(groupPosition).getChildList().get(childPosition).getOpName()+"(不可选择)");
                        break;
                    }
                }

            }

            all_layoutc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (((int) v.getTag()) == 0) {

                        //添加到集合里以后，判断该条目的状态   并设置该条目的状态
                        if (groupList.get(groupPosition).getChildList().get(childPosition).getChoosed()) {
                            groupList.get(groupPosition).getChildList().get(childPosition).setChoosed(false);
                        } else {
                            groupList.get(groupPosition).getChildList().get(childPosition).setChoosed(true);
                        }

                        myAdapter.refresh();

                    }
                }

            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


      class GroupBean{

          private String opId;
          private String opName;
          private ArrayList<ChildrenBean> childList;
          private Boolean isTurn = false;

          public GroupBean() {
          }

          public String getOpId() {
              return opId;
          }

          public void setOpId(String opId) {
              this.opId = opId;
          }

          public String getOpName() {
              return opName;
          }

          public void setOpName(String opName) {
              this.opName = opName;
          }

          public ArrayList<ChildrenBean> getChildList() {
              return childList;
          }

          public void setChildList(ArrayList<ChildrenBean> childList) {
              this.childList = childList;
          }

          public Boolean getTurn() {
              return isTurn;
          }

          public void setTurn(Boolean turn) {
              isTurn = turn;
          }
      }



      class ChildrenBean{

          private String opId;
          private String opName;
          private Boolean isChoosed = false;

          public ChildrenBean() {
          }


          public String getOpId() {
              return opId;
          }

          public void setOpId(String opId) {
              this.opId = opId;
          }

          public String getOpName() {
              return opName;
          }

          public void setOpName(String opName) {
              this.opName = opName;
          }

          public Boolean getChoosed() {
              return isChoosed;
          }

          public void setChoosed(Boolean choosed) {
              isChoosed = choosed;
          }
      }




}
