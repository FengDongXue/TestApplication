package com.lanwei.governmentstar.activity.zyx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.ChooseReceiverNBActivity;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.Finish_Convey_Activity;
import com.lanwei.governmentstar.activity.SignUp_Document_Activity;
import com.lanwei.governmentstar.activity.Watch_Notification_Activity;
import com.lanwei.governmentstar.activity.lll.DocumentFileHandleActivity;
import com.lanwei.governmentstar.activity.lll.DocumentHandleActivity;
import com.lanwei.governmentstar.activity.lll.DocumentSelectActivity;
import com.lanwei.governmentstar.activity.lll.DocumentToDoActivity;
import com.lanwei.governmentstar.activity.lll.DocumentUndertakeActivity;
import com.lanwei.governmentstar.activity.lll.refresh.OnItemClickListener;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Myhand;
import com.lanwei.governmentstar.bean.MyhandList;
import com.lanwei.governmentstar.bean.Notification_List;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ScreenUtils;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.lanwei.governmentstar.view.WrapContentLinearLayoutManager;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/31.
 */

public class MyhandActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private ListView listview_myhand;
    private TextView screen;
    private PopupWindow popupWindow;
    //刷新框架
    private Activity mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MenuAdapter mMenuAdapter;
    private List<Myhand> mDataList;
    private List<Myhand> dataList = new ArrayList<>();

    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    int x, y;
    private int size = 30;
    private boolean aBoolean = false; //是否开启加载
    private int pageNo = 1;   //当前是第几页
    private int pageCount = 1;   //总页数
    private TextView line;
    private TextView cbgw; //承办
    private String docType = "";
    private String search = "";
    private String opState = "";
    private TextView cygw;
    private TextView inform_sort;
    private EditText tvsearch;
    private String opid;
    private String orderBy = "";
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private RelativeLayout rv;
    private ProgressBar pb;
    private SharedPreferences amount_ShortcutBadger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断系统SDK版本，看看是否支持沉浸式
        if (Build.VERSION.SDK_INT >= 21){
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        }

        setContentView(R.layout.activity_myhand);

        title = (TextView) findViewById(R.id.tv_address);
        ImageView back = (ImageView) findViewById(R.id.back);
//      screen = (TextView) findViewById(R.id.screen);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        tvsearch = (EditText) findViewById(R.id.search);
        cbgw = (TextView) findViewById(R.id.cbgw);//传阅公文
        cygw = (TextView) findViewById(R.id.cygw);//传阅公文
        inform_sort = (TextView) findViewById(R.id.inform_sort);//传阅公文
        pb = (ProgressBar) findViewById(R.id.pb);//传阅公文
        rv = (RelativeLayout) findViewById(R.id.rv);//传阅公文
        cbgw.setOnClickListener(this);
        cygw.setOnClickListener(this);
        inform_sort.setVisibility(View.VISIBLE);
        inform_sort.setOnClickListener(this);
        rv.setOnClickListener(this);

        title.setVisibility(View.VISIBLE);
        title.setText("收文传阅");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);

        back.setOnClickListener(this);
