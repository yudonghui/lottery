package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class PrivilegeData {

    /**
     * sign : df48fd241a84ff5f217b4a49665322d6
     * msg : 成功
     * code : 0
     * data : {"items":[{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"buy_right","value":2,"remark":"有无购彩权限 --- 1：有； 2：无"},{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"status","value":1,"remark":"用户是否被冻结--- 1：正常； 2：冻结"},{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"account_status","value":1,"remark":"账户是否被冻结 --- 1：正常； 2：资金被冻结"},{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"recommend_right","value":1,"remark":"有无推荐权限 --- 1：有； 2：无"}],"timeStamp":1484122188543}
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
         * items : [{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"buy_right","value":2,"remark":"有无购彩权限 --- 1：有； 2：无"},{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"status","value":1,"remark":"用户是否被冻结--- 1：正常； 2：冻结"},{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"account_status","value":1,"remark":"账户是否被冻结 --- 1：正常； 2：资金被冻结"},{"id":2016092210470301000,"userId":2016092210470301000,"keyi":"recommend_right","value":1,"remark":"有无推荐权限 --- 1：有； 2：无"}]
         * timeStamp : 1484122188543
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

        public static class ItemsBean {
            /**
             * id : 2016092210470301000
             * userId : 2016092210470301000
             * keyi : buy_right
             * value : 2
             * remark : 有无购彩权限 --- 1：有； 2：无
             */

            private long id;
            private long userId;
            private String keyi;
            private int value;
            private String remark;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getKeyi() {
                return keyi;
            }

            public void setKeyi(String keyi) {
                this.keyi = keyi;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
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
