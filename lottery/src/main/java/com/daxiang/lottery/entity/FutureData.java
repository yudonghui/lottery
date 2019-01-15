package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/4/10.
 */

public class FutureData {
    /**
     * date : 2018-04-11
     * away_name : 哈马比
     * home_name : 哥德堡
     * league_name : 瑞典超
     * days_apart : 1
     */

    private String date;
    private String away_name;
    private String home_name;
    private String league_name;
    private String days_apart;
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

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
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

    public String getDays_apart() {
        return days_apart;
    }

    public void setDays_apart(String days_apart) {
        this.days_apart = days_apart;
    }
}
