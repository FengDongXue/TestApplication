package com.lanwei.governmentstar.activity.zwyx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Inbox;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.Dialog02;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/21.
 */

public class DeleteActivity extends EmailBaseActivity implements View.OnClickListener, DialogUtil.OnClickListenner {
    private PopupWindowUtil popupWindowUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initview();
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        tv_address.setText("已删除");
        collect.setText("恢复");

        tvsearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {//修改回车键功能
                    //关键字
                    searchCt = tvsearch.getText().toString();

                    mDataList.clear();
                    if (dataList != null) {
                        search = searchCt;
                        getData("1");

                        View view = getWindow().peekDecorView();
                        if (view != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    } else {
                        Toast.makeText(DeleteActivity.this, "这里没有内容~~", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        initPull();


    }

    private void getData(final String pageno) {
        int i = 1;
        Log.e("aaaa", String.valueOf(i += 1));
        String userId = new GetAccount(this).opId(); //当前登录者Id
        //获取收文的数据
        RetrofitHelper.getInstance().getDeleteInfo(userId, pageno, search, new CallBackYSAdapter() {

            @Override
            protected void showErrorMessage(String message) {
                Log.e("邮件mes", message);
            }

            @Override
            protected void parseJson(String data) {
                Log.e("邮件data", data);
                if (data != null) {
                    Gson gson = new Gson();
                    Inbox inbox = gson.fromJson(data, Inbox.class);
                    pageCount = inbox.getPagecount();
                    pageNo = inbox.getPageno();


                    dataList = inbox.getData();

                    if (dataList != null) {
                        mDataList.addAll(dataList);
                    } else {
                        return;
                    }
                    if (Integer.valueOf(pageNo) == 1) {
                        mMenuAdapter = new MenuAdapter(mDataList, tvsearch.getText().toString(), 3);
                        mMenuAdapter.setOnItemClickListener(onItemClickListener);
                        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
                    }
                    if (pageCount > Integer.parseInt(pageno)) {
                        aBoolean = true;//判断对是否有下一页进行设置
                    } else {
                        aBoolean = false;//判断对是否有下一页进行设置
                    }

                    mMenuAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initPull() {
        aBoolean = false;
        mContext = this;
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);  //左滑删除
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        getData(pageNo + "");
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
                    mDataList = new ArrayList();  //刷新会获取第一页的数据，不清空的话会重复累加第一页的数据,类似于clear
                    getData("1");

                    edit_bottom.setVisibility(View.GONE);
                    email_edit.setVisibility(View.VISIBLE);
                    email_finish.setVisibility(View.GONE);
                    tvsearch.setVisibility(View.VISIBLE); //将搜索框显示
                    mMenuAdapter.goneView(true); //隐藏选项图标
                    mMenuAdapter.notifyDataSetChanged();
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
                if (pageNo < pageCount) {
                    pageNo += 1;
                    if (pageNo == pageCount) {
                        pageNo = pageCount;
                    }
                    getData(String.valueOf(pageNo));
                } else {
//                    Toast.makeText(DocumentHQActivity.this, "真的没有更多了~~", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
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
        public void onItemClick(Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }

            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) { // 删除按钮被点击。

                final Dialog02 dialog02 = new Dialog02(DeleteActivity.this);
                dialog02.setContent("您确定要删除该邮件吗？", Color.parseColor("#4f4f4f"));
                dialog02.setTitle("删除邮件", Color.parseColor("#5184c3"));
                dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
                dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
                dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        popupWindowUtil = new PopupWindowUtil(DeleteActivity.this, "提交中...");
                        popupWindowUtil.show();
                        dialog02.dismiss();
                        moveDeleteData(mDataList.get(adapterPosition).getOpid());
                        mDataList.remove(adapterPosition);
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });
                dialog02.setNoOnclickListener("取消", new Dialog02.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog02.dismiss();
                    }
                });

                Window window = dialog02.getWindow();
                //设置显示动画
                window.setWindowAnimations(R.style.dialog_animstyle);
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = 0;

                wl.y = -DeleteActivity.this.getWindowManager().getDefaultDisplay().getHeight() / 50;
                //设置显示位置
                dialog02.onWindowAttributesChanged(wl);//设置点击外围解散
                dialog02.setCanceledOnTouchOutside(true);

                dialog02.show();

            }
        }
    };

    @Override
    public void onBackPressed() {
        DeleteActivity.this.setResult(201);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                DeleteActivity.this.setResult(201);
                // 先隐藏键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
                break;
            case R.id.email_edit:
                isEdit = true;
                edit_bottom.setVisibility(View.VISIBLE);
                email_edit.setVisibility(View.GONE);
                email_finish.setVisibility(View.VISIBLE);
                tvsearch.setVisibility(View.GONE); //将搜索框隐藏
                mMenuAdapter.goneView(false);  //显示选项图标
                mSwipeRefreshLayout.setRefreshing(false);
                select.setText("全选");
                mMenuAdapter.initCheck(1);  //全选
                mMenuAdapter.notifyDataSetChanged();
                break;
            case R.id.email_finish:
                isEdit = false;
                edit_bottom.setVisibility(View.GONE);
                email_edit.setVisibility(View.VISIBLE);
                email_finish.setVisibility(View.GONE);
                tvsearch.setVisibility(View.VISIBLE); //将搜索框显示
                mMenuAdapter.goneView(true); //隐藏选项图标
                mMenuAdapter.notifyDataSetChanged();
                break;
            case R.id.select:
                if (select.getText().equals("全选")) {
                    select.setText("反选");
                    mMenuAdapter.initCheck(0);  //反选
                } else {
                    select.setText("全选");
                    mMenuAdapter.initCheck(1);  //全选
                }
                mMenuAdapter.notifyDataSetChanged();
                break;
            case R.id.del:  //删除
                if (mDataList != null && mDataList.size() > 0) {
                    new DialogUtil(DeleteActivity.this, this).showConfirm("提交意见", "您确定要提交意见吗？", "确定", "取消");
                } else {
                    Toast.makeText(DeleteActivity.this, "请选择您要删除的邮件", Toast.LENGTH_SHORT).show();
                }
//                deleteData();
                break;
            case R.id.collect:   //恢复
                jumpDialog();
                break;
        }
    }

    private void jumpDialog() {
        final Dialog02 dialog02 = new Dialog02(this);
        dialog02.setContent("您确定要恢复该邮件吗？", Color.parseColor("#4f4f4f"));
        dialog02.setTitle("恢复邮件", Color.parseColor("#5184c3"));
        dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
        dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
        dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                popupWindowUtil = new PopupWindowUtil(DeleteActivity.this, "提交中...");
                popupWindowUtil.show();
                dialog02.dismiss();
                recover();

            }
        });
        dialog02.setNoOnclickListener("取消", new Dialog02.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog02.dismiss();
            }
        });

        Window window = dialog02.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;

        wl.y = -this.getWindowManager().getDefaultDisplay().getHeight() / 50;
        //设置显示位置
        dialog02.onWindowAttributesChanged(wl);//设置点击外围解散
        dialog02.setCanceledOnTouchOutside(true);

        dialog02.show();
    }

    /**
     * 恢复
     */
    private void recover() {
        String opid = "";
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getIsCheck() == 0) {
                opid = opid + "," + mDataList.get(i).getOpid();
                Log.e("邮件拼接ming", mDataList.get(i).getMailtitle() + "===" + mDataList.get(i).getOpid());
            }
        }
        Log.e("邮件拼接", opid);
        if (opid.length() > 0) {
            opid = opid.substring(1, opid.length());
        } else {
            Toast.makeText(DeleteActivity.this, "请选择您要恢复的邮件", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("邮件裁剪", opid);
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.zwyxDelRecovery(opid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("删除邮件", response.body().toString());
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        boolean data = jsonObject.getBoolean("data");
//                        {"message":"删除成功！","data":true}
                        if (data) {
                            mDataList.clear();
                            getData(String.valueOf(1));
                            mMenuAdapter.notifyDataSetChanged();  //恢复成功刷新数据
                            email_edit.setVisibility(View.VISIBLE);
                            email_finish.setVisibility(View.GONE);
                            edit_bottom.setVisibility(View.GONE);
                            isEdit = false;

                            new DialogUtil(DeleteActivity.this, DeleteActivity.this).showAlert("恢复邮件", "您已将该邮件恢复成功", "知道了");
                            return;
//                            Toast.makeText(DeleteActivity.this, "恢复成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeleteActivity.this, "恢复失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void moveDeleteData(String opId) {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.zwyxDel(opId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("删除邮件", response.body().toString());
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        boolean data = jsonObject.getBoolean("data");
//                        {"message":"删除成功！","data":true}
                        if (data) {

                            new DialogUtil(DeleteActivity.this, DeleteActivity.this).showAlert("删除邮件", "您已将该邮件删除成功", "知道了");
                            return;
//                            Toast.makeText(DeleteActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeleteActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }

    /**
     * 删除
     */
    private void deleteData() {
        String opid = "";
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getIsCheck() == 0) {
                opid = opid + "," + mDataList.get(i).getOpid();

                Log.e("邮件拼接ming", mDataList.get(i).getMailtitle() + "===" + mDataList.get(i).getOpid());
            }
        }
        Log.e("邮件拼接", opid);
        if (opid.length() > 0) {
            opid = opid.substring(1, opid.length());
        } else {
            Toast.makeText(DeleteActivity.this, "请选择您要删除的邮件", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("邮件裁剪", opid);
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.zwyxDel(opid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("删除邮件", response.body().toString());
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        boolean data = jsonObject.getBoolean("data");
//                        {"message":"删除成功！","data":true}
                        if (data) {
                            mDataList.clear();
                            getData(String.valueOf(1));
                            mMenuAdapter.notifyDataSetChanged();  //删除成功刷新数据
                            email_edit.setVisibility(View.VISIBLE);
                            email_finish.setVisibility(View.GONE);
                            edit_bottom.setVisibility(View.GONE);
                            isEdit = false;

                            new DialogUtil(DeleteActivity.this, DeleteActivity.this).showAlert("删除邮件", "您已将该邮件删除成功", "知道了");
                            return;
//                            Toast.makeText(DeleteActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeleteActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 21) {
            mDataList.clear();
            getData("1");
        }
    }

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(DeleteActivity.this, "提交中...");
        popupWindowUtil.show();
        deleteData();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

    }
}
