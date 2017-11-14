package com.lanwei.governmentstar.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/1.
 */

public class Return_Nizhi {
    /**
     * data : {"opId":"e97c548d-5210-4426-8ae2-83c3eae6f694","gwzh":"津蓟工信办 〔2017〕6号","docCode":"1000D010020170905/0001","docGzlx":"决议","docGwzt":"应急管理","isHq":"会签文件","hqLx":"机关下部门会签","opCreateTime":"2017/09/05 08:44","opBjTime":"2017/09/05 08:50","docQcrName":"张弘","docShName":"马季鸿","docSyName":"李孟和","docJdName":"潘序东","docQfName":"黄兆广","docHqName":"马季鸿、赵庆录","docHfName":"潘序东","docUrl":"http://121.42.29.226:80/docHtml/docHtml.jsp?opId=e97c548d-5210-4426-8ae2-83c3eae6f694&type=gwnz","flowList":[{"flowStatus":"1","flowName":"张弘","flowImageUrl":"http://121.42.29.226:80/resources/images/main/default.jpg","flowContent":"拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试","flowTime":"2017年09月05日 08:44"},{"flowStatus":"2","flowName":"马季鸿","flowImageUrl":"http://121.42.29.226:80/resources/images/head/f11c95f4-eda9-4768-9c3a-c4b4c4bf3826_20170814173009623.jpg","flowContent":"士大夫大师傅似的十多个","flowTime":"2017年09月05日 08:45"},{"flowStatus":"3","flowName":"李孟和","flowImageUrl":"http://121.42.29.226:80/resources/images/main/default.jpg","flowContent":"倒萨阿斯顿","flowTime":"2017年09月05日 08:45"},{"flowStatus":"4","flowName":"潘序东","flowImageUrl":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg","flowContent":"校对通过","flowTime":"2017年09月05日 08:46"},{"flowStatus":"5","flowName":"黄兆广","flowImageUrl":"http://121.42.29.226:80/resources/images/head/9695dca5-def1-46fd-9971-fe0985abcb26_20170812000250068.jpg","flowContent":"签发通过","flowTime":"2017年09月05日 08:46"},{"flowStatus":"6","flowName":"赵庆录","flowImageUrl":"http://121.42.29.226:80/resources/images/main/default.jpg","flowContent":"已悉，同意会签。","flowTime":"2017年09月05日 08:48"},{"flowStatus":"6","flowName":"马季鸿","flowImageUrl":"http://121.42.29.226:80/resources/images/head/f11c95f4-eda9-4768-9c3a-c4b4c4bf3826_20170814173009623.jpg","flowContent":"同意会签。","flowTime":"2017年09月05日 08:48"},{"flowStatus":"7","flowName":"潘序东","flowImageUrl":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg","flowContent":"核发通过","flowTime":"2017年09月05日 08:50"}],"fileList":[{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/附件002.doc","opName":"附件002.doc","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=a484b89e-7846-47b4-a6b4-028c06ea0e2d"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/服务协议.docx","opName":"服务协议.docx","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=451cfaf2-0a57-4e53-ace6-3bb6db4151e1"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/中 国 好名字004 - 副本 - 副本 (2).jpg","opName":"中 国 好名字004 - 副本 - 副本 (2).jpg","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=71c1e512-4cdc-4c07-ab5a-541b86d41979"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/公文.doc","opName":"公文.doc","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=d06b59f2-87f9-4e0e-b487-2d5d51337f81"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/十二生肖属相查询表.doc","opName":"十二生肖属相查询表.doc","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=879b2ddc-3ccc-4629-ad8e-49941c7bdc6f"}]}
     * gxState : {"share-status-01":"无条件共享","share-status-02":"有条件共享","share-status-03":"不共享"}
     * deptMap : [{"opId":"zfjg","opName":"政府机关","childList":[{"0127":"蓟州区工业和信息化委员会","0154":"蓟州区下窝头镇人民政府","0155":"蓟州区卫生和计划生育委员会"}]},{"opId":"sydw","opName":"事业单位","childList":[{"0239":"蓟州区人民政府献血办公室","0201":"蓟州区人民医院","0202":"蓟州区中医医院"}]}]
     */

    private DataBean data;
    private Map<String ,String> gxState;
    private List<DeptMapBean> deptMap;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Map<String, String> getGxState() {
        return gxState;
    }

    public void setGxState(Map<String, String> gxState) {
        this.gxState = gxState;
    }

