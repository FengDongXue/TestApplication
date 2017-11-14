package com.lanwei.governmentstar.activity.zyx;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Search_contacts;
import com.lanwei.governmentstar.db.dao.RecordSQLiteOpenHelper;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchContactsActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.search_et_input)
    EditText searchEtInput;
    @InjectView(R.id.search_iv_delete)
    ImageView searchIvDelete;
    @InjectView(R.id.search_btn_back)
    Button searchBtnBack;
//    @InjectView(R.id.search_lv_tips)
//    ListView searchLvTips;
    @InjectView(R.id.tv_tip)
    TextView tvTip;
    @InjectView(R.id.listView)
    MyListView listView;
    @InjectView(R.id.tv_clear)
    TextView tvClear;
    @InjectView(R.id.listView1)
    MyListView listView1;


    private int positon = 0;
    private List<Search_contacts> dataList = new ArrayList<>();
    private SQLiteDatabase db;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
    private BaseAdapter adapter;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        //清空搜索历史
        tvClear.setOnClickListener(this);


        //搜索框的键盘搜索键点击回调
        searchEtInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    content = searchEtInput.getText().toString();
                    if (content != null) {
                        searchIvDelete.setVisibility(View.VISIBLE);
                        searchIvDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searchEtInput.setText("");
                            }
                        });

                        //搜索
                        try {
                            getData(content);
                        } catch (Exception e) {
                            Toast.makeText(SearchContactsActivity.this, "查无此人！", Toast.LENGTH_SHORT).show();
                        }
                        // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                        boolean hasData = hasData(content.trim());
                        if (!hasData) {
                            insertData(content.trim());
                            queryData("");
                        }
                        // TODO 根据输入的内容模糊查询联系人，并跳转到另一个界面，由你自己去实现

//                        Toast.makeText(SearchContactsActivity.this, "clicked!", Toast.LENGTH_SHORT).show();


                    } else {
                    }

//                    // TODO 根据输入的内容模糊查询联系人，并跳转到另一个界面，由你自己去实现

                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        searchEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                searchIvDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchIvDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchIvDelete.setVisibility(View.GONE);


                if (s.toString().trim().length() == 0) {
                    tvTip.setText("搜索历史");
                    searchBtnBack.setText("取消");

                } else {
//                    tvTip.setText("搜索结果");
                    tvTip.setText("搜索历史");
                    searchBtnBack.setText("取消");

                }
                String tempName = searchEtInput.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(SearchContactsActivity.this, ContactsDetailsActivity.class);
                String opId = dataList.get(positon).getOpId();

                intent.putExtra("contactId", opId);
                startActivity(intent);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView searchName = (TextView) view.findViewById(R.id.name);
                String name = searchName.getText().toString();
                searchEtInput.setText(name);
                Toast.makeText(SearchContactsActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
            }
        });


        // 第一次进入查询所有的历史记录
        queryData("");

        if (searchBtnBack.getText().equals("取消")) {
            searchBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    finish();

                }
            });
        } else if (searchBtnBack.getText().equals("搜索")) {
            //搜索
            try {
                getData(content);
            } catch (Exception e) {
                Toast.makeText(SearchContactsActivity.this, "查无此人！", Toast.LENGTH_SHORT).show();
            }
            // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
            boolean hasData = hasData(content.trim());
            if (!hasData) {
                insertData(content.trim());
                queryData("");
            }
            // TODO 根据输入的内容模糊查询联系人，并跳转到另一个界面，由你自己去实现

            Toast.makeText(SearchContactsActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                        "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
            Cursor cursor = helper.getReadableDatabase().rawQuery(
                        "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);

            //参2 ： item
            //参3 ： 你要查询的字段
            //参4 ： 把相应的字段附上相对的值
            // 创建adapter适配器对象
            adapter = new SimpleCursorAdapter(this,R.layout.item_searchhistory, cursor, new String[] { "name" },
                               // new int[] { R.id.icon3,R.id.name,R.id.deptname,R.id.sectorname }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    new int[] {R.id.name }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            // 设置适配器
            listView.setAdapter(adapter);
//        listView.setEnabled(false);
            adapter.notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }


    /**
     * 展示数据
     *
     * @param opid
     */
    private void getData(String opid) {
        //获取全部机关的数据
        RetrofitHelper.getInstance().getSearchUserInfo(opid, new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                Gson gson = new Gson();
                dataList = gson.fromJson(data, new TypeToken<List<Search_contacts>>() {
                }.getType());
                if (dataList == null || dataList.size() == 0 || data == null) {
                    Toast.makeText(SearchContactsActivity.this, "您的搜索有误，请重新搜索！", Toast.LENGTH_SHORT).show();
                    return;
                }
                SearchAdapter searchAdapter = new SearchAdapter(dataList);
               listView1.setAdapter(searchAdapter);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clear:
            deleteData();
            queryData("");
                break;
        }
    }

    public class SearchAdapter extends BaseAdapter {
        private List<Search_contacts> dataList;


        public SearchAdapter(List<Search_contacts> dataList) {
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
            positon = position;
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(SearchContactsActivity.this, R.layout.item_address_contacts, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(dataList.get(position).getOpName());
            holder.deptname.setText(dataList.get(position).getAccountDeptName());
            holder.sectorname.setText(dataList.get(position).getAccountSectorName());
            Glide.with(SearchContactsActivity.this)
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
