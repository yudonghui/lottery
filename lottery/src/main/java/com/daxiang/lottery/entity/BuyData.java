package com.daxiang.lottery.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Android on 2018/1/16.
 */

public class BuyData extends BmobObject {
    private String name;
    private String phone;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
