package com.lanwei.governmentstar.activity.gwnz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.lll.refresh.OnItemClickListener;
import com.lanwei.governmentstar.activity.zyx.MyhandActivity;
import com.lanwei.governmentstar.bean.Document;
import com.lanwei.governmentstar.bean.DocumentList;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ScreenUtils;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.StatusBarUtils;
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

public class DocumentActivity extends BaseActivity implements View.OnClickListener {

    private TextView screen;
    private PopupWindow popupWindow;
    //刷新框架
    private Activity mContext;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MenuAdapter mMenuAdapter;

    private List<Document> mDataList;

    private List<Document> dataList = new ArrayList<>();
    String search = "";
    String opState = "";
    String orderBy = "desc";// 默认降序

    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    int x, y;
    private int size = 30;
    private boolean aBoolean = false;//是否开启加载
    private int pageNo = 1;   //当前是第几页
    private int pageCount = 1;   //总页数
    private EditText tvsearch;
    String opid;
    private TextView tvLeft;
    private String searchCt;
    private TextView waitSort;
    private RelativeLayout rv;
    private TextView title;
    private SharedPreferences amount_ShortcutBadger;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_document);

        title = (TextView) findViewById(R.id.tv_address);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        tvsearch = (EditText) findViewById(R.id.search);
        waitSort = (TextView) findViewById(R.id.wait_sort);

        rv = (RelativeLayout) findViewById(R.id.rv); //公文拟制列表

        title.setVisibility(View.VISIBLE);
        title.setText("公文拟制");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        waitSort.setVisibility(View.VISIBLE);

        waitSort.setOnClickListener(this);
        back.setOnClickListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        tvsearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {//修改回车键功能
                    //关键字
                    searchCt = tvsearch.getText().toString();

                    mDataList.clear();
                    if (dataList != null) {
                        search = searchCt;
                        getdata("1");
                        View view = getWindow().peekDecorView();
                        if (view != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    } else {
                        Toast.makeText(DocumentActivity.this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
        initPull();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initPull() {
        aBoolean = false;
        mContext = this;
        mDataList = new ArrayList<Document>();
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        // 添加滚动监听。
        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        if (mDataList != null) {
            getdata(pageNo + "");
        } else {
            Toast.makeText(DocumentActivity.this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
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
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        /**
         * @param recyclerView
         * @param dx
         * @param dy
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1) && aBoolean) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
//                Toast.makeText(DocumentHQActivity.this, "加载中...", Toast.LENGTH_SHORT).show();
                size += 50;
                //如果当前页数小于总页数
                Log.e("高浮雕鬼地方个鬼地方高浮雕", "没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。");

                if (pageNo < pageCount) {
                    pageNo += 1;
                    if (pageNo == pageCount) {
                        pageNo = pageCount;
                    }
//                    getdata(pageNo+"");
                    getdata(String.valueOf(pageNo));
                } else {
//                    Toast.makeText(DocumentHQActivity.this, "真的没有更多了~~", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    };

    private void getdata(final String pageno) {
        int i = 1;
        Log.e("aaaa", String.valueOf(i += 1));
        opid = new GetAccount(this).opId();
        search = tvsearch.getText().toString();

        //获取收文的数据
        RetrofitHelper.getInstance().getDocumentInfo(pageno, opid, search, opState, orderBy, new CallBackYSAdapter() {

            @Override
            protected void showErrorMessage(String message) {
                Log.e("mes", message);
            }

            @Override
            protected void parseJson(String data) {
                Log.e("data", data);
                if (data != null) {
                    Gson gson = new Gson();
                    DocumentList documentList = gson.fromJson(data, DocumentList.class);
                    pageCount = documentList.getPageCount();
                    pageNo = documentList.getPageNo();

                    dataList = documentList.getData();

                    if (dataList != null) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Log.d("tag", ">>>>>>>>>>" + dataList.get(i).getDocStatus() + "-------" + dataList.get(i).getDocTitle() + "----" + dataList.get(i).getOpState());
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
                } else {
                    mDataList.clear();

                }
            }
        });
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent2 =new Intent();
                setResult(20,intent2);
                Log.e("desfdasdf1111 ","dgfdsgdg");
                finish();
                break;
            case R.id.wait_sort:  //排序
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

                popupWindow.showAtLocation(waitSort, Gravity.RIGHT | Gravity.TOP, 30, rv.getMeasuredHeight() * 3 / 2);

                TextView lastest_receive = (TextView) view2.findViewById(R.id.create_offer);
                TextView long_receive = (TextView) view2.findViewById(R.id.scan_zxing);


                lastest_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
//                        Toast.makeText(DocumentHQActivity.this, "按照接收最近时间排序", Toast.LENGTH_SHORT).show();
                        orderBy = "asc";
                        mDataList.clear();
                        getdata("1");
//                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

                long_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
//                        Toast.makeText(DocumentHQActivity.this, "按照接收最久时间排序", Toast.LENGTH_SHORT).show();
                        mDataList.clear();
                        orderBy = "desc";
                        getdata("1");
//                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

                break;
        }
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

    private void change() {
        // 弹出popupwindow前，调暗屏幕的透明度
        WindowManager.LayoutParams lp2 = getWindow().getAttributes();
        lp2.alpha = (float) 0.8;
        getWindow().setAttributes(lp2);
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


    /**
     * 左滑删除菜单
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
     * 左滑删除监听
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

    //公文拟制适配器
    public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {

        private String search;
        private List<Document> datalist;
        private int thisPosition = -1;
        private int clickPosition = -1;   //定义一个索引记录点击的条目
        private OnItemClickListener mOnItemClickListener;
        private TextView name;
        private TextView title;
        private TextView time;
        private TextView state;
        private ImageView urgent;


        public MenuAdapter(List<Document> datalist, String s) {
            this.datalist = datalist;
            this.search = s;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public int getItemCount() {
            return datalist == null ? 0 : datalist.size();
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_myhand_item, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            name = (TextView) realContentView.findViewById(R.id.name);
            title = (TextView) realContentView.findViewById(R.id.title);
            time = (TextView) realContentView.findViewById(R.id.time);
            state = (TextView) realContentView.findViewById(R.id.state);
            urgent = (ImageView) realContentView.findViewById(R.id.urgent);
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

            if (thisPosition != position) {
                holder.line.setVisibility(View.INVISIBLE);
            }

            String opType = datalist.get(position).getOpType();
            if (opType.equals("0")) {
                holder.urgent.setVisibility(View.INVISIBLE);
            } else {
                holder.urgent.setVisibility(View.VISIBLE);
            }

            holder.time.setText("起草时间 : " + datalist.get(position).getOpCreateTime());
            holder.name.setText("起草人 : " + datalist.get(position).getOpCreateName());


            // TODO: 2017/5/13 改变关键字的颜色
            String tit = datalist.get(position).getDocTitle();
            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), tit, search);
            holder.title.setText(spannableString);

            String opState = datalist.get(position).getOpState();
            Log.e("TAG", opState + "<-----------------------------------------------------------");
            String docStatus = datalist.get(position).getDocStatus();
            Log.e("TAG", docStatus + "<-----docStatus----------------docStatus--------------------------------------");

            if (docStatus.equals("1")) {
                holder.title.setTextColor(Color.parseColor("#666666"));
            } else if (docStatus.equals("0")) {
                holder.title.setTextColor(Color.parseColor("#26ade6"));
            }


            switch (opState) {
                case "-1":
                    holder.state.setText("状态 : 临时拟制");
                    break;

                case "0":
                    holder.state.setText("状态 : 被驳回");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "1":
                    holder.state.setText("状态 : 等待审核");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "2":
                    holder.state.setText("状态 : 等待审阅");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "3":
                    holder.state.setText("状态 : 等待校对");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "4":
                    holder.state.setText("状态 : 等待签发");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "5":
                    holder.state.setText("状态 : 等待会签");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "6":
                    holder.state.setText("状态 : 等待核发");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "7":
                    holder.state.setText("状态 : 已完成");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                case "8":
                    holder.state.setText("状态 : 已归档");
                    holder.state.setTextColor(Color.parseColor("#d29c0d"));
                    break;

                case "9":
                    holder.state.setText("状态 : 已终止");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;

                default:
                    holder.state.setText("状态 : 已完成了吗");
                    holder.state.setTextColor(Color.parseColor("#999999"));
                    break;
            }


//            //子条目点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                /**
                 * @param v
                 */
                @Override
                public void onClick(View v) {

                    Log.d("TAG", datalist.get(position).getDocStatus() + "<<<<<<<<<");
                    if (datalist.get(position).getDocStatus().equals("1")) {
                        Toast.makeText(getApplicationContext(), "暂无权限处理该公文", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(DocumentActivity.this, DocumentDetailsActivity.class);
                        if (intent1 != null) {
                            intent1.putExtra("opId", datalist.get(position).getOpId());
                            startActivity(intent1);
                            return;
                        }
                    }
//                    Toast.makeText(DocumentHQActivity.this, "点击事件呢", Toast.LENGTH_SHORT).show();

                    if (thisPosition != -1) {
                        clickPosition = thisPosition;
                        mMenuAdapter.notifyItemChanged(clickPosition);
                    }
                    thisPosition = holder.getLayoutPosition();
                    holder.line.setVisibility(View.VISIBLE);
                    Intent intent = null;

                    //将点击条目的id保存
                    SharedPreferencesUtil.saveData(DocumentActivity.this, "DocumentItemId", datalist.get(position).getOpId());

                    String opId = datalist.get(position).getOpId();
                    String docStatus = datalist.get(position).getDocStatus();
                    int opState = Integer.parseInt(datalist.get(position).getOpState());
                    switch (opState) {
                        case -1: {//临时拟制
                            Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 0: {//被驳回 todo
                            Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 1: {//等待审核
                            intent = new Intent(DocumentActivity.this, DocumentSHActivity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                            intent.putExtra("state","审核");
                        }
                        break;

                        case 2: {//等待审阅
                            intent = new Intent(DocumentActivity.this, DocumentSYActivity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                            intent.putExtra("position", position);
                            intent.putExtra("state","审阅");
                        }
                        break;
                        case 3: {//等待校对  　todo
                            intent = new Intent(DocumentActivity.this, DocumentJDActivity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                            intent.putExtra("state","校对");

                        }
                        break;
                        case 4: {//等待签发
                            intent = new Intent(DocumentActivity.this, DocumentQFActivity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                            intent.putExtra("state","签发");
                        }
                        break;
                        case 5: {//等待会签
                            intent = new Intent(DocumentActivity.this, DocumentHQActivity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                            intent.putExtra("state","会签");
                        }
                        break;
                        case 6: {//等待核发
                            intent = new Intent(DocumentActivity.this, DocumentApproveActivity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                            intent.putExtra("state","核发");
                        }
                        break;
                        case 7: {//已完成
                            intent = new Intent(DocumentActivity.this, Process2_Activity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                        }
                        break;
                        case 8: {//已归档
                            intent = new Intent(DocumentActivity.this, Process2_Activity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                        }
                        break;
                        case 9: {//已终止
                            intent = new Intent(DocumentActivity.this, Process2_Activity.class);
                            intent.putExtra("Document", datalist.get(position));
                            intent.putExtra("NBopId", opId);
                        }
                        break;
                    }
                    if (intent != null)
                        intent.putExtra("opId", datalist.get(position).getOpId());

                    startActivityForResult(intent, 520);
                }
            });
        }

        //公文拟制ViewHolder
        class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            OnItemClickListener mOnItemClickListener;

            TextView title;
            TextView name;
            TextView time;
            TextView state;
            ImageView urgent;
            TextView line;
            View decoration;

            public DefaultViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                title = (TextView) itemView.findViewById(R.id.title);
                urgent = (ImageView) itemView.findViewById(R.id.urgent);
                name = (TextView) itemView.findViewById(R.id.name);
                time = (TextView) itemView.findViewById(R.id.time);
                state = (TextView) itemView.findViewById(R.id.state);
                line = (TextView) itemView.findViewById(R.id.line);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "bensil <<<<<<<<<<<<");
        if (resultCode == 520) {
            String opId = data.getStringExtra("opId");
            String opState = data.getStringExtra("opState");
            String docStatus = data.getStringExtra("docStatus");
            Log.e("TAG", "bensil2 <<<<<<<<<<<<3");
            //判断mDataList是否为null
            if (!mDataList.isEmpty()) {
                Log.e("TAG", "bensil2 <<<<<<<<<<<<");
                //遍历mDataList
                for (int i = 0; i < mDataList.size(); i++) {
                    //判断mDataList中的opId 是否等于opId
                    if (mDataList.get(i).getOpId().equals(opId)) {
                        //赋值
                        mDataList.get(i).setOpState(opState);
                        mDataList.get(i).setDocStatus(docStatus);
                    }
                }
            }
            mMenuAdapter.notifyDataSetChanged();
            return_amount();
        }

    }

    void return_amount() {
        String defString3 = PreferencesManager.getInstance(DocumentActivity.this, "accountBean").get("jsonStr");
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
                    ShortcutBadger.applyCount(DocumentActivity.this, response.body().getData().getManage_num());
                    amount_ShortcutBadger.edit().putInt("number",response.body().getData().getManage_num()).commit();

                }
            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {
                Toast.makeText(DocumentActivity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
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
