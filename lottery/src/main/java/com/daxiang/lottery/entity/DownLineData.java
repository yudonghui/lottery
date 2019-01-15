package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class DownLineData {


    /**
     * sign : 94372baffa8a525eff63bffeca95c285
     * msg : 成功
     * code : 0
     * data : {"items":{"isFind":0,"pages":1,"data":[{"parentUserId":7062718143800001,"mobile":"15136778516","parentUserName":"凤凰","userType":1,"userName":"东说","userId":7062814351400006},{"parentUserId":7062718143800001,"mobile":"17612101521","parentUserName":"凤凰","userType":1,"userName":"涅槃","userId":7062814351400011}],"totals":2},"timeStamp":1498812578564}
     */

    private String sign;
    private String msg;
    private int code;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * items : {"isFind":0,"pages":1,"data":[{"parentUserId":7062718143800001,"mobile":"15136778516","parentUserName":"凤凰","userType":1,"userName":"东说","userId":7062814351400006},{"parentUserId":7062718143800001,"mobile":"17612101521","parentUserName":"凤凰","userType":1,"userName":"涅槃","userId":7062814351400011}],"totals":2}
         * timeStamp : 1498812578564
         */

        private ItemsBean items;
        private long timeStamp;

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public static class ItemsBean {
            /**
             * isFind : 0
             * pages : 1
             * data : [{"parentUserId":7062718143800001,"mobile":"15136778516","parentUserName":"凤凰","userType":1,"userName":"东说","userId":7062814351400006},{"parentUserId":7062718143800001,"mobile":"17612101521","parentUserName":"凤凰","userType":1,"userName":"涅槃","userId":7062814351400011}]
             * totals : 2
             */

            private int isFind;
            private int pages;
            private int totals;
            private List<DataBean> data;

            public int getIsFind() {
                return isFind;
            }

            public void setIsFind(int isFind) {
                this.isFind = isFind;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public int getTotals() {
                return totals;
            }

            public void setTotals(int totals) {
                this.totals = totals;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * parentUserId : 7062718143800001
                 * mobile : 15136778516
                 * parentUserName : 凤凰
                 * userType : 1
                 * userName : 东说
                 * userId : 7062814351400006
                 */

                private long parentUserId;
                private String mobile;
                private String parentUserName;
                private int userType;
                private String userName;
                private String userId;
                private long createTime;

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public long getParentUserId() {
                    return parentUserId;
                }

                public void setParentUserId(long parentUserId) {
                    this.parentUserId = parentUserId;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getParentUserName() {
                    return parentUserName;
                }

                public void setParentUserName(String parentUserName) {
                    this.parentUserName = parentUserName;
                }

                public int getUserType() {
                    return userType;
                }

                public void setUserType(int userType) {
                    this.userType = userType;
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
            }
        }
    }
}
