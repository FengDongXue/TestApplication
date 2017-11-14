package com.lanwei.governmentstar.activity.zwyx;

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
import com.lanwei.governmentstar.activity.ChooseReceiverNBActivity;
import com.lanwei.governmentstar.bean.Expanable_Bean;
import com.lanwei.governmentstar.bean.Expanable_Bean_Child;
import com.lanwei.governmentstar.bean.ZwyxTreeBean;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/10/010.
 */

public class ZwyxTreeActivity extends BaseActivity implements View.OnClickListener {

    private List<Expanable_Bean> group_List;
    private List<List<Expanable_Bean_Child>> child_List;
    private String opId_choosed = "";
    private MyAdapter myAdapter;
    private ExpandableListView elv;
    private TextView title;
    private ImageView back;
    private TextView add;
    private EditText tvsearch;
    private String searchCt;
    private List<ZwyxTreeBean.DataBean> dataList = new ArrayList<>();
    private String search = "";
    private String opIdNames;
    private ArrayList<String> sjname;
    private ImageView iv_contacts;
    private LinearLayout all_layoutc;
    private ArrayList<String> opIdlist;
    private ArrayList<Search> list_choose = new ArrayList<>();
    private LinearLayout result_search;
    private TextView null_search;
    private ScrollView scrollView;
    private Adapter_Addtion adapter_addtion;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_receiver2);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweight();
        myAdapter = new MyAdapter(group_List, child_List, search, checkList);
        getData();

    }

    private void initweight() {
        title = (TextView) findViewById(R.id.tv_address);
        back = (ImageView) findViewById(R.id.back);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        result_search = (LinearLayout) findViewById(R.id.result_search);
        null_search = (TextView) findViewById(R.id.null_search);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        add = (TextView) findViewById(R.id.tv_apl);
        title.setText("选择收件人");
        add.setText("确定");
        add.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        elv = (ExpandableListView) findViewById(R.id.elv);
        group_List = new ArrayList<>();
        group_List = new ArrayList<>();
        child_List = new ArrayList<>();
        // 为RecyclerView设置默认动画和线性布局管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        tvsearch = (EditText) findViewById(R.id.search);
        initsearch();
//        tvsearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】
//
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {//修改回车键功能
//                    // 先隐藏键盘
//                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//                    //关键字
//                    searchCt = tvsearch.getText().toString();
//
//
//
//
//
//                    if (dataList != null) {
//                        search = searchCt;
//                        dataList.clear();         //清除所有数据的集合
//                        child_List.clear();      //清除子集合
//                        group_List.clear();      //清除父集合
//                        getData();
//                        myAdapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(ZwyxTreeActivity.this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

        back.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    void initsearch() {
        tvsearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                    list_choose.clear();
                    result_search.setVisibility(View.VISIBLE);
                    if (!tvsearch.getText().toString().trim().equals("")) {
                        list_choose.clear();

                        for (int i = 0; i < group_List.size(); i++) {

                            for (int j = 0; j < child_List.get(i).size(); j++) {

                                if (child_List.get(i).get(j).getContent1().getOpName().contains(tvsearch.getText().toString().trim())) {

                                    list_choose.add(new Search(i, j));
                                }
                            }

                        }

                        Log.e("大幅答复", list_choose.size() + "");
                        adapter_addtion = new Adapter_Addtion();
                        recyclerView.setAdapter(adapter_addtion);
                        recyclerView.setVisibility(View.VISIBLE);

                        if (list_choose.size() < 1) {
                            null_search.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            null_search.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                    } else {
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
                    }, 300);

                }
                return false;
            }
        });
    }


    private void getData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<ZwyxTreeBean> call = api.zwyxTree("zwyxTree", new GetAccount(this).dptId(), "");
        call.enqueue(new Callback<ZwyxTreeBean>() {
            @Override
            public void onResponse(Call<ZwyxTreeBean> call, Response<ZwyxTreeBean> response) {
                Log.e("数据返回", response.body().getData().toString());
                dataList = response.body().getData();
                if (dataList == null) {
                    Toast.makeText(ZwyxTreeActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    // 实体类含义，第一个int是item的是否选择的标志位，第二个int是group是否展开的标志位
                    getData1(response.body());
                }
            }

            @Override
            public void onFailure(Call<ZwyxTreeBean> call, Throwable t) {
                Toast.makeText(ZwyxTreeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("数据返回", t.toString());
            }
        });
    }

    private void getData1(ZwyxTreeBean zwyxTree) {

        for (int i = 0; i < zwyxTree.getData().size(); i++) {
            group_List.add(new Expanable_Bean(0, zwyxTree.getData().get(i), 0));
            ArrayList<Expanable_Bean_Child> list = new ArrayList<>();
            for (int j = 0; j < zwyxTree.getData().get(i).getChildList().size(); j++) {//子类
                list.add(new Expanable_Bean_Child(0, zwyxTree.getData().get(i).getChildList().get(j), 0));
            }
            child_List.add(list);
        }

        Intent intent = getIntent();
        /**
         *  显示在创建邮件界面已选择的人
         */
        ArrayList<String> list2 = intent.getStringArrayListExtra("deleteList");
        if (list2 != null && list2.size() > 0) {
            for (int m = 0; m < list2.size(); m++) {
                for (int n = 0; n < group_List.size(); n++) {
                    for (int l = 0; l < child_List.get(n).size(); l++) {//子类
                        if (child_List.get(n).get(l).getContent1().getOpId().equals(list2.get(m))) {
                            child_List.get(n).get(l).setIsChoosed(1);
                            checkList.add(child_List.get(n).get(l).getContent1().getOpId());
                        }
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
        elv.setGroupIndicator(null);
        elv.setAdapter(myAdapter);
        elv.setDivider(null);
        myAdapter.notifyDataSetChanged();

        // 检测点击，展开或收缩group，改变相应控件
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (group_List.get(groupPosition).getIsTurn() == 0) {  // 0未选中 1选中
                    group_List.get(groupPosition).setIsTurn(1);
                    v.findViewById(R.id.shift_leaders).setSelected(true);
                } else {
                    group_List.get(groupPosition).setIsTurn(0);
                    v.findViewById(R.id.shift_leaders).setSelected(false);
                }
                return false;
            }
        });
    }

    private ArrayList<String> checkList = new ArrayList();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                // 先隐藏键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
                break;
            case R.id.tv_apl:
                String opIds = "";
                sjname = new ArrayList<>();
                opIdlist = new ArrayList<>();
                for (int i = 0; i < child_List.size(); i++) {
                    for (int j = 0; j < child_List.get(i).size(); j++) {
                        if (child_List.get(i).get(j).getIsChoosed() == 1) {
                            //承办人
                            String opId = child_List.get(i).get(j).getContent1().getOpId();
                            opIds += child_List.get(i).get(j).getContent1().getOpId() + ",";
                            opIdNames = child_List.get(i).get(j).getContent1().getOpName();

                            Log.d("111", String.valueOf(child_List.get(i).get(j).getContent1().getOpId()));
                            if (opIdNames != null) {
                                if (sjname.size() > 29) {
                                    Toast.makeText(this, "最多不得超过30人", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                sjname.add(opIdNames);

                                opIdlist.add(child_List.get(i).get(j).getContent1().getOpId());

                            } else {
                                return;
                            }
                        }
                    }
                }
                Intent intent = new Intent();
                //判断承办人是否为空
                if (!opIds.equals("")) {
                    //去掉最后一个字符串，因为最后一个字符串是逗号
                    opIds = opIds.substring(0, opIds.length() - 1);
                }
                if (sjname == null & sjname.size() <= 0) {      //如果jsname为空 就new一个空的集合，保证传过去不为空
                    sjname = new ArrayList<>();
                    opIdlist = new ArrayList<>();
                }
                intent.putExtra("opIds", opIds);
                intent.putExtra("opIdlist", opIdlist);
                intent.putStringArrayListExtra("addresseNameList", sjname);
                setResult(20, intent);
                finish();
                break;
        }
    }

    private int count = 0;

    class MyAdapter extends BaseExpandableListAdapter {

        private List<Expanable_Bean> groupList;//外层的数据源
        private List<List<Expanable_Bean_Child>> childList;//里层的数据源
        private Handler handler;


        public MyAdapter(List<Expanable_Bean> groupList, List<List<Expanable_Bean_Child>> childList, String s, ArrayList<String> checkList) {
            this.groupList = groupList;
            this.childList = childList;
            this.childs = checkList;
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

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_out_ps, parent, false);
            }
            // 全选选项
            final ImageView all_leaders = (ImageView) convertView.findViewById(R.id.all_leaders);
            all_leaders.setVisibility(View.GONE);
            final ImageView shift_leaders = (ImageView) convertView.findViewById(R.id.shift_leaders);
            final ImageView shift_leaders_down = (ImageView) convertView.findViewById(R.id.shift_leaders_down);
            final TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText(groupList.get(groupPosition).getContext1().getOpName());

//            // TODO: 2017/5/13 改变关键字的颜色
//            String tit = groupList.get(groupPosition).getContext1().getOpName();
//            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), tit, search);
//            textView.setText(spannableString);

//            if (group_List.get(groupPosition).getIsChoosed() == 0) {
//                all_leaders.setSelected(false);
//            } else if (group_List.get(groupPosition).getIsChoosed() == 1) {
//                all_leaders.setSelected(true);
//            }

            if (group_List.get(groupPosition).getIsTurn() == 0) {
                shift_leaders.setVisibility(View.VISIBLE);
                shift_leaders_down.setVisibility(View.GONE);
            } else {
                shift_leaders.setVisibility(View.GONE);
                shift_leaders_down.setVisibility(View.VISIBLE);
            }

//            all_leaders.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int status = groupList.get(groupPosition).getIsChoosed();
//                    groupList.get(groupPosition).setIsChoosed(status == 0 ? 1 : 0);
//                    for (int j = 0; j < child_List.get(groupPosition).size(); j++) {
//                        child_List.get(groupPosition).get(j).setIsChoosed(status == 0 ? 1 : 0);
//                    }
//                    all_leaders.setSelected(status == 0 ? true : false);
//                    myAdapter.refresh();
//
//                }
//            });

            return convertView;
        }

        ArrayList<String> childs = new ArrayList<>();

        // 返回children子视图布局
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_in, parent, false);
            }

            all_layoutc = (LinearLayout) convertView.findViewById(R.id.all_layoutc);
            final ImageView isChoose = (ImageView) convertView.findViewById(R.id.isChoose);
            final TextView name = (TextView) convertView.findViewById(R.id.name);
