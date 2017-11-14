package com.lanwei.governmentstar.activity.spsq.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanwei.governmentstar.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

/**
 * Created by Administrator on 2017/9/6/006.
 */

public class FlowAdapter extends SwipeMenuAdapter<FlowAdapter.DefaultViewHolder> {

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_myhand_item, parent, false);
    }

    @Override
    public FlowAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FlowAdapter.DefaultViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public DefaultViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
