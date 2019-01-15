package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/29.
 */

public class OddsDetailData {

    /**
     * sign : d55bab47dee5a652429bd2a3236379d9
     * msg : 成功
     * code : 0
     * data : {"items":[{"llevel":"111.050","hlevel":"11.800","handicap":"半/一","name":"bet 365","time":"178小时前"},{"llevel":"1.000","hlevel":"0.820","handicap":"半/一","name":"bet 365","time":"35小时前"},{"llevel":"1.050","hlevel":"0.800","handicap":"半/一","name":"bet 365","time":"34小时前"},{"llevel":"1.020","hlevel":"0.820","handicap":"半/一","name":"bet 365","time":"初始赔率"}],"timeStamp":1523415848110}
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
         * items : [{"llevel":"111.050","hlevel":"11.800","handicap":"半/一","name":"bet 365","time":"178小时前"},{"llevel":"1.000","hlevel":"0.820","handicap":"半/一","name":"bet 365","time":"35小时前"},{"llevel":"1.050","hlevel":"0.800","handicap":"半/一","name":"bet 365","time":"34小时前"},{"llevel":"1.020","hlevel":"0.820","handicap":"半/一","name":"bet 365","time":"初始赔率"}]
         * timeStamp : 1523415848110
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
             * llevel : 111.050
             * hlevel : 11.800
             * handicap : 半/一
             * name : bet 365
             * time : 178小时前
             */

            private String llevel;
            private String hlevel;
            private String handicap;
            private String name;
            private String time;

            private String oa;
            private String od;
            private String oh;

            public String getOa() {
                return oa;
            }

            public void setOa(String oa) {
                this.oa = oa;
            }

            public String getOd() {
                return od;
            }

            public void setOd(String od) {
                this.od = od;
            }

            public String getOh() {
                return oh;
            }

            public void setOh(String oh) {
                this.oh = oh;
            }

            public String getLlevel() {
                return llevel;
            }

            public void setLlevel(String llevel) {
                this.llevel = llevel;
            }

            public String getHlevel() {
                return hlevel;
            }

            public void setHlevel(String hlevel) {
                this.hlevel = hlevel;
            }

            public String getHandicap() {
                return handicap;
            }

            public void setHandicap(String handicap) {
                this.handicap = handicap;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
