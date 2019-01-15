package com.daxiang.lottery.entity;

import java.io.Serializable;

/**
 * @author yudonghui
 * @date 2017/9/26
 * @describe May the Buddha bless bug-free!!!
 */
public class PayBean implements Serializable {
    private String caseId;//方案号
    private String merchantId;////第三方支付id(可选)
    private String amount;////实际支付金额(订单金额减去红包金额)
    private String voucherId;//红包id
    private String orderDetail;//订单明细
    private String remark;//备注
    private String lotCode;//彩种
    private String balanceAccount;

    public String getBalanceAccount() {
        return balanceAccount;
    }

    public void setBalanceAccount(String balanceAccount) {
        this.balanceAccount = balanceAccount;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
