package com.daxiang.lottery.score;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/8
 * @describe May the Buddha bless bug-free!!!
 */
public class ScoreBean {

    /**
     * sign : 8404a2e51e95ab048e72c860167343ae
     * msg : 成功
     * code : 0
     * data : {"items":[{"date":"2017-06-08","num":"周四012","rank_h":"","guest_short_cn":"西部铁路","full_score":"VS","minute":"","league_short_cn":"阿根廷杯","date_time":"06-09 01:45","half_score":"","home_short_cn":"奥林匹奥","let":-1,"rank_a":"","m_id":95127,"status":"Fixture"},{"date":"2017-06-08","num":"周四011","rank_h":"","guest_short_cn":"英格兰20","full_score":"VS","minute":"","league_short_cn":"世青赛","date_time":"06-08 19:00","half_score":"","home_short_cn":"意大利20","let":1,"rank_a":"","m_id":95126,"status":"Fixture"},{"date":"2017-06-08","num":"周四010","rank_h":"","guest_short_cn":"委内瑞20","full_score":"VS","minute":"","league_short_cn":"世青赛","date_time":"06-08 16:00","half_score":"","home_short_cn":"乌拉圭20","let":-1,"rank_a":"","m_id":95125,"status":"Fixture"},{"date":"2017-06-08","num":"周四008","rank_h":"","guest_short_cn":"巴拉圭","full_score":"VS","minute":"","league_short_cn":"国际赛","date_time":"06-09 05:00","half_score":"","home_short_cn":"秘鲁","let":-1,"rank_a":"","m_id":95119,"status":"Fixture"},{"date":"2017-06-08","num":"周四005","rank_h":"4","guest_short_cn":"特多","full_score":"VS","minute":"","league_short_cn":"世预赛","date_time":"06-09 08:00","half_score":"","home_short_cn":"美国","let":-2,"rank_a":"6","m_id":95111,"status":"Fixture"},{"date":"2017-06-08","num":"周四004","rank_h":"9","guest_short_cn":"克鲁塞罗","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 08:00","half_score":"","home_short_cn":"巴伊亚","let":-1,"rank_a":"8","m_id":95110,"status":"Fixture"},{"date":"2017-06-08","num":"周四003","rank_h":"10","guest_short_cn":"维多利亚","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 06:30","half_score":"","home_short_cn":"圣保罗","let":-1,"rank_a":"19","m_id":95109,"status":"Fixture"},{"date":"2017-06-08","num":"周四002","rank_h":"20","guest_short_cn":"庞普雷塔","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 06:30","half_score":"","home_short_cn":"戈竞技","let":1,"rank_a":"6","m_id":95108,"status":"Fixture"},{"date":"2017-06-08","num":"周四001","rank_h":"3","guest_short_cn":"沙特","full_score":"VS","minute":"","league_short_cn":"世预赛","date_time":"06-08 18:00","half_score":"","home_short_cn":"澳大利亚","let":-1,"rank_a":"2","m_id":95107,"status":"Fixture"},{"date":"2017-06-07","num":"周三007","rank_h":"1","guest_short_cn":"格雷米奥","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 07:00","half_score":"","home_short_cn":"沙佩科","let":-1,"rank_a":"4","m_id":95104,"status":"Fixture"}],"timeStamp":1496908606535}
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
         * items : [{"date":"2017-06-08","num":"周四012","rank_h":"","guest_short_cn":"西部铁路","full_score":"VS","minute":"","league_short_cn":"阿根廷杯","date_time":"06-09 01:45","half_score":"","home_short_cn":"奥林匹奥","let":-1,"rank_a":"","m_id":95127,"status":"Fixture"},{"date":"2017-06-08","num":"周四011","rank_h":"","guest_short_cn":"英格兰20","full_score":"VS","minute":"","league_short_cn":"世青赛","date_time":"06-08 19:00","half_score":"","home_short_cn":"意大利20","let":1,"rank_a":"","m_id":95126,"status":"Fixture"},{"date":"2017-06-08","num":"周四010","rank_h":"","guest_short_cn":"委内瑞20","full_score":"VS","minute":"","league_short_cn":"世青赛","date_time":"06-08 16:00","half_score":"","home_short_cn":"乌拉圭20","let":-1,"rank_a":"","m_id":95125,"status":"Fixture"},{"date":"2017-06-08","num":"周四008","rank_h":"","guest_short_cn":"巴拉圭","full_score":"VS","minute":"","league_short_cn":"国际赛","date_time":"06-09 05:00","half_score":"","home_short_cn":"秘鲁","let":-1,"rank_a":"","m_id":95119,"status":"Fixture"},{"date":"2017-06-08","num":"周四005","rank_h":"4","guest_short_cn":"特多","full_score":"VS","minute":"","league_short_cn":"世预赛","date_time":"06-09 08:00","half_score":"","home_short_cn":"美国","let":-2,"rank_a":"6","m_id":95111,"status":"Fixture"},{"date":"2017-06-08","num":"周四004","rank_h":"9","guest_short_cn":"克鲁塞罗","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 08:00","half_score":"","home_short_cn":"巴伊亚","let":-1,"rank_a":"8","m_id":95110,"status":"Fixture"},{"date":"2017-06-08","num":"周四003","rank_h":"10","guest_short_cn":"维多利亚","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 06:30","half_score":"","home_short_cn":"圣保罗","let":-1,"rank_a":"19","m_id":95109,"status":"Fixture"},{"date":"2017-06-08","num":"周四002","rank_h":"20","guest_short_cn":"庞普雷塔","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 06:30","half_score":"","home_short_cn":"戈竞技","let":1,"rank_a":"6","m_id":95108,"status":"Fixture"},{"date":"2017-06-08","num":"周四001","rank_h":"3","guest_short_cn":"沙特","full_score":"VS","minute":"","league_short_cn":"世预赛","date_time":"06-08 18:00","half_score":"","home_short_cn":"澳大利亚","let":-1,"rank_a":"2","m_id":95107,"status":"Fixture"},{"date":"2017-06-07","num":"周三007","rank_h":"1","guest_short_cn":"格雷米奥","full_score":"VS","minute":"","league_short_cn":"巴甲","date_time":"06-09 07:00","half_score":"","home_short_cn":"沙佩科","let":-1,"rank_a":"4","m_id":95104,"status":"Fixture"}]
         * timeStamp : 1496908606535
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
             * date : 2017-06-08
             * num : 周四012
             * rank_h :
             * guest_short_cn : 西部铁路
             * full_score : VS
             * minute :
             * league_short_cn : 阿根廷杯
             * date_time : 06-09 01:45
             * half_score :
             * home_short_cn : 奥林匹奥
             * let : -1
             * rank_a :
             * m_id : 95127
             * status : Fixture
             */

