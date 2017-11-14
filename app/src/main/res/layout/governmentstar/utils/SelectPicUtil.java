package com.lanwei.governmentstar.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by Administrator on 2017/4/13.
 */

public class SelectPicUtil {
    /**临时存放图片的地址，如需修改，请记得创建该路径下的文件夹*/
    private static final String appDirectoryPath = File.separator+"mnt"+File.separator+"sdcard"+File.separator+"HousingEstate";
    private static Uri lsimg = null;
    public static final int GET_BY_ALBUM = 801;//如果有冲突，记得修改
    public static final int GET_BY_CAMERA = 802;//如果有冲突，记得修改
    public static final int CROP = 803;//如果有冲突，记得修改
    public static final int BITMAP = 804;//如果有冲突，记得修改

    /**从相册获取图片*/
    public static void getByAlbum(Activity act){
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        act.startActivityForResult(getAlbum, BITMAP);
    }

    /**通过拍照获取图片*/
    public static void getByCamera(Activity act){
        if (SDCardUtil.isSDCardEnable()) {
            checkPremission(act);
        } else {
           Toast.makeText(act, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
        }
    }


    private static void checkPremission(Activity act) {
        final String permission = Manifest.permission.CAMERA;  //相机权限
        final String permission1 = Manifest.permission.WRITE_EXTERNAL_STORAGE; //写入数据权限
        if (ContextCompat.checkSelfPermission(act, permission) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(act, permission1) != PackageManager.PERMISSION_GRANTED) {  //先判断是否被赋予权限，没有则申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, permission)) {  //给出权限申请说明
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, GET_BY_CAMERA);
            } else { //直接申请权限
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.CAMERA}, GET_BY_CAMERA); //申请权限，可同时申请多个权限，并根据用户是否赋予权限进行判断
            }
        } else {  //赋予过权限，则直接调用相机拍照
            openCamera(act);
        }
    }

    public static void openCamera(Activity act) {  //调用相机拍照
        Intent intent = new Intent();
        File file = new FileStorage().createIconFile(); //工具类
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            lsimg = Uri.parse(FileProvider.getUriForFile(act, "com.lanwei.governmentstar", file).toString());//通过FileProvider创建一个content类型的Uri，进行封装
        }

        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lsimg);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        act.startActivityForResult(intent, GET_BY_CAMERA);
    }


    /**
     * 处理获取的图片，注意判断空指针
     */
    public static Bitmap onActivityResult(Activity act, int requestCode, int resultCode, Intent data){
        Bitmap bm = null;
        if (resultCode == Activity.RESULT_OK) {
            //Uri uri;

            switch (requestCode) {
                case GET_BY_ALBUM:
                    //uri = data.getData();
                    //act.startActivityForResult(crop(data.getData(), w, h, aspectX, aspectY),CROP);
                    lsimg = data.getData();
                    act.startActivityForResult(startPhotoZoom(data.getData()),CROP);
                    break;
                case GET_BY_CAMERA:
                    //uri = Uri.parse(lsimg);
                    //act.startActivityForResult(crop(uri, w, h, aspectX, aspectY),CROP);
                    act.startActivityForResult(startPhotoZoom(lsimg),CROP);
                    break;
                case CROP:
                   bm = dealCrop(act, data);
                    break;
                case BITMAP:
                    lsimg = data.getData();
                    act.startActivityForResult(data,CROP);
                    break;
            }
        }
        return bm;
    }

    /**默认裁剪输出480*480，比例1:1*/
    public static Intent crop(Uri uri){
        return crop(uri,480,480,1,1);
    }
    /**
     * 裁剪，例如：输出100*100大小的图片，宽高比例是1:1
     * @param w 输出宽
     * @param h 输出高
     * @param aspectX 宽比例
     * @param aspectY 高比例
     */
    public static Intent crop(Uri uri,int w,int h,int aspectX,int aspectY){
        if (w==0&&h==0) {
            w=h=95;
        }
        if (aspectX==0&&aspectY==0) {
            aspectX=aspectY=1;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        // 照片URL地址
        intent.setDataAndType(uri, "image*//*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", w);
        intent.putExtra("outputY", h);
        // 输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lsimg);
        // 输出格式
        intent.putExtra("outputFormat", "JPEG");
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        return intent;
    }


    public static Intent startPhotoZoom(Uri uri) {
        int  dp = 95;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);//输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", dp);//输出X方向的像素
        intent.putExtra("outputY", dp);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);//设置为不返回数据

        return intent;
    }

    /**处理裁剪，获取裁剪后的图片*/
    public static Bitmap dealCrop(Context context, Intent intent){
        // 裁剪返回
        //Log.d("TAG", lsimg + "<<<<<=====");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), lsimg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "获取图片有误", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "获取图片有误", Toast.LENGTH_LONG).show();
        }
        return bitmap;
    }

}
