package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by YL on 2017/6/5.
 */

public class MailDetails {

    /**
     * data : {"fileList":[{"filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=b7e852e8-28eb-4aaf-87b9-5a6a4ee733ee","opName":"2.jpg","path":"http://121.42.29.226:80/uploadFile/mk-xtgl-zwyx/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170531/afd86d11-6b18-4345-9397-156348a7b2a9/2.jpg"}],"mailAttn":"吴晓军","mailDate":"2017/05/31 17:33","mailSend":"张浩宁","mailTitle":"测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测","mailUrl":"http://121.42.29.226:80/docHtml/mailHtml.jsp?opId=afd86d11-6b18-4345-9397-156348a7b2a9","opId":"afd86d11-6b18-4345-9397-156348a7b2a9"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        @Override
        public String toString() {
            return "DataBean{" +
                    "mailAttn='" + mailAttn + '\'' +
                    ", mailDate='" + mailDate + '\'' +
                    ", mailSend='" + mailSend + '\'' +
                    ", mailTitle='" + mailTitle + '\'' +
                    ", mailUrl='" + mailUrl + '\'' +
                    ", opId='" + opId + '\'' +
                    ", fileList=" + fileList +
                    '}';
        }

        /**
         * fileList : [{"filePreview":"http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=b7e852e8-28eb-4aaf-87b9-5a6a4ee733ee","opName":"2.jpg","path":"http://121.42.29.226:80/uploadFile/mk-xtgl-zwyx/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170531/afd86d11-6b18-4345-9397-156348a7b2a9/2.jpg"}]
         * mailAttn : 吴晓军
         * mailDate : 2017/05/31 17:33
         * mailSend : 张浩宁
         * mailTitle : 测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测试8测
         * mailUrl : http://121.42.29.226:80/docHtml/mailHtml.jsp?opId=afd86d11-6b18-4345-9397-156348a7b2a9
         * opId : afd86d11-6b18-4345-9397-156348a7b2a9
         */

        private String mailAttn;
        private String mailDate;
        private String mailSend;
        private String mailTitle;
        private String mailUrl;
        private String opId;
        private List<FileListBean> fileList;

        public String getMailAttn() {
            return mailAttn;
        }

        public void setMailAttn(String mailAttn) {
            this.mailAttn = mailAttn;
        }

        public String getMailDate() {
            return mailDate;
        }

        public void setMailDate(String mailDate) {
            this.mailDate = mailDate;
        }

        public String getMailSend() {
            return mailSend;
        }

        public void setMailSend(String mailSend) {
            this.mailSend = mailSend;
        }

        public String getMailTitle() {
            return mailTitle;
        }

        public void setMailTitle(String mailTitle) {
            this.mailTitle = mailTitle;
        }

        public String getMailUrl() {
            return mailUrl;
        }

        public void setMailUrl(String mailUrl) {
            this.mailUrl = mailUrl;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }

        public static class FileListBean {
            @Override
            public String toString() {
                return "FileListBean{" +
                        "filePreview='" + filePreview + '\'' +
                        ", opName='" + opName + '\'' +
                        ", path='" + path + '\'' +
                        '}';
            }

            /**
             * filePreview : http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=b7e852e8-28eb-4aaf-87b9-5a6a4ee733ee
             * opName : 2.jpg
             * path : http://121.42.29.226:80/uploadFile/mk-xtgl-zwyx/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170531/afd86d11-6b18-4345-9397-156348a7b2a9/2.jpg
             */



            private String filePreview;
            private String opName;
            private String path;

            public String getFilePreview() {
                return filePreview;
            }

            public void setFilePreview(String filePreview) {
                this.filePreview = filePreview;
            }

            public String getOpName() {
                return opName;
            }

            public void setOpName(String opName) {
                this.opName = opName;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
