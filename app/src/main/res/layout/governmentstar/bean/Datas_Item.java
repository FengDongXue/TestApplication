package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/3.
 */

public class Datas_Item {

    private String flowTitle;
    private String flowContent;
    private String flowImgUrl;
    private String flowTime;
    private String flowStatus;
    private String opCreateTime;
    private String flowBut;
    private String personId;
    private ArrayList<ManyPeople> manyPeople;

//    {"flowTitle":"6.公文联合会签，已全部完成 ",
//    *   "flowContent":hardwork,
//    *   "flowImgUrl":hardwork,
//    *   "flowTime":hardwork,
//    *   "flowStatus":"6",
//    *   "opCreateTime":hardwork,
//    *   "flowBut":hardwork,
//    *   "manyPeople":[
//    *   {"flowTitle":hardwork,
//    *   "flowContent":"闻君孝已会签公文！",
//    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/main/default.jpg",
//    *   "flowTime":hardwork,"flowStatus":"6",
//    *   "opCreateTime":"2017-04-06 11:31",
//    *   "flowBut":"查看会签意见",},
//


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

    public String getFlowBut() {
        return flowBut;
    }

    public void setFlowBut(String flowBut) {
        this.flowBut = flowBut;
    }

    public ArrayList<ManyPeople> getManyPeople() {
        return manyPeople;
    }

    public void setManyPeople(ArrayList<ManyPeople> manyPeople) {
        this.manyPeople = manyPeople;
    }
}

