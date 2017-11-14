package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/4.
 */

public class Data_Handdown {

    //       "data":[
//
//         {"opId":"681be9ab-6fe4-44d3-a80a-8f8e2791ff32",
//           "issuedTitle":"关于转发选择承办人001",
//            "issuedDeptName":"蓟州区工业和信息化委员会",
//             "opState":"0",
//               "opCreateName":"2017/07/27 14:32"}
//
//              ]


//     "issuedDeptId": "0154",
//             "opId": "1ca1eec3-62f8-434a-99f6-9810c25ae975",
//             "issuedTime": "2017/08/07 16:28",
//             "issuedStauts": "签收中",
//             "issuedDeptName": "蓟州区下窝头镇人民政府"

     private String opId;
     private String opParent;
     private String issuedTitle;
     private String issuedStatus;
     private String issuedTime;
     private String issuedDeptId;
     private String issuedDeptName;
     private String opState;
     private String opCreateName;
     private String issuedChildren;
    private Boolean isSelected =false;

    public Data_Handdown(String opId, String issuedTitle, String issuedDeptName, String opState, String opCreateName) {
        this.opId = opId;
        this.issuedTitle = issuedTitle;
        this.issuedDeptName = issuedDeptName;
        this.opState = opState;
        this.opCreateName = opCreateName;
    }

    public String getIssuedStatus() {
        return issuedStatus;
    }

    public void setIssuedStatus(String issuedStatus) {
        this.issuedStatus = issuedStatus;
    }

    public String getIssuedChildren() {
        return issuedChildren;
    }

    public void setIssuedChildren(String issuedChildren) {
        this.issuedChildren = issuedChildren;
    }

    public String getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

    public String getIssuedDeptId() {
        return issuedDeptId;
    }

    public void setIssuedDeptId(String issuedDeptId) {
        this.issuedDeptId = issuedDeptId;
    }

    public String getOpParent() {
        return opParent;
    }

    public void setOpParent(String opParent) {
        this.opParent = opParent;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getIssuedTitle() {
        return issuedTitle;
    }

    public void setIssuedTitle(String issuedTitle) {
        this.issuedTitle = issuedTitle;
    }

    public String getIssuedDeptName() {
        return issuedDeptName;
    }

    public void setIssuedDeptName(String issuedDeptName) {
        this.issuedDeptName = issuedDeptName;
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
}
