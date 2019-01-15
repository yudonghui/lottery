package com.daxiang.lottery.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class UserInfo implements Serializable {

    /**
     * sign : 3bfea09ae6629f5216fba090f197d3c1
     * msg : 成功
     * code : 0
     * data : {"nickName":"cx003","timeStamp":1470796892474,"token":"148f55fe20da84a54618bf6109360273b997d8a924ae7188e17fe996373609b0592380ef2e8872fa5a56717229c102c8829977a50357d79a460e2ce9edf7d5eb1ab90dd13413666cabdd53d319c85d8cb6224fdb6f2725ddfbe90f0b77de20200de3f3dc52a7f77fcb64e505c9c68432c2a03b678638be7433d5e1bf5a50330f","userName":"qihuitest","phone":"15827459547","userId":1256586468864645,"identification":"42XXXXXXXXXXXXXXX0023","email":"1235546@qq.com","channelId":600001,"suggestDisplayName":"cx003","headImg":"/xxxx/xxxxx","realName":"王麻子","bankCard":"666662222222***1233444","cardTyp":"1","bankBranch":"张江支行"}
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

    public static class DataBean implements Serializable {
        /**
         * nickName : cx003
         * timeStamp : 1470796892474
         * token : 148f55fe20da84a54618bf6109360273b997d8a924ae7188e17fe996373609b0592380ef2e8872fa5a56717229c102c8829977a50357d79a460e2ce9edf7d5eb1ab90dd13413666cabdd53d319c85d8cb6224fdb6f2725ddfbe90f0b77de20200de3f3dc52a7f77fcb64e505c9c68432c2a03b678638be7433d5e1bf5a50330f
         * userName : qihuitest
         * phone : 15827459547
         * userId : 1256586468864645
         * identification : 42XXXXXXXXXXXXXXX0023
         * email : 1235546@qq.com
         * channelId : 600001
         * suggestDisplayName : cx003
         * headImg : /xxxx/xxxxx
         * realName : 王麻子
         * bankCard : 666662222222***1233444
         * cardTyp : 1
         * bankBranch : 张江支行
         */

        private String nickName;
        private long timeStamp;
        private String token;
        private String userName;
        private String phone;
        private long userId;
        private String identification;
        private String email;
        private String channelId;
        private String partnerCode;
        private String suggestDisplayName;
        private String headImg;
        private String realName;
        private String bankCard;
        private String cardType;
        private String bankBranch;
        private String userType;

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getPartnerCode() {
            return partnerCode;
        }

        public void setPartnerCode(String partnerCode) {
            this.partnerCode = partnerCode;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getIdentification() {
            return identification;
        }

        public void setIdentification(String identification) {
            this.identification = identification;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getSuggestDisplayName() {
            return suggestDisplayName;
        }

        public void setSuggestDisplayName(String suggestDisplayName) {
            this.suggestDisplayName = suggestDisplayName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardTyp(String cardType) {
            this.cardType = cardType;
        }

        public String getBankBranch() {
            return bankBranch;
        }

        public void setBankBranch(String bankBranch) {
            this.bankBranch = bankBranch;
        }
    }
}
