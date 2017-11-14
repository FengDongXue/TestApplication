package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/11.
 */

public class Addtion_List {

    private String path;
    private String opName;

    public Addtion_List( String opName,String path) {
        this.path = path;
        this.opName = opName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }
}
