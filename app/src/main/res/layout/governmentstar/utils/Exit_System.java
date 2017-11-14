package com.lanwei.governmentstar.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/11.
 */

public class Exit_System {

    static public ArrayList<Activity> list_system =new ArrayList<>();

    static public void addActivity(Activity activity){
        list_system.add(activity);
    }

    static public void removeActivity(Activity activity){
        list_system.remove(activity);
    }
}
