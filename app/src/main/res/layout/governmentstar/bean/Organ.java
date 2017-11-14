package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * ${END}
 * <p>
 * Created by 蓝威科技—技术部2 on 2017/3/28.
 */
public class Organ implements Serializable {

    private String opId;
    private String opName;
    private String deptAddress;
    private String deptPhone;
    private String deptState;

    public void setOpid(String opId) {
        this.opId = opId;
    }

    public String getOpid() {
        return opId;
    }

    public void setOpname(String opName) {
        this.opName = opName;
    }

    public String getOpname() {
        return opName;
    }

    public void setDeptaddress(String deptAddress) {
        this.deptAddress = deptAddress;
    }

    public String getDeptaddress() {
        return deptAddress;
    }

    public void setDeptphone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getDeptphone() {
        return deptPhone;
    }

    public void setDeptState(String deptState) {
        this.deptState = deptState;
    }

    public String getDeptState() {
        return deptState;
    }

}