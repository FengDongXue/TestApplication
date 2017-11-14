package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/3.
 */

public class Notification_Item02 {

//    {"notictCode":"1001N03170701/0007",
//     "notictType":"布告类通知",
//     "opCreateName":"于艳如",
//    "noticeTitle":"士大夫但是鬼地方高浮雕和个百分点"
//     "opId":"be1adb43-2c16-4b0b-9afe-2394f46c398b",
//     "opCreateTime":"2017/07/01 14:09",
//     "fileNum":"2"}

    private String notictCode;
    private String notictType;
    private String opCreateName;
    private String opId;
    private String opCreateTime;
    private String fileNum;

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    private String noticeTitle;

    public String getNotictCode() {
        return notictCode;
    }

    public void setNotictCode(String notictCode) {
        this.notictCode = notictCode;
    }

    public String getNotictType() {
        return notictType;
    }

    public void setNotictType(String notictType) {
        this.notictType = notictType;
    }

    public String getOpCreateName() {
        return opCreateName;
    }

    public void setOpCreateName(String opCreateName) {
        this.opCreateName = opCreateName;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(String opCreateTime) {
        this.opCreateTime = opCreateTime;
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }
}
