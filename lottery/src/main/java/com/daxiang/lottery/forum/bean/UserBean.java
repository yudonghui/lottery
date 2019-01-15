package com.daxiang.lottery.forum.bean;

import java.io.Serializable;

/**
 * Created by Android on 2018/8/22.
 */

public class UserBean implements Serializable{
    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
