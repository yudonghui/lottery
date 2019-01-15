package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/6/7.
 */

public class QQData {


    /**
     * sign : 0618c1905299c80414445f61c4dfd667
     * msg : 成功
     * code : 0
     * data : {"items":{"orderId":"29215709","mwebUrl":"https://myun.tenpay.com/mqq/pay/qrcode.html?_wv=1027&_bid=2183&t=6Vf712f51fd08055c541adafb32130a9","respCode":"0000"},"timeStamp":1528364952383}
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
         * items : {"orderId":"29215709","mwebUrl":"https://myun.tenpay.com/mqq/pay/qrcode.html?_wv=1027&_bid=2183&t=6Vf712f51fd08055c541adafb32130a9","respCode":"0000"}
         * timeStamp : 1528364952383
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
             * orderId : 29215709
             * mwebUrl : https://myun.tenpay.com/mqq/pay/qrcode.html?_wv=1027&_bid=2183&t=6Vf712f51fd08055c541adafb32130a9
             * respCode : 0000
             */

            private String orderId;
            private String mwebUrl;
            private String respCode;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getMwebUrl() {
                return mwebUrl;
            }

            public void setMwebUrl(String mwebUrl) {
                this.mwebUrl = mwebUrl;
            }

            public String getRespCode() {
                return respCode;
            }

            public void setRespCode(String respCode) {
                this.respCode = respCode;
            }
        }
    }
}
