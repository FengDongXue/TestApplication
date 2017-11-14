package com.lanwei.governmentstar.activity.gwnz;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Addtion_List;
import com.lanwei.governmentstar.bean.DocumentDetails;
import com.lanwei.governmentstar.bean.Return_Finish;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/18.
 */

public class DocumentDetailsActivity extends BaseActivity implements View.OnClickListener, MyScrollView_Focus.MyScrollListener {
    private static final String TAG = DocumentDetailsActivity.class.getSimpleName();
    private ImageView back;
    private TextView title;
    private TextView see;
    private TextView name;
    private TextView theme;
    private TextView date;
    private TextView docType;
    private Return_Finish.Data data_return;
    private DownloadReceiver downloadReceiver;
    private DownloadManager dm;
    private RecyclerView lv;
    private WebView webView;
    private LinearLayout condition;
    private LinearLayout addtionLayout;
    private int position_addtion;
    private TextView dept;
    private ArrayList<Addtion_List> files;
    private DocumentDetails documentDetails;
    private TextView docTitle;
    private String path_addtion;
    private String filePreview;
    private MyScrollView_Focus document_scrollView;
    private FloatingActionButton enshrink;
    private FloatingActionButton enlarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_documentdetails);
        LogUtils.d(TAG, "onCreate()");
        init();
        getData();
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

    private void init() {
        // TODO Auto-generated method stub
        back = (ImageView) findViewById(R.id.back);     //返回
        title = (TextView) findViewById(R.id.title);  //标题栏
        docTitle = (TextView) findViewById(R.id.doc_title);  //起草人
        name = (TextView) findViewById(R.id.name);  //起草人
        theme = (TextView) findViewById(R.id.theme);    //主题
        date = (TextView) findViewById(R.id.date);      //日期
        docType = (TextView) findViewById(R.id.docType);  //文件类型
        lv = (RecyclerView) findViewById(R.id.lv);  //文件列表
        dept = (TextView) findViewById(R.id.hqdpt);
        see = (TextView) findViewById(R.id.see);        //查看流程
        webView = (WebView) findViewById(R.id.web);    //webview
        addtionLayout = (LinearLayout) findViewById(R.id.addtion_layout);  //整个文件列表界面
        document_scrollView = (MyScrollView_Focus) findViewById(R.id.document_scrollView);  //整个文件列表界面
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);  //整个文件列表界面
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);  //整个文件列表界面
        enlarge.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        enshrink.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        document_scrollView.setMyScrollListener(this);

        // 为RecyclerView设置默认动画和线性布局管理器
        lv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        lv.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        back.setOnClickListener(this);
        see.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        enlarge.setOnClickListener(this);

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

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setTextZoom(100);

        webView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript脚本
//        webView.setOnTouchListener(new View.OnTouchListener() {
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

    }

    /**
     * 获取数据
     */
    private void getData() {
        String opId = getIntent().getStringExtra("opId");
        RetrofitHelper.getInstance().getDocumentDetaidsInfo(opId, new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                Log.e("documentData", data);
                if (data != null) {
                    Gson gson = new Gson();
                    documentDetails = gson.fromJson(data, DocumentDetails.class);
                    files = new ArrayList<>();   //创建一个存放文件名字和路径的集合

                    showData(documentDetails);
                    if (documentDetails.getFileList().size() != 0) {
                        addtionLayout.setVisibility(View.VISIBLE);
                        DetailsAdapter detailsAdapter = new DetailsAdapter(documentDetails);
                        lv.setAdapter(detailsAdapter);
                        detailsAdapter.notifyDataSetChanged();
                    } else {
                        addtionLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 展示数据
     *
     * @param data
     */
    private void showData(DocumentDetails data) {
//        title.setText(data.getDocTheme());   //// TODO: 2017/4/19 在这里设置标题栏
        String doctitle = data.getDocTitle();
        Log.e("aa", data.getDocTitle());
        if (doctitle.contains("\r\n")) {
            String s = doctitle.replace("\n", "");
            doctitle = s;
        }
        docTitle.setText(doctitle);//公文标题

        name.setText(data.getOpCreateName());  //名
        docType.setText(data.getDocType());  //公文类型

        String docTheme = data.getDocTheme();
        Log.e("aa", data.getDocTheme());
        if (docTheme.contains("\r\n")) {
            String s = docTheme.replace("\n", "");
            docTheme = s;
        }
        theme.setText(docTheme);      //主题

        date.setText(data.getOpCreateTime());  //时间
        dept.setText(data.getIsHq());  //文件部门   会签文件
        if (data.getDocUrl() != null && !data.getDocUrl().equals("")) {
            webView.loadUrl(data.getDocUrl());  //下载Url
        } else {
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.enshrink:
                webView.loadUrl("javascript:narrow()");
                break;

            case R.id.enlarge:
                webView.loadUrl("javascript:amplify()");
                break;


            case R.id.see:  //点击查看流程
                Intent intent = new Intent(this, Process2_Activity.class);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                startActivity(intent);
                break;
        }
    }

    class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

        private DocumentDetails documentDetails;
        private View view = null;

        public DetailsAdapter(DocumentDetails documentDetails) {
            this.documentDetails = documentDetails;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            //// TODO: 2017/4/19  有附件的话，不用修改
            holder.addtion.setText("附件" + (position + 1) + " : ");
//            holder.addtional.setText(documentDetails.getFileList(position_addtion).get(position_addtion).getOpName());

            //如果附件不为空的话
            if (!TextUtils.isEmpty(documentDetails.getFileList().get(position).getOpName()) || documentDetails.getFileList().size() > 0) {
                holder.addtional.setText(documentDetails.getFileList().get(position).getOpName());
            }

            if (documentDetails.getFileList().size() - 1 == position) {

                holder.line.setVisibility(View.GONE);

            } else {
                holder.line.setVisibility(View.VISIBLE);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面
                    position_addtion = position;
                    final String path = documentDetails.getFileList().get(position_addtion).getPath();
                    path_addtion = documentDetails.getFileList().get(position_addtion).getOpName();
                    filePreview = documentDetails.getFileList().get(position_addtion).getFilePreview();

                    if (path.contains(".jpg")
                            || path.contains(".png")
                            || path.contains(".doc")
                            || path.contains(".gif")
                            || path.contains(".xls")
                            || path.contains(".xlsx")
                            || path.contains(".docx")
                            || path.contains(".pdf")) {
                        Intent intent = new Intent(DocumentDetailsActivity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", filePreview);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    } else {
                        new DialogUtil(DocumentDetailsActivity.this, new Summit()).showConfirm("确认下载", "确认要下载到本地嘛？", "确定", "取消");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
//            return data_list.size();
            return documentDetails.getFileList().size();
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


    public class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // @TODO SOMETHING
                Toast.makeText(DocumentDetailsActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                Log.e("放松放松放松", "" + downId);
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

    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {
            final String path = documentDetails.getFileList().get(position_addtion).getPath();
            path_addtion = documentDetails.getFileList().get(position_addtion).getOpName();
            filePreview = documentDetails.getFileList().get(position_addtion).getFilePreview();

            DownloadUtil.startDownload(dm, DocumentDetailsActivity.this, path, path_addtion, "正在下载...");

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

        }
    }
}
