package com.lanwei.governmentstar.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lanwei.governmentstar.R;

/**
 * Created by xong2 on 2017/3/23.
 */

public class ContactsLvAdapter extends BaseAdapter {

    private  Activity activity;

    public ContactsLvAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 10;
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
        if (convertView == null){
            convertView = View.inflate(activity, R.layout.item_address_contacts,null);
        }
        return convertView;
    }
}
