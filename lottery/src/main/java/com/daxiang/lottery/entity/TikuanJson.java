package com.daxiang.lottery.entity;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class TikuanJson {

    /**
     * amount : 20
     * subject :
     * transType : 8
     * business_type : 1000
     * toUid : 0
     * toType : 0
     * remark : {"name":"辉东于","identification":"6214832135020822","payProvider":"10"}
     */

    private Double amount;
    private String subject;
    private int transType;
    private int business_type;
    private int toUid;
    private int toType;
    private String remark;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public int getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(int business_type) {
        this.business_type = business_type;
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public int getToType() {
        return toType;
    }

    public void setToType(int toType) {
        this.toType = toType;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

}
