package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30/030.
 */

public class SpsqApplyList {

    /**
     * pageCount : 1
     * pageNo : 1
     * data : [{"deptName":"党政办公室","time":"2017-09-244 05:45:42","status":"1","name":"外出申请-执行公务申请","opId":"7ce0ae55-7543-4114-945b-1f55c1a00396","state":"0","personName":"卢亚静"},{"deptName":"党政办公室","time":"2017-09-244 11:54:09","status":"1","name":"物品申请-申请采购","opId":"b4af6cd4-bcaf-4253-8537-b4b0045d4917","state":"0","personName":"卢亚静"},{"deptName":"党政办公室","time":"2017-08-243 09:18:41","status":"1","name":"请假申请-事假","opId":"ab2e9290-e7dd-461f-9724-14db97663d88","state":"0","personName":"卢亚静"},{"deptName":"党政办公室","time":"2017-08-243 09:18:20","status":"1","name":"请假申请-事假","opId":"08b285a1-cc4f-4c7b-9e3b-5567d2e42c5f","state":"0","personName":"卢亚静"},{"deptName":"党政办公室","time":"2017-08-243 09:14:57","status":"1","name":"请假申请-婚假","opId":"a54c4ecb-b3fa-4171-ba87-27e3fd342acf","state":"0","personName":"卢亚静"},{"deptName":"党政办公室","time":"2017-08-243 09:14:31","status":"1","name":"请假申请-事假","opId":"d6629bce-cd7f-4851-be16-5cd72bb54d79","state":"0","personName":"卢亚静"},{"deptName":"党政办公室","time":"2017-08-243 09:14:08","status":"1","name":"资质印章-用印申请","opId":"e2d5c4cc-444d-485e-9b54-07c3fa49fe12","state":"0","personName":"卢亚静"},{"deptName":"党政办公室","time":"2017-08-243 09:13:45","status":"1","name":"请假申请-婚假","opId":"39d39093-4c08-474c-a712-942031307244","state":"0","personName":"卢亚静"}]
     */

    private int pageCount;
    private int pageNo;
    private List<DataBean> data;
    private String spsqFlag;

    public String getSpsqFlag() {
        return spsqFlag;
    }

    public void setSpsqFlag(String spsqFlag) {
        this.spsqFlag = spsqFlag;
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
         * deptName : 党政办公室
         * time : 2017-09-244 05:45:42
         * status : 1
         * name : 外出申请-执行公务申请
         * opId : 7ce0ae55-7543-4114-945b-1f55c1a00396
         * state : 0
         * BT : 0
         * personName : 卢亚静
         */

        private String deptName;
        private String time;
        private String status;
        private String name;
        private String opId;
        private String state;
        private String BT;
        private String personName;

        public String getBT() {
            return BT;
        }

        public void setBT(String BT) {
            this.BT = BT;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }
    }
}
