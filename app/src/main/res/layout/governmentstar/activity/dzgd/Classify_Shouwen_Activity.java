package com.lanwei.governmentstar.activity.dzgd;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_Activity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Bean_QuickReply;
import com.lanwei.governmentstar.bean.Bean_Reply_Add;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Result_Message;
import com.lanwei.governmentstar.bean.Return_Shouwen;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.FileUtils;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.lanwei.governmentstar.view.wheel.WheelView;

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
 * Created by 蓝威科技-技术开发1 on 2017/9/4.
 */

public class Classify_Shouwen_Activity extends BaseActivity implements View.OnClickListener , MyScrollView_Focus.MyScrollListener , DialogUtil.OnClickListenner{

    private ImageView back;
    private ImageView replay;
    private TextView finish;
    private EditText message;
    private TextView ggbt_content;
    private TextView lwdw_content;
    private TextView lwlx_content;
    private TextView ztfl_content;
    private TextView cllx_content;
    private TextView qssj_content;
    private TextView bjsj_content;
    private TextView jjr_content;
    private TextView nbr_content;
    private TextView psr_content;
    private TextView ybr_content;
    private TextView cbr_content;
    private TextView blr_content;
    private TextView cyr_content;
    private TextView gdr_content;
    private WebView webview;
    private RecyclerView recyclerview;
    private LinearLayout document_fujian;
    private LinearLayout save_years;
    private LinearLayout flow;
    private Logging_Success bean;
    private GovernmentApi api;
    private Call<Return_Shouwen> call;
    private int position_download =0;
    private String path_addtion;
    private String filePreview;
    private List<Return_Shouwen.DataBean.FileListBean> file_addtion = new ArrayList<>();
    private PopupWindow popupWindow;
    private ArrayList<String> bcNx = new ArrayList<>();
    private String years = "";
    private MyScrollView_Focus scrollView;
    private FloatingActionButton enlarge;
    private FloatingActionButton enshrink;


