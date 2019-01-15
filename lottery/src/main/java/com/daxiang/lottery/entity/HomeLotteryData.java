package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HomeLotteryData {

    /**
     * code : 0
     * msg : 根据客服端获取可售彩种列表成功
     * data : {"item2":[{"lotCode":"23529","issueNo":"2016149","awardPool":"3816313703.00"},{"lotCode":"51","issueNo":"2016149","awardPool":"1069092085.00"},{"lotCode":"10022","issueNo":"2016150","awardPool":"36027297.00"},{"lotCode":"23528","issueNo":"2016149","awardPool":"0.00"}],"item1":[{"id":1,"type":"ios","version":"3.5.7","lotCode":"42","serial":50,"state":1,"logoName":"jczq","lotName":"竞彩足球","description":"2串1倍投返奖高","remark":null},{"id":2,"type":"ios","version":"3.5.7","lotCode":"43","serial":49,"state":1,"logoName":"jclq","lotName":"竞彩篮球","description":"NBA赛事一网打净","remark":null},{"id":3,"type":"ios","version":"3.5.7","lotCode":"1000","serial":48,"state":1,"logoName":"jczq","lotName":"竞足单关","description":"单关固定易中奖","remark":null},{"id":5,"type":"ios","version":"3.5.7","lotCode":"1001","serial":47,"state":1,"logoName":"jclq","lotName":"竞篮单关","description":"单关固定易中奖","remark":null},{"id":6,"type":"ios","version":"3.5.7","lotCode":"21406","serial":46,"state":1,"logoName":"syydj","lotName":"十一运夺金","description":"猜中1球就有奖","remark":null},{"id":7,"type":"ios","version":"3.5.7","lotCode":"23529","serial":45,"state":1,"logoName":"dlt","lotName":"超级大乐透","description":"3元可中1600万","remark":null},{"id":8,"type":"ios","version":"3.5.7","lotCode":"11","serial":44,"state":1,"logoName":"sfc","lotName":"胜负彩","description":"猜14场博500万","remark":null},{"id":9,"type":"ios","version":"3.5.7","lotCode":"19","serial":43,"state":1,"logoName":"rj","lotName":"任九场","description":"猜9场就中奖","remark":null},{"id":10,"type":"ios","version":"3.5.7","lotCode":"33","serial":42,"state":1,"logoName":"pls","lotName":"排列三","description":"猜中3球赢千元","remark":null},{"id":11,"type":"ios","version":"3.5.7","lotCode":"35","serial":41,"state":1,"logoName":"plw","lotName":"排列五","description":"2元博10万","remark":null},{"id":12,"type":"ios","version":"3.5.7","lotCode":"10022","serial":40,"state":1,"logoName":"qxc","lotName":"七星彩","description":"2元可中50万","remark":null},{"id":13,"type":"ios","version":"3.5.7","lotCode":"23528","serial":39,"state":1,"logoName":"qlc","lotName":"七乐彩","description":"30选7赢百万","remark":null},{"id":14,"type":"ios","version":"3.5.7","lotCode":"51","serial":38,"state":1,"logoName":"ssq","lotName":"双色球","description":"2元可中1000万","remark":null},{"id":15,"type":"ios","version":"3.5.7","lotCode":"52","serial":37,"state":1,"logoName":"fcsd","lotName":"福彩3D","description":"好玩易中奖","remark":null}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<Item2Bean> item2;
        private List<Item1Bean> item1;

        public List<Item2Bean> getItem2() {
            return item2;
        }

        public void setItem2(List<Item2Bean> item2) {
            this.item2 = item2;
        }

        public List<Item1Bean> getItem1() {
            return item1;
        }

        public void setItem1(List<Item1Bean> item1) {
            this.item1 = item1;
        }

        public static class Item2Bean {
            /**
             * lotCode : 23529
             * issueNo : 2016149
             * awardPool : 3816313703.00
             */

            private String lotCode;
            private String issueNo;
            private String awardPool;

            public String getLotCode() {
                return lotCode;
            }

            public void setLotCode(String lotCode) {
                this.lotCode = lotCode;
            }

            public String getIssueNo() {
                return issueNo;
            }

            public void setIssueNo(String issueNo) {
                this.issueNo = issueNo;
            }

            public String getAwardPool() {
                return awardPool;
            }

            public void setAwardPool(String awardPool) {
                this.awardPool = awardPool;
            }
        }

        public static class Item1Bean {
            /**
             * id : 1
             * type : ios
             * version : 3.5.7
             * lotCode : 42
             * serial : 50
             * state : 1
             * logoName : jczq
             * lotName : 竞彩足球
             * description : 2串1倍投返奖高
             * remark : null
             */

            private int id;
            private String type;
            private String version;
            private String lotCode;
            private int serial;
            private int state;
            private String logoName;
            private String lotName;
            private String description;
            private Object remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getLotCode() {
                return lotCode;
            }

            public void setLotCode(String lotCode) {
                this.lotCode = lotCode;
            }

            public int getSerial() {
                return serial;
            }

            public void setSerial(int serial) {
                this.serial = serial;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getLogoName() {
                return logoName;
            }

            public void setLogoName(String logoName) {
                this.logoName = logoName;
            }

            public String getLotName() {
                return lotName;
            }

            public void setLotName(String lotName) {
                this.lotName = lotName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}
