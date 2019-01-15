package com.daxiang.lottery.entity;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class LoginResultErrorData {

    /**
     * msg : 用户不存在
     * code : 1121
     */

    private String msg;
    private int code;

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
}
