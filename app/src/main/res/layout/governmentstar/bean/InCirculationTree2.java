package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/3.
 */

public class InCirculationTree2 {
    private List<DeptList> data;

    public InCirculationTree2(List<DeptList> deptList) {
        this.data = deptList;
    }

    public List<DeptList> getDeptList() {
        return data;
    }

    public void setDeptList(List<DeptList> deptList) {
        this.data = deptList;
    }

    public class DeptList{
        private List<ChildList> childList;
        private String opId;
        private String opName;

        public DeptList(List<ChildList> childList, String opId, String opName) {
            this.childList = childList;
            this.opId = opId;
            this.opName = opName;
        }

        public List<ChildList> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildList> childList) {
            this.childList = childList;
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
    public static class ChildList{
        private String accountDpet;
        private String accountRole;
        private String opId;
        private String opName;

        public ChildList(String accountDpet, String accountRole, String opId, String opName) {
            this.accountDpet = accountDpet;
            this.accountRole = accountRole;
            this.opId = opId;
            this.opName = opName;
        }

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
