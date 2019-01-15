package com.daxiang.lottery.entity;

/**
 * Created by Android on 2018/3/26.
 */

public class GodInfoNumData {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"fansNum":1,"focusFlag":0,"userDesc":null,"userName":"dongshuo","userId":7120516281200035,"focusNum":0}
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
         * fansNum : 1
         * focusFlag : 0
         * userDesc : null
         * userName : dongshuo
         * userId : 7120516281200035
         * focusNum : 0
         */

        private int fansNum;
        private int focusFlag;
        private String userDesc;
        private String userName;
        private long userId;
        private int focusNum;

        public int getFansNum() {
            return fansNum;
        }

        public void setFansNum(int fansNum) {
            this.fansNum = fansNum;
        }

        public int getFocusFlag() {
            return focusFlag;
        }

        public void setFocusFlag(int focusFlag) {
            this.focusFlag = focusFlag;
        }

        public String getUserDesc() {
            return userDesc;
        }

        public void setUserDesc(String userDesc) {
            this.userDesc = userDesc;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getFocusNum() {
            return focusNum;
        }

        public void setFocusNum(int focusNum) {
            this.focusNum = focusNum;
        }
    }
}
