package com.daxiang.lottery.entity;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class LoginResultData {

    /**
     * sign : 670B14728AD9902AECBA32E22FA4F6BD
     * msg : 成功
     * code : 0
     * data : {"userId":"545643543145454356","userName":"sosorry","suggestDisplayName":"玩意","token":"670B14728AD9902AECBA32E22FA4F6BD","realName":"静静的看着你装逼","nickName":"玩意","channelId":10000,"mobile":"1870129843","email":"hulele@51.com","headImg":"http://51cx.com/iasR6yJMLz5853p8zIHllkvv6DwUkEm43jiae7Lt0YaRIjDoKMibCBKrf87ToFWU1GlibgO9h9j/0","idNo":"61021110992333298X","status":1,"timeStamp":1475047474469}
     */

    private String sign;
    private String msg;
    private int code;
    private DataBean data;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 545643543145454356
         * userName : sosorry
         * suggestDisplayName : 玩意
         * token : 670B14728AD9902AECBA32E22FA4F6BD
         * realName : 静静的看着你装逼
         * nickName : 玩意
         * channelId : 10000
         * mobile : 1870129843
         * email : hulele@51.com
         * headImg : http://51cx.com/iasR6yJMLz5853p8zIHllkvv6DwUkEm43jiae7Lt0YaRIjDoKMibCBKrf87ToFWU1GlibgO9h9j/0
         * idNo : 61021110992333298X
         * status : 1
         * timeStamp : 1475047474469
         */

        private String userId;
        private String userName;
        private String userType;
        private String suggestDisplayName;
        private String token;
        private String realName;
        private String nickName;
        private String channelId;
        private String mobile;
        private String email;
        private String phone;
        private String headImg;
        private String idNo;
        private int status;
        private long timeStamp;

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

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

        public String getSuggestDisplayName() {
            return suggestDisplayName;
        }

        public void setSuggestDisplayName(String suggestDisplayName) {
            this.suggestDisplayName = suggestDisplayName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