//            name.setText(childList.get(groupPosition).get(childPosition).getContent1().getOpName());

            int status = childList.get(groupPosition).get(childPosition).getIsChoosed();
            isChoose.setSelected(status == 0 ? false : true);

            // TODO: 2017/5/13 改变关键字的颜色
            String tit = childList.get(groupPosition).get(childPosition).getContent1().getOpName();
            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), tit, search);
            name.setText(spannableString);

            ZwyxTreeBean.ChildListBean content = child_List.get(groupPosition).get(childPosition).getContent1();
            String accountRoleId = content.getAccountRole();
            all_layoutc.setTag(accountRoleId);


            if (childs.size() > 29) {
                if (status == 0) {
                    name.setTextColor(Color.parseColor("#999999"));
                }
            }

            all_layoutc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int status = childList.get(groupPosition).get(childPosition).getIsChoosed();

                    if (status == 0) {
                        if (childs.size() > 29) {
                            Log.e("集合数量ccc", String.valueOf(childs.size()));
                            Toast.makeText(ZwyxTreeActivity.this, "最多不得超过30人", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            childs.add(childList.get(groupPosition).get(childPosition).getContent1().getOpId());
                        }
                    } else {
                        for (int i = 0; i < childs.size(); i++) {
                            if (childList.get(groupPosition).get(childPosition).getContent1().getOpId() == childs.get(i)) {
                                childs.remove(i);
                                childList.get(groupPosition).get(childPosition).setIsChoosed(1);
                                Log.e("集合数量", String.valueOf(childs.size()));
                                Log.e("集合数量11", childs.toString());
                            }
                        }
                    }

                    childList.get(groupPosition).get(childPosition).setIsChoosed(status == 1 ? 0 : 1);
                    isChoose.setSelected(status == 1 ? false : true);

                    String isChoose = "true";
                    for (int i = 0; i < childList.get(groupPosition).size(); i++) {

                        if (childList.get(groupPosition).get(i).getIsChoosed() == 0) {
                            isChoose = "false";
                            break;
                        }
                    }

                    if (isChoose.equals("true")) {
                        groupList.get(groupPosition).setIsChoosed(1);
                    } else {
                        groupList.get(groupPosition).setIsChoosed(0);
                    }
//                    myAdapter.refresh();
                    if (adapter_addtion != null) {
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

    class Search {

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

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getContent1().getOpName(), tvsearch.getText().toString().trim());
            holder.textView.setText(spannableString);
            holder.department.setText("(" + group_List.get(list_choose.get(position).getId_father()).getContext1().getOpName() + ")");
            holder.department.setVisibility(View.VISIBLE);
            holder.fragment.setVisibility(View.GONE);

            if (child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed() == 1) {
                holder.all_leaders.setSelected(true);
            } else {
                holder.all_leaders.setSelected(false);

            }


            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).getIsChoosed() == 1) {
                        child_List.get(list_choose.get(position).getId_father()).get(list_choose.get(position).getId_children()).setIsChoosed(0);
                        holder.all_leaders.setSelected(false);
                    } else {
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

            if (list_choose == null) {
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


}
