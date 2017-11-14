package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/8.
 */

public class Search_document {
    /**
     * pageCount : 2
     * pageNo : 1
     * data : [{"opId":"654b27bc-f540-4fbc-b92b-b0c9748dd863","docTitle":"××××××文件","opCreateTime":"2017/03/26","opState":"1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"3610507d-f9b0-4efa-a35d-d1f706eebef8","docTitle":"关于×××××××××××的决议","opCreateTime":"2017/04/03","opState":"-1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"1d885497-3f61-4f5c-bb28-159a599613b4","docTitle":"关于×××××××××××的决议","opCreateTime":"2017/04/07","opState":"1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"0ac3bebd-3ee5-48c6-b24a-aeaf4b70a78a","docTitle":"文件404打算大发发啊发发法师法法师大叔大婶大大大","opCreateTime":"2017/04/07","opState":"1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"70bbec48-69c4-4344-97de-b506e5210c7a","docTitle":"totti","opCreateTime":"2017/03/27","opState":"1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"cc2202c6-3f93-4575-bdd6-12cabfe3a3f0","docTitle":"决议","opCreateTime":"2017/04/03","opState":"1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"da775bb8-4dc1-4245-9803-a416c76ed7c9","docTitle":"阿达发大水发","opCreateTime":"2017/04/07","opState":"1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"edfb6ba2-dc37-4e4a-9c8a-ef97719cce90","docTitle":"一共三是三个三个一共三是三个三个一共三是三个三个一共三是三个三个个","opCreateTime":"2017/04/07","opState":"1","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"18b88613-60c0-46b3-9ea0-e6e176244c6f","docTitle":"123131","opCreateTime":"2017/04/02","opState":"2","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"797e1845-4563-476e-ac36-d35dd1d0596d","docTitle":"多撒多","opCreateTime":"2017/03/26","opState":"2","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"d61708e3-6342-4c52-bdc1-3c27b0babaf4","docTitle":"天津市人民政府印发关于发展众创空间推进大众创新创业政策措施的通知","opCreateTime":"2017/03/26","opState":"2","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"1d540587-9144-40a9-bf45-0f342553fcde","docTitle":"456","opCreateTime":"2017/03/25","opState":"4","opCreateName":"张浩宁","docStatus":hardwork},{"opId":"6a3c89ac-6984-4436-b725-9f54ab5bc8f2","docTitle":"关于×××××××××××的决议","opCreateTime":"2017/03/28","opState":"4","opCreateName":"张浩宁","docStatus":hardwork}]
     */

    private int pageCount;
    private int pageNo;
    private List<DataBean> data;

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
         * opId : 654b27bc-f540-4fbc-b92b-b0c9748dd863
         * docTitle : ××××××文件
         * opCreateTime : 2017/03/26
         * opState : 1
         * opCreateName : 张浩宁
         * docStatus : hardwork
         */

        private String opId;
        private String docTitle;
        private String opCreateTime;
        private String opState;
        private String opCreateName;
        private Object docStatus;

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

        public String getOpCreateTime() {
            return opCreateTime;
        }

        public void setOpCreateTime(String opCreateTime) {
            this.opCreateTime = opCreateTime;
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

        public Object getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(Object docStatus) {
            this.docStatus = docStatus;
        }
    }
}
