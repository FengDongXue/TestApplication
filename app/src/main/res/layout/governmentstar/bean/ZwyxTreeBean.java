package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10/010.
 */

public class ZwyxTreeBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        /**
         * childList : [{"accountDpet":"0105","accountRole":"","opId":"02d98877-48d8-4c04-ad1d-273e15502241","opName":"朱合美"},{"accountDpet":"0105","accountRole":"","opId":"044e2e13-b992-4185-908d-9f7c5683e845","opName":"李志军"},{"accountDpet":"0105","accountRole":"","opId":"0498a112-e20f-4b7f-9b35-7ce5dd32bf00","opName":"孟学斌"},{"accountDpet":"0105","accountRole":"","opId":"04ad8714-6d44-4307-847d-2b974a242fe3","opName":"柴陈超"},{"accountDpet":"0105","accountRole":"","opId":"41661226-8379-4ef4-89c1-59fecc6cde45","opName":"王金富"},{"accountDpet":"0105","accountRole":"","opId":"419415b9-9e67-45ec-927b-145b05de0158","opName":"李旭"},{"
         * opId : 0105
         * opName : 蓟州区渔阳镇人民政府
         */

        private String opId;
        private String opName;
        private List<ChildListBean> childList;

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }

        public List<ChildListBean> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildListBean> childList) {
            this.childList = childList;
        }


    }

    public class ChildListBean {
        /**
         * accountDpet : 0105
         * accountRole :
         * opId : 02d98877-48d8-4c04-ad1d-273e15502241
         * opName : 朱合美
         */

        private String accountDpet;
        private String accountRole;
        private String opId;
        private String opName;

        public String getAccountDpet() {
            return accountDpet;
        }

        public void setAccountDpet(String accountDpet) {
            this.accountDpet = accountDpet;
        }

        public String getAccountRole() {
            return accountRole;
        }

        public void setAccountRole(String accountRole) {
            this.accountRole = accountRole;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }
    }
}
