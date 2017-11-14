package com.lanwei.governmentstar.activity.zyx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.SignUp_Document_Activity;
import com.lanwei.governmentstar.activity.gwnz.DocumentApproveActivity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_Activity;
import com.lanwei.governmentstar.activity.lll.DocumentFileHandleActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentHQActivity;
import com.lanwei.governmentstar.activity.lll.DocumentHandleActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentJDActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentQFActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentSHActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentSYActivity;
import com.lanwei.governmentstar.activity.lll.DocumentSelectActivity;
import com.lanwei.governmentstar.activity.lll.DocumentToDoActivity;
import com.lanwei.governmentstar.activity.lll.DocumentUndertakeActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalBxCsActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalBxSpActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalPayCsActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalPaySpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherActivityCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherActivitySpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherHyspCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherHyspSpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherReceptionCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherReceptionSpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherTyspCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherTyspSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutCarCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutCarSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutPublicCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutPublicSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutReimburseCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutReimburseSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonCadreCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonCadreSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonJobsCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonJobsSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonLeaveCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonLeaveSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOthersCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOthersSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOutCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOutSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectContractCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectContractSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectDepartmentCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectDepartmentSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectPointCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectPointSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectSpActivity;
import com.lanwei.governmentstar.activity.spsq.QjApplyCsActivity;
import com.lanwei.governmentstar.activity.spsq.QjApplySpActivity;
import com.lanwei.governmentstar.activity.spsq.SealLicenseCsActivity;
import com.lanwei.governmentstar.activity.spsq.SealLicenseSpActivity;
import com.lanwei.governmentstar.activity.spsq.SealOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.SealOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.SealUsesealCsActivity;
import com.lanwei.governmentstar.activity.spsq.SealUsesealSpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsCustomPurchaseCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsCustomPurchaseSpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsOtherPurchaseCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsOtherPurchaseSpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsPurchaseApplyCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsPurchaseApplySpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsReceiveCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsReceiveSpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsRentCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsRentSpActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Wait;
import com.lanwei.governmentstar.bean.Wait_Item;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.receiver.MyReceiver;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/6.
 */

public class WaitFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Activity activity;
    private RecyclerView rv;
    private Return_Wait return_wait;
    private ArrayList<Wait_Item> datas_list = new ArrayList<>();
    private ArrayList<Wait_Item> datas_list_init = new ArrayList<>();
    private Adapter_Wait adapter_wait;
    private String userId;
    // 默认降序
    private String order = "desc";
    private int position_now;
    private int all;
    private ProgressBar pb;
    private GovernmentApi api;
    private int position_click = 0;
    private SwipeRefreshLayout swipe_layout;
    private String present;



