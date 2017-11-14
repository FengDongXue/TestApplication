package com.lanwei.governmentstar.activity.dzgd;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.icarus.entity.Image;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.gwnz.DocumentBaseActivity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_Activity;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Bean_QuickReply;
import com.lanwei.governmentstar.bean.Bean_Reply_Add;
import com.lanwei.governmentstar.bean.DocumentBase;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Result_Message;
import com.lanwei.governmentstar.bean.Return_Nizhi;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.FileUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.CircleImageView;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/1.
 */

public class Classify_Nizhi_Activity extends BaseActivity implements View.OnClickListener , MyScrollView_Focus.MyScrollListener , DialogUtil.OnClickListenner{


    private ImageView back;
    private ImageView replay;
    private TextView gwzh_content;
    private TextView gwbh_content;
    private TextView gwlx_content;
    private TextView ztfl_content;
    private TextView sfhq_content;
    private TextView hqlx_content;
    private TextView qcsj_content;
    private TextView bjsj_content;
    private TextView qcr_content;
    private TextView shr_content;
    private TextView jdr_content;
    private TextView syr_content;
    private TextView qfr_content;
    private TextView hqjg_content;
    private TextView hfr_content;
    private TextView gdr_content;
    private TextView share_method;
    private TextView save_year;
    private String share_key = "";
    private String share_key_temp = "";
    private TextView finish;
    private EditText message;
    private MyListView flow_listview;
    private WebView webview;
    private RecyclerView recyclerview;
    private RecyclerView recyclerView;
    private Logging_Success bean;
    private GovernmentApi api;
    private Call<Return_Nizhi> call;
    private List<Return_Nizhi.DataBean.FlowListBean> flow_list;
    private DocumentAdapter adapter;
    private List<Return_Nizhi.DataBean.FileListBean> file_addtion =new ArrayList<>();
    private int position_download =0;
    private String path_addtion;
    private String filePreview;
    private Adapter_Addtion adapter_addtion;
    private Adapter_Addtion2 adapter_addtion2;
    private LinearLayout flow;
    private LinearLayout document_fujian;
    private LinearLayout more_selections;
    private Return_Nizhi.DataBean dataBean;
    private PopupWindow popupWindow;
    private PopupWindow popupWindow2;
    private Map<String, String> gxState;
    private List<Return_Nizhi.DeptMapBean> deptMap;
    private MyScrollView_Focus scrollView;
    private FloatingActionButton enlarge;
    private FloatingActionButton enshrink;

    private ArrayList<IsChoose> list_gx =new ArrayList<>();
    private ArrayList<IsChoose> list_gx2 =new ArrayList<>();
    private ArrayList<String> list_dept =new ArrayList<>();

    private String zhusong = "";
    private String zhusong_temp = "";
    private String caosong = "";
    private String caosong_temp = "";

    private String years = "";

