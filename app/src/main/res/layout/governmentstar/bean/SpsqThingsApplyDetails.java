package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7/007.
 */

public class SpsqThingsApplyDetails {
    /**
     * deptName : 蓟州区工业和信息化委员会-党政办公室
     * files : [{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/876ddc0a-e1f3-469c-814e-2610dc92639c/微信图片_20170818202313.psd","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=660d3fcd-1ab8-460f-b810-ed22cae61a1e","fileName":"微信图片_20170818202313.psd"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/876ddc0a-e1f3-469c-814e-2610dc92639c/微信图片_20170818202313.jpg","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=5674c59c-6efa-409d-a403-2cf44d3b68e8","fileName":"微信图片_20170818202313.jpg"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/876ddc0a-e1f3-469c-814e-2610dc92639c/微信图片_20170830154532.jpg","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=ace05771-e25f-4a8c-9619-5218a304badc","fileName":"微信图片_20170830154532.jpg"}]
     * opTime : 2017/09/250 08:41
     * goodsBuyName : 诶我去若
     * person : 潘序东
     * personImage : http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg
     * goodsDeliverydate :
     * data : [{"opTime":"2017-09-07 08:41","title":"1.潘序东提出申请","no":1,"opstate":"1","name":"潘序东","opId":"3d07ff7a-07c7-4d68-b801-d50ad5f82132","opImg":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg"},{"opTime":"2017-09-07 08:41","title":"2.张瑞明未批准","opstate":"0","no":1,"name":"张瑞明","opId":"9efa856d-5e31-4718-87d9-887932eea9b9","opImg":"http://121.42.29.226:80"},{"opTime":"2017-09-07 08:41","title":"3.解文东未批准","opstate":"0","no":2,"name":"解文东","opId":"3754dd01-907e-49fc-8d66-a1c4a69f3772","opImg":"http://121.42.29.226:80"},{"opTime":"2017-09-07 08:41","title":"4.黄兆广未批准","opstate":"0","no":3,"name":"黄兆广","opId":"20135507-51d1-4c4e-b869-e3f5f19bab90","opImg":"http://121.42.29.226:80/resources/images/head/9695dca5-def1-46fd-9971-fe0985abcb26_20170812000250068.jpg"}]
     * CSRlist : 仇占丽,李宁,张学文,张鹏博
     * goodsBuyPrice : 123
     * applicationNo : 01270401201709070005
     * goodsBuyStandard : 我去二
     * type : 物品申请
     * goodsBuyReason : 成都市挖坟
     * fileNum : 3
     * BT : BT
     * goodsBuyNum : 玩儿
     * goodsProcurementType : 自行采购
     * childType : 申请采购
     * goodsBuyDept : 我去二
     * typeId : 0401
     */

    private String deptName;
    private String opTime;
    private String goodsBuyName;
    private String person;
    private String personImage;
    private String goodsDeliverydate;
    private String CSRlist;
    private String goodsBuyPrice;
    private String applicationNo;
    private String goodsBuyStandard;
    private String type;
    private String goodsBuyReason;
    private String BT;
    private int fileNum;
    private String goodsBuyNum;
    private String goodsProcurementType;
    private String childType;
    private String goodsBuyDept;
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

    public String getGoodsBuyName() {
        return goodsBuyName;
    }

    public void setGoodsBuyName(String goodsBuyName) {
        this.goodsBuyName = goodsBuyName;
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

    public String getGoodsDeliverydate() {
        return goodsDeliverydate;
    }

    public void setGoodsDeliverydate(String goodsDeliverydate) {
        this.goodsDeliverydate = goodsDeliverydate;
    }

    public String getCSRlist() {
        return CSRlist;
    }

    public void setCSRlist(String CSRlist) {
        this.CSRlist = CSRlist;
    }

    public String getGoodsBuyPrice() {
        return goodsBuyPrice;
    }

    public void setGoodsBuyPrice(String goodsBuyPrice) {
        this.goodsBuyPrice = goodsBuyPrice;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getGoodsBuyStandard() {
        return goodsBuyStandard;
    }

    public void setGoodsBuyStandard(String goodsBuyStandard) {
        this.goodsBuyStandard = goodsBuyStandard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodsBuyReason() {
        return goodsBuyReason;
    }

    public void setGoodsBuyReason(String goodsBuyReason) {
        this.goodsBuyReason = goodsBuyReason;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public String getGoodsBuyNum() {
        return goodsBuyNum;
    }

    public void setGoodsBuyNum(String goodsBuyNum) {
        this.goodsBuyNum = goodsBuyNum;
    }

    public String getGoodsProcurementType() {
        return goodsProcurementType;
    }

    public void setGoodsProcurementType(String goodsProcurementType) {
        this.goodsProcurementType = goodsProcurementType;
    }

    public String getChildType() {
        return childType;
    }

    public void setChildType(String childType) {
        this.childType = childType;
    }

    public String getGoodsBuyDept() {
        return goodsBuyDept;
    }

    public void setGoodsBuyDept(String goodsBuyDept) {
        this.goodsBuyDept = goodsBuyDept;
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
         * filePath : http://121.42.29.226:80/uploadFile/mk-spsq-shlc/d8900fc8-50fa-4757-b8a8-4ea52a76fe02/file/20170907/876ddc0a-e1f3-469c-814e-2610dc92639c/微信图片_20170818202313.psd
         * filePreView : http://121.42.29.226:80docHtml.jsp?opId=660d3fcd-1ab8-460f-b810-ed22cae61a1e
         * fileName : 微信图片_20170818202313.psd
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
         * opTime : 2017-09-07 08:41
         * title : 1.潘序东提出申请
         * opstate : 1
         * name : 潘序东
         * opId : 3d07ff7a-07c7-4d68-b801-d50ad5f82132
         * opImg : http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg
         */

        private String opTime;
        private String title;
        private String opstate;
        private String name;
        private String opId;
        private String opImg;

        public DataBean(String opTime, String title, String opstate, String name, String opId, String opImg) {
            this.opTime = opTime;
            this.title = title;
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
