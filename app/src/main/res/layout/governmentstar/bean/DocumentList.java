package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/3.
 */

public class DocumentList {
    private  int pageCount;
    private int pageNo;
    private List<Document> data;

    public void setData(List<Document> data) {
        this.data = data;
    }
    public List<Document> getData() {
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
