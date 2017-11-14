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
import com.lanwei.governmentstar.adapter.MultiItemRecycleViewAdapter;
import com.lanwei.governmentstar.adapter.ViewHolderHelper;
import com.lanwei.governmentstar.adapter.VisitPollcyTwoAdapter;
import com.lanwei.governmentstar.bean.ChildList3;
import com.lanwei.governmentstar.bean.Data2;
import com.lanwei.governmentstar.bean.Expanable_Bean;
import com.lanwei.governmentstar.bean.Expanable_Bean2;
import com.lanwei.governmentstar.bean.Expanable_Bean_Child;
import com.lanwei.governmentstar.bean.Expanable_Bean_Child2;
import com.lanwei.governmentstar.bean.InCirculationTree2;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.PollcyFour;
import com.lanwei.governmentstar.bean.PollcyOne;
import com.lanwei.governmentstar.bean.PollcyThree;
import com.lanwei.governmentstar.bean.PollcyTwo;
import com.lanwei.governmentstar.bean.Root;
import com.lanwei.governmentstar.bean.Search_item;
import com.lanwei.governmentstar.bean.ThreeItemInfo;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.interfaces.MultiItemTypeSupport;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/28.
 */

public class ChooseReceivers_Activity extends BaseActivity  implements View.OnClickListener{


    private ExpandableListView listView;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Data2> group_List_xieban = new ArrayList<>();
    private List<ThreeItemInfo> mList;
    private List<ThreeItemInfo> mList_copy;
    private ThreeItemAdapter threeItemAdapter;
    private ArrayList<ArrayList<ChildList3>> child_List_xieban = new ArrayList<>();

    private RecyclerView recycler_view;
    private Adapter_Addtion3 adapter_addtion3;
    private Adapter_Addtion2 adapter_addtion2;
    private EditText search;
    private LinearLayout result_search;
    private TextView null_search;

    private GovernmentApi api;
    private Logging_Success bean;
    private Call<Root> call = null;

    private ImageView back;
    private TextView add;
    private ScrollView scrollView;

    private String names = "";
    private String opIds = "";

