package com.lanwei.governmentstar.bean;

import java.io.Serializable;

public class BN_TAccountLogin implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //主键ID
    private String opId;
    //登陆账号
    private String accountLogin;
    //头像链接
    private String accountlink;
    //机关ID
    private String accountDeptId;
    //机关名称
    private String accountDeptName;
    //姓名
    private String opName;
    //部门ID
    private String accountSectorId;
    //部门名称
    private String accountSectorName;
    //移动电话
    private String accountMobile;
    //办公电话
    private String accountPhone;
    //权限ID
    private String accountRoleId;
    //权限名称
    private String accountRoleName;
    //地址
    private String accountAddress;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getAccountlink() {
        return accountlink;
    }

    public void setAccountlink(String accountlink) {
        this.accountlink = accountlink;
    }

    public String getAccountDeptId() {
        return accountDeptId;
    }

    public void setAccountDeptId(String accountDeptId) {
        this.accountDeptId = accountDeptId;
    }

    public String getAccountDeptName() {
        return accountDeptName;
    }

    public void setAccountDeptName(String accountDeptName) {
        this.accountDeptName = accountDeptName;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getAccountSectorId() {
        return accountSectorId;
    }

    public void setAccountSectorId(String accountSectorId) {
        this.accountSectorId = accountSectorId;
    }

    public String getAccountSectorName() {
        return accountSectorName;
    }

    public void setAccountSectorName(String accountSectorName) {
        this.accountSectorName = accountSectorName;
    }

    public String getAccountMobile() {
        return accountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        this.accountMobile = accountMobile;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(String accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public String getAccountRoleName() {
        return accountRoleName;
    }

    public void setAccountRoleName(String accountRoleName) {
        this.accountRoleName = accountRoleName;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    @Override
    public String toString() {
        return "BN_TAccountLogin [opId=" + opId + ", accountLogin="
                + accountLogin + ", accountlink=" + accountlink
                + ", accountDeptId=" + accountDeptId + ", accountDeptName="
                + accountDeptName + ", opName=" + opName + ", accountSectorId="
                + accountSectorId + ", accountSectorName=" + accountSectorName
                + ", accountMobile=" + accountMobile + ", accountPhone="
                + accountPhone + ", accountRoleId=" + accountRoleId
                + ", accountRoleName=" + accountRoleName + ", accountAddress=" + accountAddress + "]";
    }
}
