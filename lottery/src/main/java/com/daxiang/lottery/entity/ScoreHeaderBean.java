package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/4/10.
 */

public class ScoreHeaderBean {

    /**
     * sign : 7912723d5ee9cb5713ee326750baaabd
     * msg : 成功
     * code : 0
     * data : {"items":{"date":null,"score":"0:2","away_name":"查卡里塔青年","away_logo":"https://m.api.iuliao.com/teamlogo/pic_985.jpg","home_name":"拉普拉塔大学生","home_logo":"https://img.iuliao.com/display/football/team/979.png","league_name":"阿超","half_score":"0:1","home_rank":"11","away_rank":"26","status":"Played"},"timeStamp":1523342838934}
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
         * items : {"date":null,"score":"0:2","away_name":"查卡里塔青年","away_logo":"https://m.api.iuliao.com/teamlogo/pic_985.jpg","home_name":"拉普拉塔大学生","home_logo":"https://img.iuliao.com/display/football/team/979.png","league_name":"阿超","half_score":"0:1","home_rank":"11","away_rank":"26","status":"Played"}
         * timeStamp : 1523342838934
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
             * date : null
             * score : 0:2
             * away_name : 查卡里塔青年
             * away_logo : https://m.api.iuliao.com/teamlogo/pic_985.jpg
             * home_name : 拉普拉塔大学生
             * home_logo : https://img.iuliao.com/display/football/team/979.png
             * league_name : 阿超
             * half_score : 0:1
             * home_rank : 11
             * away_rank : 26
             * status : Played
             */

            private String date;
            private String score;
            private String away_name;
            private String away_logo;
            private String home_name;
            private String home_logo;
            private String league_name;
            private String half_score;
            private String home_rank;
            private String away_rank;
            private String status;
            private String state;
            private String minute;

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getMinute() {
                return minute;
            }

            public void setMinute(String minute) {
                this.minute = minute;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getAway_name() {
                return away_name;
            }

            public void setAway_name(String away_name) {
                this.away_name = away_name;
            }

            public String getAway_logo() {
                return away_logo;
            }

            public void setAway_logo(String away_logo) {
                this.away_logo = away_logo;
            }

            public String getHome_name() {
                return home_name;
            }

            public void setHome_name(String home_name) {
                this.home_name = home_name;
            }

            public String getHome_logo() {
                return home_logo;
            }

            public void setHome_logo(String home_logo) {
                this.home_logo = home_logo;
            }

            public String getLeague_name() {
                return league_name;
            }

            public void setLeague_name(String league_name) {
                this.league_name = league_name;
            }

            public String getHalf_score() {
                return half_score;
            }

            public void setHalf_score(String half_score) {
                this.half_score = half_score;
            }

            public String getHome_rank() {
                return home_rank;
            }

            public void setHome_rank(String home_rank) {
                this.home_rank = home_rank;
            }

            public String getAway_rank() {
                return away_rank;
            }

            public void setAway_rank(String away_rank) {
                this.away_rank = away_rank;
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
