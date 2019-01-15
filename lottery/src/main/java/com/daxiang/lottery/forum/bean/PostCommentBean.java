package com.daxiang.lottery.forum.bean;

import java.util.List;

/**
 * Created by Android on 2018/8/22.
 */

public class PostCommentBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"totalRecords":18,"totalPages":2,"pageIndex":1,"pageSize":10,"list":[{"id":8,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第二条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":[{"id":1,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第一条评论的回复","createTime":1534227838000,"replyFlag":1,"violationFlag":0},{"id":2,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第二条评论的回复","createTime":1533786355000,"replyFlag":1,"violationFlag":0},{"id":3,"commentsId":8,"postId":1206,"userId":8071315483000007,"userName":"董硕","toReplyId":2,"toUserId":8012213501700010,"toUserName":"董硕11","replyContent":"这是针对第二条评论的回复的回复","createTime":1533804355000,"replyFlag":0,"violationFlag":0},{"id":6,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第三条评论的回复","createTime":1534232755000,"replyFlag":1,"violationFlag":0},{"id":7,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第四条评论的回复","createTime":1534232755000,"replyFlag":1,"violationFlag":0}]},{"id":9,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":10,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":11,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":12,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":13,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":14,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":15,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":16,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null}]}
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
         * totalRecords : 18
         * totalPages : 2
         * pageIndex : 1
         * pageSize : 10
         * list : [{"id":8,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第二条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":[{"id":1,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第一条评论的回复","createTime":1534227838000,"replyFlag":1,"violationFlag":0},{"id":2,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第二条评论的回复","createTime":1533786355000,"replyFlag":1,"violationFlag":0},{"id":3,"commentsId":8,"postId":1206,"userId":8071315483000007,"userName":"董硕","toReplyId":2,"toUserId":8012213501700010,"toUserName":"董硕11","replyContent":"这是针对第二条评论的回复的回复","createTime":1533804355000,"replyFlag":0,"violationFlag":0},{"id":6,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第三条评论的回复","createTime":1534232755000,"replyFlag":1,"violationFlag":0},{"id":7,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第四条评论的回复","createTime":1534232755000,"replyFlag":1,"violationFlag":0}]},{"id":9,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":10,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":11,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":12,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":13,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":14,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":15,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null},{"id":16,"postId":1206,"postUserId":2017030615272000055,"userId":8071315483000007,"userName":"董硕","content":"这是第N条评论","likeNum":0,"replyNum":0,"createTime":1533784282000,"updateTime":1533784282000,"violationFlag":0,"reportNum":0,"isLike":null,"replyInfoModels":null}]
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
             * id : 8
             * postId : 1206
             * postUserId : 2017030615272000055
             * userId : 8071315483000007
             * userName : 董硕
             * content : 这是第二条评论
             * likeNum : 0
             * replyNum : 0
             * createTime : 1533784282000
             * updateTime : 1533784282000
             * violationFlag : 0
             * reportNum : 0
             * isLike : null
             * replyInfoModels : [{"id":1,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第一条评论的回复","createTime":1534227838000,"replyFlag":1,"violationFlag":0},{"id":2,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第二条评论的回复","createTime":1533786355000,"replyFlag":1,"violationFlag":0},{"id":3,"commentsId":8,"postId":1206,"userId":8071315483000007,"userName":"董硕","toReplyId":2,"toUserId":8012213501700010,"toUserName":"董硕11","replyContent":"这是针对第二条评论的回复的回复","createTime":1533804355000,"replyFlag":0,"violationFlag":0},{"id":6,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第三条评论的回复","createTime":1534232755000,"replyFlag":1,"violationFlag":0},{"id":7,"commentsId":8,"postId":1206,"userId":8012213501700010,"userName":"董硕11","toReplyId":null,"toUserId":8071315483000007,"toUserName":"董硕","replyContent":"这是针对第四条评论的回复","createTime":1534232755000,"replyFlag":1,"violationFlag":0}]
             */

            private int id;
            private int postId;
            private long postUserId;
            private String userId;
            private String userName;
            private String content;
            private int likeNum;
            private int replyNum;
            private long createTime;
            private long updateTime;
            private int violationFlag;
            private int reportNum;
            private String isLike;
            private List<ReplyInfoModelsBean> replyInfoModels;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public long getPostUserId() {
                return postUserId;
            }

            public void setPostUserId(long postUserId) {
                this.postUserId = postUserId;
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

            public int getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(int likeNum) {
                this.likeNum = likeNum;
            }

            public int getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(int replyNum) {
                this.replyNum = replyNum;
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

            public int getViolationFlag() {
                return violationFlag;
            }

            public void setViolationFlag(int violationFlag) {
                this.violationFlag = violationFlag;
            }

            public int getReportNum() {
                return reportNum;
            }

            public void setReportNum(int reportNum) {
                this.reportNum = reportNum;
            }

            public String getIsLike() {
                return isLike;
            }

            public void setIsLike(String isLike) {
                this.isLike = isLike;
            }

            public List<ReplyInfoModelsBean> getReplyInfoModels() {
                return replyInfoModels;
            }

            public void setReplyInfoModels(List<ReplyInfoModelsBean> replyInfoModels) {
                this.replyInfoModels = replyInfoModels;
            }

            public static class ReplyInfoModelsBean{
                private String id;
                private String commentsId;
                private String postId;
                private String userId;
                private String userName;
                private String toReplyId;
                private String toUserId;
                private String toUserName;
                private String replyContent;
                private long createTime;
                private int replyFlag;
                private int violationFlag;
                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCommentsId() {
                    return commentsId;
                }

                public void setCommentsId(String commentsId) {
                    this.commentsId = commentsId;
                }

                public String getPostId() {
                    return postId;
                }

                public void setPostId(String postId) {
                    this.postId = postId;
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

                public String getToReplyId() {
                    return toReplyId;
                }

                public void setToReplyId(String toReplyId) {
                    this.toReplyId = toReplyId;
                }

                public String getToUserId() {
                    return toUserId;
                }

                public void setToUserId(String toUserId) {
                    this.toUserId = toUserId;
                }

                public String getToUserName() {
                    return toUserName;
                }

                public void setToUserName(String toUserName) {
                    this.toUserName = toUserName;
                }

                public String getReplyContent() {
                    return replyContent;
                }

                public void setReplyContent(String replyContent) {
                    this.replyContent = replyContent;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public int getReplyFlag() {
                    return replyFlag;
                }

                public void setReplyFlag(int replyFlag) {
                    this.replyFlag = replyFlag;
                }

                public int getViolationFlag() {
                    return violationFlag;
                }

                public void setViolationFlag(int violationFlag) {
                    this.violationFlag = violationFlag;
                }
            }
        }
    }
}
