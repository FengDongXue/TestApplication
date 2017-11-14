package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * Created by xong2 on 2017/4/2.
 */

public class Myhand implements Serializable {
    /**
     * opId : 065b46ce-7f02-4814-8e34-d30fcc575dbf
     * docTitle : 传阅没有办理人
     * opCreateTime : 2017/03/27
     * opState : 7
     * opCreateName : 张浩宁
     * docStatus : hardwork
     */

    private String opId;
    private String docTitle;
    private String opCreateTime;
    private String opState;
    private String opCreateName;
    private String docStatus;
    private String docMatter;

    public String getDocMatter() {
        return docMatter;
    }

    public void setDocMatter(String docMatter) {
        this.docMatter = docMatter;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(String opCreateTime) {
        this.opCreateTime = opCreateTime;
    }

    public String getOpState() {
        return opState;
    }

    public void setOpState(String opState) {
        this.opState = opState;
    }

    public String getOpCreateName() {
        return opCreateName;
    }

    public void setOpCreateName(String opCreateName) {
        this.opCreateName = opCreateName;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }
}
