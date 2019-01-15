package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class BillStreamData {


    /**
     * sign : 4664584e1fa548da4f91f93278b05381
     * msg : 成功
     * code : 0
     * data : {"items":[{"tradeId":2016092210470302948,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900018","amount":4,"balance":2760.67,"status":0,"payWay":2,"remark":"普通购彩_大乐透_2016149","createTime":1482116091000,"updateTime":1482116091000,"directType":2,"payExpireTime":1482116091000,"payFinishTime":1482116091000},{"tradeId":2016092210470302947,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900017","amount":2,"balance":2764.67,"status":0,"payWay":2,"remark":"普通购彩_大乐透_2016148","createTime":1481873154000,"updateTime":1481873154000,"directType":2,"payExpireTime":1481873154000,"payFinishTime":1481873154000},{"tradeId":2016092210470302946,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900016","amount":2,"balance":2766.67,"status":0,"payWay":2,"remark":"普通购彩_排列三_2016344","createTime":1481866711000,"updateTime":1481866711000,"directType":2,"payExpireTime":1481866711000,"payFinishTime":1481866711000},{"tradeId":2016092210470302945,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900015","amount":2,"balance":2768.67,"status":0,"payWay":2,"remark":"发起合买_双色球_2016148","createTime":1481854475000,"updateTime":1481854475000,"directType":2,"payExpireTime":1481854476000,"payFinishTime":1481854476000},{"tradeId":2016092210470302944,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900014","amount":2,"balance":2770.67,"status":0,"payWay":2,"remark":"普通购彩_双色球_2016148","createTime":1481854461000,"updateTime":1481854461000,"directType":2,"payExpireTime":1481854461000,"payFinishTime":1481854461000},{"tradeId":2016092210470302943,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900013","amount":2,"balance":2772.67,"status":0,"payWay":2,"remark":"普通购彩_七乐彩_2016148","createTime":1481854451000,"updateTime":1481854451000,"directType":2,"payExpireTime":1481854451000,"payFinishTime":1481854451000},{"tradeId":2016092210470302942,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900012","amount":2,"balance":2774.67,"status":0,"payWay":2,"remark":"跟单发起_七星彩_2016148","createTime":1481854439000,"updateTime":1481854438000,"directType":2,"payExpireTime":1481854439000,"payFinishTime":1481854439000},{"tradeId":2016092210470302941,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900011","amount":2,"balance":2776.67,"status":0,"payWay":2,"remark":"跟单发起_排列五_2016344","createTime":1481854420000,"updateTime":1481854420000,"directType":2,"payExpireTime":1481854420000,"payFinishTime":1481854420000},{"tradeId":2016092210470302940,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900010","amount":2,"balance":2778.67,"status":0,"payWay":2,"remark":"发起合买_排列五_2016344","createTime":1481854381000,"updateTime":1481854381000,"directType":2,"payExpireTime":1481854381000,"payFinishTime":1481854381000},{"tradeId":2016092210470302939,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900009","amount":2,"balance":2780.67,"status":0,"payWay":2,"remark":"发起合买_排列三_2016344","createTime":1481854361000,"updateTime":1481854361000,"directType":2,"payExpireTime":1481854362000,"payFinishTime":1481854362000},{"tradeId":2016092210470302938,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900008","amount":10,"balance":2782.67,"status":0,"payWay":2,"remark":"发起合买_任九场_2016194","createTime":1481854342000,"updateTime":1481854342000,"directType":2,"payExpireTime":1481854342000,"payFinishTime":1481854342000},{"tradeId":2016092210470302937,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900007","amount":10,"balance":2792.67,"status":0,"payWay":2,"remark":"普通购彩_任九场_2016194","createTime":1481854305000,"updateTime":1481854305000,"directType":2,"payExpireTime":1481854305000,"payFinishTime":1481854305000},{"tradeId":2016092210470302936,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900006","amount":10,"balance":2802.67,"status":0,"payWay":2,"remark":"普通购彩_胜负彩_2016194","createTime":1481854191000,"updateTime":1481854191000,"directType":2,"payExpireTime":1481854191000,"payFinishTime":1481854191000},{"tradeId":2016092210470302935,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900005","amount":2,"balance":2812.67,"status":0,"payWay":2,"remark":"普通购彩_大乐透_2016148","createTime":1481854162000,"updateTime":1481854162000,"directType":2,"payExpireTime":1481854162000,"payFinishTime":1481854162000},{"tradeId":2016092210470302929,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121513475600021","amount":2,"balance":2814.67,"status":0,"payWay":2,"remark":"普通购彩_七乐彩_2016148","createTime":1481782006000,"updateTime":1481782006000,"directType":2,"payExpireTime":1481782006000,"payFinishTime":1481782006000}],"pageNum":3,"pageSize":15,"pageStart":1,"timeStamp":1482118000988}
     */

    private String sign;
    private String msg;
    private int code;
    private DataBean data;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * items : [{"tradeId":2016092210470302948,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900018","amount":4,"balance":2760.67,"status":0,"payWay":2,"remark":"普通购彩_大乐透_2016149","createTime":1482116091000,"updateTime":1482116091000,"directType":2,"payExpireTime":1482116091000,"payFinishTime":1482116091000},{"tradeId":2016092210470302947,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900017","amount":2,"balance":2764.67,"status":0,"payWay":2,"remark":"普通购彩_大乐透_2016148","createTime":1481873154000,"updateTime":1481873154000,"directType":2,"payExpireTime":1481873154000,"payFinishTime":1481873154000},{"tradeId":2016092210470302946,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900016","amount":2,"balance":2766.67,"status":0,"payWay":2,"remark":"普通购彩_排列三_2016344","createTime":1481866711000,"updateTime":1481866711000,"directType":2,"payExpireTime":1481866711000,"payFinishTime":1481866711000},{"tradeId":2016092210470302945,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900015","amount":2,"balance":2768.67,"status":0,"payWay":2,"remark":"发起合买_双色球_2016148","createTime":1481854475000,"updateTime":1481854475000,"directType":2,"payExpireTime":1481854476000,"payFinishTime":1481854476000},{"tradeId":2016092210470302944,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900014","amount":2,"balance":2770.67,"status":0,"payWay":2,"remark":"普通购彩_双色球_2016148","createTime":1481854461000,"updateTime":1481854461000,"directType":2,"payExpireTime":1481854461000,"payFinishTime":1481854461000},{"tradeId":2016092210470302943,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900013","amount":2,"balance":2772.67,"status":0,"payWay":2,"remark":"普通购彩_七乐彩_2016148","createTime":1481854451000,"updateTime":1481854451000,"directType":2,"payExpireTime":1481854451000,"payFinishTime":1481854451000},{"tradeId":2016092210470302942,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900012","amount":2,"balance":2774.67,"status":0,"payWay":2,"remark":"跟单发起_七星彩_2016148","createTime":1481854439000,"updateTime":1481854438000,"directType":2,"payExpireTime":1481854439000,"payFinishTime":1481854439000},{"tradeId":2016092210470302941,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900011","amount":2,"balance":2776.67,"status":0,"payWay":2,"remark":"跟单发起_排列五_2016344","createTime":1481854420000,"updateTime":1481854420000,"directType":2,"payExpireTime":1481854420000,"payFinishTime":1481854420000},{"tradeId":2016092210470302940,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900010","amount":2,"balance":2778.67,"status":0,"payWay":2,"remark":"发起合买_排列五_2016344","createTime":1481854381000,"updateTime":1481854381000,"directType":2,"payExpireTime":1481854381000,"payFinishTime":1481854381000},{"tradeId":2016092210470302939,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900009","amount":2,"balance":2780.67,"status":0,"payWay":2,"remark":"发起合买_排列三_2016344","createTime":1481854361000,"updateTime":1481854361000,"directType":2,"payExpireTime":1481854362000,"payFinishTime":1481854362000},{"tradeId":2016092210470302938,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900008","amount":10,"balance":2782.67,"status":0,"payWay":2,"remark":"发起合买_任九场_2016194","createTime":1481854342000,"updateTime":1481854342000,"directType":2,"payExpireTime":1481854342000,"payFinishTime":1481854342000},{"tradeId":2016092210470302937,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900007","amount":10,"balance":2792.67,"status":0,"payWay":2,"remark":"普通购彩_任九场_2016194","createTime":1481854305000,"updateTime":1481854305000,"directType":2,"payExpireTime":1481854305000,"payFinishTime":1481854305000},{"tradeId":2016092210470302936,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900006","amount":10,"balance":2802.67,"status":0,"payWay":2,"remark":"普通购彩_胜负彩_2016194","createTime":1481854191000,"updateTime":1481854191000,"directType":2,"payExpireTime":1481854191000,"payFinishTime":1481854191000},{"tradeId":2016092210470302935,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121514391900005","amount":2,"balance":2812.67,"status":0,"payWay":2,"remark":"普通购彩_大乐透_2016148","createTime":1481854162000,"updateTime":1481854162000,"directType":2,"payExpireTime":1481854162000,"payFinishTime":1481854162000},{"tradeId":2016092210470302929,"userId":2016092210470300879,"tradeType":0,"outTradeNo":"2016121513475600021","amount":2,"balance":2814.67,"status":0,"payWay":2,"remark":"普通购彩_七乐彩_2016148","createTime":1481782006000,"updateTime":1481782006000,"directType":2,"payExpireTime":1481782006000,"payFinishTime":1481782006000}]
         * pageNum : 3
         * pageSize : 15
         * pageStart : 1
         * timeStamp : 1482118000988
         */

        private int pageNum;
        private int pageSize;
        private int pageStart;
        private long timeStamp;
        private List<ItemsBean> items;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageStart() {
            return pageStart;
        }

        public void setPageStart(int pageStart) {
            this.pageStart = pageStart;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * tradeId : 2016092210470302948
             * userId : 2016092210470300879
             * tradeType : 0
             * outTradeNo : 2016121514391900018
             * amount : 4.0
             * balance : 2760.67
             * status : 0
             * payWay : 2
             * remark : 普通购彩_大乐透_2016149
             * createTime : 1482116091000
             * updateTime : 1482116091000
             * directType : 2
             * payExpireTime : 1482116091000
             * payFinishTime : 1482116091000
             */

            private long tradeId;
            private long userId;
            private int tradeType;
            private String outTradeNo;
            private double amount;
            private double balance;
            private int status;
            private int payWay;
            private String remark;
            private long createTime;
            private long updateTime;
            private int directType;
            private long payExpireTime;
            private long payFinishTime;

            public long getTradeId() {
                return tradeId;
            }

            public void setTradeId(long tradeId) {
                this.tradeId = tradeId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public int getTradeType() {
                return tradeType;
            }

            public void setTradeType(int tradeType) {
                this.tradeType = tradeType;
            }

            public String getOutTradeNo() {
                return outTradeNo;
            }

            public void setOutTradeNo(String outTradeNo) {
                this.outTradeNo = outTradeNo;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPayWay() {
                return payWay;
            }

            public void setPayWay(int payWay) {
                this.payWay = payWay;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public int getDirectType() {
                return directType;
            }

            public void setDirectType(int directType) {
                this.directType = directType;
            }

            public long getPayExpireTime() {
                return payExpireTime;
            }

            public void setPayExpireTime(long payExpireTime) {
                this.payExpireTime = payExpireTime;
            }

            public long getPayFinishTime() {
                return payFinishTime;
            }

            public void setPayFinishTime(long payFinishTime) {
                this.payFinishTime = payFinishTime;
            }
        }
    }
}
