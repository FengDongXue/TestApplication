package com.lanwei.governmentstar.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

//公会签文
public class DemoListActivity extends BaseActivity implements OnClickListener,PullToRefreshBase.OnRefreshListener2{
	PullToRefreshListView activity_document;
	private List<String> strings;
	private DemoAdapter demoAdapter;
 	private static final String TAG = DemoListActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.d(TAG, "onCreate()");
		setContentView(R.layout.activity_demolist);
		strings=new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			strings.add("123");
		}
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		activity_document= (PullToRefreshListView) findViewById(R.id.pulllist);
		activity_document.setMode(PullToRefreshBase.Mode.BOTH);
		activity_document.getLoadingLayoutProxy(false, true).setPullLabel(
				getString(R.string.pull_to_load));
		activity_document.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel(getString(R.string.loading));
		activity_document.getLoadingLayoutProxy(false, true)
				.setReleaseLabel(getString(R.string.release_to_load));
		activity_document.setOnRefreshListener(this);
		demoAdapter=new DemoAdapter(strings,this);
		activity_document.setAdapter(demoAdapter);
	}
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
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate( 0,null);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			return convertView;
		}

		class ViewHolder {
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtils.d(TAG, "onStart()");
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtils.d(TAG, "onResume()");
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtils.d(TAG, "onStop()");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.d(TAG, "onDestroy()");
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}
	}
	/**
	 * 接口处理
	 */
	@Override
	protected void baseJsonNext(String response, String tag) {
		// TODO Auto-generated method stub
		super.baseJsonNext(response, tag);
		if(tag.equals(TAG+"xxx")){
			
		}
	}
	Handler handler=new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what){
				case 1:{
					for (int i = 0; i < 15; i++) {
						strings.add("");
					}
				}break;
				case 0:{
					strings.clear();
					for (int i = 0; i < 15; i++) {
						strings.add("");
					}
				}break;
			}
			demoAdapter.notifyDataSetChanged();
			activity_document.onRefreshComplete();
				return true;
		}
	});
	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		handler.sendEmptyMessageDelayed(0,1000);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		handler.sendEmptyMessageDelayed(1,5000);
	}
}
