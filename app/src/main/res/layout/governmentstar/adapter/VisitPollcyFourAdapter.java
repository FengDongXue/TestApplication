package com.lanwei.governmentstar.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.PollcyThree;

import java.util.List;


/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class VisitPollcyFourAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<PollcyThree> pollcyThrees;

    public VisitPollcyFourAdapter(Context context, List<PollcyThree> pollcyThrees) {
        this.context = context;
        this.pollcyThrees = pollcyThrees;
    }

    @Override
    public int getGroupCount() {
        return pollcyThrees.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return pollcyThrees.get(groupPosition).pollcyFours.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return pollcyThrees.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return pollcyThrees.get(groupPosition).pollcyFours.get(childPosition);
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
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(120, 20, 0, 20);
        textView.setTextSize(20);
        return textView;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.textview3,parent,false);

        TextView textView= (TextView) view.findViewById(R.id.textview);
//        textView.setPadding(120, 20, 0, 20);
        textView.setText(pollcyThrees.get(groupPosition).getContent());

        ImageView shift_leaders= (ImageView) view.findViewById(R.id.shift_leaders);
        ImageView shift_leaders_down= (ImageView) view.findViewById(R.id.shift_leaders_down);
        if(isExpanded) {
            shift_leaders_down.setVisibility(View.VISIBLE);
            shift_leaders.setVisibility(View.INVISIBLE);
        }else{
            shift_leaders_down.setVisibility(View.INVISIBLE);
            shift_leaders.setVisibility(View.VISIBLE);
        }

//        TextView textView = getTextView();
//        textView.setText(pollcyThrees.get(groupPosition).content);
        return view;

//        TextView textView = getTextView();
//        textView.setText(pollcyThrees.get(groupPosition).getContent());
//        return textView;

    }

    @Override
    public View getChildView(final int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.textview4,parent,false);

        TextView textView= (TextView) view.findViewById(R.id.textview);
        final ImageView imageView= (ImageView) view.findViewById(R.id.isChoosed);

        if(pollcyThrees.get(groupPosition).pollcyFours.get(childPosition).isChoosed){
            imageView.setImageResource(R.drawable.icon_x);
        }else{
            imageView.setImageResource(R.drawable.icon_w);
        }

        view.findViewById(R.id.itemview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pollcyThrees.get(groupPosition).pollcyFours.get(childPosition).isChoosed){
                    imageView.setImageResource(R.drawable.icon_w);
                    pollcyThrees.get(groupPosition).pollcyFours.get(childPosition).setChoosed(false);

                }else{
                    imageView.setImageResource(R.drawable.icon_x);
                    pollcyThrees.get(groupPosition).pollcyFours.get(childPosition).setChoosed(true);

                }
            }
        });

//        textView.setPadding(120, 20, 0, 20);
        textView.setText(pollcyThrees.get(groupPosition).pollcyFours.get(childPosition).getContent());

//        TextView textView = getTextView();
//        textView.setPadding(160, 20, 0, 20);
//        textView.setText(pollcyThrees.get(groupPosition).pollcyFours.get(childPosition).getContent());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
