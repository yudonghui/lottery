package com.daxiang.lottery.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class ScoreFootData implements Serializable {

    private DetailsBean Details;

    @Override
    public String toString() {
        return "ScoreFootData{" +
                "Details=" + Details +
                ", Matches=" + Matches +
                '}';
    }

    /**
     * id : 10328
     * match_date : 2016-10-09
     * bgcolor :  #81C0C0;
     * lid : 101
     * lname : 日联杯
     * away_id : 1
     * host_id : 1
     * match_id : 918874
     * week : 周日001
     * start_time : 2016-10-09T13:00:00+08:00
     * status : 未
     * host_rank : [1]J联
     * away_red_card :
     * host_red_card :
     * host_name : 浦和红钻
     * away_rank : J联[11]
     * away_name : FC东京
     * host_half_score :
     * away_half_score :
     * rq_number : -1
     * host_score :
     * away_score :
     * sp_win : 1.44
     * sp_draw : 4.10
     * sp_lose : 5.25
     * rq_sp_win : 2.38
     * rq_sp_draw : 3.50
     * rq_sp_lose : 2.36
     * jqs_result : null
     * bf_result :
     * bqc_result :
     * spf_result :
     * rqspf_result :
     * created_time : 2016-10-07T02:08:45+08:00
     * update_time : 2016-10-09T11:44:59+08:00
     */

    private List<MatchesBean> Matches;

    public DetailsBean getDetails() {
        return Details;
    }

    public void setDetails(DetailsBean Details) {
        this.Details = Details;
    }

    public List<MatchesBean> getMatches() {
        return Matches;
    }

    public void setMatches(List<MatchesBean> Matches) {
        this.Matches = Matches;
    }

    public static class DetailsBean {
        @SerializedName("817367")
        private List<?> value817367;
        @SerializedName("817368")
        private List<?> value817368;
        @SerializedName("817369")
        private List<?> value817369;
        @SerializedName("817448")
        private List<?> value817448;
        @SerializedName("817449")
        private List<?> value817449;
        @SerializedName("817450")
        private List<?> value817450;
        @SerializedName("817492")
        private List<?> value817492;
        @SerializedName("817493")
        private List<?> value817493;
        @SerializedName("868004")
        private List<?> value868004;
        @SerializedName("868111")
        private List<?> value868111;
        @SerializedName("868140")
        private List<?> value868140;
        @SerializedName("876139")
        private List<?> value876139;
        @SerializedName("882435")
        private List<?> value882435;
        @SerializedName("882437")
        private List<?> value882437;
        @SerializedName("883951")
        private List<?> value883951;
        @SerializedName("883958")
        private List<?> value883958;
        @SerializedName("883959")
        private List<?> value883959;
        @SerializedName("883968")
        private List<?> value883968;
        @SerializedName("887524")
        private List<?> value887524;
        @SerializedName("918874")
        private List<?> value918874;
        @SerializedName("918875")
        private List<?> value918875;
        @SerializedName("918965")
        private List<?> value918965;
        @SerializedName("919469")
        private List<?> value919469;

        public List<?> getValue817367() {
            return value817367;
        }

        public void setValue817367(List<?> value817367) {
            this.value817367 = value817367;
        }

        public List<?> getValue817368() {
            return value817368;
        }

        public void setValue817368(List<?> value817368) {
            this.value817368 = value817368;
        }

        public List<?> getValue817369() {
            return value817369;
        }

        public void setValue817369(List<?> value817369) {
            this.value817369 = value817369;
        }

        public List<?> getValue817448() {
            return value817448;
        }

        public void setValue817448(List<?> value817448) {
            this.value817448 = value817448;
        }

        public List<?> getValue817449() {
            return value817449;
        }

        public void setValue817449(List<?> value817449) {
            this.value817449 = value817449;
        }

        public List<?> getValue817450() {
            return value817450;
        }

        public void setValue817450(List<?> value817450) {
            this.value817450 = value817450;
        }

        public List<?> getValue817492() {
            return value817492;
        }

        public void setValue817492(List<?> value817492) {
            this.value817492 = value817492;
        }

        public List<?> getValue817493() {
            return value817493;
        }

        public void setValue817493(List<?> value817493) {
            this.value817493 = value817493;
        }

        public List<?> getValue868004() {
            return value868004;
        }

        public void setValue868004(List<?> value868004) {
            this.value868004 = value868004;
        }

        public List<?> getValue868111() {
            return value868111;
        }

        public void setValue868111(List<?> value868111) {
            this.value868111 = value868111;
        }

        public List<?> getValue868140() {
            return value868140;
        }

        public void setValue868140(List<?> value868140) {
            this.value868140 = value868140;
        }

        public List<?> getValue876139() {
            return value876139;
        }

        public void setValue876139(List<?> value876139) {
            this.value876139 = value876139;
        }

        public List<?> getValue882435() {
            return value882435;
        }

        public void setValue882435(List<?> value882435) {
            this.value882435 = value882435;
        }

        public List<?> getValue882437() {
            return value882437;
        }

        public void setValue882437(List<?> value882437) {
            this.value882437 = value882437;
        }

        public List<?> getValue883951() {
            return value883951;
        }

        public void setValue883951(List<?> value883951) {
            this.value883951 = value883951;
        }

        public List<?> getValue883958() {
            return value883958;
        }

        public void setValue883958(List<?> value883958) {
            this.value883958 = value883958;
        }

        public List<?> getValue883959() {
            return value883959;
        }

        public void setValue883959(List<?> value883959) {
            this.value883959 = value883959;
        }

        public List<?> getValue883968() {
            return value883968;
        }

        public void setValue883968(List<?> value883968) {
            this.value883968 = value883968;
        }

        public List<?> getValue887524() {
            return value887524;
        }

        public void setValue887524(List<?> value887524) {
            this.value887524 = value887524;
        }

        public List<?> getValue918874() {
            return value918874;
        }

        public void setValue918874(List<?> value918874) {
            this.value918874 = value918874;
        }

        public List<?> getValue918875() {
            return value918875;
        }

        public void setValue918875(List<?> value918875) {
            this.value918875 = value918875;
        }

        public List<?> getValue918965() {
            return value918965;
        }

        public void setValue918965(List<?> value918965) {
            this.value918965 = value918965;
        }

        public List<?> getValue919469() {
            return value919469;
        }

        public void setValue919469(List<?> value919469) {
            this.value919469 = value919469;
        }
    }

    public static class MatchesBean implements Serializable{
        private int id;
        private String match_date;
        private String bgcolor;
        private int lid;
        private String lname;
        private int away_id;
        private int host_id;
        private int match_id;
        private String week;
        private String start_time;
        private String status;
        private String host_rank;
        private String away_red_card;
        private String host_red_card;
        private String host_name;
        private String away_rank;
        private String away_name;
        private String host_half_score;
        private String away_half_score;
        private String rq_number;
        private String host_score;
        private String away_score;
        private String sp_win;
        private String sp_draw;
        private String sp_lose;
        private String rq_sp_win;
        private String rq_sp_draw;
        private String rq_sp_lose;
        private String jqs_result;
        private String bf_result;
        private String bqc_result;
        private String spf_result;
        private String rqspf_result;
        private String created_time;
        private String update_time;

        @Override
        public String toString() {
            return "MatchesBean{" +
                    "id=" + id +
                    ", match_date='" + match_date + '\'' +
                    ", bgcolor='" + bgcolor + '\'' +
                    ", lid=" + lid +
                    ", lname='" + lname + '\'' +
                    ", away_id=" + away_id +
                    ", host_id=" + host_id +
                    ", match_id=" + match_id +
                    ", week='" + week + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", status='" + status + '\'' +
                    ", host_rank='" + host_rank + '\'' +
                    ", away_red_card='" + away_red_card + '\'' +
                    ", host_red_card='" + host_red_card + '\'' +
                    ", host_name='" + host_name + '\'' +
                    ", away_rank='" + away_rank + '\'' +
                    ", away_name='" + away_name + '\'' +
                    ", host_half_score='" + host_half_score + '\'' +
                    ", away_half_score='" + away_half_score + '\'' +
                    ", rq_number='" + rq_number + '\'' +
                    ", host_score='" + host_score + '\'' +
                    ", away_score='" + away_score + '\'' +
                    ", sp_win='" + sp_win + '\'' +
                    ", sp_draw='" + sp_draw + '\'' +
                    ", sp_lose='" + sp_lose + '\'' +
                    ", rq_sp_win='" + rq_sp_win + '\'' +
                    ", rq_sp_draw='" + rq_sp_draw + '\'' +
                    ", rq_sp_lose='" + rq_sp_lose + '\'' +
                    ", jqs_result='" + jqs_result + '\'' +
                    ", bf_result='" + bf_result + '\'' +
                    ", bqc_result='" + bqc_result + '\'' +
                    ", spf_result='" + spf_result + '\'' +
                    ", rqspf_result='" + rqspf_result + '\'' +
                    ", created_time='" + created_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMatch_date() {
            return match_date;
        }

        public void setMatch_date(String match_date) {
            this.match_date = match_date;
        }

        public String getBgcolor() {
            return bgcolor;
        }

        public void setBgcolor(String bgcolor) {
            this.bgcolor = bgcolor;
        }

        public int getLid() {
            return lid;
        }

        public void setLid(int lid) {
            this.lid = lid;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public int getAway_id() {
            return away_id;
        }

        public void setAway_id(int away_id) {
            this.away_id = away_id;
        }

        public int getHost_id() {
            return host_id;
        }

        public void setHost_id(int host_id) {
            this.host_id = host_id;
        }

        public int getMatch_id() {
            return match_id;
        }

        public void setMatch_id(int match_id) {
            this.match_id = match_id;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
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

        public String getHost_rank() {
            return host_rank;
        }

        public void setHost_rank(String host_rank) {
            this.host_rank = host_rank;
        }

        public String getAway_red_card() {
            return away_red_card;
        }

        public void setAway_red_card(String away_red_card) {
            this.away_red_card = away_red_card;
        }

        public String getHost_red_card() {
            return host_red_card;
        }

        public void setHost_red_card(String host_red_card) {
            this.host_red_card = host_red_card;
        }

        public String getHost_name() {
            return host_name;
        }

        public void setHost_name(String host_name) {
            this.host_name = host_name;
        }

        public String getAway_rank() {
            return away_rank;
        }

        public void setAway_rank(String away_rank) {
            this.away_rank = away_rank;
        }

        public String getAway_name() {
            return away_name;
        }

        public void setAway_name(String away_name) {
            this.away_name = away_name;
        }

        public String getHost_half_score() {
            return host_half_score;
        }

        public void setHost_half_score(String host_half_score) {
            this.host_half_score = host_half_score;
        }

        public String getAway_half_score() {
            return away_half_score;
        }

        public void setAway_half_score(String away_half_score) {
            this.away_half_score = away_half_score;
        }

        public String getRq_number() {
            return rq_number;
        }

        public void setRq_number(String rq_number) {
            this.rq_number = rq_number;
        }

        public String getHost_score() {
            return host_score;
        }

        public void setHost_score(String host_score) {
            this.host_score = host_score;
        }

        public String getAway_score() {
            return away_score;
        }

        public void setAway_score(String away_score) {
            this.away_score = away_score;
        }

        public String getSp_win() {
            return sp_win;
        }

        public void setSp_win(String sp_win) {
            this.sp_win = sp_win;
        }

        public String getSp_draw() {
            return sp_draw;
        }

        public void setSp_draw(String sp_draw) {
            this.sp_draw = sp_draw;
        }

        public String getSp_lose() {
            return sp_lose;
        }

        public void setSp_lose(String sp_lose) {
            this.sp_lose = sp_lose;
        }

        public String getRq_sp_win() {
            return rq_sp_win;
        }

        public void setRq_sp_win(String rq_sp_win) {
            this.rq_sp_win = rq_sp_win;
        }

        public String getRq_sp_draw() {
            return rq_sp_draw;
        }

        public void setRq_sp_draw(String rq_sp_draw) {
            this.rq_sp_draw = rq_sp_draw;
        }

        public String getRq_sp_lose() {
            return rq_sp_lose;
        }

        public void setRq_sp_lose(String rq_sp_lose) {
            this.rq_sp_lose = rq_sp_lose;
        }

        public String getJqs_result() {
            return jqs_result;
        }

        public void setJqs_result(String jqs_result) {
            this.jqs_result = jqs_result;
        }

        public String getBf_result() {
            return bf_result;
        }

        public void setBf_result(String bf_result) {
            this.bf_result = bf_result;
        }

        public String getBqc_result() {
            return bqc_result;
        }

        public void setBqc_result(String bqc_result) {
            this.bqc_result = bqc_result;
        }

        public String getSpf_result() {
            return spf_result;
        }

        public void setSpf_result(String spf_result) {
            this.spf_result = spf_result;
        }

        public String getRqspf_result() {
            return rqspf_result;
        }

        public void setRqspf_result(String rqspf_result) {
            this.rqspf_result = rqspf_result;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
