package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/7/4.
 */

public class PlayingedBean {

    /**
     * sign : bee14ba8cb84d4e8fc305edece1ca35b
     * msg : 成功
     * code : 0
     * data : {"finish":[{"score":"2:2","away_name":"洛杉矶FC","away_logo":"https://m.api.iuliao.com/teamlogo/pic_451749.jpg","home_name":"休斯顿迪纳摩","home_logo":"https://m.api.iuliao.com/teamlogo/pic_4376.jpg","mid":1162819,"time":1530666000000},{"score":"1:3","away_name":"松兹瓦尔","away_logo":"https://img.iuliao.com/display/football/team/30.png","home_name":"斯瑞斯","home_logo":"https://m.api.iuliao.com/teamlogo/pic_3244.jpg","mid":1149854,"time":1530637200000}],"fixture":[{"score":"VS","odd1":"3.90","away_name":"华盛顿联队","away_logo":"https://m.api.iuliao.com/teamlogo/pic_347.jpg","odd0":"4.70","home_name":"洛杉矶银河","home_logo":"https://m.api.iuliao.com/teamlogo/pic_256.jpg","odd3":"1.45","mid":1162824,"time":1530757800000,"minute":""},{"score":"VS","odd1":"3.25","away_name":"肯萨斯体育会","away_logo":"https://m.api.iuliao.com/teamlogo/pic_309.jpg","odd0":"2.40","home_name":"皇家盐湖城","home_logo":"https://m.api.iuliao.com/teamlogo/pic_2502.jpg","odd3":"2.30","mid":1162823,"time":1530756000000,"minute":""}],"timeStamp":1530698765398}
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
         * finish : [{"score":"2:2","away_name":"洛杉矶FC","away_logo":"https://m.api.iuliao.com/teamlogo/pic_451749.jpg","home_name":"休斯顿迪纳摩","home_logo":"https://m.api.iuliao.com/teamlogo/pic_4376.jpg","mid":1162819,"time":1530666000000},{"score":"1:3","away_name":"松兹瓦尔","away_logo":"https://img.iuliao.com/display/football/team/30.png","home_name":"斯瑞斯","home_logo":"https://m.api.iuliao.com/teamlogo/pic_3244.jpg","mid":1149854,"time":1530637200000}]
         * fixture : [{"score":"VS","odd1":"3.90","away_name":"华盛顿联队","away_logo":"https://m.api.iuliao.com/teamlogo/pic_347.jpg","odd0":"4.70","home_name":"洛杉矶银河","home_logo":"https://m.api.iuliao.com/teamlogo/pic_256.jpg","odd3":"1.45","mid":1162824,"time":1530757800000,"minute":""},{"score":"VS","odd1":"3.25","away_name":"肯萨斯体育会","away_logo":"https://m.api.iuliao.com/teamlogo/pic_309.jpg","odd0":"2.40","home_name":"皇家盐湖城","home_logo":"https://m.api.iuliao.com/teamlogo/pic_2502.jpg","odd3":"2.30","mid":1162823,"time":1530756000000,"minute":""}]
         * timeStamp : 1530698765398
         */

        private long timeStamp;
        private List<FinishBean> finish;
        private List<FixtureBean> fixture;

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public List<FinishBean> getFinish() {
            return finish;
        }

        public void setFinish(List<FinishBean> finish) {
            this.finish = finish;
        }

        public List<FixtureBean> getFixture() {
            return fixture;
        }

        public void setFixture(List<FixtureBean> fixture) {
            this.fixture = fixture;
        }

        public static class FinishBean {
            /**
             * score : 2:2
             * away_name : 洛杉矶FC
             * away_logo : https://m.api.iuliao.com/teamlogo/pic_451749.jpg
             * home_name : 休斯顿迪纳摩
             * home_logo : https://m.api.iuliao.com/teamlogo/pic_4376.jpg
             * mid : 1162819
             * time : 1530666000000
             */

            private String score;
            private String away_name;
            private String away_logo;
            private String home_name;
            private String home_logo;
            private String mid;
            private long time;

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

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }
        }

        public static class FixtureBean {
            /**
             * score : VS
             * odd1 : 3.90
             * away_name : 华盛顿联队
             * away_logo : https://m.api.iuliao.com/teamlogo/pic_347.jpg
             * odd0 : 4.70
             * home_name : 洛杉矶银河
             * home_logo : https://m.api.iuliao.com/teamlogo/pic_256.jpg
             * odd3 : 1.45
             * mid : 1162824
             * time : 1530757800000
             * minute :
             */

            private String score;
            private String odd1;
            private String away_name;
            private String away_logo;
            private String odd0;
            private String home_name;
            private String home_logo;
            private String odd3;
            private String mid;
            private long time;
            private String minute;

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getOdd1() {
                return odd1;
            }

            public void setOdd1(String odd1) {
                this.odd1 = odd1;
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

            public String getOdd0() {
                return odd0;
            }

            public void setOdd0(String odd0) {
                this.odd0 = odd0;
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

            public String getOdd3() {
                return odd3;
            }

            public void setOdd3(String odd3) {
                this.odd3 = odd3;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getMinute() {
                return minute;
            }

            public void setMinute(String minute) {
                this.minute = minute;
            }
        }
    }
}