    Map<String, String> outMap= new HashMap();
    private ArrayList<String> list_key =new ArrayList<>();
    private Adapter_Document popAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shouwen_classify);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweights();
        // 获取bean;
        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();

        call = api.showuen_comin(bean.getData().getOpId(),getIntent().getStringExtra("opId"));


        call.enqueue(new Callback<Return_Shouwen>() {
            @Override
            public void onResponse(Call<Return_Shouwen> call, Response<Return_Shouwen> response) {

                ggbt_content.setText(response.body().getData().getDocTitle());
                lwdw_content.setText(response.body().getData().getDocLwdw());
                lwlx_content.setText(response.body().getData().getDocLwlx());
                ztfl_content.setText(response.body().getData().getDocGwzt());
                cllx_content.setText(response.body().getData().getDocStatus());
                qssj_content.setText(response.body().getData().getOpCreateName());
                bjsj_content.setText(response.body().getData().getOpBjTime());
                jjr_content.setText(response.body().getData().getOpCreateName());
                nbr_content.setText(response.body().getData().getOpNbrName());
                psr_content.setText(response.body().getData().getOpPsrName());
                ybr_content.setText(response.body().getData().getOpYbrName());
                cbr_content.setText(response.body().getData().getOpCbrName());
                blr_content.setText(response.body().getData().getOpBsrName());
                cyr_content.setText("");
                gdr_content.setText(response.body().getData().getOpGdrName());
                bcNx.addAll(response.body().getData().getBcNx());
                webview.loadUrl(response.body().getData().getDocUrl());
                if(response.body().getData().getFileList() != null){
                    file_addtion = response.body().getData().getFileList();
                    recyclerview.setAdapter(new Adapter_Addtion());
                }

            }

            @Override
            public void onFailure(Call<Return_Shouwen> call, Throwable t) {

                Toast.makeText(Classify_Shouwen_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Classify_Shouwen_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(Classify_Shouwen_Activity.this,"快捷回复不能超过8条",Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(Classify_Shouwen_Activity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                                        popAdapter.notifyDataSetChanged();

                                    }else{
                                        Toast.makeText(Classify_Shouwen_Activity.this,"添加失败！",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Bean_Reply_Add> call, Throwable t) {
                                    Toast.makeText(Classify_Shouwen_Activity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{

                            Toast.makeText(Classify_Shouwen_Activity.this,"快捷内容不能为空",Toast.LENGTH_SHORT).show();
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


                  if(message.getText().toString().trim().equals("")){
                      Toast.makeText(this,"请填写归档说明！",Toast.LENGTH_SHORT).show();
                      return;
                  }

                  if(years.equals("")){
                      Toast.makeText(this,"请选择保存时间！",Toast.LENGTH_SHORT).show();
                      return;
                  }


                  new DialogUtil(Classify_Shouwen_Activity.this, Classify_Shouwen_Activity.this).showConfirm("是否立即归档文件？", "文件归档后该文件办理结束，您可以调取档案归档内容及打印公文处理单。", "立即归档", "暂不归档");

                break;

              case R.id.save_years:

                  // 弹出popupwindow前，调暗屏幕的透明度
                  WindowManager.LayoutParams lp = getWindow().getAttributes();
                  lp.alpha=(float) 0.8;
                  getWindow().setAttributes(lp);

                  // 加载popupwindow的布局
                  View view=getLayoutInflater().inflate(R.layout.popup_window_years,null ,false);
                  popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

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
                          popupWindow.dismiss();
                          years = bcNx.get(wheellistview1.getCurrentItem());
                      }
                  });

                  cancel.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          popupWindow.dismiss();
                      }
                  });

                  // 点击屏幕之外的区域可否让popupwindow消失
                  popupWindow.setFocusable(true);
                  popupWindow.setBackgroundDrawable(new BitmapDrawable());
                  popupWindow.setOnDismissListener(new PoponDismissListener());

                  View rootview = LayoutInflater.from(this).inflate(R.layout.layout_shouwen_classify, null);
                  // 设置popupwindow的显示位置
                  popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);


                  break;

              case R.id.flow:

                  Intent intent =new Intent(this, Convey_Files_Activity.class);
                  intent.putExtra("opId",getIntent().getStringExtra("opId"));
                  startActivity(intent);


                break;
        }

    }

    @Override
    public void yesClick() {

        Call<JsonObject> call2 =api.showuen_commit(bean.getData().getOpId(),getIntent().getStringExtra("opId"),message.getText().toString().trim(),years);
        call2.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body() != null && !response.body().equals("")){

                    if(response.body().get("result").getAsBoolean()){
                        new DialogUtil(Classify_Shouwen_Activity.this, Classify_Shouwen_Activity.this).showAlert("文件已归档", "该文件已成功归档，查看状态请在主页面点击状态按钮查看公文处理单！", "知道了");

                    }else{

                        Toast.makeText(Classify_Shouwen_Activity.this, "提交失败！", Toast.LENGTH_SHORT).show();
                    }
                }
                if(popupWindow != null){

                    popupWindow.dismiss();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(Classify_Shouwen_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void noClick() {
    }

    @Override
    public void onSingleClick() {

        Intent intent= new Intent();
        setResult(520,intent);
        Log.e("执行了没，欸有","的方式感到十分");
        finish();
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

    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha = (float) 1;
            getWindow().setAttributes(p);
            if(popupWindow != null){
                popupWindow.dismiss();
            }

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
                                Toast.makeText(Classify_Shouwen_Activity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Classify_Shouwen_Activity.this,"删除失败！",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result_Message> call, Throwable t) {
                            Toast.makeText(Classify_Shouwen_Activity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(Classify_Shouwen_Activity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", filePreview);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    } else {
                        if (FileUtils.checkFileExists(path_addtion)) {
                            File externalStorageDirectory = Environment.getExternalStorageDirectory();
                            FileUtils.openFile(externalStorageDirectory.getPath() + "/Download/" + path_addtion, Classify_Shouwen_Activity.this);
                        } else {
                            new DialogUtil(Classify_Shouwen_Activity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
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




    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {

            final String path = file_addtion.get(position_download).getPath();
            path_addtion = file_addtion.get(position_download).getOpName();

            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);//获取系统下载的service
            DownloadUtil.startDownload(dm, Classify_Shouwen_Activity.this, path, path_addtion, "正在下载...");

        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {
        }
    }

    void initweights(){

        back = (ImageView) findViewById(R.id.back);
        replay = (ImageView) findViewById(R.id.replay);
        message = (EditText) findViewById(R.id.message);
        finish = (TextView) findViewById(R.id.finish);
        back.setOnClickListener(this);
        replay.setOnClickListener(this);
        finish.setOnClickListener(this);
        ggbt_content = (TextView) findViewById(R.id.ggbt_content);
        lwdw_content =  (TextView) findViewById(R.id.lwdw_content);
        lwlx_content =  (TextView) findViewById(R.id.lwlx_content);
        ztfl_content =  (TextView) findViewById(R.id.ztfl_content);
        cllx_content =  (TextView) findViewById(R.id.cllx_content);
        qssj_content =  (TextView) findViewById(R.id.qssj_content);
        bjsj_content =  (TextView) findViewById(R.id.bjsj_content);
        jjr_content =  (TextView) findViewById(R.id.jjr_content);
        nbr_content =  (TextView) findViewById(R.id.nbr_content);
        psr_content =  (TextView) findViewById(R.id.psr_content);
        ybr_content =  (TextView) findViewById(R.id.ybr_content);
        cbr_content =  (TextView) findViewById(R.id.cbr_content);
        blr_content =  (TextView) findViewById(R.id.blr_content);
        cyr_content =  (TextView) findViewById(R.id.cyr_content);
        gdr_content =  (TextView) findViewById(R.id.gdr_content);
        webview =  (WebView) findViewById(R.id.webview);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(true);
        scrollView = (MyScrollView_Focus) findViewById(R.id.scrollView);
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);
        scrollView.setMyScrollListener(this);
        enlarge.setOnClickListener(this);
        enshrink.setOnClickListener(this);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setTextZoom(100);

        recyclerview =  (RecyclerView) findViewById(R.id.recyclerview);

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


        document_fujian =  (LinearLayout) findViewById(R.id.document_fujian);
        save_years =  (LinearLayout) findViewById(R.id.save_years);
        flow =  (LinearLayout) findViewById(R.id.flow);

        document_fujian.setOnClickListener(this);
        save_years.setOnClickListener(this);
        flow.setOnClickListener(this);

    }
}
