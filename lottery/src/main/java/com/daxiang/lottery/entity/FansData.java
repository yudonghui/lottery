package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/26.
 */

public class FansData {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"totalRecords":1,"totalPages":1,"pageIndex":1,"pageSize":20,"list":[{"headImg":"http://res.51caixiangtest.com/avatar/7120516281200035.png","isCertified":1,"focusFlag":0,"fansUserId":7120516281200035,"userName":"dongshuo"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * totalRecords : 1
         * totalPages : 1
         * pageIndex : 1
         * pageSize : 20
         * list : [{"headImg":"http://res.51caixiangtest.com/avatar/7120516281200035.png","isCertified":1,"focusFlag":0,"fansUserId":7120516281200035,"userName":"dongshuo"}]
         */

        private int totalRecords;
        private int totalPages;
        private int pageIndex;
        private int pageSize;
        private List<ListBean> list;

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * headImg : http://res.51caixiangtest.com/avatar/7120516281200035.png
             * isCertified : 1
             * focusFlag : 0
             * fansUserId : 7120516281200035
             * userName : dongshuo
             */

            private String headImg;
            private String isCertified;
            private int focusFlag;
            private String fansUserId;
            private String userName;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getIsCertified() {
                return isCertified;
            }

            public void setIsCertified(String isCertified) {
                this.isCertified = isCertified;
            }

            public int getFocusFlag() {
                return focusFlag;
            }

            public void setFocusFlag(int focusFlag) {
                this.focusFlag = focusFlag;
            }

            public String getFansUserId() {
                return fansUserId;
            }

            public void setFansUserId(String fansUserId) {
                this.fansUserId = fansUserId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }

}
