package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ActionDeclareData {

    /**
     * sign : null
     * msg : 成功
     * code : 0
     * data : {"items":[{"id":2,"channelId":"10000000","activityNumber":"CX2017031502","activityStatus":"0","activityType":"11","rechargeAmount":1,"totalAmount":"198","startTime":1490250769000,"endTime":1490961896000,"description":"\t1、充值满20元送88元红包.(20元*2个，10元*3个，5元*2个，2元*3个，1元*2个）\r\n\t2、充值成功后，可在 \u201c我的彩票\u201d >>> \u201c我的红包\u201d中查看红包发放详情\r\n\t3、同一用户仅限参与该活动一次。\r\n\t4、参与活动的充值金额仅可用户购彩，不可提现。\r\n\t5、活动仅限于2017年4月1日之后注册的用户参与。\r\n\t6、红包有效期为到账起30天内有效，请及时使用"}]}
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
        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 2
             * channelId : 10000000
             * activityNumber : CX2017031502
             * activityStatus : 0
             * activityType : 11
             * rechargeAmount : 1
             * totalAmount : 198
             * startTime : 1490250769000
             * endTime : 1490961896000
             * description : 	1、充值满20元送88元红包.(20元*2个，10元*3个，5元*2个，2元*3个，1元*2个）
             2、充值成功后，可在 “我的彩票” >>> “我的红包”中查看红包发放详情
             3、同一用户仅限参与该活动一次。
             4、参与活动的充值金额仅可用户购彩，不可提现。
             5、活动仅限于2017年4月1日之后注册的用户参与。
             6、红包有效期为到账起30天内有效，请及时使用
             */

            private int id;
            private String channelId;
            private String activityNumber;
            private String activityStatus;
            private String activityType;
            private String rechargeAmount;
            private String totalAmount;
            private long startTime;
            private long endTime;
            private String description;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getActivityNumber() {
                return activityNumber;
            }

            public void setActivityNumber(String activityNumber) {
                this.activityNumber = activityNumber;
            }

            public String getActivityStatus() {
                return activityStatus;
            }

            public void setActivityStatus(String activityStatus) {
                this.activityStatus = activityStatus;
            }

            public String getActivityType() {
                return activityType;
            }

            public void setActivityType(String activityType) {
                this.activityType = activityType;
            }

            public String getRechargeAmount() {
                return rechargeAmount;
            }

            public void setRechargeAmount(String rechargeAmount) {
                this.rechargeAmount = rechargeAmount;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
