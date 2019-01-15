package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by GK on 2016/4/14.
 */
public class LotteryResultMode {

    /**
     * code : 0
     * msg : 获取历史开奖信息成功
     * data : [{"score":"4:0","lotCode":"42","issue":"2016-12-13","homeTeam":"欧本","guestTeam":"科特赖克","lotName":"竞彩足球"},{"score":"111:113","lotCode":"43","issue":"2016-12-13","homeTeam":"太阳","guestTeam":"尼克斯","lotName":"竞彩篮球"},{"lotCode":"23529","issue":"2016146","awardNumber":"21,22,25,29,34:01,11","lotName":"超级大乐透"},{"lotCode":"11","issue":"2016192","awardNumber":"3,3,1,3,0,0,1,0,0,3,0,3,3,1","lotName":"胜负彩"},{"lotCode":"33","issue":"2016341","awardNumber":"0,3,9","lotName":"排列三"},{"lotCode":"35","issue":"2016341","awardNumber":"0,3,9,7,0","lotName":"排列五"},{"lotCode":"51","issue":"2016146","awardNumber":"03,07,15,16,17,23:10","lotName":"双色球"},{"lotCode":"52","issue":"2016335","awardNumber":"5,5,0","lotName":"福彩3D"},{"lotCode":"10022","issue":"2016147","awardNumber":"4,7,5,2,8,7,7","lotName":"七星彩"},{"lotCode":"23528","issue":"2016146","awardNumber":"12,15,18,22,24,25,30:17","lotName":"七乐彩"},{"lotCode":"19","issue":"2016192","awardNumber":"3,3,1,3,0,0,1,0,0,3,0,3,3,1","lotName":"任九场"}]
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
         * score : 4:0
         * lotCode : 42
         * issue : 2016-12-13
         * homeTeam : 欧本
         * guestTeam : 科特赖克
         * lotName : 竞彩足球
         * awardNumber : 21,22,25,29,34:01,11
         */

        private String score;
        private String lotCode;
        private String issue;
        private String homeTeam;
        private String guestTeam;
        private String lotName;
        private String awardNumber;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

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

        public String getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(String homeTeam) {
            this.homeTeam = homeTeam;
        }

        public String getGuestTeam() {
            return guestTeam;
        }

        public void setGuestTeam(String guestTeam) {
            this.guestTeam = guestTeam;
        }

        public String getLotName() {
            return lotName;
        }

        public void setLotName(String lotName) {
            this.lotName = lotName;
        }

        public String getAwardNumber() {
            return awardNumber;
        }

        public void setAwardNumber(String awardNumber) {
            this.awardNumber = awardNumber;
        }
    }
}
