package com.lanwei.governmentstar.activity.zyx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Department;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xong2 on 2017/3/23.
 */

public class ThisOrganActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ListView mylistview;
    private List<Department> dataList = new ArrayList<>();
    private View search;
    public Activity activity;
    private String opid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//代码去除标题栏
        setContentView(R.layout.activity_address_details3);
        title = (TextView) findViewById(R.id.tv_address);
        title.setVisibility(View.VISIBLE);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        mylistview = (ListView) findViewById(R.id.mylistview);
        search = findViewById(R.id.search);

        search.setOnClickListener(this);
        back.setOnClickListener(this);

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ThisOrganActivity.this, ContactsActivity.class);
                Department department = dataList.get(position);
                intent.putExtra("department", department);
                startActivity(intent);
            }
        });


//        Intent intent = getIntent();
//        Organ organ = (Organ) intent.getSerializableExtra("organ");
//        String opid = organ.getOpid();
//        String opname = organ.getOpname();
//        String opid = (String) SharedPreferencesUtil.getData(this, "dptId", "0126");
        opid = new GetAccount(this).dptId();
        Log.e("dptId",opid);
        title.setText("本机关");  //设置标题栏
        getData(opid);
    }

    /**
     * 展示数据
     * @param opid
     */
    private void getData(String opid) {
        Log.e("opid",opid);
        //获取全部机关的数据
        RetrofitHelper.getInstance().getThisGovInfo(opid,new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                Log.e("data",data);
                Gson gson = new Gson();
                dataList = gson.fromJson(data, new TypeToken<List<Department>>() {
                }.getType());
                ThisOrganAdapter thisOrganAdapter = new ThisOrganAdapter(dataList);
                mylistview.setAdapter(thisOrganAdapter);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back :
                finish();
                break;
            case R.id.search :
                Intent intent = new Intent(ThisOrganActivity.this, SearchContactsActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class ThisOrganAdapter extends BaseAdapter {

        private  List<Department> dataList;
        private TextView title;

        public ThisOrganAdapter(List<Department> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            if(dataList==null){
               return 0;
            }
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
                convertView = View.inflate(ThisOrganActivity.this, R.layout.item_department, null);
                title = (TextView) convertView.findViewById(R.id.title2);
            }
            title.setText(dataList.get(position).getOpname());
            return convertView;
        }
    }
}

