package com.lanwei.governmentstar.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.PersonalDataActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/20.
 */

public class ContactsAdapter extends RecyclerView.Adapter {

    private Activity activity;

    public ContactsAdapter(Activity activity) {
        this.activity = activity;
    }

    public ContactsAdapter() {

    }

    //生成ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.item_address_contacts, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).title3.setText("13234");
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.icon3)
        ImageView icon3;
        @InjectView(R.id.title3)
        TextView title3;
//        @InjectView(R.id.online3)
//        TextView online3;
//        @InjectView(R.id.count3)
//        TextView count3;
        @InjectView(R.id.item)
        RelativeLayout item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        @OnClick({R.id.item})
        public void onClick(View view){
            switch (view.getId()){
                case R.id.item:
                    Intent intent = new Intent(activity, PersonalDataActivity.class);
                    activity.startActivity(intent);
                    break;
            }
        }
    }
}
