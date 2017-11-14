package com.lanwei.governmentstar.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.icarus.Icarus;
import com.icarus.TextViewToolbar;
import com.icarus.Toolbar;
import com.icarus.button.Button;
import com.icarus.button.FontScaleButton;
import com.icarus.button.TextViewButton;
import com.icarus.entity.Html;
import com.icarus.entity.Options;
import com.icarus.popover.FontScalePopoverImpl;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zwyx.CreateEmailActivity;
import com.lanwei.governmentstar.adapter.TagAdapter;
import com.lanwei.governmentstar.adapter.TagAdapter2;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Module;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.FlowTagLayout;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.OnTagClickListener;
import com.lanwei.governmentstar.utils.OnTagSelectListener;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class Notification_Edit_Activity extends BaseActivity implements View.OnClickListener, DialogUtil.OnClickListenner {


    private EditText theme;
    private FlowTagLayout names;
    private TextView title;
    private TextView module;
    private TextView remind_receivers;
    private TextView files_choose;
    private ImageView add_files;
    private TextView send;
    private ImageView add_persons;
    private LinearLayout add_modules;
    private ImageView back;
    private WebView webview;
    private RecyclerView rv;
    private Icarus icarus;
    private ArrayList<File> fileList = new ArrayList<>();
    private Adapter_Addtion adapter_addtion =new Adapter_Addtion();
    private String module_name = "";
    private String module_id = "";
    private GovernmentApi api;
    private String opIds = "";
    private String opIds2 = "";
    private String opIds_upload = "";
    private String opBsrId = "";
    private Logging_Success bean;
    private String content = "";
    private PopupWindowUtil popupWindowUtil;
    private TagAdapter2<String> mFlowerAdapter;
    private ArrayList<String> opIdlist;
    private ArrayList<String> addresseNameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_notification);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        theme= (EditText) findViewById(R.id.theme);
        names= (FlowTagLayout) findViewById(R.id.names);
        module= (TextView) findViewById(R.id.module);
        remind_receivers= (TextView) findViewById(R.id.remind_receivers);
        files_choose= (TextView) findViewById(R.id.files_choose);
        send= (TextView) findViewById(R.id.send);
        add_files= (ImageView) findViewById(R.id.add_files);
        title= (TextView) findViewById(R.id.title);
        add_persons= (ImageView) findViewById(R.id.add_persons);
        add_modules= (LinearLayout) findViewById(R.id.add_modules);
        back= (ImageView) findViewById(R.id.back);
        webview= (WebView) findViewById(R.id.webview);
        rv= (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        back.setOnClickListener(this);
        add_persons.setOnClickListener(this);
        add_modules.setOnClickListener(this);
        add_files.setOnClickListener(this);
        send.setOnClickListener(this);
        initWebView();

        TextViewToolbar toolbar = new TextViewToolbar();
        Options options = new Options();
        options.setPlaceholder("请选择通知模板 ： ");
        //  img: ['src', 'alt', 'width', 'height', 'data-non-image']
        // a: ['href', 'target']
        //, "data-non-image"
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));

        icarus = new Icarus(toolbar, options, webview);
        prepareToolbar(toolbar, icarus);

        icarus.getContent(new com.icarus.Callback() {
            @Override
            public void run(String params) {
                Gson gson = new Gson();
                Html html = gson.fromJson(params, Html.class);
                content = html.getContent();
                Log.e("发送通知Content gotten", html.getContent());
            }
        });

        icarus.loadCSS("file:///android_asset/editor.css");
        icarus.loadJs("file:///android_asset/test.js");
        icarus.render();

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rv.setAdapter(adapter_addtion);

        if(fileList.size()==0){
            rv.setVisibility(View.GONE);
        }

    }

    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        webview.setInitialScale(100);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setTextZoom(100);

