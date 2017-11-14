package com.lanwei.governmentstar.activity.spsq.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.SpsqOutApplyDetails;
import com.lanwei.governmentstar.bean.SpsqQTApplyDetails;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.view.CircleImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8/008.
 */

public class OutQJFlowAdapter extends BaseAdapter {
    List<SpsqQTApplyDetails.DataBean> dataBeanList;
    Context context;
    LayoutInflater inflater = null;

    public OutQJFlowAdapter(List<SpsqQTApplyDetails.DataBean> dataBeanList, Context context) {
        this.dataBeanList = dataBeanList;
        for(int i=Integer.parseInt(dataBeanList.get(dataBeanList.size()-1).getOpstate()) ;i<3 ;i++){
            dataBeanList.add(new SpsqQTApplyDetails.DataBean("","",i+1,""+(i+1),"","",""));
        }
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_document_item, viewGroup, false);
            holder.adapter_document_texticon = (TextView) convertView.findViewById(R.id.adapter_document_texticon);
            holder.adapter_document_name = (TextView) convertView.findViewById(R.id.adapter_document_name);
            holder.adapter_document_time = (TextView) convertView.findViewById(R.id.adapter_document_time);
            holder.adapter_document_content = (TextView) convertView.findViewById(R.id.adapter_document_content);
            holder.adapter_document_icon = (CircleImageView) convertView.findViewById(R.id.adapter_document_icon);
            holder.adapter_document_line1 = convertView.findViewById(R.id.adapter_document_line1);
            holder.adapter_document_line2 = convertView.findViewById(R.id.adapter_document_line2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
        }

        holder.adapter_document_content.setText("");//防止listview不断加长item却没有改变造成的问题

        holder.adapter_document_line1.setVisibility(View.INVISIBLE);
        holder.adapter_document_line2.setVisibility(View.INVISIBLE);
        AutoRelativeLayout.LayoutParams layoutParams = (AutoRelativeLayout.LayoutParams) holder.adapter_document_icon.getLayoutParams();
        layoutParams.topMargin = AutoUtils.getPercentWidthSize(38);
        holder.adapter_document_icon.setLayoutParams(layoutParams);
        if (dataBeanList.get(i).getName().equals("")) {//判断条件改变颜色如果没到//如果内容为null那是自己加的未审核
            holder.adapter_document_line2.setBackgroundResource(R.color.color_bb);
            holder.adapter_document_line1.setBackgroundResource(R.color.color_bb);
            holder.adapter_document_texticon.setBackgroundResource(R.drawable.gray);
            holder.adapter_document_name.setVisibility(View.INVISIBLE);
            holder.adapter_document_time.setVisibility(View.INVISIBLE);
            holder.adapter_document_content.setVisibility(View.INVISIBLE);
            holder.adapter_document_icon.setVisibility(View.INVISIBLE);
            if (!dataBeanList.get(i - 1).getName().equals("")) {//如果上一条有数据
                holder.adapter_document_line1.setBackgroundResource(R.color.color_43a7e1);
            }
        } else {
            if ((!dataBeanList.get(i).getName().equals("")) && (Math.abs(ManagerUtils.StringToData("yyyy-MM-dd hh:mm", dataBeanList.get(i).getOpTime())
                    - Math.abs(ManagerUtils.StringToData("yyyy-MM-dd hh:mm", dataBeanList.get(i ).getOpTime())))) > 2678400) {//两个月份超过1个月翻倍
                layoutParams.topMargin = AutoUtils.getPercentWidthSize((int) (38 * ((Math.abs(ManagerUtils.StringToData("yyyy-MM-dd hh:mm",
                        dataBeanList.get(i).getOpTime()) - Math.abs(ManagerUtils.StringToData("yyyy-MM-dd hh:mm", dataBeanList.get(i ).getOpTime()))) / 2678400))));
                holder.adapter_document_icon.setLayoutParams(layoutParams);
            }

            holder.adapter_document_line2.setBackgroundResource(R.color.color_43a7e1);
            holder.adapter_document_texticon.setBackgroundResource(R.drawable.blue01);
            holder.adapter_document_name.setVisibility(View.VISIBLE);
            holder.adapter_document_time.setVisibility(View.VISIBLE);
            holder.adapter_document_content.setVisibility(View.VISIBLE);
            holder.adapter_document_icon.setVisibility(View.VISIBLE);
            holder.adapter_document_name.setText(dataBeanList.get(i).getName());
            holder.adapter_document_time.setText(dataBeanList.get(i).getOpTime());
            holder.adapter_document_content.setText(dataBeanList.get(i).getTitle());
            Picasso.with(context).load(dataBeanList.get(i).getOpImg())
                    .memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.adapter_document_icon);
        }
        ViewTreeObserver vto = holder.adapter_document_content.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (holder.adapter_document_content.getLineCount() == 1) {
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line2.getLayoutParams();
                    layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_name);
                    holder.adapter_document_line2.setLayoutParams(layoutParams1);
                } else {
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line2.getLayoutParams();
                    layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_time);
                    holder.adapter_document_line2.setLayoutParams(layoutParams1);
                }
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                layoutParams1.addRule(RelativeLayout.ABOVE, R.id.adapter_document_texticon);
                holder.adapter_document_line1.setLayoutParams(layoutParams1);
                holder.adapter_document_texticon.setVisibility(View.VISIBLE);
                if (i == 0) {//首页
                    holder.adapter_document_line1.setVisibility(View.INVISIBLE);
                    holder.adapter_document_line2.setVisibility(View.VISIBLE);
                }  else {
                    holder.adapter_document_line2.setVisibility(View.VISIBLE);
                    holder.adapter_document_line1.setVisibility(View.VISIBLE);
                    if ( (dataBeanList.get(i-1).getOpstate().equals("2") && dataBeanList.get(i).getOpstate().equals("2"))) {
                        //如果上一个就是会签人,并且这个也是
                        holder.adapter_document_texticon.setVisibility(View.GONE);
                        holder.adapter_document_time.setVisibility(View.VISIBLE);
                        holder.adapter_document_line2.setVisibility(View.INVISIBLE);
                        if (holder.adapter_document_content.getLineCount() == 1) {
                            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                            layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_name);
                            holder.adapter_document_line1.setLayoutParams(layoutParams2);
                        } else {
                            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                            layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_time);
                            holder.adapter_document_line1.setLayoutParams(layoutParams2);
                        }
                    }
                    if (i == (dataBeanList.size() - 1)) {//尾页
                        holder.adapter_document_line1.setVisibility(View.VISIBLE);
                        holder.adapter_document_line2.setVisibility(View.INVISIBLE);
                    }
                }


                holder.adapter_document_content.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        Log.d("666",dataBeanList.get(i).getOpstate());
        switch (Integer.parseInt(dataBeanList.get(i).getOpstate())) {
            case 0: {
                holder.adapter_document_texticon.setText("起");
            }
            break;
            case 1: {
                holder.adapter_document_texticon.setText("审");
            }
            break;
            case 2: {
                holder.adapter_document_texticon.setText("核");
            }
            break;
            case 3: {
                holder.adapter_document_texticon.setText("批");
            }
            break;

        }

        return convertView;
    }

    class ViewHolder {
        TextView adapter_document_texticon, adapter_document_name, adapter_document_time, adapter_document_content = null;
        CircleImageView adapter_document_icon = null;
        View adapter_document_line1, adapter_document_line2;//线段上下
    }
}
