package com.daxiang.lottery.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class ChoosedContentFormBean  implements Serializable {
    private String awary;
    private String home;
    private float let;
    private String content;
    private String mid;
    private String playMethod;
    //赔率
    private String odds;
    //是否支持单关玩法

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getAwary() {
        return awary;
    }

    public void setAwary(String awary) {
        this.awary = awary;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public float getLet() {
        return let;
    }

    public void setLet(float let) {
        this.let = let;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlayMethod() {
        return playMethod;
    }

    public void setPlayMethod(String playMethod) {
        this.playMethod = playMethod;
    }

    @Override
    public String toString() {
        return "ChoosedContentFormBean{" +
                "awary='" + awary + '\'' +
                ", home='" + home + '\'' +
                ", let=" + let +
                ", content='" + content + '\'' +
                '}';
    }
}