//        // webview和scrollview的滑动冲突，设置webview不响应点击（包括滑动）事件
//        webview.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//
//            public boolean onTouch(View v, MotionEvent ev) {
//
//                ((WebView) v).requestDisallowInterceptTouchEvent(false);
//                return true;
//
//            }
//
//        });

        // 取消webview的垂直，水平
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);

    }

    private Toolbar prepareToolbar(TextViewToolbar toolbar, Icarus icarus) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "Simditor.ttf");
        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put(Button.NAME_BOLD, R.id.button_bold);
        generalButtons.put(Button.NAME_OL, R.id.button_list_ol);
        generalButtons.put(Button.NAME_BLOCKQUOTE, R.id.button_blockquote);
        generalButtons.put(Button.NAME_UL, R.id.button_list_ul);
        generalButtons.put(Button.NAME_ALIGN_LEFT, R.id.button_align_left);
        generalButtons.put(Button.NAME_ALIGN_CENTER, R.id.button_align_center);
        generalButtons.put(Button.NAME_ALIGN_RIGHT, R.id.button_align_right);
        generalButtons.put(Button.NAME_ITALIC, R.id.button_italic);
        generalButtons.put(Button.NAME_UNDERLINE, R.id.button_underline);
        generalButtons.put(Button.NAME_STRIKETHROUGH, R.id.button_strike_through);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }

//        imageButtonTextView = (TextView) findViewById(R.id.button_image);
//        imageButtonTextView.setOnClickListener(this);
//        imageButtonTextView.setTypeface(iconfont);
//        TextViewButton imageButton = new TextViewButton(imageButtonTextView, icarus);
//        imageButton.setName(Button.NAME_IMAGE);
//        imageButton.setPopover(new ImagePopoverImpl(imageButtonTextView, icarus));
//        toolbar.addButton(imageButton);  //不注释掉的话不能自定义点击事件

        TextView fontScaleTextView = (TextView) findViewById(R.id.button_font_scale);
        fontScaleTextView.setTypeface(iconfont);
        TextViewButton fontScaleButton = new FontScaleButton(fontScaleTextView, icarus);
        fontScaleButton.setPopover(new FontScalePopoverImpl(fontScaleTextView, icarus));
        toolbar.addButton(fontScaleButton);

        return toolbar;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back:

              finish();
                break;

            case R.id.add_persons:

                Intent intent = new Intent(this,ChooseReceivers2_Activity.class);

