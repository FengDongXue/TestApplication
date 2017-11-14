package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/6.
 */

public class ManyPeople {

//    "manyPeople":[
//    *   {"flowTitle":hardwork,
//    *   "flowContent":"闻君孝已会签公文！",
//    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/main/default.jpg",
//    *   "flowTime":hardwork,"flowStatus":"6",
//    *   "opCreateTime":"2017-04-06 11:31",
//    *   "flowBut":"查看会签意见","manyPeople":hardwork},


    private String flowTitle;
    private String flowContent;
    private String flowImgUrl;
    private String flowTime;
    private String opCreateTime;
    private String flowBut;
    private String flowStatus;
    private String personId;

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public ManyPeople(String flowTitle, String flowContent, String flowImgUrl, String flowTime, String opCreateTime, String flowBut, String flowStatus) {
        this.flowTitle = flowTitle;
        this.flowContent = flowContent;
        this.flowImgUrl = flowImgUrl;
        this.flowTime = flowTime;
        this.opCreateTime = opCreateTime;
        this.flowBut = flowBut;
        this.flowStatus = flowStatus;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

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

    public String getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(String flowTime) {
        this.flowTime = flowTime;
    }

    public String getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(String opCreateTime) {
        this.opCreateTime = opCreateTime;
    }

    public String getFlowBut() {
        return flowBut;
    }

    public void setFlowBut(String flowBut) {
        this.flowBut = flowBut;
    }
}
