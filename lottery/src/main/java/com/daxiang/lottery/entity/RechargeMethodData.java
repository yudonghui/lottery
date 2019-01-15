package com.daxiang.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class RechargeMethodData implements Serializable {
    /**
     * sign : 012fe74d410dc0e3ad5c7f13080faf17
     * msg : 成功
     * code : 0
     * data : {"items":[{"id":2,"client":2,"ordinal":1,"code":"U_PAY","name":"U付","status":1,"detail":"快捷方便"}],"timeStamp":1481348425748}
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

    public static class DataBean implements Serializable{
        /**
         * items : [{"id":2,"client":2,"ordinal":1,"code":"U_PAY","name":"U付","status":1,"detail":"快捷方便"}]
         * timeStamp : 1481348425748
         */

        private long timeStamp;
        private List<ItemsBean> items;

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

        public static class ItemsBean implements Serializable{
            /**
             * id : 2
             * client : 2
             * ordinal : 1
             * code : U_PAY
             * name : U付
             * status : 1
             * detail : 快捷方便
             */

            private int id;
            private String client;
            private int sort;
            private String code;
            private String name;
            private int status;
            private String description;
            private String merchantId;
            private String openType;
            private boolean isChecked;

            public boolean getIsChecked() {
                return isChecked;
            }

            public void setIsChecked(boolean isChecked) {
                this.isChecked = isChecked;
            }

            public String getOpenType() {
                return openType;
            }

            public void setOpenType(String openType) {
                this.openType = openType;
            }

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getClient() {
                return client;
            }

            public void setClient(String client) {
                this.client = client;
            }



            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }


        }
    }



   /* *//**
     * data : [{"code":"ALIPAY_OFFLINE","client":"android","channelName":"caixiang","status":1,"name":"支付宝转账","id":13,"index":6},{"code":"UPAY_QUICK_CREDIT","client":"android","channelName":"caixiang","status":1,"name":"U付-信用卡快捷","id":14,"index":4},{"code":"UPAY_QUICK_DEBIT","client":"android","channelName":"caixiang","status":1,"name":"U付-储蓄卡快捷","id":15,"index":3},{"code":"YEEPAY","client":"android","channelName":"caixiang","status":1,"name":"易宝支付","id":24,"index":2},{"code":"LLPAY","client":"android","channelName":"caixiang","status":1,"name":"连连支付","id":29,"index":1}]
     * msg : 成功
     * code : 0
     *//*

    private String msg;
    private int code;
    *//**
     * code : ALIPAY_OFFLINE
     * client : android
     * channelName : caixiang
     * status : 1
     * name : 支付宝转账
     * id : 13
     * index : 6
     *//*

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String code;
        private String client;
        private String channelName;
        private int status;
        private String name;
        private int id;
        private int index;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }*/
}
