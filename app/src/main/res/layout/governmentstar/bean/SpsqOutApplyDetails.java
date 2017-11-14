package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31/031.
 */

public class SpsqOutApplyDetails {

    /**
     * deptName : 蓟州区工业和信息化委员会-党政办公室
     * files : [{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/a98fda36-61e0-48c6-bcd0-2df758424817/微信图片_20170830154532.jpg","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=8e4a2b5b-41c6-4dda-a448-cdb3a4cfdefd","fileName":"微信图片_20170830154532.jpg"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/a98fda36-61e0-48c6-bcd0-2df758424817/微信图片_20170818202313.psd","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=de5363f4-df62-4e1d-8c27-9721fe04c056","fileName":"微信图片_20170818202313.psd"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/a98fda36-61e0-48c6-bcd0-2df758424817/微信图片_20170818202313.jpg","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=885223fd-7478-4369-ae87-ff176be56297","fileName":"微信图片_20170818202313.jpg"}]
     * opTime : 2017/09/250 08:34
     * person : 潘序东
     * personImage : http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg
     * outOfficialPlice : 1232421341234214
     * data : [{"opTime":"2017-09-07 08:34","title":"1.潘序东提出申请","no":1,"opstate":"1","name":"潘序东","opId":"3edbe364-05c8-487b-afea-7528aab2f606","opImg":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg"},{"opTime":"2017-09-07 08:34","title":"2.马季鸿未批准","opstate":"0","no":1,"name":"马季鸿","opId":"06b70b5a-55ea-470a-a6d1-b38b7a0ed6be","opImg":"http://121.42.29.226:80/resources/images/head/f11c95f4-eda9-4768-9c3a-c4b4c4bf3826_20170814173009623.jpg"},{"opTime":"2017-09-07 08:34","title":"3.张瑞明未批准","opstate":"0","no":2,"name":"张瑞明","opId":"269a5763-50d6-4743-b31b-a42be06ad99e","opImg":"http://121.42.29.226:80"},{"opTime":"2017-09-07 08:34","title":"4.黄兆广未批准","opstate":"0","no":3,"name":"黄兆广","opId":"c8d6f795-7020-48d1-8658-81423e219d76","opImg":"http://121.42.29.226:80/resources/images/head/9695dca5-def1-46fd-9971-fe0985abcb26_20170812000250068.jpg"}]
     * CSRlist : 李护仲,解文东
     * outOfficialEndtime : 20179780000000
     * applicationNo : 01270201201709070002
     * BT : BT
     * type : 外出申请
     * fileNum : 3
     * outOfficialStarttime : 20179780000000
     * outOfficialReason : 213412341243124
     * outOfficialVehicle : 出租车
     * childType : 执行公务申请
     * typeId : 0201
     */

    private String deptName;
    private String opTime;
    private String person;
    private String personImage;
    private String outOfficialPlice;
    private String CSRlist;
    private String outOfficialEndtime;
    private String applicationNo;
    private String type;
    private int fileNum;
    private String outOfficialStarttime;
    private String outOfficialReason;
    private String outOfficialVehicle;
    private String childType;
    private String BT;
    private String typeId;
    private List<FilesBean> files;
    private List<DataBean> data;


    public String getBT() {
        return BT;
    }

    public void setBT(String BT) {
        this.BT = BT;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getOutOfficialPlice() {
        return outOfficialPlice;
    }

    public void setOutOfficialPlice(String outOfficialPlice) {
        this.outOfficialPlice = outOfficialPlice;
    }

    public String getCSRlist() {
        return CSRlist;
    }

    public void setCSRlist(String CSRlist) {
        this.CSRlist = CSRlist;
    }

    public String getOutOfficialEndtime() {
        return outOfficialEndtime;
    }

    public void setOutOfficialEndtime(String outOfficialEndtime) {
        this.outOfficialEndtime = outOfficialEndtime;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public String getOutOfficialStarttime() {
        return outOfficialStarttime;
    }

    public void setOutOfficialStarttime(String outOfficialStarttime) {
        this.outOfficialStarttime = outOfficialStarttime;
    }

    public String getOutOfficialReason() {
        return outOfficialReason;
    }

    public void setOutOfficialReason(String outOfficialReason) {
        this.outOfficialReason = outOfficialReason;
    }

    public String getOutOfficialVehicle() {
        return outOfficialVehicle;
    }

    public void setOutOfficialVehicle(String outOfficialVehicle) {
        this.outOfficialVehicle = outOfficialVehicle;
    }

    public String getChildType() {
        return childType;
    }

    public void setChildType(String childType) {
        this.childType = childType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class FilesBean {
        /**
         * filePath : http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/a98fda36-61e0-48c6-bcd0-2df758424817/微信图片_20170830154532.jpg
         * filePreView : http://121.42.29.226:80docHtml.jsp?opId=8e4a2b5b-41c6-4dda-a448-cdb3a4cfdefd
         * fileName : 微信图片_20170830154532.jpg
         */

        private String filePath;
        private String filePreView;
        private String fileName;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePreView() {
            return filePreView;
        }

        public void setFilePreView(String filePreView) {
            this.filePreView = filePreView;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

    public static class DataBean {
        /**
         * opTime : 2017-09-07 08:34
         * title : 1.潘序东提出申请
         * no : 1
         * opstate : 1
         * name : 潘序东
         * opId : 3edbe364-05c8-487b-afea-7528aab2f606
         * opImg : http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg
         */

        private String opTime;
        private String title;
        private int no;
        private String opstate;
        private String name;
        private String opId;
        private String opImg;

        public DataBean(String opTime, String title, int no, String opstate, String name, String opId, String opImg) {
            this.opTime = opTime;
            this.title = title;
            this.no = no;
            this.opstate = opstate;
            this.name = name;
            this.opId = opId;
            this.opImg = opImg;
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

        public String getOpImg() {
            return opImg;
        }

        public void setOpImg(String opImg) {
            this.opImg = opImg;
        }
    }
}
