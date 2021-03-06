package com.lanwei.governmentstar.activity.lll;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.gwnz.DocumentBaseActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.DocumentBase;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.CircleImageView;
import com.lanwei.governmentstar.view.Dialog02;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//文件归档
public abstract class FileBaseActivity extends BaseActivity implements OnClickListener, MyScrollView_Focus.MyScrollListener {
    private static final String TAG = FileBaseActivity.class.getSimpleName();
    protected TextView document_qicao, document_qicaotime, more, title,
            documentWh, documentBh, documentType, themeTypes, isSign, signType, documentOvertime, documentSh, documentSy, documentQf;
    protected MyListView document_shenpi;
    protected DocumentAdapter documentAdapter;
    protected List<String> list;
    protected WebView webView;
    protected ImageView back, document_guild_k,iv_contacts;
    protected RecyclerView rv;
    protected ArrayList<String> data = null;
    protected Adapter_Addtion adapter;
    protected LinearLayout linearLayout, document_guild_layout;
    protected MyScrollView_Focus document_scrollView;
    protected EditText document_guild_edit;
    protected LinearLayout document_fujian;
    protected String uid, did;
    protected String opIds;
    protected PopupWindow popupWindow;
    DocumentBase documentBase;
    DownloadManager dm;
    private String present;
    private DownloadReceiver downloadReceiver;
    private Dialog02 dialog02;
    protected PopupWindowUtil popupWindowUtil;
    private String nBopId;
    private JSONObject dataJson;
    private int position_download;
    String path_addtion;
    private FloatingActionButton enlarge;
    private FloatingActionButton enshrink;
    private SharedPreferences amount_ShortcutBadger;
    private String filePreview;
    protected LinearLayout lnl_shenhe;

    abstract protected int getLayoutResId();

