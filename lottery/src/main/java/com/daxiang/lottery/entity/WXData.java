package com.daxiang.lottery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/23 0023.
 */
public class WXData {

    /**
     * sign : 1e969da01b72b20fb37e352c951a6f23
     * msg : 成功
     * code : 0
     * data : {"items":{"currencyType":"156","orderId":"2016092210470303028","sign":"49652549DE4CE221ED5EEE4E0E8BAF47","channelType":"6002","txnType":"01","noncestr":"vtjuewkjuna2u2vjg86xoipb4vdmtnv4","termId":"","orderTime":"20161223143315","txnTime":"20161223143309","partnerid":"17479366","timestamp":"1482474789","signAture":"4A48A8067C0E889D116AE92DBB5651C2","package":"Sign=WXPay","txnSubType":"010132","encoding":"UTF-8","orderBody":"彩像彩票","appid":"wx1ac7e4187017d73e","respMsg":"交易成功","merId":"996600008000005","txnSeqId":"9012016122314243309518571","prepayid":"wx20161223143315950652f4ab0822865947","payAccessType":"02","respCode":"0000","signMethod":"02","txnAmt":"1000"},"timeStamp":1482474795689}
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
         * items : {"currencyType":"156","orderId":"2016092210470303028","sign":"49652549DE4CE221ED5EEE4E0E8BAF47","channelType":"6002","txnType":"01","noncestr":"vtjuewkjuna2u2vjg86xoipb4vdmtnv4","termId":"","orderTime":"20161223143315","txnTime":"20161223143309","partnerid":"17479366","timestamp":"1482474789","signAture":"4A48A8067C0E889D116AE92DBB5651C2","package":"Sign=WXPay","txnSubType":"010132","encoding":"UTF-8","orderBody":"彩像彩票","appid":"wx1ac7e4187017d73e","respMsg":"交易成功","merId":"996600008000005","txnSeqId":"9012016122314243309518571","prepayid":"wx20161223143315950652f4ab0822865947","payAccessType":"02","respCode":"0000","signMethod":"02","txnAmt":"1000"}
         * timeStamp : 1482474795689
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
             * currencyType : 156
             * orderId : 2016092210470303028
             * sign : 49652549DE4CE221ED5EEE4E0E8BAF47
             * channelType : 6002
             * txnType : 01
             * noncestr : vtjuewkjuna2u2vjg86xoipb4vdmtnv4
             * termId :
             * orderTime : 20161223143315
             * txnTime : 20161223143309
             * partnerid : 17479366
             * timestamp : 1482474789
             * signAture : 4A48A8067C0E889D116AE92DBB5651C2
             * package : Sign=WXPay
             * txnSubType : 010132
             * encoding : UTF-8
             * orderBody : 彩像彩票
             * appid : wx1ac7e4187017d73e
             * respMsg : 交易成功
             * merId : 996600008000005
             * txnSeqId : 9012016122314243309518571
             * prepayid : wx20161223143315950652f4ab0822865947
             * payAccessType : 02
             * respCode : 0000
             * signMethod : 02
             * txnAmt : 1000
             */

            private String currencyType;
            private String orderId;
            private String sign;
            private String channelType;
            private String txnType;
            private String noncestr;
            private String termId;
            private String orderTime;
            private String txnTime;
            private String partnerid;
            private String timestamp;
            private String signAture;
            @SerializedName("package")
            private String packageX;
            private String txnSubType;
            private String encoding;
            private String orderBody;
            private String appid;
            private String respMsg;
            private String merId;
            private String txnSeqId;
            private String prepayid;
            private String payAccessType;
            private String respCode;
            private String signMethod;
            private String txnAmt;

            public String getCurrencyType() {
                return currencyType;
            }

            public void setCurrencyType(String currencyType) {
                this.currencyType = currencyType;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getChannelType() {
                return channelType;
            }

            public void setChannelType(String channelType) {
                this.channelType = channelType;
            }

            public String getTxnType() {
                return txnType;
            }

            public void setTxnType(String txnType) {
                this.txnType = txnType;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getTermId() {
                return termId;
            }

            public void setTermId(String termId) {
                this.termId = termId;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getTxnTime() {
                return txnTime;
            }

            public void setTxnTime(String txnTime) {
                this.txnTime = txnTime;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getSignAture() {
                return signAture;
            }

            public void setSignAture(String signAture) {
                this.signAture = signAture;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getTxnSubType() {
                return txnSubType;
            }

            public void setTxnSubType(String txnSubType) {
                this.txnSubType = txnSubType;
            }

            public String getEncoding() {
                return encoding;
            }

            public void setEncoding(String encoding) {
                this.encoding = encoding;
            }

            public String getOrderBody() {
                return orderBody;
            }

            public void setOrderBody(String orderBody) {
                this.orderBody = orderBody;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getRespMsg() {
                return respMsg;
            }

            public void setRespMsg(String respMsg) {
                this.respMsg = respMsg;
            }

            public String getMerId() {
                return merId;
            }

            public void setMerId(String merId) {
                this.merId = merId;
            }

            public String getTxnSeqId() {
                return txnSeqId;
            }

            public void setTxnSeqId(String txnSeqId) {
                this.txnSeqId = txnSeqId;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPayAccessType() {
                return payAccessType;
            }

            public void setPayAccessType(String payAccessType) {
                this.payAccessType = payAccessType;
            }

            public String getRespCode() {
                return respCode;
            }

            public void setRespCode(String respCode) {
                this.respCode = respCode;
            }

            public String getSignMethod() {
                return signMethod;
            }

            public void setSignMethod(String signMethod) {
                this.signMethod = signMethod;
            }

            public String getTxnAmt() {
                return txnAmt;
            }

            public void setTxnAmt(String txnAmt) {
                this.txnAmt = txnAmt;
            }
        }
    }
}
