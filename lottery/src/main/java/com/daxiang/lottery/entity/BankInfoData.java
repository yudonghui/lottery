package com.daxiang.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class BankInfoData implements Serializable {

    /**
     * data : [{"identification":"6217994980000814389","payProvider":10,"createdTime":1458896830000,"city":"上海,上海","subBranch":"陆家嘴支行","uid":603240000004700,"name":"董硕","id":354,"type":1}]
     * msg :
     * code : 0
     */

    private String msg;
    private int code;
    /**
     * identification : 6217994980000814389
     * payProvider : 10
     * createdTime : 1458896830000
     * city : 上海,上海
     * subBranch : 陆家嘴支行
     * uid : 603240000004700
     * name : 董硕
     * id : 354
     * type : 1
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private String identification;
        private int payProvider;
        private long createdTime;
        private String city;
        private String subBranch;
        private long uid;
        private String name;
        private int id;
        private int type;

        public String getIdentification() {
            return identification;
        }

        public void setIdentification(String identification) {
            this.identification = identification;
        }

        public int getPayProvider() {
            return payProvider;
        }

        public void setPayProvider(int payProvider) {
            this.payProvider = payProvider;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSubBranch() {
            return subBranch;
        }

        public void setSubBranch(String subBranch) {
            this.subBranch = subBranch;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
