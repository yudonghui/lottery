package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class SfcAndRjcData {

    /**
     * code : 0
     * msg : 获取任九场(胜负彩)成功
     * data : [{"gameId":421,"awardId":143099,"lotCode":"11","teamName0":"霍芬","teamName1":"多特","kickOffTime":1481916600000,"serialNo":1,"leagueName":"德甲","odds3":"4.29","odds1":"4.04","odds0":"1.73","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":422,"awardId":143099,"lotCode":"11","teamName0":"柏林联","teamName1":"菲尔特","kickOffTime":1481909400000,"serialNo":2,"leagueName":"德乙","odds3":"1.77","odds1":"3.56","odds0":"4.29","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":423,"awardId":143099,"lotCode":"11","teamName0":"奥厄","teamName1":"杜塞尔","kickOffTime":1481909400000,"serialNo":3,"leagueName":"德乙","odds3":"2.65","odds1":"3.15","odds0":"2.60","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":424,"awardId":143099,"lotCode":"11","teamName0":"慕尼黑","teamName1":"海登","kickOffTime":1481909400000,"serialNo":4,"leagueName":"德乙","odds3":"2.58","odds1":"3.05","odds0":"2.75","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":425,"awardId":143099,"lotCode":"11","teamName0":"阿拉维","teamName1":"贝蒂斯","kickOffTime":1481917500000,"serialNo":5,"leagueName":"西甲","odds3":"2.28","odds1":"3.13","odds0":"3.32","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":426,"awardId":143099,"lotCode":"11","teamName0":"昂热","teamName1":"南特","kickOffTime":1481917500000,"serialNo":6,"leagueName":"法甲","odds3":"2.19","odds1":"3.01","odds0":"3.67","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":427,"awardId":143099,"lotCode":"11","teamName0":"阿雅克","teamName1":"尼姆","kickOffTime":1481914800000,"serialNo":7,"leagueName":"法乙","odds3":"2.15","odds1":"3.06","odds0":"3.45","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":428,"awardId":143099,"lotCode":"11","teamName0":"阿弗尔","teamName1":"图尔","kickOffTime":1481914800000,"serialNo":8,"leagueName":"法乙","odds3":"1.70","odds1":"3.40","odds0":"5.06","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":429,"awardId":143099,"lotCode":"11","teamName0":"欧塞尔","teamName1":"瓦朗谢","kickOffTime":1481914800000,"serialNo":9,"leagueName":"法乙","odds3":"2.33","odds1":"3.01","odds0":"3.13","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":430,"awardId":143099,"lotCode":"11","teamName0":"克莱蒙","teamName1":"奥尔良","kickOffTime":1481914800000,"serialNo":10,"leagueName":"法乙","odds3":"2.20","odds1":"3.00","odds0":"3.42","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":431,"awardId":143099,"lotCode":"11","teamName0":"索肖","teamName1":"圣红星","kickOffTime":1481914800000,"serialNo":11,"leagueName":"法乙","odds3":"1.81","odds1":"3.12","odds0":"4.73","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":432,"awardId":143099,"lotCode":"11","teamName0":"斯特堡","teamName1":"尼奥尔","kickOffTime":1481914800000,"serialNo":12,"leagueName":"法乙","odds3":"1.93","odds1":"3.02","odds0":"4.34","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":433,"awardId":143099,"lotCode":"11","teamName0":"布雷斯","teamName1":"布尔格","kickOffTime":1481914800000,"serialNo":13,"leagueName":"法乙","odds3":"1.73","odds1":"3.37","odds0":"4.81","createTime":1481378457000,"issueNo":"2016194","status":0},{"gameId":434,"awardId":143099,"lotCode":"11","teamName0":"拉瓦勒","teamName1":"亚眠","kickOffTime":1481914800000,"serialNo":14,"leagueName":"法乙","odds3":"2.62","odds1":"2.82","odds0":"2.89","createTime":1481378457000,"issueNo":"2016194","status":0}]
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
         * gameId : 421
         * awardId : 143099
         * lotCode : 11
         * teamName0 : 霍芬
         * teamName1 : 多特
         * kickOffTime : 1481916600000
         * serialNo : 1
         * leagueName : 德甲
         * odds3 : 4.29
         * odds1 : 4.04
         * odds0 : 1.73
         * createTime : 1481378457000
         * issueNo : 2016194
         * status : 0
         */

        private int gameId;
        private int awardId;
        private String lotCode;
        private String teamName0;
        private String teamName1;
        private long kickOffTime;
        private int serialNo;
        private String leagueName;
        private String odds3;
        private String odds1;
        private String odds0;
        private long createTime;
        private String issueNo;
        private int status;

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public int getAwardId() {
            return awardId;
        }

        public void setAwardId(int awardId) {
            this.awardId = awardId;
        }

        public String getLotCode() {
            return lotCode;
        }

        public void setLotCode(String lotCode) {
            this.lotCode = lotCode;
        }

        public String getTeamName0() {
            return teamName0;
        }

        public void setTeamName0(String teamName0) {
            this.teamName0 = teamName0;
        }

        public String getTeamName1() {
            return teamName1;
        }

        public void setTeamName1(String teamName1) {
            this.teamName1 = teamName1;
        }

        public long getKickOffTime() {
            return kickOffTime;
        }

        public void setKickOffTime(long kickOffTime) {
            this.kickOffTime = kickOffTime;
        }

        public int getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(int serialNo) {
            this.serialNo = serialNo;
        }

        public String getLeagueName() {
            return leagueName;
        }

        public void setLeagueName(String leagueName) {
            this.leagueName = leagueName;
        }

        public String getOdds3() {
            return odds3;
        }

        public void setOdds3(String odds3) {
            this.odds3 = odds3;
        }

        public String getOdds1() {
            return odds1;
        }

        public void setOdds1(String odds1) {
            this.odds1 = odds1;
        }

        public String getOdds0() {
            return odds0;
        }

        public void setOdds0(String odds0) {
            this.odds0 = odds0;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getIssueNo() {
            return issueNo;
        }

        public void setIssueNo(String issueNo) {
            this.issueNo = issueNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
