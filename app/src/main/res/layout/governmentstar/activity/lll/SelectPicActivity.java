package com.lanwei.governmentstar.activity.lll;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.allpic.bean.ImageFloder;
import com.lanwei.governmentstar.activity.allpic.utils.ImageLoader;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//选择图片
public class SelectPicActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = SelectPicActivity.class.getSimpleName();
	private ProgressDialog mProgressDialog;
	private GridView id_gridView;
	private SelectPicAdapter data2View;
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			mProgressDialog.dismiss();
			// 为View绑定数据
			id_gridView.setAdapter(data2View);
		}
	};
	/**
	 * 所有的图片
	 */
	private List<DrawSelect> mImgs=new ArrayList<>();
	/**
	 * 临时的辅助类，用于防止同一个文件夹的多次扫描
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();
	private ImageView back,selectpic_icon;
	private TextView title,more,selectpic_add;
	private String file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.d(TAG, "onCreate()");
		setContentView(R.layout.activity_selectpic);
		init();
		getImages();
	}
	private void init() {
		// TODO Auto-generated method stub
		id_gridView= (GridView) findViewById(R.id.id_gridView);
		back= (ImageView) findViewById(R.id.back);
		title= (TextView) findViewById(R.id.title);
		selectpic_add= (TextView) findViewById(R.id.selectpic_add);
		more= (TextView) findViewById(R.id.more);
		selectpic_icon= (ImageView) findViewById(R.id.selectpic_icon);
		back.setOnClickListener(this);
		selectpic_add.setOnClickListener(this);
		data2View=new SelectPicAdapter(mImgs,SelectPicActivity.this);
		title.setText("手机照片");
		more.setVisibility(View.INVISIBLE);
		file="";
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
	public class SelectPicAdapter extends BaseAdapter {

		List<DrawSelect> strs = null;
		LayoutInflater inflater = null;
		Context context;
		public SelectPicAdapter(List<DrawSelect> strs, Context context) {
			this.strs = strs;
			this.inflater = LayoutInflater.from(context);
			this.context=context;
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
		public View getView(final int arg0, View convertView, ViewGroup arg2) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView=inflater.inflate(R.layout.adapter_selectpic_item,null);
				holder.id_item_image= (ImageView) convertView.findViewById(R.id.id_item_image);
				holder.id_item_select= (ImageButton) convertView.findViewById(R.id.id_item_select);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			if(holder.id_item_image.getTag()!=null&&holder.id_item_image.getTag().equals(strs.get(arg0).getPath())){

			}else {
				holder.id_item_image.setImageResource( R.drawable.pictures_no);
				ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(strs.get(arg0).getPath(), holder.id_item_image);
			}

			if(strs.get(arg0).isSelect()){
				holder.id_item_select.setImageResource(R.drawable.selected);
			}else{
				holder.id_item_select.setImageResource(R.drawable.choice01);
			}
			holder.id_item_select.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					file=strs.get(arg0).getPath();
					ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(strs.get(arg0).getPath(), selectpic_icon);
					for (int i = 0; i < strs.size(); i++) {
						if(strs.get(i).isSelect()){//如果找到了单选的那个
							strs.get(i).setSelect(false);
							break;
						}
					}
					strs.get(arg0).setSelect(!strs.get(arg0).isSelect());
					notifyDataSetChanged();
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageButton id_item_select = null;
			ImageView id_item_image=null;
		}

	}
	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */
	private void getImages()
	{
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{

				String firstImage = null;

				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = SelectPicActivity.this
						.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext())
				{
					// 获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					Log.e("TAG", path);
					mImgs.add(new DrawSelect(path,false));
					// 拿到第一张图片的路径
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
					if (mDirPaths.contains(dirPath))
					{
						continue;
					} else
					{
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}
				}
				mCursor.close();
				// 扫描完成，辅助的HashSet也就可以释放内存了
				mDirPaths = null;

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);

			}
		}).start();

	}
	class DrawSelect{
		String path;
		boolean isSelect;

		public DrawSelect() {
		}

		public DrawSelect(String path, boolean isSelect) {
			this.path = path;
			this.isSelect = isSelect;
		}

		@Override
		public String toString() {
			return "DrawSelect{" +
					"path='" + path + '\'' +
					", isSelect=" + isSelect +
					'}';
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public boolean isSelect() {
			return isSelect;
		}

		public void setSelect(boolean select) {
			isSelect = select;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.back:{
				finish();
			}break;
			case R.id.selectpic_add:{
				if(!file.equals("")){
					Intent intent=new Intent();
					intent.putExtra("file",file);
					setResult(10,intent);
					finish();
				}
			}break;
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
