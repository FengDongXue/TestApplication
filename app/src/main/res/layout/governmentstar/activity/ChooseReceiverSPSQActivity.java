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

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.bean.Expanable_Bean;
import com.lanwei.governmentstar.bean.Expanable_Bean_Child;
import com.lanwei.governmentstar.bean.InCirculationTree;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.GetAccount;
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
 * Created by 蓝威科技-技术开发1 on 2017/4/1.
 */

public class ChooseReceiverSPSQActivity extends BaseActivity implements View.OnClickListener {

    private ExpandableListView elv;
    private ImageView back;
    private TextView add;

    private List<Expanable_Bean> group_List;
    private List<List<Expanable_Bean_Child>> child_List;
    private MyAdapter myAdapter;
    private TextView title;
    private String opId_choosed = "";
    private ImageView iv_contacts;
    private LinearLayout result_search;
    private RecyclerView recyclerView;
    private TextView null_search;
    private EditText search;
    private ScrollView scrollView;
    private Adapter_Addtion adapter_addtion;
    private ArrayList<Search> list_choose =new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_receiver2);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        initweight();
        getDate();

    }

    private void getDate() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<InCirculationTree> call = api.inCirculationTree(getIntent().getStringExtra("action"),
                new GetAccount(this).opId(), new GetAccount(this).dptId());
        call.enqueue(new Callback<InCirculationTree>() {
            @Override
            public void onResponse(Call<InCirculationTree> call, Response<InCirculationTree> response) {
                Log.e("response", response.body().toString());
                if (response.body().getDeptList() == null) {
                    Toast.makeText(ChooseReceiverSPSQActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    // 实体类含义，第一个int是item的是否选择的标志位，第二个int是group是否展开的标志位
                    getdata(response.body());
                }
            }
            @Override
            public void onFailure(Call<InCirculationTree> call, Throwable t) {
                Toast.makeText(ChooseReceiverSPSQActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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
        elv.setGroupIndicator(null);
        elv.setAdapter(myAdapter);
        elv.setDivider(null);


        // 检测点击，展开或收缩group，改变相应控件
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (group_List.get(groupPosition).getIsTurn() == 0) {
                    group_List.get(groupPosition).setIsTurn(1);
                    v.findViewById(R.id.shift_leaders).setSelected(true);

                } else {
                    group_List.get(groupPosition).setIsTurn(0);
                    v.findViewById(R.id.shift_leaders).setSelected(false);

                }
                return false;
            }
        });

        search.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                    list_choose.clear();
                    result_search.setVisibility(View.VISIBLE);
                    if(!search.getText().toString().trim().equals("")){

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

        switch (v.getId()) {
            case R.id.back:

                finish();
                break;

            case R.id.tv_apl:
                String opIds = "";
                String opBsrId = "";
                String opNbrId = "";
                ArrayList<String> list =new ArrayList<>();
                String opNames="";
                for (int i = 0; i < child_List.size(); i++) {
                    for (int j = 0; j < child_List.get(i).size(); j++) {
                        if (child_List.get(i).get(j).getIsChoosed() == 1) {
                            //判断是否选择的是部门领导还是主任领导
                            if (child_List.get(i).get(j).getContent().getAccountRole().equals("duty-04") && !getIntent().getStringExtra("opNbrId").equals(child_List.get(i).get(j).getContent().getOpId())) {
                                //办事人
                                opBsrId += child_List.get(i).get(j).getContent().getOpId() + ",";
                                list.add(child_List.get(i).get(j).getContent().getOpName());
                            } else if (getIntent().getStringExtra("opNbrId").equals(child_List.get(i).get(j).getContent().getOpId())) {
                                opNbrId = getIntent().getStringExtra("opNbrId");
                                list.add(child_List.get(i).get(j).getContent().getOpName());
                            } else {
                                //承办人
                                opIds += child_List.get(i).get(j).getContent().getOpId() + ",";
                                list.add(child_List.get(i).get(j).getContent().getOpName());
                            }
                        }
                    }
                }


                if(list != null && list.size()>0){
                    opNames= list.get(0);
                }


                Log.e("dsfdsfds",opNames);


                Intent intent = new Intent(this, DocumentBaseCActivity.class);
                //判断承办人是否为空
                if (!opIds.equals("")) {
                    //去掉最后一个字符串，因为最后一个字符串是逗号
                    opIds = opIds.substring(0, opIds.length() - 1);
                }
                //判断办事人是否为空
                if (!opBsrId.equals("")) {
                    opBsrId = opBsrId.substring(0, opBsrId.length() - 1);
                }

                //判断办事人是否为空
                if (!opNbrId.equals("")) {
                    opNbrId = opNbrId.substring(0, opNbrId.length() - 1);
                }

                Log.e("已选择的承办人", opIds);
                Log.e("已选择的办事人", opBsrId);
                Log.e("拟办人账号", opNbrId);

                intent.putExtra("opIds", opIds);
                intent.putExtra("opBsrId", opBsrId);
                intent.putExtra("opNbrId", opNbrId);
                intent.putExtra("opNames", opNames);

                setResult(10, intent);
                finish();
                //Toast.makeText(this,app.toString()+"添加更多",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void initweight() {
        title = (TextView) findViewById(R.id.tv_address);
        elv = (ExpandableListView) findViewById(R.id.elv);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        back = (ImageView) findViewById(R.id.back);
        add = (TextView) findViewById(R.id.tv_apl);
        search=(EditText) findViewById(R.id.search);
        scrollView=(ScrollView) findViewById(R.id.scrollView);
        group_List = new ArrayList<>();
        child_List = new ArrayList<>();

        null_search=(TextView) findViewById(R.id.null_search);
        result_search=(LinearLayout) findViewById(R.id.result_search);
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

        iv_contacts.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        add.setText("确定");
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        if (getIntent().getStringExtra("title") != null) {
            title.setText(getIntent().getStringExtra("title"));
        }
    }

    class MyAdapter extends BaseExpandableListAdapter {


        private Handler handler;

        public MyAdapter(List<Expanable_Bean> groupList, List<List<Expanable_Bean_Child>> childList) {

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
                convertView = getLayoutInflater().inflate(R.layout.item_out_ps, parent, false);
            }
            // 全选选项
            final ImageView all_leaders = (ImageView) convertView.findViewById(R.id.all_leaders);
            final ImageView shift_leaders = (ImageView) convertView.findViewById(R.id.shift_leaders);
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
                convertView = getLayoutInflater().inflate(R.layout.item_in, parent, false);
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

//            Boolean is_choose;
//
//            if(child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed()==1){
//                is_choose=true;
//            }else{
//                is_choose=false;
//            }

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
                    myAdapter.refresh();


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