package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class JoinData {

    /**
     * code : 0
     * msg : 获取推荐订单参与信息成功
     * data : {"totalRecords":2,"totalPages":1,"pageIndex":1,"pageSize":10,"list":[{"aftertaxBonus":null,"buyNum":null,"betNum":"4","totalPrice":"8.00","winStatus":null,"betMulti":"1","realCost":"8.00","betTime":"2017-03-02 09:01:33","orderStatus":"500","userName":"CX_821161hbz","userId":"2017020610243700427"},{"aftertaxBonus":null,"buyNum":null,"betNum":"4","totalPrice":"8.00","winStatus":null,"betMulti":"1","realCost":"8.00","betTime":"2017-03-02 09:01:28","orderStatus":"500","userName":"CX_821161hbz","userId":"2017020610243700427"}]}
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
         * totalRecords : 2
         * totalPages : 1
         * pageIndex : 1
         * pageSize : 10
         * list : [{"aftertaxBonus":null,"buyNum":null,"betNum":"4","totalPrice":"8.00","winStatus":null,"betMulti":"1","realCost":"8.00","betTime":"2017-03-02 09:01:33","orderStatus":"500","userName":"CX_821161hbz","userId":"2017020610243700427"},{"aftertaxBonus":null,"buyNum":null,"betNum":"4","totalPrice":"8.00","winStatus":null,"betMulti":"1","realCost":"8.00","betTime":"2017-03-02 09:01:28","orderStatus":"500","userName":"CX_821161hbz","userId":"2017020610243700427"}]
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
             * aftertaxBonus : null
             * buyNum : null
             * betNum : 4
             * totalPrice : 8.00
             * winStatus : null
             * betMulti : 1
             * realCost : 8.00
             * betTime : 2017-03-02 09:01:33
             * orderStatus : 500
             * userName : CX_821161hbz
             * userId : 2017020610243700427
             */

            private String aftertaxBonus;
            private String buyNum;
            private String betNum;
            private String totalPrice;
            private String winStatus;
            private String betMulti;
            private String realCost;
            private String betTime;
            private String orderStatus;
            private String userName;
            private String userId;

            public String getAftertaxBonus() {
                return aftertaxBonus;
            }

            public void setAftertaxBonus(String aftertaxBonus) {
                this.aftertaxBonus = aftertaxBonus;
            }

            public String getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(String buyNum) {
                this.buyNum = buyNum;
            }

            public String getBetNum() {
                return betNum;
            }

            public void setBetNum(String betNum) {
                this.betNum = betNum;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getWinStatus() {
                return winStatus;
            }

            public void setWinStatus(String winStatus) {
                this.winStatus = winStatus;
            }

            public String getBetMulti() {
                return betMulti;
            }

            public void setBetMulti(String betMulti) {
                this.betMulti = betMulti;
            }

            public String getRealCost() {
                return realCost;
            }

            public void setRealCost(String realCost) {
                this.realCost = realCost;
            }

            public String getBetTime() {
                return betTime;
            }

            public void setBetTime(String betTime) {
                this.betTime = betTime;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
