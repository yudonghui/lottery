package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class OutTicketDetailData {

    /**
     * code : 0
     * msg : 获取拆分订单成功
     * data : [{"unitPrice":"2.00","margin":null,"centerBonus":null,"bonus":null,"totalMoney":"10.00","expiredTime":"2016-12-13 23:55:00","multi":"5","centerMargin":null,"lotCode":"42","betNum":"1","playType":"12","ticketStatus":"700","oddsContent":"HH|SPF>20161213001=1(6.100),RQSPF>20161213002=3{-1}(5.000)|2*1","ticketId":"2016121015293800001","pureContent":"HH|SPF>20161213001=1,RQSPF>20161213002=3|2*1"}]
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

    public static class DataBean {
        /**
         * unitPrice : 2.00
         * margin : null
         * centerBonus : null
         * bonus : null
         * totalMoney : 10.00
         * expiredTime : 2016-12-13 23:55:00
         * multi : 5
         * centerMargin : null
         * lotCode : 42
         * betNum : 1
         * playType : 12
         * ticketStatus : 700
         * oddsContent : HH|SPF>20161213001=1(6.100),RQSPF>20161213002=3{-1}(5.000)|2*1
         * ticketId : 2016121015293800001
         * pureContent : HH|SPF>20161213001=1,RQSPF>20161213002=3|2*1
         */

        private String unitPrice;
        private String margin;
        private String centerBonus;
        private String bonus;
        private String totalMoney;
        private String expiredTime;
        private String multi;
        private String centerMargin;
        private String lotCode;
        private String betNum;
        private String playType;
        private String ticketStatus;
        private String oddsContent;
        private String ticketId;
        private String pureContent;
        private String printStatus;
        private String extraContent;
        private String extraMargin;//乐善奖金

        public String getExtraMargin() {
            return extraMargin;
        }

        public void setExtraMargin(String extraMargin) {
            this.extraMargin = extraMargin;
        }

        public void setPrintStatus(String printStatus) {
            this.printStatus = printStatus;
        }

        public String getExtraContent() {
            return extraContent;
        }

        public void setExtraContent(String extraContent) {
            this.extraContent = extraContent;
        }

        public String getPrintStatus() {
            return printStatus;
        }

        public void setPrintStatus() {
            this.printStatus = printStatus;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getMargin() {
            return margin;
        }

        public void setMargin(String margin) {
            this.margin = margin;
        }

        public String getCenterBonus() {
            return centerBonus;
        }

        public void setCenterBonus(String centerBonus) {
            this.centerBonus = centerBonus;
        }

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(String expiredTime) {
            this.expiredTime = expiredTime;
        }

        public String getMulti() {
            return multi;
        }

        public void setMulti(String multi) {
            this.multi = multi;
        }

        public String getCenterMargin() {
            return centerMargin;
        }

        public void setCenterMargin(String centerMargin) {
            this.centerMargin = centerMargin;
        }

        public String getLotCode() {
            return lotCode;
        }

        public void setLotCode(String lotCode) {
            this.lotCode = lotCode;
        }

        public String getBetNum() {
            return betNum;
        }

        public void setBetNum(String betNum) {
            this.betNum = betNum;
        }

        public String getPlayType() {
            return playType;
        }

        public void setPlayType(String playType) {
            this.playType = playType;
        }

        public String getTicketStatus() {
            return ticketStatus;
        }

        public void setTicketStatus(String ticketStatus) {
            this.ticketStatus = ticketStatus;
        }

        public String getOddsContent() {
            return oddsContent;
        }

        public void setOddsContent(String oddsContent) {
            this.oddsContent = oddsContent;
        }

        public String getTicketId() {
            return ticketId;
        }

        public void setTicketId(String ticketId) {
            this.ticketId = ticketId;
        }

        public String getPureContent() {
            return pureContent;
        }

        public void setPureContent(String pureContent) {
            this.pureContent = pureContent;
        }
    }
}
