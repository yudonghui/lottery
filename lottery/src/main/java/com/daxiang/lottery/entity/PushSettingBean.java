package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/4/19.
 */

public class PushSettingBean {

    /**
     * code : 0
     * msg : 获取推送配置成功
     * data : [{"id":1524048082757,"userId":7082113342900001,"pushType":"recommend","pushItem":"recommend","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082758,"userId":7082113342900001,"pushType":"prize","pushItem":"ssq","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082759,"userId":7082113342900001,"pushType":"prize","pushItem":"dlt","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082760,"userId":7082113342900001,"pushType":"prize","pushItem":"fc3d","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082761,"userId":7082113342900001,"pushType":"prize","pushItem":"pl3","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082762,"userId":7082113342900001,"pushType":"prize","pushItem":"pl5","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082763,"userId":7082113342900001,"pushType":"prize","pushItem":"qxc","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082764,"userId":7082113342900001,"pushType":"prize","pushItem":"qlc","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082765,"userId":7082113342900001,"pushType":"prize","pushItem":"sfc","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082766,"userId":7082113342900001,"pushType":"prize","pushItem":"rjc","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082767,"userId":7082113342900001,"pushType":"result","pushItem":"result","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082768,"userId":7082113342900001,"pushType":"win","pushItem":"win","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082769,"userId":7082113342900001,"pushType":"withdraw","pushItem":"withdraw","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082770,"userId":7082113342900001,"pushType":"cancel","pushItem":"cancel","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082771,"userId":7082113342900001,"pushType":"hot","pushItem":"hot","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082772,"userId":7082113342900001,"pushType":"notice","pushItem":"notice","createTime":1524049512000,"status":1,"remark":null},{"id":1524048082773,"userId":7082113342900001,"pushType":"pushTime","pushItem":"pushTime","createTime":1524049512000,"status":1,"remark":null}]
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
         * id : 1524048082757
         * userId : 7082113342900001
         * pushType : recommend
         * pushItem : recommend
         * createTime : 1524049512000
         * status : 1
         * remark : null
         */

        private long id;
        private String userId;
        private String pushType;
        private String pushItem;
        private String createTime;
        private int status;
        private String remark;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPushType() {
            return pushType;
        }

        public void setPushType(String pushType) {
            this.pushType = pushType;
        }

        public String getPushItem() {
            return pushItem;
        }

        public void setPushItem(String pushItem) {
            this.pushItem = pushItem;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
