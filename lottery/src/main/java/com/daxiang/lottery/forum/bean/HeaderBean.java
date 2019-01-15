package com.daxiang.lottery.forum.bean;

/**
 * Created by Android on 2018/8/24.
 */

public class HeaderBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":1526,"userId":2017030615272000055,"userName":"彩象彩票","title":"越南U23vs巴林U23分析 越南U23防守坚固表现惊人","pictureUrl":"http://res.51caixiangtest.com/upfile/forum/18/08/23/180823160009839647.png","likeNum":0,"userFlag":2,"qualityFlag":2,"commentsNum":0,"createTime":1535007467000,"auditFlag":1,"infoSource":"足球魔方","betContent":"","postFlag":"新闻资讯","browseNum":3,"issueNo":null,"money":null,"postStatus":0,"isLike":null}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1526
         * userId : 2017030615272000055
         * userName : 彩象彩票
         * title : 越南U23vs巴林U23分析 越南U23防守坚固表现惊人
         * pictureUrl : http://res.51caixiangtest.com/upfile/forum/18/08/23/180823160009839647.png
         * likeNum : 0
         * userFlag : 2
         * qualityFlag : 2
         * commentsNum : 0
         * createTime : 1535007467000
         * auditFlag : 1
         * infoSource : 足球魔方
         * betContent :
         * postFlag : 新闻资讯
         * browseNum : 3
         * issueNo : null
         * money : null
         * postStatus : 0
         * isLike : null
         */

        private int id;
        private String userId;
        private String userName;
        private String title;
        private String pictureUrl;
        private int likeNum;
        private String userFlag;
        private String qualityFlag;
        private String commentsNum;
        private String createTime;
        private String auditFlag;
        private String infoSource;
        private String betContent;
        private String postFlag;
        private String browseNum;
        private String issueNo;
        private String money;
        private String postStatus;
        private String isLike;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getUserFlag() {
            return userFlag;
        }

        public void setUserFlag(String userFlag) {
            this.userFlag = userFlag;
        }

        public String getQualityFlag() {
            return qualityFlag;
        }

        public void setQualityFlag(String qualityFlag) {
            this.qualityFlag = qualityFlag;
        }

        public String getCommentsNum() {
            return commentsNum;
        }

        public void setCommentsNum(String commentsNum) {
            this.commentsNum = commentsNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAuditFlag() {
            return auditFlag;
        }

        public void setAuditFlag(String auditFlag) {
            this.auditFlag = auditFlag;
        }

        public String getInfoSource() {
            return infoSource;
        }

        public void setInfoSource(String infoSource) {
            this.infoSource = infoSource;
        }

        public String getBetContent() {
            return betContent;
        }

        public void setBetContent(String betContent) {
            this.betContent = betContent;
        }

        public String getPostFlag() {
            return postFlag;
        }

        public void setPostFlag(String postFlag) {
            this.postFlag = postFlag;
        }

        public String getBrowseNum() {
            return browseNum;
        }

        public void setBrowseNum(String browseNum) {
            this.browseNum = browseNum;
        }

        public String getIssueNo() {
            return issueNo;
        }

        public void setIssueNo(String issueNo) {
            this.issueNo = issueNo;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPostStatus() {
            return postStatus;
        }

        public void setPostStatus(String postStatus) {
            this.postStatus = postStatus;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }
    }
}
