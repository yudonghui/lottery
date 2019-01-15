package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class RebateDetailData {

    /**
     * sign : 8de100b547963b1debb080fb602f4413
     * msg : 成功
     * code : 0
     * data : {"items":{"totalFanDian":54.9,"totalTrade":3772,"detail":[{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517773503000,"cost":1200,"amount":36,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517679004000,"cost":480,"amount":14.4,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517616658000,"cost":50,"amount":1.5,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517616655000,"cost":100,"amount":3,"lotCode":"42"}],"pageNum":1},"timeStamp":1517907679819}
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
         * items : {"totalFanDian":54.9,"totalTrade":3772,"detail":[{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517773503000,"cost":1200,"amount":36,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517679004000,"cost":480,"amount":14.4,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517616658000,"cost":50,"amount":1.5,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517616655000,"cost":100,"amount":3,"lotCode":"42"}],"pageNum":1}
         * timeStamp : 1517907679819
         */

        private ItemsBean items;
        private long timeStamp;

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public static class ItemsBean {
            /**
             * totalFanDian : 54.9
             * totalTrade : 3772.0
             * detail : [{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517773503000,"cost":1200,"amount":36,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517679004000,"cost":480,"amount":14.4,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517616658000,"cost":50,"amount":1.5,"lotCode":"42"},{"userId":"7122714222400953","userName":"水","channelId":10000000,"mobile":null,"createTime":1517616655000,"cost":100,"amount":3,"lotCode":"42"}]
             * pageNum : 1
             */

            private String totalFanDian;
            private String totalTrade;
            private int pageNum;
            private List<DetailBean> detail;

            public String getTotalFanDian() {
                return totalFanDian;
            }

            public void setTotalFanDian(String totalFanDian) {
                this.totalFanDian = totalFanDian;
            }

            public String getTotalTrade() {
                return totalTrade;
            }

            public void setTotalTrade(String totalTrade) {
                this.totalTrade = totalTrade;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public List<DetailBean> getDetail() {
                return detail;
            }

            public void setDetail(List<DetailBean> detail) {
                this.detail = detail;
            }

            public static class DetailBean {
                /**
                 * userId : 7122714222400953
                 * userName : 水
                 * channelId : 10000000
                 * mobile : null
                 * createTime : 1517773503000
                 * cost : 1200.0
                 * amount : 36.0
                 * lotCode : 42
                 */

                private String userId;
                private String userName;
                private String channelId;
                private String mobile;
                private String createTime;
                private String cost;
                private String amount;
                private String lotCode;

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getChannelId() {
                    return channelId;
                }

                public void setChannelId(String channelId) {
                    this.channelId = channelId;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getCost() {
                    return cost;
                }

                public void setCost(String cost) {
                    this.cost = cost;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getLotCode() {
                    return lotCode;
                }

                public void setLotCode(String lotCode) {
                    this.lotCode = lotCode;
                }
            }
        }
    }
}
