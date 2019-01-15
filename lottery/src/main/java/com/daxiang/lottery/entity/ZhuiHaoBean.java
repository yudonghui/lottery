package com.daxiang.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author yudonghui
 * @date 2017/7/6
 * @describe May the Buddha bless bug-free!!!
 */
public class ZhuiHaoBean implements Serializable {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"totalRecords":13,"totalPages":2,"pageIndex":1,"pageSize":10,"list":[{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"0.00","totalMoney":"4.00","stopType":"1","zhuihaoId":"7070610205700003","Remark":"追号完成后停止。","lotCode":"23529","totalNum":"2","createTime":"2017-07-06 10:39:55","orderCode":"ZH20170706103954_2467895","boughtNum":"1","betContent":"02,16,14,24,32|11,03:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"200.00","totalMoney":"30.00","stopType":"2","zhuihaoId":"7070611081900001","Remark":"累计中奖满200.00即停止。","lotCode":"23529","totalNum":"5","createTime":"2017-07-06 11:10:05","orderCode":"ZH20170706111004_0221244","boughtNum":"1","betContent":"11,16,04,32,01|01,12:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"50.00","totalMoney":"220.00","stopType":"1","zhuihaoId":"7070611081900003","Remark":"追号完成后停止。","lotCode":"23529","totalNum":"10","createTime":"2017-07-06 11:11:25","orderCode":"ZH20170706111124_2658481","boughtNum":"1","betContent":"10,25,27,05,31|03,05:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"50.00","totalMoney":"330.00","stopType":"2","zhuihaoId":"7070611081900005","Remark":"累计中奖满50.00即停止。","lotCode":"23529","totalNum":"10","createTime":"2017-07-06 11:11:39","orderCode":"ZH20170706111138_2839651","boughtNum":"1","betContent":"10,25,27,05,31|03,05:2:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"0.00","totalMoney":"4.00","stopType":"2","zhuihaoId":"7070611484600006","Remark":"中奖即停止。","lotCode":"23529","totalNum":"2","createTime":"2017-07-06 13:44:26","orderCode":"ZH20170706134425_2831004","boughtNum":"1","betContent":"18,35,22,31,12|08,02:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"6.00","stopType":"2","zhuihaoId":"7070616113400003","Remark":"累计中奖满100.00即停止。","lotCode":"21406","totalNum":"3","createTime":"2017-07-06 16:16:09","orderCode":"ZH20170706161608_9856766","boughtNum":"1","betContent":"10,11:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"6.00","stopType":"1","zhuihaoId":"7070616113400006","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"3","createTime":"2017-07-06 16:19:04","orderCode":"ZH20170706161903_9633182","boughtNum":"1","betContent":"09,06:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"20.00","stopType":"1","zhuihaoId":"7070616113400008","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"10","createTime":"2017-07-06 16:19:15","orderCode":"ZH20170706161914_6148952","boughtNum":"1","betContent":"09,06:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"6.00","stopType":"1","zhuihaoId":"7070616113400012","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"3","createTime":"2017-07-06 16:20:32","orderCode":"ZH20170706162031_2461742","boughtNum":"1","betContent":"01,06:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"20.00","stopType":"1","zhuihaoId":"7070616113400014","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"10","createTime":"2017-07-06 16:20:48","orderCode":"ZH20170706162047_2344266","boughtNum":"1","betContent":"01,06:02:01"}]}
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

    public static class DataBean implements Serializable {
        /**
         * totalRecords : 13
         * totalPages : 2
         * pageIndex : 1
         * pageSize : 10
         * list : [{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"0.00","totalMoney":"4.00","stopType":"1","zhuihaoId":"7070610205700003","Remark":"追号完成后停止。","lotCode":"23529","totalNum":"2","createTime":"2017-07-06 10:39:55","orderCode":"ZH20170706103954_2467895","boughtNum":"1","betContent":"02,16,14,24,32|11,03:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"200.00","totalMoney":"30.00","stopType":"2","zhuihaoId":"7070611081900001","Remark":"累计中奖满200.00即停止。","lotCode":"23529","totalNum":"5","createTime":"2017-07-06 11:10:05","orderCode":"ZH20170706111004_0221244","boughtNum":"1","betContent":"11,16,04,32,01|01,12:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"50.00","totalMoney":"220.00","stopType":"1","zhuihaoId":"7070611081900003","Remark":"追号完成后停止。","lotCode":"23529","totalNum":"10","createTime":"2017-07-06 11:11:25","orderCode":"ZH20170706111124_2658481","boughtNum":"1","betContent":"10,25,27,05,31|03,05:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"50.00","totalMoney":"330.00","stopType":"2","zhuihaoId":"7070611081900005","Remark":"累计中奖满50.00即停止。","lotCode":"23529","totalNum":"10","createTime":"2017-07-06 11:11:39","orderCode":"ZH20170706111138_2839651","boughtNum":"1","betContent":"10,25,27,05,31|03,05:2:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"0.00","totalMoney":"4.00","stopType":"2","zhuihaoId":"7070611484600006","Remark":"中奖即停止。","lotCode":"23529","totalNum":"2","createTime":"2017-07-06 13:44:26","orderCode":"ZH20170706134425_2831004","boughtNum":"1","betContent":"18,35,22,31,12|08,02:1:1"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"6.00","stopType":"2","zhuihaoId":"7070616113400003","Remark":"累计中奖满100.00即停止。","lotCode":"21406","totalNum":"3","createTime":"2017-07-06 16:16:09","orderCode":"ZH20170706161608_9856766","boughtNum":"1","betContent":"10,11:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"6.00","stopType":"1","zhuihaoId":"7070616113400006","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"3","createTime":"2017-07-06 16:19:04","orderCode":"ZH20170706161903_9633182","boughtNum":"1","betContent":"09,06:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"20.00","stopType":"1","zhuihaoId":"7070616113400008","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"10","createTime":"2017-07-06 16:19:15","orderCode":"ZH20170706161914_6148952","boughtNum":"1","betContent":"09,06:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"6.00","stopType":"1","zhuihaoId":"7070616113400012","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"3","createTime":"2017-07-06 16:20:32","orderCode":"ZH20170706162031_2461742","boughtNum":"1","betContent":"01,06:02:01"},{"zhuihaoStatus":"200","winMoney":"0.00","stopPrize":"100.00","totalMoney":"20.00","stopType":"1","zhuihaoId":"7070616113400014","Remark":"追号完成后停止。","lotCode":"21406","totalNum":"10","createTime":"2017-07-06 16:20:48","orderCode":"ZH20170706162047_2344266","boughtNum":"1","betContent":"01,06:02:01"}]
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

        public static class ListBean implements Serializable {
            /**
             * zhuihaoStatus : 200
             * winMoney : 0.00
             * stopPrize : 0.00
             * totalMoney : 4.00
             * stopType : 1
             * zhuihaoId : 7070610205700003
             * Remark : 追号完成后停止。
             * lotCode : 23529
             * totalNum : 2
             * createTime : 2017-07-06 10:39:55
             * orderCode : ZH20170706103954_2467895
             * boughtNum : 1
             * betContent : 02,16,14,24,32|11,03:1:1
             */

            private String zhuihaoStatus;
            private String winMoney;
            private String stopPrize;
            private String totalMoney;
            private String stopType;
            private String zhuihaoId;
            private String Remark;
            private String lotCode;
            private String totalNum;
            private String createTime;
            private String orderCode;
            private String boughtNum;
            private String betContent;

            public String getZhuihaoStatus() {
                return zhuihaoStatus;
            }

            public void setZhuihaoStatus(String zhuihaoStatus) {
                this.zhuihaoStatus = zhuihaoStatus;
            }

            public String getWinMoney() {
                return winMoney;
            }

            public void setWinMoney(String winMoney) {
                this.winMoney = winMoney;
            }

            public String getStopPrize() {
                return stopPrize;
            }

            public void setStopPrize(String stopPrize) {
                this.stopPrize = stopPrize;
            }

            public String getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(String totalMoney) {
                this.totalMoney = totalMoney;
            }

            public String getStopType() {
                return stopType;
            }

            public void setStopType(String stopType) {
                this.stopType = stopType;
            }

            public String getZhuihaoId() {
                return zhuihaoId;
            }

            public void setZhuihaoId(String zhuihaoId) {
                this.zhuihaoId = zhuihaoId;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public String getLotCode() {
                return lotCode;
            }

            public void setLotCode(String lotCode) {
                this.lotCode = lotCode;
            }

            public String getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(String totalNum) {
                this.totalNum = totalNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getBoughtNum() {
                return boughtNum;
            }

            public void setBoughtNum(String boughtNum) {
                this.boughtNum = boughtNum;
            }

            public String getBetContent() {
                return betContent;
            }

            public void setBetContent(String betContent) {
                this.betContent = betContent;
            }
        }
    }
}
