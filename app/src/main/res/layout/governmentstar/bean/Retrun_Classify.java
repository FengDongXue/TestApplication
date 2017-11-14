package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/1.
 */

public class Retrun_Classify {


    /**
     * message : null
     * pageCount : 1
     * pageNo : 1
     * data : [{"opId":"d3844d2a-7a7b-4b5e-bb1a-c81287422ae7","docTitle":"时光隧道","docType":"拟制公文","opCreateTime":"2017/08/31 19:53","opState":null},{"opId":"81e1b327-f568-4d6d-9a5a-6d94a6b2272c","docTitle":"3个会签人的处理003","docType":"拟制公文","opCreateTime":"2017/08/31 16:20","opState":null},{"opId":"31140e06-fe2f-44ee-ad8e-c0c03c710047","docTitle":"3个会签人的处理001","docType":"拟制公文","opCreateTime":"2017/08/31 16:20","opState":null},{"opId":"7a896c1c-1031-4393-a832-cea0b234fc76","docTitle":"拟制到归档的测试","docType":"拟制公文","opCreateTime":"2017/08/31 11:02","opState":null},{"opId":"24bf805e-f367-449b-bbf3-7d6e40c539a3","docTitle":"收文到归档的测试","docType":"收文传阅","opCreateTime":"2017/08/31 11:00","opState":null},{"opId":"88858111-467b-43d7-8175-95048794bb04","docTitle":"工地施工方的","docType":"拟制公文","opCreateTime":"2017/08/31 10:59","opState":null},{"opId":"30137864-8802-4bf5-b46d-3ab9a67a0c6b","docTitle":"拟制到归档的从测试","docType":"拟制公文","opCreateTime":"2017/08/31 10:57","opState":null},{"opId":"17d9e775-b146-46a1-8e45-7efabaddf690","docTitle":"通知按时发生发送","docType":"拟制公文","opCreateTime":"2017/08/24 16:21","opState":null},{"opId":"12e98fe3-178c-4173-8df0-392c7c741f98","docTitle":"时光隧道根深蒂固","docType":"拟制公文","opCreateTime":"2017/08/23 17:10","opState":null},{"opId":"01f07c87-e626-434d-a41f-ad32dd8bfc55","docTitle":"1、移动端新增通告通知1、移动端新增通告通知","docType":"拟制公文","opCreateTime":"2017/08/21 23:04","opState":null},{"opId":"d02b6b67-39a3-49ef-9a53-f4d3cdae79de","docTitle":"两个成本那人反对法","docType":"收文传阅","opCreateTime":"2017/08/21 22:22","opState":null}]
     */

    private Object message;
    private int pageCount;
    private int pageNo;
    private List<DataBean> data;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * opId : d3844d2a-7a7b-4b5e-bb1a-c81287422ae7
         * docTitle : 时光隧道
         * docType : 拟制公文
         * opCreateTime : 2017/08/31 19:53
         * opState : null
         * "jyNum":"1"
         * "gxState":""
         * "docLwdw":"大飒飒的"
         */

        private String opId = "";
        private String docTitle = "";
        private String docType = "";
        private String opCreateTime = "";
        private String jyNum = "";
        private String gxState = "";
        private String docLwdw = "";
        private int opState;

        public DataBean(String opId, String docTitle, String docType, String opCreateTime, String jyNum, String gxState, String docLwdw, int opState) {
            this.opId = opId;
            this.docTitle = docTitle;
            this.docType = docType;
            this.opCreateTime = opCreateTime;
            this.jyNum = jyNum;
            this.gxState = gxState;
            this.docLwdw = docLwdw;
            this.opState = opState;
        }

        public String getDocLwdw() {
            return docLwdw;
        }

        public void setDocLwdw(String docLwdw) {
            this.docLwdw = docLwdw;
        }

        public String getJyNum() {
            return jyNum;
        }

        public void setJyNum(String jyNum) {
            this.jyNum = jyNum;
        }

        public String getGxState() {
            return gxState;
        }

        public void setGxState(String gxState) {
            this.gxState = gxState;
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

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public String getOpCreateTime() {
            return opCreateTime;
        }

        public void setOpCreateTime(String opCreateTime) {
            this.opCreateTime = opCreateTime;
        }

        public int getOpState() {
            return opState;
        }

        public void setOpState(int opState) {
            this.opState = opState;
        }
    }
}
