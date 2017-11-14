package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/27.
 */

public class Bean_Spsq {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * childList : [{"accountDpet":"012701","accountRole":"duty-02","opId":"9695dca5-def1-46fd-9971-fe0985abcb26","opName":"黄兆广"},{"accountDpet":"012701","accountRole":"duty-02","opId":"f7cfa5bb-4ee7-41a9-8a57-b0366871e750","opName":"李孟和"},{"accountDpet":"012701","accountRole":"duty-02","opId":"af3a1406-75ca-492a-aaea-7e32e3f4734b","opName":"付士明"},{"accountDpet":"012701","accountRole":"duty-02","opId":"3d2a2355-48eb-4ab7-b9f9-e8fce0836722","opName":"于艳如"},{"accountDpet":"012701","accountRole":"duty-02","opId":"95155d73-2751-4252-b8fd-ab65f896e7d2","opName":"张建民"},{"accountDpet":"012701","accountRole":"duty-02","opId":"c4e6466e-85f8-41b0-9ec9-6869c6824144","opName":"张瑞明"},{"accountDpet":"012701","accountRole":"duty-02","opId":"8e3fc9b1-0c51-44ca-9306-7fcf5815940f","opName":"李亚宾"}]
         * opId : 012701
         * opName : 主任领导
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

        public static class ChildListBean {
            /**
             * accountDpet : 012701
             * accountRole : duty-02
             * opId : 9695dca5-def1-46fd-9971-fe0985abcb26
             * opName : 黄兆广
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
}
