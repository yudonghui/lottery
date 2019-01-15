package com.daxiang.lottery.entity;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class SendMsgData {
    String msgType = "0";
    String[] paras = {""};
    String mobile;
    String uid = "0";

    public String getMsgType() {
        return msgType;
    }

    public String getUid() {
        return uid;
    }

    public String getMobile() {
        return mobile;
    }

    public String[] getParas() {
        return paras;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setParas(String[] paras) {
        this.paras = paras;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
