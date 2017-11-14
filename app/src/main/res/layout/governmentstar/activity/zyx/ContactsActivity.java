package com.lanwei.governmentstar.activity.zyx;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Contacts;
import com.lanwei.governmentstar.bean.Department;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ContactsActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private List<Contacts> dataList = new ArrayList<>();
    private MyListView mylistview;
    private View search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//代码去除标题栏
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_address_details2);
        //设置沉浸式状态栏,必须在setContentView方法之前执行

        title = (TextView) findViewById(R.id.tv_address);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        ImageView back = (ImageView) findViewById(R.id.back);

        search = findViewById(R.id.search);
        search.setOnClickListener(this);
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        mylistview = (MyListView) findViewById(R.id.listview);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsActivity.this, ContactsDetailsActivity.class);
                Contacts contacts = dataList.get(position);

                String opid = contacts.getOpId();

                intent.putExtra("contacts", contacts);
                intent.putExtra("contactId", opid);

                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Department department = (Department) intent.getSerializableExtra("department");
        String opid = department.getOpid();
        String opname = department.getOpname();
        title.setText(opname);  //设置标题栏
        getData(opid);
    }

    /**
     * 展示数据
     *
     * @param opid
     */
    private void getData(String opid) {
        //获取全部机关的数据
        RetrofitHelper.getInstance().getAllCtsInfo(opid, new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                Gson gson = new Gson();
                dataList = gson.fromJson(data, new TypeToken<List<Contacts>>() {
                }.getType());
                if(dataList != null && dataList.size()>0){

                    ContactsAdapter contactsAdapter = new ContactsAdapter(dataList);
                    mylistview.setAdapter(contactsAdapter);
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
            case R.id.search:
                Intent intent = new Intent(ContactsActivity.this, SearchContactsActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class ContactsAdapter extends BaseAdapter {

        private List<Contacts> dataList;

        public ContactsAdapter(List<Contacts> dataList) {
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(ContactsActivity.this, R.layout.item_address_contacts, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(dataList.get(position).getOpName());
            holder.deptname.setText(dataList.get(position).getAccountDeptName());
            holder.sectorname.setText(dataList.get(position).getAccountSectorName());
            Glide.with(ContactsActivity.this)
                    .load(dataList.get(position).getAccountlink())
                    .into(holder.icon3);
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.icon3)
            ImageView icon3;
            @InjectView(R.id.name)
            TextView name;
            @InjectView(R.id.deptname)
            TextView deptname;
            @InjectView(R.id.sectorname)
            TextView sectorname;
            @InjectView(R.id.item)
            RelativeLayout item;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
