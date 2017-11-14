package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/10.
 */

public class Return_Finish {

    //    {
//        "data": {
//                "opId": "70bbec48-69c4-4344-97de-b506e5210c7a",
//                "docUnit": "天津市发展和改革委员会",
//                "opCreateTime": "2017/03/27 20:57",
//                "docTheme": "政务督查",
//                "docUrgent": "一般公文",
//                "docUrl": hardwork,
//                "docStatus": hardwork,
//                "opNbrId": "7a57752e-1000-401d-a6b0-c3a0015d10bb",
//                "docType": "img",
//                "flowList": [
//                              {
//                                "flowStatus": "1",
//                                  "flowName": "张浩宁",
//                                    "flowImageUrl": "http://121.42.29.226:80/resources/images/head/18537d2f-5466-4279-b4be-ab66e2c96d7b_20170409090707118.png",
//                                     "flowContent": "totti",
//                                       "flowTime": "2017年03月27日 20:57"
//                                  }
//                               ],
//        "fileList":[{"path":"http://121.42.29.226:80/uploadFile/mk-xtgl-swcy/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170407/edfb6ba2-dc37-4e4a-9c8a-ef97719cce90/撒打发.zip","opName":"撒打发.zip"}],
//        "imageList": [
//        {
//            "path": "http://121.42.29.226:80/uploadFile/mk-xtgl-swcy/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/timg.jpg",
//                "opName": "timg.jpg"
//        }
//        ]
//    }
//    }



//    {
//        "data": {
//                "opId": "9a39087a-578f-42c2-9a2f-e3d03bae7e38",
//                "docUnit": "天津市人民政府",
//                "opCreateTime": "2017/04/24 18:05",
//                "docTheme": "政务督查",
//                "docUrgent": "一般公文",
//                "docUrl": "http://121.42.29.226:80/uploadFile/mk-xtgl-swcy/18537d2f-5466-4279-b4be-ab66e2c96d7b/swcy-ordinary-1/swcy-ordinary-1_20170424180456715.doc",
//                "docStatus": hardwork,
//                "opNbrId": "7a57752e-1000-401d-a6b0-c3a0015d10bb",
//                "docType": "doc",
//                "flowList": hardwork,
//                "fileList": [
//        {
//            "path": "http://121.42.29.226:80/uploadFile/mk-xtgl-swcy/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170424/9a39087a-578f-42c2-9a2f-e3d03bae7e38/card01.png",
//                "opName": "card01.png"
//        }
//        ],
//        "imageList": hardwork
//    }
//    }




    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        private String opId;
        private String docUnit;
        private String opCreateTime;
        private String docTheme;
        private String docUrgent;
        private String docUrl;
        private String docTitle;
        private String docStatus;
        private String opNbrId;
        private String docType;
        private String opType;
        private ArrayList<Flowlist> flowList;
        private ArrayList<Filelist> fileList;
        private ArrayList<Imagelist> imageList;


        public String getOpType() {
            return opType;
        }

        public void setOpType(String opType) {
            this.opType = opType;
        }

        public String getDocTitle() {
            return docTitle;
        }

        public void setDocTitle(String docTitle) {
            this.docTitle = docTitle;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getDocUnit() {
            return docUnit;
        }

        public void setDocUnit(String docUnit) {
            this.docUnit = docUnit;
        }

        public String getOpCreateTime() {
            return opCreateTime;
        }

        public void setOpCreateTime(String opCreateTime) {
            this.opCreateTime = opCreateTime;
        }

        public String getDocTheme() {
            return docTheme;
        }

        public void setDocTheme(String docTheme) {
            this.docTheme = docTheme;
        }

        public String getDocUrgent() {
            return docUrgent;
        }

        public void setDocUrgent(String docUrgent) {
            this.docUrgent = docUrgent;
        }

        public String getDocUrl() {
            return docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public String getOpNbrId() {
            return opNbrId;
        }

        public void setOpNbrId(String opNbrId) {
            this.opNbrId = opNbrId;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public ArrayList<Flowlist> getFlowList() {
            return flowList;
        }

        public void setFlowList(ArrayList<Flowlist> flowList) {
            this.flowList = flowList;
        }

        public ArrayList<Filelist> getFileList() {
            return fileList;
        }

        public void setFileList(ArrayList<Filelist> fileList) {
            this.fileList = fileList;
        }

        public ArrayList<Imagelist> getImageList() {
            return imageList;
        }

        public void setImageList(ArrayList<Imagelist> imageList) {
            this.imageList = imageList;
        }
    }



    public class Flowlist {


//                                "flowStatus": "1",
//                                  "flowName": "张浩宁",
//                                    "flowImageUrl": "http://121.42.29.226:80/resources/images/head/18537d2f-5466-4279-b4be-ab66e2c96d7b_20170409090707118.png",
//                                     "flowContent": "totti",
//                                       "flowTime": "2017年03月27日 20:57"
//

        private String flowStatus;
        private String flowName;
        private String flowImageUrl;
        private String flowContent;
        private String flowTime;

        public String getFlowStatus() {
            return flowStatus;
        }

        public void setFlowStatus(String flowStatus) {
            this.flowStatus = flowStatus;
        }

        public String getFlowName() {
            return flowName;
        }

        public void setFlowName(String flowName) {
            this.flowName = flowName;
        }

        public String getFlowImageUrl() {
            return flowImageUrl;
        }

        public void setFlowImageUrl(String flowImageUrl) {
            this.flowImageUrl = flowImageUrl;
        }

        public String getFlowContent() {
            return flowContent;
        }

        public void setFlowContent(String flowContent) {
            this.flowContent = flowContent;
        }

        public String getFlowTime() {
            return flowTime;
        }

        public void setFlowTime(String flowTime) {
            this.flowTime = flowTime;
        }
    }

     public  class Imagelist{
//        "path": "http://121.42.29.226:80/uploadFile/mk-xtgl-swcy/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/timg.jpg",
//                "opName": "timg.jpg"

        private String path;
        private String opName;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }
    }

    public class Filelist{
   //      "fileList":[{"path":"http://121.42.29.226:80/uploadFile/mk-xtgl-swcy/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170407/edfb6ba2-dc37-4e4a-9c8a-ef97719cce90/撒打发.zip",
        // "opName":"撒打发.zip"}],
   private String path;
   private String opName;
        private String filePreview;

        public Filelist(String path, String opName, String filePreview) {
            this.path = path;
            this.opName = opName;
            this.filePreview = filePreview;
        }

        public String getFilePreview() {
            return filePreview;
        }

        public void setFilePreview(String filePreview) {
            this.filePreview = filePreview;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }
    }

}
