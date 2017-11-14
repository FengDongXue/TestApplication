package com.lanwei.governmentstar.activity.zyx;

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
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.ChooseReceiverNBActivity;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.LoggingActivity;
import com.lanwei.governmentstar.activity.Notification_Edit_Activity;
import com.lanwei.governmentstar.activity.Watch_Notification_Activity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_List_Actitivty;
import com.lanwei.governmentstar.activity.lll.SelectStateActivity;
import com.lanwei.governmentstar.activity.lll.refresh.ListViewDecoration;
import com.lanwei.governmentstar.activity.lll.refresh.OnItemClickListener;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Notification_List;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.bean.Return_Proceed;
import com.lanwei.governmentstar.bean.Return_Wait;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/31.
 */

public class GgNoticeActivity extends BaseActivity implements View.OnClickListener {

    private PopupWindow popupWindow;

    //刷新框架
    private Activity mContext;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MenuAdapter mMenuAdapter;

    private List<String> mDataList;

    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    int x, y;
    private boolean aBoolean;//是否开启加载
    private TextView title;
    private TextView tv_left;
    private TextView screen;
    private ImageView ivContacts;
    private EditText noticeSearch;
    private ImageView back;
    private TextView tvFb;
    private RelativeLayout rv;
    private String userId;
    private GovernmentApi api;
    private String pageNo = "1";
    private String search = "";
    private String orderBy = "desc";
    private String readState = "";
    private Notification_List notification_list;
    private String pageCount;
    private String pageNo_back;
    private ArrayList<Notification_List.Data> list = new ArrayList<>();
    private ProgressBar pb;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private Logging_Success bean2;
    private String opCreateId;
    private TextView myapply;
    private Boolean is_jump = false;
    private SharedPreferences amount_ShortcutBadger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff

        setContentView(R.layout.activity_ggnotice);
        rv = (RelativeLayout) findViewById(R.id.rv);
        title = (TextView) findViewById(R.id.tv_address);
        title.setVisibility(View.VISIBLE);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setPadding(40, 0, 0, 0);
        screen = (TextView) findViewById(R.id.screen);
        ivContacts = (ImageView) findViewById(R.id.iv_contacts);
        noticeSearch = (EditText) findViewById(R.id.search);
        myapply = (TextView) findViewById(R.id.myapply);
        back = (ImageView) findViewById(R.id.back);
        tvFb = (TextView) findViewById(R.id.tv_fb);
        pb = (ProgressBar) findViewById(R.id.pb);
        title.setText("公告通知");
        tv_left.setText("仅显示未读");
        screen.setText("按时间排序");
        back.setVisibility(View.VISIBLE);
        tvFb.setVisibility(View.INVISIBLE);
        ivContacts.setVisibility(View.GONE);
        myapply.setVisibility(View.VISIBLE);
        myapply.setText("发布");
        String defString = PreferencesManager.getInstance(this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean2 = gson.fromJson(defString, Logging_Success.class);
        opCreateId = bean2.getData().getOpId();
        back.setOnClickListener(this);
        screen.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        myapply.setOnClickListener(this);
//        listviewNotice = (MyListView) findViewById(R.id.listview_document);
//        //展示数据
//        listviewNotice.setAdapter(new NoticeAdapter());
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        noticeSearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                    String searchCt = noticeSearch.getText().toString();
//                    if (TextUtils.isEmpty(searchCt.trim())) {
//                        Toast.makeText(GgNoticeActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
//                    } else {
                        if (list != null) {
                            search = noticeSearch.getText().toString().trim();
                            pageNo = "1";
                            getdata();


                        } else {
                            Toast.makeText(GgNoticeActivity.this, "无相关的结果", Toast.LENGTH_SHORT).show();
                        }
//                    }
                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
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

                Call<Notification_List> call = api.list_notification(userId, (Integer.parseInt(pageNo) + 1) + "", search, orderBy, readState);

                call.enqueue(new Callback<Notification_List>() {
                    @Override
                    public void onResponse(Call<Notification_List> call, Response<Notification_List> response) {

                        notification_list = response.body();
                        pageCount = notification_list.getPageCount();
                        pageNo = notification_list.getPageNo();
                        pageNo_back = notification_list.getPageNo();
                        list.addAll(notification_list.getData());
                        mMenuAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.INVISIBLE);
                        mMenuAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Notification_List> call, Throwable throwable) {
                        pb.setVisibility(View.INVISIBLE);
                        mMenuAdapter.notifyDataSetChanged();
                        Toast.makeText(GgNoticeActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

                    }
                });

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
                return Integer.parseInt(pageCount) <= Integer.parseInt(pageNo);
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
        mDataList = new ArrayList<String>();
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        mSwipeMenuRecyclerView.setLayoutManager(linearLayoutManager);// 布局管理器。
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
        mMenuAdapter = new MenuAdapter();
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);

