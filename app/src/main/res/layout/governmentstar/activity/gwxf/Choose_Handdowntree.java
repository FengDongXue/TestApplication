package com.lanwei.governmentstar.activity.gwxf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
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
import com.lanwei.governmentstar.activity.ChooseReceiverPSActivity;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.activity.zyx.GgNoticeActivity;
import com.lanwei.governmentstar.bean.Data_HanddownTree;
import com.lanwei.governmentstar.bean.Expanable_Bean;
import com.lanwei.governmentstar.bean.Expanable_Bean_Child;
import com.lanwei.governmentstar.bean.InCirculationTree;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.view.MyExpandableListView;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/5.
 */

public class Choose_Handdowntree extends BaseActivity implements View.OnClickListener {

    private MyExpandableListView elv;
    private ImageView back;
    private TextView myapply;
    private TextView null_search;
    private ArrayList<Data_HanddownTree.ChildList> list_data;
    private TextView title;
    private String opId_choosed = "";
    private ImageView iv_contacts;
    private MyAdapter myAdapter;
    private Adapter_Addtion adapter_addtion;
    private EditText search;
    private RecyclerView recyclerView;
    private ArrayList<Search> list_choose =new ArrayList<>();
    private LinearLayout result_search;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_receiver2);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        initweight();
        elv.setGroupIndicator(null);
        elv.setDivider(null);


        // 检测点击，展开或收缩group，改变相应控件
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (list_data.get(groupPosition).getIsTurn() == 0) {
                    list_data.get(groupPosition).setIsTurn(1);
                    v.findViewById(R.id.shift_leaders).setSelected(true);

                } else {
                    list_data.get(groupPosition).setIsTurn(0);
                    v.findViewById(R.id.shift_leaders).setSelected(false);

                }
                return false;
            }
        });

        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<Data_HanddownTree> call = api.handdown_tree(new GetAccount(this).dptId());
        call.enqueue(new Callback<Data_HanddownTree>() {
            @Override
            public void onResponse(Call<Data_HanddownTree> call, Response<Data_HanddownTree> response) {

                list_data = response.body().getData();
                myAdapter = new MyAdapter();

                if (!getIntent().getStringExtra("opIds").equals("")) {
                    opId_choosed = getIntent().getStringExtra("opIds");
                }

                Log.e("前一个界面带过来的已选择人的ID", opId_choosed);
                String[] opid_list = opId_choosed.split(",");
                // 数组转为Arraylist
                ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));


                for(int i=0;i<list2.size();i++){

                    for(int j=0 ;j<list_data.size() ;j++){

                        for(int k=0 ;k<list_data.get(j).getChildList().size() ;k++){

                            if(list2.get(i).equals(list_data.get(j).getChildList().get(k).getOpId())){

                                list_data.get(j).getChildList().get(k).setIsChoosed(1);
                            }
                        }
                    }
                }

                // 恢复group_list的状态
                int count = 0;
                for (int n = 0; n < list_data.size(); n++) {

                    for (int l = 0; l < list_data.get(n).getChildList().size(); l++) {

                        if (list_data.get(n).getChildList().get(l).getIsChoosed() == 0) {

                            break;

                        } else {
                            if (l == list_data.get(n).getChildList().size() - 1) {
                                list_data.get(n).setIsChoosed(1);
                            }
                        }

                    }
                }


                elv.setAdapter(myAdapter);

                search.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                            list_choose.clear();
                            result_search.setVisibility(View.VISIBLE);
                            if(!search.getText().toString().trim().equals("")){

                                for(int i=0;i<list_data.size();i++){

                                    for(int j=0;j<list_data.get(i).getChildList().size();j++){

                                        if(list_data.get(i).getChildList().get(j).getOpName().contains(search.getText().toString().trim())){

                                          list_choose.add(new Search(i,j));
                                    }
                                }

                              }

                              Log.e("names多少个：",list_choose.size()+"");

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

            @Override
            public void onFailure(Call<Data_HanddownTree> call, Throwable t) {
                Toast.makeText(Choose_Handdowntree.this, "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });

    }

    void initweight() {
        title = (TextView) findViewById(R.id.tv_address);
        elv = (MyExpandableListView) findViewById(R.id.elv);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        back = (ImageView) findViewById(R.id.back);
        myapply = (TextView) findViewById(R.id.tv_apl);
        null_search = (TextView) findViewById(R.id.null_search);
        search = (EditText) findViewById(R.id.search);
        result_search = (LinearLayout) findViewById(R.id.result_search);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // 为RecyclerView设置默认动画和线性布局管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        search.setHint("请输入下发单位");
        iv_contacts.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        myapply.setVisibility(View.VISIBLE);
        myapply.setText("确定");
        back.setOnClickListener(this);
        myapply.setOnClickListener(this);
        if (getIntent().getStringExtra("title") != null) {
            title.setText(getIntent().getStringExtra("title"));
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:

                finish();
                break;
            case R.id.tv_apl:

                String opIds_now ="" ;
                String dopIds_name ="" ;

                for(int j=0 ;j<list_data.size() ;j++){

                    for(int k=0 ;k<list_data.get(j).getChildList().size() ;k++){

                        if(list_data.get(j).getChildList().get(k).getIsChoosed()==1){

                            opIds_now=opIds_now+list_data.get(j).getChildList().get(k).getOpId()+",";
                            dopIds_name=dopIds_name+list_data.get(j).getChildList().get(k).getOpName()+",";
                        }
                    }
                }

                //判断承办人是否为空
                if (!opIds_now.equals("")) {
                    //去掉最后一个字符串，因为最后一个字符串是逗号
                    opIds_now = opIds_now.substring(0, opIds_now.length() - 1);
                    dopIds_name = dopIds_name.substring(0, dopIds_name.length() - 1);
                }

                Log.e("dfdgf非常的说法都是d",opIds_now.toString());
                Log.e("dfdg对方对方的fd",dopIds_name.toString());

                Intent intent = new Intent(this,DocuHanddown_Activity.class);
                intent.putExtra("opIds",opIds_now);
                intent.putExtra("dopIds_name",dopIds_name);
                setResult(001,intent);

                finish();
                break;

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

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), list_data.get(list_choose.get(position).getId_father()).getChildList().get(list_choose.get(position).getId_children()).getOpName(), search.getText().toString().trim());
            holder.textView.setText(spannableString);
            holder.department.setText("("+list_data.get(list_choose.get(position).getId_father()).getOpName()+")");
            holder.department.setVisibility(View.VISIBLE);
            holder.fragment.setVisibility(View.GONE);

            Boolean is_choose;

            if(list_data.get(list_choose.get(position).getId_father()).getChildList().get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                is_choose=true;
            }else{
                is_choose=false;
            }

            if(is_choose){
                holder.all_leaders.setSelected(true);
            }else{
                holder.all_leaders.setSelected(false);

            }


            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(list_data.get(list_choose.get(position).getId_father()).getChildList().get(list_choose.get(position).getId_children()).getIsChoosed()==1){
                        list_data.get(list_choose.get(position).getId_father()).getChildList().get(list_choose.get(position).getId_children()).setIsChoosed(0);
                        holder.all_leaders.setSelected(false);
                    }else{
                        list_data.get(list_choose.get(position).getId_father()).getChildList().get(list_choose.get(position).getId_children()).setIsChoosed(1);
                        holder.all_leaders.setSelected(true);
                    }

                    String isChoose = "true";
                    for (int i = 0; i < list_data.get(list_choose.get(position).getId_father()).getChildList().size(); i++) {

                        if (list_data.get(list_choose.get(position).getId_father()).getChildList().get(i).getIsChoosed() == 0) {
                            isChoose = "false";
                            break;
                        }
                    }

                    if (isChoose.equals("true")) {
                        list_data.get(list_choose.get(position).getId_father()).setIsChoosed(1);
                    } else {
                        list_data.get(list_choose.get(position).getId_father()).setIsChoosed(0);
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
            return list_data.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list_data.get(groupPosition).getChildList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return list_data.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return list_data.get(groupPosition).getChildList().get(childPosition);
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
            textView.setText(list_data.get(groupPosition).getOpName());

            if (list_data.get(groupPosition).getIsChoosed() == 0) {
                all_leaders.setSelected(false);
            } else if (list_data.get(groupPosition).getIsChoosed() == 1) {
                all_leaders.setSelected(true);
            }

            if (list_data.get(groupPosition).getIsTurn() == 0) {
                shift_leaders.setVisibility(View.VISIBLE);
                shift_leaders_down.setVisibility(View.GONE);
            } else {
                shift_leaders.setVisibility(View.GONE);
                shift_leaders_down.setVisibility(View.VISIBLE);
            }

            all_leaders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int status = list_data.get(groupPosition).getIsChoosed();
                    list_data.get(groupPosition).setIsChoosed(status == 0 ? 1 : 0);
                    for (int j = 0; j < list_data.get(groupPosition).getChildList().size(); j++) {
                        list_data.get(groupPosition).getChildList().get(j).setIsChoosed(status == 0 ? 1 : 0);
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
            name.setText(list_data.get(groupPosition).getChildList().get(childPosition).getOpName());

            int status = list_data.get(groupPosition).getChildList().get(childPosition).getIsChoosed();
            isChoose.setSelected(status == 0 ? false : true);

//            InCirculationTree.ChildList content = list_data.get(groupPosition).getChildList().get(childPosition).get();
//            String accountRoleId = content.getAccountRole();
//            all_layoutc.setTag(accountRoleId);

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int status = list_data.get(groupPosition).getChildList().get(childPosition).getIsChoosed();
                    list_data.get(groupPosition).getChildList().get(childPosition).setIsChoosed(status == 1 ? 0 : 1);
                    isChoose.setSelected(status == 1 ? false : true);

                    String isChoose = "true";
                    for (int i = 0; i < list_data.get(groupPosition).getChildList().size(); i++) {

                        if (list_data.get(groupPosition).getChildList().get(i).getIsChoosed() == 0) {
                            isChoose = "false";
                            break;
                        }
                    }

                    if (isChoose.equals("true")) {
                        list_data.get(groupPosition).setIsChoosed(1);
                    } else {
                        list_data.get(groupPosition).setIsChoosed(0);
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