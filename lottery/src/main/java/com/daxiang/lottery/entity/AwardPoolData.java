package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class AwardPoolData {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"awardPool":989524476,"awardNumber":null,"seExpect":"2016060","seLotid":51,"seDsendtime":1464262200000,"seAllowbuy":1,"seFsendtime":1464262920000,"seIsactive":1,"seEndtime":1464264000000,"awardTime":1464101186382,"seAllowcp":0,"seId":null,"seAddtime":1459094700000,"seIsqs":0,"seIsover":0,"awardFlag":null,"seIsIssue":0,"modifyflag":0,"status":100},{"awardPool":3043271917,"awardNumber":null,"seExpect":"2016060","seLotid":23529,"seDsendtime":1464175800000,"seAllowbuy":1,"seFsendtime":1464175800000,"seIsactive":1,"seEndtime":1464177600000,"awardTime":1464101186411,"seAllowcp":1,"seId":null,"seAddtime":1459008301000,"seIsqs":0,"seIsover":0,"awardFlag":null,"seIsIssue":0,"modifyflag":0,"status":100},{"awardPool":10611970,"awardNumber":null,"seExpect":"2016061","seLotid":10022,"seDsendtime":1464348600000,"seAllowbuy":1,"seFsendtime":1464349320000,"seIsactive":1,"seEndtime":1464350400000,"awardTime":1464101186440,"seAllowcp":1,"seId":null,"seAddtime":1459181101000,"seIsqs":0,"seIsover":0,"awardFlag":null,"seIsIssue":0,"modifyflag":0,"status":100},{"awardPool":0,"awardNumber":null,"seExpect":"2016060","seLotid":23528,"seDsendtime":1464175800000,"seAllowbuy":1,"seFsendtime":1464176520000,"seIsactive":1,"seEndtime":1464177600000,"awardTime":1464101186470,"seAllowcp":0,"seId":null,"seAddtime":1459008302000,"seIsqs":0,"seIsover":0,"awardFlag":null,"seIsIssue":0,"modifyflag":0,"status":100}]
     */

    private int code;
    private String msg;
    /**
     * awardPool : 989524476
     * awardNumber : null
     * seExpect : 2016060
     * seLotid : 51
     * seDsendtime : 1464262200000
     * seAllowbuy : 1
     * seFsendtime : 1464262920000
     * seIsactive : 1
     * seEndtime : 1464264000000
     * awardTime : 1464101186382
     * seAllowcp : 0
     * seId : null
     * seAddtime : 1459094700000
     * seIsqs : 0
     * seIsover : 0
     * awardFlag : null
     * seIsIssue : 0
     * modifyflag : 0
     * status : 100
     */

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
        private String awardPool;
        private String awardNumber;
        private String seExpect;
        private int seLotid;
        private long seDsendtime;
        private int seAllowbuy;
        private long seFsendtime;
        private int seIsactive;
        private long seEndtime;
        private long awardTime;
        private int seAllowcp;
        private String seId;
        private long seAddtime;
        private int seIsqs;
        private int seIsover;
        private String awardFlag;
        private int seIsIssue;
        private int modifyflag;
        private int status;

        public String getAwardPool() {
            return awardPool;
        }

        public void setAwardPool(String awardPool) {
            this.awardPool = awardPool;
        }

        public String getAwardNumber() {
            return awardNumber;
        }

        public void setAwardNumber(String awardNumber) {
            this.awardNumber = awardNumber;
        }

        public String getSeExpect() {
            return seExpect;
        }

        public void setSeExpect(String seExpect) {
            this.seExpect = seExpect;
        }

        public int getSeLotid() {
            return seLotid;
        }

        public void setSeLotid(int seLotid) {
            this.seLotid = seLotid;
        }

        public long getSeDsendtime() {
            return seDsendtime;
        }

        public void setSeDsendtime(long seDsendtime) {
            this.seDsendtime = seDsendtime;
        }

        public int getSeAllowbuy() {
            return seAllowbuy;
        }

        public void setSeAllowbuy(int seAllowbuy) {
            this.seAllowbuy = seAllowbuy;
        }

        public long getSeFsendtime() {
            return seFsendtime;
        }

        public void setSeFsendtime(long seFsendtime) {
            this.seFsendtime = seFsendtime;
        }

        public int getSeIsactive() {
            return seIsactive;
        }

        public void setSeIsactive(int seIsactive) {
            this.seIsactive = seIsactive;
        }

        public long getSeEndtime() {
            return seEndtime;
        }

        public void setSeEndtime(long seEndtime) {
            this.seEndtime = seEndtime;
        }

        public long getAwardTime() {
            return awardTime;
        }

        public void setAwardTime(long awardTime) {
            this.awardTime = awardTime;
        }

        public int getSeAllowcp() {
            return seAllowcp;
        }

        public void setSeAllowcp(int seAllowcp) {
            this.seAllowcp = seAllowcp;
        }

        public String getSeId() {
            return seId;
        }

        public void setSeId(String seId) {
            this.seId = seId;
        }

        public long getSeAddtime() {
            return seAddtime;
        }

        public void setSeAddtime(long seAddtime) {
            this.seAddtime = seAddtime;
        }

        public int getSeIsqs() {
            return seIsqs;
        }

        public void setSeIsqs(int seIsqs) {
            this.seIsqs = seIsqs;
        }

        public int getSeIsover() {
            return seIsover;
        }

        public void setSeIsover(int seIsover) {
            this.seIsover = seIsover;
        }

        public String getAwardFlag() {
            return awardFlag;
        }

        public void setAwardFlag(String awardFlag) {
            this.awardFlag = awardFlag;
        }

        public int getSeIsIssue() {
            return seIsIssue;
        }

        public void setSeIsIssue(int seIsIssue) {
            this.seIsIssue = seIsIssue;
        }

        public int getModifyflag() {
            return modifyflag;
        }

        public void setModifyflag(int modifyflag) {
            this.modifyflag = modifyflag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
