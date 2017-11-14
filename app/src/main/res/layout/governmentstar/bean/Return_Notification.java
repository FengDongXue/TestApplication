package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/3.
 */

public class Return_Notification {

//     "pageCount":1,
//     "pageNo":1,
//      "data":
//    {"notictCode":"1001N03170701/0007",
//     "notictType":"布告类通知",
//     "opCreateName":"于艳如",
//     "opId":"be1adb43-2c16-4b0b-9afe-2394f46c398b",
//     "opCreateTime":"2017/07/01 14:09",
//     "fileNum":"2"}

    private int pageCount;
    private int pageNo;
    private ArrayList<Notification_Item02> data;


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

    public ArrayList<Notification_Item02> getData() {
        return data;
    }

    public void setData(ArrayList<Notification_Item02> data) {
        this.data = data;
    }
}