    Map<String, String> outMap= new HashMap();
    private ArrayList<String> list_key =new ArrayList<>();
    private Adapter_Document popAdapter;
    private ArrayList<String> bcNx = new ArrayList<>();
    private View rootview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nizhi_classify);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweights();
        // 获取bean;
        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();

        call =api.nizhi_comin(bean.getData().getOpId(),getIntent().getStringExtra("opId"));

        call.enqueue(new Callback<Return_Nizhi>() {

            @Override
            public void onResponse(Call<Return_Nizhi> call, Response<Return_Nizhi> response) {
                if(response.body().getData() != null){

                    webview.loadUrl(response.body().getData().getDocUrl());
                    flow_list = response.body().getData().getFlowList();
                    dataBean =response.body().getData();
                    gwzh_content.setText(response.body().getData().getGwzh());
                    gwbh_content.setText(response.body().getData().getDocCode());
                    gwlx_content.setText(response.body().getData().getDocGzlx());
                    ztfl_content.setText(response.body().getData().getDocGwzt());
                    sfhq_content.setText(response.body().getData().getIsHq());
                    hqlx_content.setText(response.body().getData().getHqLx());
                    qcsj_content.setText(response.body().getData().getOpCreateTime());
                    bjsj_content.setText(response.body().getData().getOpBjTime());
                    qcr_content.setText(response.body().getData().getDocQcrName());
                    shr_content.setText(response.body().getData().getDocShName());
                    syr_content.setText(response.body().getData().getDocSyName());
                    jdr_content.setText(response.body().getData().getDocJdName());
                    qfr_content.setText(response.body().getData().getDocQfName());
                    hqjg_content.setText(response.body().getData().getDocHqName());
                    hfr_content.setText(response.body().getData().getDocHfName());
                    gdr_content.setText("");

                    for (int i = Integer.parseInt(flow_list.get(flow_list.size() - 1).getFlowStatus()) + 1; i < 9; i++) {//补齐八位
                        flow_list.add(new Return_Nizhi.DataBean.FlowListBean(i+"" + "", "", "", "", ""));
                    }
                    adapter =new DocumentAdapter(flow_list,Classify_Nizhi_Activity.this);
                    flow_listview.setAdapter(adapter);
                    if(response.body().getData().getFileList() != null){
                        file_addtion = response.body().getData().getFileList();
                        adapter_addtion = new Adapter_Addtion();
                        recyclerview.setAdapter(adapter_addtion);
                    }

                    gxState = response.body().getGxState();
                    deptMap =response.body().getDeptMap();


                    for (Map.Entry<String, String> entry : gxState.entrySet()) {

                        list_gx.add(new IsChoose(entry.getKey(),entry.getValue(),false));
                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                    }

                }

            }

            @Override
            public void onFailure(Call<Return_Nizhi> call, Throwable t) {

                Toast.makeText(Classify_Nizhi_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });


        Call<Bean_QuickReply> call2 = api.getQuickReply(bean.getData().getOpId(),getIntent().getStringExtra("state"));

        call2.enqueue(new Callback<Bean_QuickReply>() {
            @Override
            public void onResponse(Call<Bean_QuickReply> call, Response<Bean_QuickReply> response) {

                JSONObject jsonObj =new JSONObject();
                try {
                    jsonObj = new JSONObject(response.body().getData().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator<String> nameItr = jsonObj.keys();
                String name;
                outMap = new HashMap<String, String>();
                while (nameItr.hasNext()) {
                    name = nameItr.next();
                    try {
                        outMap.put(name, jsonObj.getString(name));
                        list_key.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Bean_QuickReply> call, Throwable t) {
                Toast.makeText(Classify_Nizhi_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void sendDistanceY(int distance) {

        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
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

    public class DocumentAdapter extends BaseAdapter {

        List<Return_Nizhi.DataBean.FlowListBean> strs = null;
        LayoutInflater inflater = null;

        public DocumentAdapter(List<Return_Nizhi.DataBean.FlowListBean> strs, Context context) {
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
            final DocumentAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new DocumentAdapter.ViewHolder();
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
                holder = (DocumentAdapter.ViewHolder) convertView.getTag();// 取出ViewHolder对象
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
                Picasso.with(Classify_Nizhi_Activity.this).load(strs.get(arg0).getFlowImageUrl())
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


    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {

            holder.addtion.setText("附件" + (position + 1) + " : ");

            if (!TextUtils.isEmpty(file_addtion.get(position).getOpName()) || file_addtion.size() > 0) {
                holder.addtional.setText(file_addtion.get(position).getOpName());
            }

            if (position == file_addtion.size() - 1) {
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    position_download = position;

                    final String path = file_addtion.get(position_download).getPath();
                    path_addtion = file_addtion.get(position_download).getOpName();
                    filePreview = file_addtion.get(position_download).getFilePreview();

                    if (path.contains(".jpg")
                            || path.contains(".png")
                            || path.contains(".doc")
                            || path.contains(".gif")
                            || path.contains(".xls")
                            || path.contains(".xlsx")
                            || path.contains(".docx")
                            || path.contains(".pdf")) {
                        Intent intent = new Intent(Classify_Nizhi_Activity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", filePreview);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    } else {
                        if (FileUtils.checkFileExists(path_addtion)) {
                            File externalStorageDirectory = Environment.getExternalStorageDirectory();
                            FileUtils.openFile(externalStorageDirectory.getPath() + "/Download/" + path_addtion, Classify_Nizhi_Activity.this);
                        } else {
                            new DialogUtil(Classify_Nizhi_Activity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {

            if( file_addtion== null || file_addtion.size()==0){
                document_fujian.setVisibility(View.GONE);
                return 0;
            }
            return file_addtion.size();
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


    @Override
    public void yesClick() {

        Call<JsonObject> call=api.nizhi_commit(bean.getData().getOpId(),getIntent().getStringExtra("opId"),message.getText().toString().trim(),zhusong,caosong,share_key,years);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body() != null && !response.body().equals("")){

                    if(response.body().get("result").getAsBoolean()){
                        new DialogUtil(Classify_Nizhi_Activity.this, new Summit()).showAlert("文件已归档！", "该文件已归档，查看状态在档案柜列表中查看文件文件状态！", "知道了");

                    }else{

                        Toast.makeText(Classify_Nizhi_Activity.this, "提交失败！", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(Classify_Nizhi_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void noClick() {
    }

    @Override
    public void onSingleClick() {


    }



    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {

            final String path = file_addtion.get(position_download).getPath();
            path_addtion = file_addtion.get(position_download).getOpName();

            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);//获取系统下载的service
            DownloadUtil.startDownload(dm, Classify_Nizhi_Activity.this, path, path_addtion, "正在下载...");


        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {

            Intent intent = new Intent();
            setResult(520,intent);
            finish();

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back:

                finish();

                break;

            case R.id.replay:

                // 设置屏幕的透明度
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = (float) 0.8;
                getWindow().setAttributes(lp2);
                // 加载popupwindow的布局
                final View view2 = getLayoutInflater().inflate(R.layout.popwindow_sy3, null);
                final RecyclerView listView = (RecyclerView) view2.findViewById(R.id.popwindow_sy3_list);
                final ArrayList<Map<String,String>> popList = new ArrayList<Map<String,String>>();
                view2.findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(list_key.size()>7){
                            Toast.makeText(Classify_Nizhi_Activity.this,"快捷回复不能超过8条",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!((EditText)view2.findViewById(R.id.add_content)).getText().toString().equals("")){

                            Call<Bean_Reply_Add> call = api.setQuickReply(bean.getData().getOpId(),getIntent().getStringExtra("state"),((EditText)view2.findViewById(R.id.add_content)).getText().toString());

                            call.enqueue(new Callback<Bean_Reply_Add>() {
                                @Override
                                public void onResponse(Call<Bean_Reply_Add> call, Response<Bean_Reply_Add> response) {

                                    if(response.body().getResult()){
                                        list_key.add(response.body().getOpId());
                                        outMap.put(response.body().getOpId(),((EditText)view2.findViewById(R.id.add_content)).getText().toString());
                                        ((EditText) view2.findViewById(R.id.add_content)).setText("");
                                        Toast.makeText(Classify_Nizhi_Activity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                                        popAdapter.notifyDataSetChanged();

                                    }else{
                                        Toast.makeText(Classify_Nizhi_Activity.this,"添加失败！",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Bean_Reply_Add> call, Throwable t) {
                                    Toast.makeText(Classify_Nizhi_Activity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{

                            Toast.makeText(Classify_Nizhi_Activity.this,"快捷内容不能为空",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                // 为RecyclerView设置默认动画和线性布局管理器
                listView.setItemAnimator(new DefaultItemAnimator());
                //设置线性布局
                listView.setLayoutManager(new LinearLayoutManager(this));

                popAdapter = new Adapter_Document();
                listView.setAdapter(popAdapter);
                popupWindow = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams p = getWindow().getAttributes();
                        p.alpha = (float) 1;
                        getWindow().setAttributes(p);
                    }
                });
                int xy[] = new int[2];
                v.getLocationOnScreen(xy);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

                break;

            case R.id.enshrink:

                webview.loadUrl("javascript:narrow()");
                break;

            case R.id.enlarge:

                webview.loadUrl("javascript:amplify()");
                break;

            case R.id.finish:

               if(years.equals("") ){

                 Toast.makeText(this,"请选保存年限!",Toast.LENGTH_SHORT).show();
                   return;
               }
               if(share_key.equals("")){
                   Toast.makeText(this,"请选择共享原则!",Toast.LENGTH_SHORT).show();
                   return;
               }

               if(message.getText().toString().trim().equals("")){
                   Toast.makeText(this,"请填写共享说明!",Toast.LENGTH_SHORT).show();
                   return;
               }
                new DialogUtil(Classify_Nizhi_Activity.this, this).showConfirm("是否立即归档文件？", "文件归档后该文件办理结束，您可以调取档案归档内容及打印公文处理单。", "立即归档", "暂不归档");

                break;

            case R.id.more_selections:

                // 再次点击已
                zhusong_temp=zhusong;
                caosong_temp=caosong;
                share_key_temp=share_key;

                // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=(float) 0.8;
                getWindow().setAttributes(lp);

                // 加载popupwindow的布局
                View view=getLayoutInflater().inflate(R.layout.activity_popup_classify,null ,false);
                popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                TextView cancel = (TextView) view.findViewById(R.id.cancel);
                TextView identify = (TextView) view.findViewById(R.id.identify);
                share_method = (TextView) view.findViewById(R.id.share_method);
                save_year = (TextView) view.findViewById(R.id.save_year);
                save_year.setText(years);

                share_method.setText(gxState.get(share_key_temp));
                LinearLayout add_department = (LinearLayout) view.findViewById(R.id.add_department);
                LinearLayout main_department = (LinearLayout) view.findViewById(R.id.main_department);
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview2);
                // 为RecyclerView设置默认动画和线性布局管理器
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //设置线性布局
                recyclerView.setLayoutManager(new LinearLayoutManager(this){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });

                adapter_addtion2 =new Adapter_Addtion2();
                recyclerView.setAdapter(adapter_addtion2);
                LinearLayout share_way = (LinearLayout) view.findViewById(R.id.share_way);
                identify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        zhusong=zhusong_temp;
                        caosong=caosong_temp;
                        share_key=share_key_temp;

                        Log.e("zhusong",zhusong);
                        Log.e("caosong",caosong);
                        Log.e("share_key",share_key);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                share_way.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(recyclerView.getVisibility() == View.VISIBLE){
                            recyclerView.setVisibility(View.GONE);
                        }else{
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });


                main_department.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent= new Intent(Classify_Nizhi_Activity.this,Chooses_Receivers_Activity.class);
                        intent.putExtra("type","zhu");
                        intent.putExtra("zhusong",zhusong_temp);
                        intent.putExtra("caosong",caosong_temp);
                        intent.putExtra("type","zhu");
                        intent.putExtra("opId", dataBean.getOpId());
                        startActivityForResult(intent,3);

                    }
                });

                add_department.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Intent intent= new Intent(Classify_Nizhi_Activity.this,Chooses_Receivers_Activity.class);
//                        intent.putExtra("type","cao");
//                        intent.putExtra("zhusong",zhusong_temp);
//                        intent.putExtra("caosong",caosong_temp);
//                        intent.putExtra("opId", dataBean.getOpId());
//                        startActivityForResult(intent,4);


                        if(popupWindow != null){
                            popupWindow.dismiss();
                        }

                        // 弹出popupwindow前，调暗屏幕的透明度
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha=(float) 0.8;
                        getWindow().setAttributes(lp);

                        // 加载popupwindow的布局
                        View view=getLayoutInflater().inflate(R.layout.popup_window_years,null ,false);
                        popupWindow2=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                        final com.bigkoo.pickerview.lib.WheelView wheellistview1 = (com.bigkoo.pickerview.lib.WheelView)view.findViewById(R.id.wheellistview1);

                        wheellistview1.setAdapter(new ArrayWheelAdapter(bcNx));

                        if(years.equals("")){
                            wheellistview1.setCurrentItem(bcNx.size()/2);
                        }else{
                            for(int i=0;i<bcNx.size();i++){
                                if(years.equals(bcNx.get(i))){
                                    wheellistview1.setCurrentItem(i);
                                    break;
                                }
                            }
                        }

                        Log.e("怎么回事","额个人头给富人的发帖回复");
                        wheellistview1.setCyclic(false);
                        wheellistview1.setTextSize(16);
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        TextView identify = (TextView) view.findViewById(R.id.identify);

                        identify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow2.dismiss();
                                if(popupWindow != null){
                                    popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);
                                }
                                years = bcNx.get(wheellistview1.getCurrentItem());
                                if(save_year != null){
                                    save_year.setText(bcNx.get(wheellistview1.getCurrentItem()));
                                }
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow2.dismiss();
                                if(popupWindow != null){
                                    popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);
                                }
                            }
                        });

                        // 点击屏幕之外的区域可否让popupwindow消失
                        popupWindow2.setFocusable(true);
                        popupWindow2.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow2.setOnDismissListener(new PoponDismissListener());

                        rootview = LayoutInflater.from(Classify_Nizhi_Activity.this).inflate(R.layout.layout_shouwen_classify, null);
                        // 设置popupwindow的显示位置
                        popupWindow2.showAtLocation(rootview, Gravity.BOTTOM,0,0);


                    }
                });

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());

                rootview = LayoutInflater.from(this).inflate(R.layout.layout_nizhi_classify, null);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);

                break;

            case R.id.flow:

                Intent intent= new Intent(this,Process2_Activity.class);
                intent.putExtra("opId", dataBean.getOpId());
                startActivity(intent);

                break;

        }
    }


    class Adapter_Document extends RecyclerView.Adapter<Adapter_Document.MyViewHolder> {

        private View view = null;

        public Adapter_Document() {}

        @Override
        public Adapter_Document.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.adapter_approve_item, parent,false);

            return new Adapter_Document.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Document.MyViewHolder holder, final int position) {

            holder.adapter_approve_item_text.setText(outMap.get(list_key.get(position)));
            holder.adapter_approve_item_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    message.setText(outMap.get(list_key.get(position)));
                    message.setSelection(message.getText().length());
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<Result_Message> call =api.delQuickReply(list_key.get(position));
                    call.enqueue(new Callback<Result_Message>() {
                        @Override
                        public void onResponse(Call<Result_Message> call, Response<Result_Message> response) {

                            if(response.body().getResult()){
                                outMap.remove(list_key.get(position));
                                list_key.remove(position);
                                popAdapter.notifyDataSetChanged();
                                Toast.makeText(Classify_Nizhi_Activity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Classify_Nizhi_Activity.this,"删除失败！",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result_Message> call, Throwable t) {
                            Toast.makeText(Classify_Nizhi_Activity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });
        }

        @Override
        public int getItemCount() {
            return list_key.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView adapter_approve_item_text;
            private ImageView remove;

            public MyViewHolder(View itemView) {

                super(itemView);
                adapter_approve_item_text = (TextView) itemView.findViewById(R.id.adapter_approve_item_text);
                remove = (ImageView) itemView.findViewById(R.id.remove);

            }
        }
    }

    // 弹出快捷回复的adapter
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
            PopAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new PopAdapter.ViewHolder();
                convertView = inflater.inflate(R.layout.adapter_approve_item, arg2, false);
                holder.adapter_approve_item_text = (TextView) convertView.findViewById(R.id.adapter_approve_item_text);
                convertView.setTag(holder);
            } else {
                holder = (PopAdapter.ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }
            holder.adapter_approve_item_text.setText(strs.get(arg0));
            return convertView;
        }

        class ViewHolder {
            TextView adapter_approve_item_text = null;
            ImageView imageView = null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 520){
            zhusong_temp = data.getStringExtra("zhusong");
            caosong_temp = data.getStringExtra("caosong");
            Log.e("zhusong已选定，待提交",zhusong_temp);
            Log.e("caosong已选定，待提交",caosong_temp);
        }

    }

    class Adapter_Addtion2 extends RecyclerView.Adapter<Adapter_Addtion2.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion2() {

        }

        @Override
        public Adapter_Addtion2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_layout, parent, false);

            return new Adapter_Addtion2.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion2.MyViewHolder holder, final int position) {

            holder.no.setText(list_gx.get(position).getShare_way());
            if(list_gx.get(position).getNo().equals(share_key_temp)){
                holder.isChoosed_three.setImageDrawable(getResources().getDrawable(R.drawable.icon_x));
                holder.no.setTextColor(getResources().getColor(R.color.blue));
            }else{
                holder.isChoosed_three.setImageDrawable(getResources().getDrawable(R.drawable.icon_w));
                holder.no.setTextColor(getResources().getColor(R.color.color_23));
            }
            holder.three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!list_gx.get(position).getChoose()){

                        for(int i=0;i<list_gx.size();i++){
                            if(i==position){
                                share_key_temp = list_gx.get(position).getNo();
                                list_gx.get(i).setChoose(true);
                                share_method.setText(gxState.get(list_gx.get(position).getNo()));
                                Log.e("name","个地方士大夫发到广泛大概");
                            }else{
                                list_gx.get(i).setChoose(false);
                            }
                        }
                        adapter_addtion2.notifyDataSetChanged();
                    }

                }
            });



        }

        @Override
        public int getItemCount() {

            if( list_gx== null || list_gx.size()==0){
                return 0;
            }
            return list_gx.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView isChoosed_three;
            LinearLayout three;
            TextView no;

            public MyViewHolder(View itemView) {

                super(itemView);
                isChoosed_three = (ImageView) view.findViewById(R.id.isChoosed_three);
                three = (LinearLayout) view.findViewById(R.id.three);
                no = (TextView) view.findViewById(R.id.no);
            }
        }
    }

    void initweights(){

        back = (ImageView) findViewById(R.id.back);
        replay = (ImageView) findViewById(R.id.replay);
        back.setOnClickListener(this);
        replay.setOnClickListener(this);
        gwzh_content = (TextView) findViewById(R.id.gwzh_content);
        gwbh_content = (TextView) findViewById(R.id.gwbh_content);
        gwlx_content = (TextView) findViewById(R.id.gwlx_content);
        ztfl_content = (TextView) findViewById(R.id.ztfl_content);
        sfhq_content = (TextView) findViewById(R.id.sfhq_content);
        hqlx_content = (TextView) findViewById(R.id.hqlx_content);
        qcsj_content = (TextView) findViewById(R.id.qcsj_content);
        bjsj_content = (TextView) findViewById(R.id.bjsj_content);
        qcr_content = (TextView) findViewById(R.id.qcr_content);
        shr_content = (TextView) findViewById(R.id.shr_content);
        jdr_content = (TextView) findViewById(R.id.jdr_content);
        syr_content = (TextView) findViewById(R.id.syr_content);
        qfr_content = (TextView) findViewById(R.id.qfr_content);
        hqjg_content = (TextView) findViewById(R.id.hqjg_content);
        hfr_content = (TextView) findViewById(R.id.hfr_content);
        gdr_content = (TextView) findViewById(R.id.gdr_content);
        finish = (TextView) findViewById(R.id.finish);
        message = (EditText) findViewById(R.id.message);
        flow_listview = (MyListView) findViewById(R.id.flow_listview);
        flow = (LinearLayout) findViewById(R.id.flow);
        document_fujian = (LinearLayout) findViewById(R.id.document_fujian);
        more_selections = (LinearLayout) findViewById(R.id.more_selections);
        scrollView = (MyScrollView_Focus) findViewById(R.id.scrollView);
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);
        scrollView.setMyScrollListener(this);
        enlarge.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        flow.setOnClickListener(this);
        finish.setOnClickListener(this);
        more_selections.setOnClickListener(this);

        webview = (WebView) findViewById(R.id.webview);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(true);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setTextZoom(100);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        // 为RecyclerView设置默认动画和线性布局管理器
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recyclerview.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        bcNx.add("短期存档");
        bcNx.add("10");
        bcNx.add("20");
        bcNx.add("30");
        bcNx.add("50");
        bcNx.add("永久");

    }

    class IsChoose{

        private String no;
        private String share_way;
        private Boolean isChoose;


        public IsChoose(String no,String share_way, Boolean isChoose) {
            this.no = no;
            this.share_way = share_way;
            this.isChoose = isChoose;
        }

        public String getShare_way() {
            return share_way;
        }

        public void setShare_way(String share_way) {
            this.share_way = share_way;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public Boolean getChoose() {
            return isChoose;
        }

        public void setChoose(Boolean choose) {
            isChoose = choose;
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

}
