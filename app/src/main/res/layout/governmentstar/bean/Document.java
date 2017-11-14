package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/3.
 */

public class Document implements Serializable {

    /**
     * opId : 5352554c-bad2-482e-82a2-f14b9df01b3a
     * opState : 2
     * docTitle : 关于×××××××××××的决议
     * opCreateName : 张浩宁
     * docStatus : 1
     */

    private String opId;
    private String opState;
    private String docTitle;
    private String opCreateName;
    private String opCreateTime;
    private String docStatus;
    private String opType;
    private String orderBy;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getOpState() {
        return opState;
    }

    public void setOpState(String opState) {
        this.opState = opState;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getOpCreateName() {
        return opCreateName;
    }

    public void setOpCreateName(String opCreateName) {
        this.opCreateName = opCreateName;
    }

    public String getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(String opCreateTime) {
        this.opCreateTime = opCreateTime;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }
}
