package com.lanwei.governmentstar.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.SendMessageActivity;
import com.lanwei.governmentstar.activity.zyx.OrganActivity;
import com.lanwei.governmentstar.activity.zyx.SearchContactsActivity;
import com.lanwei.governmentstar.activity.zyx.ThisOrganActivity;
import com.lanwei.governmentstar.bean.Organ;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/17.
 */

@SuppressLint("ValidFragment")
public class AddressFragment extends BaseHomeFragment implements View.OnClickListener {

    private View view;

    public Activity activity;
    private MyListView rlv_contact;
    private RelativeLayout checkin2;
    private RelativeLayout checkin1;
    private RelativeLayout checkin3;
    private List<Organ> dataList = new ArrayList<>();
    private String dptName;
    //    private String dptName;
//    private String dptId;
//    private String opId;


    public AddressFragment(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("ValidFragment")
    public AddressFragment() {
    }

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
        view = View.inflate(getContext(), R.layout.fragment_address, null);

        checkin1 = (RelativeLayout) view.findViewById(R.id.checkin1);     // 本机关
//        checkin2 = (RelativeLayout) view.findViewById(R.id.checkin2);     //工作组
        checkin3 = (RelativeLayout) view.findViewById(R.id.checkin3);     //全部机关
        rlv_contact = (MyListView) view.findViewById(R.id.rlv_contact);//联系人
        TextView title = (TextView) view.findViewById(R.id.title);
        View ll_contact = view.findViewById(R.id.ll_contact);
        ll_contact.setVisibility(View.GONE);                               //// TODO: 2017/5/8     常用联系人！！！！！

        View search = view.findViewById(R.id.search);//搜索

        search.setOnClickListener(this);

////        String dptName = (String) SharedPreferencesUtil.getData(activity, "dptName", "蓟州区渔阳镇人民政府");
//        opId = new GetAccount(activity).opId();
//        dptId = new GetAccount(activity).dptId();
        dptName = new GetAccount(activity).dptName();
        title.setText(dptName);
        initData();
        //展示数据
        rlv_contact.setAdapter(new MyAdapter());
        //设置listview的条目的点击事件
        rlv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, SendMessageActivity.class);
                activity.startActivity(intent);
            }
        });


        checkin1.setOnClickListener(this);
//        checkin2.setOnClickListener(this);
        checkin3.setOnClickListener(this);


//        params.setMargins(0, 0, 0, 0);
//        LinearLayout linearLayout = new LinearLayout(inflater.getContext());
//        linearLayout.setLayoutParams(params);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.addView(view);

        return view;
    }

    /**
     * 展示数据
     */
    private void initData() {
        RetrofitHelper.getInstance().getAllGovInfo(new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                Gson gson = new Gson();
                dataList = gson.fromJson(data, new TypeToken<List<Organ>>() {
                }.getType());
                //展示数据
//                rlv_contact.setAdapter(new MyAdapter());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkin1:
                Intent intent1 = new Intent(activity, ThisOrganActivity.class);
                startActivity(intent1);
                break;
//            case R.id.checkin2:
////                Intent intent2 = new Intent(activity, OrganActivity.class);
////                startActivity(intent2);
//                Toast.makeText(activity, "暂未开发", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.checkin3:
                Intent intent3 = new Intent(activity, OrganActivity.class);
                startActivity(intent3);
                break;

            case R.id.search:
                Intent intent4 = new Intent(activity, SearchContactsActivity.class);
                startActivity(intent4);
                break;

        }
    }

    private class MyAdapter extends BaseAdapter {


        public MyAdapter() {
        }

        @Override
        public int getCount() {
            return 0;
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
                convertView = View.inflate(activity, R.layout.item_address_contacts, null);
            }
            return convertView;
        }
    }
}
