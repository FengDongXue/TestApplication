package com.lanwei.governmentstar.activity.spsq.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.SpsqOutApplyDetails;
import com.lanwei.governmentstar.bean.SpsqSealApplyDetails;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.FileUtils;

import java.io.File;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Administrator on 2017/9/7/007.
 */

public class FilesUsesealAdapter extends RecyclerView.Adapter<FilesUsesealAdapter.MyViewHolder> {

    private List<SpsqSealApplyDetails.FilesBean> fileList;
    private View view = null;
    private Activity activity;
    private int position_download;
    private String path_addtion;
    private DownloadManager dm;
    private DownloadReceiver downloadReceiver;
    private String filePreview;

    public FilesUsesealAdapter(Activity activity, List<SpsqSealApplyDetails.FilesBean> fileList) {
        this.activity = activity;
        this.fileList = fileList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = activity.getLayoutInflater().inflate(R.layout.spsqitemfj_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.addtional.setText(fileList.get(position).getFileName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 跳去附件详情界面

                position_download = position;

                final String path = fileList.get(position_download).getFilePath();
                path_addtion = fileList.get(position_download).getFileName();
                Log.e("附件", path_addtion);
                filePreview = fileList.get(position_download).getFilePreView();
//                Log.e("附件", filePreview);

                if (path.contains(".jpg")
                        || path.contains(".png")
                        || path.contains(".doc")
                        || path.contains(".gif")
                        || path.contains(".xls")
                        || path.contains(".xlsx")
                        || path.contains(".docx")
                        || path.contains(".pdf")) {
                    Intent intent = new Intent(activity, DetailsFJActivity.class);
                    intent.putExtra("filePreview", filePreview);
                    intent.putExtra("type", "1");
                    activity.startActivity(intent);
                } else {
                    if (FileUtils.checkFileExists(path_addtion)) {
                        File externalStorageDirectory = Environment.getExternalStorageDirectory();
                        FileUtils.openFile(externalStorageDirectory.getPath() + "/Download/" + path_addtion, activity);
                    } else {
                        new DialogUtil(activity,  new FilesUsesealAdapter.Summit(fileList)).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
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
        TextView addtional;

        public MyViewHolder(View itemView) {
            super(itemView);
            addtional = (TextView) view.findViewById(R.id.addtional);
        }
    }

    class Summit implements DialogUtil.OnClickListenner {

        private List<SpsqSealApplyDetails.FilesBean> data;

        public Summit(List<SpsqSealApplyDetails.FilesBean> fileList) {
            this.data = fileList;
        }

        @Override
        public void yesClick() {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 30);
                return;
            }
            try {
                downloading();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "如果您拒绝使用读写权限，您将无法进行下载更新。", Toast.LENGTH_LONG).show();
            }
            downloading();

        }



        private void downloading() {
            final String path = data.get(position_download).getFilePath();
            path_addtion = data.get(position_download).getFileName();

            dm = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);//获取系统下载的service
            DownloadUtil.startDownload(dm, activity, path, path_addtion, "正在下载...");

            downloadReceiver = new DownloadReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            activity.registerReceiver(downloadReceiver, intentFilter);
        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {

            activity.finish();

        }
    }

    public class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // @TODO SOMETHING
                Toast.makeText(activity, "下载完成", Toast.LENGTH_SHORT).show();
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.e("放松放松放松", "" + downId);
            }

        }
    }
}
