package com.daxiang.lottery.entity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/7/6
 * @describe May the Buddha bless bug-free!!!
 */
public class ZhuihaoDetailBean  {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"totalRecords":1,"totalPages":1,"pageIndex":1,"pageSize":5,"list":[{"orderId":7070616113400019,"orderCode":"ZH20170706162307_3996486","issueNo":"2017078","orderStatus":500,"winStatus":null,"aftertaxBonus":null}]}
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
         * pageSize : 5
         * list : [{"orderId":7070616113400019,"orderCode":"ZH20170706162307_3996486","issueNo":"2017078","orderStatus":500,"winStatus":null,"aftertaxBonus":null}]
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
             * orderId : 7070616113400019
             * orderCode : ZH20170706162307_3996486
             * issueNo : 2017078
             * orderStatus : 500
             * winStatus : null
             * aftertaxBonus : null
             */

            private String orderId;
            private String orderCode;
            private String issueNo;
            private String orderStatus;
            private String winStatus;
            private String aftertaxBonus;
            private String totalPrice;

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getIssueNo() {
                return issueNo;
            }

            public void setIssueNo(String issueNo) {
                this.issueNo = issueNo;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getWinStatus() {
                return winStatus;
            }

            public void setWinStatus(String winStatus) {
                this.winStatus = winStatus;
            }

            public String getAftertaxBonus() {
                return aftertaxBonus;
            }

            public void setAftertaxBonus(String aftertaxBonus) {
                this.aftertaxBonus = aftertaxBonus;
            }
        }
    }
}
