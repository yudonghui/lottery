package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class RebateGroupData {

    /**
     * sign : 3f3b40d48d1c66a3f3d193ce8602d951
     * msg : 成功
     * code : 0
     * data : {"items":[{"id":1,"rebateGroupName":"竞彩足球返点组","tradeTypeId":42,"rebateType":0,"rebateRate":"0.07","createTime":1475130117000,"isDeleted":0,"remarks":""},{"id":3,"rebateGroupName":"竞彩篮球返点组","tradeTypeId":43,"rebateType":0,"rebateRate":"0.07","createTime":1480410976000,"isDeleted":0,"remarks":"竞彩篮球"},{"id":4,"rebateGroupName":"胜负彩返点组","tradeTypeId":11,"rebateType":0,"rebateRate":"0.07","createTime":1480411273000,"isDeleted":0,"remarks":"胜负彩"},{"id":5,"rebateGroupName":"任九场返点组","tradeTypeId":19,"rebateType":0,"rebateRate":"0.07","createTime":1480411325000,"isDeleted":0,"remarks":"任九场"},{"id":6,"rebateGroupName":"超级大乐透返点组","tradeTypeId":23529,"rebateType":0,"rebateRate":"0.07","createTime":1480411374000,"isDeleted":0,"remarks":"超级大乐透"},{"id":7,"rebateGroupName":"双色球返点组","tradeTypeId":51,"rebateType":0,"rebateRate":"0.07","createTime":1480411417000,"isDeleted":0,"remarks":"双色球"},{"id":8,"rebateGroupName":"排列三返点组","tradeTypeId":33,"rebateType":0,"rebateRate":"0.07","createTime":1480411443000,"isDeleted":0,"remarks":"排列三"},{"id":9,"rebateGroupName":"排列五返点组","tradeTypeId":35,"rebateType":0,"rebateRate":"0.07","createTime":1480411457000,"isDeleted":0,"remarks":"排列五"},{"id":10,"rebateGroupName":"福彩3D返点组","tradeTypeId":52,"rebateType":0,"rebateRate":"0.07","createTime":1480411473000,"isDeleted":0,"remarks":"福彩3D"},{"id":11,"rebateGroupName":"十一运夺金返点组","tradeTypeId":21406,"rebateType":0,"rebateRate":"0.07","createTime":1480411498000,"isDeleted":0,"remarks":"十一运夺金"},{"id":12,"rebateGroupName":"七星彩返点组","tradeTypeId":10022,"rebateType":0,"rebateRate":"0.07","createTime":1480411520000,"isDeleted":0,"remarks":"七星彩"},{"id":13,"rebateGroupName":"七乐彩","tradeTypeId":23528,"rebateType":0,"rebateRate":"0.07","createTime":1480411536000,"isDeleted":0,"remarks":"七乐彩"}],"timeStamp":1488448730328}
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
         * items : [{"id":1,"rebateGroupName":"竞彩足球返点组","tradeTypeId":42,"rebateType":0,"rebateRate":"0.07","createTime":1475130117000,"isDeleted":0,"remarks":""},{"id":3,"rebateGroupName":"竞彩篮球返点组","tradeTypeId":43,"rebateType":0,"rebateRate":"0.07","createTime":1480410976000,"isDeleted":0,"remarks":"竞彩篮球"},{"id":4,"rebateGroupName":"胜负彩返点组","tradeTypeId":11,"rebateType":0,"rebateRate":"0.07","createTime":1480411273000,"isDeleted":0,"remarks":"胜负彩"},{"id":5,"rebateGroupName":"任九场返点组","tradeTypeId":19,"rebateType":0,"rebateRate":"0.07","createTime":1480411325000,"isDeleted":0,"remarks":"任九场"},{"id":6,"rebateGroupName":"超级大乐透返点组","tradeTypeId":23529,"rebateType":0,"rebateRate":"0.07","createTime":1480411374000,"isDeleted":0,"remarks":"超级大乐透"},{"id":7,"rebateGroupName":"双色球返点组","tradeTypeId":51,"rebateType":0,"rebateRate":"0.07","createTime":1480411417000,"isDeleted":0,"remarks":"双色球"},{"id":8,"rebateGroupName":"排列三返点组","tradeTypeId":33,"rebateType":0,"rebateRate":"0.07","createTime":1480411443000,"isDeleted":0,"remarks":"排列三"},{"id":9,"rebateGroupName":"排列五返点组","tradeTypeId":35,"rebateType":0,"rebateRate":"0.07","createTime":1480411457000,"isDeleted":0,"remarks":"排列五"},{"id":10,"rebateGroupName":"福彩3D返点组","tradeTypeId":52,"rebateType":0,"rebateRate":"0.07","createTime":1480411473000,"isDeleted":0,"remarks":"福彩3D"},{"id":11,"rebateGroupName":"十一运夺金返点组","tradeTypeId":21406,"rebateType":0,"rebateRate":"0.07","createTime":1480411498000,"isDeleted":0,"remarks":"十一运夺金"},{"id":12,"rebateGroupName":"七星彩返点组","tradeTypeId":10022,"rebateType":0,"rebateRate":"0.07","createTime":1480411520000,"isDeleted":0,"remarks":"七星彩"},{"id":13,"rebateGroupName":"七乐彩","tradeTypeId":23528,"rebateType":0,"rebateRate":"0.07","createTime":1480411536000,"isDeleted":0,"remarks":"七乐彩"}]
         * timeStamp : 1488448730328
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
             * id : 1
             * rebateGroupName : 竞彩足球返点组
             * tradeTypeId : 42
             * rebateType : 0
             * rebateRate : 0.07
             * createTime : 1475130117000
             * isDeleted : 0
             * remarks :
             */

            private int id;
            private String rebateGroupName;
            private String tradeTypeId;
            private int rebateType;
            private String rebateRate;
            private long createTime;
            private int isDeleted;
            private String remarks;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRebateGroupName() {
                return rebateGroupName;
            }

            public void setRebateGroupName(String rebateGroupName) {
                this.rebateGroupName = rebateGroupName;
            }

            public String getTradeTypeId() {
                return tradeTypeId;
            }

            public void setTradeTypeId(String tradeTypeId) {
                this.tradeTypeId = tradeTypeId;
            }

            public int getRebateType() {
                return rebateType;
            }

            public void setRebateType(int rebateType) {
                this.rebateType = rebateType;
            }

            public String getRebateRate() {
                return rebateRate;
            }

            public void setRebateRate(String rebateRate) {
                this.rebateRate = rebateRate;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(int isDeleted) {
                this.isDeleted = isDeleted;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }
        }
    }
}
