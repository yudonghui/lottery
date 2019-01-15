package com.daxiang.lottery.entity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/21
 * @describe May the Buddha bless bug-free!!!
 */
public class AwardResultData {

    /**
     * sign : 508184af459201a4a4a6327ac05eaaae
     * msg : 成功
     * code : 0
     * data : {"items":[{"letCode":"23529","redNumber":["06","16","18","26","30"],"blueNumber":["02","03"],"issueNo":"2017070","deadline":1497873600000}],"timeStamp":1498016470542}
     */

    private String sign;
    private String msg;
    private int code;
    private DataBean data;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * items : [{"letCode":"23529","redNumber":["06","16","18","26","30"],"blueNumber":["02","03"],"issueNo":"2017070","deadline":1497873600000}]
         * timeStamp : 1498016470542
         */

        private long timeStamp;
        private List<ItemsBean> items;

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * letCode : 23529
             * redNumber : ["06","16","18","26","30"]
             * blueNumber : ["02","03"]
             * issueNo : 2017070
             * deadline : 1497873600000
             */

            private String letCode;
            private String issueNo;
            private long deadline;
            private List<String> redNumber;
            private List<String> blueNumber;

            public String getLetCode() {
                return letCode;
            }

            public void setLetCode(String letCode) {
                this.letCode = letCode;
            }

            public String getIssueNo() {
                return issueNo;
            }

            public void setIssueNo(String issueNo) {
                this.issueNo = issueNo;
            }

            public long getDeadline() {
                return deadline;
            }

            public void setDeadline(long deadline) {
                this.deadline = deadline;
            }

            public List<String> getRedNumber() {
                return redNumber;
            }

            public void setRedNumber(List<String> redNumber) {
                this.redNumber = redNumber;
            }

            public List<String> getBlueNumber() {
                return blueNumber;
            }

            public void setBlueNumber(List<String> blueNumber) {
                this.blueNumber = blueNumber;
            }
        }
    }
}