            private String date;
            private String score1;
            private String score2;
            private String score3;
            private String score4;
            private String num;
            private String rank_h;
            private String guest_short_cn;
            private String extra_score;
            private String full_score;
            private String minute;
            private String league_short_cn;
            private String date_time;
            private String half_score;
            private String home_short_cn;
            private String let;
            private String rank_a;
            private String m_id;//之前竞彩官网的mid
            private String uMid;//有料数据的mid
            private String status;
            private String a_pic;
            private String h_pic;

            public String getScore1() {
                return score1;
            }

            public void setScore1(String score1) {
                this.score1 = score1;
            }

            public String getScore2() {
                return score2;
            }

            public void setScore2(String score2) {
                this.score2 = score2;
            }

            public String getScore3() {
                return score3;
            }

            public void setScore3(String score3) {
                this.score3 = score3;
            }

            public String getScore4() {
                return score4;
            }

            public void setScore4(String score4) {
                this.score4 = score4;
            }

            public String getExtra_score() {
                return extra_score;
            }

            public void setExtra_score(String extra_score) {
                this.extra_score = extra_score;
            }

            public String getuMid() {
                return uMid;
            }

            public void setuMid(String uMid) {
                this.uMid = uMid;
            }

            public String getA_pic() {
                return a_pic;
            }

            public void setA_pic(String a_pic) {
                this.a_pic = a_pic;
            }

            public String getH_pic() {
                return h_pic;
            }

            public void setH_pic(String h_pic) {
                this.h_pic = h_pic;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getRank_h() {
                return rank_h;
            }

            public void setRank_h(String rank_h) {
                this.rank_h = rank_h;
            }

            public String getGuest_short_cn() {
                return guest_short_cn;
            }

            public void setGuest_short_cn(String guest_short_cn) {
                this.guest_short_cn = guest_short_cn;
            }

            public String getFull_score() {
                return full_score;
            }

            public void setFull_score(String full_score) {
                this.full_score = full_score;
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

            public String getHalf_score() {
                return half_score;
            }

            public void setHalf_score(String half_score) {
                this.half_score = half_score;
            }

            public String getHome_short_cn() {
                return home_short_cn;
            }

            public void setHome_short_cn(String home_short_cn) {
                this.home_short_cn = home_short_cn;
            }

            public String getLet() {
                return let;
            }

            public void setLet(String let) {
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