    public List<DeptMapBean> getDeptMap() {
        return deptMap;
    }

    public void setDeptMap(List<DeptMapBean> deptMap) {
        this.deptMap = deptMap;
    }

    public static class DataBean {
        /**
         * opId : e97c548d-5210-4426-8ae2-83c3eae6f694
         * gwzh : 津蓟工信办 〔2017〕6号
         * docCode : 1000D010020170905/0001
         * docGzlx : 决议
         * docGwzt : 应急管理
         * isHq : 会签文件
         * hqLx : 机关下部门会签
         * opCreateTime : 2017/09/05 08:44
         * opBjTime : 2017/09/05 08:50
         * docQcrName : 张弘
         * docShName : 马季鸿
         * docSyName : 李孟和
         * docJdName : 潘序东
         * docQfName : 黄兆广
         * docHqName : 马季鸿、赵庆录
         * docHfName : 潘序东
         * docUrl : http://121.42.29.226:80/docHtml/docHtml.jsp?opId=e97c548d-5210-4426-8ae2-83c3eae6f694&type=gwnz
         * flowList : [{"flowStatus":"1","flowName":"张弘","flowImageUrl":"http://121.42.29.226:80/resources/images/main/default.jpg","flowContent":"拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试","flowTime":"2017年09月05日 08:44"},{"flowStatus":"2","flowName":"马季鸿","flowImageUrl":"http://121.42.29.226:80/resources/images/head/f11c95f4-eda9-4768-9c3a-c4b4c4bf3826_20170814173009623.jpg","flowContent":"士大夫大师傅似的十多个","flowTime":"2017年09月05日 08:45"},{"flowStatus":"3","flowName":"李孟和","flowImageUrl":"http://121.42.29.226:80/resources/images/main/default.jpg","flowContent":"倒萨阿斯顿","flowTime":"2017年09月05日 08:45"},{"flowStatus":"4","flowName":"潘序东","flowImageUrl":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg","flowContent":"校对通过","flowTime":"2017年09月05日 08:46"},{"flowStatus":"5","flowName":"黄兆广","flowImageUrl":"http://121.42.29.226:80/resources/images/head/9695dca5-def1-46fd-9971-fe0985abcb26_20170812000250068.jpg","flowContent":"签发通过","flowTime":"2017年09月05日 08:46"},{"flowStatus":"6","flowName":"赵庆录","flowImageUrl":"http://121.42.29.226:80/resources/images/main/default.jpg","flowContent":"已悉，同意会签。","flowTime":"2017年09月05日 08:48"},{"flowStatus":"6","flowName":"马季鸿","flowImageUrl":"http://121.42.29.226:80/resources/images/head/f11c95f4-eda9-4768-9c3a-c4b4c4bf3826_20170814173009623.jpg","flowContent":"同意会签。","flowTime":"2017年09月05日 08:48"},{"flowStatus":"7","flowName":"潘序东","flowImageUrl":"http://121.42.29.226:80/resources/images/head/d8900fc8-50fa-4757-b8a8-4ea52a76fe02_20170811112125887.jpg","flowContent":"核发通过","flowTime":"2017年09月05日 08:50"}]
         * fileList : [{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/附件002.doc","opName":"附件002.doc","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=a484b89e-7846-47b4-a6b4-028c06ea0e2d"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/服务协议.docx","opName":"服务协议.docx","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=451cfaf2-0a57-4e53-ace6-3bb6db4151e1"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/中 国 好名字004 - 副本 - 副本 (2).jpg","opName":"中 国 好名字004 - 副本 - 副本 (2).jpg","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=71c1e512-4cdc-4c07-ab5a-541b86d41979"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/公文.doc","opName":"公文.doc","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=d06b59f2-87f9-4e0e-b487-2d5d51337f81"},{"path":"http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/十二生肖属相查询表.doc","opName":"十二生肖属相查询表.doc","filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=879b2ddc-3ccc-4629-ad8e-49941c7bdc6f"}]
         */

        private String opId;
        private String gwzh;
        private String docCode;
        private String docGzlx;
        private String docGwzt;
        private String isHq;
        private String hqLx;
        private String opCreateTime;
        private String opBjTime;
        private String docQcrName;
        private String docShName;
        private String docSyName;
        private String docJdName;
        private String docQfName;
        private String docHqName;
        private String docHfName;
        private String docUrl;
        private List<FlowListBean> flowList;
        private List<FileListBean> fileList;

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getGwzh() {
            return gwzh;
        }

