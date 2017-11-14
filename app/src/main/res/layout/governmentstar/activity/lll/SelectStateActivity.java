package com.lanwei.governmentstar.activity.lll;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.io.State;
import com.lanwei.governmentstar.activity.zyx.MyhandActivity;
import com.lanwei.governmentstar.bean.Condition_Shift;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.fragment.SelectStateFragment;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//通知中查看状态
public class SelectStateActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = SelectStateActivity.class.getSimpleName();
	private TabLayout selectstate_tablayout;
	private ViewPager selectstate_viewpager;
	private TextView title,more;
	private ImageView back;
	private String opId;
	private GovernmentApi api;
	private  ArrayList<Condition_Shift.Data> list=new ArrayList<>();
	public   ArrayList<Condition_Shift.Data> list_seen=new ArrayList<>();
	public   ArrayList<Condition_Shift.Data> list_notseen=new ArrayList<>();
	private Condition_Shift condition =new Condition_Shift();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.d(TAG, "onCreate()");
		setContentView(R.layout.activity_selectstate);

		if (Build.VERSION.SDK_INT >= 21) {
			StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
		}

		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		selectstate_tablayout= (TabLayout) findViewById(R.id.selectstate_tablayout);
		selectstate_viewpager= (ViewPager) findViewById(R.id.selectstate_viewpager);
		title= (TextView) findViewById(R.id.title);
		more= (TextView) findViewById(R.id.more);
		back= (ImageView) findViewById(R.id.back);
		more.setOnClickListener(this);
		back.setOnClickListener(this);

		opId=getIntent().getStringExtra("opId");
		initView();

//        userId=bean.getData().getOpId();
		api = HttpClient.getInstance().getGovernmentApi();
		Call<Condition_Shift> call=api.watch_state(opId,getIntent().getStringExtra("userId"));

		call.enqueue(new Callback<Condition_Shift>() {
			@Override
			public void onResponse(Call<Condition_Shift> call, Response<Condition_Shift> response) {

				if(response.body().getData() != null){

					condition=response.body();

					list=response.body().getData();

					for(int i=0;i<list.size();i++){

						if(list.get(i).getCk()){
							list_seen.add(list.get(i));
						}else{
							list_notseen.add(list.get(i));
						}
					}

					initPager();

				}
			}

			@Override
			public void onFailure(Call<Condition_Shift> call, Throwable t) {
				Toast.makeText(SelectStateActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
			}
		});


	}

	private void initView() {
		title.setText("查阅状态");
		more.setText("全部提醒");
		more.setVisibility(View.INVISIBLE);
	}

	public void initPager(){//绑定title
		Bundle bundle=new Bundle();
		bundle.putSerializable("enum", State.UnRead);
		bundle.putSerializable("condition", condition);
//		bundle.putString("userID",getIntent().getStringExtra("uid"));//传递对应参数
		SelectStateFragment fragment=SelectStateFragment.getInstance(bundle);
		PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());//创建其他页面
		pagerAdapter.addFragment(fragment, "未读信息");
		Bundle bundle2=new Bundle();
		SelectStateFragment fragment2=SelectStateFragment.getInstance(bundle2);
		bundle2.putSerializable("enum", State.Read);
		bundle2.putSerializable("condition", condition);
//		bundle2.putString("userID",getIntent().getStringExtra("uid"));
		pagerAdapter.addFragment(fragment2, "已读信息");
		selectstate_viewpager.setAdapter(pagerAdapter);
		selectstate_tablayout.setupWithViewPager(selectstate_viewpager);
		selectstate_tablayout.post(new Runnable() {
			@Override
			public void run() {
				setIndicator(selectstate_tablayout,50,50);
			}
		});
	}

	public void setIndicator (TabLayout tabs,int leftDip,int rightDip) {
		Class<?> tabLayout = tabs.getClass();
		Field tabStrip = null;
		try {
			tabStrip = tabLayout.getDeclaredField("mTabStrip");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		tabStrip.setAccessible(true);
		LinearLayout llTab = null;
		try {
			llTab = (LinearLayout) tabStrip.get(tabs);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
		int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

		for (int i = 0; i < llTab.getChildCount(); i++) {
			View child = llTab.getChildAt(i);
			child.setPadding(0, 0, 0, 0);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
			params.leftMargin = left;
			params.rightMargin = right;
			child.setLayoutParams(params);
			child.invalidate();
		}
	}
	class PagerAdapter extends FragmentPagerAdapter {

		private final List<Fragment> fragmentList = new ArrayList<>();
		private final List<String> fragmentTitleList = new ArrayList<>();

		public PagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		public void addFragment(Fragment fragment, String title) {
			fragmentList.add(fragment);
			fragmentTitleList.add(title);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return fragmentTitleList.get(position);
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

			case R.id.back:
				finish();
				break;

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
