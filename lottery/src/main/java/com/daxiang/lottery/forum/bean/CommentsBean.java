package com.daxiang.lottery.forum.bean;

import java.io.Serializable;

/**
 * Created by Android on 2018/8/22.
 */

public class CommentsBean implements Serializable{
    private String commentsId;
    private String toReplyId;
    private String content;
    private UserBean replyUser; // 回复人信息
    private UserBean commentsUser;  // 评论人信息

    public String getToReplyId() {
        return toReplyId;
    }

    public void setToReplyId(String toReplyId) {
        this.toReplyId = toReplyId;
    }

    public String getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(String commentsId) {
        this.commentsId = commentsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(UserBean replyUser) {
        this.replyUser = replyUser;
    }

    public UserBean getCommentsUser() {
        return commentsUser;
    }

    public void setCommentsUser(UserBean commentsUser) {
        this.commentsUser = commentsUser;
    }


}
