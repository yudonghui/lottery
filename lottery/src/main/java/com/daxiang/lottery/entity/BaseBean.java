package com.daxiang.lottery.entity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/17
 * @describe May the Buddha bless bug-free!!!
 */
public class BaseBean {

    /**
     * arr : [{"date":"2017-09-21","score":"2:1","away_name":"哥德堡","wdr_detail":"胜","home_name":"哈马比","league_name":"瑞典超"},{"date":"2017-04-27","score":"1:1","away_name":"哈马比","wdr_detail":"平","home_name":"哥德堡","league_name":"瑞典超"},{"date":"2016-08-21","score":"2:0","away_name":"哥德堡","wdr_detail":"胜","home_name":"哈马比","league_name":"瑞典超"},{"date":"2016-05-24","score":"2:1","away_name":"哈马比","wdr_detail":"胜","home_name":"哥德堡","league_name":"瑞典超"},{"date":"2015-09-20","score":"1:0","away_name":"哈马比","wdr_detail":"胜","home_name":"哥德堡","league_name":"瑞典超"},{"date":"2015-06-07","score":"0:1","away_name":"哥德堡","wdr_detail":"负","home_name":"哈马比","league_name":"瑞典超"},{"date":"2009-09-20","score":"2:0","away_name":"哈马比","wdr_detail":"胜","home_name":"哥德堡","league_name":"瑞典超"},{"date":"2009-04-24","score":"0:1","away_name":"哥德堡","wdr_detail":"负","home_name":"哈马比","league_name":"瑞典超"},{"date":"2008-10-07","score":"2:0","away_name":"哈马比","wdr_detail":"胜","home_name":"哥德堡","league_name":"瑞典超"},{"date":"2008-05-04","score":"0:0","away_name":"哥德堡","wdr_detail":"平","home_name":"哈马比","league_name":"瑞典超"}]
     * draw_num : 2
     * lose_num : 6
     * get_ball : 5
     * lose_ball : 12
     * win_num : 2
     */

    private int draw_num;
    private int lose_num;
    private int get_ball;
    private int lose_ball;
    private int win_num;
    private List<ArrBean> arr;

    public int getDraw_num() {
        return draw_num;
    }

    public void setDraw_num(int draw_num) {
        this.draw_num = draw_num;
    }

    public int getLose_num() {
        return lose_num;
    }

    public void setLose_num(int lose_num) {
        this.lose_num = lose_num;
    }

    public int getGet_ball() {
        return get_ball;
    }

    public void setGet_ball(int get_ball) {
        this.get_ball = get_ball;
    }

    public int getLose_ball() {
        return lose_ball;
    }

    public void setLose_ball(int lose_ball) {
        this.lose_ball = lose_ball;
    }

    public int getWin_num() {
        return win_num;
    }

    public void setWin_num(int win_num) {
        this.win_num = win_num;
    }

    public List<ArrBean> getArr() {
        return arr;
    }

    public void setArr(List<ArrBean> arr) {
        this.arr = arr;
    }

    public static class ArrBean {
        /**
         * date : 2017-09-21
         * score : 2:1
         * away_name : 哥德堡
         * wdr_detail : 胜
         * home_name : 哈马比
         * league_name : 瑞典超
         */

        private String date;
        private String score;
        private String away_name;
        private String wdr_detail;
        private String home_name;
        private String league_name;
        private String home_id;
        private String away_id;

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

        public String getAway_id() {
            return away_id;
        }

        public void setAway_id(String away_id) {
            this.away_id = away_id;
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

        public String getWdr_detail() {
            return wdr_detail;
        }

        public void setWdr_detail(String wdr_detail) {
            this.wdr_detail = wdr_detail;
        }

        public String getHome_name() {
            return home_name;
        }

        public void setHome_name(String home_name) {
            this.home_name = home_name;
        }

        public String getLeague_name() {
            return league_name;
        }

        public void setLeague_name(String league_name) {
            this.league_name = league_name;
        }
    }
}
