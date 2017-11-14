package com.lanwei.governmentstar.activity.zwyx;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.MailDetails;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.FileUtils;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.Dialog02;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lanwei.com.governmentstar.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2017/5/26/026.
 */

public class CheckMail extends AutoLayoutActivity implements View.OnClickListener, DialogUtil.OnClickListenner {

    private CircleImageView iv_contacts;
    private ImageView back;
    private TextView title;
    private ImageView del;
    private ImageView zf;
    private TextView mailInbox;
    private TextView mail_send;
    private TextView mailSj;
    private TextView mail_title;
    private WebView mailContent;
    private RelativeLayout mail_fj;
    private String mailOpId;
    private int position_download;
    private String path_addtion;
    private Adapter_Addtion adapter;
    private RecyclerView rv_fjname;
    private String filePreview;
    private DownloadManager dm;
    private DownloadReceiver downloadReceiver;
    private MailDetails mailDetails;
    private ImageView look;
    private ImageView inlook;
    private View inlookLine;
    private ScrollView scrollview;
    private int type = -1;
    private EditText edit_hf;
    private TextView hf;
    private String opid;
    private RelativeLayout rv_hfzf;
    private View hfLine;
    private PopupWindowUtil popupWindowUtil;
    private SharedPreferences amount_ShortcutBadger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.checkmail);

        rv_fjname = (RecyclerView) findViewById(R.id.rv_fjname);
        iv_contacts = (CircleImageView) findViewById(R.id.iv_contacts);
        title = (TextView) findViewById(R.id.tv_address);
        look = (ImageView) findViewById(R.id.look);
        inlook = (ImageView) findViewById(R.id.inlook);
        edit_hf = (EditText) findViewById(R.id.hfqb);
        hf = (TextView) findViewById(R.id.hf);
        del = (ImageView) findViewById(R.id.zwyx_sc);
        zf = (ImageView) findViewById(R.id.zwyx_zf);
        back = (ImageView) findViewById(R.id.back);
        hf.setOnClickListener(this);
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        inlookLine = findViewById(R.id.inlook_line);
        return_amount();
        rv_hfzf = (RelativeLayout) findViewById(R.id.zwyx_hf);
        hfLine = findViewById(R.id.hf_line);

        del.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        title.setText("查看邮件");
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        del.setOnClickListener(this);
        zf.setOnClickListener(this);
        look.setOnClickListener(this);
        inlook.setOnClickListener(this);
        mail_title = (TextView) findViewById(R.id.mail_title);
        mailInbox = (TextView) findViewById(R.id.mail_inbox);
        mail_send = (TextView) findViewById(R.id.mail_send);
        mailSj = (TextView) findViewById(R.id.mail_sj);
        mailContent = (WebView) findViewById(R.id.mail_content);
        mail_fj = (RelativeLayout) findViewById(R.id.mail_fj);

        mailContent.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        if (type == 4) {
            del.setVisibility(View.GONE);
        } else {
            del.setVisibility(View.VISIBLE);
        }

        if (type == 0) {
            rv_hfzf.setVisibility(View.VISIBLE);
            hfLine.setVisibility(View.VISIBLE);
            hf.setVisibility(View.VISIBLE);
        } else {
            hf.setVisibility(View.GONE);
            rv_hfzf.setVisibility(View.GONE);
            hfLine.setVisibility(View.GONE);
        }

        getData();
    }

    private void getData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        String userId = new GetAccount(this).opId(); //当前登录者ID
        Log.e("111",userId);
        Intent intent = getIntent();
        //查看邮件的数据ID
        opid = intent.getStringExtra("opid");
        Log.e("11",opid);

        Call<JsonObject> call = api.zwyxDetail(userId, opid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                popupWindowUtil.dismiss();
                Log.e("查看邮件", response.body().toString());
                Gson gson = new Gson();
                mailDetails = gson.fromJson(response.body().toString(), MailDetails.class);
                mailOpId = mailDetails.getData().getOpId();


                if (mailDetails.getData().getFileList() == null | mailDetails.getData().getFileList().size() <= 0) {
                    mail_fj.setVisibility(View.GONE);
                } else {
                    mail_fj.setVisibility(View.VISIBLE);
                    rv_fjname.setVisibility(View.VISIBLE);
                    if (mailDetails.getData().getFileList().size() > 3) {
                        look.setVisibility(View.VISIBLE);
                        inlook.setVisibility(View.GONE);
                        inlookLine.setVisibility(View.GONE);
                        rv_fjname.setVisibility(View.GONE);
                    }

                    adapter = new Adapter_Addtion(mailDetails.getData().getFileList());
                    rv_fjname.setLayoutManager(new LinearLayoutManager(CheckMail.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                    rv_fjname.setAdapter(adapter);
                }

                mailSj.setText(mailDetails.getData().getMailDate()); //时间
                mailInbox.setText(mailDetails.getData().getMailAttn()); //收件人
                mail_send.setText(mailDetails.getData().getMailSend()); //发件人
                mail_title.setText(mailDetails.getData().getMailTitle()); //邮件标题
                mailContent.loadUrl(mailDetails.getData().getMailUrl()); //邮件内容

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


    void return_amount() {
        String defString3 = PreferencesManager.getInstance(CheckMail.this, "accountBean").get("jsonStr");
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
                    ShortcutBadger.applyCount(CheckMail.this, response.body().getData().getManage_num());
                    amount_ShortcutBadger.edit().putInt("number",response.body().getData().getManage_num()).commit();

                }
            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {
                Toast.makeText(CheckMail.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.zwyx_sc:
//                finish();
                new DialogUtil(CheckMail.this, this).showConfirm("删除邮件", "您确定要删除该邮件吗？", "确定", "取消");
                break;
            case R.id.zwyx_zf:
//                finish();
                break;
            case R.id.look:
                rv_fjname.setVisibility(View.VISIBLE);
                look.setVisibility(View.GONE);
                inlook.setVisibility(View.VISIBLE);
                inlookLine.setVisibility(View.VISIBLE);
                break;

            case R.id.inlook:
                scrollview.scrollTo(0, 0);
                rv_fjname.setVisibility(View.GONE);
                look.setVisibility(View.VISIBLE);
                inlook.setVisibility(View.GONE);
                inlookLine.setVisibility(View.GONE);
                break;
            case R.id.hf:
                String text = edit_hf.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                jumpDialog(text);
                break;
        }
    }

    private void jumpDialog(final String text) {
        final Dialog02 dialog02 = new Dialog02(this);
        dialog02.setContent("您确定要回复该邮件吗？", Color.parseColor("#4f4f4f"));
        dialog02.setTitle("回复邮件", Color.parseColor("#5184c3"));
        dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
        dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
        dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                popupWindowUtil = new PopupWindowUtil(CheckMail.this, "提交中...");
                popupWindowUtil.show();
                dialog02.dismiss();
                hfData(text);
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
     * 回复邮件
     *
     * @param text
     */
    private void hfData(String text) {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        String userId = new GetAccount(this).opId(); //当前登录者ID
        Call<JsonObject> call = api.zwyxReply(userId, opid, text);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("回复邮件", response.body().toString());
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        boolean data = jsonObject.getBoolean("data");
                        if (data) {

                            new DialogUtil(CheckMail.this, CheckMail.this).showAlert("回复邮件", "您已成功回复该邮件", "知道了");
                            return;
//                            Toast.makeText(CheckMail.this, "已发送", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CheckMail.this, "回复失败，请检查网络状态后重新回复", Toast.LENGTH_SHORT).show();
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
     * 删除邮件
     */
    private void deleteData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        String userId = new GetAccount(this).opId(); //当前登录者ID
        Intent intent = getIntent();
        String opid = intent.getStringExtra("opid"); //查看邮件的数据ID
        Call<JsonObject> call = null;

        if (type == -1)
            return;
        // 0 收件箱  // 1 已发送  //  2 临时邮件  // 3 已删除  // 4 收藏的邮件
        switch (type) {
            case 0:
                call = api.zwyxListDel(userId, opid);
                break;
            case 1:
                call = api.zwyxSendDel(opid);
                break;
            case 2:
                call = api.zwyxTempSaveDel(opid);
                break;
            case 3:
                call = api.zwyxDel(opid);
                break;
        }

        if (call == null)
            return;


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
                        Intent intent = new Intent();
                        if (data) {
                            if (type == 2) {
                                intent.putExtra("isDelete", true);
                            }
                            intent.putExtra("deleteOpid", mailOpId);
                            setResult(21, intent);
                            new DialogUtil(CheckMail.this, CheckMail.this).showAlert("删除邮件", "您已将该邮件删除成功", "知道了");
                            return;
                        } else {
                            if (type == 2) {
                                intent.putExtra("isDelete", false);
                            }

                            Toast.makeText(CheckMail.this, "删除失败！", Toast.LENGTH_SHORT).show();
                        }

                    }
//                    Toast.makeText(CheckMail.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(CheckMail.this, "提交中...");
        popupWindowUtil.show();
        deleteData();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        this.finish();
    }

    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private List<MailDetails.DataBean.FileListBean> fileList;
        private View view = null;

        public Adapter_Addtion(List<MailDetails.DataBean.FileListBean> fileList) {

            this.fileList = fileList;

            Log.e("邮箱附件", this.fileList.toString());
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.itemfj_layout, parent, false);

            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


//            holder.addtion.setText("附件" + (position + 1) + " : ");


            holder.addtional.setText(fileList.get(position).getOpName());
            Log.e("邮箱附件", fileList.get(position).getOpName());
//            if (!TextUtils.isEmpty(fileList.get(position).getOpName())) {
//                holder.addtional.setText(fileList.get(position).getOpName());
//
//            }

//            if (position == fileList.size() - 1) {
//                holder.inlookLine.setVisibility(View.GONE);
//            } else {
//                holder.inlookLine.setVisibility(View.VISIBLE);
//            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    position_download = position;

                    final String path = fileList.get(position_download).getPath();
                    path_addtion = fileList.get(position_download).getOpName();
                    Log.e("附件", path_addtion);
                    filePreview = fileList.get(position_download).getFilePreview();
                    Log.e("附件", filePreview);

                    if (path.contains(".jpg")
                            || path.contains(".png")
                            || path.contains(".doc")
                            || path.contains(".gif")
                            || path.contains(".xls")
                            || path.contains(".xlsx")
                            || path.contains(".docx")
                            || path.contains(".pdf")) {
                        Intent intent = new Intent(CheckMail.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", filePreview);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    } else {
                        if (FileUtils.checkFileExists(path_addtion)) {
                            File externalStorageDirectory = Environment.getExternalStorageDirectory();
                            FileUtils.openFile(externalStorageDirectory.getPath() + "/Download/" + path_addtion, CheckMail.this);
                        } else {
                            new DialogUtil(CheckMail.this, new Summit(mailDetails.getData().getFileList())).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
                        }
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return fileList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            //            TextView addtion;
            TextView addtional;
//            View inlookLine;

            public MyViewHolder(View itemView) {

                super(itemView);
//                addtion = (TextView) view.findViewById(R.id.addtion);
                addtional = (TextView) view.findViewById(R.id.addtional);
//                inlookLine = view.findViewById(R.id.inlookLine);
            }
        }
    }

    class Summit implements DialogUtil.OnClickListenner {

        private List<MailDetails.DataBean.FileListBean> data;

        public Summit(List<MailDetails.DataBean.FileListBean> fileList) {
            this.data = fileList;
        }

        @Override
        public void yesClick() {
            if (ActivityCompat.checkSelfPermission(CheckMail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(CheckMail.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 30);
                return;
            }
            try {
                downloading();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CheckMail.this, "如果您拒绝使用读写权限，您将无法进行下载更新。", Toast.LENGTH_LONG).show();
            }
            downloading();

        }


        private void downloading() {
            final String path = data.get(position_download).getPath();
            path_addtion = data.get(position_download).getOpName();

            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);//获取系统下载的service
            DownloadUtil.startDownload(dm, CheckMail.this, path, path_addtion, "正在下载...");

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

            finish();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 30: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。
                    Toast.makeText(CheckMail.this, "如果您拒绝使用读写权限，您将无法进行下载更新。", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
        }
    }

    public class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // @TODO SOMETHING
                Toast.makeText(CheckMail.this, "下载完成", Toast.LENGTH_SHORT).show();
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.e("放松放松放松", "" + downId);
            }
        }
    }

}
