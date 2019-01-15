package com.daxiang.lottery.forum.bean;

import java.util.List;

/**
 * Created by Android on 2018/8/17.
 */

public class MessageBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"totalRecords":16,"totalPages":2,"pageIndex":2,"pageSize":10,"list":[{"userId":8012213501700010,"userName":"董硕11","content":"这是针对第二条评论的回复的回复的回复","createTime":1533786355000,"commentId":8,"toReplyId":3,"replyFlag":1,"replyInfo":{"replyType":0,"commnetId":8,"createTime":1533786355000,"replyId":2,"toUserName":"董硕","userName":"董硕11","userId":8012213501700010,"toUserId":8071315483000007,"content":"这是针对第二条评论的回复"},"postUserId":8071315483000007,"postUserName":"董硕","postTitle":"半全场3串4搏冷神准 浙江彩民40元斩15.3万","postId":1206}]}
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
         * totalRecords : 16
         * totalPages : 2
         * pageIndex : 2
         * pageSize : 10
         * list : [{"userId":8012213501700010,"userName":"董硕11","content":"这是针对第二条评论的回复的回复的回复","createTime":1533786355000,"commentId":8,"toReplyId":3,"replyFlag":1,"replyInfo":{"replyType":0,"commnetId":8,"createTime":1533786355000,"replyId":2,"toUserName":"董硕","userName":"董硕11","userId":8012213501700010,"toUserId":8071315483000007,"content":"这是针对第二条评论的回复"},"postUserId":8071315483000007,"postUserName":"董硕","postTitle":"半全场3串4搏冷神准 浙江彩民40元斩15.3万","postId":1206}]
         */

        private int totalRecords;
        private int totalPages;
        private int pageIndex;
        private int pageSize;
        private List<ListBean> list;

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * userId : 8012213501700010
             * userName : 董硕11
             * content : 这是针对第二条评论的回复的回复的回复
             * createTime : 1533786355000
             * commentId : 8
             * toReplyId : 3
             * replyFlag : 1
             * replyInfo : {"replyType":0,"commnetId":8,"createTime":1533786355000,"replyId":2,"toUserName":"董硕","userName":"董硕11","userId":8012213501700010,"toUserId":8071315483000007,"content":"这是针对第二条评论的回复"}
             * postUserId : 8071315483000007
             * postUserName : 董硕
             * postTitle : 半全场3串4搏冷神准 浙江彩民40元斩15.3万
             * postId : 1206
             */

            private String userId;
            private String userName;
            private String content;
            private String createTime;
            private int commentId;
            private int toReplyId;
            private int replyId;
            private int replyFlag;
            private ReplyInfoBean replyInfo;
            private String postUserId;
            private String postUserName;
            private String postTitle;
            private String postPictureUrl;
            private int postId;

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public int getToReplyId() {
                return toReplyId;
            }

            public void setToReplyId(int toReplyId) {
                this.toReplyId = toReplyId;
            }

            public int getReplyFlag() {
                return replyFlag;
            }

            public void setReplyFlag(int replyFlag) {
                this.replyFlag = replyFlag;
            }

            public ReplyInfoBean getReplyInfo() {
                return replyInfo;
            }

            public void setReplyInfo(ReplyInfoBean replyInfo) {
                this.replyInfo = replyInfo;
            }

            public String getPostUserId() {
                return postUserId;
            }

            public void setPostUserId(String postUserId) {
                this.postUserId = postUserId;
            }

            public String getPostUserName() {
                return postUserName;
            }

            public void setPostUserName(String postUserName) {
                this.postUserName = postUserName;
            }

            public String getPostTitle() {
                return postTitle;
            }

            public void setPostTitle(String postTitle) {
                this.postTitle = postTitle;
            }

            public String getPostPictureUrl() {
                return postPictureUrl;
            }

            public void setPostPictureUrl(String postPictureUrl) {
                this.postPictureUrl = postPictureUrl;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public static class ReplyInfoBean {
                /**
                 * replyType : 0
                 * commnetId : 8
                 * createTime : 1533786355000
                 * replyId : 2
                 * toUserName : 董硕
                 * userName : 董硕11
                 * userId : 8012213501700010
                 * toUserId : 8071315483000007
                 * content : 这是针对第二条评论的回复
                 */

                private int replyType;
                private int commnetId;
                private long createTime;
                private int replyId;
                private String toUserName;
                private String userName;
                private String userId;
                private String toUserId;
                private String content;

                public int getReplyType() {
                    return replyType;
                }

                public void setReplyType(int replyType) {
                    this.replyType = replyType;
                }

                public int getCommnetId() {
                    return commnetId;
                }

                public void setCommnetId(int commnetId) {
                    this.commnetId = commnetId;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public int getReplyId() {
                    return replyId;
                }

                public void setReplyId(int replyId) {
                    this.replyId = replyId;
                }

                public String getToUserName() {
                    return toUserName;
                }

                public void setToUserName(String toUserName) {
                    this.toUserName = toUserName;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getToUserId() {
                    return toUserId;
                }

                public void setToUserId(String toUserId) {
                    this.toUserId = toUserId;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }
    }
}
