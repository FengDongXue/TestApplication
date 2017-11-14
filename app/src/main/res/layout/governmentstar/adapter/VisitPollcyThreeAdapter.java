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
import com.lanwei.governmentstar.bean.PollcyTwo;
import com.lanwei.governmentstar.interfaces.Onshow;

import java.util.List;


/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class VisitPollcyThreeAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<PollcyTwo> pollcyTwos;
    private Onshow onshow;
    private Handler handler;
    public VisitPollcyThreeAdapter(Context context,List<PollcyTwo> pollcyTwos ,Onshow onshow) {
        this.context = context;
        this.pollcyTwos = pollcyTwos;
        this.onshow = onshow;

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                notifyDataSetChanged();
                super.handleMessage(msg);
            }
        };

    }

    public void refresh() {
        handler.sendMessage(new Message());
    }

    @Override
    public int getGroupCount() {

        Log.e("二级的大小：",pollcyTwos.size()+"");
        return pollcyTwos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("三级的大小：",pollcyTwos.get(groupPosition).pollcyThrees.size()+"");
        return pollcyTwos.get(groupPosition).pollcyThrees.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return pollcyTwos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return pollcyTwos.get(groupPosition).pollcyThrees.get(childPosition);
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
        textView.setPadding(80, 20, 0, 20);
        textView.setTextSize(20);
        return textView;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

      View view = LayoutInflater.from(context).inflate(R.layout.textview2,parent,false);

        TextView textView= (TextView) view.findViewById(R.id.textview);
//        textView.setPadding(80, 20, 0, 20);
        textView.setText(pollcyTwos.get(groupPosition).getContent());
        ImageView shift_leaders= (ImageView) view.findViewById(R.id.shift_leaders);
        ImageView shift_leaders_down= (ImageView) view.findViewById(R.id.shift_leaders_down);

        Log.e("二级的大小：",pollcyTwos.size()+"");
        if(isExpanded) {
            shift_leaders_down.setVisibility(View.VISIBLE);
            shift_leaders.setVisibility(View.INVISIBLE);

//            parent.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,parent.getLayoutParams().height+160*pollcyTwos.get(groupPosition).pollcyThrees.size()));

//            ((CustExpListview)parent).count+=pollcyTwos.get(groupPosition).pollcyThrees.size();
//            parent.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }else{
            shift_leaders_down.setVisibility(View.INVISIBLE);
            shift_leaders.setVisibility(View.VISIBLE);
//            parent.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,parent.getLayoutParams().height-160*pollcyTwos.get(groupPosition).pollcyThrees.size()));

        }



//        TextView textView = getTextView();
//        textView.setText(pollcyTwos.get(groupPosition).content);
//        Log.d("VisitPollcyThreeAdapter", pollcyTwos.get(groupPosition).content);
//        return view;

//        TextView textView = getTextView();
//        textView.setText(pollcyTwos.get(groupPosition).getContent());

        return view;

    }

    @Override
    public View getChildView(final int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
         final CustExpListview listView = new CustExpListview(context ,groupPosition);


        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int Position) {

                pollcyTwos.get(groupPosition).getPollcyThrees().get(Position).setNot_expand(true);
//                onshow.onmeasure(groupPosition,pollcyTwos.get(groupPosition).getPollcyThrees().get(Position).getPollcyFours().size(),true ,childPosition);


            }
        });

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int Position) {
                pollcyTwos.get(groupPosition).getPollcyThrees().get(Position).setNot_expand(false);
//                onshow.onmeasure(groupPosition,pollcyTwos.get(groupPosition).getPollcyThrees().get(Position).getPollcyFours().size(),false ,childPosition);

//                listView.count-=pollcyTwos.get(groupPosition).getPollcyThrees().get(Position).getPollcyFours().size();
//                listView.measure(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });
        Log.e("三级的大小：",pollcyTwos.get(groupPosition).pollcyThrees.size()+"");
        VisitPollcyFourAdapter visitPollcyThreeAdapter = new VisitPollcyFourAdapter(context,pollcyTwos.get(groupPosition).pollcyThrees);
        listView.setAdapter(visitPollcyThreeAdapter);
        listView.setGroupIndicator(null);
        listView.setDivider(null);


//        if(childPosition>21){
//            return new TextView(context);
//        }

        return listView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class CustExpListview extends ExpandableListView {

        int groupPosition;
        int count=0;


        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                return true; // 禁止GridView滑动
            }
            return super.dispatchTouchEvent(ev);
        }

        public CustExpListview(Context context,int groupPosition) {
            super(context);
            this.groupPosition = groupPosition;

                for(int j=0; j<pollcyTwos.get(groupPosition).pollcyThrees.size();j++){

                    count++;
                    for(int k=0; k<pollcyTwos.get(groupPosition).pollcyThrees.get(j).pollcyFours.size();k++){
                        count++;

                    }
                }

            Log.e("二级子目录个数",count+"");
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


            heightMeasureSpec = MeasureSpec.makeMeasureSpec(160*count,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


}
