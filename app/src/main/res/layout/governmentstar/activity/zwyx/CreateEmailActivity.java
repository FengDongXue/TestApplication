package com.lanwei.governmentstar.activity.zwyx;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.icarus.Callback;
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
import com.lanwei.governmentstar.activity.Notification_Edit_Activity;
import com.lanwei.governmentstar.adapter.TagAdapter;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.FlowTagLayout;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.OnTagClickListener;
import com.lanwei.governmentstar.utils.OnTagSelectListener;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/18.
 */

public class CreateEmailActivity extends AutoLayoutActivity implements View.OnClickListener, DialogUtil.OnClickListenner {

    //发送
    private TextView email_sent, tv_address;
    //返回键,圆角图，添加收件人，添加附件
    private ImageView back, iv_contacts, receiver_add, accessory_add;

    WebView webView;
    protected Icarus icarus;
    private TextView pic;
    private Intent intent;
    private RecyclerView rv_fjname;
    private View inlookLine;
    private ScrollView scrollview;


    private int isNotChoosed = 0;
    private String opids = "";
    private FlowTagLayout addressRelView;
    //    private RecyclerView addressRelView;
    private ArrayList<String> addresseNameList = new ArrayList<>();
    private AddfjAdapter fjadapter;
    private ImageView look1;
    private ImageView inlook1;
    private View inlookLine1;
    private EditText mail_title;
    private String content = "";
    private PopupWindowUtil popupWindowUtil;
    private TagAdapter<String> mFlowerAdapter;
    private ArrayList<String> opIdlist;
    private TextView sjnameGone;
    private TextView fjName;
    private TextView sjName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.create_email_layout);

        initview();

        webView = (WebView) findViewById(R.id.editor);
        TextViewToolbar toolbar = new TextViewToolbar();
        Options options = new Options();
        options.setPlaceholder("请输入邮件内容 ： ");
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));

        icarus = new Icarus(toolbar, options, webView);
        prepareToolbar(toolbar, icarus);
        icarus.loadCSS("file:///android_asset/editor.css");
        icarus.loadJs("file:///android_asset/test.js");
        icarus.render();

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

    private void initview() {
        email_sent = (TextView) findViewById(R.id.email_sent);
        tv_address = (TextView) findViewById(R.id.tv_address);
        back = (ImageView) findViewById(R.id.back);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        receiver_add = (ImageView) findViewById(R.id.receiver_add);
        accessory_add = (ImageView) findViewById(R.id.accessory_add);

        mail_title = (EditText) findViewById(R.id.xyjtitle_content);  //邮件标题
        mail_title.setCursorVisible(false);  //设置EditText光标隐藏

        scrollview = (ScrollView) findViewById(R.id.scrollview);

        email_sent.setVisibility(View.VISIBLE);
        tv_address.setVisibility(View.VISIBLE);
        tv_address.setText("写邮件");
        back.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        receiver_add.setOnClickListener(this);
        accessory_add.setOnClickListener(this);
        email_sent.setOnClickListener(this);
        mail_title.setOnClickListener(this);

//        look = (ImageView) findViewById(R.id.looking);
//        inlook = (ImageView) findViewById(R.id.inlooking);
//        inlookLine = findViewById(R.id.inlook_line);
//        look.setOnClickListener(this);
//        inlook.setOnClickListener(this);

        look1 = (ImageView) findViewById(R.id.looking1);
        inlook1 = (ImageView) findViewById(R.id.inlooking1);
        inlookLine1 = findViewById(R.id.inlook_line1);
        look1.setOnClickListener(this);
        inlook1.setOnClickListener(this);

        sjnameGone = (TextView) findViewById(R.id.tv_sjname);
        sjnameGone.setOnClickListener(this);

        addressRelView = (FlowTagLayout) findViewById(R.id.rv_sjname);
        rv_fjname = (RecyclerView) findViewById(R.id.rv_fjname);

        sjName = (TextView) findViewById(R.id.tv_sjname1);
        fjName = (TextView) findViewById(R.id.tv_fjname);


    }


    private static final int FILE_SELECT_CODE = 0X111;

    /**
     * 打开文件管理
     */
    private void opFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件!"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
        }

    }

    ArrayList<File> fileList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                Uri newUri;
                if (data == null) {
                    return;
                }
                    //// TODO: 2017/8/3/003        小于25
                try {
                    newUri = Uri.parse(CropUtils.getPath(this, data.getData()));
                    File file = new File(String.valueOf(newUri));
                    if (file != null) {
                        fileList.add(file);
                    }

                    if (fileList != null || fileList.size() > 0) {
                        rv_fjname.setVisibility(View.VISIBLE);
                        if (fileList.size() > 3) {
                            look1.setVisibility(View.VISIBLE);
                            inlook1.setVisibility(View.GONE);
                            inlookLine1.setVisibility(View.GONE);
                            rv_fjname.setVisibility(View.GONE);
                        }
                        fjadapter = new AddfjAdapter(fileList, this);
                        rv_fjname.setLayoutManager(new LinearLayoutManager(CreateEmailActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                        rv_fjname.setAdapter(fjadapter);
                        fjadapter.notifyDataSetChanged();
                    }
                    if (fileList.size() < 1) {
                        fjName.setVisibility(View.VISIBLE);
                    } else {
                        fjName.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "请通过其他方式选择该附件", Toast.LENGTH_SHORT).show();
                }
                break;
            case 20:
                opIdlist = data.getStringArrayListExtra("opIdlist");
                addresseNameList = data.getStringArrayListExtra("addresseNameList");
                // 改变isNotChoosed标记，表明已进入过快速回复，选择过处理人，以便再次进入初始化这些已经选择的人
                isNotChoosed = 1;
                if (addresseNameList != null || addresseNameList.size() > 0) {
                    addressRelView.setVisibility(View.VISIBLE);
                    sjName.setVisibility(View.GONE);


                    mFlowerAdapter = new TagAdapter<>(this);
                    addressRelView.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
                    addressRelView.setAdapter(mFlowerAdapter);
                    mFlowerAdapter.setPosition(-11);
                    mFlowerAdapter.notifyDataSetChanged();
                    addressRelView.setOnTagClickListener(new OnTagClickListener() {
                        @Override
                        public void onItemClick(FlowTagLayout parent, View view, int position) {
                            mail_title.setCursorVisible(false);//设置EditText光标隐藏
                            // 先隐藏键盘
                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            mFlowerAdapter.setPosition(position);
                            mFlowerAdapter.notifyDataSetChanged();
                        }
                    });
                    addressRelView.setOnTagSelectListener(new OnTagSelectListener() {
                        @Override
                        public void onItemSelect(final FlowTagLayout parent, final List<Integer> selectedList) {
                            mail_title.setCursorVisible(false);//设置EditText光标隐藏
                            // 先隐藏键盘
                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                            if (selectedList != null && selectedList.size() >= 0) {
                                StringBuilder sb = new StringBuilder();
                                for (final int i : selectedList) {
                                    sb.append(parent.getAdapter().getItem(i));
//                                    mFlowerAdapter.getItem(i);
                                }
                                Snackbar.make(parent, "收件人:" + sb.toString(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });

                    mFlowerAdapter.removeListner(new TagAdapter.RemoveListner() {
                        @Override
                        public void setRemListner(int position) {
                            // 先隐藏键盘
                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            //// TODO: 2017/7/31/03 删除的逻辑
                            addresseNameList.remove(position);
                            opIdlist.remove(position);
                            opids = "";
                            if (opIdlist != null && opIdlist.size() >= 0) {
                                for (int i = 0; i < opIdlist.size(); i++) {
                                    opids += opIdlist.get(i) + ",";
                                }
                            }

                            if (opIdlist.size() < 1) {
                                sjName.setVisibility(View.VISIBLE);
                            }else {
                                sjName.setVisibility(View.GONE);
                            }

                            if (!opids.equals("")) {
                                //去掉最后一个字符串，因为最后一个字符串是逗号
                                opids = opids.substring(0, opids.length() - 1);
                            }

                            mFlowerAdapter.onlyAddAll(addresseNameList);
                        }
                    });
                    mFlowerAdapter.setPosition(-11);
                    mFlowerAdapter.notifyDataSetChanged();
                    mFlowerAdapter.onlyAddAll(addresseNameList);
                    addressRelView.clearAllOption();
                }
                break;
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
            Toast.makeText(this, "上传附件太大了~" + fileSize, Toast.LENGTH_SHORT).show();
            return;
        }
        popupWindowUtil = new PopupWindowUtil(CreateEmailActivity.this, "提交中...");
        popupWindowUtil.show();
        sentMail();
    }


    @Override
    public void noClick() {
    }

    @Override
    public void onSingleClick() {
    }

    private void sentMail() {
        RequestBody mailTitle = RequestBody.create(MediaType.parse("multipart/form-data"), mail_title.getText().toString()); //新建邮件的标题
        RequestBody mailContent = RequestBody.create(MediaType.parse("multipart/form-data"), content);  //新建邮件的内容
        String opId = new GetAccount(this).opId();
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id

        RequestBody opIds = RequestBody.create(MediaType.parse("multipart/form-data"), opids);  //收件人的Id
        Log.e("发送邮件opid", opids);
        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            paramsMap.put("file\"; filename=\"" + file.getName(), fileBody);
        }

        RetrofitHelper.getInstance().zwyxEdit(userId, mailTitle, mailContent, opIds, paramsMap, new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {
                Toast.makeText(CreateEmailActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                Log.e("发送邮件失败", message);
                popupWindowUtil.dismiss();
            }

            @Override
            protected void parseJson(String data) {
                popupWindowUtil.dismiss();
                try {
                    JSONObject dataJson = new JSONObject(data);
                    boolean result = dataJson.getBoolean("result");
                    Log.e("返回", String.valueOf(result));
                    dataJson.getBoolean("result");
                    Toast.makeText(CreateEmailActivity.this, dataJson.getBoolean("result") ? "发送成功" : "发送失败，请重新尝试！",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    class AddfjAdapter extends RecyclerView.Adapter<AddfjAdapter.MyViewHolder1> {

        private Activity activity;
        private ArrayList<File> fileArrayList;
        private View view = null;

        public AddfjAdapter(ArrayList<File> fileList, Activity activity) {
            this.fileArrayList = fileList;
            this.activity = activity;
        }

        @Override
        public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            view = getLayoutInflater().inflate(R.layout.createfj_layout1, parent, false);
            MyViewHolder1 myViewHolder = new MyViewHolder1(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder1 holder, final int position) {
            if (fileArrayList != null && fileArrayList.size() > 0)
                holder.addtional1.setText(fileArrayList.get(position).getName());
            holder.setPosition(position);
        }

        @Override
        public int getItemCount() {
            if (fileArrayList != null && fileArrayList.size() > 0)
                return fileArrayList.size();
            return 0;
        }

        class MyViewHolder1 extends RecyclerView.ViewHolder {

            TextView addtional1;
            ImageView removeFj1;
            int position;

            public MyViewHolder1(View itemView) {

                super(itemView);
                addtional1 = (TextView) itemView.findViewById(R.id.addtional1);
                removeFj1 = (ImageView) itemView.findViewById(R.id.remove_fj1);

                removeFj1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fileList.size() >= 1) {
                            fileList.remove(position);  //将所对应的附件移除
                        }
                        notifyDataSetChanged();


                        change();

                    }
                });
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public void change() {
                if (fileList != null || fileList.size() > 0) {
                    rv_fjname.setVisibility(View.VISIBLE);

                    if (fileList.size() > 3) {  //如果收件人的集合大于三
                        look1.setVisibility(View.VISIBLE);
                        inlook1.setVisibility(View.GONE);
                        inlookLine1.setVisibility(View.GONE);
                        rv_fjname.setVisibility(View.GONE);
                    } else {
                        inlook1.setVisibility(View.GONE);
                        inlookLine1.setVisibility(View.GONE);
                    }
                }

                if (fileList.size() < 1) {
                    fjName.setVisibility(View.VISIBLE);
                } else {
                    fjName.setVisibility(View.GONE);
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                // 先隐藏键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
                break;

            case R.id.receiver_add:
                mail_title.setCursorVisible(false);  //设置EditText光标隐藏
//                icarus.getContent(new Callback() {
//                    @Override
//                    public void run(String params) {
//                        Gson gson = new Gson();
//                        Html html = gson.fromJson(params, Html.class);
//                        content = html.getContent();
//                        Log.e("发送邮件Content gotten", html.getContent());
//                    }
//                });
                intent = new Intent(this, ZwyxTreeActivity.class);
                intent.putExtra("deleteList", opIdlist);
                startActivityForResult(intent, 20);
                break;

            case R.id.accessory_add:   //添加附件
                mail_title.setCursorVisible(false);  //设置EditText光标隐藏

//                icarus.getContent(new Callback() {
//                    @Override
//                    public void run(String params) {
//                        Gson gson = new Gson();
//                        Html html = gson.fromJson(params, Html.class);
//                        content = html.getContent();
//                        Log.e("发送邮件Content gotten", html.getContent());
//                    }
//                });
                if (isGrantExternalRW(CreateEmailActivity.this)) {

                    opFileManager();
                }else {
                    Toast.makeText(CreateEmailActivity.this, "请检查是否开启读写权限", Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.looking1:
                rv_fjname.setVisibility(View.VISIBLE);
                look1.setVisibility(View.GONE);
                inlook1.setVisibility(View.VISIBLE);
                inlookLine1.setVisibility(View.VISIBLE);
                break;

            case R.id.inlooking1:
                scrollview.scrollTo(0, 0);
                rv_fjname.setVisibility(View.GONE);
                look1.setVisibility(View.VISIBLE);
                inlook1.setVisibility(View.GONE);
                inlookLine1.setVisibility(View.GONE);
                break;

            case R.id.email_sent:   //发送邮件
                mail_title.setCursorVisible(false);  //设置EditText光标隐藏

                icarus.getContent(new Callback() {
                    @Override
                    public void run(String params) {
                        Gson gson = new Gson();
                        Html html = gson.fromJson(params, Html.class);
                        content = html.getContent();
                        Log.e("发送邮件Content gotten", html.getContent());
                    }
                });

//                if (opIdlist == null) {
//                    ManagerUtils.showToast(this, "请选择收件人");
//                    return;
//                } else if (opIdlist.size() <= 0) {
//                    ManagerUtils.showToast(this, "请选择收件人");
//                    return;
//                }
                if (!(opIdlist != null && opIdlist.size() > 0)) {
//                } else {
                    ManagerUtils.showToast(this, "请选择收件人");
                    return;
                }

                if (TextUtils.isEmpty(mail_title.getText().toString()) || mail_title.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写邮件标题");
                    return;
                }

                new DialogUtil(CreateEmailActivity.this, this).showConfirm("发送邮件", "您确定要发送该邮件吗？", "确定", "取消");

                break;

            case R.id.xyjtitle_content:
                mail_title.setCursorVisible(true);  //设置EditText光标显示
                if (addresseNameList.size() >= 3) {
                    addressRelView.setVisibility(View.GONE);
                    sjnameGone.setText(addresseNameList.get(0) + "," + addresseNameList.get(1) + "...等" + addresseNameList.size() + "人");
                    sjnameGone.setVisibility(View.VISIBLE);
                    receiver_add.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_sjname:
                // 先隐藏键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                mail_title.setCursorVisible(false);  //设置EditText光标隐藏
//                if (addresseNameList.size() >= 3) {
                addressRelView.setVisibility(View.VISIBLE);
                sjnameGone.setVisibility(View.GONE);
                receiver_add.setVisibility(View.VISIBLE);
//                }
                break;
        }
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


}
