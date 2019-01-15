package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/28.
 */

public class ShooterData {

    /**
     * sign : e710cb4780988315673c6d7b9c964d63
     * msg : 成功
     * code : 0
     * data : {"items":[{"get_ball":97,"penalty":null,"rank":1,"player_name":"白银","team_name":"范甘迪"},{"get_ball":24,"penalty":null,"rank":2,"player_name":"手动","team_name":"儿童"},{"get_ball":5,"penalty":null,"rank":3,"player_name":"射手","team_name":"3儿童"},{"get_ball":3,"penalty":null,"rank":4,"player_name":"球队","team_name":"大范甘迪的认同"}],"timeStamp":1524019763339}
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
         * items : [{"get_ball":97,"penalty":null,"rank":1,"player_name":"白银","team_name":"范甘迪"},{"get_ball":24,"penalty":null,"rank":2,"player_name":"手动","team_name":"儿童"},{"get_ball":5,"penalty":null,"rank":3,"player_name":"射手","team_name":"3儿童"},{"get_ball":3,"penalty":null,"rank":4,"player_name":"球队","team_name":"大范甘迪的认同"}]
         * timeStamp : 1524019763339
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
             * get_ball : 97
             * penalty : null
             * rank : 1
             * player_name : 白银
             * team_name : 范甘迪
             */

            private String get_ball;
            private String penalty;
            private String rank;
            private String player_name;
            private String team_name;

            public String getGet_ball() {
                return get_ball;
            }

            public void setGet_ball(String get_ball) {
                this.get_ball = get_ball;
            }

            public String getPenalty() {
                return penalty;
            }

            public void setPenalty(String penalty) {
                this.penalty = penalty;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getPlayer_name() {
                return player_name;
            }

            public void setPlayer_name(String player_name) {
                this.player_name = player_name;
            }

            public String getTeam_name() {
                return team_name;
            }

            public void setTeam_name(String team_name) {
                this.team_name = team_name;
            }
        }
    }
}
