package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/5/26.
 */

public class Notification_List {



//    05-26 09:02:03.747 2859-3743/com.lanwei.governmentstar D/OkHttp:
// {"pageCount":1,"pageNo":1,"data":
//         [{"opId":"aebeeefe-2a44-4963-8eae-d6accaad9e79","noticeTitle":"大师傅赶得上赶得上范德萨范德萨","noticeUrl":null,"opState":null,"noticeType":null,"opCreateId":"18537d2f-5466-4279-b4be-ab66e2c96d7b","opCreateName":"","opCreateTime":"2017-05-26 08:42","noticeCollectionState":false,"fileList":null},
//          {"opId":"3eda125c-e78e-4235-94d5-471bf9d18c78","noticeTitle":"是对方如果豆腐干豆腐干地方很方便","opState":"1","noticeUrl":null,"opState":null,"noticeType":null,"opCreateId":"18537d2f-5466-4279-b4be-ab66e2c96d7b","opCreateName":"","opCreateTime":"2017-05-25 17:28","noticeCollectionState":false,"fileList":null}]}


    private String pageCount;
    private String pageNo;
    private ArrayList<Data> data;

    public Notification_List(String pageCount, String pageNo, ArrayList<Data> data) {
        this.pageCount = pageCount;
        this.pageNo = pageNo;
        this.data = data;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data{

        private String opId;
        private String noticeTitle;
        private String noticeUrl;
        private String opState;
        private String noticeType;
        private String opCreateId;
        private String opCreateName;
        private String opCreateTime;
        private Boolean noticeCollectionState;
        private String fileList;
        private String opReadState;

        public String getOpReadState() {
            return opReadState;
        }

        public void setOpReadState(String opReadState) {
            this.opReadState = opReadState;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getNoticeUrl() {
            return noticeUrl;
        }

        public void setNoticeUrl(String noticeUrl) {
            this.noticeUrl = noticeUrl;
        }

        public String getOpState() {
            return opState;
        }

        public void setOpState(String opState) {
            this.opState = opState;
        }

        public String getNoticeType() {
            return noticeType;
        }

        public void setNoticeType(String noticeType) {
            this.noticeType = noticeType;
        }

        public String getOpCreateId() {
            return opCreateId;
        }

        public void setOpCreateId(String opCreateId) {
            this.opCreateId = opCreateId;
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

        public Boolean getNoticeCollectionState() {
            return noticeCollectionState;
        }

        public void setNoticeCollectionState(Boolean noticeCollectionState) {
            this.noticeCollectionState = noticeCollectionState;
        }

        public String getFileList() {
            return fileList;
        }

        public void setFileList(String fileList) {
            this.fileList = fileList;
        }
    }






}
