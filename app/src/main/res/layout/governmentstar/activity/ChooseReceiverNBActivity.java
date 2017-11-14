package com.lanwei.governmentstar.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.bean.Expanable_Bean;
import com.lanwei.governmentstar.bean.Expanable_Bean_Child;
import com.lanwei.governmentstar.bean.InCirculationTree;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Wait;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/1.
 */

public class ChooseReceiverNBActivity extends BaseActivity implements View.OnClickListener {

    private ExpandableListView elv;
    private ExpandableListView elv_2;
    private ImageView back;
    private TextView myapply;
    private LinearLayout chengban;

    private List<Expanable_Bean> group_List;
    private List<Expanable_Bean> group_List_2;
    private List<List<Expanable_Bean_Child>> child_List;
    private List<List<Expanable_Bean_Child>> child_List_2;
    private MyAdapter myAdapter;
    private MyAdapter_2 myAdapter_2;
    private TextView tv_address;
    private ImageView shiftLeaders;
    private ImageView shiftLeaders_down;
    private TextView textView;
    private TextView banliren;
    private TextView xiebanren;
    private ImageView isChoose;
    private TextView name;
    private  String opId_choosed;
    private  String opId_choosed_cieban;
    private GovernmentApi api;
    private CircleImageView iv_contacts;
    int m=0;
    int b=0;
    int n=0;
    private LinearLayout result_search;
    private TextView null_search;
    private EditText search;
    private RecyclerView recyclerView;
    private Adapter_Addtion adapter_addtion;
    private Adapter_Addtion2 adapter_Addtion2;
    private ScrollView scrollView;
    private ArrayList<Search> list_choose =new ArrayList<>();
    private String amount = "banli";
    private int count =0;

    private String account_role = "";

    ArrayList<String> opIds_out=new ArrayList<>();
    ArrayList<String> list =new ArrayList<>();