        public void setGwzh(String gwzh) {
            this.gwzh = gwzh;
        }

        public String getDocCode() {
            return docCode;
        }

        public void setDocCode(String docCode) {
            this.docCode = docCode;
        }

        public String getDocGzlx() {
            return docGzlx;
        }

        public void setDocGzlx(String docGzlx) {
            this.docGzlx = docGzlx;
        }

        public String getDocGwzt() {
            return docGwzt;
        }

        public void setDocGwzt(String docGwzt) {
            this.docGwzt = docGwzt;
        }

        public String getIsHq() {
            return isHq;
        }

        public void setIsHq(String isHq) {
            this.isHq = isHq;
        }

        public String getHqLx() {
            return hqLx;
        }

        public void setHqLx(String hqLx) {
            this.hqLx = hqLx;
        }

        public String getOpCreateTime() {
            return opCreateTime;
        }

        public void setOpCreateTime(String opCreateTime) {
            this.opCreateTime = opCreateTime;
        }

        public String getOpBjTime() {
            return opBjTime;
        }

        public void setOpBjTime(String opBjTime) {
            this.opBjTime = opBjTime;
        }

        public String getDocQcrName() {
            return docQcrName;
        }

        public void setDocQcrName(String docQcrName) {
            this.docQcrName = docQcrName;
        }

        public String getDocShName() {
            return docShName;
        }

        public void setDocShName(String docShName) {
            this.docShName = docShName;
        }

        public String getDocSyName() {
            return docSyName;
        }

        public void setDocSyName(String docSyName) {
            this.docSyName = docSyName;
        }

        public String getDocJdName() {
            return docJdName;
        }

        public void setDocJdName(String docJdName) {
            this.docJdName = docJdName;
        }

        public String getDocQfName() {
            return docQfName;
        }

        public void setDocQfName(String docQfName) {
            this.docQfName = docQfName;
        }

        public String getDocHqName() {
            return docHqName;
        }

        public void setDocHqName(String docHqName) {
            this.docHqName = docHqName;
        }

        public String getDocHfName() {
            return docHfName;
        }

        public void setDocHfName(String docHfName) {
            this.docHfName = docHfName;
        }

        public String getDocUrl() {
            return docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public List<FlowListBean> getFlowList() {
            return flowList;
        }

        public void setFlowList(List<FlowListBean> flowList) {
            this.flowList = flowList;
        }

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }

        public static class FlowListBean {
            /**
             * flowStatus : 1
             * flowName : 张弘
             * flowImageUrl : http://121.42.29.226:80/resources/images/main/default.jpg
             * flowContent : 拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试拟制全部阶段的测试
             * flowTime : 2017年09月05日 08:44
             */

            private String flowStatus;
            private String flowName;
            private String flowImageUrl;
            private String flowContent;
            private String flowTime;


            public FlowListBean(String flowStatus, String flowName, String flowImageUrl, String flowContent, String flowTime) {
                this.flowStatus = flowStatus;
                this.flowName = flowName;
                this.flowImageUrl = flowImageUrl;
                this.flowContent = flowContent;
                this.flowTime = flowTime;
            }

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

        public static class FileListBean {
            /**
             * path : http://121.42.29.226:80/uploadFile/mk-gwnz/ba3f0748-f3cd-49ff-8817-36c60866aa6a/file/20170905/e97c548d-5210-4426-8ae2-83c3eae6f694/附件002.doc
             * opName : 附件002.doc
             * filePreview : http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=a484b89e-7846-47b4-a6b4-028c06ea0e2d
             */

            private String path;
            private String opName;
            private String filePreview;

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

            public String getFilePreview() {
                return filePreview;
            }

            public void setFilePreview(String filePreview) {
                this.filePreview = filePreview;
            }
        }
    }

    public static class DeptMapBean {
        /**
         * opId : zfjg
         * opName : 政府机关
         * childList : [{"0127":"蓟州区工业和信息化委员会","0154":"蓟州区下窝头镇人民政府","0155":"蓟州区卫生和计划生育委员会"}]
         */

        private String opId;
        private String opName;
        private ArrayList<Map<String,String>> childList;

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

        public ArrayList<Map<String, String>> getChildList() {
            return childList;
        }

        public void setChildList(ArrayList<Map<String, String>> childList) {
            this.childList = childList;
        }
    }


}
