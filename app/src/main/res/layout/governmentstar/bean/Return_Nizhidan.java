package com.lanwei.governmentstar.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/4.
 */

public class Return_Nizhidan {


    /**
     * message : null
     * data : {"docTitle":"有公文字号的归档测试003","gwzh":"津蓟工信函 〔2017〕7号","docCode":"1000D020020170906/0011","docGzlx":"决定","docGwzt":"应急管理","docQcrName":"张弘","docQcrTime":"2017/09/06 15:07","docShName":"马季鸿","docShTime":"2017/09/06 15:07","docSyName":"李孟和","docSyTime":"2017/09/06 15:09","docJdName":"潘序东","docJdTime":"2017/09/06 15:09","docQfName":"黄兆广","docQfTime":"2017/09/06 15:10","docHqName":"马季鸿","docHfName":"潘序东","docHfTime":"2017/09/06 15:12","gxSm":"took哦哦OK了","gxState":{"share-status-01":"无条件共享","share-status-02":"有条件共享","share-status-03":"不共享"}}
     */

    private Object message;
    private DataBean data;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * docTitle : 有公文字号的归档测试003
         * gwzh : 津蓟工信函 〔2017〕7号
         * docCode : 1000D020020170906/0011
         * docGzlx : 决定
         * docGwzt : 应急管理
         * docQcrName : 张弘
         * docQcrTime : 2017/09/06 15:07
         * docShName : 马季鸿
         * docShTime : 2017/09/06 15:07
         * docSyName : 李孟和
         * docSyTime : 2017/09/06 15:09
         * docJdName : 潘序东
         * docJdTime : 2017/09/06 15:09
         * docQfName : 黄兆广
         * docQfTime : 2017/09/06 15:10
         * docHqName : 马季鸿
         * docHfName : 潘序东
         * docHfTime : 2017/09/06 15:12
         * docGdName : 2017/09/06 15:12
         * docGdTime : 2017/09/06 15:12
         * gxSm : took哦哦OK了
         * gxState : {"share-status-01":"无条件共享","share-status-02":"有条件共享","share-status-03":"不共享"}
         */

        private String docTitle;
        private String gwzh;
        private String docCode;
        private String docGzlx;
        private String docGwzt;
        private String docQcrName;
        private String docQcrTime;
        private String docShName;
        private String docShTime;
        private String docSyName;
        private String docSyTime;
        private String docJdName;
        private String docJdTime;
        private String docQfName;
        private String docQfTime;
        private String docHqName;
        private String docHfName;
        private String docHfTime;
        private String docGdName;
        private String docGdTime;
        private String gxSm;
        private Map<String ,String> gxState;

        public String getDocTitle() {
            return docTitle;
        }

        public void setDocTitle(String docTitle) {
            this.docTitle = docTitle;
        }

        public String getGwzh() {
            return gwzh;
        }

        public String getDocGdName() {
            return docGdName;
        }

        public void setDocGdName(String docGdName) {
            this.docGdName = docGdName;
        }

        public String getDocGdTime() {
            return docGdTime;
        }

        public void setDocGdTime(String docGdTime) {
            this.docGdTime = docGdTime;
        }

        public void setGwzh(String gwzh) {
            this.gwzh = gwzh;
        }

        public String getDocCode() {
            return docCode;
        }

        public void setDocCode(String docCode) {
            this.docCode = docCode;
        }

        public String getDocGzlx() {
            return docGzlx;
        }

        public void setDocGzlx(String docGzlx) {
            this.docGzlx = docGzlx;
        }

        public String getDocGwzt() {
            return docGwzt;
        }

        public void setDocGwzt(String docGwzt) {
            this.docGwzt = docGwzt;
        }

        public String getDocQcrName() {
            return docQcrName;
        }

        public void setDocQcrName(String docQcrName) {
            this.docQcrName = docQcrName;
        }

        public String getDocQcrTime() {
            return docQcrTime;
        }

        public void setDocQcrTime(String docQcrTime) {
            this.docQcrTime = docQcrTime;
        }

        public String getDocShName() {
            return docShName;
        }

        public void setDocShName(String docShName) {
            this.docShName = docShName;
        }

        public String getDocShTime() {
            return docShTime;
        }

        public void setDocShTime(String docShTime) {
            this.docShTime = docShTime;
        }

        public String getDocSyName() {
            return docSyName;
        }

        public void setDocSyName(String docSyName) {
            this.docSyName = docSyName;
        }

        public String getDocSyTime() {
            return docSyTime;
        }

        public void setDocSyTime(String docSyTime) {
            this.docSyTime = docSyTime;
        }

        public String getDocJdName() {
            return docJdName;
        }

        public void setDocJdName(String docJdName) {
            this.docJdName = docJdName;
        }

        public String getDocJdTime() {
            return docJdTime;
        }

        public void setDocJdTime(String docJdTime) {
            this.docJdTime = docJdTime;
        }

        public String getDocQfName() {
            return docQfName;
        }

        public void setDocQfName(String docQfName) {
            this.docQfName = docQfName;
        }

        public String getDocQfTime() {
            return docQfTime;
        }

        public void setDocQfTime(String docQfTime) {
            this.docQfTime = docQfTime;
        }

        public String getDocHqName() {
            return docHqName;
        }

        public void setDocHqName(String docHqName) {
            this.docHqName = docHqName;
        }

        public String getDocHfName() {
            return docHfName;
        }

        public void setDocHfName(String docHfName) {
            this.docHfName = docHfName;
        }

        public String getDocHfTime() {
            return docHfTime;
        }

        public void setDocHfTime(String docHfTime) {
            this.docHfTime = docHfTime;
        }

        public String getGxSm() {
            return gxSm;
        }

        public void setGxSm(String gxSm) {
            this.gxSm = gxSm;
        }

        public Map<String, String> getGxState() {
            return gxState;
        }

        public void setGxState(Map<String, String> gxState) {
            this.gxState = gxState;
        }

    }
}
