package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/4/12.
 */

public class NewMatchData {

    /**
     * sign : a31881764d6127c694bf9cb24b6fa7e4
     * msg : 获取最新一期竞足赛事成功
     * code : 0
     * data : {"items":{"uMid":1078113,"guest_short_cn":"什鲁斯","rank_h":null,"full_score":"VS","a_pic":"http://static.sporttery.com/images/2013/01/12/11_1529112801.png","minute":"","league_short_cn":"英甲","date_time":"04-13 02:45","h_pic":"http://static.sporttery.com/images/2013/01/12/11_1438311731.png","home_short_cn":"布拉德福","half_score":"","let":1,"rank_a":null,"m_id":"106564","status":"Fixture"},"timeStamp":1523530785645}
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
         * items : {"uMid":1078113,"guest_short_cn":"什鲁斯","rank_h":null,"full_score":"VS","a_pic":"http://static.sporttery.com/images/2013/01/12/11_1529112801.png","minute":"","league_short_cn":"英甲","date_time":"04-13 02:45","h_pic":"http://static.sporttery.com/images/2013/01/12/11_1438311731.png","home_short_cn":"布拉德福","half_score":"","let":1,"rank_a":null,"m_id":"106564","status":"Fixture"}
         * timeStamp : 1523530785645
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
             * uMid : 1078113
             * guest_short_cn : 什鲁斯
             * rank_h : null
             * full_score : VS
             * a_pic : http://static.sporttery.com/images/2013/01/12/11_1529112801.png
             * minute :
             * league_short_cn : 英甲
             * date_time : 04-13 02:45
             * h_pic : http://static.sporttery.com/images/2013/01/12/11_1438311731.png
             * home_short_cn : 布拉德福
             * half_score :
             * let : 1
             * rank_a : null
             * m_id : 106564
             * status : Fixture
             */

            private String uMid;
            private String guest_short_cn;
            private String rank_h;
            private String full_score;
            private String a_pic;
            private String minute;
            private String league_short_cn;
            private String date_time;
            private String h_pic;
            private String home_short_cn;
            private String half_score;
            private int let;
            private String rank_a;
            private String m_id;
            private String status;

            public String getUMid() {
                return uMid;
            }

            public void setUMid(String uMid) {
                this.uMid = uMid;
            }

            public String getGuest_short_cn() {
                return guest_short_cn;
            }

            public void setGuest_short_cn(String guest_short_cn) {
                this.guest_short_cn = guest_short_cn;
            }

            public String getRank_h() {
                return rank_h;
            }

            public void setRank_h(String rank_h) {
                this.rank_h = rank_h;
            }

            public String getFull_score() {
                return full_score;
            }

            public void setFull_score(String full_score) {
                this.full_score = full_score;
            }

            public String getA_pic() {
                return a_pic;
            }

            public void setA_pic(String a_pic) {
                this.a_pic = a_pic;
            }

            public String getMinute() {
                return minute;
            }

            public void setMinute(String minute) {
                this.minute = minute;
            }

            public String getLeague_short_cn() {
                return league_short_cn;
            }

            public void setLeague_short_cn(String league_short_cn) {
                this.league_short_cn = league_short_cn;
            }

            public String getDate_time() {
                return date_time;
            }

            public void setDate_time(String date_time) {
                this.date_time = date_time;
            }

            public String getH_pic() {
                return h_pic;
            }

            public void setH_pic(String h_pic) {
                this.h_pic = h_pic;
            }

            public String getHome_short_cn() {
                return home_short_cn;
            }

            public void setHome_short_cn(String home_short_cn) {
                this.home_short_cn = home_short_cn;
            }

            public String getHalf_score() {
                return half_score;
            }

            public void setHalf_score(String half_score) {
                this.half_score = half_score;
            }

            public int getLet() {
                return let;
            }

            public void setLet(int let) {
                this.let = let;
            }

            public String getRank_a() {
                return rank_a;
            }

            public void setRank_a(String rank_a) {
                this.rank_a = rank_a;
            }

            public String getM_id() {
                return m_id;
            }

            public void setM_id(String m_id) {
                this.m_id = m_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
