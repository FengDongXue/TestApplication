package com.lanwei.governmentstar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/27.
 */

public class Bean_Return_Flow {

    /**
     * CSRlist :
     * data : [{"opTime":"2017-09-26 17:16","title":"1.张弘提出申请","no":1,"opstate":"0","name":"张弘","opId":"75816fd9-cdcf-471b-bd73-6e005ff737b6","head":"1.已提出申请","opImg":"http://121.42.29.226:80/resources/images/main/default.jpg"},{"opTime":"2017-09-26 17:16","title":"2.马季鸿已审定通过","opstate":"1","no":2,"name":"马季鸿","opId":"86006cd5-9733-47ae-871f-cd2789d4fd72","head":"2.已审核","opImg":"http://121.42.29.226:80/resources/images/head/f11c95f4-eda9-4768-9c3a-c4b4c4bf3826_20170814173009623.jpg"},{"opTime":"2017-09-26 17:17","title":"3.赵庆录已审核通过","opstate":"2","no":3,"name":"赵庆录","opId":"f7907faa-b959-439e-b419-d90678c2043c","head":"3.已审核","opImg":"http://121.42.29.226:80/resources/images/main/default.jpg"},{"opTime":"2017-09-26 17:16","title":"4.潘序东已审核通过","opstate":"2","no":4,"name":"潘序东","opId":"00fe97eb-a14e-41ef-a083-0b5773787c80","head":"4.已审核","opImg":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg"},{"opTime":"2017-09-26 17:17","title":"5.李孟和已审核通过","opstate":"2","no":5,"name":"李孟和","opId":"2948aed3-897d-425f-850c-1b275ade2d33","head":"5.已审核","opImg":"http://121.42.29.226:80/resources/images/main/default.jpg"},{"opTime":"2017-09-26 17:18","title":"6.黄兆广未批准","opstate":"3","no":6,"name":"黄兆广","opId":"4b9f7a59-7cc9-45cc-9311-1066a376fe43","head":"6.已批示","opImg":"http://121.42.29.226:80/resources/images/head/9695dca5-def1-46fd-9971-fe0985abcb26_20170812000250068.jpg"},{"opTime":"","title":"","opstate":"","no":7,"opId":"","name":"","head":"","opImg":""}]
     * opNo : 01270301201709260005
     */

    private String CSRlist;
    private String opNo;
    private String createId;
    private List<DataBean> data;

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCSRlist() {
        return CSRlist;
    }

    public void setCSRlist(String CSRlist) {
        this.CSRlist = CSRlist;
    }

    public String getOpNo() {
        return opNo;
    }

    public void setOpNo(String opNo) {
        this.opNo = opNo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * opTime : 2017-09-26 17:16
         * title : 1.张弘提出申请
         * no : 1
         * opstate : 0
         * name : 张弘
         * opId : 75816fd9-cdcf-471b-bd73-6e005ff737b6
         * head : 1.已提出申请
         * opImg : http://121.42.29.226:80/resources/images/main/default.jpg
         */

        private String opTime;
        private String title;
        private int no;
        private String opstate;
        private String name;
        private String opId;
        private String head;
        private String opImg;
        private ArrayList<DataBean> list = new ArrayList<>();

        public ArrayList<DataBean> getList() {
            return list;
        }

        public void setList(ArrayList<DataBean> list) {
            this.list = list;
        }

        public String getOpTime() {
            return opTime;
        }

        public void setOpTime(String opTime) {
            this.opTime = opTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getOpstate() {
            return opstate;
        }

        public void setOpstate(String opstate) {
            this.opstate = opstate;
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

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getOpImg() {
            return opImg;
        }

        public void setOpImg(String opImg) {
            this.opImg = opImg;
        }
    }
}
