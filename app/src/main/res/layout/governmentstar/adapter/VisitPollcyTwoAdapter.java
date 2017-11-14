package com.lanwei.governmentstar.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.PollcyOne;
import com.lanwei.governmentstar.interfaces.Onshow;
import com.lanwei.governmentstar.view.CustExpListview;


import java.util.List;


/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class VisitPollcyTwoAdapter extends BaseExpandableListAdapter implements Onshow{


    private Context context;
    private List<PollcyOne> pollcyOnes;
    private CustExpListview listView;
    VisitPollcyThreeAdapter visitPollcyThreeAdapter;
    private int groupPosition;

    public VisitPollcyTwoAdapter(Context context,List<PollcyOne> pollcyOnes) {
        this.context = context;
        this.pollcyOnes = pollcyOnes;
    }

    @Override
    public int getGroupCount() {

        Log.e("一级的大小",pollcyOnes.size()+"");
        return pollcyOnes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("二级的大小：",pollcyOnes.get(groupPosition).pollcyTwos.size()+"");
        return pollcyOnes.get(groupPosition).pollcyTwos.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return pollcyOnes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return pollcyOnes.get(groupPosition).pollcyTwos.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private TextView getTextView(){
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(40, 20, 0, 20);
        textView.setTextSize(20);
        return textView;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.textview1, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        ImageView shift_leaders = (ImageView) view.findViewById(R.id.shift_leaders);
        ImageView shift_leaders_down = (ImageView) view.findViewById(R.id.shift_leaders_down);
        textView.setText(pollcyOnes.get(groupPosition).getContent());

        if (isExpanded) {
            shift_leaders_down.setVisibility(View.VISIBLE);
            shift_leaders.setVisibility(View.INVISIBLE);
        } else {
            shift_leaders_down.setVisibility(View.INVISIBLE);
            shift_leaders.setVisibility(View.VISIBLE);
        }
        return view;
    }
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        listView = new CustExpListview(context ,groupPosition);
        this.groupPosition=groupPosition;
        visitPollcyThreeAdapter = new VisitPollcyThreeAdapter(context,pollcyOnes.get(groupPosition).pollcyTwos ,this);
        listView.setAdapter(visitPollcyThreeAdapter);
        listView.setGroupIndicator(null);
        listView.setDivider(null);


        return listView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class CustExpListview extends ExpandableListView {

        int groupPosition;
        int count =0;

        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                return true; // 禁止GridView滑动
            }
            return super.dispatchTouchEvent(ev);
        }

        public CustExpListview(Context context ,int groupPosition) {
            super(context);
            this.groupPosition = groupPosition;

            for(int i=0;i<pollcyOnes.get(groupPosition).pollcyTwos.size();i++){
                count++;
                for(int j=0; j<pollcyOnes.get(groupPosition).pollcyTwos.get(i).pollcyThrees.size();j++){
                    count++;
                    for(int k=0; k<pollcyOnes.get(groupPosition).pollcyTwos.get(i).pollcyThrees.get(j).pollcyFours.size();k++){
                        count++;
                    }
                }
            }

            Log.e("一级子目录个数：",count+"");

        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(160*count,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }


    }

    @Override
    public void onmeasure(int childPosition ,int number, boolean up_down ,final int item_expand) {

        if(up_down){

            listView.count+=number;
        }else{
            listView.count-=number;

        }

        listView.measure(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);

        for(int i=0;i<pollcyOnes.get(groupPosition).pollcyTwos.get(childPosition).getPollcyThrees().size();i++){

            if(pollcyOnes.get(groupPosition).pollcyTwos.get(childPosition).getPollcyThrees().get(i).isNot_expand()){
                listView.expandGroup(i);
            }else{
                listView.collapseGroup(i);
            }
        }
    }
}
