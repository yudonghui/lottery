package com.daxiang.lottery.entity;

import java.io.Serializable;

/**
 * Created by Android on 2018/3/9.
 */

public class RecommendBaseData implements Serializable {
    /**
     * orderId : 2016120717151800000
     * orderCode : HM20161207184015_5595832
     * userId : 2016092210470301000
     * userName : 13162821161
     * channel : 6000001
     * declaration : 跟着成功人的脚步走
     * openType : 1
     * commissionType : 1
     * commissionScale : 2
     * pretaxBonus : null
     * aftertaxBonus : null
     * commission : null
     * joinNum : 1
     * theoreticalPrize : 442
     * totalNum : 10
     * buyedNum : 8
     * buyRatio : 80
     * oneMoney : 1
     * totalMoney : 10
     * isFull : 0
     * fullTime : null
     * isGuaranteed : 1
     * guaranteedMoney : 1
     * guaranteedRatio : 10
     * sendFlag : 0
     * lotCode : 42
     */

    private String orderId;
    private String orderCode;       //订单号
    private String userId;            //用户id
    private String userName;        //用户昵称
    private String channel;         //
    private String declaration;     //备注
    private String openType;            //保密方式
    private String commissionType;            //提成方式
    private String commissionScale;            //提成比例
    private String pretaxBonus;            //税前奖金
    private String aftertaxBonus;          //税后奖金
    private String commission;            //提成金额
    private String joinNum;                  //参与人数
    private double theoreticalPrize;         //理论奖金
    private int totalNum;               //总金额
    private int buyedNum;               //购买金额
    private String buyRatio;               //购买进度
    private String oneMoney;               //单注金额
    private String totalMoney;              //总金额
    private String isFull;                //
    private String fullTime;           //
    private String isGuaranteed;            //是否保底
    private String guaranteedMoney;         //保底金额
    private String guaranteedRatio;         //保底比例
    private String sendFlag;
    private String lotCode;              //彩种id
    private String deadline;
    private String winStatus;          //是否中奖
    private String totalBuy;          //跟单金额
    private String winInfo;          //几中几.
    private String userNum;
    private String isCertified;   //0普通大神，1认证大神
    private String remark;
    private String winRate;//命中率
    private String gainRate;//盈利率

    public String getWinRate() {
        return winRate;
    }

    public void setWinRate(String winRate) {
        this.winRate = winRate;
    }

    public String getGainRate() {
        return gainRate;
    }

    public void setGainRate(String gainRate) {
        this.gainRate = gainRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsCertified() {
        return isCertified;
    }

    public void setIsCertified(String isCertified) {
        this.isCertified = isCertified;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getWinInfo() {
        return winInfo;
    }

    public void setWinInfo(String winInfo) {
        this.winInfo = winInfo;
    }

    public String getWinStatus() {
        return winStatus;
    }

    public void setWinStatus(String winStatus) {
        this.winStatus = winStatus;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getCommissionScale() {
        return commissionScale;
    }

    public void setCommissionScale(String commissionScale) {
        this.commissionScale = commissionScale;
    }

    public String getPretaxBonus() {
        return pretaxBonus;
    }

    public void setPretaxBonus(String pretaxBonus) {
        this.pretaxBonus = pretaxBonus;
    }

    public String getAftertaxBonus() {
        return aftertaxBonus;
    }

    public void setAftertaxBonus(String aftertaxBonus) {
        this.aftertaxBonus = aftertaxBonus;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public double getTheoreticalPrize() {
        return theoreticalPrize;
    }

    public void setTheoreticalPrize(double theoreticalPrize) {
        this.theoreticalPrize = theoreticalPrize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getBuyedNum() {
        return buyedNum;
    }

    public void setBuyedNum(int buyedNum) {
        this.buyedNum = buyedNum;
    }

    public String getBuyRatio() {
        return buyRatio;
    }

    public void setBuyRatio(String buyRatio) {
        this.buyRatio = buyRatio;
    }

    public String getOneMoney() {
        return oneMoney;
    }

    public void setOneMoney(String oneMoney) {
        this.oneMoney = oneMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getIsFull() {
        return isFull;
    }

    public void setIsFull(String isFull) {
        this.isFull = isFull;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getIsGuaranteed() {
        return isGuaranteed;
    }

    public void setIsGuaranteed(String isGuaranteed) {
        this.isGuaranteed = isGuaranteed;
    }

    public String getGuaranteedMoney() {
        return guaranteedMoney;
    }

    public void setGuaranteedMoney(String guaranteedMoney) {
        this.guaranteedMoney = guaranteedMoney;
    }

    public String getGuaranteedRatio() {
        return guaranteedRatio;
    }

    public void setGuaranteedRatio(String guaranteedRatio) {
        this.guaranteedRatio = guaranteedRatio;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public String getTotalBuy() {
        return totalBuy;
    }

    public void setTotalBuy(String totalBuy) {
        this.totalBuy = totalBuy;
    }
}
