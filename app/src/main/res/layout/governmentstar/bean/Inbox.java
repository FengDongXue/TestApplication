package com.lanwei.governmentstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26/026.
 */

public class Inbox implements Serializable {
    private List<DataBean> data;
    private int pageCount;  //总页数
    private int pageNo;  //当前页数

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setPagecount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPagecount() {
        return pageCount;
    }

    public void setPageno(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageno() {
        return pageNo;
    }

    public class DataBean {
        private  int isCheck;  // 0 选择 1 未选
        private int isSlide; // 0 滑动 1 未滑动
        private String mailContent; //邮件内容
        private String mailPath; //发件人的头像
        private String mailTitle; //邮件标题
        private String opCreateTime; //创建时间
        private String opId; //发件人
        private String mailState; //已读未读  0未读  1已读


        public int getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(int isCheck) {
            this.isCheck = isCheck;
        }

        public int getIsSlide() {
            return isSlide;
        }

        public void setIsSlide(int isSlide) {
            this.isSlide = isSlide;
        }

        public void setMailcontent(String mailContent) {
            this.mailContent = mailContent;
        }

        public String getMailcontent() {
            return mailContent;
        }

        public void setMailpath(String mailPath) {
            this.mailPath = mailPath;
        }

        public String getMailpath() {
            return mailPath;
        }

        public void setMailtitle(String mailTitle) {
            this.mailTitle = mailTitle;
        }

        public String getMailtitle() {
            return mailTitle;
        }

        public void setOpcreatetime(String opCreateTime) {
            this.opCreateTime = opCreateTime;
        }

        public String getOpcreatetime() {
            return opCreateTime;
        }

        public void setOpid(String opId) {
            this.opId = opId;
        }

        public String getOpid() {
            return opId;
        }

        public String getMailState() {
            return mailState;
        }

        public void setMailState(String mailState) {
            this.mailState = mailState;
        }
    }
}