    // 前一个界面带过来的要恢复的协办人id集合
    private String opId_choosed_xieban = "";
    private String[] opXbrId_list;
    // 后台给的不能选的协办人id集合
    private ArrayList<String> xieban_Not_choosed_download =new ArrayList<>();
    private ArrayList<String> list_opXbrId;
    private ArrayList<Search> list_choose =new ArrayList<>();
    private ArrayList<Search_weijiwei> list_choose_weijiwei =new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_receviers_zhuanban);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        back = (ImageView) findViewById(R.id.back);
        add = (TextView) findViewById(R.id.add);
        back.setOnClickListener(this);
        add.setOnClickListener(this);

        String defString = PreferencesManager.getInstance(ChooseReceivers_Activity.this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean = gson.fromJson(defString, Logging_Success.class);

        initViews();
        initsearch();

        if(!getIntent().getStringExtra("opXbrId").equals("")){
            opId_choosed_xieban = getIntent().getStringExtra("opXbrId");
            Log.e("已选择人的ID 协办人ids", opId_choosed_xieban);
            opXbrId_list = opId_choosed_xieban.split(",");
            // 数组转为Arraylist
            list_opXbrId = new ArrayList<>(Arrays.asList(opXbrId_list));
        }

        api = HttpClient.getInstance().getGovernmentApi();

        call = api.swcyXbrTree(bean.getData().getOpId(),bean.getData().getAccountDeptId(),getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {

                if (response.body().getData() == null) {
//                    Toast.makeText(ChooseReceiverNBActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {

                    if(bean.getData().getAccountDeptId().equals("0155")){
                        initData(response.body());
                    }else{
                        getdata2(response.body());
                    }

                }

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

                Toast.makeText(ChooseReceivers_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void initsearch(){
        search.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                    list_choose.clear();
                    list_choose_weijiwei.clear();
                    result_search.setVisibility(View.VISIBLE);

                    if(!search.getText().toString().trim().equals("")){

                        if(bean.getData().getAccountDeptId().equals("0155")){

                            for (int i = 0; i < mList_copy.size(); i++) {

                                for (int j = 0; j < mList_copy.get(i).getResults().size(); j++) {

                                    for (int k = 0; k < mList_copy.get(i).getResults().get(j).getResults().size(); k++) {

                                        for (int l = 0; l < mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().size(); l++) {

                                            if(mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getContent().contains(search.getText().toString().trim())){
                                                list_choose_weijiwei.add(new Search_weijiwei(i,j,k,l));

                                                Log.e("namne",mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getContent());

                                            }
                                        }
                                    }
                                }
                            }
                            adapter_addtion3 = new Adapter_Addtion3();
                            recycler_view.setAdapter(adapter_addtion3);
                            recycler_view.setVisibility(View.VISIBLE);

                            if(list_choose_weijiwei.size()<1){
                                null_search.setVisibility(View.VISIBLE);
                                recycler_view.setVisibility(View.GONE);
                            }else{
                                null_search.setVisibility(View.GONE);
                                recycler_view.setVisibility(View.VISIBLE);
                            }


                        }else{

                            for(int i=0;i<group_List_xieban.size();i++){

                                for(int j=0;j<child_List_xieban.get(i).size();j++){

                                    if(child_List_xieban.get(i).get(j).getOpName().contains(search.getText().toString().trim())){

                                        list_choose.add(new Search(i,j));
                                    }
                                }
                            }
                            adapter_addtion2 =new Adapter_Addtion2();
                            recycler_view.setAdapter(adapter_addtion2);
                            recycler_view.setVisibility(View.VISIBLE);

                            if(list_choose.size()<1){
                                null_search.setVisibility(View.VISIBLE);
                                recycler_view.setVisibility(View.GONE);
                            }else{
                                null_search.setVisibility(View.GONE);
                                recycler_view.setVisibility(View.VISIBLE);
                            }

                        }

                    }else{
                        result_search.setVisibility(View.GONE);
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


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back:

                finish();
                break;

             case R.id.add:

                 if(bean.getData().getAccountDeptId().equals("0155")){

                     for (int i = 0; i < mList.size(); i++) {

                         for (int j = 0; j < mList.get(i).getResults().size(); j++) {

                             for (int k = 0; k < mList.get(i).getResults().get(j).getResults().size(); k++) {

                                 for (int l = 0; l < mList.get(i).getResults().get(j).getResults().get(k).getResults().size(); l++) {

                                     if(mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getIs_choosed()){
                                         opIds+=mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getOpId()+",";
                                         names+=mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getContent()+",";
                                     }
                                 }
                             }
                         }
                     }


                 }else{

                     for (int n = 0; n < group_List_xieban.size(); n++) {

                         for (int l = 0; l < child_List_xieban.get(n).size(); l++) {//子类

                             if (child_List_xieban.get(n).get(l).getIsChoosed()==1) {
                                    opIds+=child_List_xieban.get(n).get(l).getOpId()+",";
                                    names+=child_List_xieban.get(n).get(l).getOpName()+",";
                             }
                         }
                     }

                 }

                 if(!names.equals("")){

                     if(names.substring(names.length()-1,names.length()).equals(",")){
                         names=names.substring(0,names.length()-1);
                     }

                 }

                 if(!opIds.equals("")){

                     if(opIds.substring(opIds.length()-1,opIds.length()).equals(",")){
                         opIds=opIds.substring(0,opIds.length()-1);
                     }
                 }

                 Intent intent =new Intent();
                 intent.putExtra("names",names);
                 intent.putExtra("opXbrId",opIds);

                 Log.e("opXbrId",opIds);
                 Log.e("names",names);
                 setResult(10,intent);
                 finish();
                break;
        }
    }

    /**初始化*/
    private void initViews() {
        listView = (ExpandableListView) findViewById(R.id.listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        search = (EditText) findViewById(R.id.search);
        result_search = (LinearLayout) findViewById(R.id.result_search);
        null_search = (TextView) findViewById(R.id.null_search);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        // 为RecyclerView设置默认动画和线性布局管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        // 为RecyclerView设置默认动画和线性布局管理器
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        listView.setGroupIndicator(null);
        listView.setDivider(null);
    }
    /**加载数据*/
    public void initData(Root root) {

        mList = new ArrayList<>();
        mList_copy = new ArrayList<>();
        if(root.getAccIds() !=null){
            xieban_Not_choosed_download = root.getAccIds();
        }

        for (int i = 0; i < root.getData().size(); i++) {
            ThreeItemInfo threeItemInfo = new ThreeItemInfo();
            threeItemInfo.setStatus("1");
            threeItemInfo.setContent(root.getData().get(i).getOpName());
            threeItemInfo.setOpId(root.getData().get(i).getOpId());
            threeItemInfo.setIsExpading(true);

            List<ThreeItemInfo> itemInfos = new ArrayList<>();
            for (int j = 0; j < root.getData().get(i).getChildList().size(); j++) {
                ThreeItemInfo threeItemInfo_2 = new ThreeItemInfo();
                threeItemInfo_2.setStatus("2");
                threeItemInfo_2.setContent(root.getData().get(i).getChildList().get(j).getOpName());
                threeItemInfo_2.setOpId(root.getData().get(i).getChildList().get(j).getOpId());
                threeItemInfo_2.setIsExpading(true);
                itemInfos.add(threeItemInfo_2);


                List<ThreeItemInfo> itemInfos_list = new ArrayList<>();
                for (int z = 0; z < root.getData().get(i).getChildList().get(j).getChildList().size(); z++) {
                    ThreeItemInfo threeItemInfo_3 = new ThreeItemInfo();
                    threeItemInfo_3.setStatus("3");
                    threeItemInfo_3.setContent(root.getData().get(i).getChildList().get(j).getChildList().get(z).getOpName());
                    threeItemInfo_3.setOpId(root.getData().get(i).getChildList().get(j).getChildList().get(z).getOpId());
                    threeItemInfo_3.setIsExpading(true);
                    itemInfos_list.add(threeItemInfo_3);

                    List<ThreeItemInfo> itemInfos_list4 = new ArrayList<>();
                    for (int w = 0; w < root.getData().get(i).getChildList().get(j).getChildList().get(z).getChildList().size(); w++) {

                        ThreeItemInfo threeItemInfo_4 = new ThreeItemInfo();
                        threeItemInfo_4.setStatus("4");
                        threeItemInfo_4.setContent(root.getData().get(i).getChildList().get(j).getChildList().get(z).getChildList().get(w).getOpName());
                        threeItemInfo_4.setOpId(root.getData().get(i).getChildList().get(j).getChildList().get(z).getChildList().get(w).getOpId());
                        threeItemInfo_4.setAccountDpet(root.getData().get(i).getChildList().get(j).getChildList().get(z).getChildList().get(w).getAccountDpet());
                        threeItemInfo_4.setAccountRole(root.getData().get(i).getChildList().get(j).getChildList().get(z).getChildList().get(w).getAccountRole());
                        threeItemInfo_4.setIsExpading(true);
                        itemInfos_list4.add(threeItemInfo_4);
                    }
                    // 四级目录的添加
                    threeItemInfo_3.setResults(itemInfos_list4);

                }
                // 三级目录的添加
                threeItemInfo_2.setResults(itemInfos_list);

            }
            // 二级目录的添加
            threeItemInfo.setResults(itemInfos);
            // 添加一级目录
            mList.add(threeItemInfo);
            mList_copy.add(threeItemInfo);
        }
        if (!getIntent().getStringExtra("opXbrId").equals("")) {

            for (int n = 0; n < list_opXbrId.size(); n++) {

                for (int i = 0; i < mList.size(); i++) {

                    for (int j = 0; j < mList.get(i).getResults().size(); j++) {

                        for (int k = 0; k < mList.get(i).getResults().get(j).getResults().size(); k++) {

                            for (int l = 0; l < mList.get(i).getResults().get(j).getResults().get(k).getResults().size(); l++) {

                                if (list_opXbrId.get(n).equals(mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getOpId())) {
                                    mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).setIs_choosed(true);
                                    mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().get(l).setIs_choosed(true);
                                    Log.e("大幅度反对的是", "尔特热");
                                    break;
                                }
                            }
                        }
                    }
                }

            }
        }

        threeItemAdapter = new ThreeItemAdapter(this, mList);

        recyclerView.setAdapter(threeItemAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
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
            return group_List_xieban.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child_List_xieban.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group_List_xieban.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child_List_xieban.get(groupPosition).get(childPosition);
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
            textView.setText(group_List_xieban.get(groupPosition).getOpName());

            if (group_List_xieban.get(groupPosition).getIsTurn() == 0) {
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
            name.setText(child_List_xieban.get(groupPosition).get(childPosition).getOpName());
            name.setTextColor(getResources().getColor(R.color.color_23));
            if (child_List_xieban.get(groupPosition).get(childPosition).getIsChoosed() == 0) {
                isChoose.setSelected(false);
            } else {
                isChoose.setSelected(true);
            }

            all_layoutc.setTag(0);
            for (int i = 0; i < xieban_Not_choosed_download.size(); i++) {
                if (xieban_Not_choosed_download.get(i).equals(child_List_xieban.get(groupPosition).get(childPosition).getOpId())) {
                    all_layoutc.setTag(1);
                    isChoose.setVisibility(View.INVISIBLE);
                    name.setTextColor(getResources().getColor(R.color.color_aaa));
                    name.setText(child_List_xieban.get(groupPosition).get(childPosition).getOpName()+"(不可选择)");
                    break;
                }
            }
            all_layoutc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (((int) v.getTag()) == 0) {

                        //添加到集合里以后，判断该条目的状态   并设置该条目的状态
                        if (child_List_xieban.get(groupPosition).get(childPosition).getIsChoosed() == 1) {
                            child_List_xieban.get(groupPosition).get(childPosition).setIsChoosed(0);
                        } else {
                            child_List_xieban.get(groupPosition).get(childPosition).setIsChoosed(1);
                        }

                        myAdapter.refresh();
                        if(adapter_addtion2 !=null){
                            adapter_addtion2.notifyDataSetChanged();
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


    private void getdata2(Root root) {

        for (int i = 0; i < root.getData().size(); i++) {
            group_List_xieban.add(new Data2(root.getData().get(i).getChildList(),0,root.getData().get(i).getOpId(),0,root.getData().get(i).getOpName()));
            ArrayList<ChildList3> list = new ArrayList<>();

            for (int j = 0; j < root.getData().get(i).getChildList().size(); j++) {//子类
                list.add(new ChildList3(root.getData().get(i).getChildList().get(j).getChildList(), root.getData().get(i).getChildList().get(j).getOpId(),root.getData().get(i).getChildList().get(j).getOpName(),0,0));
            }
            child_List_xieban.add(list);
        }
        if(root.getAccIds() !=null){
            xieban_Not_choosed_download = root.getAccIds();
        }

        // 之前已进入此界面选择了联系人，返回到处理界面后又跳回来，需要恢复显示之前选中的人，这些已选中的数据由前一个界面记录，并返回过来，并在此匹配一下并选中这个人
        if (!getIntent().getStringExtra("opXbrId").equals("")) {

            for (int m = 0; m < list_opXbrId.size(); m++) {

                for (int n = 0; n < group_List_xieban.size(); n++) {

                    for (int l = 0; l < child_List_xieban.get(n).size(); l++) {//子类

                        if (child_List_xieban.get(n).get(l).getOpId().equals(list_opXbrId.get(m))) {

                            child_List_xieban.get(n).get(l).setIsChoosed(1);

                            break;
                        }
                    }
                }
            }
        }

        // 恢复group_list的状态
        for (int n = 0; n < group_List_xieban.size(); n++) {

            for (int l = 0; l < child_List_xieban.get(n).size(); l++) {

                if (child_List_xieban.get(n).get(l).getIsChoosed() == 0) {

                    break;

                } else {
                    if (l == child_List_xieban.get(n).size() - 1) {
                        group_List_xieban.get(n).setIsChoosed(1);
                    }
                }
            }
        }
        recyclerView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        myAdapter =new MyAdapter();
        listView.setAdapter(myAdapter);
        // 检测点击，展开或收缩group，改变相应控件
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (group_List_xieban.get(groupPosition).getIsTurn() == 0) {
                    group_List_xieban.get(groupPosition).setIsTurn(1);
                    v.findViewById(R.id.shiftLeaders).setVisibility(View.GONE);
                    v.findViewById(R.id.shiftLeaders_down).setVisibility(View.VISIBLE);

                } else {
                    group_List_xieban.get(groupPosition).setIsTurn(0);
                    v.findViewById(R.id.shiftLeaders).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.shiftLeaders_down).setVisibility(View.GONE);

                }

                return false;
            }
        });
    }


    public class ThreeItemAdapter extends MultiItemRecycleViewAdapter<ThreeItemInfo> {
        private static final int TYPE_ONE = 0;
        private static final int TYPE_TWO = 1;
        private static final int TYPE_THREE = 2;
        private static final int TYPE_four = 3;

        public ThreeItemAdapter(Context context, List<ThreeItemInfo> datas) {
            super(context, datas, new MultiItemTypeSupport<ThreeItemInfo>() {
                @Override
                public int getLayoutId(int itemType) {
                    int i = 0;
                    switch (itemType) {
                        case TYPE_ONE:
                            i = R.layout.item_one_threeitem;
                            break;
                        case TYPE_TWO:
                            i = R.layout.item_two_threeitem;
                            break;
                        case TYPE_THREE:
                            i = R.layout.item_three_threeitem;
                            break;
                        case TYPE_four:
                            i = R.layout.item_four_threeitem;
                            break;
                    }
                    return i;
                }

                @Override
                public int getItemViewType(int position, ThreeItemInfo threeItemInfo) {
                    String currentCity = threeItemInfo.getStatus();
                    int i = 0;
                    switch (currentCity) {
                        case "1":
                            i = TYPE_ONE;
                            break;
                        case "2":
                            i = TYPE_TWO;
                            break;
                        case "3":
                            i = TYPE_THREE;
                            break;
                        case "4":
                            i = TYPE_four;
                            break;

                    }
                    return i;
                }
            });
        }

        @Override
        public void convert(ViewHolderHelper helper, ThreeItemInfo threeItemInfo, int position) {
            switch (helper.getLayoutId()) {
                case R.layout.item_one_threeitem:
                    bindViewOne(helper, threeItemInfo, position);
                    break;
                case R.layout.item_two_threeitem:
                    bindViewTwo(helper, threeItemInfo, position);
                    break;
                case R.layout.item_three_threeitem:
                    bindViewThree(helper, threeItemInfo, position);
                    break;
                case R.layout.item_four_threeitem:
                    bindViewFour(helper, threeItemInfo, position);
                    break;
            }
        }

        private void bindViewOne(ViewHolderHelper helper, final ThreeItemInfo threeItemInfo, final int position) {
            TextView tv_1 = helper.getView(R.id.tv_1);
            LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
            final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
            final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);

            tv_1.setText(threeItemInfo.getContent());

            if(threeItemInfo.getIsExpading()){
                shift_leaders.setVisibility(View.VISIBLE);
                shift_leaders_down.setVisibility(View.INVISIBLE);
            }else{
                shift_leaders.setVisibility(View.INVISIBLE);
                shift_leaders_down.setVisibility(View.VISIBLE);
            }

            extend_leaders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (threeItemInfo.getResults() != null && threeItemInfo.getResults().size() != 0 && threeItemInfo.getIsExpading()) {

                        shift_leaders.setVisibility(View.INVISIBLE);
                        shift_leaders_down.setVisibility(View.VISIBLE);
                        Log.e("点击后展开","点击后展开");

                        threeItemInfo.setIsExpading(false);
                        addAllAt(position + 1, threeItemInfo.getResults());


                    } else if (threeItemInfo.getResults() != null & threeItemInfo.getResults().size() != 0 && !threeItemInfo.getIsExpading()) {
                        Log.e("点击后收缩","点击后收缩");
                        shift_leaders.setVisibility(View.VISIBLE);
                        shift_leaders_down.setVisibility(View.INVISIBLE);


                        for (ThreeItemInfo item : threeItemInfo.getResults()) {
                            if (!item.getIsExpading()) {
                                removeAll(item.getResults());
                            }

                            for(ThreeItemInfo item2 : item.getResults()){

                                if (!item2.getIsExpading()) {
                                    removeAll(item2.getResults());
                                }
                            }
                        }
                        threeItemInfo.setIsExpading(true);

                        for (int i=0;i<threeItemInfo.getResults().size();i++){

                            threeItemInfo.getResults().get(i).setIsExpading(true);

                            for(int j=0; j<threeItemInfo.getResults().get(i).getResults().size();j++){

                                threeItemInfo.getResults().get(i).getResults().get(j).setIsExpading(true);

                            }
                        }
                        removeAll(threeItemInfo.getResults());

                    }
                }
            });

        }

        private void bindViewTwo(ViewHolderHelper helper, final ThreeItemInfo threeItemInfo, final int position) {
            TextView tv_2 = helper.getView(R.id.tv_2);
            LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
            final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
            final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);
            tv_2.setText(threeItemInfo.getContent());

            if(threeItemInfo.getIsExpading()){
                shift_leaders.setVisibility(View.VISIBLE);
                shift_leaders_down.setVisibility(View.INVISIBLE);
            }else{
                shift_leaders.setVisibility(View.INVISIBLE);
                shift_leaders_down.setVisibility(View.VISIBLE);
            }


            extend_leaders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (threeItemInfo.getResults() != null && threeItemInfo.getResults().size() != 0 && threeItemInfo.getIsExpading()) {
                        shift_leaders.setVisibility(View.INVISIBLE);
                        shift_leaders_down.setVisibility(View.VISIBLE);
                        Log.e("点击后展开","点击后展开");

                        threeItemInfo.setIsExpading(false);
                        addAllAt(position + 1, threeItemInfo.getResults());

                    } else if (threeItemInfo.getResults() != null & threeItemInfo.getResults().size() != 0 && !threeItemInfo.getIsExpading()) {
                        Log.e("点击后收缩","点击后收缩");
                        shift_leaders.setVisibility(View.VISIBLE);
                        shift_leaders_down.setVisibility(View.INVISIBLE);


                        for (ThreeItemInfo item : threeItemInfo.getResults()) {
                            if (!item.getIsExpading()) {
                                removeAll(item.getResults());
                            }
                        }

                        threeItemInfo.setIsExpading(true);
                        for (int i=0;i<threeItemInfo.getResults().size();i++){

                            threeItemInfo.getResults().get(i).setIsExpading(true);

                        }
                        removeAll(threeItemInfo.getResults());

                    }
                }
            });
        }

        private void bindViewThree(ViewHolderHelper helper,final ThreeItemInfo threeItemInfo, final int position) {
            TextView tv_3 = helper.getView(R.id.tv_3);
            LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
            final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
            final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);

            if(threeItemInfo.getIsExpading()){
                shift_leaders.setVisibility(View.VISIBLE);
                shift_leaders_down.setVisibility(View.INVISIBLE);
            }else{
                shift_leaders.setVisibility(View.INVISIBLE);
                shift_leaders_down.setVisibility(View.VISIBLE);
            }


            tv_3.setText(threeItemInfo.getContent());
            extend_leaders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (threeItemInfo.getResults() != null && threeItemInfo.getResults().size() != 0 && threeItemInfo.getIsExpading()) {

                        shift_leaders.setVisibility(View.INVISIBLE);
                        shift_leaders_down.setVisibility(View.VISIBLE);


                        Log.e("点击后展开","点击后展开");
                        threeItemInfo.setIsExpading(false);
                        addAllAt(position + 1, threeItemInfo.getResults());


                    } else if (threeItemInfo.getResults() != null & threeItemInfo.getResults().size() != 0 && !threeItemInfo.getIsExpading()) {

                        shift_leaders.setVisibility(View.VISIBLE);
                        shift_leaders_down.setVisibility(View.INVISIBLE);

                        Log.e("点击后收缩","点击后收缩");

                        threeItemInfo.setIsExpading(true);
                        removeAll(threeItemInfo.getResults());

                    }
                }
            });
        }

        private void bindViewFour(ViewHolderHelper helper, final ThreeItemInfo threeItemInfo, final int position) {
            TextView tv_4 = helper.getView(R.id.tv_4);
            LinearLayout linearLayout = helper.getView(R.id.linearLayout);
            final ImageView isChoosed=helper.getView(R.id.isChoosed);
            tv_4.setText(threeItemInfo.getContent());

            if(threeItemInfo.getIs_choosed()){
                isChoosed.setSelected(true);
            }else{
                isChoosed.setSelected(false);
            }
            linearLayout.setTag(0);

            for(int i=0;i<xieban_Not_choosed_download.size();i++){
                if(xieban_Not_choosed_download.get(i).equals(threeItemInfo.getOpId())){
                    linearLayout.setTag(1);
                    tv_4.setTextColor(getResources().getColor(R.color.color_aaa));
                    isChoosed.setVisibility(View.INVISIBLE);
//                    linearLayout.setBackgroundColor(getResources().getColor(R.color.color_e5));
                }
            }


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((int)v.getTag())==0){
                        if(threeItemInfo.getIs_choosed()){

                            isChoosed.setSelected(false);
                            threeItemInfo.setIs_choosed(false);

                            for (int i = 0; i < mList_copy.size(); i++){
                                for(int j = 0; j < mList_copy.get(i).getResults().size(); j++){
                                    for(int k = 0; k < mList_copy.get(i).getResults().get(j).getResults().size(); k++){
                                        for(int l = 0; l < mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().size(); l++){
                                            if(mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getOpId().equals(threeItemInfo.getOpId())){
                                                mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().get(l).setIs_choosed(false);
                                            }
                                        }
                                    }
                                }
                            }

                        }else{
                            isChoosed.setSelected(true);
                            threeItemInfo.setIs_choosed(true);


                            for (int i = 0; i < mList_copy.size(); i++){
                                for(int j = 0; j < mList_copy.get(i).getResults().size(); j++){
                                    for(int k = 0; k < mList_copy.get(i).getResults().get(j).getResults().size(); k++){
                                        for(int l = 0; l < mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().size(); l++){
                                            if(mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getOpId().equals(threeItemInfo.getOpId())){
                                                mList_copy.get(i).getResults().get(j).getResults().get(k).getResults().get(l).setIs_choosed(true);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        if (onItemThreeClickListener != null) {
                            onItemThreeClickListener.ThreeOnclickListener(position);
                        }

                        if(adapter_addtion3 != null){
                            adapter_addtion3.notifyDataSetChanged();
                        }

                    }

                }
            });
        }


        private com.lanwei.governmentstar.adapter.ThreeItemAdapter.OnItemThreeClickListener onItemThreeClickListener;

        public void setOnItemThreeClickListener(com.lanwei.governmentstar.adapter.ThreeItemAdapter.OnItemThreeClickListener onItemThreeClickListener) {
            this.onItemThreeClickListener = onItemThreeClickListener;
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

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), child_List_xieban.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getOpName(), search.getText().toString().trim());
            holder.textView.setText(spannableString);
            holder.department.setText("("+group_List_xieban.get(list_choose.get(position).getId_father()).getOpName()+")");
            holder.department.setVisibility(View.VISIBLE);
            holder.department.setTextColor(getResources().getColor(R.color.color_23));
            holder.fragment.setVisibility(View.GONE);
            holder.all_leaders.setVisibility(View.VISIBLE);

            if(child_List_xieban.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                holder.all_leaders.setSelected(true);
            }else{
                holder.all_leaders.setSelected(false);
            }

            holder.itemView.setTag(0);

            for(int i=0;i<xieban_Not_choosed_download.size();i++){
                if(xieban_Not_choosed_download.get(i).equals(child_List_xieban.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getOpId())){
                    holder.itemView.setTag(1);
//                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.color_e5));
                    holder.textView.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.department.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.all_leaders.setVisibility(View.INVISIBLE);
                    break;
                }
            }


            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((int)v.getTag())==0){

                        if(child_List_xieban.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                            child_List_xieban.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).setIsChoosed(0);
                            holder.all_leaders.setSelected(false);
                        }else{
                            child_List_xieban.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).setIsChoosed(1);
                            holder.all_leaders.setSelected(true);
                        }

                        String isChoose = "true";
                        for (int i = 0; i < child_List_xieban.get(list_choose.get(position).getId_father()).size(); i++) {

                            if (child_List_xieban.get(list_choose.get(position).getId_father()).get(i).getIsChoosed() == 0) {
                                isChoose = "false";
                                break;
                            }
                        }

                        if (isChoose.equals("true")) {
                            group_List_xieban.get(list_choose.get(position).getId_father()).setIsChoosed(1);
                        } else {
                            group_List_xieban.get(list_choose.get(position).getId_father()).setIsChoosed(0);
                        }

                        if(myAdapter!= null){
                            myAdapter.refresh();
                        }
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
    class Adapter_Addtion3 extends RecyclerView.Adapter<Adapter_Addtion3.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion3() {

        }

        @Override
        public Adapter_Addtion3.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_out_ps, parent, false);

            return new Adapter_Addtion3.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion3.MyViewHolder holder, final int position) {

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).getContent(), search.getText().toString().trim());
            holder.textView.setText(spannableString);
            holder.department.setText("("+mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getContent()+
                    "-"+mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getContent()+ ")");
            holder.department.setVisibility(View.VISIBLE);
            holder.fragment.setVisibility(View.GONE);


            if(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).getIs_choosed()){
                holder.all_leaders.setSelected(true);
            }else{
                holder.all_leaders.setSelected(false);
            }

            holder.itemView.setTag(0);
            for(int i=0;i<xieban_Not_choosed_download.size();i++){
                if(xieban_Not_choosed_download.get(i).equals(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).getOpId())){
//                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.color_e5));
                    holder.textView.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.department.setTextColor(getResources().getColor(R.color.color_aaa));
                    holder.all_leaders.setVisibility(View.INVISIBLE);
                    holder.itemView.setTag(1);
                    break;
                }
            }

            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((int)v.getTag())==0){

                        if(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).getIs_choosed()){
                            mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).setIs_choosed(false);
                            holder.all_leaders.setSelected(false);

                            for (int i = 0; i < mList.size(); i++){
                                for(int j = 0; j < mList.get(i).getResults().size(); j++){
                                    for(int k = 0; k < mList.get(i).getResults().get(j).getResults().size(); k++){
                                        for(int l = 0; l < mList.get(i).getResults().get(j).getResults().get(k).getResults().size(); l++){
                                            if(mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getOpId().equals(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).getOpId())){
                                                mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).setIs_choosed(false);
                                            }
                                        }
                                    }
                                }
                            }


                        }else{
                            mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).setIs_choosed(true);
                            holder.all_leaders.setSelected(true);


                            for (int i = 0; i < mList.size(); i++){
                                for(int j = 0; j < mList.get(i).getResults().size(); j++){
                                    for(int k = 0; k < mList.get(i).getResults().get(j).getResults().size(); k++){
                                        for(int l = 0; l < mList.get(i).getResults().get(j).getResults().get(k).getResults().size(); l++){
                                            if(mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).getOpId().equals(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getResults().get(list_choose_weijiwei.get(position).getId_two()).getResults().get(list_choose_weijiwei.get(position).getId_three()).getResults().get(list_choose_weijiwei.get(position).getId_four()).getOpId())){
                                                mList.get(i).getResults().get(j).getResults().get(k).getResults().get(l).setIs_choosed(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        threeItemAdapter.notifyDataSetChanged();

                    }
                }
            });
        }


        @Override
        public int getItemCount() {

            if(list_choose_weijiwei == null){
                return 0;
            }
            return list_choose_weijiwei.size();
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

    class Search_weijiwei{

        private int id_one;
        private int id_two;
        private int id_three;
        private int id_four;

        public Search_weijiwei(int id_one, int id_two, int id_three, int id_four) {
            this.id_one = id_one;
            this.id_two = id_two;
            this.id_three = id_three;
            this.id_four = id_four;
        }

        public int getId_one() {
            return id_one;
        }

        public void setId_one(int id_one) {
            this.id_one = id_one;
        }

        public int getId_two() {
            return id_two;
        }

        public void setId_two(int id_two) {
            this.id_two = id_two;
        }

        public int getId_three() {
            return id_three;
        }

        public void setId_three(int id_three) {
            this.id_three = id_three;
        }

        public int getId_four() {
            return id_four;
        }

        public void setId_four(int id_four) {
            this.id_four = id_four;
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


}
