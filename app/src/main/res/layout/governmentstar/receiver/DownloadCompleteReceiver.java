package com.lanwei.governmentstar.receiver;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.DownloadManager.Query;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.view.WpsModel;

import java.io.File;

/**
 * Created by 蓝威科技—技术部2 on 2017/5/9.
 */

@SuppressLint("NewApi")
public class DownloadCompleteReceiver extends BroadcastReceiver {
    private DownloadManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            //通过downloadId去查询下载的文件名
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Query query = new Query();
            query.setFilterById(downloadId);
            Cursor myDownload = manager.query(query);
            if (myDownload != null && myDownload.moveToFirst()) {
                int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String fileUri = myDownload.getString(fileUriIdx);
                String fileName = null;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (fileUri != null) {
                        fileName = Uri.parse(fileUri).getPath();
                        Log.e("version1", fileName + "\n\rfileurl===" + fileUri);
                        if (fileName.contains(".apk")) {
                            Log.e("version1", "APK");
                            installAPK(fileName, context);
                        } else {
                            Log.e("version1", "PFD");
                            openPDF(fileName, context);
                        }
                    }
                } else {
                    //Android 7.0以上的方式：请求获取写入权限，这一步报错
                    //过时的方式：DownloadManager.COLUMN_LOCAL_FILENAME
                    int fileNameIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                    fileName = myDownload.getString(fileNameIdx);
                    if (fileName.contains(".apk")) {
                        installAPK(fileName, context);
                    } else {
                        openPDF(fileName, context);
                    }
                }

            }
        }

    }

    //安装APK
    private void installAPK(String filePath, Context context) {
        Intent intent = new Intent();
        File file = new File(filePath);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, "com.lanwei.governmentstar", file);

            intent.addCategory("android.intent.category.DEFAULT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            context.startActivity(intent);

        } else {
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);

        }

//        intent.setAction("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//广播里面操作需要加上这句，存在于一个独立的栈里
//        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
//        context.startActivity(intent);

    }

    public void openPDF(String filePath, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        File file = new File(filePath);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, "com.lanwei.governmentstar", file);

//            Bundle bundle = new Bundle();
//            bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
//            bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
//            bundle.putString(WpsModel.THIRD_PACKAGE, "com.lanwei.governmentstar"); // 第三方应用的包名，用于对改应用合法性的验证
//            bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
//
//            intent.putExtras(bundle);

            String type = getMIMEType(file);
            //设置intent的data和Type属性。
            intent.setDataAndType(contentUri, type);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "sorry附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "openPDF: wrong", Toast.LENGTH_SHORT).show();
            }

        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
//            bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
//            bundle.putString(WpsModel.THIRD_PACKAGE, "com.lanwei.governmentstar"); // 第三方应用的包名，用于对改应用合法性的验证
//            bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
//
//            intent.putExtras(bundle);
            String type = getMIMEType(file);
            //设置intent的data和Type属性。
            intent.setDataAndType(Uri.fromFile(file), type);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "sorry附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
            }
        }


//        Intent intent2 = new Intent("android.intent.action.VIEW");
//
//        Bundle bundle = new Bundle();
//        bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
//        bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
//        bundle.putString(WpsModel.THIRD_PACKAGE, "com.lanwei.governmentstar"); // 第三方应用的包名，用于对改应用合法性的验证
//        bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
//        intent2.putExtras(bundle);
//        intent2.addCategory("android.intent.category.DEFAULT");
//        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(filePath));
//        intent2.setData(uri);
//
//        try {
//            context.startActivity(intent2);
////            startActivity(intent2);
////            Toast.makeText(DocumentBaseCActivity.this, "正在打开：》》", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
////            Toast.makeText(DocumentBaseCActivity.this, "你未安装PDF软件", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
    }


    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {

            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };


}

