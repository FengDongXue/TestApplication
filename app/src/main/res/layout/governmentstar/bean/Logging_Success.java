package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/24.
 */

public class Logging_Success {

    //  {"message":"登陆成功！",
    //    "data":  {  "accountAddress":"",
    //                 “accountCard”：“”
    //                "accountDeptId":"0105",
    //                "accountDeptName":"蓟州区渔阳镇人民政府",
    //                 "accountLogin":"18322595845",
    //                  "accountMobile":"18322595845",
    //                 "accountPhone":"",
    //                 "accountRoleId":"duty-05",
    //                 "accountRoleName":"一般科员",
    //                   "accountSectorId":"010517",
    //                   "accountSectorName":"拆迁办",
    //                   "accountlink":"",
    //                    "opId":"bdd45a25-700f-4402-9690-a9db08a37037",
    //                      "opName":"冯冬雪"
    //              },
    //     "security":false
    // }

    //主键ID
    private String message;
    private String otherLogin;
    private Boolean security;
    private Data data;

    public String getOtherLogin() {
        return otherLogin;
    }

    public void setOtherLogin(String otherLogin) {
        this.otherLogin = otherLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSecurity() {
        return security;
    }

    public void setSecurity(Boolean security) {
        this.security = security;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        private String accountAddress;
        //头像链接

        private String accountDeptId;
        //机关ID

        private String accountDeptName;
        //机关名称

        private String accountLogin;
        //姓名

        private String accountMobile;
        //部门ID

        private String accountPhone;
        //部门名称

        private String accountRoleId;

        private String accountRoleName;
        //移动电话

        private String accountSectorId;

        private String accountCard;

        private String accountSectorName;

        private String accountlink;
        //移动电话
        private String opId;

        private String opName;


        public String getAccountAddress() {
            return accountAddress;
        }

        public void setAccountAddress(String accountAddress) {
            this.accountAddress = accountAddress;
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

        public String getAccountLogin() {
            return accountLogin;
        }

        public void setAccountLogin(String accountLogin) {
            this.accountLogin = accountLogin;
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

        public String getAccountSectorId() {
            return accountSectorId;
        }

        public void setAccountSectorId(String accountSectorId) {
            this.accountSectorId = accountSectorId;
        }

        public String getAccountCard() {
            return accountCard;
        }

        public void setAccountCard(String accountCard) {
            this.accountCard = accountCard;
        }

        public String getAccountSectorName() {
            return accountSectorName;
        }

        public void setAccountSectorName(String accountSectorName) {
            this.accountSectorName = accountSectorName;
        }

        public String getAccountlink() {
            return accountlink;
        }

        public void setAccountlink(String accountlink) {
            this.accountlink = accountlink;
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
