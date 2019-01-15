package com.daxiang.lottery.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class CommentContentData {
    private int id;					//评论记录ID
    private String commnetAccount;	//评论人账号
    private String commentNickname;	//评论人昵称
    private String commentTime;		//评论时间
    private String commentContent;	//评论内容
    private String paisenum;

    public String getPaisenum() {
        return paisenum;
    }

    public void setPaisenum(String paisenum) {
        this.paisenum = paisenum;
    }

    private List<ReplyBean> replyList = new ArrayList<ReplyBean>();
    //回复内容列表
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCommnetAccount() {
        return commnetAccount;
    }
    public void setCommnetAccount(String commnetAccount) {
        this.commnetAccount = commnetAccount;
    }
    public String getCommentNickname() {
        return commentNickname;
    }
    public void setCommentNickname(String commentNickname) {
        this.commentNickname = commentNickname;
    }
    public String getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
    public String getCommentContent() {
        return commentContent;
    }
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public List<ReplyBean> getReplyList() {
        return replyList;
    }
    public void setReplyList(List<ReplyBean> replyList) {
        this.replyList = replyList;
    }
    public static class ReplyBean {
        private int id;					//内容ID
        private String replyAccount;	//回复人账号
        private String replyNickname;	//回复人昵称
        private String commentAccount;	//被回复人账号
        private String commentNickname;	//被回复人昵称
        private String replyContent;	//回复的内容
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getReplyAccount() {
            return replyAccount;
        }
        public void setReplyAccount(String replyAccount) {
            this.replyAccount = replyAccount;
        }
        public String getReplyNickname() {
            return replyNickname;
        }
        public void setReplyNickname(String replyNickname) {
            this.replyNickname = replyNickname;
        }
        public String getCommentAccount() {
            return commentAccount;
        }
        public void setCommentAccount(String commentAccount) {
            this.commentAccount = commentAccount;
        }
        public String getCommentNickname() {
            return commentNickname;
        }
        public void setCommentNickname(String commentNickname) {
            this.commentNickname = commentNickname;
        }
        public String getReplyContent() {
            return replyContent;
        }
        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }
    }

}
