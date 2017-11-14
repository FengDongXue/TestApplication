package com.lanwei.governmentstar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/12.
 */

public class ThreeItemInfo2 {

    private String status = "1";
    private boolean isExpading =false;
    private String opName ="";
    private String opId ="";
    private String accountDpet ="";
    private String accountRole ="";
    private Boolean is_choosed = false;
    private Boolean is_extend =false;
    private List<ThreeItemInfo2> childList = new ArrayList<>();

    public Boolean getIs_extend() {
        return is_extend;
    }

    public void setIs_extend(Boolean is_extend) {
        this.is_extend = is_extend;
    }

    public Boolean getIs_choosed() {
        return is_choosed;
    }

    public void setIs_choosed(Boolean is_choosed) {
        this.is_choosed = is_choosed;
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

    public boolean isExpading() {
        return isExpading;
    }

    public void setExpading(boolean expading) {
        isExpading = expading;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public boolean getIsExpading(){
        return isExpading ;
    }
    public void setIsExpading(boolean isExpading){
        this.isExpading = isExpading ;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ThreeItemInfo2> getChildList() {
        return childList;
    }

    public void setChildList(List<ThreeItemInfo2> childList) {
        this.childList = childList;
    }
}
