package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/28.
 */

public class MatchsData  {

    /**
     * sign : 78ac7e0a919cf4c3d73f25c6475e055a
     * msg : 成功
     * code : 0
     * data : {"items":[{"league_name":"中超","icon":"https://m.api.iuliao.com/lgimg/152.jpg ","league_id":152}],"timeStamp":1524019768818}
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
         * items : [{"league_name":"中超","icon":"https://m.api.iuliao.com/lgimg/152.jpg ","league_id":152}]
         * timeStamp : 1524019768818
         */

        private long timeStamp;
        private List<ItemsBean> items;

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * league_name : 中超
             * icon : https://m.api.iuliao.com/lgimg/152.jpg
             * league_id : 152
             */

            private String league_name;
            private String icon;
            private String league_id;

            public String getLeague_name() {
                return league_name;
            }

            public void setLeague_name(String league_name) {
                this.league_name = league_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLeague_id() {
                return league_id;
            }

            public void setLeague_id(String league_id) {
                this.league_id = league_id;
            }
        }
    }
}
