package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/11.
 */

public class Wait_Item {



//    {
//        "pageCount": 1,
//            "pageNo": 1,
//            "data": [
//        {
//            "opId": "7fa9f05d-6664-4b90-9975-aea6622eaf71",
//                "docTitle": "关于×××××××××××的决议",
//                "docClassify": "\r\n财政工作",
//                "docFileNum": "无附件",
//                "docTime": "2017/04/12 15:13:56",
//                "docCode": "1000D010020170412/0001",
//                "docName": "张浩宁",
//                "docCompany": hardwork,
//                "docState": "gwnz",
//                "opState": "1"
//        },
//        {
//            "opId": "e970d2a6-8a06-4edd-addb-b7ed973ae337",
//                "docTitle": "人民政府",
//                "docClassify": "税务工作",
//                "docFileNum": "有4个附件",
//                "docTime": "2017/03/26 15:25:56",
//                "docCode": "1503F0003170326/0003",
//                "docName": hardwork,
//                "docCompany": "天津市人民政府办公厅",
//                "docState": "swcy",
//                "opState": "4"
//        }
//    ]
//    }

    private String opId;
    private String docTitle;
    private String docClassify;
    private String docFileNum;
    private String docTime;
    private String docCode;
    private String docName;
    private String docCompany;
    private String docState;
    private String opState;
    private String DocStatus;
    private String docMatter;

    public String getDocMatter() {
        return docMatter;
    }

    public void setDocMatter(String docMatter) {
        this.docMatter = docMatter;
    }

    public String getDocStatus() {
        return DocStatus;
    }

    public void setDocStatus(String docStatus) {
        DocStatus = docStatus;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocClassify() {
        return docClassify;
    }

    public void setDocClassify(String docClassify) {
        this.docClassify = docClassify;
    }

    public String getDocFileNum() {
        return docFileNum;
    }

    public void setDocFileNum(String docFileNum) {
        this.docFileNum = docFileNum;
    }

    public String getDocTime() {
        return docTime;
    }

    public void setDocTime(String docTime) {
        this.docTime = docTime;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocCompany() {
        return docCompany;
    }

    public void setDocCompany(String docCompany) {
        this.docCompany = docCompany;
    }

    public String getDocState() {
        return docState;
    }

    public void setDocState(String docState) {
        this.docState = docState;
    }

    public String getOpState() {
        return opState;
    }

    public void setOpState(String opState) {
        this.opState = opState;
    }
}
