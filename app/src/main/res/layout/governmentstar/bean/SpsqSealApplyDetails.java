package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7/007.
 */

public class SpsqSealApplyDetails {
    /**
     * stampType : 党委公章
     * deptName : 蓟州区工业和信息化委员会-党政办公室
     * files : [{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/1f062397-b8eb-40a3-974a-95ea03d11e5b/file/20170907/3ebf710c-d599-4515-8c92-6527ceda72e6/工作-拟制-电子公文处理单查看共享类型.jpg","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=c8d0e43c-516e-4f3c-b7dc-9d5a56bc5405","fileName":"工作-拟制-电子公文处理单查看共享类型.jpg"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/1f062397-b8eb-40a3-974a-95ea03d11e5b/file/20170907/3ebf710c-d599-4515-8c92-6527ceda72e6/工作-拟制-电子公文处理单.jpg","filePreView":"http://121.42.29.226:80docHtml.jsp?opId=742bdb74-0678-4954-bf70-afde89606cb8","fileName":"工作-拟制-电子公文处理单.jpg"}]
     * opTime : 2017/09/250 08:37
     * person : 卢亚静
     * personImage : http://121.42.29.226:80/resources/images/head/1f062397-b8eb-40a3-974a-95ea03d11e5b_20170817161528861.gif
     * data : [{"opTime":"2017-09-07 08:37","title":"1.卢亚静提出申请","no":1,"opstate":"1","name":"卢亚静","opId":"619555a8-a4d1-4a4d-b92b-f3f9e70dbe84","opImg":"http://121.42.29.226:80/resources/images/head/1f062397-b8eb-40a3-974a-95ea03d11e5b_20170817161528861.gif"},{"opTime":"2017-09-07 08:37","title":"2.潘序东未批准","opstate":"0","no":1,"name":"潘序东","opId":"a20abe76-fa7d-4254-b407-00e9350802d5","opImg":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg"},{"opTime":"2017-09-07 08:37","title":"3.李孟和未批准","opstate":"0","no":2,"name":"李孟和","opId":"aa6b3264-515c-467d-9c79-ad9497771ac3","opImg":"http://121.42.29.226:80"},{"opTime":"2017-09-07 08:37","title":"4.付士明未批准","opstate":"0","no":3,"name":"付士明","opId":"bc3c0231-2e47-4504-998b-732ba9efdd1c","opImg":"http://121.42.29.226:80"},{"opTime":"2017-09-07 08:37","title":"5.张瑞明未批准","opstate":"0","no":4,"name":"张瑞明","opId":"e0869f02-71c7-4668-b1f0-70071b82dcd3","opImg":"http://121.42.29.226:80"},{"opTime":"2017-09-07 08:37","title":"6.于艳如未批准","opstate":"0","no":5,"name":"于艳如","opId":"e2a7a87f-781d-47ea-b0ad-d55977687b1a","opImg":"http://121.42.29.226:80"},{"opTime":"2017-09-07 08:37","title":"7.黄兆广未批准","opstate":"0","no":6,"name":"黄兆广","opId":"41cedf0c-e997-489d-b233-f7de78f165e6","opImg":"http://121.42.29.226:80/resources/images/head/9695dca5-def1-46fd-9971-fe0985abcb26_20170812000250068.jpg"}]
     * CSRlist : 张建民,张瑞明,李亚宾
     * stampApplicationFiletype : 采购合同
     * applicationNo : 01270301201709070003
     * stampApplicationFilenum : 1
     * type : 资质印章
     * BT : BT
     * fileNum : 2
     * stampApplicationReason : 各地纷纷大幅低估广泛大使馆的非官方的给对方是个地方国大使馆反对撒
     * childType : 用印申请
     * typeId : 0301
     * stampApplicationFilename : 地方大师傅
     */

    private String stampType;
    private String deptName;
    private String opTime;
    private String person;
    private String personImage;
    private String BT;
    private String CSRlist;
    private String stampApplicationFiletype;
    private String applicationNo;
    private String stampApplicationFilenum;
    private String type;
    private int fileNum;
    private String stampApplicationReason;
    private String childType;
    private String typeId;
    private String stampApplicationFilename;
    private List<FilesBean> files;
    private List<DataBean> data;


    public String getBT() {
        return BT;
    }

    public void setBT(String BT) {
        this.BT = BT;
    }

    public String getStampType() {
        return stampType;
    }

    public void setStampType(String stampType) {
        this.stampType = stampType;
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

    public String getCSRlist() {
        return CSRlist;
    }

    public void setCSRlist(String CSRlist) {
        this.CSRlist = CSRlist;
    }

    public String getStampApplicationFiletype() {
        return stampApplicationFiletype;
    }

    public void setStampApplicationFiletype(String stampApplicationFiletype) {
        this.stampApplicationFiletype = stampApplicationFiletype;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getStampApplicationFilenum() {
        return stampApplicationFilenum;
    }

    public void setStampApplicationFilenum(String stampApplicationFilenum) {
        this.stampApplicationFilenum = stampApplicationFilenum;
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

    public String getStampApplicationReason() {
        return stampApplicationReason;
    }

    public void setStampApplicationReason(String stampApplicationReason) {
        this.stampApplicationReason = stampApplicationReason;
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

    public String getStampApplicationFilename() {
        return stampApplicationFilename;
    }

    public void setStampApplicationFilename(String stampApplicationFilename) {
        this.stampApplicationFilename = stampApplicationFilename;
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
         * filePath : http://121.42.29.226:80/uploadFile/mk-spsq-shlc/1f062397-b8eb-40a3-974a-95ea03d11e5b/file/20170907/3ebf710c-d599-4515-8c92-6527ceda72e6/工作-拟制-电子公文处理单查看共享类型.jpg
         * filePreView : http://121.42.29.226:80docHtml.jsp?opId=c8d0e43c-516e-4f3c-b7dc-9d5a56bc5405
         * fileName : 工作-拟制-电子公文处理单查看共享类型.jpg
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
         * opTime : 2017-09-07 08:37
         * title : 1.卢亚静提出申请
         * no : 1
         * opstate : 1
         * name : 卢亚静
         * opId : 619555a8-a4d1-4a4d-b92b-f3f9e70dbe84
         * opImg : http://121.42.29.226:80/resources/images/head/1f062397-b8eb-40a3-974a-95ea03d11e5b_20170817161528861.gif
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
