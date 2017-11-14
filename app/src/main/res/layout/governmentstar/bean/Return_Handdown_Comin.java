package com.lanwei.governmentstar.bean;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/5.
 */

public class Return_Handdown_Comin {


    private String message;

    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public  class Data{

        private ArrayList<IssuedFileList> issuedFileList;
        private  String issuedURL;
        private  String issuedZh5;
        private  String issuedZh4;
        private  String issuedZh3;
        private  String issuedZh2;
        private  String issuedZh1;
        private  String issuedTitle;
        private  String issuedLwdwId;
        private  String issuedLwdwName;


        public String getIssuedLwdwId() {
            return issuedLwdwId;
        }

        public void setIssuedLwdwId(String issuedLwdwId) {
            this.issuedLwdwId = issuedLwdwId;
        }

        public String getIssuedLwdwName() {
            return issuedLwdwName;
        }

        public void setIssuedLwdwName(String issuedLwdwName) {
            this.issuedLwdwName = issuedLwdwName;
        }

        public ArrayList<IssuedFileList> getIssuedFileList() {
            return issuedFileList;
        }

        public void setIssuedFileList(ArrayList<IssuedFileList> issuedFileList) {
            this.issuedFileList = issuedFileList;
        }

        public String getIssuedURL() {
            return issuedURL;
        }

        public void setIssuedURL(String issuedURL) {
            this.issuedURL = issuedURL;
        }

        public String getIssuedZh5() {
            return issuedZh5;
        }

        public void setIssuedZh5(String issuedZh5) {
            this.issuedZh5 = issuedZh5;
        }

        public String getIssuedZh4() {
            return issuedZh4;
        }

        public void setIssuedZh4(String issuedZh4) {
            this.issuedZh4 = issuedZh4;
        }

        public String getIssuedZh3() {
            return issuedZh3;
        }

        public void setIssuedZh3(String issuedZh3) {
            this.issuedZh3 = issuedZh3;
        }

        public String getIssuedZh2() {
            return issuedZh2;
        }

        public void setIssuedZh2(String issuedZh2) {
            this.issuedZh2 = issuedZh2;
        }

        public String getIssuedZh1() {
            return issuedZh1;
        }

        public void setIssuedZh1(String issuedZh1) {
            this.issuedZh1 = issuedZh1;
        }

        public String getIssuedTitle() {
            return issuedTitle;
        }

        public void setIssuedTitle(String issuedTitle) {
            this.issuedTitle = issuedTitle;
        }
    }


    public class IssuedFileList {

        private String path;
        private String opName;
        private String filePreview;

        public IssuedFileList(String path, String opName, String filePreview) {
            this.path = path;
            this.opName = opName;
            this.filePreview = filePreview;
        }

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
