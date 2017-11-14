package com.lanwei.governmentstar.activity.gwnz.opinion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Opinion;
import com.lanwei.governmentstar.bean.OpinionList;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/31.
 */

public class DocumentOpinionActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_closed;
    private View view;
    private ListView listview_result;
    private TextView ren;
    private TextView sj;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_results);

        TextView baseTitle = (TextView) findViewById(R.id.tv_address);
        CircleImageView icon = (CircleImageView) findViewById(R.id.iv_contacts);
        ImageView back = (ImageView) findViewById(R.id.back);

        icon.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        Intent intent = getIntent();
        String opid = intent.getStringExtra("opId");
        String flowStatus = intent.getStringExtra("flowStatus");
        if (flowStatus.equals("6")) {
            baseTitle.setVisibility(View.VISIBLE);
            baseTitle.setText("查看会签意见");
        }

        getData(opid, flowStatus);
    }

    private void getData(String opid, final String flowStatus) {
        //获取公文拟制的数据
        RetrofitHelper.getInstance().getNZOpinionInfo(opid, flowStatus, new CallBackYSAdapter() {

            @Override
            protected void showErrorMessage(String message) {
                Log.e("mes", message);
            }

            @Override
            protected void parseJson(String data) {
                Log.e("data", data);
                if (data != null) {
                    Gson gson = new Gson();
                    OpinionList opinionList = gson.fromJson(data, OpinionList.class);
                    List<Opinion> datalist = opinionList.getData();
                    ResultAdapter resultAdapter = new ResultAdapter(datalist,flowStatus);

                    listview_result = (ListView) findViewById(R.id.listview_result);
                    listview_result.setAdapter(resultAdapter);
                }
            }
        });
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

        private String flowStatus;
        private List<Opinion> dataList;


        public ResultAdapter(List<Opinion> dataList, String flowStatus) {
            this.dataList = dataList;
            this.flowStatus = flowStatus;
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = View.inflate(DocumentOpinionActivity.this, R.layout.activity_results_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (flowStatus.equals("6")) {
                holder.ren.setText("会签人 ： ");
                holder.sj.setText("时间 ： ");
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