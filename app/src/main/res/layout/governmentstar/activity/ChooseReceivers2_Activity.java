package com.lanwei.governmentstar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import com.lanwei.governmentstar.adapter.ThreeItemAdapter;
import com.lanwei.governmentstar.adapter.ViewHolderHelper;
import com.lanwei.governmentstar.bean.Expanable_Bean2;
import com.lanwei.governmentstar.bean.Expanable_Bean_Child2;
import com.lanwei.governmentstar.bean.InCirculationTree2;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Weijiwei;
import com.lanwei.governmentstar.bean.Return_Weijiweif;
import com.lanwei.governmentstar.bean.Root;
import com.lanwei.governmentstar.bean.ThreeItemInfo;
import com.lanwei.governmentstar.bean.ThreeItemInfo2;
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

public class ChooseReceivers2_Activity extends BaseActivity implements View.OnClickListener{

    private RecyclerView recyclerview ;
    private RecyclerView recyclerView ;
    private List<ThreeItemInfo2> mList;
    private List<ThreeItemInfo2> mList_copy;
    private Logging_Success bean;
    private ImageView back;
    private TextView add;
    private GovernmentApi api;
    private LinearLayout result_search;
    private ExpandableListView expandablelistview1;
    private List<Expanable_Bean2> group_List = new ArrayList<>();
    private List<List<Expanable_Bean_Child2>> child_List = new ArrayList<>();
    private String opId_choosed = "";
    private MyAdapter myAdapter;
    private String names = "";
    private String opIds = "";
    private EditText search;
    private TextView null_search;
    private ArrayList<Search> list_choose =new ArrayList<>();
    private ArrayList<Search_weijiwei> list_choose_weijiwei =new ArrayList<>();
    private Adapter_Addtion adapter_addtion;
    private Adapter_Addtion3 adapter_addtion3;
    private ScrollView scrollView;
    private ThreeItemAdapter threeItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_receviers3);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        back = (ImageView) findViewById(R.id.back);
        add = (TextView) findViewById(R.id.add);

        back.setOnClickListener(this);
        add.setOnClickListener(this);

        String defString = PreferencesManager.getInstance(ChooseReceivers2_Activity.this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean = gson.fromJson(defString, Logging_Success.class);
        api = HttpClient.getInstance().getGovernmentApi();

        initViews();
        initsearch();

        if(bean.getData().getAccountDeptId().equals("0155")){

            Call<Return_Weijiweif> call=api.receivers_notification(bean.getData().getAccountDeptId());

            call.enqueue(new Callback<Return_Weijiweif>() {
                @Override
                public void onResponse(Call<Return_Weijiweif> call, Response<Return_Weijiweif> response) {

//               Log.e("modules",response.body().getData().get(0).getChildList().get(0).getChildList().get(0).getChildList().get(0).getAccountDpet());
                    initData(response.body());
                    Log.e("response.body().size",response.body().getData().size()+"  ");

                }

                @Override
                public void onFailure(Call<Return_Weijiweif> call, Throwable t) {
                    Toast.makeText(ChooseReceivers2_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

                }
            });

        }else{

            Call<InCirculationTree2> call=api.receivers_notification2(bean.getData().getAccountDeptId());

            call.enqueue(new Callback<InCirculationTree2>() {
                @Override
                public void onResponse(Call<InCirculationTree2> call, Response<InCirculationTree2> response) {

                    Log.e("response", response.body().toString());
                    if (response.body() == null) {
                        Toast.makeText(ChooseReceivers2_Activity.this, "", Toast.LENGTH_SHORT).show();
                    } else {
                        // 实体类含义，第一个int是item的是否选择的标志位，第二个int是group是否展开的标志位
                        getdata(response.body());
                    }
                }

                @Override
                public void onFailure(Call<InCirculationTree2> call, Throwable t) {
                    Toast.makeText(ChooseReceivers2_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

                }
            });


        }


    }

    void initsearch(){
        search.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                    list_choose.clear();
                    result_search.setVisibility(View.VISIBLE);
                    list_choose_weijiwei.clear();

                    if(!search.getText().toString().trim().equals("")){
                        if(!bean.getData().getAccountDeptId().equals("0155")){

                            list_choose.clear();
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

                        }else if(bean.getData().getAccountDeptId().equals("0155")){

                            list_choose_weijiwei.clear();
                                for (int i = 0; i < mList_copy.size(); i++) {
                                    for (int j = 0; j < mList_copy.get(i).getChildList().size(); j++) {
                                        for (int k = 0; k < mList_copy.get(i).getChildList().get(j).getChildList().size(); k++) {
                                            for (int l = 0; l < mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++) {
                                                if(mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpName().contains(search.getText().toString().trim())){
                                                    list_choose_weijiwei.add(new Search_weijiwei(i,j,k,l));
                                                }
                                            }
                                        }
                                    }
                                }
                                adapter_addtion3 = new Adapter_Addtion3();
                                recyclerView.setAdapter(adapter_addtion3);
                                recyclerView.setVisibility(View.VISIBLE);

                                if(list_choose_weijiwei.size()<1){
                                    null_search.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }else{
                                    null_search.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }

                        }
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        null_search.setVisibility(View.VISIBLE);
                        list_choose.clear();
                        list_choose_weijiwei.clear();
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

            Log.e("getId_one",list_choose_weijiwei.get(position).getId_one()+"  "+list_choose_weijiwei.get(position).getId_two()+"  "+list_choose_weijiwei.get(position).getId_three()+"  "+list_choose_weijiwei.get(position).getId_four()+"  ");

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getChildList().get(list_choose_weijiwei.get(position).getId_four()).getOpName(), search.getText().toString().trim());
            holder.textView.setText(spannableString);
            holder.department.setText("("+mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getOpName()+
                    "-"+mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getOpName()+ ")");
            holder.department.setVisibility(View.VISIBLE);
            holder.fragment.setVisibility(View.GONE);


            if(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getChildList().get(list_choose_weijiwei.get(position).getId_four()).getIs_choosed()){
                holder.all_leaders.setSelected(true);
            }else{
                holder.all_leaders.setSelected(false);
            }

            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getChildList().get(list_choose_weijiwei.get(position).getId_four()).getIs_choosed()){
                            mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getChildList().get(list_choose_weijiwei.get(position).getId_four()).setIs_choosed(false);
                            holder.all_leaders.setSelected(false);

                            for (int i = 0; i < mList.size(); i++){
                                for(int j = 0; j < mList.get(i).getChildList().size(); j++){
                                    for(int k = 0; k < mList.get(i).getChildList().get(j).getChildList().size(); k++){
                                        for(int l = 0; l < mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++){
                                            if(mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpId().equals(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getChildList().get(list_choose_weijiwei.get(position).getId_four()).getOpId())){
                                                mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setIs_choosed(false);
                                            }
                                        }
                                    }
                                }
                            }



                        }else{
                            mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getChildList().get(list_choose_weijiwei.get(position).getId_four()).setIs_choosed(true);
                            holder.all_leaders.setSelected(true);


                            for (int i = 0; i < mList.size(); i++){
                                for(int j = 0; j < mList.get(i).getChildList().size(); j++){
                                    for(int k = 0; k < mList.get(i).getChildList().get(j).getChildList().size(); k++){
                                        for(int l = 0; l < mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++){
                                            if(mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpId().equals(mList_copy.get(list_choose_weijiwei.get(position).getId_one()).getChildList().get(list_choose_weijiwei.get(position).getId_two()).getChildList().get(list_choose_weijiwei.get(position).getId_three()).getChildList().get(list_choose_weijiwei.get(position).getId_four()).getOpId())){
                                                mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setIs_choosed(true);
                                            }
                                        }
                                    }
                                }
                            }


                        }

                        if(threeItemAdapter !=null){
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
            holder.department.setTextColor(getResources().getColor(R.color.color_23));
            holder.department.setVisibility(View.VISIBLE);
            holder.fragment.setVisibility(View.GONE);
            holder.all_leaders.setVisibility(View.VISIBLE);

            if(child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                holder.all_leaders.setSelected(true);
            }else{
                holder.all_leaders.setSelected(false);
            }


            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                        if(myAdapter != null){
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



    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.back:

                finish();
                break;

            case R.id.add:

                if(bean.getData().getAccountDeptId().equals("0155")){

                    for(int i=0;i < mList.size();i++){

                        for(int j = 0; j < mList.get(i).getChildList().size(); j++){

                            for(int k = 0; k < mList.get(i).getChildList().get(j).getChildList().size(); k++){

                                for (int l = 0; l < mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++) {

                                    if(mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getIs_choosed()){

                                        names+=mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpName()+",";
                                        opIds+=mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpId()+",";

                                    }
                                }
                            }
                        }
                    }


                }else{

                    for (int n = 0; n < group_List.size(); n++) {

                        for (int l = 0; l < child_List.get(n).size(); l++) {//子类

                            if (child_List.get(n).get(l).getIsChoosed()==1) {
                                opIds+=child_List.get(n).get(l).getContent().getOpId()+",";
                                names+=child_List.get(n).get(l).getContent().getOpName()+",";
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
                intent.putExtra("opIds",opIds);
                setResult(9,intent);
                finish();

                break;


        }


    }

    /**初始化*/
    private void initViews() {
        expandablelistview1 = (ExpandableListView) findViewById(R.id.expandablelistview1);
        expandablelistview1.setGroupIndicator(null);
        expandablelistview1.setDivider(null);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        result_search = (LinearLayout) findViewById(R.id.result_search);
        search = (EditText) findViewById(R.id.search);
        null_search = (TextView) findViewById(R.id.null_search);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        result_search.setVisibility(View.GONE);


    }

    public void initData(Return_Weijiweif root){

        if (!getIntent().getStringExtra("opIds").equals("")) {
            opId_choosed = getIntent().getStringExtra("opIds");
        }

        Log.e("前一个界面带过来的已选择人的ID", opId_choosed);
        String[] opid_list = opId_choosed.split(",");
        // 数组转为Arraylist
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));

        mList = root.getData();
        // 数据备份，搜索时用这份数据，注意实时同步mList与mList_copy的勾选状态,不能直接跟mList一样赋值，要不然指的时同一份数据，要重新组装数据
        mList_copy = new ArrayList<>();

        // 初始化mLis同时，给mList_copy赋值
        for (int i = 0; i < mList.size(); i++){
            mList.get(i).setStatus("1");
            mList.get(i).setIsExpading(true);
            ThreeItemInfo2 threeItemInfo_1 =new ThreeItemInfo2();
            threeItemInfo_1.setOpId(mList.get(i).getOpId());
            threeItemInfo_1.setOpName(mList.get(i).getOpName());

            ArrayList<ThreeItemInfo2> threeItemInfo_list1 =new ArrayList<>();
            threeItemInfo_1.setChildList(threeItemInfo_list1);

            for(int j = 0; j < mList.get(i).getChildList().size(); j++){

                mList.get(i).getChildList().get(j).setStatus("2");
                mList.get(i).getChildList().get(j).setIsExpading(true);
                ThreeItemInfo2 threeItemInfo_2 =new ThreeItemInfo2();
                threeItemInfo_2.setOpId(mList.get(i).getChildList().get(j).getOpId());
                threeItemInfo_2.setOpName(mList.get(i).getChildList().get(j).getOpName());

                ArrayList<ThreeItemInfo2> threeItemInfo_list2 =new ArrayList<>();
                threeItemInfo_2.setChildList(threeItemInfo_list2);

                for(int k = 0; k < mList.get(i).getChildList().get(j).getChildList().size(); k++){

                    mList.get(i).getChildList().get(j).getChildList().get(k).setStatus("3");
                    mList.get(i).getChildList().get(j).getChildList().get(k).setIsExpading(true);
                    ThreeItemInfo2 threeItemInfo_3 =new ThreeItemInfo2();
                    threeItemInfo_3.setOpId(mList.get(i).getChildList().get(j).getChildList().get(k).getOpId());
                    threeItemInfo_3.setOpName(mList.get(i).getChildList().get(j).getChildList().get(k).getOpName());

                    ArrayList<ThreeItemInfo2> threeItemInfo_list3 =new ArrayList<>();
                    threeItemInfo_3.setChildList(threeItemInfo_list3);

                    for(int l = 0; l < mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++){

                        mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setStatus("4");
                        mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setIsExpading(true);
                        ThreeItemInfo2 threeItemInfo_4 =new ThreeItemInfo2();
                        threeItemInfo_4.setOpId(mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpId());
                        threeItemInfo_4.setOpName(mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpName());
                        threeItemInfo_list3.add(threeItemInfo_4);
                    }
                    threeItemInfo_list2.add(threeItemInfo_3);

                }
                threeItemInfo_list1.add(threeItemInfo_2);
            }
            mList_copy.add(threeItemInfo_1);
        }



        Log.e("mList.size()",mList.size()+"尔特热");

        if (!getIntent().getStringExtra("opIds").equals("")){

            for(int n=0;n<list2.size();n++){

                for(int i=0;i < mList.size();i++){
                    for(int j = 0; j < mList.get(i).getChildList().size(); j++){
                        for(int k = 0; k < mList.get(i).getChildList().get(j).getChildList().size(); k++){
                            for (int l = 0; l < mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++) {
                                if(list2.get(n).equals(mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpId())){
                                    mList.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setIs_choosed(true);
                                    mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setIs_choosed(true);
                                }
                            }
                        }
                    }
                }
            }
        }

        threeItemAdapter = new ThreeItemAdapter(this, mList);

        recyclerview.setVisibility(View.VISIBLE);
        recyclerview.setAdapter(threeItemAdapter);

    }

    private void getdata(InCirculationTree2 inCirculationTree) {

        for (int i = 0; i < inCirculationTree.getDeptList().size(); i++) {
            group_List.add(new Expanable_Bean2(0, inCirculationTree.getDeptList().get(i), 0));
            ArrayList<Expanable_Bean_Child2> list = new ArrayList<>();
            for (int j = 0; j < inCirculationTree.getDeptList().get(i).getChildList().size(); j++) {//子类
                list.add(new Expanable_Bean_Child2(0, inCirculationTree.getDeptList().get(i).getChildList().get(j), 0));
            }
            child_List.add(list);
        }


        // 之前已进入此界面选择了联系人，返回到处理界面后又跳回来，需要恢复显示之前选中的人，这些已选中的数据由前一个界面记录，并返回过来，并在此匹配一下并选中这个人

        if (!getIntent().getStringExtra("opIds").equals("")) {
            opId_choosed = getIntent().getStringExtra("opIds");
        }
        if (!getIntent().getStringExtra("opBsrId").equals("")) {
            opId_choosed = opId_choosed + "," + getIntent().getStringExtra("opBsrId");
        }

        Log.e("前一个界面带过来的已选择人的ID", opId_choosed);
        String[] opid_list = opId_choosed.split(",");
        // 数组转为Arraylist
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));
        Log.e("前一个界面带过来的已选择人的ID", list2.size()+"");
        for (int m = 0; m < list2.size(); m++) {

            for (int n = 0; n < group_List.size(); n++) {

                for (int l = 0; l < child_List.get(n).size(); l++) {//子类

                    if (child_List.get(n).get(l).getContent().getOpId().equals(list2.get(m))) {

                        child_List.get(n).get(l).setIsChoosed(1);
                        break;
                    }
                }
            }
        }


        // 恢复group_list的状态
        int count = 0;
        for (int n = 0; n < group_List.size(); n++) {

            for (int l = 0; l < child_List.get(n).size(); l++) {

                if (child_List.get(n).get(l).getIsChoosed() == 0) {

                    break;

                } else {
                    if (l == child_List.get(n).size() - 1) {
                        group_List.get(n).setIsChoosed(1);
                    }
                }

            }
        }

        myAdapter = new MyAdapter(group_List, child_List);
        expandablelistview1.setAdapter(myAdapter);
        expandablelistview1.setVisibility(View.VISIBLE);

    }


    class MyAdapter extends BaseExpandableListAdapter {

        //        private List<Expanable_Bean2> groupList;//外层的数据源
//        private List<List<Expanable_Bean_Child2>> childList;//里层的数据源
        private Handler handler;

        public MyAdapter(List<Expanable_Bean2> groupList, List<List<Expanable_Bean_Child2>> childList) {
//            this.groupList = groupList;
//            this.childList = childList;

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


            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_out_ps2, parent, false);
            }
            // 全选选项
            final ImageView all_leaders = (ImageView) convertView.findViewById(R.id.all_leaders);
            final ImageView shift_leaders = (ImageView) convertView.findViewById(R.id.shiftLeaders);
            final ImageView shift_leaders_down = (ImageView) convertView.findViewById(R.id.shift_leaders_down);
            final TextView textView = (TextView) convertView.findViewById(R.id.textView);
            final LinearLayout all_choose = (LinearLayout) convertView.findViewById(R.id.all_choose);
            textView.setText(group_List.get(groupPosition).getContent().getOpName());

            if (group_List.get(groupPosition).getIsChoosed() == 0) {
                all_leaders.setSelected(false);
            } else if (group_List.get(groupPosition).getIsChoosed() == 1) {
                all_leaders.setSelected(true);
            }

            if (group_List.get(groupPosition).getIsTurn() == 0) {
                shift_leaders.setVisibility(View.VISIBLE);
                shift_leaders_down.setVisibility(View.GONE);
            } else {
                shift_leaders.setVisibility(View.GONE);
                shift_leaders_down.setVisibility(View.VISIBLE);
            }

            all_choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int status = group_List.get(groupPosition).getIsChoosed();
                    group_List.get(groupPosition).setIsChoosed(status == 0 ? 1 : 0);
                    for (int j = 0; j < child_List.get(groupPosition).size(); j++) {
                        child_List.get(groupPosition).get(j).setIsChoosed(status == 0 ? 1 : 0);
                    }
                    all_leaders.setSelected(status == 0 ? true : false);
                    myAdapter.refresh();

                    if(adapter_addtion != null){
                        adapter_addtion.notifyDataSetChanged();
                    }


                }
            });

            return convertView;
        }

        // 返回children子视图布局
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_in2, parent, false);

            }
            LinearLayout all_layoutc = (LinearLayout) convertView.findViewById(R.id.all_layoutc);
            final ImageView isChoose = (ImageView) convertView.findViewById(R.id.isChoose);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(child_List.get(groupPosition).get(childPosition).getContent().getOpName());

            int status = child_List.get(groupPosition).get(childPosition).getIsChoosed();
            isChoose.setSelected(status == 0 ? false : true);

            all_layoutc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int status = child_List.get(groupPosition).get(childPosition).getIsChoosed();
                    child_List.get(groupPosition).get(childPosition).setIsChoosed(status == 1 ? 0 : 1);
                    isChoose.setSelected(status == 1 ? false : true);

                    String isChoose = "true";
                    for (int i = 0; i < child_List.get(groupPosition).size(); i++) {

                        if (child_List.get(groupPosition).get(i).getIsChoosed() == 0) {
                            isChoose = "false";
                            break;
                        }
                    }

                    if (isChoose.equals("true")) {
                        group_List.get(groupPosition).setIsChoosed(1);
                    } else {
                        group_List.get(groupPosition).setIsChoosed(0);
                    }
                    myAdapter.refresh();

                    if(adapter_addtion != null){
                        adapter_addtion.notifyDataSetChanged();
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

    public class ThreeItemAdapter extends MultiItemRecycleViewAdapter<ThreeItemInfo2> {
        private static final int TYPE_ONE = 0;
        private static final int TYPE_TWO = 1;
        private static final int TYPE_THREE = 2;
        private static final int TYPE_four = 3;

        public ThreeItemAdapter(Context context, List<ThreeItemInfo2> datas) {
            super(context, datas, new MultiItemTypeSupport<ThreeItemInfo2>() {
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
                public int getItemViewType(int position, ThreeItemInfo2 threeItemInfo) {
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
        public void convert(ViewHolderHelper helper, ThreeItemInfo2 threeItemInfo, int position) {
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

        private void bindViewOne(ViewHolderHelper helper, final ThreeItemInfo2 threeItemInfo, final int position) {
            TextView tv_1 = helper.getView(R.id.tv_1);
            LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
            final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
            final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);

            tv_1.setText(threeItemInfo.getOpName());

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

                    if (threeItemInfo.getChildList() != null && threeItemInfo.getChildList().size() != 0 && threeItemInfo.getIsExpading()) {

                        shift_leaders.setVisibility(View.INVISIBLE);
                        shift_leaders_down.setVisibility(View.VISIBLE);
                        Log.e("点击后展开","点击后展开");

                        threeItemInfo.setIsExpading(false);
                        addAllAt(position + 1, threeItemInfo.getChildList());


                    } else if (threeItemInfo.getChildList() != null & threeItemInfo.getChildList().size() != 0 && !threeItemInfo.getIsExpading()) {
                        Log.e("点击后收缩","点击后收缩");
                        shift_leaders.setVisibility(View.VISIBLE);
                        shift_leaders_down.setVisibility(View.INVISIBLE);


                        for (ThreeItemInfo2 item : threeItemInfo.getChildList()) {
                            if (!item.getIsExpading()) {
                                removeAll(item.getChildList());
                            }

                            for(ThreeItemInfo2 item2 : item.getChildList()){

                                if (!item2.getIsExpading()) {
                                    removeAll(item2.getChildList());
                                }
                            }
                        }
                        threeItemInfo.setIsExpading(true);

                        for (int i=0;i<threeItemInfo.getChildList().size();i++){

                            threeItemInfo.getChildList().get(i).setIsExpading(true);

                            for(int j=0; j<threeItemInfo.getChildList().get(i).getChildList().size();j++){

                                threeItemInfo.getChildList().get(i).getChildList().get(j).setIsExpading(true);

                            }
                        }
                        removeAll(threeItemInfo.getChildList());

                    }
                }
            });

        }

        private void bindViewTwo(ViewHolderHelper helper, final ThreeItemInfo2 threeItemInfo, final int position) {
            TextView tv_2 = helper.getView(R.id.tv_2);
            LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
            final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
            final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);
            tv_2.setText(threeItemInfo.getOpName());

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

                    if (threeItemInfo.getChildList() != null && threeItemInfo.getChildList().size() != 0 && threeItemInfo.getIsExpading()) {
                        shift_leaders.setVisibility(View.INVISIBLE);
                        shift_leaders_down.setVisibility(View.VISIBLE);
                        Log.e("点击后展开","点击后展开");

                        threeItemInfo.setIsExpading(false);
                        addAllAt(position + 1, threeItemInfo.getChildList());

                    } else if (threeItemInfo.getChildList() != null & threeItemInfo.getChildList().size() != 0 && !threeItemInfo.getIsExpading()) {
                        Log.e("点击后收缩","点击后收缩");
                        shift_leaders.setVisibility(View.VISIBLE);
                        shift_leaders_down.setVisibility(View.INVISIBLE);


                        for (ThreeItemInfo2 item : threeItemInfo.getChildList()) {
                            if (!item.getIsExpading()) {
                                removeAll(item.getChildList());
                            }
                        }

                        threeItemInfo.setIsExpading(true);
                        for (int i=0;i<threeItemInfo.getChildList().size();i++){

                            threeItemInfo.getChildList().get(i).setIsExpading(true);

                        }
                        removeAll(threeItemInfo.getChildList());

                    }

                }
            });
        }

        private void bindViewThree(ViewHolderHelper helper,final ThreeItemInfo2 threeItemInfo, final int position) {
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

            tv_3.setText(threeItemInfo.getOpName());
            extend_leaders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (threeItemInfo.getChildList() != null && threeItemInfo.getChildList().size() != 0 && threeItemInfo.getIsExpading()) {

                        shift_leaders.setVisibility(View.INVISIBLE);
                        shift_leaders_down.setVisibility(View.VISIBLE);

                        Log.e("点击后展开","点击后展开");
                        threeItemInfo.setIsExpading(false);
                        addAllAt(position + 1, threeItemInfo.getChildList());


                    } else if (threeItemInfo.getChildList() != null & threeItemInfo.getChildList().size() != 0 && !threeItemInfo.getIsExpading()) {

                        shift_leaders.setVisibility(View.VISIBLE);
                        shift_leaders_down.setVisibility(View.INVISIBLE);

                        Log.e("点击后收缩","点击后收缩");
                        threeItemInfo.setIsExpading(true);
                        removeAll(threeItemInfo.getChildList());

                    }
                }
            });
        }

        private void bindViewFour(ViewHolderHelper helper, final ThreeItemInfo2 threeItemInfo, final int position) {
            TextView tv_4 = helper.getView(R.id.tv_4);
            LinearLayout linearLayout = helper.getView(R.id.linearLayout);
            final ImageView isChoosed=helper.getView(R.id.isChoosed);
            tv_4.setText(threeItemInfo.getOpName());

            if(threeItemInfo.getIs_choosed()){
                isChoosed.setSelected(true);
            }else{
                isChoosed.setSelected(false);
            }

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if(threeItemInfo.getIs_choosed()){

                            isChoosed.setSelected(false);
                            threeItemInfo.setIs_choosed(false);

                            for (int i = 0; i < mList_copy.size(); i++){
                                for(int j = 0; j < mList_copy.get(i).getChildList().size(); j++){
                                    for(int k = 0; k < mList_copy.get(i).getChildList().get(j).getChildList().size(); k++){
                                        for(int l = 0; l < mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++){
                                            if(mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpId().equals(threeItemInfo.getOpId())){
                                                mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setIs_choosed(false);
                                            }
                                        }
                                    }
                                }
                            }

                        }else{
                            isChoosed.setSelected(true);
                            threeItemInfo.setIs_choosed(true);

                            for (int i = 0; i < mList_copy.size(); i++){
                                for(int j = 0; j < mList_copy.get(i).getChildList().size(); j++){
                                    for(int k = 0; k < mList_copy.get(i).getChildList().get(j).getChildList().size(); k++){
                                        for(int l = 0; l < mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); l++){
                                            if(mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).getOpId().equals(threeItemInfo.getOpId())){
                                                mList_copy.get(i).getChildList().get(j).getChildList().get(k).getChildList().get(l).setIs_choosed(true);
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
            });
        }


        private com.lanwei.governmentstar.adapter.ThreeItemAdapter.OnItemThreeClickListener onItemThreeClickListener;

        public void setOnItemThreeClickListener(com.lanwei.governmentstar.adapter.ThreeItemAdapter.OnItemThreeClickListener onItemThreeClickListener) {
            this.onItemThreeClickListener = onItemThreeClickListener;
        }
    }

}
