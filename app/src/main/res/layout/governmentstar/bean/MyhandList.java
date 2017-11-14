package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/28.
 */

public class MyhandList {

    private  int pageCount;
    private int pageNo;
    private List<Myhand> data;

    public void setData(List<Myhand> data) {
        this.data = data;
    }
    public List<Myhand> getData() {
        return data;
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
}
