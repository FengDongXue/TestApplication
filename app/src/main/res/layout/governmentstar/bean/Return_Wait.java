package com.lanwei.governmentstar.bean;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/12.
 */

public class Return_Wait {

    //    {
//        "pageCount": 1,
//            "pageNo": 1,
//            "data": [
//        {
//            "opId": "7fa9f05d-6664-4b90-9975-aea6622eaf71",
//                "docTitle": "关于×××××××××××的决议",
//                "docClassify": "\r\n财政工作",
//                "docFileNum": "无附件",
//                "docTime": "2017/04/12 15:13:56",
//                "docCode": "1000D010020170412/0001",
//                "docName": "张浩宁",
//                "docCompany": hardwork,
//                "docState": "gwnz",
//                "opState": "1"
//        },

    private int pageCount;
    private int pageNo;
    private String otherLogin="0";
    private ArrayList<Wait_Item> data;

    public String getOtherLogin() {
        return otherLogin;
    }

    public void setOtherLogin(String otherLogin) {
        this.otherLogin = otherLogin;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public ArrayList<Wait_Item> getData() {
        return data;
    }

    public void setData(ArrayList<Wait_Item> data) {
        this.data = data;
    }
}
