package com.lanwei.governmentstar.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.SelectStateActivity;
import com.lanwei.governmentstar.activity.lll.io.State;
import com.lanwei.governmentstar.activity.lll.refresh.ListViewDecoration;
import com.lanwei.governmentstar.activity.lll.refresh.MenuAdapter;
import com.lanwei.governmentstar.activity.lll.refresh.OnItemClickListener;
import com.lanwei.governmentstar.activity.lll.refresh.RefreshLoadMoreActivity;
import com.lanwei.governmentstar.bean.Condition_Shift;
import com.lanwei.governmentstar.demo.DemoAdapter;
import com.lanwei.governmentstar.utils.LogUtils;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


public class SelectStateFragment extends Fragment {
	public static SelectStateFragment fragmentDemo;
	private static final String TAG = SelectStateFragment.class.getSimpleName();
	private View rootView;
	private PullToRefreshListView fragment_selectstate_list;
	private List<String> strings;
	private State state;
	private BaseAdapter selectStateAdapter;
	private Activity mContext;
	private Condition_Shift condition =new Condition_Shift();
//	private SwipeRefreshLayout mSwipeRefreshLayout;

	private SwipeMenuAdapter mMenuAdapter;

	private   ArrayList<Condition_Shift.Data> list_seen=new ArrayList<>();
	private   ArrayList<Condition_Shift.Data> list_all=new ArrayList<>();
	private   ArrayList<Condition_Shift.Data> list_notseen=new ArrayList<>();

	private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

	private int size = 30;
	private boolean aBoolean;//是否开启加载
	/*
	 * 执行顺序先后
	 * onAttach 
	 * onCreate :保存恢复数据
	 * onCreateView
	 * onActivityCreated 具体操作
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {//当isVisibleToUser=true会触发也就是页面显示的时候
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
	}
	public static SelectStateFragment getInstance(Bundle args) {
		fragmentDemo = new SelectStateFragment();
		if (args != null) {
			fragmentDemo.setArguments(args);
		}
		return fragmentDemo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		list_all=new ArrayList<>();
//		condition=(Condition_Shift) getArguments().getSerializable("condition");
//		list_all=condition.getData();
//		Log.e("onCreateView: ",list_all.size()+"");
//					for(int i=0;i<list_all.size();i++){
//
//						if(list_all.get(i).getSc()){
//							list_seen.add(list_all.get(i));
//						}else{
//							list_notseen.add(list_all.get(i));
//						}
//					}

		list_seen=((SelectStateActivity)getActivity()).list_seen;
		list_notseen=((SelectStateActivity)getActivity()).list_notseen;

		rootView = inflater.inflate(R.layout.fragment_selectstate ,container,false);
		init();
		return rootView;
	}
	private void init() {
		// TODO Auto-generated method stub
		state= (State) getArguments().getSerializable("enum");
		aBoolean=true;
		mContext = getActivity();

//		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout);
////		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
//		mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));

		mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) rootView.findViewById(R.id.recycler_view);
		mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
		mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
		mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
//		mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
		// 添加滚动监听。
//		mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
		// 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
//		 设置菜单创建器。

//		mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
		// 设置菜单Item点击监听。
//		mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
		switch (state){
			case UnRead:{
				mMenuAdapter = new MenuAdapter(list_notseen);
//				((MenuAdapter)mMenuAdapter).setOnItemClickListener(onItemClickListener);
			}break;
			case Read:{
				mMenuAdapter = new MenuAdapter2(list_seen);
//				((MenuAdapter2)mMenuAdapter).setOnItemClickListener(onItemClickListener);
			}break;
		}
		mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		LogUtils.d(TAG, "onActivityCreated()");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.d(TAG, "onDestroy()");
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		LogUtils.d(TAG, "onDestroy()");
	}

	/**
	 * 刷新监听。
	 */
//	private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//		@Override
//		public void onRefresh() {
//			mSwipeRefreshLayout.setRefreshing(false);
//
//
//
//
//			// todo put新的数据
//		}
//	};

//	/**
//	 * 加载更多
//	 */
//	private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
//		@Override
//		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//			if (!recyclerView.canScrollVertically(1)&&aBoolean) {// 手指不能向上滑动了
//				// TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
//				Toast.makeText(getActivity(), "加载中...", Toast.LENGTH_SHORT).show();
//				size += 50;
//				for (int i = size - 50; i < size; i++) {
//					mDataList.add("我是第" + i + "个。");
//				}
//				mMenuAdapter.notifyDataSetChanged();
//			}
//		}
//	};

	/**
	 * 菜单创建器。在Item要创建菜单的时候调用。
	 */
//	private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
//		@Override
//		public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
//			int width = getResources().getDimensionPixelSize(R.dimen.item_height);
//
//			// MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
//			int height = ViewGroup.LayoutParams.MATCH_PARENT;
//			// 添加右侧的，如果不添加，则右侧不会出现菜单。
//			{
//				SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
//						.setBackgroundDrawable(R.drawable.selector_red)
//						.setText("删除") // 文字，还可以设置文字颜色，大小等。。
//						.setTextColor(Color.WHITE)
//						.setWidth(width)
//						.setHeight(height);
//				swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
//			}
//		}
//	};

//	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
//		@Override
//		public void onItemClick(int position) {
//			Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
//		}
//	};

