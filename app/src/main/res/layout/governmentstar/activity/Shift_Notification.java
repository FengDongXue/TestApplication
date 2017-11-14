package com.lanwei.governmentstar.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.DocumentFileForwardActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Collection_Return;
import com.lanwei.governmentstar.bean.Notification_Item;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/5/26.
 */

public class Shift_Notification extends BaseActivity implements View.OnClickListener ,MyScrollView_Focus.MyScrollListener ,DialogUtil.OnClickListenner ,View.OnLayoutChangeListener{

    private ImageView back;
    private TextView more;
    private FrameLayout right;

    private TextView publish;
    private TextView type;
    private TextView name;
    private TextView time;
    private TextView shfit_choose;
    private TextView title;
    private TextView document_guild_edit;
    private TextView noticetitle;
    private RecyclerView rv;

    private Adapter_Addtion adapter;
    private ArrayList<String> data_list = null;
    private RelativeLayout rv2;
    private GovernmentApi api;
    private String userId;
    private String opId;
    //  根据网络请求的数据，加载不同是否显示作废文件的标志
    private int mark = 0;
    private LinearLayout condition;
    private LinearLayout end;
    private Notification_Item notification_item;
    private Notification_Item.Data data_content;
    private ArrayList<Notification_Item.Filelist> list_add;
    private WebView webview;
    private FloatingActionButton enshrink;
    private FloatingActionButton enlarge;
    private MyScrollView_Focus scroll_view;
    private String content_edit;
    private  String opIds="";
    private PopupWindowUtil popupWindowUtil;
    //Activity最外层的Layout视图
    private View activityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shfit);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        activityRootView = findViewById(R.id.root_layout);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;

        back=(ImageView)findViewById(R.id.back);
        more=(TextView)findViewById(R.id.more);
        noticetitle=(TextView)findViewById(R.id.noticetitle);
        rv2=(RelativeLayout) findViewById(R.id.rv2);
        end=(LinearLayout) findViewById(R.id.end);
        scroll_view=(MyScrollView_Focus) findViewById(R.id.scroll_view);
        webview=(WebView) findViewById(R.id.webview);
        enshrink=(FloatingActionButton) findViewById(R.id.enshrink);
        enlarge=(FloatingActionButton) findViewById(R.id.enlarge);
        enlarge.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        enshrink.setBackgroundTintList(getResources().getColorStateList(R.color.white));

        webview.setVisibility(View.VISIBLE);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        shfit_choose=(TextView) findViewById(R.id.shfit_choose);
        document_guild_edit=(TextView) findViewById(R.id.document_guild_edit);
        title=(TextView) findViewById(R.id.title);
        type=(TextView) findViewById(R.id.type);
        type.setVisibility(View.VISIBLE);
        name=(TextView) findViewById(R.id.name);
        name.setVisibility(View.VISIBLE);
        time=(TextView) findViewById(R.id.time);
        time.setVisibility(View.VISIBLE);
        more.setText("转发");
        more.setVisibility(View.VISIBLE);
        rv=(RecyclerView) findViewById(R.id.rv);
        title.setText("转发通知");
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);

        webview.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return true;

            }

        });

        scroll_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘

                return false;
            }
        });

        back.setOnClickListener(this);
        enlarge.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        more.setOnClickListener(this);
        shfit_choose.setOnClickListener(this);
        document_guild_edit.setOnClickListener(this);
        scroll_view.setMyScrollListener(this);
        //        adapter=new DetailsAdapter();

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        api = HttpClient.getInstance().getGovernmentApi();
        opId=getIntent().getStringExtra("opId");
        userId=getIntent().getStringExtra("userId");
        noticetitle.setText(getIntent().getStringExtra("noticeTitle"));
        Call<Notification_Item> call=api.join_zhuanfa(opId,userId);

        call.enqueue(new Callback<Notification_Item>() {
            @Override
            public void onResponse(Call<Notification_Item> call, Response<Notification_Item> response) {

                notification_item=response.body();
                data_content=response.body().getData();
                list_add=response.body().getData().getFileList();
                type.setText(data_content.getNoticeType());
                name.setText(data_content.getOpCreateName());
                time.setText(data_content.getOpCreateTime());

                webview.loadUrl(data_content.getNoticeUrl());

                Log.e("onResponse: ",""+list_add.size());

                if(list_add != null && list_add.size()>0){
                    end.setVisibility(View.VISIBLE);
                    adapter=new Adapter_Addtion();
                    rv.setVisibility(View.VISIBLE);
                    rv.setAdapter(adapter);
                }else{
                    end.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Notification_Item> call, Throwable throwable) {

                end.setVisibility(View.GONE);
                Toast.makeText(Shift_Notification.this,"网络连接有误",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //添加layout大小发生改变监听器
        activityRootView.addOnLayoutChangeListener(this);
    }


    // 自定义的Scrollview_Focus的回掉方法，里面监听滑动，之后就通过handler每隔一定时间判断是否还在滑动，并不断回掉该方法设置FloatingActionButton的可见性
    // 悬浮按钮的显示随着webview是否在屏幕上显示而动态设置可见性，（就是这么简单，不用我们自己去监听scrollview的滑动，判断滑动值知道webview的可视性了，恩，简单的方法总是有的，尽可能去畅想是否有相关的API接口就好了，不必自己去实现）
    @Override
    public void sendDistanceY(int distance) {
        Rect scrollBounds = new Rect();
        scroll_view.getHitRect(scrollBounds);
        if (webview.getLocalVisibleRect(scrollBounds)) {

            enlarge.setVisibility(View.VISIBLE);
            enshrink.setVisibility(View.VISIBLE);
            //子控件至少有一个像素在可视范围内
            // Any portion of the childView, even a single pixel, is within the visible window
        } else {

            enlarge.setVisibility(View.GONE);
            enshrink.setVisibility(View.GONE);

            //子控件完全不在可视范围内
            // NONE of the childView is within the visible window
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值

//      System.out.println(oldLeft + " " + oldTop +" " + oldRight + " " + oldBottom);
//      System.out.println(left + " " + top +" " + right + " " + bottom);


        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

//            Toast.makeText(DocuHanddown_Activity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

            enlarge.setVisibility(View.GONE);
            enshrink.setVisibility(View.GONE);

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {

//            Toast.makeText(DocuHanddown_Activity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

            enlarge.setVisibility(View.VISIBLE);
            enshrink.setVisibility(View.VISIBLE);

        }


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back:
                finish();
                break;
            case R.id.more:

                if(document_guild_edit.getText().toString().trim().equals("") ){

                    Toast.makeText(this,"留言内容不能为空",Toast.LENGTH_SHORT).show();

                }else if(opIds.equals("")){
                    Toast.makeText(this,"请选择转发人",Toast.LENGTH_SHORT).show();
                }else{

                    new DialogUtil(Shift_Notification.this, this).showConfirm("是否立即转发该通知？", "您是否立即转发该通知给通知接收者？", "立即转发", "我再看看");

                }

                break;

             case R.id.enlarge:
                webview.loadUrl("javascript:amplify()") ;
                break;

            case R.id.enshrink:
                webview.loadUrl("javascript:narrow()");
                break;
            case R.id.shfit_choose:


                Intent intent=new Intent(this,Choose_Shift.class);
                intent.putExtra("opId",opId);
                intent.putExtra("userId",userId);
                intent.putExtra("opIds",opIds);
                startActivityForResult(intent,100);

                break;
        }
    }

    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent ,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.addtion.setText("附件"+(position+1)+" : ");

            holder.addtional.setText(list_add.get(position).getOpName());

            if(position==list_add.size()-1){
                holder.line.setVisibility(View.GONE);
            }else{
                holder.line.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    Intent intent = new Intent(Shift_Notification.this, DetailsFJActivity.class);
                    intent.putExtra("filePreview", list_add.get(position).getFilePreview());
                    intent.putExtra("type", "1");
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list_add.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) view.findViewById(R.id.addtion);
                addtional = (TextView) view.findViewById(R.id.addtional);
                line =  view.findViewById(R.id.line);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode==200){

            opIds=data.getStringExtra("opIds");

        }


    }


    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(Shift_Notification.this, "提交中...");
        popupWindowUtil.show();
        Call<Collection_Return> call=api.shift_commit(opId,userId,document_guild_edit.getText().toString().trim(),opIds);

        call.enqueue(new Callback<Collection_Return>() {
            @Override
            public void onResponse(Call<Collection_Return> call, Response<Collection_Return> response) {
                popupWindowUtil.dismiss();

                if(response.body().getData()){

                    new DialogUtil(Shift_Notification.this, Shift_Notification.this).showAlert("转发成功", "转发人员成功！", "知道了");

                }else{

                    Toast.makeText(Shift_Notification.this,"转发失败",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Collection_Return> call, Throwable throwable) {

                Toast.makeText(Shift_Notification.this,"网络连接有误",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        finish();
    }


}
