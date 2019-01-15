package com.daxiang.lottery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author yudonghui
 * @date 2017/5/11
 * @describe May the Buddha bless bug-free!!!
 */
public class TestData {
    /**
     * sign : de0d478a56de85f21a579a2e85565610
     * msg : 成功
     * code : 0
     * data : {"items":{"package":"Sign=WXPay","orderId":"7051020335000062","sign":"A04370ED584B7B43DB738B8A6EFAED90","partnerid":"17586772","prepayid":"wx201705111705351bc1dbd1220819318458","noncestr":"56v1el21ce9dwlfpr91w81tiu78vmcjb","respCode":"0000","timestamp":"1494493531"},"timeStamp":1494493521325}
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
         * items : {"package":"Sign=WXPay","orderId":"7051020335000062","sign":"A04370ED584B7B43DB738B8A6EFAED90","partnerid":"17586772","prepayid":"wx201705111705351bc1dbd1220819318458","noncestr":"56v1el21ce9dwlfpr91w81tiu78vmcjb","respCode":"0000","timestamp":"1494493531"}
         * timeStamp : 1494493521325
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
             * package : Sign=WXPay
             * orderId : 7051020335000062
             * sign : A04370ED584B7B43DB738B8A6EFAED90
             * partnerid : 17586772
             * prepayid : wx201705111705351bc1dbd1220819318458
             * noncestr : 56v1el21ce9dwlfpr91w81tiu78vmcjb
             * respCode : 0000
             * timestamp : 1494493531
             */

            @SerializedName("package")
            private String packageX;
            private String orderId;
            private String sign;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private String respCode;
            private String timestamp;

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
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

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getRespCode() {
                return respCode;
            }

            public void setRespCode(String respCode) {
                this.respCode = respCode;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
