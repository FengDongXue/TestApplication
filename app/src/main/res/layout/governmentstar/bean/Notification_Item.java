package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/5/26.
 */

public class Notification_Item {

//    05-26 10:13:40.433 14399-14960/com.lanwei.governmentstar D/OkHttp: {"data":
//                                    {"opId":"416c77d1-10b8-44b8-93b3-880f5b604bda",
//                                     "noticeTitle":null,
//                                     "noticeUrl":"http://121.42.29.226:80/docHtml/noticeHtml.jsp?opId=416c77d1-10b8-44b8-93b3-880f5b604bda",
//                                     "opState":null,
//                                     "noticeType":"其他知照意图",
//                                     "opCreateId":"18537d2f-5466-4279-b4be-ab66e2c96d7b",
//                                      "opCreateName":"张浩宁",
//                                      "opCreateTime":"2017年05月26日 09:59",
//                                      "noticeCollectionState":false,
//                                      "fileList":[]}}

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
        private ArrayList<Filelist> fileList;


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

        public ArrayList<Filelist> getFileList() {
            return fileList;
        }

        public void setFileList(ArrayList<Filelist> fileList) {
            this.fileList = fileList;
        }
    }

    public class Filelist{

//        "path":"http://121.42.29.226:80/uploadFile/mk-ggtz-ggtz/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170526/c72f7140-7a28-47a5-9b4d-afae24358d7d/2007exl.xlsx","opName":"2007exl.xlsx","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=5901bef9-f7a2-4726-ab1d-2f518d2320b1"}、

        private String path;
        private String opName;
        private String filePreview;


        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }

        public String getFilePreview() {
            return filePreview;
        }

        public void setFilePreview(String filePreview) {
            this.filePreview = filePreview;
        }
    }


}
