package com.lanwei.governmentstar.activity.spsq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.gwnz.DocumentActivity;
import com.lanwei.governmentstar.activity.lll.refresh.OnItemClickListener;
import com.lanwei.governmentstar.activity.spsq.adapter.ApplyAdapter;
import com.lanwei.governmentstar.bean.SpsqApplyList;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.ScreenUtils;
import com.lanwei.governmentstar.view.LoadingDialog;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.lanwei.governmentstar.view.WrapContentLinearLayoutManager;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/1/001.
 */

public class ApplyActivity extends AutoLayoutActivity implements View.OnClickListener , DialogUtil.OnClickListenner{
    private TextView title;
    private ImageView back;
    private ImageView iv_contacts;
    private ImageView spsqEdit;
    private TextView tvApproval;
    private TextView tvApply;
    private TextView tvReportdata;
    private EditText tvsearch;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private boolean aBoolean = false; //是否开启加载
    private Activity mContext;
    private ApplyAdapter mApplyAdapter;
    private ScrollView scrollview;
    private boolean hasApproval = false;
    private ImageView spsqSort;
    private PopupWindow popupWindow;
    private RelativeLayout rv;
    private List<SpsqApplyList.DataBean> mDataList;
    private List<SpsqApplyList.DataBean> dataList = new ArrayList<>();
    private int pageNo = 1;   //当前是第几页
    private int pageCount = 1;   //总页数
    private String spsqFlag="1";
    private String userId;
    String search = "";
    String state = "4";
    String oderby = "ORDERBY op_time DESC";// 默认降序
    private int size = 30;
    private LoadingDialog pd;
    private int type = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_apply);

        initViews();
        initPull();
    }

    private void initViews() {
        title = (TextView) findViewById(R.id.tv_address);
        back = (ImageView) findViewById(R.id.back);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        spsqEdit = (ImageView) findViewById(R.id.spsq_edit);
        spsqSort = (ImageView) findViewById(R.id.spsq_sort);
        tvApproval = (TextView) findViewById(R.id.tv_approval);  //等待审批
        tvApply = (TextView) findViewById(R.id.tv_apply);  //我的申请
//        tvReportdata = (TextView) findViewById(R.id.tv_reportdata);  //报表数据
        tvsearch = (EditText) findViewById(R.id.search);
        search();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        rv = (RelativeLayout) findViewById(R.id.rv);

        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        spsqEdit.setVisibility(View.VISIBLE);
        spsqSort.setVisibility(View.VISIBLE);
        title.setText("审批申请");
        spsqEdit.setOnClickListener(this);
        spsqSort.setOnClickListener(this);
        back.setOnClickListener(this);
        tvApproval.setOnClickListener(this);
        tvApply.setOnClickListener(this);
//        tvReportdata.setOnClickListener(this);

    }

    private void search() {
        tvsearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {//修改回车键功能
                    final String searchCt = tvsearch.getText().toString();
                    mDataList.clear();
                    if (dataList != null) {
                        search = searchCt;
                        getdata("1");
                        View view = getWindow().peekDecorView();
                        if (view != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }else {
                            Toast.makeText(ApplyActivity.this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
                        }
                    }

                    return true;
                }
                return false;
            }
        });
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
                    mApplyAdapter.notifyDataSetChanged();
                }
            }, 0);
        }
    };

    private void initPull() {
        aBoolean = false;
        mContext = this;
        mDataList = new ArrayList<SpsqApplyList.DataBean>();
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mSwipeMenuRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        // 添加滚动监听。
        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
//        Mugen.with(mSwipeMenuRecyclerView, new MugenCallbacks() {
//            @Override
//            public void onLoadMore() {
//
//                showPD("");   //加载中的进度框
////                Toast.makeText(ApplyActivity.this, "加载中...", Toast.LENGTH_SHORT).show();
//                size += 50;
//                Log.e("高浮雕鬼地方个鬼地方高浮雕", "没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。");
//                //如果当前页数小于总页数
//                if (pageNo < pageCount) {
//                    pageNo += 1;
//                    if (pageNo == pageCount) {
//                        pageNo = pageCount;
//                    }
//                    getdata(String.valueOf(pageNo));
//                } else {
////                    Toast.makeText(ApplyActivity.this, "真的没有了", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//            }
//
//            @Override
//            public boolean isLoading() {
//                return false;
//            }
//
//            @Override
//            public boolean hasLoadedAllItems() {
//                return false;
//            }
//        }).start();

        mApplyAdapter = new ApplyAdapter(0,mDataList, tvsearch.getText().toString(), ApplyActivity.this);
        mSwipeMenuRecyclerView.setAdapter(mApplyAdapter);
        if (mDataList != null) {
            getdata(pageNo + "");
        } else {
            Toast.makeText(ApplyActivity.this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
        }
    }

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
                showPD("");   //加载中的进度框
//                Toast.makeText(ApplyActivity.this, "加载中...", Toast.LENGTH_SHORT).show();
                size += 50;
                Log.e("高浮雕鬼地方个鬼地方高浮雕", "没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。");
                //如果当前页数小于总页数
                if (pageNo < pageCount) {
                    pageNo += 1;
                    if (pageNo == pageCount) {
                        pageNo = pageCount;
                    }
                    getdata(String.valueOf(pageNo));
                } else {
//                    Toast.makeText(ApplyActivity.this, "真的没有了", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    };

    private void getdata(final String pageno) {
        int i = 1;
        Log.e("aaaa", String.valueOf(i += 1));
        userId = new GetAccount(this).opId();
        search = tvsearch.getText().toString();

        //获取审批申请列表的数据
        RetrofitHelper.getInstance().spsqList(pageno, userId, search, state, oderby, new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {
                Log.e("mes", message);
                dismissPD();
            }

            @Override
            protected void parseJson(String data) {
                dismissPD();
                Log.e("data11", data);
                if (data != null) {
                    Gson gson = new Gson();
                    SpsqApplyList spsqApplyList = gson.fromJson(data, SpsqApplyList.class);
                    pageCount = spsqApplyList.getPageCount();
                    pageNo = spsqApplyList.getPageNo();
                    spsqFlag = spsqApplyList.getSpsqFlag();
                    dataList = spsqApplyList.getData();

                    if (dataList != null) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Log.d("tag", ">>>>>>>>>>" + dataList.get(i).getStatus() + "-------" + dataList.get(i).getPersonName() + "----" + dataList.get(i).getState());
                            mDataList.add(dataList.get(i));
                        }
                    } else {
                        return;
                    }
                    if (Integer.valueOf(pageNo) == 1) {
                        mApplyAdapter = new ApplyAdapter(0,mDataList, tvsearch.getText().toString(), ApplyActivity.this);
                        mSwipeMenuRecyclerView.setAdapter(mApplyAdapter);
                    }
                    if (pageCount > Integer.parseInt(pageno)) {
                        aBoolean = true;//判断对是否有下一页进行设置
                    } else {
                        aBoolean = false;//判断对是否有下一页进行设置
                    }

                    mApplyAdapter.notifyDataSetChanged();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                // 先隐藏键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Intent intent2 =new Intent();
                setResult(20,intent2);
                Log.e("desfdasdf1111 ","dgfdsgdg");
                finish();
                break;
            case R.id.spsq_edit:

                if(spsqFlag.equals("0")){
                    Intent intent = new Intent(this, SentApplyActivity.class);
                    startActivity(intent);
                }else{
                    new DialogUtil(this, this).showAlert("信息提示", "该功能暂不对您开放！", "知道了");
                }


                break;
            case R.id.spsq_sort:
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

                popupWindow.showAtLocation(spsqSort, Gravity.RIGHT | Gravity.TOP, 30, rv.getMeasuredHeight() * 3 / 2);

                TextView lastest_receive = (TextView) view2.findViewById(R.id.create_offer);
                TextView long_receive = (TextView) view2.findViewById(R.id.scan_zxing);


                lastest_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
//                        Toast.makeText(ApplyActivity.this, "按照接收最近时间排序", Toast.LENGTH_SHORT).show();
                        oderby = "ORDERBY op_time asc";
                        mDataList.clear();
                        getdata("1");
                        mApplyAdapter.notifyDataSetChanged();
                    }
                });

                long_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
//                        Toast.makeText(ApplyActivity.this, "按照接收最久时间排序", Toast.LENGTH_SHORT).show();
                        mDataList.clear();
                        oderby = "ORDERBY op_time DESC";
                        getdata("1");
                        mApplyAdapter.notifyDataSetChanged();
                    }
                });

                break;
            case R.id.tv_approval://等待审批
                type = 1;
                tvsearch.setText("");
                tvsearch.clearFocus();
                mDataList.clear();
                mApplyAdapter.notifyDataSetChanged();
                tvApproval.setTextColor(Color.parseColor("#00a6e4"));
                tvApply.setTextColor(Color.parseColor("#333333"));
                if (mDataList != null) {

                    if (!state.equals("1")) {
                        state = "1";
                        tvApproval.setTextColor(Color.parseColor("#00a6e4"));
                        tvApply.setTextColor(Color.parseColor("#333333"));
                    } else {
                        state = "4";
                        tvApproval.setTextColor(Color.parseColor("#333333"));
                        tvApply.setTextColor(Color.parseColor("#333333"));
                    }


                    getdata("1");
                    mApplyAdapter = new ApplyAdapter(1,mDataList, tvsearch.getText().toString(), ApplyActivity.this);
                    mSwipeMenuRecyclerView.setAdapter(mApplyAdapter);
                    mApplyAdapter.notifyDataSetChanged();
                }
                break;

            case R.id.tv_apply://我的申请
                type = 2;
                tvsearch.setText("");
                tvsearch.clearFocus();
                mDataList.clear();
                mApplyAdapter.notifyDataSetChanged();
                tvApproval.setTextColor(Color.parseColor("#00a6e4"));
                tvApply.setTextColor(Color.parseColor("#333333"));
                if (mDataList != null) {

                    if (!state.equals("2")) {
                        state = "2";
                        tvApply.setTextColor(Color.parseColor("#00a6e4"));
                        tvApproval.setTextColor(Color.parseColor("#333333"));
                    } else {
                        state = "4";
                        tvApproval.setTextColor(Color.parseColor("#333333"));
                        tvApply.setTextColor(Color.parseColor("#333333"));
                    }


                    getdata("1");
                    mApplyAdapter = new ApplyAdapter(2,mDataList, tvsearch.getText().toString(), ApplyActivity.this);
                    mSwipeMenuRecyclerView.setAdapter(mApplyAdapter);
                    mApplyAdapter.notifyDataSetChanged();
                }
