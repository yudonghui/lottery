package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/4/10.
 */

public class IntegrateData {
    /**
     * rank :
     * win_draw_lose : 10/7/9
     * point : 37
     * match_end : 26
     * get_lose_ball : 28/28
     */

    private String rank;
    private String win_draw_lose;
    private String point;
    private String match_end;
    private String get_lose_ball;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getWin_draw_lose() {
        return win_draw_lose;
    }

    public void setWin_draw_lose(String win_draw_lose) {
        this.win_draw_lose = win_draw_lose;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getMatch_end() {
        return match_end;
    }

    public void setMatch_end(String match_end) {
        this.match_end = match_end;
    }

    public String getGet_lose_ball() {
        return get_lose_ball;
    }

    public void setGet_lose_ball(String get_lose_ball) {
        this.get_lose_ball = get_lose_ball;
    }
}
