package com.lanwei.governmentstar.utils;

import android.content.Context;

/**
 * Created by 蓝威科技-技术开发1 on 2017/5/10.
 */

public class ProviderUtil {

    public static String getFileProviderName(Context context){
        return context.getPackageName()+".provider";
    }
}
