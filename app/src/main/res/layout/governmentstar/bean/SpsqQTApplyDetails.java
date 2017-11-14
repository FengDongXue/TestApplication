package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/29.
 */

public class SpsqQTApplyDetails {


    /**
     * deptName : 蓟州区工业和信息化委员会-人事科
     * files : [{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170929/9b14c10e-d471-432c-9aad-ccfab4c360d4/工作-资质印章申请-用印申请_PxCook.png","filePreView":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=3e94ade1-9379-47a5-a10a-e2ae3f71cb74","fileName":"工作-资质印章申请-用印申请_PxCook.png"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170929/9b14c10e-d471-432c-9aad-ccfab4c360d4/工作-资质印章申请-用印申请_PxCook - 副本 - 副本.png","filePreView":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=a040080d-875a-437c-9efd-a2be8439f079","fileName":"工作-资质印章申请-用印申请_PxCook - 副本 - 副本.png"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170929/9b14c10e-d471-432c-9aad-ccfab4c360d4/工作-我的申请_PxCook.png","filePreView":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=903a9be4-20ad-44a2-b587-83582f9ee64c","fileName":"工作-我的申请_PxCook.png"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170929/9b14c10e-d471-432c-9aad-ccfab4c360d4/工作-资质印章申请-用印申请_PxCook - 副本 (2).png","filePreView":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=79866793-7edd-47d0-855e-3b9a2c6dd509","fileName":"工作-资质印章申请-用印申请_PxCook - 副本 (2).png"},{"filePath":"http://121.42.29.226:80/uploadFile/mk-spsq-shlc/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170929/9b14c10e-d471-432c-9aad-ccfab4c360d4/工作-资质印章申请-用印申请_PxCook - 副本.png","filePreView":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=86273767-6ceb-4885-b496-67e1a0a4f037","fileName":"工作-资质印章申请-用印申请_PxCook - 副本.png"}]
     * opTime : 2017-09-29 14:59
     * person : 张弘
     * BT : BT
     * personImage : http://121.42.29.226:80/resources/images/main/default.jpg
     * data : [{"opTime":"2017-09-29 14:59","title":"1.张弘提出申请","no":1,"opstate":"0","name":"张弘","opId":"4e4cc5b0-5307-43b2-8b58-f68402c0fbd0","opImg":"http://121.42.29.226:80/resources/images/main/default.jpg"}]
     * CSRlist : 于艳如
     * otherUniversalReason : 大锅饭大概f'd'g
     * applicationNo : 01270501201709290009
     * otherUniversalDetail : 到时很过分的话电费太贵梵蒂冈地方
     * type : 其他申请
     * fileNum : 5
     * childType : 通用审批
     * typeId : 0501
     */

    private String deptName;
    private String opTime;
    private String person;
    private String personImage;
    private String CSRlist;
    private String otherUniversalReason;
    private String applicationNo;
    private String otherUniversalDetail;
    private String docContent;
    private String type;
    private String BT;
    private int fileNum;
    private String childType;
    private String typeId;
    private List<FilesBean> files;
    private List<DataBean> data;

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

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

    public String getCSRlist() {
        return CSRlist;
    }

    public void setCSRlist(String CSRlist) {
        this.CSRlist = CSRlist;
    }

    public String getOtherUniversalReason() {
        return otherUniversalReason;
    }

    public void setOtherUniversalReason(String otherUniversalReason) {
        this.otherUniversalReason = otherUniversalReason;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getOtherUniversalDetail() {
        return otherUniversalDetail;
    }

    public void setOtherUniversalDetail(String otherUniversalDetail) {
        this.otherUniversalDetail = otherUniversalDetail;
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
         * filePath : http://121.42.29.226:80/uploadFile/mk-spsq-shlc/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170929/9b14c10e-d471-432c-9aad-ccfab4c360d4/工作-资质印章申请-用印申请_PxCook.png
         * filePreView : http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=3e94ade1-9379-47a5-a10a-e2ae3f71cb74
         * fileName : 工作-资质印章申请-用印申请_PxCook.png
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
         * opTime : 2017-09-29 14:59
         * title : 1.张弘提出申请
         * no : 1
         * opstate : 0
         * name : 张弘
         * opId : 4e4cc5b0-5307-43b2-8b58-f68402c0fbd0
         * opImg : http://121.42.29.226:80/resources/images/main/default.jpg
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
