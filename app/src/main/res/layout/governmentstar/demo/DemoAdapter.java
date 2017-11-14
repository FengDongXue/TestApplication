package com.lanwei.governmentstar.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DemoAdapter extends BaseAdapter {

	List<String> strs = null;
	LayoutInflater inflater = null;
	
	public DemoAdapter(List<String> strs, Context context) {
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView textView = null;
		ImageView imageView=null;
	}

}