//                tvReportdata.setTextColor(Color.parseColor("#333333"));
                break;
//            case R.id.tv_reportdata://报表数据
//                tvReportdata.setTextColor(Color.parseColor("#00a6e4"));
//                tvApproval.setTextColor(Color.parseColor("#333333"));
//                tvApply.setTextColor(Color.parseColor("#333333"));
//
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "6 <<<<<<<<<<<<");
        if (resultCode == 6) {
            String opId = data.getStringExtra("opId");
            String opState = data.getStringExtra("opState");
            String opStatus = data.getStringExtra("opStatus");
            Log.e("TAG", "66 <<<<<<<<<<<<3");
            //判断mDataList是否为null
            if (!mDataList.isEmpty()) {
                Log.e("TAG", "666 <<<<<<<<<<<<");
                //遍历mDataList
                for (int i = 0; i < mDataList.size(); i++) {
                    //判断mDataList中的opId 是否等于opId
                    if (mDataList.get(i).getOpId().equals(opId)) {
                        //赋值
                        mDataList.get(i).setState(opState);
                        mDataList.get(i).setStatus(opStatus);
                    }
                }
            }
            mApplyAdapter.notifyDataSetChanged();
        }else if(resultCode == 444){
            if(mApplyAdapter.thisPosition != -1){
                mDataList.remove(mApplyAdapter.thisPosition);
                mApplyAdapter.thisPosition = -1;
                mApplyAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void yesClick() {

    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

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

    /**
     * 显示进度对话框
     *
     * @param message 要显示的文字
     */
    public void showPD(String message) {
        try {
            if (pd == null) {
                pd = new LoadingDialog(this);
            }
            pd.setMessage(message);
            if (!pd.isShowing()) {
                pd.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭进度对话框
     */
    public void dismissPD() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }

    }

    public class ApplyAdapter extends SwipeMenuAdapter<ApplyAdapter.DefaultViewHolder> {
        OnItemClickListener mOnItemClickListener;
        TextView creatName, tv_tag, spsx, szbm, fqtime, line1, textview1, things ,sqbt;
        LinearLayout myapply, department ,all;
        View line;
        ImageView csd;

        private Activity activity;
        private String search;
        private List<SpsqApplyList.DataBean> datalist;
        public int thisPosition = -1;
        private String userId;
        private int type;

        public ApplyAdapter(int type, List<SpsqApplyList.DataBean> datalist, String s, ApplyActivity activity) {
            this.type = type;
            this.activity = activity;
            this.datalist = datalist;
            this.search = s;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {

            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apply, parent, false);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public ApplyAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            creatName = (TextView) realContentView.findViewById(R.id.creat_name);
            tv_tag = (TextView) realContentView.findViewById(R.id.tv_tag);
            myapply = (LinearLayout) realContentView.findViewById(R.id.myapply);
            spsx = (TextView) realContentView.findViewById(R.id.spsx);
            szbm = (TextView) realContentView.findViewById(R.id.szbm);
            fqtime = (TextView) realContentView.findViewById(R.id.fqtime);
            sqbt = (TextView) realContentView.findViewById(R.id.sqbt);
            line = realContentView.findViewById(R.id.line);
            line1 = (TextView) realContentView.findViewById(R.id.line1);

            textview1 = (TextView) realContentView.findViewById(R.id.textview1);
            things = (TextView) realContentView.findViewById(R.id.things);
            department = (LinearLayout) realContentView.findViewById(R.id.department);
            all = (LinearLayout) realContentView.findViewById(R.id.all_person);
//            csd = (ImageView) realContentView.findViewById(R.id.csd);


            ApplyAdapter.DefaultViewHolder viewHolder = new ApplyAdapter.DefaultViewHolder(realContentView);
            viewHolder.mOnItemClickListener = mOnItemClickListener;

            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final ApplyAdapter.DefaultViewHolder holder, final int position) {


            if (thisPosition != position) {
                holder.line1.setVisibility(View.INVISIBLE);
            }else{
                holder.line1.setVisibility(View.VISIBLE);
            }
//
            String opCreateName = datalist.get(position).getPersonName();
            final String opId = datalist.get(position).getOpId();
            final String opTypeName = datalist.get(position).getName();
            final String opState = datalist.get(position).getState();
            final String opStatus = datalist.get(position).getStatus();
            String opTime = datalist.get(position).getTime();
            String deptName = datalist.get(position).getDeptName();

//        holder.creatName.setText(datalist.get(position).getPersonName());
            holder.spsx.setText(datalist.get(position).getName());
            holder.szbm.setText(datalist.get(position).getDeptName());
            holder.fqtime.setText(datalist.get(position).getTime());

            holder.creatName.setText(opCreateName);

            switch (datalist.get(position).getStatus()){

                case "1":
                    holder.tv_tag.setText("状态：等待审定");
                    if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
                    }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    }

                    break;

                case "3":
                    holder.tv_tag.setText("状态：等待审核");
                    if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
                    }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    }

                    break;

                case "4":
                    holder.tv_tag.setText("状态：等待批准");
                    if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
                    }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    }

                    break;

                case "6":
                    if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.app_focus2));
                        holder.tv_tag.setText("状态：等待查看");
                    }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                        holder.sqbt.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                        holder.tv_tag.setText("状态：已查看");
                    }

                    break;

                case "0":
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.sqbt.setTextColor(activity.getResources().getColor(R.color.red_normal));
                    holder.tv_tag.setText("状态：未通过");

                    break;

                case "5":
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.sqbt.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    holder.tv_tag.setText("状态：已通过");
                    break;

                case "8":
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.sqbt.setTextColor(activity.getResources().getColor(R.color.red_normal));
                    holder.tv_tag.setText("状态：未批准");
                    break;

                default:

                    break;

            }

            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), datalist.get(position).getBT(), search);
            holder.sqbt.setText(spannableString);


            holder.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    thisPosition = holder.getLayoutPosition();
                    notifyDataSetChanged();

                    Intent intent2 = new Intent();
                    Intent intent = null;
                    switch (datalist.get(position).getStatus()){
                        case "1":
                        case "3":
                        case "4":
                            intent = intentSp(intent2, opTypeName ,opState);  //审批人界面跳转
                            break;

                        case "6":
                            datalist.get(position).setState("0");
                            notifyDataSetChanged();
                            intent = intentCs(intent2, opTypeName ,"0");   //抄送人界面跳转
                            break;

                        case "0":
                        case "5":
                        case "8":
                            intent = intentSp(intent2, opTypeName ,"0");  //审批人界面跳转
                            break;
                    }

                    int opStatus1 = Integer.parseInt(opStatus);
                    userId = new GetAccount(activity).opId();
                    Log.d("bb", opStatus);

                    if (intent != null){
                        intent.putExtra("opId", opId);
                        intent.putExtra("userId", userId);
                        activity.startActivityForResult(intent,6);
                    }
                }
            });

        }

        private Intent intentSp(Intent intent, String opTypeName ,String opState) {
            switch (opTypeName) {
                case "资质印章-其他":
                    intent = new Intent(activity, SealOtherSpActivity.class);
                    break;
                case "资质印章-机构证照申请":
                    intent = new Intent(activity, SealLicenseSpActivity.class);
                    break;
                case "资质印章-用印申请":
                    intent = new Intent(activity, SealUsesealSpActivity.class);
                    break;


                case "项目申请-其他":
                    intent = new Intent(activity, ProjectOtherSpActivity.class);
                    break;
                case "项目申请-立项申请":
                    intent = new Intent(activity, ProjectSpActivity.class);
                    break;
                case "项目申请-合同审批":
                    intent = new Intent(activity, ProjectContractSpActivity.class);
                    break;
                case "项目申请-工作指示":
                    intent = new Intent(activity, ProjectPointSpActivity.class);
                    break;
                case "项目申请-部门协作":
                    intent = new Intent(activity, ProjectDepartmentSpActivity.class);
                    break;


                case "资金申请-付款申请":
                    intent = new Intent(activity, CapitalPaySpActivity.class);
                    break;
                case "资金申请-备用金申请":
                    intent = new Intent(activity, CapitalOtherSpActivity.class);
                    break;
                case "资金申请-报销申请":
                    intent = new Intent(activity, CapitalBxSpActivity.class);
                    break;


                case "请假申请-病假":
                case "请假申请-事假":
                case "请假申请-婚假":
                case "请假申请-丧假":
                case "请假申请-产假":
                case "请假申请-年休假":
                    intent = new Intent(activity, QjApplySpActivity.class);
                    break;

                case "人事申请-其他":
                    intent = new Intent(activity, PersonOthersSpActivity.class);
                    break;
                case "人事申请-招聘申请":
                    intent = new Intent(activity, PersonJobsSpActivity.class);
                    break;
                case "人事申请-调派申请":
                    intent = new Intent(activity, PersonOutSpActivity.class);
                    break;
                case "人事申请-离职申请":
                    intent = new Intent(activity, PersonLeaveSpActivity.class);
                    break;
                case "人事申请-后备干部申请":
                    intent = new Intent(activity, PersonCadreSpActivity.class);
                    break;

                case "外出申请-其他":
                    intent = new Intent(activity, OutOtherSpActivity.class);
                    break;
                case "外出申请-用车申请":
                    intent = new Intent(activity, OutCarSpActivity.class);
                    break;
                case "外出申请-执行公务申请":
                    intent = new Intent(activity, OutPublicSpActivity.class);
                    break;
                case "外出申请-出差报销申请":
                    intent = new Intent(activity, OutReimburseSpActivity.class);
                    break;


                case "物品申请-租用申请":
                    intent = new Intent(activity, ThingsRentSpActivity.class);
                    break;
                case "物品申请-其他采购":
                    intent = new Intent(activity, ThingsOtherPurchaseSpActivity.class);
                    break;
                case "物品申请-物品领用":
                    intent = new Intent(activity, ThingsReceiveSpActivity.class);
                    break;
                case "物品申请-申请采购":
                    intent = new Intent(activity, ThingsPurchaseApplySpActivity.class);
                    break;
                case "物品申请-定制采购":
                    intent = new Intent(activity, ThingsCustomPurchaseSpActivity.class);
                    break;

                case "其他申请-通用审批":
                    intent = new Intent(activity, OtherTyspSpActivity.class);
                    break;
                case "其他申请-接待申请":
                    intent = new Intent(activity, OtherReceptionSpActivity.class);
                    break;
                case "其他申请-活动申请":
                    intent = new Intent(activity, OtherActivitySpActivity.class);
                    break;
                case "其他申请-会议审批":
                    intent = new Intent(activity, OtherHyspSpActivity.class);
                    break;
            }
            intent.putExtra("type",opState);
            return intent;
        }

        private Intent intentCs(Intent intent, String opTypeName ,String opState) {
            switch (opTypeName) {
                case "资质印章-其他":
                    intent = new Intent(activity, SealOtherCsActivity.class);
                    break;
                case "资质印章-机构证照申请":
                    intent = new Intent(activity, SealLicenseCsActivity.class);
                    break;
                case "资质印章-用印申请":
                    intent = new Intent(activity, SealUsesealCsActivity.class);
                    break;

                case "项目申请-其他":
                    intent = new Intent(activity, ProjectOtherCsActivity.class);
                    break;
                case "项目申请-立项申请":
                    intent = new Intent(activity, ProjectCsActivity.class);
                    break;
                case "项目申请-合同审批":
                    intent = new Intent(activity, ProjectContractCsActivity.class);
                    break;
                case "项目申请-工作指示":
                    intent = new Intent(activity, ProjectPointCsActivity.class);
                    break;
                case "项目申请-部门协作":
                    intent = new Intent(activity, ProjectDepartmentCsActivity.class);
                    break;


                case "资金申请-付款申请":
                    intent = new Intent(activity, CapitalPayCsActivity.class);
                    break;
                case "资金申请-备用金申请":
                    intent = new Intent(activity, CapitalOtherCsActivity.class);
                    break;
                case "资金申请-报销申请":
                    intent = new Intent(activity, CapitalBxCsActivity.class);
                    break;

                case "请假申请-病假":
                case "请假申请-事假":
                case "请假申请-婚假":
                case "请假申请-丧假":
                case "请假申请-产假":
                case "请假申请-年休假":
                    intent = new Intent(activity, QjApplyCsActivity.class);
                    break;

                case "人事申请-其他":
                    intent = new Intent(activity, PersonOthersCsActivity.class);
                    break;

                case "人事申请-招聘申请":
                    intent = new Intent(activity, PersonJobsCsActivity.class);
                    break;
                case "人事申请-调派申请":
                    intent = new Intent(activity, PersonOutCsActivity.class);
                    break;
                case "人事申请-离职申请":
                    intent = new Intent(activity, PersonLeaveCsActivity.class);
                    break;
                case "人事申请-后备干部申请":
                    intent = new Intent(activity, PersonCadreCsActivity.class);
                    break;

                case "外出申请-其他":
                    intent = new Intent(activity, OutOtherCsActivity.class);
                    break;
                case "外出申请-用车申请":
                    intent = new Intent(activity, OutCarCsActivity.class);
                    break;
                case "外出申请-执行公务申请":
                    intent = new Intent(activity, OutPublicCsActivity.class);
                    break;
                case "外出申请-出差报销申请":
                    intent = new Intent(activity, OutReimburseCsActivity.class);
                    break;


                case "物品申请-租用申请":
                    intent = new Intent(activity, ThingsRentCsActivity.class);
                    break;
                case "物品申请-其他采购":
                    intent = new Intent(activity, ThingsOtherPurchaseCsActivity.class);
                    break;
                case "物品申请-物品领用":
                    intent = new Intent(activity, ThingsReceiveCsActivity.class);
                    break;
                case "物品申请-申请采购":
                    intent = new Intent(activity, ThingsPurchaseApplyCsActivity.class);
                    break;
                case "物品申请-定制采购":
                    intent = new Intent(activity, ThingsCustomPurchaseCsActivity.class);
                    break;

                case "其他申请-通用审批":
                    intent = new Intent(activity, OtherTyspCsActivity.class);
                    break;
                case "其他申请-接待申请":
                    intent = new Intent(activity, OtherReceptionCsActivity.class);
                    break;
                case "其他申请-活动申请":
                    intent = new Intent(activity, OtherActivityCsActivity.class);
                    break;
                case "其他申请-会议审批":
                    intent = new Intent(activity, OtherHyspCsActivity.class);
                    break;
            }
            intent.putExtra("type",opState);
            return intent;
        }


        @Override
        public int getItemCount() {
            return datalist == null ? 0 : datalist.size();
        }

        class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            OnItemClickListener mOnItemClickListener;
            TextView creatName, tv_tag, spsx, szbm, fqtime, line1, textview1, things ,sqbt;
            LinearLayout myapply, department ,all;
            View line;
            ImageView csd;

            public DefaultViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                creatName = (TextView) itemView.findViewById(R.id.creat_name);
                tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
                spsx = (TextView) itemView.findViewById(R.id.spsx);
                szbm = (TextView) itemView.findViewById(R.id.szbm);
                fqtime = (TextView) itemView.findViewById(R.id.fqtime);
                sqbt = (TextView) itemView.findViewById(R.id.sqbt);
                line = itemView.findViewById(R.id.line);
                line1 = (TextView) itemView.findViewById(R.id.line1);

                myapply = (LinearLayout) itemView.findViewById(R.id.myapply);
                all = (LinearLayout) itemView.findViewById(R.id.all_person);

                textview1 = (TextView) itemView.findViewById(R.id.textview1);
                things = (TextView) itemView.findViewById(R.id.things);
                department = (LinearLayout) itemView.findViewById(R.id.department);
//                csd = (ImageView) itemView.findViewById(R.id.csd);


            }

            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
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

}
