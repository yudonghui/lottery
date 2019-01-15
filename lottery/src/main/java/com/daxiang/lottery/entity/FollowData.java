package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/26.
 */

public class FollowData {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"totalRecords":3,"totalPages":1,"pageIndex":1,"pageSize":20,"list":[{"headImg":null,"isCertified":0,"focusUserId":7081421480900011,"userName":"凤凰涅槃","canBuy":0},{"headImg":"http://res.51caixiangtest.com/avatar/7120516281200035.png","isCertified":1,"focusUserId":7120516281200035,"userName":"dongshuo","canBuy":0},{"headImg":"http://res.51caixiangtest.com/avatar/7091117051800005.gif","isCertified":0,"focusUserId":7091117051800005,"userName":"魑魅魍魉","canBuy":0}]}
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
         * totalRecords : 3
         * totalPages : 1
         * pageIndex : 1
         * pageSize : 20
         * list : [{"headImg":null,"isCertified":0,"focusUserId":7081421480900011,"userName":"凤凰涅槃","canBuy":0},{"headImg":"http://res.51caixiangtest.com/avatar/7120516281200035.png","isCertified":1,"focusUserId":7120516281200035,"userName":"dongshuo","canBuy":0},{"headImg":"http://res.51caixiangtest.com/avatar/7091117051800005.gif","isCertified":0,"focusUserId":7091117051800005,"userName":"魑魅魍魉","canBuy":0}]
         */

        private int totalRecords;
        private int totalPages;
        private String pageIndex;
        private String pageSize;
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

        public String getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(String pageIndex) {
            this.pageIndex = pageIndex;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
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
             * headImg : null
             * isCertified : 0
             * focusUserId : 7081421480900011
             * userName : 凤凰涅槃
             * canBuy : 0
             */

            private String headImg;
            private String isCertified;
            private String focusUserId;
            private String userName;
            private String canBuy;

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

            public String getFocusUserId() {
                return focusUserId;
            }

            public void setFocusUserId(String focusUserId) {
                this.focusUserId = focusUserId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getCanBuy() {
                return canBuy;
            }

            public void setCanBuy(String canBuy) {
                this.canBuy = canBuy;
            }
        }
    }
}