    ArrayList<String> opXbrId_out=new ArrayList<>();
    ArrayList<String> list2 =new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_receiver2);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweight();

        if(getIntent().getStringExtra("title").equals("选择办事人")){
            chengban= (LinearLayout) findViewById(R.id.chengban);
            banliren= (TextView) findViewById(R.id.banliren);
            xiebanren= (TextView) findViewById(R.id.xiebanren);
            xiebanren.setOnClickListener(this);
            banliren.setOnClickListener(this);
            String defString = PreferencesManager.getInstance(this, "accountBean").get("jsonStr");
            Gson gson = new Gson();
            Logging_Success bean = gson.fromJson(defString, Logging_Success.class);
            api = HttpClient.getInstance().getGovernmentApi();
            String userId = bean.getData().getOpId();
            String deptId = bean.getData().getAccountDeptId();
            Call<InCirculationTree> call = api.inCirculationTreeX(userId, deptId, getIntent().getStringExtra("opId"));
            amount = "xieban";
            call.enqueue(new Callback<InCirculationTree>() {
                @Override
                public void onResponse(Call<InCirculationTree> call, Response<InCirculationTree> response) {
                    if (response.body().getDeptList() == null) {
//                    Toast.makeText(ChooseReceiverNBActivity.this, "", Toast.LENGTH_SHORT).show();
                    } else {
                        // 实体类含义，第一个int是item的是否选择的标志位，第二个int是group是否展开的标志位
                        getdata_2(response.body());
                       count++;
                        if(getIntent().getIntExtra("isNotAssist",0)==1){

                            tv_address.setText("选择协办人");
                            chengban.setVisibility(View.GONE);

                            myAdapter_2 = new MyAdapter_2(group_List_2, child_List_2);

                            elv_2.setVisibility(View.VISIBLE);
                            elv.setVisibility(View.GONE);
                            elv_2.setGroupIndicator(null);
                            elv_2.setAdapter(myAdapter_2);
                            elv_2.setDivider(null);

                        }else{
                            chengban.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<InCirculationTree> call, Throwable t) {

                    Toast.makeText(ChooseReceiverNBActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

                }
            });

            if(getIntent().getIntExtra("isNotAssist",0)==0){
                amount = "banli";
            }
            getDate();
            initsearch();

        }else{
            amount = "banli";
            getDate();
            initsearch();
        }

    }

    void initsearch(){
        search.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                    list_choose.clear();
                    result_search.setVisibility(View.VISIBLE);
                    if(!search.getText().toString().trim().equals("")){
                        list_choose.clear();
                        if(amount.equals("banli")){

                            for(int i=0;i<group_List.size();i++){

                                for(int j=0;j<child_List.get(i).size();j++){

                                    if(child_List.get(i).get(j).getContent().getOpName().contains(search.getText().toString().trim())){

                                        list_choose.add(new Search(i,j));
                                    }
                                }

                            }
                            adapter_addtion =new Adapter_Addtion();
                            recyclerView.setAdapter(adapter_addtion);
                            recyclerView.setVisibility(View.VISIBLE);

                            if(list_choose.size()<1){
                                null_search.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else{
                                null_search.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }


                        }else if(amount.equals("xieban")){
                            list_choose.clear();
                            for(int i=0;i<group_List_2.size();i++){

                                for(int j=0;j<child_List_2.get(i).size();j++){

                                    if(child_List_2.get(i).get(j).getContent().getOpName().contains(search.getText().toString().trim())){

                                        list_choose.add(new Search(i,j));
                                    }
                                }

                            }
                            adapter_Addtion2 =new Adapter_Addtion2();
                            recyclerView.setAdapter(adapter_Addtion2);
                            recyclerView.setVisibility(View.VISIBLE);

                            if(list_choose.size()<1){
                                null_search.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else{
                                null_search.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }

                        }

                    }else{
                        null_search.setVisibility(View.VISIBLE);
                    }

                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            scrollView.fullScroll(ScrollView.FOCUS_UP);
                        }
                    },300);

                }
                return false;
            }
        });
    }


    private void getDate() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<InCirculationTree> call = null;
        //转发树
        if ("swcyZfTree".equals(getIntent().getStringExtra("action"))) {
            call = api.inCirculationTreeZ(getIntent().getStringExtra("action"), new GetAccount(this).opId(), new GetAccount(this).sectorId(), getIntent().getStringExtra("opId"));
        } else {
            call = api.inCirculationTree2(getIntent().getStringExtra("opId"),getIntent().getStringExtra("action"), new GetAccount(this).opId(), new GetAccount(this).dptId());
        }

        call.enqueue(new Callback<InCirculationTree>() {
            @Override
            public void onResponse(Call<InCirculationTree> call, Response<InCirculationTree> response) {
                if (response.body().getDeptList() == null) {
//                    Toast.makeText(ChooseReceiverNBActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    // 实体类含义，第一个int是item的是否选择的标志位，第二个int是group是否展开的标志位
                    getdata(response.body());
                    count++;
                }
            }

            @Override
            public void onFailure(Call<InCirculationTree> call, Throwable t) {
                Toast.makeText(ChooseReceiverNBActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
    }

    private void getdata(InCirculationTree inCirculationTree) {

        for (int i = 0; i < inCirculationTree.getDeptList().size(); i++) {
            group_List.add(new Expanable_Bean(0, inCirculationTree.getDeptList().get(i), 0));
            ArrayList<Expanable_Bean_Child> list = new ArrayList<>();

            for (int j = 0; j < inCirculationTree.getDeptList().get(i).getChildList().size(); j++) {//子类
                list.add(new Expanable_Bean_Child(0, inCirculationTree.getDeptList().get(i).getChildList().get(j), 0));
            }
            child_List.add(list);
        }

        if(getIntent().getStringExtra("action").equals("swcyCbTree")){
            list2 = inCirculationTree.getOpIds();
        }

        // 之前已进入此界面选择了联系人，返回到处理界面后又跳回来，需要恢复显示之前选中的人，这些已选中的数据由前一个界面记录，并返回过来，并在此匹配一下并选中这个人
        if(!getIntent().getStringExtra("opIds").equals("")){

            opId_choosed=getIntent().getStringExtra("opIds");
            opId_choosed=opId_choosed+","+getIntent().getStringExtra("opBsrId");
            Log.e("已选择人的ID 协办人ids",opId_choosed);
            String[] opid_list=opId_choosed.split(",");

            // 数组转为Arraylist
            ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));

            for(int m=0;m<list2.size();m++){

                for(int n=0;n<group_List.size();n++){

                    for (int l = 0; l < child_List.get(n).size(); l++) {//子类

                        if(child_List.get(n).get(l).getContent().getOpId().equals(list2.get(m))){

                            child_List.get(n).get(l).setIsChoosed(1);
                            account_role = child_List.get(n).get(l).getContent().getAccountRole();
                            break;
                        }
                    }
                }
            }

        }

        if(!getIntent().getStringExtra("opXbrId").equals("")){

            opId_choosed_cieban=getIntent().getStringExtra("opXbrId");
            Log.e("已选择人的ID 协办人ids2",opId_choosed_cieban);
            String[] opid_list=opId_choosed_cieban.split(",");
            // 数组转为Arraylist
            ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));
            opXbrId_out.addAll(list2);

        }


        // 恢复group_list的状态
        for(int n=0;n<group_List.size();n++){

            for(int l = 0; l < child_List.get(n).size(); l++){

                if(child_List.get(n).get(l).getIsChoosed()==0){

                    break;

                }else{
                    if(l==child_List.get(n).size()-1){
                        group_List.get(n).setIsChoosed(1);
                    }
                }

            }
        }

        myAdapter = new MyAdapter();
        elv.setGroupIndicator(null);
        elv.setAdapter(myAdapter);
        elv.setDivider(null);

        // 检测点击，展开或收缩group，改变相应控件
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (group_List.get(groupPosition).getIsTurn() == 0) {
                    group_List.get(groupPosition).setIsTurn(1);
                    v.findViewById(R.id.shiftLeaders).setSelected(true);

                } else {
                    group_List.get(groupPosition).setIsTurn(0);
                    v.findViewById(R.id.shiftLeaders).setSelected(false);

                }
                return false;
            }
        });
    }



    private void getdata_2(InCirculationTree inCirculationTree) {

        for (int i = 0; i < inCirculationTree.getDeptList().size(); i++) {
            group_List_2.add(new Expanable_Bean(0, inCirculationTree.getDeptList().get(i), 0));
            ArrayList<Expanable_Bean_Child> list = new ArrayList<>();

            for (int j = 0; j < inCirculationTree.getDeptList().get(i).getChildList().size(); j++) {//子类
                list.add(new Expanable_Bean_Child(0, inCirculationTree.getDeptList().get(i).getChildList().get(j), 0));
            }
            child_List_2.add(list);
        }

       list = inCirculationTree.getOpIds();

        // 之前已进入此界面选择了联系人，返回到处理界面后又跳回来，需要恢复显示之前选中的人，这些已选中的数据由前一个界面记录，并返回过来，并在此匹配一下并选中这个人
        if(!getIntent().getStringExtra("opXbrId").equals("")){

            opId_choosed_cieban=getIntent().getStringExtra("opXbrId");
            Log.e("已选择人的ID 协办人ids2",opId_choosed_cieban);
            String[] opid_list=opId_choosed_cieban.split(",");
            // 数组转为Arraylist
            ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));


            for(int m=0;m<list2.size();m++){

                for(int n=0;n<group_List_2.size();n++){

                    for (int l = 0; l < child_List_2.get(n).size(); l++) {//子类

                        if(child_List_2.get(n).get(l).getContent().getOpId().equals(list2.get(m))){

                            child_List_2.get(n).get(l).setIsChoosed(1);
                           
                            break;
                        }
                    }
                }
            }
        }


        // 恢复group_list的状态
        for(int n=0;n<group_List_2.size();n++){

            for(int l = 0; l < child_List_2.get(n).size(); l++){

                if(child_List_2.get(n).get(l).getIsChoosed()==0){

                    break;

                }else{
                    if(l==child_List_2.get(n).size()-1){
                        group_List_2.get(n).setIsChoosed(1);
                    }
                }

            }
        }


        // 检测点击，展开或收缩group，改变相应控件
        elv_2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (group_List_2.get(groupPosition).getIsTurn() == 0) {
                    group_List_2.get(groupPosition).setIsTurn(1);
                    v.findViewById(R.id.shiftLeaders).setSelected(true);

                } else {
                    group_List_2.get(groupPosition).setIsTurn(0);
                    v.findViewById(R.id.shiftLeaders).setSelected(false);

                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:

                finish();
                break;

            case R.id.banliren:
                m=0;
                amount = "banli";
                if(count != 2){
                    return;
                }
                list_choose.clear();
                search.setText("");
                if(adapter_addtion != null){
                    adapter_addtion.notifyDataSetChanged();
                }
                result_search.setVisibility(View.GONE);
                banliren.setTextColor(getResources().getColor(R.color.blue));
                xiebanren.setTextColor(getResources().getColor(R.color.color_23));

                for(int i=0;i<group_List.size();i++){
                    group_List.get(i).setIsTurn(0);
                }
                opXbrId_out=new ArrayList<>();
                for (int i = 0; i < child_List_2.size(); i++) {
                    for (int j = 0; j < child_List_2.get(i).size(); j++) {
                        if (child_List_2.get(i).get(j).getIsChoosed() == 1){
                            InCirculationTree.ChildList content = child_List_2.get(i).get(j).getContent();
                            opXbrId_out.add(content.getOpId());
                        }
                    }
                }

                myAdapter = new MyAdapter();

                elv.setVisibility(View.VISIBLE);
                elv_2.setVisibility(View.GONE);

                elv.setGroupIndicator(null);
                elv.setAdapter(myAdapter);
                elv.setDivider(null);

                myAdapter.refresh();

                break;

            case R.id.xiebanren:
                m=1;
                amount = "xieban";
                // 保证两次请求都完成，以免点击后数据没有初始化
                if(count != 2){
                    return;
                }
                list_choose.clear();
                search.setText("");
                result_search.setVisibility(View.GONE);
                if(adapter_addtion != null){
                    adapter_addtion.notifyDataSetChanged();
                }
                xiebanren.setTextColor(getResources().getColor(R.color.blue));
                banliren.setTextColor(getResources().getColor(R.color.color_23));

                for(int i=0;i<group_List_2.size();i++){
                    group_List_2.get(i).setIsTurn(0);
                }
                opIds_out=new ArrayList<>();
                for (int i = 0; i < child_List.size(); i++) {
                    for (int j = 0; j < child_List.get(i).size(); j++) {
                        if (child_List.get(i).get(j).getIsChoosed() == 1){
                            InCirculationTree.ChildList content = child_List.get(i).get(j).getContent();
                            opIds_out.add(content.getOpId());
                        }
                    }
                }

                myAdapter_2 = new MyAdapter_2(group_List_2, child_List_2);

                elv_2.setVisibility(View.VISIBLE);
                elv.setVisibility(View.GONE);

                elv_2.setGroupIndicator(null);
                elv_2.setAdapter(myAdapter_2);
                elv_2.setDivider(null);

                myAdapter_2.refresh();
                break;

            case R.id.myapply:
                StringBuffer app = new StringBuffer();
                String opIds = "";
                String opXbrId = "";
                ArrayList<String> list_names =new ArrayList<>();
                String opNames = "";
                //获取选中的opIds
                for (int i = 0; i < child_List.size(); i++) {
                    for (int j = 0; j < child_List.get(i).size(); j++) {
                        if (child_List.get(i).get(j).getIsChoosed() == 1){
                            InCirculationTree.ChildList content = child_List.get(i).get(j).getContent();
                            opIds +=content.getOpId() + ",";
                            list_names.add(content.getOpName());
                        }
                    }
                }
                for (int i = 0; i < child_List_2.size(); i++) {
                    for (int j = 0; j < child_List_2.get(i).size(); j++) {
                        if (child_List_2.get(i).get(j).getIsChoosed() == 1){
                            InCirculationTree.ChildList content = child_List_2.get(i).get(j).getContent();
                            opXbrId +=content.getOpId() + ",";
                            list_names.add(content.getOpName());
                        }
                    }
                }

                if(list_names != null && list_names.size()>0){
                    opNames = list_names.get(0);
                }

                Log.e("已选择的办理人的ids",opIds);
                Log.e("已选择的协办人的ids",opXbrId);
                //截取最后一个字符串
                if (!opIds.equals("")){
                    opIds = opIds.substring(0, opIds.length() - 1);
                }
                //传值到父类
                Intent intent = new Intent();
                intent.putExtra("opIds", opIds);
                intent.putExtra("opBsrId","");
                intent.putExtra("opNbrId","");
                intent.putExtra("opXbrId",opXbrId);
                intent.putExtra("opNames", opNames);
                setResult(10, intent);
                finish();
               // Toast.makeText(this, app.toString() + "添加更多", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void initweight() {
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address.setVisibility(View.VISIBLE);
        elv = (ExpandableListView) findViewById(R.id.elv);
        elv_2 = (ExpandableListView) findViewById(R.id.elv_2);
        back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        myapply = (TextView) findViewById(R.id.myapply);
        myapply.setVisibility(View.VISIBLE);
        iv_contacts = (CircleImageView) findViewById(R.id.iv_contacts);
        iv_contacts.setVisibility(View.INVISIBLE);
        group_List = new ArrayList<>();
        group_List_2 = new ArrayList<>();
        child_List = new ArrayList<>();
        child_List_2 = new ArrayList<>();

        search=(EditText) findViewById(R.id.search);
        null_search=(TextView) findViewById(R.id.null_search);
        result_search=(LinearLayout) findViewById(R.id.result_search);
        scrollView=(ScrollView) findViewById(R.id.scrollView);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        // 为RecyclerView设置默认动画和线性布局管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        myapply.setText("确定");
        back.setOnClickListener(this);
        myapply.setOnClickListener(this);
        if (getIntent().getStringExtra("title") != null) {
            tv_address.setText(getIntent().getStringExtra("title"));
        }
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
            return group_List.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child_List.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group_List.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child_List.get(groupPosition).get(childPosition);
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

            shiftLeaders = (ImageView) convertView.findViewById(R.id.shiftLeaders);
            shiftLeaders_down = (ImageView) convertView.findViewById(R.id.shiftLeaders_down);
            textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText(group_List.get(groupPosition).getContent().getOpName());

            if (group_List.get(groupPosition).getIsTurn() == 0) {
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
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,  View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.item_in, parent, false);
            isChoose = (ImageView) convertView.findViewById(R.id.isChoose);
            isChoose.setVisibility(View.VISIBLE);
            name = (TextView) convertView.findViewById(R.id.name);
            final LinearLayout all_layoutc = (LinearLayout) convertView.findViewById(R.id.all_layoutc);
            name.setText(child_List.get(groupPosition).get(childPosition).getContent().getOpName());
            name.setTextColor(getResources().getColor(R.color.color_23));
            if (child_List.get(groupPosition).get(childPosition).getIsChoosed() == 0) {
                isChoose.setSelected(false);
//                name.setTextColor(Color.parseColor("#999999"));
            } else {
                isChoose.setSelected(true);
//                name.setTextColor(Color.parseColor("#333333"));
            }

            all_layoutc.setTag(0);
            opXbrId_out.addAll(list2);
            for(int i=0;i<opXbrId_out.size();i++){
                if(opXbrId_out.get(i).equals(child_List.get(groupPosition).get(childPosition).getContent().getOpId())){
                    n=1;
                    all_layoutc.setTag(1);
                    isChoose.setVisibility(View.INVISIBLE);
                    name.setTextColor(getResources().getColor(R.color.color_aaa));
                    name.setText(child_List.get(groupPosition).get(childPosition).getContent().getOpName()+"(不可选择)");
                    break;
                }
            }
                all_layoutc.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                   if (account_role.equals("")) {
                        account_role = child_List.get(groupPosition).get(childPosition).getContent().getAccountRole();
                      }

                    if(account_role.equals(child_List.get(groupPosition).get(childPosition).getContent().getAccountRole())){

                        if(((int)v.getTag())==0){

                            //添加到集合里以后，判断该条目的状态   并设置该条目的状态
                            if (child_List.get(groupPosition).get(childPosition).getIsChoosed() == 1) {
                                child_List.get(groupPosition).get(childPosition).setIsChoosed(0);
                            } else {
                                child_List.get(groupPosition).get(childPosition).setIsChoosed(1);
                            }
                            if(m==0){
                                myAdapter.refresh();
                            }else{
                                myAdapter_2.refresh();
                            }
                            if(adapter_addtion != null){
                                adapter_addtion.notifyDataSetChanged();
                            }
                        }

                      }

                      if (countNum() == 0) {
                        account_role = "";
                      }

                        }

                });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


        private int countNum() {
            int count = 0;
            for (int i = 0; i < child_List.size(); i++) {
                for (int j = 0; j < child_List.get(i).size(); j++) {
                    if (child_List.get(i).get(j).getIsChoosed() != 0){
                        count++;
                    }
                }
            }
            return count;
        }

    }


    class MyAdapter_2 extends BaseExpandableListAdapter {

        private List<Expanable_Bean> groupList;//外层的数据源
        private List<List<Expanable_Bean_Child>> childList;//里层的数据源
        private Handler handler;
        public MyAdapter_2(List<Expanable_Bean> groupList, List<List<Expanable_Bean_Child>> childList) {
            this.groupList = groupList;
            this.childList = childList;

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


        // 返回group子视图布局
        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_out, parent, false);

            }

            shiftLeaders = (ImageView) convertView.findViewById(R.id.shiftLeaders);
            shiftLeaders_down = (ImageView) convertView.findViewById(R.id.shiftLeaders_down);
            textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText(groupList.get(groupPosition).getContent().getOpName());

            if (groupList.get(groupPosition).getIsTurn() == 0) {
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

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_in, parent, false);

            }

            isChoose = (ImageView) convertView.findViewById(R.id.isChoose);
            isChoose.setVisibility(View.VISIBLE);
            name = (TextView) convertView.findViewById(R.id.name);
            final LinearLayout all_layoutc = (LinearLayout) convertView.findViewById(R.id.all_layoutc);
            name.setText(childList.get(groupPosition).get(childPosition).getContent().getOpName());
            name.setTextColor(getResources().getColor(R.color.color_23));
            if (childList.get(groupPosition).get(childPosition).getIsChoosed() == 0) {
                isChoose.setSelected(false);
//                name.setTextColor(Color.parseColor("#999999"));
            } else {
                isChoose.setSelected(true);
//                name.setTextColor(Color.parseColor("#333333"));
            }

            all_layoutc.setTag(0);
            opIds_out.addAll(list);
            for(int i=0;i<opIds_out.size();i++){
                if(opIds_out.get(i).equals(child_List_2.get(groupPosition).get(childPosition).getContent().getOpId())){
                    b=1;
                    all_layoutc.setTag(1);
                    isChoose.setVisibility(View.INVISIBLE);
                    name.setTextColor(getResources().getColor(R.color.color_aaa));
                    name.setText(childList.get(groupPosition).get(childPosition).getContent().getOpName()+"(不可选择)");
                    break;
                }
            }

                all_layoutc.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                            if(((int)v.getTag())==0){

                                //添加到集合里以后，判断该条目的状态   并设置该条目的状态
                                if (childList.get(groupPosition).get(childPosition).getIsChoosed() == 1) {
                                    childList.get(groupPosition).get(childPosition).setIsChoosed(0);
                                } else {
                                    childList.get(groupPosition).get(childPosition).setIsChoosed(1);
                                }
                                if(getIntent().getIntExtra("isNotAssist",0)==0){

                                    if(m==0){
                                        myAdapter.refresh();
                                    }else{
                                        myAdapter_2.refresh();
                                    }
                                }else{
                                    myAdapter_2.refresh();
                                }

                                if(adapter_Addtion2 != null){
                                    adapter_Addtion2.notifyDataSetChanged();
                                }

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

    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_out_ps, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getContent().getOpName(), search.getText().toString().trim());
            holder.textView.setText(spannableString);
            holder.department.setText("("+group_List.get(list_choose.get(position).getId_father()).getContent().getOpName()+")");
            holder.department.setVisibility(View.VISIBLE);
            holder.fragment.setVisibility(View.GONE);

            Boolean is_choose;

            if(child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                is_choose=true;
            }else{
                is_choose=false;
            }

            if(is_choose){
                holder.all_leaders.setSelected(true);
            }else{
                holder.all_leaders.setSelected(false);

            }

            holder.itemView.setTag(0);
            opXbrId_out.addAll(list2);
            for(int i=0;i<opXbrId_out.size();i++){
                if(opXbrId_out.get(i).equals(child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getContent().getOpId())){
                    n=1;
                    holder.itemView.setTag(1);
                    holder.department.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.textView.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.all_leaders.setVisibility(View.INVISIBLE);
                    break;
                }
            }

            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(((int)v.getTag())==0){

                        if(child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                            child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).setIsChoosed(0);
                            holder.all_leaders.setSelected(false);
                        }else{
                            child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).setIsChoosed(1);
                            holder.all_leaders.setSelected(true);
                        }

                        String isChoose = "true";
                        for (int i = 0; i < child_List.get(list_choose.get(position).getId_father()).size(); i++) {

                            if (child_List.get(list_choose.get(position).getId_father()).get(i).getIsChoosed() == 0) {
                                isChoose = "false";
                                break;
                            }
                        }

                        if (isChoose.equals("true")) {
                            group_List.get(list_choose.get(position).getId_father()).setIsChoosed(1);
                        } else {
                            group_List.get(list_choose.get(position).getId_father()).setIsChoosed(0);
                        }
                        myAdapter.refresh();

                    }



                }
            });
        }


        @Override
        public int getItemCount() {

            if(list_choose == null){
                return 0;
            }
            return list_choose.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            LinearLayout all_choose;
            ImageView all_leaders;
            TextView textView;
            TextView department;
            FrameLayout fragment;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                all_choose = (LinearLayout) itemView.findViewById(R.id.all_choose);
                all_leaders = (ImageView) itemView.findViewById(R.id.all_leaders);
                textView = (TextView) itemView.findViewById(R.id.textView);
                department = (TextView) itemView.findViewById(R.id.department);
                fragment = (FrameLayout) itemView.findViewById(R.id.fragment);
                line = itemView.findViewById(R.id.line);
            }
        }
    }

    // 附件的adapter
    class Adapter_Addtion2 extends RecyclerView.Adapter<Adapter_Addtion2.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion2() {

        }

        @Override
        public Adapter_Addtion2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_out_ps, parent, false);

            return new Adapter_Addtion2.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion2.MyViewHolder holder, final int position) {

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), child_List_2.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getContent().getOpName(), search.getText().toString().trim());
            holder.textView.setText(spannableString);
            holder.department.setText("("+group_List_2.get(list_choose.get(position).getId_father()).getContent().getOpName()+")");
            holder.department.setVisibility(View.VISIBLE);
            holder.fragment.setVisibility(View.GONE);

            Boolean is_choose;

            if(child_List_2.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                is_choose=true;
            }else{
                is_choose=false;
            }

            if(is_choose){
                holder.all_leaders.setSelected(true);
            }else{
                holder.all_leaders.setSelected(false);

            }

            holder.itemView.setTag(0);
            opIds_out.addAll(list);
            for(int i=0;i<opIds_out.size();i++){
                if(opIds_out.get(i).equals(child_List_2.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getContent().getOpId())){
                    b=1;
                    holder.itemView.setTag(1);
                    holder.department.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.textView.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.all_leaders.setVisibility(View.INVISIBLE);
                    break;
                }
            }


            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((int)v.getTag())==0){

                        if(child_List_2.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                            child_List_2.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).setIsChoosed(0);
                            holder.all_leaders.setSelected(false);
                        }else{
                            child_List_2.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).setIsChoosed(1);
                            holder.all_leaders.setSelected(true);
                        }

                        String isChoose = "true";
                        for (int i = 0; i < child_List_2.get(list_choose.get(position).getId_father()).size(); i++) {

                            if (child_List_2.get(list_choose.get(position).getId_father()).get(i).getIsChoosed() == 0) {
                                isChoose = "false";
                                break;
                            }
                        }

                        if (isChoose.equals("true")) {
                            group_List_2.get(list_choose.get(position).getId_father()).setIsChoosed(1);
                        } else {
                            group_List_2.get(list_choose.get(position).getId_father()).setIsChoosed(0);
                        }
                        myAdapter_2.refresh();


                    }



                }
            });
        }


        @Override
        public int getItemCount() {

            if(list_choose == null){
                return 0;
            }
            return list_choose.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            LinearLayout all_choose;
            ImageView all_leaders;
            TextView textView;
            TextView department;
            FrameLayout fragment;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                all_choose = (LinearLayout) itemView.findViewById(R.id.all_choose);
                all_leaders = (ImageView) itemView.findViewById(R.id.all_leaders);
                textView = (TextView) itemView.findViewById(R.id.textView);
                department = (TextView) itemView.findViewById(R.id.department);
                fragment = (FrameLayout) itemView.findViewById(R.id.fragment);
                line = itemView.findViewById(R.id.line);
            }
        }
    }

    //匹配搜索的关键字
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    class Search{

        private int id_father;
        private int id_children;


        public Search(int id_father, int id_children) {
            this.id_father = id_father;
            this.id_children = id_children;
        }


        public int getId_father() {
            return id_father;
        }

        public void setId_father(int id_father) {
            this.id_father = id_father;
        }

        public int getId_children() {
            return id_children;
        }

        public void setId_children(int id_children) {
            this.id_children = id_children;
        }
    }



}