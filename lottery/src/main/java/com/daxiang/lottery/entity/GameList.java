package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/1/4.
 */

public class GameList {

    /**
     * homeShortCn : 莱加内斯
     * guestShortCn : 比利亚雷
     * halfScore : null
     * liveScore :
     * session : 20180104001
     * mid : 102735
     * fullScore : null
     * let : 1
     * kickOffTime : 1515088800000
     */

    private String homeShortCn;
    private String guestShortCn;
    private String halfScore;
    private String liveScore;
    private String session;
    private String mid;
    private String uMid;
    private String fullScore;
    private String let;
    private long kickOffTime;
    //下面是篮球
    private String score;
    private String presetScore;

    public String getuMid() {
        return uMid;
    }

    public void setuMid(String uMid) {
        this.uMid = uMid;
    }

    public String getPresetScore() {
        return presetScore;
    }

    public void setPresetScore(String presetScore) {
        this.presetScore = presetScore;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHomeShortCn() {
        return homeShortCn;
    }

    public void setHomeShortCn(String homeShortCn) {
        this.homeShortCn = homeShortCn;
    }

    public String getGuestShortCn() {
        return guestShortCn;
    }

    public void setGuestShortCn(String guestShortCn) {
        this.guestShortCn = guestShortCn;
    }

    public String getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }

    public String getLiveScore() {
        return liveScore;
    }

    public void setLiveScore(String liveScore) {
        this.liveScore = liveScore;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    public String getLet() {
        return let;
    }

    public void setLet(String let) {
        this.let = let;
    }

    public long getKickOffTime() {
        return kickOffTime;
    }

    public void setKickOffTime(long kickOffTime) {
        this.kickOffTime = kickOffTime;
    }
}
