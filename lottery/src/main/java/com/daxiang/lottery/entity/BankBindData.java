package com.daxiang.lottery.entity;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class BankBindData {

    /**
     * bankId : 1
     * cardNo : 428766886
     * city : 河南
     * name : 辉东于
     * password : 987115
     * token : a35fd3d8128b473da8a062a1e7a1f65e5f8a5f4457fc9a7cd1190bff93013a3b8b69c2af54282a1b36efd7858afb21764eaa0f8dccf5f7cb6f94a500bc79f678129118bb4988378ba6c59e34b8e6ca26ff0457b14adf7c9b3b41235deaa4e43924d107cc51fd0f6f51fdc8ea64162813045b037ba5bcd4ce0deefaedd1fceb53
     * uid : 608040000912700
     */

    private String bankId;
    private String cardNo;
    private String city;
    private String name;
    private String password;
    private String token;
    private String uid;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
