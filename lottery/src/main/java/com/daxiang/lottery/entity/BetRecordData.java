package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class BetRecordData {


    /**
     * code : 0
     * msg : 查询成功
     * data : {"totalRecords":9,"totalPages":1,"pageIndex":1,"pageSize":10,"list":[{"orderId":2016120717151800000,"orderCode":"HM20161207195606_7796886","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":null,"betNum":null,"unitPrice":null,"totalPrice":2,"realCost":null,"betTime":1481111767000,"updateTime":1481111768000,"channel":"6000001","client":null,"orderType":4,"splitNum":null,"buyNum":2,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481111768000,"sessions":null,"firstEndTime":null,"expiredTime":null,"playType":null,"parentId":2016120717151800000,"supId":null,"betContent":null},{"orderId":2016120717151800000,"orderCode":"FZ20161207184035_6902323","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481107235000,"updateTime":1481107246000,"channel":"6000001","client":"android","orderType":7,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481107246000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":10,"parentId":null,"supId":5,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"FZ20161207184025_9479663","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481107226000,"updateTime":1481107245000,"channel":"6000001","client":"android","orderType":7,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481107245000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":10,"parentId":null,"supId":5,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"HM20161207184015_5595832","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":8,"realCost":null,"betTime":1481107216000,"updateTime":1481111765000,"channel":"6000001","client":"android","orderType":1,"splitNum":null,"buyNum":8,"orderStatus":600,"revokeFlag":2,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":3,"printTime":null,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":null,"playType":10,"parentId":null,"supId":null,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"GC20161207184001_5770407","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481107202000,"updateTime":1481107226000,"channel":"10000000","client":"android","orderType":0,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481107226000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":10,"parentId":null,"supId":5,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"GC20161207182231_0602426","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481106151000,"updateTime":1481106165000,"channel":"10000000","client":"android","orderType":0,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481106165000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"HH|SPF&gt;20161207001=1(3.85),RQSPF&gt;20161207002=3{-1}(3.22)|2*1"},{"orderId":2016120717151800000,"orderCode":"FZ20161207181905_4830675","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481105945000,"updateTime":1481105965000,"channel":"6000001","client":"android","orderType":7,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481105965000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"HH|SPF&gt;20161207001=1(3.85),RQSPF&gt;20161207002=3{-1}(3.22)|2*1"},{"orderId":2016120717151800000,"orderCode":"HM20161207181841_1916863","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":8,"realCost":8,"betTime":1481105922000,"updateTime":1481105947000,"channel":"6000001","client":"android","orderType":1,"splitNum":1,"buyNum":8,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481105947000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"HH|SPF&gt;20161207002=3(1.70),RQSPF&gt;20161207001=1{-1}(3.15)|2*1"},{"orderId":2016120717151800000,"orderCode":"GC20161207175229_3707062","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481104349000,"updateTime":1481104365000,"channel":"10000000","client":"android","orderType":0,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481104365000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"SPF|20161207002=3(1.70),20161207001=1(3.85)|2*1"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * totalRecords : 9
         * totalPages : 1
         * pageIndex : 1
         * pageSize : 10
         * list : [{"orderId":2016120717151800000,"orderCode":"HM20161207195606_7796886","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":null,"betNum":null,"unitPrice":null,"totalPrice":2,"realCost":null,"betTime":1481111767000,"updateTime":1481111768000,"channel":"6000001","client":null,"orderType":4,"splitNum":null,"buyNum":2,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481111768000,"sessions":null,"firstEndTime":null,"expiredTime":null,"playType":null,"parentId":2016120717151800000,"supId":null,"betContent":null},{"orderId":2016120717151800000,"orderCode":"FZ20161207184035_6902323","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481107235000,"updateTime":1481107246000,"channel":"6000001","client":"android","orderType":7,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481107246000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":10,"parentId":null,"supId":5,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"FZ20161207184025_9479663","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481107226000,"updateTime":1481107245000,"channel":"6000001","client":"android","orderType":7,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481107245000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":10,"parentId":null,"supId":5,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"HM20161207184015_5595832","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":8,"realCost":null,"betTime":1481107216000,"updateTime":1481111765000,"channel":"6000001","client":"android","orderType":1,"splitNum":null,"buyNum":8,"orderStatus":600,"revokeFlag":2,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":3,"printTime":null,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":null,"playType":10,"parentId":null,"supId":null,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"GC20161207184001_5770407","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":-1,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481107202000,"updateTime":1481107226000,"channel":"10000000","client":"android","orderType":0,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481107226000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":10,"parentId":null,"supId":5,"betContent":"HH|RQSPF&gt;20161207001=3(2.60),SPF&gt;20161207002=3(1.70)|2*1_5"},{"orderId":2016120717151800000,"orderCode":"GC20161207182231_0602426","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481106151000,"updateTime":1481106165000,"channel":"10000000","client":"android","orderType":0,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481106165000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"HH|SPF&gt;20161207001=1(3.85),RQSPF&gt;20161207002=3{-1}(3.22)|2*1"},{"orderId":2016120717151800000,"orderCode":"FZ20161207181905_4830675","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481105945000,"updateTime":1481105965000,"channel":"6000001","client":"android","orderType":7,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481105965000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"HH|SPF&gt;20161207001=1(3.85),RQSPF&gt;20161207002=3{-1}(3.22)|2*1"},{"orderId":2016120717151800000,"orderCode":"HM20161207181841_1916863","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":8,"realCost":8,"betTime":1481105922000,"updateTime":1481105947000,"channel":"6000001","client":"android","orderType":1,"splitNum":1,"buyNum":8,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481105947000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"HH|SPF&gt;20161207002=3(1.70),RQSPF&gt;20161207001=1{-1}(3.15)|2*1"},{"orderId":2016120717151800000,"orderCode":"GC20161207175229_3707062","lotCode":"42","issueNo":"20161207001","userId":2016092210470301000,"userName":"13162821161","betMulti":5,"betNum":1,"unitPrice":2,"totalPrice":10,"realCost":10,"betTime":1481104349000,"updateTime":1481104365000,"channel":"10000000","client":"android","orderType":0,"splitNum":1,"buyNum":null,"orderStatus":500,"revokeFlag":0,"winStatus":null,"pretaxBonus":null,"aftertaxBonus":null,"sendFlag":null,"clearFlag":1,"printStatus":2,"printTime":1481104365000,"sessions":"20161207001 20161207002","firstEndTime":1481111700000,"expiredTime":1481111700000,"playType":0,"parentId":null,"supId":5,"betContent":"SPF|20161207002=3(1.70),20161207001=1(3.85)|2*1"}]
         */

        private int totalRecords;
        private int totalPages;
        private int pageIndex;
        private int pageSize;
        private List<ListBean> list;

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * orderId : 2016120717151800000
             * orderCode : HM20161207195606_7796886
             * lotCode : 42
             * issueNo : 20161207001
             * userId : 2016092210470301000
             * userName : 13162821161
             * betMulti : null
             * betNum : null
             * unitPrice : null
             * totalPrice : 2
             * realCost : null
             * betTime : 1481111767000
             * updateTime : 1481111768000
             * channel : 6000001
             * client : null
             * orderType : 4
             * splitNum : null
             * buyNum : 2
             * orderStatus : 500
             * revokeFlag : 0
             * winStatus : null
             * pretaxBonus : null
             * aftertaxBonus : null
             * sendFlag : null
             * clearFlag : 1
             * printStatus : 2
             * printTime : 1481111768000
             * sessions : null
             * firstEndTime : null
             * expiredTime : null
             * playType : null
             * parentId : 2016120717151800000
             * supId : null
             * betContent : null
             */

            private String orderId;
            //方案编号
            private String orderCode;
            private String lotCode;
            //期号
            private String issueNo;
            private String userId;
            private String userName;
            //投注倍数
            private String betMulti;
            //投注注数
            private String betNum;
            //单价
            private String unitPrice;
            //投注金额
            private String totalPrice;
            //实际金额
            private String realCost;
            //投注时间
            private String betTime;
            private String updateTime;
            private String channel;
            private String client;
            //订单类型
            private int orderType;
            //拆分订单数量
            private String splitNum;
            //购买份数
            private int buyNum;
            //订票状态
            private int orderStatus;
            //撤单标志
            private int revokeFlag;
            //中奖状态
            private String winStatus;
            //税前奖金
            private String pretaxBonus;
            //税后奖金
            private String aftertaxBonus;
            //            派奖标志
            private String sendFlag;
            //清算标志
            private String clearFlag;
            //
            private int printStatus;
            //
            private String printTime;
            //
            private String sessions;
            //首场结束时间
            private String firstEndTime;
            //过期时间
            private String expiredTime;
            //玩法类型
            private String playType;
            //父订单id
            private String parentId;
            //出票商id
            private String supId;
            //投注内容
            private String betContent;
            //参与推荐的时候,推荐大神的uid
            private String supUserId;
            //参与推荐的时候,推荐大神的名字
            private String supUserName;
            //乐善奖金
            private String extraMargin;

            public String getExtraMargin() {
                return extraMargin;
            }

            public void setExtraMargin(String extraMargin) {
                this.extraMargin = extraMargin;
            }

            public String getSupUserId() {
                return supUserId;
            }

            public void setSupUserId(String supUserId) {
                this.supUserId = supUserId;
            }

            public String getSupUserName() {
                return supUserName;
            }

            public void setSupUserName(String supUserName) {
                this.supUserName = supUserName;
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

            public String getLotCode() {
                return lotCode;
            }

            public void setLotCode(String lotCode) {
                this.lotCode = lotCode;
            }

            public String getIssueNo() {
                return issueNo;
            }

            public void setIssueNo(String issueNo) {
                this.issueNo = issueNo;
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

            public String getBetMulti() {
                return betMulti;
            }

            public void setBetMulti(String betMulti) {
                this.betMulti = betMulti;
            }

            public String getBetNum() {
                return betNum;
            }

            public void setBetNum(String betNum) {
                this.betNum = betNum;
            }

            public String getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(String unitPrice) {
                this.unitPrice = unitPrice;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getRealCost() {
                return realCost;
            }

            public void setRealCost(String realCost) {
                this.realCost = realCost;
            }

            public String getBetTime() {
                return betTime;
            }

            public void setBetTime(String betTime) {
                this.betTime = betTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getClient() {
                return client;
            }

            public void setClient(String client) {
                this.client = client;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public String getSplitNum() {
                return splitNum;
            }

            public void setSplitNum(String splitNum) {
                this.splitNum = splitNum;
            }

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getRevokeFlag() {
                return revokeFlag;
            }

            public void setRevokeFlag(int revokeFlag) {
                this.revokeFlag = revokeFlag;
            }

            public String getWinStatus() {
                return winStatus;
            }

            public void setWinStatus(String winStatus) {
                this.winStatus = winStatus;
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

            public String getSendFlag() {
                return sendFlag;
            }

            public void setSendFlag(String sendFlag) {
                this.sendFlag = sendFlag;
            }

            public String getClearFlag() {
                return clearFlag;
            }

            public void setClearFlag(String clearFlag) {
                this.clearFlag = clearFlag;
            }

            public int getPrintStatus() {
                return printStatus;
            }

            public void setPrintStatus(int printStatus) {
                this.printStatus = printStatus;
            }

            public String getPrintTime() {
                return printTime;
            }

            public void setPrintTime(String printTime) {
                this.printTime = printTime;
            }

            public String getSessions() {
                return sessions;
            }

            public void setSessions(String sessions) {
                this.sessions = sessions;
            }

            public String getFirstEndTime() {
                return firstEndTime;
            }

            public void setFirstEndTime(String firstEndTime) {
                this.firstEndTime = firstEndTime;
            }

            public String getExpiredTime() {
                return expiredTime;
            }

            public void setExpiredTime(String expiredTime) {
                this.expiredTime = expiredTime;
            }

            public String getPlayType() {
                return playType;
            }

            public void setPlayType(String playType) {
                this.playType = playType;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getSupId() {
                return supId;
            }

            public void setSupId(String supId) {
                this.supId = supId;
            }

            public String getBetContent() {
                return betContent;
            }

            public void setBetContent(String betContent) {
                this.betContent = betContent;
            }
        }
    }
}
