package com.daxiang.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2018/4/11.
 */

public class OddsData implements Serializable {

    /**
     * sign : 519dfd19ef25a2c69a14972993388500
     * msg : 成功
     * code : 0
     * data : {"items":[{"now_llevel":"111.050","start_hlevel":"0.820","now_handicap":"半/一","start_llevel":"1.020","now_hlevel":"11.800","start_handicap":"半/一","name":"bet 365","id":12},{"now_llevel":"0.820","start_hlevel":"1.110","now_handicap":"受半球","start_llevel":"0.800","now_hlevel":"1.080","start_handicap":"受半球","name":"立博","id":344}],"timeStamp":1523342274093}
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

    public static class DataBean implements Serializable {
        /**
         * items : [{"now_llevel":"111.050","start_hlevel":"0.820","now_handicap":"半/一","start_llevel":"1.020","now_hlevel":"11.800","start_handicap":"半/一","name":"bet 365","id":12},{"now_llevel":"0.820","start_hlevel":"1.110","now_handicap":"受半球","start_llevel":"0.800","now_hlevel":"1.080","start_handicap":"受半球","name":"立博","id":344}]
         * timeStamp : 1523342274093
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

        public static class ItemsBean implements Serializable {
            /**
             * now_llevel : 111.050
             * start_hlevel : 0.820
             * now_handicap : 半/一
             * start_llevel : 1.020
             * now_hlevel : 11.800
             * start_handicap : 半/一
             * name : bet 365
             * id : 12
             */

            private String now_llevel;
            private String start_hlevel;
            private String now_handicap;
            private String start_llevel;
            private String now_hlevel;
            private String start_handicap;
            private String name;
            private String id;

            private String start_oh;
            private String start_od;
            private String start_oa;
            private String now_oh;
            private String now_od;
            private String now_oa;

            public String getStart_oh() {
                return start_oh;
            }

            public void setStart_oh(String start_oh) {
                this.start_oh = start_oh;
            }

            public String getStart_od() {
                return start_od;
            }

            public void setStart_od(String start_od) {
                this.start_od = start_od;
            }

            public String getStart_oa() {
                return start_oa;
            }

            public void setStart_oa(String start_oa) {
                this.start_oa = start_oa;
            }

            public String getNow_oh() {
                return now_oh;
            }

            public void setNow_oh(String now_oh) {
                this.now_oh = now_oh;
            }

            public String getNow_od() {
                return now_od;
            }

            public void setNow_od(String now_od) {
                this.now_od = now_od;
            }

            public String getNow_oa() {
                return now_oa;
            }

            public void setNow_oa(String now_oa) {
                this.now_oa = now_oa;
            }

            public String getNow_llevel() {
                return now_llevel;
            }

            public void setNow_llevel(String now_llevel) {
                this.now_llevel = now_llevel;
            }

            public String getStart_hlevel() {
                return start_hlevel;
            }

            public void setStart_hlevel(String start_hlevel) {
                this.start_hlevel = start_hlevel;
            }

            public String getNow_handicap() {
                return now_handicap;
            }

            public void setNow_handicap(String now_handicap) {
                this.now_handicap = now_handicap;
            }

            public String getStart_llevel() {
                return start_llevel;
            }

            public void setStart_llevel(String start_llevel) {
                this.start_llevel = start_llevel;
            }

            public String getNow_hlevel() {
                return now_hlevel;
            }

            public void setNow_hlevel(String now_hlevel) {
                this.now_hlevel = now_hlevel;
            }

            public String getStart_handicap() {
                return start_handicap;
            }

            public void setStart_handicap(String start_handicap) {
                this.start_handicap = start_handicap;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