        api = HttpClient.getInstance().getGovernmentApi();

        String defString = PreferencesManager.getInstance(this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        Logging_Success bean = gson.fromJson(defString, Logging_Success.class);

        userId = bean.getData().getOpId();

        getdata();
    }

    private void getdata() {

        search = noticeSearch.getText().toString().trim();
        Call<Notification_List> call = api.list_notification(userId, pageNo, search, orderBy, readState);

        call.enqueue(new Callback<Notification_List>() {
            @Override
            public void onResponse(Call<Notification_List> call, Response<Notification_List> response) {

                notification_list = response.body();
                pageCount = notification_list.getPageCount();
                pageNo_back = notification_list.getPageNo();

                list = notification_list.getData();
                mMenuAdapter.notifyDataSetChanged();
                pb.setVisibility(View.INVISIBLE);
                linearLayoutManager.scrollToPosition(0);
            }

            @Override
            public void onFailure(Call<Notification_List> call, Throwable throwable) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(GgNoticeActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });


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

            case R.id.myapply:

                Intent intent = new Intent(this, Notification_Edit_Activity.class);
                startActivityForResult(intent,01);

                break;

            case R.id.tv_left:

                if (readState.equals("")) {
                    readState = "0";
                    tv_left.setTextColor(getResources().getColor(R.color.blue));
                } else {
                    readState = "";
                    tv_left.setTextColor(getResources().getColor(R.color.color_23));
                }

                pageNo = "1";
                mMenuAdapter.thisPosition = -1;
                getdata();
                break;

            case R.id.latest:
                orderBy = "desc";
                popupWindow.dismiss();
                pageNo = "1";
                mMenuAdapter.thisPosition = -1;
                getdata();

                break;

            case R.id.longest:
                orderBy = "asc";
                popupWindow.dismiss();
                pageNo = "1";
                mMenuAdapter.thisPosition = -1;
                getdata();
                break;


            case R.id.screen:

                // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = (float) 0.8;
                getWindow().setAttributes(lp2);

                // 加载popupwindow的布局
                View view = getLayoutInflater().inflate(R.layout.notification_document, null);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 初始化popupwindow的点击控件
                TextView latest = (TextView) view.findViewById(R.id.latest);
                TextView longest = (TextView) view.findViewById(R.id.longest);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());
                // 注册popupwindow里面的点击事件
                latest.setOnClickListener(this);
                longest.setOnClickListener(this);

                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(screen, Gravity.RIGHT | Gravity.TOP, 20, rv.getMeasuredHeight() * 7 / 3);
//                popupWindow.showAsDropDown(screen, -180, 35);
                break;
//            case R.id.document_search:
//                Intent intent = new Intent(GgNoticeActivity.this, SearchContactsActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            pageNo = "1";
            search = noticeSearch.getText().toString().trim();
            Call<Notification_List> call = api.list_notification(userId, pageNo, search, orderBy, readState);

            call.enqueue(new Callback<Notification_List>() {
                @Override
                public void onResponse(Call<Notification_List> call, Response<Notification_List> response) {

                    notification_list = response.body();
                    pageCount = notification_list.getPageCount();
                    pageNo_back = notification_list.getPageNo();

                    mSwipeRefreshLayout.setRefreshing(false);
                    list = notification_list.getData();
                    mMenuAdapter.thisPosition = -1;
                    mMenuAdapter.notifyDataSetChanged();
                    pb.setVisibility(View.INVISIBLE);
                    linearLayoutManager.scrollToPosition(0);
                }

                @Override
                public void onFailure(Call<Notification_List> call, Throwable throwable) {
                    pb.setVisibility(View.INVISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(GgNoticeActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

                }
            });


        }
    };

    /**
     * 加载更多
     */
//    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//            if (!recyclerView.canScrollVertically(1) && aBoolean) {// 手指不能向上滑动了
//                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
//
//
//                mSwipeMenuRecyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Toast.makeText(GgNoticeActivity.this, "加载中...", Toast.LENGTH_SHORT).show();
//
//                        for (int i = 0; i < 50; i++) {
//                            mDataList.add("我是第" + i + "个。");
//                        }
//                        mMenuAdapter.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        }
//    };

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
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
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

//            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//            }

            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                list.remove(adapterPosition);
                mMenuAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 520 && requestCode == 9) {
            if (mMenuAdapter.thisPosition != -1) {
                list.remove(mMenuAdapter.thisPosition);
                mMenuAdapter.thisPosition=-1;
                mMenuAdapter.notifyDataSetChanged();
                return_amount();
            }
        }else if(resultCode == 510){
            pageNo ="1";
            mMenuAdapter.thisPosition=-1;
            getdata();
        }
    }

    void return_amount() {
        String defString3 = PreferencesManager.getInstance(GgNoticeActivity.this, "accountBean").get("jsonStr");
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
                    ShortcutBadger.applyCount(GgNoticeActivity.this, response.body().getData().getManage_num());
                    amount_ShortcutBadger.edit().putInt("number",response.body().getData().getManage_num()).commit();

                }
            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {
                Toast.makeText(GgNoticeActivity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {
        public int thisPosition = -1;

        private OnItemClickListener mOnItemClickListener;

        public MenuAdapter() {


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
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notice_item, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
            viewHolder.mOnItemClickListener = mOnItemClickListener;
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final DefaultViewHolder holder, final int position) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thisPosition = position;
                    mMenuAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(GgNoticeActivity.this, Watch_Notification_Activity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("opId", list.get(position).getOpId());
                    intent.putExtra("noticeTitle", list.get(position).getNoticeTitle());

                    holder.line.setVisibility(View.VISIBLE);

                    if(list.get(position).getOpState().equals("-1")){
                        Toast.makeText(GgNoticeActivity.this,"请到网页端重新发布",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 显示未读
                    if (readState.equals("0")) {
                        intent.putExtra("readState", "0");
                        startActivityForResult(intent, 9);

                    } else if (readState.equals("") && list.get(position).getOpReadState().equals("0")) {
                       // 默认列表
                        intent.putExtra("readState", "");
                        list.get(position).setOpReadState("1");
                        holder.title.setTextColor(getResources().getColor(R.color.color_23_1));

                        startActivity(intent);
                        Log.e("s股份大股东", "gdfgfgg");
                    } else {
                        intent.putExtra("readState", "");
                        list.get(position).setOpReadState("1");

                        startActivity(intent);
                    }

                }
            });
            holder.decoration.setVisibility(View.VISIBLE);
            if (position == 0) {
                holder.decoration.setVisibility(View.GONE);
            }

            if (thisPosition == position) {
                holder.line.setVisibility(View.VISIBLE);
            } else {
                holder.line.setVisibility(View.INVISIBLE);
            }

            // TODO: 2017/5/13 改变关键字的颜色
            String tit = list.get(position).getNoticeTitle();
            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), tit, search);
            holder.title.setText(spannableString);

            holder.creat_time.setText(list.get(position).getOpCreateTime());
            holder.name.setText("发布者:" + list.get(position).getOpCreateName());

            if (opCreateId.equals(list.get(position).getOpCreateId()) && list.get(position).getOpState().equals("3")) {
                holder.title.setTextColor(getResources().getColor(R.color.red_normal_2));
            } else {
                holder.title.setTextColor(getResources().getColor(R.color.color_23_1));
            }
            if (readState.equals("")) {
                if (list.get(position).getOpReadState().equals("0")) {
                    holder.title.setTextColor(getResources().getColor(R.color.blue));

                } else {
                    holder.title.setTextColor(getResources().getColor(R.color.color_23));
                }
            }

            if(list.get(position).getOpState().equals("-1")){
                holder.title.setTextColor(getResources().getColor(R.color.red_normal));
            }
        }

        class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            OnItemClickListener mOnItemClickListener;
            private TextView title;
            private TextView creat_time;
            private TextView name;
            private TextView line;
            private View decoration;


            public DefaultViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                title = (TextView) itemView.findViewById(R.id.title);
                creat_time = (TextView) itemView.findViewById(R.id.creat_time);
                name = (TextView) itemView.findViewById(R.id.name);
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
