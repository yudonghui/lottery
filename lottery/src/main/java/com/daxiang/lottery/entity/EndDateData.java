package com.daxiang.lottery.entity;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class EndDateData {


    /**
     * code : 0
     * msg : 根据彩种查询截止停售时间成功
     * data : {"stopSaleTime":"1481977800000","deadline":"1481976000000","issueNo":"2016148","status":"100"}
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
         * stopSaleTime : 1481977800000
         * deadline : 1481976000000
         * issueNo : 2016148
         * status : 100
         */

        private String stopSaleTime;
        private String deadline;
        private String issueNo;
        private String status;

        public String getStopSaleTime() {
            return stopSaleTime;
        }

        public void setStopSaleTime(String stopSaleTime) {
            this.stopSaleTime = stopSaleTime;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getIssueNo() {
            return issueNo;
        }

        public void setIssueNo(String issueNo) {
            this.issueNo = issueNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