    abstract protected String getAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");

        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(getLayoutResId());
        present = getIntent().getStringExtra("present");
        init();


    }

    /**
     *
     */
    private void init() {
        // TODO Auto-generated method stub
        more = (TextView) findViewById(R.id.email_finish);
        title = (TextView) findViewById(R.id.tv_address);
        back = (ImageView) findViewById(R.id.back);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);

        lnl_shenhe = (LinearLayout) findViewById(R.id.lnl_shenhe);

        document_scrollView = (MyScrollView_Focus) findViewById(R.id.document_scrollView);
        document_qicao = (TextView) findViewById(R.id.document_qicao);
        document_qicaotime = (TextView) findViewById(R.id.document_qicaotime);
        document_shenpi = (MyListView) findViewById(R.id.document_shenpi);
        webView = (WebView) findViewById(R.id.document_webview);

        documentWh = (TextView) findViewById(R.id.document_wh);
        documentBh = (TextView) findViewById(R.id.document_bh);
        documentType = (TextView) findViewById(R.id.document_type);
        themeTypes = (TextView) findViewById(R.id.theme_types);
        isSign = (TextView) findViewById(R.id.is_sign);
        signType = (TextView) findViewById(R.id.sign_type);
        documentOvertime = (TextView) findViewById(R.id.document_overtime);
        documentSh = (TextView) findViewById(R.id.document_sh);
        documentSy = (TextView) findViewById(R.id.document_sy);
        documentQf = (TextView) findViewById(R.id.document_qf);



        rv = (RecyclerView) findViewById(R.id.recyclerview);

        document_fujian = (LinearLayout) findViewById(R.id.document_fujian);

        document_guild_layout = (LinearLayout) findViewById(R.id.document_guild_layout);
        document_guild_edit = (EditText) findViewById(R.id.document_guild_edit);
        document_guild_k = (ImageView) findViewById(R.id.document_guild_k);
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);




        document_shenpi.setFocusable(false);

        if (document_guild_k != null) {
            document_guild_k.setOnClickListener(this);
        }
        more.setOnClickListener(this);
        back.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        enlarge.setOnClickListener(this);
        list = new ArrayList<>();

        document_scrollView.setMyScrollListener(this);

        document_scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                return false;
            }
        });
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                return false;
            }
        });

        if (getLayoutResId() == R.layout.activity_documentapprove) {//如果是核发的话不加载

        } else {
//            getData();
        }
    }

    private void getData() {
        uid = new GetAccount(this).opId();
        did = new GetAccount(this).dptId();
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.documentFiction(getAction(), getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<JsonObject>() {
            /**
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JSONObject jsonObject = null;
                try {
//                    Log.e("json数据判断", response.body().getAsJsonObject("data").toString());
                    jsonObject = new JSONObject(response.body().getAsJsonObject("data").toString());
//                    Toast.makeText(DocumentBaseActivity.this, jsonObject.getBoolean("result")  ? "提交成功" : "提交失败，请重新尝试！",
//                            Toast.LENGTH_SHORT).show();
                    if (jsonObject == null || jsonObject.equals("")) {
                        Toast.makeText(FileBaseActivity.this, "空数据", Toast.LENGTH_SHORT).show();
                    } else {
                        documentBase = new DocumentBase();
                        DocumentBase.Data
                                dData = new DocumentBase.Data();
                        dData.setOpId(jsonObject.getString("opId"));
                        dData.setOpCreateName(jsonObject.getString("opCreateName"));
                        dData.setDocType(jsonObject.getString("docType"));
//                        dData.setDocTheme(jsonObject.getString("docTheme"));
                        dData.setDocTitle(jsonObject.getString("docTitle"));
                        dData.setIsHq(jsonObject.getString("isHq"));
                        dData.setOpShName(jsonObject.getString("opShName"));
                        dData.setOpCreateTime(jsonObject.getString("opCreateTime"));
                        dData.setDocUrl(jsonObject.getString("docUrl"));
                        JSONArray jsonArray = jsonObject.getJSONArray("flowList");
                        List<DocumentBase.FlowList> flowList = new ArrayList<DocumentBase.FlowList>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            flowList.add(new DocumentBase.FlowList(jsonArray.getJSONObject(i).getString("flowStatus"),
                                    jsonArray.getJSONObject(i).getString("flowName"),
                                    jsonArray.getJSONObject(i).getString("flowImageUrl"),
                                    jsonArray.getJSONObject(i).getString("flowContent"),
                                    jsonArray.getJSONObject(i).getString("flowTime")));
                        }
                        for (int i = Integer.parseInt(flowList.get(flowList.size() - 1).getFlowStatus()) + 1; i < 9; i++) {//补齐八位
                            flowList.add(new DocumentBase.FlowList(i + "", "", "", "", ""));
                        }
                        dData.setFlowList(flowList);
                        List<DocumentBase.DocStatus> fileLists = new Gson().
                                fromJson(jsonObject.getJSONArray("fileList").toString(), new TypeToken<List<DocumentBase.DocStatus>>() {
                                }.getType());
                        dData.setFileList(fileLists);
                        dData.setDocStatus(jsonObject.get("docStatus").toString());
                        documentBase.setData(dData);
                        documentAdapter = new DocumentAdapter(documentBase.getData().getFlowList(), FileBaseActivity.this);
                        document_shenpi.setAdapter(documentAdapter);
                        data = new ArrayList<>();
                        for (int i = 0; i < documentBase.getData().getFileList().size(); i++) {
                            data.add(documentBase.getData().getFileList().get(i).getOpName());
                        }
                        adapter = new Adapter_Addtion();
                        rv.setAdapter(adapter);


                        if (data.size() <= 0) {
                            document_fujian.setVisibility(View.GONE);
                        } else {
                            document_fujian.setVisibility(View.VISIBLE);
                        }

                        document_qicao.setText(documentBase.getData().getOpCreateName());//起草人
                        document_qicaotime.setText(documentBase.getData().getOpCreateTime());//起草时间

                        documentWh.setText("1");  //公文文号
                        documentBh.setText("2");  //公文编号
                        documentType.setText("3"); //公文类型
                        themeTypes.setText("4"); // 主题分类
                        isSign.setText("5");  //是否会签
                        signType.setText("6");  //会签类型
                        documentOvertime.setText("7");  //办结时间
                        documentSh.setText("8"); //审核人
                        documentSy.setText("9");//审阅人
                        documentQf.setText("10");//签发人
//                        document_title.setVisibility(View.GONE);
//                        lnl_shenhe.setVisibility(View.INVISIBLE);
                        if (data.size() == 0) {
                            document_fujian.setVisibility(View.GONE);
                        } else {
                            document_fujian.setVisibility(View.VISIBLE);
                        }

                        webView.setVisibility(View.VISIBLE);
                        webView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript脚本
                        webView.loadUrl(documentBase.getData().getDocUrl());

                        webView.setOnTouchListener(new View.OnTouchListener() {

                            @Override

                            public boolean onTouch(View v, MotionEvent ev) {

                                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                                return true;

                            }

                        });

                        initWebView();


                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FileBaseActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });
        more.setText("完成");
        title.setText("公文签发");
        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

//    //设置文字
//    protected void setViewText(TextView text, String first, String content) {
//        SpannableString spannableString = new SpannableString(first + content);
//        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_aaa)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_23)), first.length(), first.length() + content.length()
//                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        text.setText(spannableString);
//    }


    private void initWebView() {
        webView.getSettings().setTextZoom(100);
        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return true;
            }

        });


        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        Rect scrollBounds = new Rect();
        document_scrollView.getHitRect(scrollBounds);
        if (webView.getLocalVisibleRect(scrollBounds)) {

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
    public void sendDistanceY(int distance) {
        Rect scrollBounds = new Rect();
        document_scrollView.getHitRect(scrollBounds);
        if (webView.getLocalVisibleRect(scrollBounds)) {


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


    //设置文字
    protected void setViewText(TextView text, String first, String content) {
        SpannableString spannableString = new SpannableString(first + content);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_aaa)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_23)), first.length(), first.length() + content.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannableString);
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
//
//
//    @Override
//    public void sendDistanceY(int scrollY) {
//        Log.d("scroll","----------------------height:"+scrollY);
//        if(scrollY >= distance_active - distance_focus){  //如果滑动的距离大于或等于位置3到位置2的距离，那么说明内部绿色的顶部在位置2上面了，我们需要显示外部绿色栏了
//            button_focus.setVisibility(View.VISIBLE);
//        }else {  //反之隐藏
//            button_focus.setVisibility(View.GONE);
//        }
//    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.document_file_look: {//查看流程
                Intent intent = new Intent(this, Process2_Activity.class);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                startActivity(intent);
            }
            break;

            case R.id.back: {
                finish();
            }
            break;

            case R.id.document_guild_k: {//快速回复

            }
            break;

            case R.id.more: {//完成

            }
            break;

            case R.id.enlarge:
                //放大
                webView.loadUrl("javascript:amplify()");
                break;


            case R.id.enshrink:
                //缩小
                webView.loadUrl("javascript:narrow()");
                break;


            default:
                break;
        }
    }




    public class PopAdapter extends BaseAdapter {

        List<String> strs = null;
        LayoutInflater inflater = null;

        public PopAdapter(List<String> strs, Context context) {
            this.strs = strs;
            this.inflater = LayoutInflater.from(context);
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
        public View getView(int arg0, View convertView, ViewGroup arg2) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.adapter_approve_item, arg2, false);
                holder.adapter_approve_item_text = (TextView) convertView.findViewById(R.id.adapter_approve_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }
            holder.adapter_approve_item_text.setText(strs.get(arg0));
            return convertView;
        }

        class ViewHolder {
            TextView adapter_approve_item_text = null;
            ImageView imageView = null;
        }

    }

    /**
     * 接口处理
     */
    @Override
    protected void baseJsonNext(String response, String tag) {
        // TODO Auto-generated method stub
        super.baseJsonNext(response, tag);
        if (tag.equals(TAG + "xxx")) {

        }
    }

    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent, false);

            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.addtion.setText("附件" + (position + 1) + " : ");

            if (!TextUtils.isEmpty(data.get(position)) || data.size() > 0) {
                holder.addtional.setText(data.get(position));
            }

            if (position == data.size() - 1) {
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    position_download = position;

                    final String path = documentBase.getData().getFileList().get(position_download).getPath();
                    path_addtion = documentBase.getData().getFileList().get(position_download).getOpName();
                    filePreview = documentBase.getData().getFileList().get(position_download).getFilePreview();

                    if (path.contains(".jpg")
                            || path.contains(".png")
                            || path.contains(".doc")
                            || path.contains(".gif")
                            || path.contains(".xls")
                            || path.contains(".xlsx")
                            || path.contains(".docx")
                            || path.contains(".pdf")) {
                        Intent intent = new Intent(FileBaseActivity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", filePreview);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    } else {
                        new DialogUtil(FileBaseActivity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
                    }
                }
            });
        }

        public String getExtensionName(String filename) {
            if ((filename != null) && (filename.length() > 0)) {
                int dot = filename.lastIndexOf('.');
                if ((dot > -1) && (dot < (filename.length() - 1))) {
                    return filename.substring(dot + 1);
                }
            }
            return filename;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) view.findViewById(R.id.addtion);
                addtional = (TextView) view.findViewById(R.id.addtional);
                line = view.findViewById(R.id.line);
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

    public class DocumentAdapter extends BaseAdapter {

        List<DocumentBase.FlowList> strs = null;
        LayoutInflater inflater = null;

        public DocumentAdapter(List<DocumentBase.FlowList> strs, Context context) {
            this.strs = strs;
            this.inflater = LayoutInflater.from(context);
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
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.adapter_document_item, arg2, false);
                holder.adapter_document_texticon = (TextView) convertView.findViewById(R.id.adapter_document_texticon);
                holder.adapter_document_name = (TextView) convertView.findViewById(R.id.adapter_document_name);
                holder.adapter_document_time = (TextView) convertView.findViewById(R.id.adapter_document_time);
                holder.adapter_document_content = (TextView) convertView.findViewById(R.id.adapter_document_content);
                holder.adapter_document_icon = (CircleImageView) convertView.findViewById(R.id.adapter_document_icon);
                holder.adapter_document_line1 = convertView.findViewById(R.id.adapter_document_line1);
                holder.adapter_document_line2 = convertView.findViewById(R.id.adapter_document_line2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }

            holder.adapter_document_content.setText("");//防止listview不断加长item却没有改变造成的问题

            holder.adapter_document_line1.setVisibility(View.INVISIBLE);
            holder.adapter_document_line2.setVisibility(View.INVISIBLE);
            AutoRelativeLayout.LayoutParams layoutParams = (AutoRelativeLayout.LayoutParams) holder.adapter_document_icon.getLayoutParams();
            layoutParams.topMargin = AutoUtils.getPercentWidthSize(38);
            holder.adapter_document_icon.setLayoutParams(layoutParams);
            if (strs.get(arg0).getFlowName().equals("")) {//判断条件改变颜色如果没到//如果内容为null那是自己加的未审核
                holder.adapter_document_line2.setBackgroundColor(getResources().getColor(R.color.color_bb));
                holder.adapter_document_line1.setBackgroundColor(getResources().getColor(R.color.color_bb));
                holder.adapter_document_texticon.setBackgroundResource(R.drawable.gray);
                holder.adapter_document_name.setVisibility(View.INVISIBLE);
                holder.adapter_document_time.setVisibility(View.INVISIBLE);
                holder.adapter_document_content.setVisibility(View.INVISIBLE);
                holder.adapter_document_icon.setVisibility(View.INVISIBLE);
                if (!strs.get(arg0 - 1).getFlowName().equals("")) {//如果上一条有数据
                    holder.adapter_document_line1.setBackgroundColor(getResources().getColor(R.color.color_43a7e1));
                }
            } else {
                if ((!strs.get(arg0 + 1).getFlowName().equals("")) && (Math.abs(ManagerUtils.StringToData("yyyy年MM月dd日 hh:mm", strs.get(arg0).getFlowTime())
                        - Math.abs(ManagerUtils.StringToData("yyyy年MM月dd日 hh:mm", strs.get(arg0 + 1).getFlowTime())))) > 2678400) {//两个月份超过1个月翻倍
                    layoutParams.topMargin = AutoUtils.getPercentWidthSize((int) (38 * ((Math.abs(ManagerUtils.StringToData("yyyy年MM月dd日 hh:mm",
                            strs.get(arg0).getFlowTime()) - Math.abs(ManagerUtils.StringToData("yyyy年MM月dd日 hh:mm", strs.get(arg0 + 1).getFlowTime()))) / 2678400))));
                    holder.adapter_document_icon.setLayoutParams(layoutParams);
                }
                holder.adapter_document_line2.setBackgroundColor(getResources().getColor(R.color.color_43a7e1));
                holder.adapter_document_texticon.setBackgroundResource(R.drawable.blue01);
                holder.adapter_document_name.setVisibility(View.VISIBLE);
                holder.adapter_document_time.setVisibility(View.VISIBLE);
                holder.adapter_document_content.setVisibility(View.VISIBLE);
                holder.adapter_document_icon.setVisibility(View.VISIBLE);
                holder.adapter_document_name.setText(strs.get(arg0).getFlowName());
                holder.adapter_document_time.setText(strs.get(arg0).getFlowTime());
                holder.adapter_document_content.setText(strs.get(arg0).getFlowContent());
                Picasso.with(FileBaseActivity.this).load(strs.get(arg0).getFlowImageUrl())
                        .memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.adapter_document_icon);
            }
            ViewTreeObserver vto = holder.adapter_document_content.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (holder.adapter_document_content.getLineCount() == 1) {
                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line2.getLayoutParams();
                        layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_name);
                        holder.adapter_document_line2.setLayoutParams(layoutParams1);
                    } else {
                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line2.getLayoutParams();
                        layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_time);
                        holder.adapter_document_line2.setLayoutParams(layoutParams1);
                    }
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                    layoutParams1.addRule(RelativeLayout.ABOVE, R.id.adapter_document_texticon);
                    holder.adapter_document_line1.setLayoutParams(layoutParams1);
                    holder.adapter_document_texticon.setVisibility(View.VISIBLE);
                    if (arg0 == 0) {//首页
                        holder.adapter_document_line1.setVisibility(View.INVISIBLE);
                        holder.adapter_document_line2.setVisibility(View.VISIBLE);
                    } else if (arg0 == (strs.size() - 1)) {//尾页
                        holder.adapter_document_line1.setVisibility(View.VISIBLE);
                        holder.adapter_document_line2.setVisibility(View.INVISIBLE);
                    } else {
                        holder.adapter_document_line2.setVisibility(View.VISIBLE);
                        holder.adapter_document_line1.setVisibility(View.VISIBLE);
                        if (arg0 > 1 && strs.get(arg0 - 1).getFlowStatus().equals("6") && strs.get(arg0).getFlowStatus().equals("6")) {
                            //如果上一个就是会签人,并且这个也是
                            holder.adapter_document_texticon.setVisibility(View.GONE);
                            holder.adapter_document_line2.setVisibility(View.INVISIBLE);
                            if (holder.adapter_document_content.getLineCount() == 1) {
                                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                                layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_name);
                                holder.adapter_document_line1.setLayoutParams(layoutParams2);
                            } else {
                                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                                layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_time);
                                holder.adapter_document_line1.setLayoutParams(layoutParams2);
                            }
                        }
                    }
                    holder.adapter_document_content.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
            switch (Integer.parseInt(strs.get(arg0).getFlowStatus()) - 1) {
                case 0: {
                    holder.adapter_document_texticon.setText("起");
                }
                break;
                case 1: {
                    holder.adapter_document_texticon.setText("审");
                }
                break;
                case 2: {
                    holder.adapter_document_texticon.setText("阅");
                }
                break;
                case 3: {
                    holder.adapter_document_texticon.setText("校");
                }
                break;
                case 4: {
                    holder.adapter_document_texticon.setText("签");
                }
                break;
                case 5: {
                    holder.adapter_document_texticon.setText("会");
                }
                break;
                case 6: {
                    holder.adapter_document_texticon.setText("核");
                }
                break;
                case 7: {
                    holder.adapter_document_texticon.setText("档");
                }
                break;
            }
            return convertView;
        }

        class ViewHolder {
            TextView adapter_document_texticon, adapter_document_name, adapter_document_time, adapter_document_content = null;
            CircleImageView adapter_document_icon = null;
            View adapter_document_line1, adapter_document_line2;//线段上下
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {//收取选择的人员ID
            opIds = data.getStringExtra("opIds");
        }
    }

    public class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // @TODO SOMETHING
                Toast.makeText(FileBaseActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.e("放松放松放松", "" + downId);
            }
        }
    }

    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {

            final String path = documentBase.getData().getFileList().get(position_download).getPath();
            path_addtion = documentBase.getData().getFileList().get(position_download).getOpName();

            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);//获取系统下载的service
            DownloadUtil.startDownload(dm, FileBaseActivity.this, path, path_addtion, "正在下载...");

            downloadReceiver = new DownloadReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(downloadReceiver, intentFilter);

        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {

            amount_ShortcutBadger = getSharedPreferences("amount_ShortcutBadger", 0);
            Log.e("number", amount_ShortcutBadger.getInt("number", 0) + "");
            amount_ShortcutBadger.edit().putInt("number", amount_ShortcutBadger.getInt("number", 0) - 1).commit();
            ShortcutBadger.applyCount(FileBaseActivity.this, amount_ShortcutBadger.getInt("number", 0) - 1);

        }
    }


}