//        screen.setOnClickListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        tvsearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {//修改回车键功能
                    final String searchCt = tvsearch.getText().toString();
                    mDataList.clear();
                    if (dataList != null) {
                        search = searchCt;
                        getdata("1");

                    }
                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    return true;
                }
                return false;
            }
        });
        initPull();

        // Mugen的用法
        Mugen.with(mSwipeMenuRecyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {

                pb.setVisibility(View.VISIBLE);

                getdata((pageNo+1) + "");

            }

            @Override
            public boolean isLoading() {
                boolean isLoading;
                if (pb.getVisibility() == View.INVISIBLE) {
                    isLoading = false;
                } else {
                    isLoading = true;
                }

                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return pageCount<=pageNo;
            }
        }).start();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent2 =new Intent();
            setResult(20,intent2);
            Log.e("desfdasdf1111 ","dgfdsgdg");
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initPull() {
        aBoolean = false;
        mContext = this;
        mDataList = new ArrayList<Myhand>();
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mSwipeMenuRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mMenuAdapter = new MenuAdapter(mDataList, tvsearch.getText().toString());
        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
        if (mDataList != null) {
            getdata(pageNo + "");
        } else {
            Toast.makeText(MyhandActivity.this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
        }

    }

    private void change() {
        // 弹出popupwindow前，调暗屏幕的透明度
        WindowManager.LayoutParams lp2 = getWindow().getAttributes();
        lp2.alpha = (float) 0.8;
        getWindow().setAttributes(lp2);
    }

    private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent2 =new Intent();
                setResult(20,intent2);
                Log.e("desfdasdf1111 ","dgfdsgdg");
                finish();
                break;

            case R.id.inform_sort:

                change();
                // 加载popupwindow的布局
                View view2 = getLayoutInflater().inflate(R.layout.message_sortaddpop, null);
                popupWindow = new PopupWindow(view2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 点击屏幕之外的区域可否让popupwindow消失
                int windowPos[] = calculatePopWindowPos(view2, view2);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());
                // 设置popupwindow的显示位置

                popupWindow.showAtLocation(inform_sort, Gravity.RIGHT | Gravity.TOP, 30, rv.getMeasuredHeight() * 3 / 2);
//                popupWindow.showAsDropDown(inform_sort,0,30);


                TextView lastest_receive = (TextView) view2.findViewById(R.id.create_offer);
                TextView long_receive = (TextView) view2.findViewById(R.id.scan_zxing);


                lastest_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        orderBy ="desc";
                        mDataList.clear();
                        if (mDataList != null) {

                            getdata("1");
//                    Log.e("如果一天退热和沟通如何",""+mDataList.get(0).getDocTitle());
                            mMenuAdapter = new MenuAdapter(mDataList, tvsearch.getText().toString());
                            mMenuAdapter.setOnItemClickListener(onItemClickListener);
                            mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
                            mMenuAdapter.notifyDataSetChanged();

                        }



                    }
                });

                long_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        orderBy ="asc";

                        mDataList.clear();
                        if (mDataList != null) {

                            getdata("1");
//                    Log.e("如果一天退热和沟通如何",""+mDataList.get(0).getDocTitle());
                            mMenuAdapter = new MenuAdapter(mDataList, tvsearch.getText().toString());
                            mMenuAdapter.setOnItemClickListener(onItemClickListener);
                            mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
                            mMenuAdapter.notifyDataSetChanged();

                        }

                    }
                });


                break;

