package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/28.
 */

public class List_Person {

    ArrayList<String> childList;
    boolean is_choosed = false;

    public ArrayList<String> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<String> childList) {
        this.childList = childList;
    }

    public boolean is_choosed() {
        return is_choosed;
    }

    public void setIs_choosed(boolean is_choosed) {
        this.is_choosed = is_choosed;
    }
}
