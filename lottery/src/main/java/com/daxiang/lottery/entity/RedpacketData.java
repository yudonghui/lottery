package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class RedpacketData {

    /**
     * sign : null
     * msg : 成功
     * code : 0
     * data : {"items":[{"id":13,"uid":2017030615272000080,"getTime":1489653282000,"type":"11","amount":"2","limitAmount":"10","isEffect":"0","effectTime":1492245282000,"isExpired":"0","expiredTime":1492245282000,"isUsed":"0","usedTime":null,"orderId":null,"remark":null}],"pageNum":1,"pageSize":10,"pageStart":1,"totalNum":9}
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
         * items : [{"id":13,"uid":2017030615272000080,"getTime":1489653282000,"type":"11","amount":"2","limitAmount":"10","isEffect":"0","effectTime":1492245282000,"isExpired":"0","expiredTime":1492245282000,"isUsed":"0","usedTime":null,"orderId":null,"remark":null}]
         * pageNum : 1
         * pageSize : 10
         * pageStart : 1
         * totalNum : 9
         */

        private int pageNum;
        private int pageSize;
        private int pageStart;
        private int totalNum;
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

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 13
             * uid : 2017030615272000080
             * getTime : 1489653282000
             * type : 11
             * amount : 2
             * limitAmount : 10
             * isEffect : 0
             * effectTime : 1492245282000
             * isExpired : 0
             * expiredTime : 1492245282000
             * isUsed : 0
             * usedTime : null
             * orderId : null
             * remark : null
             */

            private String id;
            private long uid;
            private String type;
            private String amount;//红包钱数
            private String limitAmount;//满多少钱可以用
            private String status;
            private long createTime;
            private long expiredTime;
            private long effectTime;
            private long usedTime;
            private String orderId;
            private String remark;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public long getUid() {
                return uid;
            }

            public void setUid(long uid) {
                this.uid = uid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getLimitAmount() {
                return limitAmount;
            }

            public void setLimitAmount(String limitAmount) {
                this.limitAmount = limitAmount;
            }

            public long getEffectTime() {
                return effectTime;
            }

            public void setEffectTime(long effectTime) {
                this.effectTime = effectTime;
            }

            public long getExpiredTime() {
                return expiredTime;
            }

            public void setExpiredTime(long expiredTime) {
                this.expiredTime = expiredTime;
            }

            public long getUsedTime() {
                return usedTime;
            }

            public void setUsedTime(long usedTime) {
                this.usedTime = usedTime;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
