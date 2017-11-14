package com.lanwei.governmentstar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter2<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private List<T> mDataList;
    private TextView remove;
    private RemoveListner listner;
    private int position;
    private int remPosition;
    private TextView textView;
    private LinearLayout llll;

    public TagAdapter2(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position = position;
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);
        llll = (LinearLayout) view.findViewById(R.id.llll);

        textView = (TextView) view.findViewById(R.id.tv_tag);
        remove = (TextView) view.findViewById(R.id.remove_item);

        if (remPosition == position) {
            remove.setVisibility(View.VISIBLE);
            textView.setBackgroundResource(R.drawable.shape_tag_bg);
            textView.setTextColor(Color.WHITE);
        }


            llll.setPadding(0, 0, 0, 0);
        T t = mDataList.get(position);

        if (t instanceof String) {
            textView.setText((String) t);
        }

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.setRemListner(position);
            }
        });
        return view;
    }

    public void setPosition(int remPosition) {
        this.remPosition = remPosition;
    }

    public int getRemPosition() {
        return remPosition;
    }

    public void removeListner(RemoveListner listner) {
        this.listner = listner;
    }


    public interface RemoveListner {
        void setRemListner(int position);
    }

    public void onlyAddAll(List<T> datas) {
//        mDataList.addAll(datas);
        mDataList = datas;
//        mDataList = new ArrayList<>();
//        mDataList.add("");
        notifyDataSetChanged();
    }


    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return true;
        }
        return false;
    }

}
