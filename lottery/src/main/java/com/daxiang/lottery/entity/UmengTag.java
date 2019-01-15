package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/4/19.
 */

public class UmengTag {

    /**
     * code : 0
     * msg : 获取推送标签成功
     * data : [{"id":1524048084549,"userId":7091117051800005,"pushType":"recommend","userTag":"7120516281200035","createTime":1524122473000},{"id":1524048084550,"userId":7091117051800005,"pushType":"recommend","userTag":"7081421480900011","createTime":1524122574000}]
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
         * id : 1524048084549
         * userId : 7091117051800005
         * pushType : recommend
         * userTag : 7120516281200035
         * createTime : 1524122473000
         */

        private long id;
        private long userId;
        private String pushType;
        private String userTag;
        private long createTime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getPushType() {
            return pushType;
        }

        public void setPushType(String pushType) {
            this.pushType = pushType;
        }

        public String getUserTag() {
            return userTag;
        }

        public void setUserTag(String userTag) {
            this.userTag = userTag;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
