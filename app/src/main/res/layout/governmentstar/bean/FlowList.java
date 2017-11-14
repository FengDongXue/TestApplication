package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by xong2 on 2017/4/3.
 */

public class FlowList {

    private List<Document> data;
    private boolean isCb;

    public List<Document> getData() {
        return data;
    }

    public void setData(List<Document> data) {
        this.data = data;
    }

    public boolean isCb() {
        return isCb;
    }

    public void setCb(boolean cb) {
        isCb = cb;
    }

}
