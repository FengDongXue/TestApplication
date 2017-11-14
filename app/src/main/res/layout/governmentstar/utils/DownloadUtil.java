package com.lanwei.governmentstar.utils;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/10.
 */

public class DownloadUtil {

    static public long startDownload(DownloadManager dm, Context context, String uri, String title, String description) {

        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(uri));

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |DownloadManager.Request.NETWORK_WIFI);
        //req.setAllowedOverRoaming(false);

        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //设置文件的保存的位置[三种方式]
        //第一种
        //storage/emulated/0/Android/data/your-package/files/Download/update.apk
//        try {
//            req.setDestinationInExternalFilesDir(context, null ,title);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //第二种
        //file:///storage/emulated/0/Download/update.apk
        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
        //第三种 自定义文件路径
        //req.setDestinationUri()

        // 设置一些基本显示信息
        req.setTitle(title);
        req.setDescription(description);
        req.setMimeType("application/com.trinea.download.file");

        //加入下载队列
        return dm.enqueue(req);

        //long downloadId = dm.enqueue(req);
        //Log.d("DownloadManager", downloadId + "");
        //dm.openDownloadedFile()
    }

    static public long startDownload(DownloadManager dm, Context context, String uri, String title,String app_name, String description) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(uri));

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |DownloadManager.Request.NETWORK_WIFI);
        //req.setAllowedOverRoaming(false);

        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //设置文件的保存的位置[三种方式]
        //第一种
        //storage/emulated/0/Android/data/your-package/files/Download/update.apk
        try {
            req.setDestinationInExternalFilesDir(context, null ,app_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //第二种
        //file:///storage/emulated/0/Download/update.apk
//        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
        //第三种 自定义文件路径
        //req.setDestinationUri()

        // 设置一些基本显示信息
        req.setTitle(title);
        req.setDescription(description);
        req.setMimeType("application/vnd.android.package-archive");

        //加入下载队列
        return dm.enqueue(req);

        //long downloadId = dm.enqueue(req);
        //Log.d("DownloadManager", downloadId + "");
        //dm.openDownloadedFile()
    }

}
