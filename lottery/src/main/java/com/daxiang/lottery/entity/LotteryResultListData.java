package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class LotteryResultListData {

    /**
     * code : 0
     * msg : 根据彩种查询历史开奖记录成功
     * data : {"item":[{"lotCode":"23529","issue":"2016142","awardNumber":"01,06,17,23,26:06,12"},{"lotCode":"23529","issue":"2016143","awardNumber":"04,06,22,27,31:06,07"},{"lotCode":"23529","issue":"2016144","awardNumber":"04,05,13,22,31:05,10"},{"lotCode":"23529","issue":"2016145","awardNumber":"01,09,11,17,28:03,08"},{"lotCode":"23529","issue":"2016146","awardNumber":"21,22,25,29,34:01,11"}],"totalNu":1}
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
         * item : [{"lotCode":"23529","issue":"2016142","awardNumber":"01,06,17,23,26:06,12"},{"lotCode":"23529","issue":"2016143","awardNumber":"04,06,22,27,31:06,07"},{"lotCode":"23529","issue":"2016144","awardNumber":"04,05,13,22,31:05,10"},{"lotCode":"23529","issue":"2016145","awardNumber":"01,09,11,17,28:03,08"},{"lotCode":"23529","issue":"2016146","awardNumber":"21,22,25,29,34:01,11"}]
         * totalNu : 1
         */

        private int totalNu;
        private List<ItemBean> item;

        public int getTotalNu() {
            return totalNu;
        }

        public void setTotalNu(int totalNu) {
            this.totalNu = totalNu;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * lotCode : 23529
             * issue : 2016142
             * awardNumber : 01,06,17,23,26:06,12
             */

            private String lotCode;
            private String issue;
            private String awardNumber;

            public String getLotCode() {
                return lotCode;
            }

            public void setLotCode(String lotCode) {
                this.lotCode = lotCode;
            }

            public String getIssue() {
                return issue;
            }

            public void setIssue(String issue) {
                this.issue = issue;
            }

            public String getAwardNumber() {
                return awardNumber;
            }

            public void setAwardNumber(String awardNumber) {
                this.awardNumber = awardNumber;
            }
        }
    }
}