//    private boolean mIsRefreshing = false;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @SuppressLint("ValidFragment")
    public WaitFragment(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("ValidFragment")
    public WaitFragment() {
    }

    // 下拉刷新SwipeRefreshLayout,上拉加载mugen

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wait_fragment, container, false);
        rv = (RecyclerView) view.findViewById(R.id.wait_recycleview);
        swipe_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);

        pb = (ProgressBar) view.findViewById(R.id.pb);
        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipe_layout.setOnRefreshListener(this);
        swipe_layout.setColorSchemeColors(getResources().getColor(R.color.theme));

        adapter_wait = new Adapter_Wait();
        rv.setAdapter(adapter_wait);
        // 解析登陆成功的实体类
        String defString = PreferencesManager.getInstance(getActivity(), "accountBean").get("jsonStr");
        Gson gson = new Gson();
        Logging_Success bean = gson.fromJson(defString, Logging_Success.class);

        // 获取opId和userId
        userId = bean.getData().getOpId();

        // 初始化数据，网络请求
        api = HttpClient.getInstance().getGovernmentApi();
        Call<Return_Wait> call = api.Wait(userId, "1", order);
        ((HomeActivity)getActivity()).return_amount();
        call.enqueue(new Callback<Return_Wait>() {
            @Override
            public void onResponse(Call<Return_Wait> call, Response<Return_Wait> response) {
                if(response.body().getOtherLogin().equals("1")){
                    ((HomeActivity) getActivity()).shift_user.setVisibility(View.VISIBLE);
                }else{
                    ((HomeActivity) getActivity()).shift_user.setVisibility(View.GONE);
                }
                if (response.body().getPageCount() != 0) {
                    position_now = response.body().getPageNo();
                    all = response.body().getPageCount();
                    return_wait = response.body();
                    datas_list = response.body().getData();
                    Log.e("市场的是长发短发cdc的", "onResponse: " + response.body().getOtherLogin());

                    for(int i=0;i<datas_list.size();i++){
                        if(datas_list.get(i).getDocState().equals("swcy") ||datas_list.get(i).getDocState().equals("gwcy")||datas_list.get(i).getDocState().equals("gwqs") || datas_list.get(i).getDocState().equals("gwnz") || datas_list.get(i).getDocState().equals("spsq") || datas_list.get(i).getDocState().equals("gwxf")){
                            datas_list_init.add(datas_list.get(i));
                        }
                    }

                    rv.setAdapter(adapter_wait);
                    pb.setVisibility(View.INVISIBLE);
                    adapter_wait.notifyDataSetChanged();
                } else {
                    rv.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<Return_Wait> call, Throwable t) {

                Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });

        // Mugen的用法
        Mugen.with(rv, new MugenCallbacks() {

            // 加载更多

            @Override
            public void onLoadMore() {
                pb.setVisibility(View.VISIBLE);

                api = HttpClient.getInstance().getGovernmentApi();
                Call<Return_Wait> call = api.Wait(userId, (position_now + 1) + "", order);

                call.enqueue(new Callback<Return_Wait>() {
                    @Override
                    public void onResponse(Call<Return_Wait> call, Response<Return_Wait> response) {

                        if (response.body().getPageNo() != 0) {
                            position_now = response.body().getPageNo();
                            all = response.body().getPageCount();
                            return_wait = response.body();
                            datas_list.addAll(response.body().getData());

                            for(int j=0;j<response.body().getData().size();j++){
                                if(response.body().getData().get(j).getDocState().equals("swcy")||datas_list.get(j).getDocState().equals("gwcy")||datas_list.get(j).getDocState().equals("gwqs")||datas_list.get(j).getDocState().equals("spsq") || response.body().getData().get(j).getDocState().equals("gwnz") || response.body().getData().get(j).getDocState().equals("gwxf")){
                                    datas_list_init.add(response.body().getData().get(j));

                                }

                            }

                            Log.e("市场的是长发短发cdc的", "onResponse: " + datas_list.get(0).getDocCompany());
                            pb.setVisibility(View.INVISIBLE);
                            adapter_wait.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "没有更多了....", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Return_Wait> call, Throwable t) {
                        Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            // 是否正在加载数据，注意标记的转换
            @Override
            public boolean isLoading() {

                boolean isLoading;
                if (pb.getVisibility() == View.INVISIBLE) {
                    isLoading = false;
                } else {
                    isLoading = true;
                }

                return isLoading;
            }

            // 分页加载，是否后台已经没有后台数据了？
            @Override
            public boolean hasLoadedAllItems() {
                return all <= position_now;
            }
        }).start();


        // todo  网络请求获取数据

        return view;
    }


    // 从列表处理数据提交成功后，返回到待办，我们要去掉已经处理过的数据条目，通过从列表进去处理成功后改变isTrue的标记来精确控制
    @Override
    public void onResume() {
        super.onResume();

//        change_position = getActivity().getSharedPreferences("summit_position", 0);
//        editor = change_position.edit();
//        Boolean isTrue = change_position.getBoolean("summit", false);
//        Log.e("十分十分出色的v", "" + isTrue);
//
//        if (isTrue) {
//
//            Log.e("十分十分出色的v", "" + isTrue);
//            onRefresh();
//            editor.putBoolean("summit", false);
//            editor.putBoolean("allow", true);
//            editor.commit();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 520) {
            datas_list_init.remove(position_click);

            if(datas_list_init.size()<5 && all>1){
                onRefresh();
            }else{
                adapter_wait.notifyDataSetChanged();
            }

            ((HomeActivity)getActivity()).return_amount();
            Intent intent = new Intent(getActivity(),MyReceiver.class);
            getActivity().startService(intent);
        }else if (resultCode == 0) {

        }else if(resultCode == 530 || resultCode == 6 || resultCode == 444){

            datas_list_init.remove(position_click);

            if(datas_list_init.size()<5 && all>1){
                onRefresh();
            }else{
                adapter_wait.notifyDataSetChanged();
            }

            ((HomeActivity)getActivity()).return_amount();
            Intent intent = new Intent(getActivity(),MyReceiver.class);
            getActivity().startService(intent);
        }
    }

    @Override
    public void onRefresh() {
//        mIsRefreshing = true;
        String defString = PreferencesManager.getInstance(getActivity(), "accountBean").get("jsonStr");
        Gson gson = new Gson();
        Logging_Success bean = gson.fromJson(defString, Logging_Success.class);
        userId = bean.getData().getOpId();
        rv.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).return_amount();
        Call<Return_Wait> call = api.Wait(userId, "1", order);
        call.enqueue(new Callback<Return_Wait>() {
            @Override
            public void onResponse(Call<Return_Wait> call, final Response<Return_Wait> response) {

                if (response.body().getPageCount() != 0) {
                    position_now = response.body().getPageNo();
                    all = response.body().getPageCount();
                    datas_list.clear();
                    return_wait = response.body();
                    datas_list = response.body().getData();
                    Log.e("市场的是长发短发cdc的", "onResponse: " + datas_list.get(0).getDocCompany());

                    if(datas_list_init.size()>0){
                        datas_list_init.clear();
                    }

                    for(int i=0;i<datas_list.size();i++){
                        if(datas_list.get(i).getDocState().equals("swcy") ||datas_list.get(i).getDocState().equals("gwcy")||datas_list.get(i).getDocState().equals("gwqs")||datas_list.get(i).getDocState().equals("spsq") || datas_list.get(i).getDocState().equals("gwnz") || datas_list.get(i).getDocState().equals("gwxf")){
                            datas_list_init.add(datas_list.get(i));

                        }
                    }

                    adapter_wait = new Adapter_Wait();
                    rv.setAdapter(adapter_wait);
                    pb.setVisibility(View.INVISIBLE);
                    adapter_wait.notifyDataSetChanged();
                    swipe_layout.setRefreshing(false);
//                    mIsRefreshing = false;

                } else {
                    datas_list_init.clear();
                    datas_list.clear();
                    rv.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                    swipe_layout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Return_Wait> call, Throwable t) {
                swipe_layout.setRefreshing(false);
                datas_list.clear();
                datas_list_init.clear();
                Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 待处理条目的adapter
    class Adapter_Wait extends RecyclerView.Adapter<Adapter_Wait.MyViewHolder> {

        private View view = null;

        public Adapter_Wait() {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getActivity().getLayoutInflater().inflate(R.layout.item_drawer, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            if(datas_list_init.get(position).getDocState().equals("swcy") || datas_list_init.get(position).getDocState().equals("gwnz")){

                holder.addition.setText("附件：" + datas_list_init.get(position).getDocFileNum());
                holder.classify.setText("分类：" + datas_list_init.get(position).getDocClassify());
                holder.title.setText(datas_list_init.get(position).getDocTitle());
                holder.number.setText("编号：" + datas_list_init.get(position).getDocCode());
                holder.classify.setVisibility(View.VISIBLE);
                holder.number.setVisibility(View.VISIBLE);
                holder.department_from.setVisibility(View.VISIBLE);
                holder.line.setVisibility(View.VISIBLE);
            }else if(datas_list_init.get(position).getDocState().equals("gwxf")){

                holder.classify.setVisibility(View.GONE);
                holder.number.setVisibility(View.GONE);
                holder.urgent.setVisibility(View.GONE);
                holder.line.setVisibility(View.GONE);
                holder.department_from.setVisibility(View.VISIBLE);
                holder.header.setImageDrawable(getResources().getDrawable(R.drawable.xf));
                holder.theme.setText("状态 ：等待下发");
                holder.addition.setText("附件：" + datas_list_init.get(position).getDocFileNum()+"");
                holder.title.setText(datas_list_init.get(position).getDocTitle());
                holder.date_receive.setText("接收时间: " + datas_list_init.get(position).getDocTime()+"");
                holder.department_from.setText("来文单位：" + datas_list_init.get(position).getDocCompany());
                holder.hang.setBackground(getResources().getDrawable(R.drawable.item_header_gwxf));
            }else if(datas_list_init.get(position).getDocState().equals("spsq")){

                if(datas_list_init.get(position).getOpState().equals("0")){
                    holder.theme.setText("状态 ：等待审定");
                }else if(datas_list_init.get(position).getOpState().equals("1")){
                    holder.theme.setText("状态 ：等待审核");
                }else if(datas_list_init.get(position).getOpState().equals("2")){
                    holder.theme.setText("状态 ：等待审批");
                }else if(datas_list_init.get(position).getOpState().equals("3")){
                    holder.theme.setText("状态 ：等待查看");
                }

                holder.number.setText("编号：" + datas_list_init.get(position).getDocCode());
                holder.title.setText(datas_list_init.get(position).getDocTitle());
                holder.classify.setText("分类：" + datas_list_init.get(position).getDocClassify());
                holder.addition.setText("附件：" + datas_list_init.get(position).getDocFileNum());
                holder.date_receive.setText("申请日期: " + datas_list_init.get(position).getDocTime());
                holder.department_from.setVisibility(View.GONE);
                holder.header.setImageDrawable(getResources().getDrawable(R.drawable.spsq));
                holder.hang.setBackground(getResources().getDrawable(R.drawable.item_header_spsq));

            }else if(datas_list_init.get(position).getDocState().equals("gwcy") || datas_list_init.get(position).getDocState().equals("gwqs")){

                holder.classify.setVisibility(View.GONE);
                holder.number.setVisibility(View.GONE);
                holder.urgent.setVisibility(View.GONE);
                holder.line.setVisibility(View.GONE);
                holder.department_from.setVisibility(View.VISIBLE);
                holder.theme.setText("状态 ：等待签收");
                holder.addition.setText("附件：" + datas_list_init.get(position).getDocFileNum()+"");
                holder.title.setText(datas_list_init.get(position).getDocTitle());
                holder.date_receive.setText("接收时间: " + datas_list_init.get(position).getDocTime()+"");
                holder.department_from.setText("来文单位：" + datas_list_init.get(position).getDocCompany());
                holder.header.setImageDrawable(getResources().getDrawable(R.drawable.ddqs));
                holder.hang.setBackground(getResources().getDrawable(R.drawable.item_header_ddqs));


            }


            // 判断是收文传阅还是公文拟制
            if (datas_list_init.get(position).getDocState().equals("swcy")) {
                holder.date_receive.setText("收文日期: " + datas_list_init.get(position).getDocTime());
                holder.department_from.setText("来文单位：" + datas_list_init.get(position).getDocCompany());
                holder.header.setImageDrawable(getResources().getDrawable(R.drawable.cy));
                holder.hang.setBackground(getResources().getDrawable(R.drawable.item_header_swcy));

                if(datas_list_init.get(position).getDocMatter().equals("1")){
                    holder.urgent.setVisibility(View.VISIBLE);
                }else{
                    holder.urgent.setVisibility(View.INVISIBLE);
                }

                switch (datas_list_init.get(position).getOpState()) {

                    case "qs":
                        holder.theme.setText("状态 ：等待签收");
                        break;
                    case "nb":
                        holder.theme.setText("状态 ：等待拟办");
                        break;
                    case "ps":
                        holder.theme.setText("状态 ：等待批示");
                        break;
                    case "yb":
                        holder.theme.setText("状态 ：等待阅办");
                        break;
                    case "cb":
                        holder.theme.setText("状态 ：等待承办");
                        break;
                    case "bs":
                        holder.theme.setText("状态 ：等待办理");
                        break;
                    case "zf":
                        holder.theme.setText("状态 ：等待转办");
                        break;
                    case "gd":
                        holder.theme.setText("状态 ：等待归档");
                        break;
                    case "xb":
                        holder.theme.setText("状态 ：等待协办");
                        break;
                    default:
                        holder.theme.setText("状态 ：已完成");
                        break;

                }

            } else if (datas_list_init.get(position).getDocState().equals("gwnz")) {
                holder.date_receive.setText("起草日期：" + datas_list_init.get(position).getDocTime());
                holder.department_from.setText("起草人：" + datas_list_init.get(position).getDocName());
                holder.header.setImageDrawable(getResources().getDrawable(R.drawable.nb));
                holder.hang.setBackground(getResources().getDrawable(R.drawable.item_header_gwnz));
                holder.urgent.setVisibility(View.INVISIBLE);
                    switch (Integer.parseInt(datas_list_init.get(position).getOpState())) {

                    case 0:
                        holder.theme.setText("状态 ：等待起草");
                        break;

                    case 1:
                        holder.theme.setText("状态 ：等待审核");

                        break;

                    case 2:
                        holder.theme.setText("状态 ：等待审阅");
                        break;

                    case 3:
                        holder.theme.setText("状态 ：等待校对");
                        break;

                    case 4:
                        holder.theme.setText("状态 ：等待签发");
                        break;

                    case 5:
                        holder.theme.setText("状态 ：等待会签");
                        break;

                    case 6:
                        holder.theme.setText("状态 ：等待核发");
                        break;

                    case 7:
                        holder.theme.setText("状态 ：等待归档");
                        break;

                    default:
                        holder.theme.setText("状态 ：已完成");
                        break;

                }

            }

            // item的点击事件
            holder.layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = null;
                    // 记录点击的条目，以便处理完成后，以便移除这个条目
                    position_click = position;

                    if (datas_list_init.get(position).getDocState().equals("gwnz")) {

//                        change_position = getActivity().getSharedPreferences("summit_position", 0);
//                        editor = change_position.edit();
//                        editor.putBoolean("isOpen", true);
//                        editor.commit();

                        switch (Integer.parseInt(datas_list_init.get(position).getOpState())) {

                            case 1:   // 审核
                                intent = new Intent(getActivity(), DocumentSHActivity.class);
                                intent.putExtra("opId", datas_list_init.get(position).getOpId());
                                present = "1";
                                intent.putExtra("state","审核");
                                break;

                            case 2:   // 审阅
                                intent = new Intent(getActivity(), DocumentSYActivity.class);
                                intent.putExtra("opId", datas_list_init.get(position).getOpId());
                                present = "2";
                                intent.putExtra("state","审阅");
                                break;

                            case 3:  // 校对
                                intent = new Intent(getActivity(), DocumentJDActivity.class);
                                intent.putExtra("opId", datas_list_init.get(position).getOpId());
                                present = "3";
                                intent.putExtra("state","校对");
                                break;

                            case 4:   // 签发
                                intent = new Intent(getActivity(), DocumentQFActivity.class);
                                intent.putExtra("opId", datas_list_init.get(position).getOpId());
                                present = "4";
                                intent.putExtra("state","签发");
                                break;

                            case 5:    // 会签
                                intent = new Intent(getActivity(), DocumentHQActivity.class);
                                intent.putExtra("opId", datas_list_init.get(position).getOpId());
                                present = "5";
                                intent.putExtra("state","会签");
                                break;

                            case 6:   // 核发
                                intent = new Intent(getActivity(), DocumentApproveActivity.class);
                                intent.putExtra("opId", datas_list_init.get(position).getOpId());
                                present = "6";
                                intent.putExtra("state","核发");
                                break;

                            default:
                                //
                                intent =new Intent();
                                break;

                        }
                        if (intent != null) {
                            intent.putExtra("NBopId", datas_list_init.get(position).getOpId());
                            intent.putExtra("DocStatus", datas_list_init.get(position).getDocStatus());
                            intent.putExtra("present", present);
                        }
                        startActivityForResult(intent, 3);
//                        startActivity(intent);

                    } else if (datas_list_init.get(position).getDocState().equals("swcy")) {


//                        // 从待办进入处理界面，不用执行那个方法体，关闭标志值
//                        change_position = getActivity().getSharedPreferences("summit_position", 0);
//                        editor = change_position.edit();
//                        editor.putBoolean("allow", false);
//                        editor.commit();

                        // 根据状态进行跳转
                        switch (datas_list_init.get(position).getOpState()) {

                            case "nb":  // 拟办
                                intent = new Intent(getActivity(), DocumentToDoActivity.class);
                                present = "1";
                                intent.putExtra("state","拟办");
                                break;

                            case "ps":  // 批示
                                intent = new Intent(getActivity(), DocumentHandleActivity.class);
                                intent.putExtra("type", "2");
                                intent.putExtra("tjms", "cycl");
                                intent.putExtra("state","批示");
                                present = "2";
                                break;

                            case "yb":  // 阅办
                                // 跳转之后做标记代做
                                intent = new Intent(getActivity(), DocumentUndertakeActivity.class);
                                intent.putExtra("type", "1");
                                intent.putExtra("state","阅办");
                                present = "3";
                                break;

                            case "cb":  // 承办

                                intent = new Intent(getActivity(), DocumentUndertakeActivity.class);
                                intent.putExtra("type", "0");
                                intent.putExtra("type5", "chengban");
                                intent.putExtra("state","承办");
                                present = "4";
                                break;

                            case "bs":  // 办理
                                intent = new Intent(getActivity(), DocumentFileHandleActivity.class);
                                intent.putExtra("type5", "banli");
                                intent.putExtra("state","办事");
                                intent.putExtra("OpState", "5");
                                present = "5";
                                break;

                            case "zf":  // 转发
                                intent = new Intent(getActivity(), DocumentSelectActivity.class);
                                intent.putExtra("type5", "zhuanfa");
                                intent.putExtra("state","转发");
                                present = "5";
                                break;

                            case "xb":  // 协办
                                intent = new Intent(getActivity(), DocumentSelectActivity.class);
                                intent.putExtra("type5", "xieban");
                                intent.putExtra("state","协办");
                                present = "5";
                                break;

                            default:
                                intent = new Intent();
                                break;
                        }

                        if (intent != null) {
                            intent.putExtra("opId", datas_list_init.get(position).getOpId());
                            intent.putExtra("DocStatus", datas_list_init.get(position).getDocStatus());
                            intent.putExtra("present", present);
                        }
                        startActivityForResult(intent, 3);

                    }else if(datas_list_init.get(position).getDocState().equals("gwxf")){
                        intent =new Intent(getActivity(), DocuHanddown_Activity.class);
                        intent.putExtra("opId", datas_list_init.get(position).getOpId());
                        intent.putExtra("opParent", datas_list_init.get(position).getDocName());
                        startActivityForResult(intent,3);

                    }else if(datas_list_init.get(position).getDocState().equals("gwcy") || datas_list_init.get(position).getDocState().equals("gwqs")){

                        intent =new Intent(getActivity(), SignUp_Document_Activity.class);
                        intent.putExtra("opId", datas_list_init.get(position).getOpId());
                        intent.putExtra("type", datas_list_init.get(position).getDocState());
                        intent.putExtra("opParent", datas_list_init.get(position).getDocName());
                        startActivityForResult(intent,3);

                    }else if(datas_list_init.get(position).getDocState().equals("spsq")){

                        Intent intent2 = new Intent();
                        switch (datas_list_init.get(position).getOpState()){

                             case "0":
                             case "1":
                             case "2":
                                 intent = intentSp(intent2, datas_list_init.get(position).getDocClassify() ,"1");  //审定、审批人界面跳转
                                break;

                            case "3":
                                intent = intentCs(intent2, datas_list_init.get(position).getDocClassify() ,"0");   //查看人界面跳转
                                datas_list_init.remove(position);
                                adapter_wait.notifyDataSetChanged();
                                break;
                        }
                        if (intent != null){
                            intent.putExtra("opId", datas_list_init.get(position).getOpId());
                            intent.putExtra("userId", userId);
                            startActivityForResult(intent,6);
                        }


                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return datas_list_init.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private ImageView header;
            private TextView theme;
            private TextView date_receive;
            private TextView title;
            private TextView addition;
            private TextView department_from;
            private TextView number;
            private TextView classify;
            private LinearLayout hang;
            private LinearLayout layout_item;
            private ImageView urgent;
            private View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                header = (ImageView) view.findViewById(R.id.header);
                urgent = (ImageView) view.findViewById(R.id.urgent);
                theme = (TextView) view.findViewById(R.id.theme);
                date_receive = (TextView) view.findViewById(R.id.date_receive);
                title = (TextView) view.findViewById(R.id.title);
                department_from = (TextView) view.findViewById(R.id.department_from);
                classify = (TextView) view.findViewById(R.id.classify);
                number = (TextView) view.findViewById(R.id.number);
                addition = (TextView) view.findViewById(R.id.addition);
                hang = (LinearLayout) view.findViewById(R.id.hang);
                layout_item = (LinearLayout) view.findViewById(R.id.layout_item);
                line =  view.findViewById(R.id.line);
            }
        }
    }


    private Intent intentSp(Intent intent, String opTypeName ,String opState) {
        switch (opTypeName) {
            case "资质印章-其他":
                intent = new Intent(activity, SealOtherSpActivity.class);
                break;
            case "资质印章-机构证照申请":
                intent = new Intent(activity, SealLicenseSpActivity.class);
                break;
            case "资质印章-用印申请":
                intent = new Intent(activity, SealUsesealSpActivity.class);
                break;


            case "项目申请-其他":
                intent = new Intent(activity, ProjectOtherSpActivity.class);
                break;
            case "项目申请-立项申请":
                intent = new Intent(activity, ProjectSpActivity.class);
                break;
            case "项目申请-合同审批":
                intent = new Intent(activity, ProjectContractSpActivity.class);
                break;
            case "项目申请-工作指示":
                intent = new Intent(activity, ProjectPointSpActivity.class);
                break;
            case "项目申请-部门协作":
                intent = new Intent(activity, ProjectDepartmentSpActivity.class);
                break;

            case "资金申请-付款申请":
                intent = new Intent(activity, CapitalPaySpActivity.class);
                break;
            case "资金申请-备用金申请":
                intent = new Intent(activity, CapitalOtherSpActivity.class);
                break;
            case "资金申请-报销申请":
                intent = new Intent(activity, CapitalBxSpActivity.class);
                break;


            case "请假申请-病假":
            case "请假申请-事假":
            case "请假申请-婚假":
            case "请假申请-丧假":
            case "请假申请-产假":
            case "请假申请-年休假":
                intent = new Intent(activity, QjApplySpActivity.class);
                break;

            case "人事申请-其他":
                intent = new Intent(activity, PersonOthersSpActivity.class);
                break;
            case "人事申请-招聘申请":
                intent = new Intent(activity, PersonJobsSpActivity.class);
                break;
            case "人事申请-调派申请":
                intent = new Intent(activity, PersonOutSpActivity.class);
                break;
            case "人事申请-离职申请":
                intent = new Intent(activity, PersonLeaveSpActivity.class);
                break;
            case "人事申请-后备干部申请":
                intent = new Intent(activity, PersonCadreSpActivity.class);
                break;

            case "外出申请-其他":
                intent = new Intent(activity, OutOtherSpActivity.class);
                break;
            case "外出申请-用车申请":
                intent = new Intent(activity, OutCarSpActivity.class);
                break;
            case "外出申请-执行公务申请":
                intent = new Intent(activity, OutPublicSpActivity.class);
                break;
            case "外出申请-出差报销申请":
                intent = new Intent(activity, OutReimburseSpActivity.class);
                break;


            case "物品申请-租用申请":
                intent = new Intent(activity, ThingsRentSpActivity.class);
                break;
            case "物品申请-其他采购":
                intent = new Intent(activity, ThingsOtherPurchaseSpActivity.class);
                break;
            case "物品申请-物品领用":
                intent = new Intent(activity, ThingsReceiveSpActivity.class);
                break;
            case "物品申请-申请采购":
                intent = new Intent(activity, ThingsPurchaseApplySpActivity.class);
                break;
            case "物品申请-定制采购":
                intent = new Intent(activity, ThingsCustomPurchaseSpActivity.class);
                break;

            case "其他申请-通用审批":
                intent = new Intent(activity, OtherTyspSpActivity.class);
                break;
            case "其他申请-接待申请":
                intent = new Intent(activity, OtherReceptionSpActivity.class);
                break;
            case "其他申请-活动申请":
                intent = new Intent(activity, OtherActivitySpActivity.class);
                break;
            case "其他申请-会议审批":
                intent = new Intent(activity, OtherHyspSpActivity.class);
                break;
        }
        intent.putExtra("type",opState);
        return intent;
    }

    private Intent intentCs(Intent intent, String opTypeName ,String opState) {
        switch (opTypeName) {
            case "资质印章-其他":
                intent = new Intent(activity, SealOtherCsActivity.class);
                break;
            case "资质印章-机构证照申请":
                intent = new Intent(activity, SealLicenseCsActivity.class);
                break;
            case "资质印章-用印申请":
                intent = new Intent(activity, SealUsesealCsActivity.class);
                break;

            case "项目申请-其他":
                intent = new Intent(activity, ProjectOtherCsActivity.class);
                break;
            case "项目申请-立项申请":
                intent = new Intent(activity, ProjectCsActivity.class);
                break;
            case "项目申请-合同审批":
                intent = new Intent(activity, ProjectContractCsActivity.class);
                break;
            case "项目申请-工作指示":
                intent = new Intent(activity, ProjectPointCsActivity.class);
                break;
            case "项目申请-部门协作":
                intent = new Intent(activity, ProjectDepartmentCsActivity.class);
                break;


            case "资金申请-付款申请":
                intent = new Intent(activity, CapitalPayCsActivity.class);
                break;
            case "资金申请-备用金申请":
                intent = new Intent(activity, CapitalOtherCsActivity.class);
                break;
            case "资金申请-报销申请":
                intent = new Intent(activity, CapitalBxCsActivity.class);
                break;

            case "请假申请-病假":
            case "请假申请-事假":
            case "请假申请-婚假":
            case "请假申请-丧假":
            case "请假申请-产假":
            case "请假申请-年休假":
                intent = new Intent(activity, QjApplyCsActivity.class);
                break;

            case "人事申请-其他":
                intent = new Intent(activity, PersonOthersCsActivity.class);
                break;

            case "人事申请-招聘申请":
                intent = new Intent(activity, PersonJobsCsActivity.class);
                break;
            case "人事申请-调派申请":
                intent = new Intent(activity, PersonOutCsActivity.class);
                break;
            case "人事申请-离职申请":
                intent = new Intent(activity, PersonLeaveCsActivity.class);
                break;
            case "人事申请-后备干部申请":
                intent = new Intent(activity, PersonCadreCsActivity.class);
                break;

            case "外出申请-其他":
                intent = new Intent(activity, OutOtherCsActivity.class);
                break;
            case "外出申请-用车申请":
                intent = new Intent(activity, OutCarCsActivity.class);
                break;
            case "外出申请-公务出行申请":
                intent = new Intent(activity, OutPublicCsActivity.class);
                break;
            case "外出申请-出差报销申请":
                intent = new Intent(activity, OutReimburseCsActivity.class);
                break;


            case "物品申请-租用申请":
                intent = new Intent(activity, ThingsRentCsActivity.class);
                break;
            case "物品申请-其他采购":
                intent = new Intent(activity, ThingsOtherPurchaseCsActivity.class);
                break;
            case "物品申请-物品领用":
                intent = new Intent(activity, ThingsReceiveCsActivity.class);
                break;
            case "物品申请-申请采购":
                intent = new Intent(activity, ThingsPurchaseApplyCsActivity.class);
                break;
            case "物品申请-定制采购":
                intent = new Intent(activity, ThingsCustomPurchaseCsActivity.class);
                break;

            case "其他申请-通用审批":
                intent = new Intent(activity, OtherTyspCsActivity.class);
                break;
            case "其他申请-接待申请":
                intent = new Intent(activity, OtherReceptionCsActivity.class);
                break;
            case "其他申请-活动申请":
                intent = new Intent(activity, OtherActivityCsActivity.class);
                break;
            case "其他申请-会议审批":
                intent = new Intent(activity, OtherHyspCsActivity.class);
                break;
        }
        intent.putExtra("type",opState);
        return intent;
    }



    // 暴露给HomeActivity的方法体，排序的刷新数据
    public void initData(String order_again) {

        api = HttpClient.getInstance().getGovernmentApi();
        order = order_again;
        Call<Return_Wait> call = api.Wait(userId, 1 + "", order_again);
        rv.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Return_Wait>() {
            @Override
            public void onResponse(Call<Return_Wait> call, Response<Return_Wait> response) {

                if (response.body().getPageCount() != 0) {
                    datas_list.clear();
                    datas_list_init.clear();
                    position_now = response.body().getPageNo();
                    all = response.body().getPageCount();
                    return_wait = response.body();
                    datas_list = response.body().getData();

                    for(int i=0;i<datas_list.size();i++){
                        if(datas_list.get(i).getDocState().equals("swcy") || datas_list.get(i).getDocState().equals("spsq") || datas_list.get(i).getDocState().equals("gwnz") || datas_list.get(i).getDocState().equals("gwxf")){

                            datas_list_init.add(datas_list.get(i));
                        }
                    }

                    adapter_wait = new Adapter_Wait();
                    rv.setAdapter(adapter_wait);
                    adapter_wait.notifyDataSetChanged();
                } else {
                    datas_list.clear();
                    datas_list_init.clear();
                }
            }

            @Override
            public void onFailure(Call<Return_Wait> call, Throwable t) {
                datas_list.clear();
                datas_list_init.clear();
                Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
