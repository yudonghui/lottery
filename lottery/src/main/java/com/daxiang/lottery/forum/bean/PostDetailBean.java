package com.daxiang.lottery.forum.bean;

/**
 * Created by Android on 2018/8/21.
 */

public class PostDetailBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"id":2,"userId":1234567,"userName":"于冬辉","title":"第二个帖子","pictureUrl":"","likeNum":56,"topWeight":0,"userFlag":0,"qualityFlag":2,"commentsNum":2,"createTime":1505036609000,"updateTime":1533548609000,"auditFlag":1,"infoSource":"于冬辉","betContent":"","reportNum":0,"postFlag":"不分类","postType":0,"browseNum":0,"issueNo":null,"money":null,"postStatus":0,"content":"今天是个好日子天气不错","isLike":null,"commentInfoModels":null}
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
         * id : 2
         * userId : 1234567
         * userName : 于冬辉
         * title : 第二个帖子
         * pictureUrl :
         * likeNum : 56
         * topWeight : 0
         * userFlag : 0
         * qualityFlag : 2
         * commentsNum : 2
         * createTime : 1505036609000
         * updateTime : 1533548609000
         * auditFlag : 1
         * infoSource : 于冬辉
         * betContent :
         * reportNum : 0
         * postFlag : 不分类
         * postType : 0
         * browseNum : 0
         * issueNo : null
         * money : null
         * postStatus : 0
         * content : 今天是个好日子天气不错
         * isLike : null
         * commentInfoModels : null
         */

        private int id;
        private String userId;
        private String userName;
        private String title;
        private String pictureUrl;
        private int likeNum;
        private int topWeight;
        private int userFlag;
        private int qualityFlag;
        private int commentsNum;
        private long createTime;
        private long updateTime;
        private int auditFlag;
        private String infoSource;
        private String betContent;
        private int reportNum;
        private String postFlag;
        private int postType;
        private int browseNum;
        private String issueNo;
        private String money;
        private int postStatus;
        private String content;
        private String isLike;
        private String isFocus;
        private String commentInfoModels;

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

        public int getTopWeight() {
            return topWeight;
        }

        public void setTopWeight(int topWeight) {
            this.topWeight = topWeight;
        }

        public int getUserFlag() {
            return userFlag;
        }

        public void setUserFlag(int userFlag) {
            this.userFlag = userFlag;
        }

        public int getQualityFlag() {
            return qualityFlag;
        }

        public void setQualityFlag(int qualityFlag) {
            this.qualityFlag = qualityFlag;
        }

        public int getCommentsNum() {
            return commentsNum;
        }

        public void setCommentsNum(int commentsNum) {
            this.commentsNum = commentsNum;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getAuditFlag() {
            return auditFlag;
        }

        public void setAuditFlag(int auditFlag) {
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

        public int getReportNum() {
            return reportNum;
        }

        public void setReportNum(int reportNum) {
            this.reportNum = reportNum;
        }

        public String getPostFlag() {
            return postFlag;
        }

        public void setPostFlag(String postFlag) {
            this.postFlag = postFlag;
        }

        public int getPostType() {
            return postType;
        }

        public void setPostType(int postType) {
            this.postType = postType;
        }

        public int getBrowseNum() {
            return browseNum;
        }

        public void setBrowseNum(int browseNum) {
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

        public int getPostStatus() {
            return postStatus;
        }

        public void setPostStatus(int postStatus) {
            this.postStatus = postStatus;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }

        public String getCommentInfoModels() {
            return commentInfoModels;
        }

        public void setCommentInfoModels(String commentInfoModels) {
            this.commentInfoModels = commentInfoModels;
        }

        public String getIsFocus() {
            return isFocus;
        }

        public void setIsFocus(String isFocus) {
            this.isFocus = isFocus;
        }
    }
}
