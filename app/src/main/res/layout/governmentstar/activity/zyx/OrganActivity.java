package com.lanwei.governmentstar.activity.zyx;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Organ;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部部门
 * Created by 蓝威科技—技术部2 on 2017/3/18.
 */

public class OrganActivity extends BaseActivity implements View.OnClickListener {
    private List<Organ> dataList = new ArrayList<>();
    private MyListView listView;
    private View search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_address_details3);
        listView = (MyListView) findViewById(R.id.mylistview);
        TextView title = (TextView) findViewById(R.id.tv_address);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        search = findViewById(R.id.search);
        search.setOnClickListener(this);
        title.setVisibility(View.VISIBLE);

        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        title.setText("全部机关");
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        getData();

        //点击条目跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(OrganActivity.this, DepartmentActivity.class);
                Organ organ = dataList.get(position);
                intent.putExtra("organ", organ);
                startActivity(intent);
            }
        });
    }

    /**
     * 展示数据
     */
    private void getData() {
        //获取全部机关的数据
        RetrofitHelper.getInstance().getAllGovInfo(new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                Gson gson = new Gson();
                dataList = gson.fromJson(data, new TypeToken<List<Organ>>() {
                }.getType());
                OrganAdapter organAdapter = new OrganAdapter(dataList);
                listView.setAdapter(organAdapter);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.search:
                Intent intent = new Intent(OrganActivity.this, SearchContactsActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class OrganAdapter extends BaseAdapter {

        public List<Organ> dataList;
        private TextView title;
        private ImageView icon2;
        private ImageView icon3;

        private OrganAdapter(List<Organ> dataList) {
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(OrganActivity.this, R.layout.item_organ, null);
                title = (TextView) convertView.findViewById(R.id.title2);
                icon2 = (ImageView) convertView.findViewById(R.id.icon2);
                icon3 = (ImageView) convertView.findViewById(R.id.icon3);
            }
            title.setText(dataList.get(position).getOpname());
            String deptState = dataList.get(position).getDeptState();
            if (deptState.equals("1")){
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.VISIBLE);
            } else {
                icon2.setVisibility(View.VISIBLE);
                icon3.setVisibility(View.GONE);
            }
            return convertView;
        }
    }
}
