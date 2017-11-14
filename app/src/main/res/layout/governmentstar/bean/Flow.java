package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * Created by xong2 on 2017/4/3.
 */

public class Flow  implements Serializable {
    /**
     * flowTitle : 1.公文录入。已完成
     * flowContent : 张浩宁已录入公文
     * flowImgUrl : http://121.42.29.226:80/resources/images/head/18537d2f-5466-4279-b4be-ab66e2c96d7b_20170205024056495.jpg
     * flowTime : hardwork
     * flowStatus : 1
     * opCreateTime : 2017-03-27 20:57
     */

    private String flowTitle;
    private String flowContent;
    private String flowImgUrl;
    private Object flowTime;
    private String flowStatus;
    private String opCreateTime;

    public String getFlowTitle() {
        return flowTitle;
    }

    public void setFlowTitle(String flowTitle) {
        this.flowTitle = flowTitle;
    }

    public String getFlowContent() {
        return flowContent;
    }

    public void setFlowContent(String flowContent) {
        this.flowContent = flowContent;
    }

    public String getFlowImgUrl() {
        return flowImgUrl;
    }

    public void setFlowImgUrl(String flowImgUrl) {
        this.flowImgUrl = flowImgUrl;
    }

    public Object getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Object flowTime) {
        this.flowTime = flowTime;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(String opCreateTime) {
        this.opCreateTime = opCreateTime;
    }
}
