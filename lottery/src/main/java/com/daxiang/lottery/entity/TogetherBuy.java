package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/1/4.
 */

public class TogetherBuy {

    /**
     * oneMoney : 24.00
     * sendFlag : 0
     * commissionScale : 0
     * orderId : 2017123017163103014
     * joinNum : 0
     * totalMoney : 120.00
     * userName : 没有
     * userId : 7082113342900001
     * declaration : 跟随大神去购彩！
     * openType : 3
     * aftertaxBonus : null
     * lotCode : 42
     * theoreticalPrize : 2437.48
     * orderCode : FZ20180103172811_0219
     * commission : null
     */

    private String oneMoney;
    private String sendFlag;
    private String commissionScale;
    private String orderId;
    private String joinNum;
    private int userNum;
    private String totalMoney;
    private String userName;
    private String userId;
    private String declaration;
    private int openType;
    private String aftertaxBonus;
    private String lotCode;
    private String theoreticalPrize;
    private String orderCode;
    private String commission;
    private String guaranteedMoney;

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public String getGuaranteedMoney() {
        return guaranteedMoney;
    }

    public void setGuaranteedMoney(String guaranteedMoney) {
        this.guaranteedMoney = guaranteedMoney;
    }

    public String getOneMoney() {
        return oneMoney;
    }

    public void setOneMoney(String oneMoney) {
        this.oneMoney = oneMoney;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getCommissionScale() {
        return commissionScale;
    }

    public void setCommissionScale(String commissionScale) {
        this.commissionScale = commissionScale;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }

    public String getAftertaxBonus() {
        return aftertaxBonus;
    }

    public void setAftertaxBonus(String aftertaxBonus) {
        this.aftertaxBonus = aftertaxBonus;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public String getTheoreticalPrize() {
        return theoreticalPrize;
    }

    public void setTheoreticalPrize(String theoreticalPrize) {
        this.theoreticalPrize = theoreticalPrize;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
