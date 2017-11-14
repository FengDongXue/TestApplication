package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * Created by xong2 on 2017/3/pic24.
 */

public class Department implements Serializable{

    private String opId;
    private String opName;
    private String deptAddress;
    private String deptPhone;
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
}
