package com.lanwei.governmentstar.activity.gwxf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zwyx.CheckMail;
import com.lanwei.governmentstar.bean.Data_Handdown;
import com.lanwei.governmentstar.bean.HandDown;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.MyDocument;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.bean.Return_Wait;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 蓝威科技-技术开发1 on 2017/8/4.
 */

public class DocuHanddown_List_Actitivty extends BaseActivity implements View.OnClickListener{

    private Adapter_Handdown adaper;
    private ArrayList<MyDocument> data = null;
    private RecyclerView recycler_view;
    private RelativeLayout process;
    private RelativeLayout  mark;
    private ImageView back;
    private EditText search;
    private TextView title;
    private TextView banjie;
//    private ImageView home;
    private GovernmentApi api;
    private String state = "0";
    private String pageNo = "1";
    private String seartch = "";
    private int pageCount;
    private SwipeRefreshLayout swipe_layout;
    private Logging_Success bean;
    private ArrayList<Data_Handdown> list = new ArrayList<>();
    private ProgressBar not_loading;
    private int position_before=-1;
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private SharedPreferences amount_ShortcutBadger;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydocument);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        process=(RelativeLayout) findViewById(R.id.process);
        mark=(RelativeLayout) findViewById(R.id.mark);
        recycler_view=(RecyclerView) findViewById(R.id.recycler_view);
        back=(ImageView) findViewById(R.id.back);
        search=(EditText) findViewById(R.id.search);
        title=(TextView) findViewById(R.id.title);
        banjie=(TextView) findViewById(R.id.banjie);
        swipe_layout=(SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        not_loading=(ProgressBar) findViewById(R.id.not_loading);

//        search.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (keyCode == KeyEvent.KEYCODE_ENTER){
//
//                    refresh(state);
//
//                }
//                    return false;
//            }
//        });


        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // do something
                    refresh(state);

                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    return true;
                }
                return false;
            }
        });

        title.setText("公文下发");
        banjie.setText("仅显示已办结");
        process.setOnClickListener(this);
        mark.setOnClickListener(this);
        back.setOnClickListener(this);
        search.setOnClickListener(this);

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                position_before =-1;
                refresh(state);

            }
        });


        // 为RecyclerView设置默认动画和线性布局管理器
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        swipe_layout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        Mugen.with(recycler_view, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                not_loading.setVisibility(View.VISIBLE);
                loadmore();

            }

            @Override
            public boolean isLoading() {
                return not_loading.getVisibility()==View.VISIBLE;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return pageCount <= Integer.parseInt(pageNo);
            }
        }).start();



        adaper = new Adapter_Handdown(list);

        recycler_view.setAdapter(adaper);

       // 获取bean;
        String defString = PreferencesManager.getInstance(DocuHanddown_List_Actitivty.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        refresh("0");

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent2 =new Intent();
            setResult(20,intent2);
            Log.e("desfdasdf1111 ","dgfdsgdg");
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    void refresh(String stata){

        api= HttpClient.getInstance().getGovernmentApi();
        state=stata;
        pageNo="1";
        seartch = search.getText().toString();
        Call<HandDown> call= api.list_handdown(bean.getData().getOpId(),stata,seartch,pageNo);
        call.enqueue(new Callback<HandDown>() {
            @Override
            public void onResponse(Call<HandDown> call, Response<HandDown> response) {

                if(response.body()!= null){
                    pageCount = response.body().getPageCount();
                    pageNo = String.valueOf(response.body().getPageNo());
                    if(list != null && list.size()>0){
                        list.clear();
                    }
                    list = response.body().getData();
                    position_before=-1;
                    if(list != null && list.size()>0){
                        recycler_view.setAdapter(adaper);
                    }else{
                        recycler_view.setAdapter(adaper);
                        recycler_view.setVisibility(View.INVISIBLE);
                    }
                }else{
                    recycler_view.setVisibility(View.INVISIBLE);
                }
                swipe_layout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<HandDown> call, Throwable t) {
                Toast.makeText(DocuHanddown_List_Actitivty.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                swipe_layout.setRefreshing(false);
            }
        });

    }

    void loadmore(){

        api= HttpClient.getInstance().getGovernmentApi();
        pageNo=String.valueOf(Integer.parseInt(pageNo)+1);
        seartch = search.getText().toString();
        Call<HandDown> call= api.list_handdown(bean.getData().getOpId(),state,seartch,pageNo);
        call.enqueue(new Callback<HandDown>() {
            @Override
            public void onResponse(Call<HandDown> call, Response<HandDown> response) {
                if(response.body().getData()!= null){

                    pageCount =response.body().getPageCount();
                    pageNo= String.valueOf(response.body().getPageNo());
                    list.addAll(response.body().getData());
                    adaper.notifyDataSetChanged();
                }

                not_loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<HandDown> call, Throwable t) {
                Toast.makeText(DocuHanddown_List_Actitivty.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                not_loading.setVisibility(View.INVISIBLE);

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:

                Intent intent2 =new Intent();
                setResult(20,intent2);
                Log.e("desfdasdf1111 ","dgfdsgdg");
                finish();
                break;

            case R.id.process:
                search.setText("");
                if(!state.equals("1")){
                    if(list != null && list.size()>0){
                        list.clear();
                    }
                    adaper.notifyDataSetChanged();
                    process.setSelected(true);
                    mark.setSelected(false);
                    refresh("1");
                }else{
                    if(list != null && list.size()>0){
                        list.clear();
                    }
                    adaper.notifyDataSetChanged();
                    process.setSelected(false);
                    mark.setSelected(false);
                    refresh("0");

                }




                break;

            case R.id.mark:
                search.setText("");
                if(!state.equals("2")){

                    if(list != null && list.size()>0){
                        list.clear();
                    }
                    adaper.notifyDataSetChanged();
                    process.setSelected(false);
                    mark.setSelected(true);
                    refresh("2");
                }else{

                    if(list != null && list.size()>0){
                        list.clear();
                    }
                    adaper.notifyDataSetChanged();
                    process.setSelected(false);
                    mark.setSelected(false);
                    refresh("0");

                }


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==520){

            list.remove(position_before);
            position_before = -1;
            adaper.notifyDataSetChanged();
            return_amount();
//            adaper.notifyItemRemoved(position_before);
//            Log.e("的束缚舒服发", "remove" + position_before);
//            if (position_before != list.size()){
//                adaper.notifyItemChanged(position_before);
//            }


            //记录提交成功，改变标记，以便切换到待办时，刷新一下数据，获取最新的数据（去掉已经办理的数据条目）
            change_position = getSharedPreferences("summit_position", 0);
            editor=change_position.edit();
            editor.putBoolean("summit",true);
            editor.commit();

        }

    }


    void return_amount() {
        String defString3 = PreferencesManager.getInstance(DocuHanddown_List_Actitivty.this, "accountBean").get("jsonStr");
        Gson gson3 = new Gson();
        Logging_Success bean3 = gson3.fromJson(defString3, Logging_Success.class);
        GovernmentApi api3 = HttpClient.getInstance().getGovernmentApi();
        Call<Return_Amount> call2 = api3.return_amount_daiban(bean3.getData().getOpId());
        call2.enqueue(new Callback<Return_Amount>() {
            @Override
            public void onResponse(Call<Return_Amount> call, Response<Return_Amount> response) {
                if (response.body().getData() != null && !response.body().getData().equals("")) {

                    amount_ShortcutBadger = getSharedPreferences("amount_ShortcutBadger", 0);
                    Log.e("number",amount_ShortcutBadger.getInt("number",0)+"");
                    ShortcutBadger.applyCount(DocuHanddown_List_Actitivty.this, response.body().getData().getManage_num());
                    amount_ShortcutBadger.edit().putInt("number",response.body().getData().getManage_num()).commit();

                }
            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {
                Toast.makeText(DocuHanddown_List_Actitivty.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class Adapter_Handdown extends RecyclerView.Adapter<Adapter_Handdown.My_ViewHolder>{

        View view;
        private ArrayList<Data_Handdown> list_dats;

        public Adapter_Handdown(ArrayList<Data_Handdown> list_dats) {
            super();
            this.list_dats=list_dats;
        }

        @Override
        public My_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view=LayoutInflater.from(DocuHanddown_List_Actitivty.this).inflate(R.layout.item_document ,parent ,false);

            return new My_ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final My_ViewHolder holder, final int position) {

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), list.get(position).getIssuedTitle(), seartch);
            holder.theme.setText(spannableString);

            if(state.equals("0")){
               holder.time.setText(list.get(position).getIssuedDeptName());
               holder.date.setText("批准时间："+list.get(position).getOpCreateName());
               holder.status.setText("状态：等待下发");

            }else if(state.equals("1")){
                holder.time.setText(list.get(position).getIssuedDeptName());
                holder.date.setText("下发时间："+list.get(position).getOpCreateName());
                holder.status.setText("状态：正在办理");
                holder.status.setTextColor(getResources().getColor(R.color.blue));
            }else if(state.equals("2")){
                holder.time.setText("办结时间："+list.get(position).getOpCreateName());
                holder.status.setText("状态：已办结");
            }

//            if(holder.getAdapterPosition() == list.size()-1){
//                holder.decoration.setVisibility(View.INVISIBLE);
//            }else{
//                holder.decoration.setVisibility(View.VISIBLE);
//            }

            if (position_before != position) {
                holder.hasRead.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(position_before == -1){
                        list.get(position).setSelected(true);
                    }else if(position_before!=position){
                        list.get(position_before).setSelected(false);
                        adaper.notifyItemChanged(position_before);
                    }

                    holder.hasRead.setVisibility(View.VISIBLE);
                    position_before = position;

                    Intent intent =new Intent();
                    if(state.equals("0")){

                        intent =new Intent(DocuHanddown_List_Actitivty.this,DocuHanddown_Activity.class);
                        intent.putExtra("opId",list.get(position).getOpId());
                        intent.putExtra("opParent",list.get(position).getOpParent());
                        startActivityForResult(intent,9);
                    }else if(state.equals("1")){
                        intent =new Intent(DocuHanddown_List_Actitivty.this,DocuHnaddown_Status_Activity.class);
                        intent.putExtra("opId",list.get(position).getOpId());
                        intent.putExtra("opParent",list.get(position).getOpParent());
                        intent.putExtra("state","1");
                        startActivityForResult(intent,8);
                    }else if(state.equals("2")){
                        intent =new Intent(DocuHanddown_List_Actitivty.this,DocuHnaddown_Status_Activity.class);
                        intent.putExtra("opId",list.get(position).getOpId());
                        intent.putExtra("opParent",list.get(position).getOpParent());
                        intent.putExtra("state","2");
                        startActivity(intent);
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            if(list == null){
                recycler_view.setVisibility(View.INVISIBLE);
                return 0;
            }
            recycler_view.setVisibility(View.VISIBLE);
            return list.size();
        }

        class My_ViewHolder extends RecyclerView.ViewHolder{

            private TextView theme;
            private TextView time;
            private TextView status;
            private View hasRead;
            private TextView date;

            public My_ViewHolder(View itemView) {

                super(itemView);
                theme = (TextView) itemView.findViewById(R.id.theme);
                time = (TextView) itemView.findViewById(R.id.time);
                status = (TextView) itemView.findViewById(R.id.status);
                hasRead =  itemView.findViewById(R.id.hasRead);
                date = (TextView) itemView.findViewById(R.id.date);
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

    }



