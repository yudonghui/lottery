package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/15.
 */

public class SearchData {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"headImg":null,"userName":"CX_777232zyg","userId":"7120516281200008"},{"headImg":null,"userName":"CX_211799szh","userId":"7120516281200028"},{"headImg":null,"userName":"CX_806310vfy","userId":"2017083016513601001"}]
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
         * headImg : null
         * userName : CX_777232zyg
         * userId : 7120516281200008
         */

        private String headImg;
        private String userName;
        private String userId;

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
