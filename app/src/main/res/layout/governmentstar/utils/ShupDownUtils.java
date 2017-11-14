package com.lanwei.governmentstar.utils;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/28.
 */

public class ShupDownUtils {

    static ArrayList<AppCompatActivity> list=null;


    static void addActivity(AppCompatActivity activity){
        list.add(activity);
    }

    static void removeActivity(AppCompatActivity activity){
        list.remove(activity);
    }

    static void finishAll(){

        for(int i=0;i<list.size();i++){
            list.get(i).finish();
        }
    }

}
