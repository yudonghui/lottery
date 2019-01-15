package com.daxiang.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class ScoreBasketData implements Serializable{

    /**
     * id : 1383
     * week : 周日302
     * match_id : 5343973
     * match_date : 2016-10-10
     * lid : 486
     * lname : WNBA
     * start_time : 10-10 03:00
     * status : 完
     * host_name : &amp;nbsp;天猫
     * away_name : 火花
     * away_score1 : 21
     * away_score2 : 13
     * away_score3 : 22
     * away_score4 : 22
     * away_score_js :
     * away_score_finish : 78
     * host_score1 : 18
     * host_score2 : 18
     * host_score3 : 24
     * host_score4 : 16
     * host_score_js :
     * host_score_finish : 76
     * score_diff : +2
     * host_sf_result : 主&amp;nbsp;&amp;nbsp;1.250
     * away_sf_result : 客&amp;nbsp;&amp;nbsp;2.900
     * host_dxf_result : ＜161.5&amp;nbsp;&amp;nbsp;1.70
     * away_dxf_result : ＞161.5&amp;nbsp;&amp;nbsp;1.81
     * host_rfsf_result : 主&amp;nbsp;&amp;nbsp;1.75
     * away_rfsf_result : &amp;nbsp;客&amp;nbsp;&amp;nbsp;1.75
     * rf_number : -6.5
     * create_time : 2016-10-05 22:26:37
     * update_time : 2016-10-10 22:23:06
     */

    private List<MatchsBean> Matchs;

    public List<MatchsBean> getMatchs() {
        return Matchs;
    }

    public void setMatchs(List<MatchsBean> Matchs) {
        this.Matchs = Matchs;
    }

    public static class MatchsBean  implements Serializable{
        private int id;
        private String week;
        private String match_id;
        private String match_date;
        private String lid;
        private String lname;
        private String start_time;
        private String status;
        private String host_name;
        private String away_name;
        private String away_score1;
        private String away_score2;
        private String away_score3;
        private String away_score4;
        private String away_score_js;
        private String away_score_finish;
        private String host_score1;
        private String host_score2;
        private String host_score3;
        private String host_score4;
        private String host_score_js;
        private String host_score_finish;
        private String score_diff;
        private String host_sf_result;
        private String away_sf_result;
        private String host_dxf_result;
        private String away_dxf_result;
        private String host_rfsf_result;
        private String away_rfsf_result;
        private String rf_number;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getMatch_id() {
            return match_id;
        }

        public void setMatch_id(String match_id) {
            this.match_id = match_id;
        }

        public String getMatch_date() {
            return match_date;
        }

        public void setMatch_date(String match_date) {
            this.match_date = match_date;
        }

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHost_name() {
            return host_name;
        }

        public void setHost_name(String host_name) {
            this.host_name = host_name;
        }

        public String getAway_name() {
            return away_name;
        }

        public void setAway_name(String away_name) {
            this.away_name = away_name;
        }

        public String getAway_score1() {
            return away_score1;
        }

        public void setAway_score1(String away_score1) {
            this.away_score1 = away_score1;
        }

        public String getAway_score2() {
            return away_score2;
        }

        public void setAway_score2(String away_score2) {
            this.away_score2 = away_score2;
        }

        public String getAway_score3() {
            return away_score3;
        }

        public void setAway_score3(String away_score3) {
            this.away_score3 = away_score3;
        }

        public String getAway_score4() {
            return away_score4;
        }

        public void setAway_score4(String away_score4) {
            this.away_score4 = away_score4;
        }

        public String getAway_score_js() {
            return away_score_js;
        }

        public void setAway_score_js(String away_score_js) {
            this.away_score_js = away_score_js;
        }

        public String getAway_score_finish() {
            return away_score_finish;
        }

        public void setAway_score_finish(String away_score_finish) {
            this.away_score_finish = away_score_finish;
        }

        public String getHost_score1() {
            return host_score1;
        }

        public void setHost_score1(String host_score1) {
            this.host_score1 = host_score1;
        }

        public String getHost_score2() {
            return host_score2;
        }

        public void setHost_score2(String host_score2) {
            this.host_score2 = host_score2;
        }

        public String getHost_score3() {
            return host_score3;
        }

        public void setHost_score3(String host_score3) {
            this.host_score3 = host_score3;
        }

        public String getHost_score4() {
            return host_score4;
        }

        public void setHost_score4(String host_score4) {
            this.host_score4 = host_score4;
        }

        public String getHost_score_js() {
            return host_score_js;
        }

        public void setHost_score_js(String host_score_js) {
            this.host_score_js = host_score_js;
        }

        public String getHost_score_finish() {
            return host_score_finish;
        }

        public void setHost_score_finish(String host_score_finish) {
            this.host_score_finish = host_score_finish;
        }

        public String getScore_diff() {
            return score_diff;
        }

        public void setScore_diff(String score_diff) {
            this.score_diff = score_diff;
        }

        public String getHost_sf_result() {
            return host_sf_result;
        }

        public void setHost_sf_result(String host_sf_result) {
            this.host_sf_result = host_sf_result;
        }

        public String getAway_sf_result() {
            return away_sf_result;
        }

        public void setAway_sf_result(String away_sf_result) {
            this.away_sf_result = away_sf_result;
        }

        public String getHost_dxf_result() {
            return host_dxf_result;
        }

        public void setHost_dxf_result(String host_dxf_result) {
            this.host_dxf_result = host_dxf_result;
        }

        public String getAway_dxf_result() {
            return away_dxf_result;
        }

        public void setAway_dxf_result(String away_dxf_result) {
            this.away_dxf_result = away_dxf_result;
        }

        public String getHost_rfsf_result() {
            return host_rfsf_result;
        }

        public void setHost_rfsf_result(String host_rfsf_result) {
            this.host_rfsf_result = host_rfsf_result;
        }

        public String getAway_rfsf_result() {
            return away_rfsf_result;
        }

        public void setAway_rfsf_result(String away_rfsf_result) {
            this.away_rfsf_result = away_rfsf_result;
        }

        public String getRf_number() {
            return rf_number;
        }

        public void setRf_number(String rf_number) {
            this.rf_number = rf_number;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
