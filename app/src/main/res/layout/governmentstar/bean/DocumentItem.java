package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/14.
 */

public class DocumentItem {
    /**
     * data : {"result":true,"opState":"2","docStatus":"1"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * result : true
         * opState : 2
         * docStatus : 1
         */

        private boolean result;
        private String opState;
        private String docStatus;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getOpState() {
            return opState;
        }

        public void setOpState(String opState) {
            this.opState = opState;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }
    }
}
