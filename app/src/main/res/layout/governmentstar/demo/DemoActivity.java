package com.lanwei.governmentstar.demo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lanwei.governmentstar.utils.LogUtils;


public class DemoActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = DemoActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.d(TAG, "onCreate()");
		setContentView(0);
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
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
}
