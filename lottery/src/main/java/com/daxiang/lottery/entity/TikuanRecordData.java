package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class TikuanRecordData {


    /**
     * sign : 01e7395909ed34c6b3b84e6d65192bb9
     * msg : 成功
     * code : 0
     * data : {"items":[{"handle_time":null,"amount":10,"bankCard":"6214832135020829","bankBranch":"宝山支行","director":null,"cardType":"招商银行","remark":null,"accounting":null,"userId":2016092210470300879,"tradeRecordId":2016092210470303014,"applyName":"于冬辉","applyTime":1482459252000,"tradeType":4,"status":1},{"handle_time":null,"amount":10,"bankCard":"6214832135020829","bankBranch":"宝山支行","director":null,"cardType":"招商银行","remark":null,"accounting":null,"userId":2016092210470300879,"tradeRecordId":2016092210470303013,"applyName":"于冬辉","applyTime":1482458979000,"tradeType":4,"status":1}],"pageNum":1,"timeStamp":1482460145351}
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
         * items : [{"handle_time":null,"amount":10,"bankCard":"6214832135020829","bankBranch":"宝山支行","director":null,"cardType":"招商银行","remark":null,"accounting":null,"userId":2016092210470300879,"tradeRecordId":2016092210470303014,"applyName":"于冬辉","applyTime":1482459252000,"tradeType":4,"status":1},{"handle_time":null,"amount":10,"bankCard":"6214832135020829","bankBranch":"宝山支行","director":null,"cardType":"招商银行","remark":null,"accounting":null,"userId":2016092210470300879,"tradeRecordId":2016092210470303013,"applyName":"于冬辉","applyTime":1482458979000,"tradeType":4,"status":1}]
         * pageNum : 1
         * timeStamp : 1482460145351
         */

        private int pageNum;
        private long timeStamp;
        private List<ItemsBean> items;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
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
             * handle_time : null
             * amount : 10
             * bankCard : 6214832135020829
             * bankBranch : 宝山支行
             * director : null
             * cardType : 招商银行
             * remark : null
             * accounting : null
             * userId : 2016092210470300879
             * tradeRecordId : 2016092210470303014
             * applyName : 于冬辉
             * applyTime : 1482459252000
             * tradeType : 4
             * status : 1
             */

            private String handle_time;
            private String amount;
            private String bankCard;
            private String bankBranch;
            private String director;
            private String cardType;
            private String remark;
            private String accounting;
            private long userId;
            private String tradeRecordId;
            private String applyName;
            private long applyTime;
            private int tradeType;
            private int status;

            public String getHandle_time() {
                return handle_time;
            }

            public void setHandle_time(String handle_time) {
                this.handle_time = handle_time;
            }

            public String getAmount() {
                return amount;
            }

            public void String(String amount) {
                this.amount = amount;
            }

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public String getBankBranch() {
                return bankBranch;
            }

            public void setBankBranch(String bankBranch) {
                this.bankBranch = bankBranch;
            }

            public String getDirector() {
                return director;
            }

            public void setDirector(String director) {
                this.director = director;
            }

            public String getCardType() {
                return cardType;
            }

            public void setCardType(String cardType) {
                this.cardType = cardType;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getAccounting() {
                return accounting;
            }

            public void setAccounting(String accounting) {
                this.accounting = accounting;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getTradeRecordId() {
                return tradeRecordId;
            }

            public void setTradeRecordId(String tradeRecordId) {
                this.tradeRecordId = tradeRecordId;
            }

            public String getApplyName() {
                return applyName;
            }

            public void setApplyName(String applyName) {
                this.applyName = applyName;
            }

            public long getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(long applyTime) {
                this.applyTime = applyTime;
            }

            public int getTradeType() {
                return tradeType;
            }

            public void setTradeType(int tradeType) {
                this.tradeType = tradeType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