//                if(opIdlist != null && opIdlist.size()>0){
//
//                    for(int i=0;i<opIdlist.size();i++){
//
//                        opIds+=opIdlist.get(i)+",";
//                    }
//                        opIds=opIds.substring(0,opIds.length()-1);
//                }

                intent.putExtra("opIds",opIds);
                intent.putExtra("opBsrId",opBsrId);
                startActivityForResult(intent,001);

                Log.e("带过去已选择的人opIds",opIds);
                Log.e("带过去已选择的人opBsrId",opBsrId);
                Log.e("带过去已选择的人opIds_upload",opIds_upload);

                break;

            case R.id.add_modules:
                Intent intent2 = new Intent(this,Modules_Type_Activity.class);
                intent2.putExtra("module_name",module_name);
                intent2.putExtra("type","1");
                startActivityForResult(intent2,003);

                break;

            case R.id.add_files:

                if (isGrantExternalRW(Notification_Edit_Activity.this)) {

                    opFileManager();
                }else {
                    Toast.makeText(Notification_Edit_Activity.this, "请检查是否开启读写权限", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.send:


                icarus.getContent(new com.icarus.Callback() {
                    @Override
                    public void run(String params) {
                        Gson gson = new Gson();
                        Html html = gson.fromJson(params, Html.class);
                        content = html.getContent();
                        Log.e("发送通知Content gotten", html.getContent());
                    }
                });

                if(theme.getText().toString().trim().equals("")){
                    Toast.makeText(this,"请输入公告标题",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(module_id.equals("")){
                    Toast.makeText(this,"请选择公告类型",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(opIds_upload.equals("")){
                    Toast.makeText(this,"请选择要转发的人",Toast.LENGTH_SHORT).show();
                    return;
                }

                new DialogUtil(Notification_Edit_Activity.this, this).showConfirm("是否立即发送该通知？", "您是否已完成编辑，立即发送该公告通知吗？", "立即发送", "暂时不");

                break;

        }
    }


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                Uri newUri;
                if (data == null) {
                    return;
                }

                try {
                    newUri = Uri.parse(CropUtils.getPath(this, data.getData()));
                    Log.e("小于25File", String.valueOf(newUri));
                    File file = new File(String.valueOf(newUri));
                    if (file != null) {
                        fileList.add(file);
                        rv.setVisibility(View.VISIBLE);
                        adapter_addtion.notifyDataSetChanged();
                    }

                    if(fileList != null && fileList.size() >= 1){
                        files_choose.setVisibility(View.GONE);
                    }else{
                        files_choose.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "请通过其他方式选择该附件", Toast.LENGTH_SHORT).show();
                }

                break;

            case 004:

                module.setText(data.getStringExtra("module"));
                module.setTextSize(18);
                module.setTextColor(getResources().getColor(R.color.color_23));
                module_name=data.getStringExtra("module");
                module_id=data.getStringExtra("module_id");
                api = HttpClient.getInstance().getGovernmentApi();
                Call<Module> call=api.module_choose(data.getStringExtra("module_id"));

                call.enqueue(new Callback<Module>() {
                    @Override
                    public void onResponse(Call<Module> call, Response<Module> response) {

//                        webview.loadDataWithBaseURL(null, response.body().getData(), "text/html", "UTF-8", null);
//                        icarus.render();
                        icarus.setContent(response.body().getData());
//                        icarus.insertHtml(response.body().getData());

                    }

                    @Override
                    public void onFailure(Call<Module> call, Throwable t) {

                        Toast.makeText(Notification_Edit_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                    }
                });


                break;

            case 9:
                mFlowerAdapter =new TagAdapter2(this);
                opIds="";
                opIds_upload="";

                if (!data.getStringExtra("opIds").equals("")) {
                    opIds = data.getStringExtra("opIds");
                    opIds_upload = data.getStringExtra("names");
                }
                Log.e("后一个界面带过来的已选择人的ID", opIds);
                String[] opid_list = opIds.split(",");
                // 数组转为Arraylist
                opIdlist = new ArrayList<>(Arrays.asList(opid_list));

                Log.e("后一个界面带过来的已选择人的人名", opIds_upload);
                String[] opid_list2 = opIds_upload.split(",");


                // 数组转为Arraylist
                addresseNameList = new ArrayList<>(Arrays.asList(opid_list2));

                for(int i=0;i<addresseNameList.size();i++){
                    if(addresseNameList.get(i).trim().equals("")){
                       addresseNameList.remove(i);
                    }
                }


                if(addresseNameList != null && addresseNameList.size()>0){
                    names.setVisibility(View.VISIBLE);
                    remind_receivers.setVisibility(View.INVISIBLE);

                }else{
                    names.setVisibility(View.GONE);
                    remind_receivers.setVisibility(View.VISIBLE);
                }


                mFlowerAdapter.onlyAddAll(addresseNameList);
                mFlowerAdapter.setPosition(-11);
                names.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
                names.setAdapter(mFlowerAdapter);
                mFlowerAdapter.notifyDataSetChanged();

                names.setOnTagClickListener(new OnTagClickListener() {
                    @Override
                    public void onItemClick(FlowTagLayout parent, View view, int position) {

                        mFlowerAdapter.setPosition(position);
                        mFlowerAdapter.notifyDataSetChanged();
                        Log.e("click or not","yes or no");
                        if(addresseNameList != null && addresseNameList.size() <= 0){
                            names.setVisibility(View.GONE);
                        }

                    }
                });

                mFlowerAdapter.removeListner(new TagAdapter2.RemoveListner() {
                    @Override
                    public void setRemListner(int position) {

                        opIdlist.remove(position);
                        addresseNameList.remove(position);

                        if(opIdlist.size()<=0){
                            remind_receivers.setVisibility(View.VISIBLE);
                        }
                        opIds = "";
                        opIds_upload = "";
                        if (opIdlist != null && opIdlist.size() >= 0) {
                            for (int i = 0; i < opIdlist.size(); i++) {
                                opIds += opIdlist.get(i) + ",";
                            }
                        }
                        if (!opIds.equals("")) {
                            //去掉最后一个字符串，因为最后一个字符串是逗号
                            opIds = opIds.substring(0, opIds.length() - 1);
                        }else{
                            names.setVisibility(View.GONE);
                        }
                        Log.e("删除后的选择人员的ID", opIds);

                        if (addresseNameList != null && addresseNameList.size() >= 0) {
                            for (int i = 0; i < addresseNameList.size(); i++) {
                                opIds_upload += addresseNameList.get(i) + ",";
                            }
                        }
                        if (!opIds_upload.equals("")) {
                            //去掉最后一个字符串，因为最后一个字符串是逗号
                            opIds_upload = opIds_upload.substring(0, opIds_upload.length() - 1);
                        }
                        Log.e("删除后的选择人员的opIds_upload", opIds_upload);


                        mFlowerAdapter.setPosition(-11);
                        mFlowerAdapter.notifyDataSetChanged();

                    }
                });

//                names.setText(data.getStringExtra("names"));
//                opIds = data.getStringExtra("names");
//                opIds_upload = data.getStringExtra("opIds");
//                Log.e("已选择的人", opIds);
//                Log.e("已选择的人", opIds_upload);

                break;
        }

    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(popupWindowUtil != null){
            popupWindowUtil.dismiss();
        }
    }

    private static final int FILE_SELECT_CODE = 0X111;
    /**
     * 打开文件管理
     */
    private void opFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件!"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void yesClick() {

        long fileSize = 0;
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            Log.e("发送邮件file", file.getPath() + "附件大小   :   " + file.length() / 50 + "M");
            fileSize += file.length();
        }
        if (fileSize > 1048576 * 50) {      //   1kb = 1024byte  1m = 1048576byte
            Toast.makeText(this, "上传附件太大了" , Toast.LENGTH_SHORT).show();
            return;
        }

        popupWindowUtil = new PopupWindowUtil(Notification_Edit_Activity.this, "提交中...");
        popupWindowUtil.show();
        sendNotice();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

    }

    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = LayoutInflater.from(Notification_Edit_Activity.this).inflate(R.layout.addition_files, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {

//            holder.addtion.setText("附件" + (position + 1) + " : ");
//            Log.e("附件路径", fileList.get(position).toString());
            holder.addtion.setVisibility(View.GONE);
            holder.addtional.setText(Uri.decode(getFileName(fileList.get(position).toString())));
            Log.e("name",Uri.decode(getFileName(fileList.get(position).toString())));
            holder.addtional.setTextColor(getResources().getColor(R.color.blue));
            holder.line.setVisibility(View.INVISIBLE);
            holder.remove_fj.setVisibility(View.VISIBLE);
            holder.remove_fj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fileList.remove(fileList.get(position));

                    if(fileList.size()>0){
                        rv.setVisibility(View.VISIBLE);
                        files_choose.setVisibility(View.GONE);

                    }else{
                        rv.setVisibility(View.GONE);
                        files_choose.setVisibility(View.VISIBLE);
                    }


                    adapter_addtion.notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {

            if(fileList ==null){
                return 0;
            }

            return fileList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            ImageView remove_fj;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) itemView.findViewById(R.id.addtion);
                addtional = (TextView) itemView.findViewById(R.id.addtional);
                remove_fj = (ImageView) itemView.findViewById(R.id.remove_fj);
                line = itemView.findViewById(R.id.line);
            }
        }

        public String getFileName(String pathandname){

            int start=pathandname.lastIndexOf("/");
            if(start!=-1){
                return pathandname.substring(start+1,pathandname.length());
            }else{
                return null;
            }

        }
    }


    public  MultipartBody filesToMultipartBody(String userId,String noticeTemplateId,String noticeTitle,String noticeContent,String opIds,List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("userId",userId);
        builder.addFormDataPart("noticeTemplateId",noticeTemplateId);
        builder.addFormDataPart("noticeTitle",noticeTitle);
        builder.addFormDataPart("noticeContent",noticeContent);
        builder.addFormDataPart("opIds",opIds);

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            String mimeType = getMIMEType(file);
            Log.e("mimeType",mimeType);
            RequestBody requestBody = RequestBody.create(MediaType.parse(mimeType), file);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addPart(requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


    void sendNotice(){

//                RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id
        Log.e("邮件主题", theme.getText().toString());
        Log.e("邮件内容", content);
        Log.e("发送邮件opid", opIds_upload);

        RequestBody notice_Title = RequestBody.create(MediaType.parse("multipart/form-data"), theme.getText().toString()); //新建邮件的标题
        RequestBody notice_Content = RequestBody.create(MediaType.parse("multipart/form-data"), content);  //新建邮件的内容
        String opId = new GetAccount(this).opId();
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id
        RequestBody noticeTemplateId = RequestBody.create(MediaType.parse("multipart/form-data"), module_id);  //当前登陆者的Id

        RequestBody opIds2 = RequestBody.create(MediaType.parse("multipart/form-data"), opIds);  //收件人的Id

        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            paramsMap.put("file\"; filename=\"" + file.getName(), fileBody);
        }
        Log.e("已选择的人",opIds_upload);
        Log.e("已选择的人",opIds);
//        RequestBody userId, RequestBody noticeTemplateId, RequestBody noticeTitle,RequestBody noticeContent, RequestBody opIds, Map<String, RequestBody> params,
        RetrofitHelper.getInstance().noticeEdit(userId, noticeTemplateId, notice_Title, notice_Content, opIds2, paramsMap, new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {
                Toast.makeText(Notification_Edit_Activity.this, "发送失败", Toast.LENGTH_SHORT).show();
                Log.e("发送通知失败", message);
                popupWindowUtil.dismiss();
            }

            @Override
            protected void parseJson(String data) {

                popupWindowUtil.dismiss();

                JSONObject dataJson = null;
                try {
                    dataJson = new JSONObject(data);
                    boolean result = dataJson.getBoolean("result");
                    Log.e("返回", String.valueOf(result));
                    dataJson.getBoolean("result");
                    Toast.makeText(Notification_Edit_Activity.this, dataJson.getBoolean("result") ? "发送成功" : "发送失败，请重新尝试！",
                            Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent=new Intent();
                setResult(510,intent);
                finish();


            }
        });

//        Call<JsonObject> call = api.noticeEdit(filesToMultipartBody(opId,module_id,theme.getText().toString(),content,opIds_upload, fileList));
//
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                popupWindowUtil.dismiss();
//
//                JSONObject dataJson = null;
//                try {
//                    dataJson = new JSONObject(response.body().toString());
//                    boolean result = dataJson.getBoolean("result");
//                    Log.e("返回", String.valueOf(result));
//                    dataJson.getBoolean("result");
//                    Toast.makeText(Notification_Edit_Activity.this, dataJson.getBoolean("result") ? "发送成功" : "发送失败，请重新尝试！",
//                            Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                finish();
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                popupWindowUtil.dismiss();
//
//                Toast.makeText(Notification_Edit_Activity.this, "发送失败", Toast.LENGTH_SHORT).show();
//                Log.e("发送通知失败", "发送通知失败");
//
//            }
//        });

    }

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;
        }
        return true;
    }


    private String getMIMEType(File file)
    {
        String type="*/*";
        String fName=file.getName();
        Log.e("name",fName);
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名 */
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        Log.e("end",end);
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }


    private final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",      "image/bmp"},
            {".c",        "text/plain"},
            {".class",    "application/octet-stream"},
            {".conf",    "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",    "application/x-gtar"},
            {".gz",        "application/x-gzip"},
            {".h",        "text/plain"},
            {".htm",    "text/html"},
            {".html",    "text/html"},
            {".jar",    "application/java-archive"},
            {".java",    "text/plain"},
            {".jpeg",    "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",        "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",    "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",    "video/mp4"},
            {".mpga",    "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".prop",    "text/plain"},
            {".rar",    "application/x-rar-compressed"},
            {".rc",        "text/plain"},
            {".rmvb",    "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",        "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
            {".xml",    "text/plain"},
            {".z",        "application/x-compress"},
            {".zip",    "application/zip"},
            {"",        "*/*"}
    };

    public static long getFileSize(File file) throws Exception {
        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }


}
