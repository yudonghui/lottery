package com.daxiang.lottery.entity;

/**
 * Created by Administrator on 2017/2/28.
 */

public class JsonData {
    private String key;
    private String value;
    public JsonData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
