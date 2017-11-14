package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Retrun_Down;
import com.lanwei.governmentstar.bean.Return_Down2;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/24.
 */

public class DocumentOpinionActivity2 extends BaseActivity implements View.OnClickListener{

    private TextView tv_closed;
    private View view;
    private MyListView listview_result;
    private TextView ren;
    private TextView title;
    String userId;
    private GovernmentApi api;
    TextView baseTitle;
    private RelativeLayout liuyan_kezahng;
    private TextView opinion;
    private TextView name;
    private TextView state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_results);

        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        Logging_Success bean=gson.fromJson(defString,Logging_Success.class);

        userId=bean.getData().getOpId();
        baseTitle = (TextView) findViewById(R.id.tv_address);
        title = (TextView) findViewById(R.id.title);
        opinion = (TextView) findViewById(R.id.opinion);
        name = (TextView) findViewById(R.id.name);
        ren = (TextView) findViewById(R.id.ren);
        state = (TextView) findViewById(R.id.state);
        liuyan_kezahng = (RelativeLayout) findViewById(R.id.liuyan_kezahng);
        listview_result=(MyListView)findViewById(R.id.listview_result);
        CircleImageView icon = (CircleImageView) findViewById(R.id.iv_contacts);
        ImageView back = (ImageView) findViewById(R.id.back);
        api= HttpClient.getInstance().getGovernmentApi();

        if(getIntent().getStringExtra("type").equals("liuyan")){
            listview_result.setVisibility(View.GONE);
            // 获取opId和userId
            Call<Return_Down2> call= api.watch_liuyan(getIntent().getStringExtra("opId"),userId);

            call.enqueue(new Callback<Return_Down2>() {
                @Override
                public void onResponse(Call<Return_Down2> call, Response<Return_Down2> response) {
                    baseTitle.setVisibility(View.VISIBLE);
                    baseTitle.setText("转发的文件");
                    title.setText("留言如下：");

                    if(response.body().getData()!=null && !response.body().getData().equals("")){

                        Return_Down2 retrun_down=response.body();
                        liuyan_kezahng.setVisibility(View.VISIBLE);
                        listview_result.setVisibility(View.GONE);

                        opinion.setText(retrun_down.getData().getMessage());
                        name.setText(retrun_down.getData().getOpCreateName());
                        state.setText(retrun_down.getData().getOpCreateTime());

                    }else{
                        listview_result.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onFailure(Call<Return_Down2> call, Throwable t) {
                    Toast.makeText(DocumentOpinionActivity2.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                }
            });

        }else if(getIntent().getStringExtra("type").equals("fankui")){
            baseTitle.setText("科员反馈");
            title.setText("回复内容如下：");
            baseTitle.setVisibility(View.VISIBLE);
            Call<Retrun_Down> call2=api.watch_replys(getIntent().getStringExtra("opId"));
            call2.enqueue(new Callback<Retrun_Down>() {
                @Override
                public void onResponse(Call<Retrun_Down> call, Response<Retrun_Down> response) {

                    if(response.body().getData()!=null && !response.body().getData().equals("")){

                        if(response.body().getData().size()>0){
                            Retrun_Down retrun_down=response.body();
                            listview_result.setAdapter(new ResultAdapter(retrun_down.getData()));
                        }else{
                            listview_result.setVisibility(View.GONE);
                        }

                    }else{
                        listview_result.setVisibility(View.GONE);

                    }

                }

                @Override
                public void onFailure(Call<Retrun_Down> call, Throwable t) {
                    Toast.makeText(DocumentOpinionActivity2.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                }
            });


        }

        icon.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    public class ResultAdapter extends BaseAdapter {

        private List<Retrun_Down.Data> dataList;

        public ResultAdapter(List<Retrun_Down.Data> dataList) {
            this.dataList = dataList;

        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return dataList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = View.inflate(DocumentOpinionActivity2.this, R.layout.activity_results_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(dataList.get(position).getOpCreateName());
            holder.state.setText(dataList.get(position).getOpCreateTime());
            holder.opinion.setText(dataList.get(position).getOpOpinion());
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.opinion)
            TextView opinion;
            @InjectView(R.id.ren)
            TextView ren;
            @InjectView(R.id.name)
            TextView name;
            @InjectView(R.id.state)
            TextView state;
            @InjectView(R.id.sj)
            TextView sj;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

}
