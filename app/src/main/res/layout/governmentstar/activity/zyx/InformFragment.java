package com.lanwei.governmentstar.activity.zyx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.lanwei.governmentstar.activity.Watch_Notification_Activity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Notification_Item02;
import com.lanwei.governmentstar.bean.Return_Notification;
import com.lanwei.governmentstar.bean.Return_Wait;
import com.lanwei.governmentstar.bean.Wait_Item;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
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

public class InformFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private Activity activity;
    private RecyclerView rv;
    private Return_Notification return_wait;
    private ArrayList<Notification_Item02> datas_list = new ArrayList<>();
    private Adapter_Notice adapter_wait;
    private String userId;
    // 默认降序
    private String order = "desc";
    private int position_now;
    private int all;
    private ProgressBar pb;
    private GovernmentApi api;
    private int position_click = 0;
    private SwipeRefreshLayout swipe_layout;
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private String present;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @SuppressLint("ValidFragment")
    public InformFragment(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("ValidFragment")
    public InformFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inform_fragment, container, false);

        rv = (RecyclerView) view.findViewById(R.id.wait_recycleview);
        swipe_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);

        pb = (ProgressBar) view.findViewById(R.id.pb);
        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipe_layout.setOnRefreshListener(this);
        swipe_layout.setColorSchemeColors(getResources().getColor(R.color.theme));

        adapter_wait = new Adapter_Notice();
        rv.setAdapter(adapter_wait);
        // 解析登陆成功的实体类
        String defString = PreferencesManager.getInstance(getActivity(), "accountBean").get("jsonStr");
        Gson gson = new Gson();
        Logging_Success bean = gson.fromJson(defString, Logging_Success.class);

        // 获取opId和userId
        userId = bean.getData().getOpId();

        // 初始化数据，网络请求
        api = HttpClient.getInstance().getGovernmentApi();
        Call<Return_Notification> call = api.Notification(userId, "1", order);

        call.enqueue(new Callback<Return_Notification>() {
            @Override
            public void onResponse(Call<Return_Notification> call, Response<Return_Notification> response) {

                if (response.body().getPageCount() != 0) {
                    position_now = response.body().getPageNo();
                    all = response.body().getPageCount();
                    return_wait = response.body();
                    datas_list = response.body().getData();
                    rv.setAdapter(adapter_wait);
                    pb.setVisibility(View.INVISIBLE);
                    adapter_wait.notifyDataSetChanged();
                } else {
                    rv.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<Return_Notification> call, Throwable t) {

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
                Call<Return_Notification> call = api.Notification(userId, (position_now + 1) + "", order);

                call.enqueue(new Callback<Return_Notification>() {
                    @Override
                    public void onResponse(Call<Return_Notification> call, Response<Return_Notification> response) {

                        if (response.body().getPageNo() != 0) {
                            position_now = response.body().getPageNo();
                            all = response.body().getPageCount();
                            return_wait = response.body();
                            datas_list.addAll(response.body().getData());

                            pb.setVisibility(View.INVISIBLE);
                            adapter_wait.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "没有更多了....", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Return_Notification> call, Throwable t) {
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



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 520) {
            datas_list.remove(position_click);
            adapter_wait.notifyDataSetChanged();
//            adapter_wait.notifyItemRemoved(position_click);
//            Log.e("的束缚舒服发", "remove" + position_click);
//            if (position_click != datas_list.size()){
//                adapter_wait.notifyItemChanged(position_click,datas_list.size()-position_click);
//            }
//            ((HomeActivity)getActivity()).return_amount();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        change_position = getActivity().getSharedPreferences("commit_position", 0);
        editor = change_position.edit();
        Boolean isTrue = change_position.getBoolean("commit", false);
        Log.e("十分十分出色的v", "" + isTrue);

        if (isTrue) {

            Log.e("十分十分出色的v", "" + isTrue);
            onRefresh();
            editor.putBoolean("commit", false);
            editor.commit();
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
        Call<Return_Notification> call = api.Notification(userId, "1", order);
        call.enqueue(new Callback<Return_Notification>() {
            @Override
            public void onResponse(Call<Return_Notification> call, final Response<Return_Notification> response) {

                if (response.body().getPageCount() != 0) {
                    position_now = response.body().getPageNo();
                    all = response.body().getPageCount();
                    datas_list.clear();
                    return_wait = response.body();
                    datas_list = response.body().getData();

                    adapter_wait = new Adapter_Notice();
                    rv.setAdapter(adapter_wait);
                    pb.setVisibility(View.INVISIBLE);
                    adapter_wait.notifyDataSetChanged();
                    swipe_layout.setRefreshing(false);
//                    mIsRefreshing = false;

                } else {
                    datas_list.clear();
                    rv.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                    swipe_layout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Return_Notification> call, Throwable t) {
                swipe_layout.setRefreshing(false);
                datas_list.clear();
                Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });

    }


    class  Adapter_Notice extends RecyclerView.Adapter<Adapter_Notice.ViewHolder_Notice>{


        View view;

        public Adapter_Notice() {
        }
        @Override
        public ViewHolder_Notice onCreateViewHolder(ViewGroup parent, int i) {

            view = getActivity().getLayoutInflater().inflate(R.layout.item_notification, parent, false);

            return new ViewHolder_Notice(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder_Notice holder, final int position) {

            holder.date_release.setText("发布日期："+datas_list.get(position).getOpCreateTime());
            holder.person_from.setText("来自："+datas_list.get(position).getOpCreateName());
            holder.type_notification.setText(datas_list.get(position).getNotictType());
            holder.number.setText("编号"+datas_list.get(position).getNotictCode());
            if( Integer.parseInt(datas_list.get(position).getFileNum())==0){
                holder.addition.setText("附件:"+"无附件");
            }else{
                holder.addition.setText("附件："+"有"+datas_list.get(position).getFileNum()+"附件");
            }

            holder.title.setText(datas_list.get(position).getNoticeTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    position_click = holder.getAdapterPosition();
                    Intent intent=new Intent(getActivity(),Watch_Notification_Activity.class);
                    intent.putExtra("opId",datas_list.get(holder.getAdapterPosition()).getOpId());
                    intent.putExtra("noticeTitle",datas_list.get(holder.getAdapterPosition()).getNoticeTitle());
                    intent.putExtra("readState","1");
                    startActivityForResult(intent,3);
                }
            });

        }

        @Override
        public int getItemCount() {
            return datas_list.size();
        }


        class ViewHolder_Notice extends RecyclerView.ViewHolder{

            private TextView title;
            private TextView addition;
            private TextView date_release;
            private TextView type_notification;
            private TextView number;
            private TextView person_from;


            public ViewHolder_Notice(View itemView) {
                super(itemView);

                title=(TextView) itemView.findViewById(R.id.title);
                addition=(TextView) itemView.findViewById(R.id.addition);
                date_release=(TextView) itemView.findViewById(R.id.date_release);
                type_notification=(TextView) itemView.findViewById(R.id.type_notification);
                number=(TextView) itemView.findViewById(R.id.number);
                person_from=(TextView) itemView.findViewById(R.id.person_from);

            }
        }


    }


    // 暴露给HomeActivity的方法体，排序的刷新数据
    public void initData(String order_again) {

        api = HttpClient.getInstance().getGovernmentApi();
        order = order_again;
        Call<Return_Notification> call = api.Notification(userId, 1 + "", order_again);
        rv.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Return_Notification>() {
            @Override
            public void onResponse(Call<Return_Notification> call, Response<Return_Notification> response) {

                if (response.body().getPageCount() != 0) {

                    datas_list.clear();
                    position_now = response.body().getPageNo();
                    all = response.body().getPageCount();
                    return_wait = response.body();
                    datas_list = response.body().getData();

                    adapter_wait = new Adapter_Notice();
                    rv.setAdapter(adapter_wait);
                    adapter_wait.notifyDataSetChanged();
                } else {
                    datas_list.clear();
                }
            }

            @Override
            public void onFailure(Call<Return_Notification> call, Throwable t) {
                datas_list.clear();
                Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });


    }





}
