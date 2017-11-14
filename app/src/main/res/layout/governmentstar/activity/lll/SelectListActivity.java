package com.lanwei.governmentstar.activity.lll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


public class SelectListActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = SelectListActivity.class.getSimpleName();
    private ListView selectlist_list;
    private List<isSelect> list;
    private SelectListAdapter selectListAdapter;
    private TextView title;
    private TextView more;
    private ArrayList<String> dptList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        setContentView(R.layout.activity_selectlist);
        dptList = new ArrayList<>();
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        selectlist_list = (ListView) findViewById(R.id.selectlist_list);
        title = (TextView) findViewById(R.id.title);
        more = (TextView) findViewById(R.id.more);

        more.setVisibility(View.VISIBLE);
        more.setText("确定");
        more.setOnClickListener(this);

        list = new ArrayList<>();
        list.add(new isSelect(false, "蓟州区渔阳镇人民政府"));
        list.add(new isSelect(false, "蓟州区别山镇人民政府"));
        list.add(new isSelect(false, "蓟州区工业和信息化委员会"));
        selectListAdapter = new SelectListAdapter(list, this);
        selectlist_list.setAdapter(selectListAdapter);
        title.setText("选择政府");

        selectlist_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:  //渔阳
                        dptList.add("0105");
                        break;
                    case 2:  //别山
                        dptList.add("0106");
                        break;
                    case 3:  //工业信息
                        dptList.add("0127");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class isSelect {
        boolean isSelect;
        String name;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public isSelect(boolean isSelect, String name) {
            this.isSelect = isSelect;
            this.name = name;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        LogUtils.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        LogUtils.d(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LogUtils.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy()");
    }

    public class SelectListAdapter extends BaseAdapter {

        List<isSelect> strs = null;
        LayoutInflater inflater = null;

        public SelectListAdapter(List<isSelect> strs, Context context) {
            this.strs = strs;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public Object getItem(int arg0) {
            return strs.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int arg0, View convertView, ViewGroup arg2) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.adapter_selectlist_item, arg2, false);
                holder.all_layout = (LinearLayout) convertView.findViewById(R.id.all_layout);
                holder.selectlist_text = (TextView) convertView.findViewById(R.id.selectlist_text);
                holder.selectlist_icon = (ImageView) convertView.findViewById(R.id.selectlist_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }
            holder.selectlist_text.setText(strs.get(arg0).getName());
            if (strs.get(arg0).isSelect()) {
                holder.selectlist_icon.setImageResource(R.drawable.icon_x);
            } else {
                holder.selectlist_icon.setImageResource(R.drawable.icon_w);
            }
            holder.all_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    strs.get(arg0).setSelect(!strs.get(arg0).isSelect());
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView selectlist_text = null;
            ImageView selectlist_icon = null;
            public LinearLayout all_layout;
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.more:
                Intent intent = new Intent();
                intent.putStringArrayListExtra("dptId", dptList);
                setResult(18,intent);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 接口处理
     */
    @Override
    protected void baseJsonNext(String response, String tag) {
        // TODO Auto-generated method stub
        super.baseJsonNext(response, tag);
        if (tag.equals(TAG + "xxx")) {

        }
    }
}
