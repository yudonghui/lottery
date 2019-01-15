package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/9.
 */

public class HotRecommendData {

    /**
     * code : 0
     * msg : 获取热门推荐成功
     * data : [{"oneMoney":2,"sendFlag":0,"commissionScale":10,"userNum":1,"orderId":"2018030616201102002","winStatus":0,"joinNum":1,"isCertified":0,"totalMoney":200,"winInfo":"1中1","remark":"跟单最多","userName":"魑魅魍魉","userId":"7091117051800005","declaration":"跟上我，带你一起去领奖！","openType":3,"aftertaxBonus":null,"lotCode":"42","totalBuy":200,"theoreticalPrize":400,"orderCode":"FZ20180309135200_2803","commission":null,"deadline":1520592600000}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends RecommendBaseData{

    }
}