//            case R.id.screen:
//
//                // 弹出popupwindow前，调暗屏幕的透明度
//                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
//                lp2.alpha = (float) 0.8;
//                getWindow().setAttributes(lp2);
//
//                // 加载popupwindow的布局
//                View view = getLayoutInflater().inflate(R.layout.activity_myhandpop, hardwork);
//                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//                // 初始化popupwindow的点击控件
//                TextView tv_look = (TextView) view.findViewById(R.id.tv_look);
//                TextView tv_stop = (TextView) view.findViewById(R.id.tv_stop);
//
//                // 点击屏幕之外的区域可否让popupwindow消失
//                popupWindow.setFocusable(true);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setOnDismissListener(new PoponDismissListener());
//                // 注册popupwindow里面的点击事件
//                tv_look.setOnClickListener(this);
//                tv_stop.setOnClickListener(this);
//
//                // 设置popupwindow的显示位置
//                popupWindow.showAsDropDown(screen, -220, 35);
//
//                break;

            case R.id.cbgw://仅显示承办
                tvsearch.setText("");
                tvsearch.clearFocus();
                mDataList.clear();
                mMenuAdapter.notifyDataSetChanged();
                if (mDataList != null) {


                    if(!docType.equals("0")){
                        docType = "0";
                        cbgw.setTextColor(Color.parseColor("#00a6e4"));
                        cygw.setTextColor(Color.parseColor("#333333"));
                    }else{
                        docType = "";
                        cbgw.setTextColor(Color.parseColor("#333333"));
                        cygw.setTextColor(Color.parseColor("#333333"));
                    }


                    getdata("1");
                    mMenuAdapter = new MenuAdapter(mDataList, tvsearch.getText().toString());
                    mMenuAdapter.setOnItemClickListener(onItemClickListener);
                    mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
                    mMenuAdapter.notifyDataSetChanged();

                } else {
//                    Toast.makeText(this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cygw://仅显示传阅

                mDataList.clear();
                tvsearch.setText("");
                tvsearch.clearFocus();
                mMenuAdapter.notifyDataSetChanged();
                if (mDataList != null) {


                    if(!docType.equals("1")){
                        docType = "1";
                        cygw.setTextColor(Color.parseColor("#00a6e4"));
                        cbgw.setTextColor(Color.parseColor("#333333"));
                    }else{
                        docType = "";
                        cygw.setTextColor(Color.parseColor("#333333"));
                        cbgw.setTextColor(Color.parseColor("#333333"));
                    }

                    getdata("1");
//                    Log.e("如果一天退热和沟通如何",""+mDataList.get(0).getDocTitle());
                    mMenuAdapter = new MenuAdapter(mDataList, tvsearch.getText().toString());
                    mMenuAdapter.setOnItemClickListener(onItemClickListener);
                    mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
                    mMenuAdapter.notifyDataSetChanged();

                } else {
//                    Toast.makeText(this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mDataList.clear();
                    final String searchCt = tvsearch.getText().toString();
                    search = searchCt;

                    getdata("1");
                    mMenuAdapter.notifyDataSetChanged();
                }
            }, 0);
        }
    };

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
//            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                mDataList.remove(adapterPosition);
                mMenuAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };

    private void getdata(final String pageno) {
//        String opid = (String) SharedPreferencesUtil.getData(this, "login", "18537d2f-5466-4279-b4be-ab66e2c96d7b");
//        String defString = PreferencesManager.getInstance(MyhandActivity.this,"accountBean").get("jsonStr");
//        Gson gson=new Gson();
//        Logging_Success bean=gson.fromJson(defString,Logging_Success.class);
//
//        opid = bean.getData().getOpId();
        opid = new GetAccount(this).opId();
        search = tvsearch.getText().toString();
        //获取收文的数据
        RetrofitHelper.getInstance().getMyhandInfo(pageno, opid, search, docType, opState,orderBy, new CallBackYSAdapter() {

            @Override
            protected void showErrorMessage(String message) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(MyhandActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void parseJson(String data) {
                Log.e("data", data);
                if (data != null) {
                    pb.setVisibility(View.INVISIBLE);
                    Gson gson = new Gson();
                    MyhandList myhandList = gson.fromJson(data, MyhandList.class);
                    pageCount = myhandList.getPageCount();
                    pageNo = myhandList.getPageNo();

                    dataList = myhandList.getData();
//                    mDataList.clear();
                    if (dataList != null) {
                        for (int i = 0; i < dataList.size(); i++) {
                            mDataList.add(dataList.get(i));
                        }
                    } else {
                        return;
                    }
                    if (Integer.valueOf(pageNo) == 1) {
                        mMenuAdapter = new MenuAdapter(mDataList, tvsearch.getText().toString());
                        mMenuAdapter.setOnItemClickListener(onItemClickListener);
                        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
                    }
                    if (pageCount > Integer.parseInt(pageno)) {
                        aBoolean = true;//判断对是否有下一页进行设置
                    } else {
                        aBoolean = false;//判断对是否有下一页进行设置
                    }
                    mMenuAdapter.notifyDataSetChanged();
                }else{
                    mDataList.clear();

                }
            }
        });
    }

    public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {
        private String search;
        private List<Myhand> dataList;
        private int thisPosition = -1;
        private int clickPosition = -1;   //定义一个索引记录点击的条目
        private OnItemClickListener mOnItemClickListener;
        private TextView title;
        private TextView name;
        private TextView state;

        public MenuAdapter(List<Myhand> dataList, String s) {
            this.dataList = dataList;
            this.search = s;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_myhand_item, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            title = (TextView) realContentView.findViewById(R.id.title);
            name = (TextView) realContentView.findViewById(R.id.name);
            state = (TextView) realContentView.findViewById(R.id.state);
            DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
            viewHolder.mOnItemClickListener = mOnItemClickListener;
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final DefaultViewHolder holder, final int position) {

            if (position == 0) {
                holder.decoration.setVisibility(View.GONE);
            } else {
                holder.decoration.setVisibility(View.VISIBLE);
            }
            holder.itemView.setSelected(false);
            if (thisPosition != position) {
                holder.line.setVisibility(View.INVISIBLE);
            }else{
                holder.line.setVisibility(View.VISIBLE);
            }

            if (dataList.get(position).getDocStatus() == null) {
                holder.title.setTextColor(Color.parseColor("#666666"));
            } else if (dataList.get(position).getDocStatus() != null) {
                holder.title.setTextColor(Color.parseColor("#26ade6"));
            }

            // TODO: 2017/5/13 改变关键字的颜色
            String tit = dataList.get(position).getDocTitle();
            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), tit, search);
            holder.title.setText(spannableString);
            holder.date.setText("收文日期:"+dataList.get(position).getOpCreateTime());

            holder.urgent.setVisibility(View.INVISIBLE);

            if(dataList.get(position).getDocMatter().equals("1")){
                holder.urgent.setVisibility(View.VISIBLE);
            }

            holder.name.setText(dataList.get(position).getOpCreateName());
            final String opState = dataList.get(position).getOpState();

            switch (opState) {
                case "-2":
                    holder.state.setText("状态 : 已签收");
//                    holder.state.setTextColor(Color.parseColor("#26ade6"));
                    break;
                case "-1":
                    holder.state.setText("状态 : 临时收文");
//                    holder.state.setTextColor(Color.parseColor("#26ade6"));
                    break;
                case "0":
                    holder.state.setText("状态 : 等待签收");
                    holder.state.setTextColor(Color.parseColor("#d29c0d"));
                    break;
                case "1":
                    holder.state.setText("状态 : 等待拟办");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
                case "2":
                    holder.state.setText("状态 : 等待批示");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
                case "3":
                    holder.state.setText("状态 : 等待阅办");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
                case "4":
                    String docStatus = (String) dataList.get(position).getDocStatus();
                    if (docStatus != null && docStatus.equals("bs")) {
                        holder.state.setText("状态 : 等待办理");
                    } else if (docStatus != null && docStatus.equals("zf")) {
                        holder.state.setText("状态 : 等待办理");
                    } else {
                        holder.state.setText("状态 : 等待承办");
                    }

                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
                case "5":
                    holder.state.setText("状态 : 等待办理");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
                case "6":
                    holder.state.setText("状态 : 等待归档");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
                case "7":
                    holder.state.setText("状态 : 已归档");
                    holder.state.setTextColor(Color.parseColor("#d29c0d"));
                    break;
                case "8":
                    holder.state.setText("状态 : 已失效");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
                case "9":
                    holder.state.setText("状态 : 已撤回");
                    holder.state.setTextColor(Color.parseColor("#d29c0d"));
                    break;
                default:
                    holder.state.setText("状态 : 已完成");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    thisPosition = holder.getLayoutPosition();
                    holder.line.setVisibility(View.VISIBLE);
                    mMenuAdapter.notifyDataSetChanged();
//                    if(dataList.get(position).getOpState().equals("0")){
//                        intent = new Intent(MyhandActivity.this, SignUp_Document_Activity.class);
//                        intent.putExtra("opId", dataList.get(position).getOpId());
//                        intent.putExtra("type", "gwcy");
//                        startActivityForResult(intent, 520);
//                        Log.e("广东佛山广东佛山的广泛大概", "onClick: " );
//                        return;
//                    }
                    if (dataList.get(position).getDocStatus() == null) {
                        intent = new Intent(MyhandActivity.this, Finish_Convey_Activity.class);
                        intent.putExtra("opId", dataList.get(position).getOpId());
                        startActivity(intent);
                        return;
                    }

                    switch (dataList.get(position).getDocStatus()) {
//                    switch (opState) {
                        case "": {//临时收文
                            Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case "1": {//等待签收
                            Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case "nb": {//拟办公文*
//                            DocumentToDoActivity
                            intent = new Intent(MyhandActivity.this, DocumentToDoActivity.class);
                            intent.putExtra("present", dataList.get(position).getOpState());
                            intent.putExtra("state","拟办");
                        }
                        break;
                        case "ps": {//批示公文*
                            intent = new Intent(MyhandActivity.this, DocumentHandleActivity.class);
                            intent.putExtra("present", dataList.get(position).getOpState());
                            intent.putExtra("type", "2");
                            intent.putExtra("tjms", "cycl");
                            intent.putExtra("state","批示");
//                            intent=new Intent(MyhandActivity.this, Finish_Convey_Activity.class);
                        }
                        break;
                        case "yb": {//阅办公文*
                            intent = new Intent(MyhandActivity.this, DocumentUndertakeActivity.class);
                            intent.putExtra("type", "1");
                            intent.putExtra("state","阅办");
                            intent.putExtra("present", dataList.get(position).getOpState());
                        }
                        break;
                        case "cb": {//承办
                            //跳转承办
                            intent = new Intent(MyhandActivity.this, DocumentUndertakeActivity.class);
                            intent.putExtra("type", "0");
                            intent.putExtra("DocStatus_type", "0");
                            intent.putExtra("type5", "chengban");
                            intent.putExtra("state","承办");
                            intent.putExtra("present", dataList.get(position).getOpState());
                        }
                        break;
                        case "bs": {//办理
                            intent = new Intent(MyhandActivity.this, DocumentFileHandleActivity.class);
                            intent.putExtra("present", dataList.get(position).getOpState());
                            intent.putExtra("type5", "banli");
                            intent.putExtra("state","办事");
                        }
                        break;
                        case "zf": {//转发
                            //跳转转发
                            intent = new Intent(MyhandActivity.this, DocumentSelectActivity.class);
                            intent.putExtra("present", "6");
                            intent.putExtra("type5", "zhuanfa");
                            intent.putExtra("state","转发");

                        }
                        break;
                        case "xb": {//协办
                            intent = new Intent(MyhandActivity.this, DocumentSelectActivity.class);
                            intent.putExtra("present", dataList.get(position).getOpState());
                            intent.putExtra("type5", "xieban");
                            intent.putExtra("state","协办");
                        }
                        break;
                        case "gwcy": {//签收
                            intent = new Intent(MyhandActivity.this, SignUp_Document_Activity.class);
                            intent.putExtra("present", dataList.get(position).getOpState());
                            intent.putExtra("type", dataList.get(position).getDocStatus());
                        }
                        break;
                        case "gwqs": {//签收
                            intent = new Intent(MyhandActivity.this, SignUp_Document_Activity.class);
                            intent.putExtra("present", dataList.get(position).getOpState());
                            intent.putExtra("type", dataList.get(position).getDocStatus());
                        }
                        break;

                    }

                    if (intent != null) {
                        // 改变标记，以便让下个界面执行一段代码块，改变相对应的标记，处理成功后移除该条目
                        change_position = getSharedPreferences("summit_position", 0);
                        editor = change_position.edit();
                        editor.putBoolean("allow", true);
                        editor.commit();
                        intent.putExtra("opId", dataList.get(position).getOpId());
                        intent.putExtra("OpState", dataList.get(position).getOpState());
                        intent.putExtra("DocStatus", dataList.get(position).getDocStatus());
                        startActivityForResult(intent, 520);
                    }

                }
            });
        }

        class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            OnItemClickListener mOnItemClickListener;

            TextView title;
            TextView name;
            TextView state;
            TextView line;
            ImageView urgent;
            View decoration;
            TextView date;

            public DefaultViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                title = (TextView) itemView.findViewById(R.id.title);
                name = (TextView) itemView.findViewById(R.id.name);
                state = (TextView) itemView.findViewById(R.id.state);
                line = (TextView) itemView.findViewById(R.id.line);
                date = (TextView) itemView.findViewById(R.id.time);
                urgent = (ImageView) itemView.findViewById(R.id.urgent);
                decoration = itemView.findViewById(R.id.decoration);
            }

            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            }
        }
    }

    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha = (float) 1;
            getWindow().setAttributes(p);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // 防止点击back键会崩的问题
        if (requestCode == 520 && resultCode == 520) {

            String opId = data.getStringExtra("opId");
            String opState = data.getStringExtra("opState");
            String docStatus = data.getStringExtra("docStatus");

            if (!mDataList.isEmpty()) {
                for (int i = 0; i < mDataList.size(); i++) {
                    if (mDataList.get(i).getOpId().equals(opId)) {
                        mDataList.get(i).setOpState(opState);
                        if (docStatus == null || docStatus.equals("null"))
                            Log.d("TAG", data.getStringExtra("docStatus") + "------------------------");
                        mDataList.get(i).setDocStatus(null);
                    }
                }
            }

            mMenuAdapter.notifyDataSetChanged();
            return_amount();
            // mDataList;
        } else if (resultCode == 0) {

        }else if(resultCode == 530){

            if(mMenuAdapter.thisPosition != -1){
                mDataList.remove(mMenuAdapter.thisPosition) ;
                mMenuAdapter.thisPosition =-1;
                mMenuAdapter.notifyDataSetChanged();
            }

        }
    }

    void return_amount() {
        String defString3 = PreferencesManager.getInstance(MyhandActivity.this, "accountBean").get("jsonStr");
        Gson gson3 = new Gson();
        Logging_Success bean3 = gson3.fromJson(defString3, Logging_Success.class);
        GovernmentApi api3 = HttpClient.getInstance().getGovernmentApi();
        Call<Return_Amount> call2 = api3.return_amount_daiban(bean3.getData().getOpId());
        call2.enqueue(new Callback<Return_Amount>() {
            @Override
            public void onResponse(Call<Return_Amount> call, Response<Return_Amount> response) {
                if (response.body().getData() != null && !response.body().getData().equals("")) {

                    amount_ShortcutBadger = getSharedPreferences("amount_ShortcutBadger", 0);
                    Log.e("number",amount_ShortcutBadger.getInt("number",0)+"");
                    ShortcutBadger.applyCount(MyhandActivity.this, response.body().getData().getManage_num());
                    amount_ShortcutBadger.edit().putInt("number",response.body().getData().getManage_num()).commit();

                }
            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {
                Toast.makeText(MyhandActivity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //匹配搜索的关键字
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
}