	/**
	 * 菜单点击监听。
	 */
//	private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
//		/**
//		 * Item的菜单被点击的时候调用。
//		 * @param closeable       closeable. 用来关闭菜单。
//		 * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
//		 * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
//		 * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
//		 *                        #RIGHT_DIRECTION.
//		 */
//		@Override
//		public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
//			closeable.smoothCloseMenu();// 关闭被点击的菜单。
//
//			if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//				Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//			} else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//				Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//			}
//
//			// TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
//			if (menuPosition == 0) {// 删除按钮被点击。
//				mDataList.remove(adapterPosition);
//				mMenuAdapter.notifyItemRemoved(adapterPosition);
//			}
//		}
//	};

	public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {

		private   ArrayList<Condition_Shift.Data> list=new ArrayList<>();

		private OnItemClickListener mOnItemClickListener;

		public MenuAdapter(ArrayList<Condition_Shift.Data> titles) {
			this.list = titles;
		}

		public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
			this.mOnItemClickListener = onItemClickListener;
		}

		@Override
		public int getItemCount() {
			return list == null ? 0 : list.size();
		}

		@Override
		public View onCreateContentView(ViewGroup parent, int viewType) {
			return LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selectstate_item, parent, false);
		}

		@Override
		public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
			DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
			viewHolder.mOnItemClickListener = mOnItemClickListener;
			return viewHolder;
		}

		@Override
		public void onBindViewHolder(DefaultViewHolder holder, int position) {
			holder.adapter_selectstate_name.setText(list.get(position).getOpName());
			holder.adapter_selectstate_title.setText(list.get(position).getOpDept());

			if(position==list.size()-1){
				holder.decoration.setVisibility(View.GONE);
			}else{
				holder.decoration.setVisibility(View.VISIBLE);
			}

		}

		 class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			 TextView adapter_selectstate_name,adapter_selectstate_title,adapter_selectstate_tixing = null;
			 View decoration;
			OnItemClickListener mOnItemClickListener;

			public DefaultViewHolder(View itemView) {
				super(itemView);
				itemView.setOnClickListener(this);
				adapter_selectstate_name= (TextView) itemView.findViewById(R.id.adapter_selectstate_name);
				adapter_selectstate_title= (TextView) itemView.findViewById(R.id.adapter_selectstate_title);
				adapter_selectstate_tixing= (TextView) itemView.findViewById(R.id.adapter_selectstate_tixing);
				decoration=  itemView.findViewById(R.id.decoration);
			}
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(getAdapterPosition());
				}
			}
		}

	}
	public class MenuAdapter2 extends SwipeMenuAdapter<MenuAdapter2.DefaultViewHolder> {

		private   ArrayList<Condition_Shift.Data> list=new ArrayList<>();

		private OnItemClickListener mOnItemClickListener;

		public MenuAdapter2(ArrayList<Condition_Shift.Data> titles) {
			this.list = titles;
		}

		public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
			this.mOnItemClickListener = onItemClickListener;
		}

		@Override
		public int getItemCount() {
			return list == null ? 0 : list.size();
		}

		@Override
		public View onCreateContentView(ViewGroup parent, int viewType) {
			return LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selectstate_item2, parent, false);
		}

		@Override
		public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
			DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
			viewHolder.mOnItemClickListener = mOnItemClickListener;
			return viewHolder;
		}

		@Override
		public void onBindViewHolder(DefaultViewHolder holder, int position) {
			holder.adapter_selectstate2_name.setText(list.get(position).getOpName());
			holder.adapter_selectstate2_title.setText(list.get(position).getOpDept());
			holder.adapter_selectstate2_time.setText(list.get(position).getOpDate());

			if(list.get(position).getSc()){
				holder.adapter_selectstate2_shoucang.setVisibility(View.VISIBLE);
			}else{
				holder.adapter_selectstate2_shoucang.setVisibility(View.GONE);
			}

			if(list.get(position).getZf()){
				holder.adapter_selectstate2_zhuanfa.setVisibility(View.VISIBLE);
			}else{
				holder.adapter_selectstate2_zhuanfa.setVisibility(View.GONE);
			}

			if(position==list.size()-1){
				holder.decoration.setVisibility(View.GONE);
			}else{
				holder.decoration.setVisibility(View.VISIBLE);
			}


		}

		class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			TextView adapter_selectstate2_name,adapter_selectstate2_title,adapter_selectstate2_time,adapter_selectstate2_shoucang,adapter_selectstate2_zhuanfa = null;
			OnItemClickListener mOnItemClickListener;
			private View decoration;

			public DefaultViewHolder(View itemView) {
				super(itemView);
				itemView.setOnClickListener(this);
				adapter_selectstate2_name= (TextView) itemView.findViewById(R.id.adapter_selectstate2_name);
				adapter_selectstate2_title= (TextView) itemView.findViewById(R.id.adapter_selectstate2_title);
				adapter_selectstate2_time= (TextView) itemView.findViewById(R.id.adapter_selectstate2_time);
				adapter_selectstate2_shoucang= (TextView) itemView.findViewById(R.id.adapter_selectstate2_shoucang);
				adapter_selectstate2_zhuanfa= (TextView) itemView.findViewById(R.id.adapter_selectstate2_zhuanfa);
				decoration= itemView.findViewById(R.id.decoration);
			}
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(getAdapterPosition());
				}
			}
		}

	}
}
