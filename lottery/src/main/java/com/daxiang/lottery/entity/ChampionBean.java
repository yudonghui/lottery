package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/5/2.
 */

public class ChampionBean {

    /**
     * sign : f0edc071b981f9da25bedec8fd6db77b
     * msg : 成功
     * code : 0
     * data : {"items":[{"serial":1,"prize":2.9,"team_name":"巴西","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/417.png","status":1},{"serial":2,"prize":3.5,"team_name":"德国","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/377.png","status":1},{"serial":3,"prize":4.1,"team_name":"西班牙","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/23.png","status":1},{"serial":4,"prize":4.5,"team_name":"阿根廷","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/413.png","status":1},{"serial":5,"prize":5,"team_name":"法国","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/375.png","status":1},{"serial":6,"prize":10,"team_name":"比利时","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/363.png","status":1},{"serial":7,"prize":15,"team_name":"葡萄牙","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/1044.png","status":1},{"serial":8,"prize":14,"team_name":"英格兰","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/370.png","status":1},{"serial":9,"prize":22,"team_name":"乌拉圭","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/414.png","status":1},{"serial":10,"prize":25,"team_name":"哥伦比亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/410.png","status":1},{"serial":11,"prize":35,"team_name":"克罗地亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/366.png","status":1},{"serial":12,"prize":70,"team_name":"俄罗斯","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/396.png","status":1},{"serial":13,"prize":70,"team_name":"墨西哥","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/416.png","status":1},{"serial":14,"prize":65,"team_name":"波兰","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/392.png","status":1},{"serial":15,"prize":125,"team_name":"瑞士","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/403.png","status":1},{"serial":16,"prize":100,"team_name":"丹麦","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/369.png","status":1},{"serial":17,"prize":175,"team_name":"塞尔维亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/399.png","status":1},{"serial":18,"prize":200,"team_name":"瑞典","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/402.png","status":1},{"serial":19,"prize":200,"team_name":"秘鲁","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/409.png","status":1},{"serial":20,"prize":250,"team_name":"日本","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/438.png","status":1},{"serial":21,"prize":250,"team_name":"尼日利亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/773.png","status":1},{"serial":22,"prize":200,"team_name":"塞内加尔","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/774.png","status":1},{"serial":23,"prize":300,"team_name":"埃及","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/543.png","status":1},{"serial":24,"prize":250,"team_name":"冰岛","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/380.png","status":1},{"serial":25,"prize":700,"team_name":"突尼斯","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/425.png","status":1},{"serial":26,"prize":600,"team_name":"澳大利亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/421.png","status":1},{"serial":27,"prize":500,"team_name":"摩洛哥","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/424.png","status":1},{"serial":28,"prize":600,"team_name":"韩国","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/430.png","status":1},{"serial":29,"prize":600,"team_name":"伊朗","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/440.png","status":1},{"serial":30,"prize":500,"team_name":"哥斯达黎加","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/420.png","status":1},{"serial":31,"prize":800,"team_name":"巴拿马","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/524.png","status":1},{"serial":32,"prize":1000,"team_name":"沙特","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/434.png","status":1}],"timeStamp":1525242050270}
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
         * items : [{"serial":1,"prize":2.9,"team_name":"巴西","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/417.png","status":1},{"serial":2,"prize":3.5,"team_name":"德国","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/377.png","status":1},{"serial":3,"prize":4.1,"team_name":"西班牙","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/23.png","status":1},{"serial":4,"prize":4.5,"team_name":"阿根廷","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/413.png","status":1},{"serial":5,"prize":5,"team_name":"法国","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/375.png","status":1},{"serial":6,"prize":10,"team_name":"比利时","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/363.png","status":1},{"serial":7,"prize":15,"team_name":"葡萄牙","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/1044.png","status":1},{"serial":8,"prize":14,"team_name":"英格兰","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/370.png","status":1},{"serial":9,"prize":22,"team_name":"乌拉圭","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/414.png","status":1},{"serial":10,"prize":25,"team_name":"哥伦比亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/410.png","status":1},{"serial":11,"prize":35,"team_name":"克罗地亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/366.png","status":1},{"serial":12,"prize":70,"team_name":"俄罗斯","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/396.png","status":1},{"serial":13,"prize":70,"team_name":"墨西哥","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/416.png","status":1},{"serial":14,"prize":65,"team_name":"波兰","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/392.png","status":1},{"serial":15,"prize":125,"team_name":"瑞士","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/403.png","status":1},{"serial":16,"prize":100,"team_name":"丹麦","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/369.png","status":1},{"serial":17,"prize":175,"team_name":"塞尔维亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/399.png","status":1},{"serial":18,"prize":200,"team_name":"瑞典","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/402.png","status":1},{"serial":19,"prize":200,"team_name":"秘鲁","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/409.png","status":1},{"serial":20,"prize":250,"team_name":"日本","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/438.png","status":1},{"serial":21,"prize":250,"team_name":"尼日利亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/773.png","status":1},{"serial":22,"prize":200,"team_name":"塞内加尔","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/774.png","status":1},{"serial":23,"prize":300,"team_name":"埃及","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/543.png","status":1},{"serial":24,"prize":250,"team_name":"冰岛","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/380.png","status":1},{"serial":25,"prize":700,"team_name":"突尼斯","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/425.png","status":1},{"serial":26,"prize":600,"team_name":"澳大利亚","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/421.png","status":1},{"serial":27,"prize":500,"team_name":"摩洛哥","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/424.png","status":1},{"serial":28,"prize":600,"team_name":"韩国","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/430.png","status":1},{"serial":29,"prize":600,"team_name":"伊朗","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/440.png","status":1},{"serial":30,"prize":500,"team_name":"哥斯达黎加","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/420.png","status":1},{"serial":31,"prize":800,"team_name":"巴拿马","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/524.png","status":1},{"serial":32,"prize":1000,"team_name":"沙特","team_logo":"http://static.sporttery.cn/sinaimg/football/wcp2018/434.png","status":1}]
         * timeStamp : 1525242050270
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
             * serial : 1
             * prize : 2.9
             * team_name : 巴西
             * team_logo : http://static.sporttery.cn/sinaimg/football/wcp2018/417.png
             * status : 1
             */

            private String serial;
            private String prize;
            private String team_name;
            private String team_logo;
            private int status;

            public String getSerial() {
                return serial;
            }

            public void setSerial(String serial) {
                this.serial = serial;
            }

            public String getPrize() {
                return prize;
            }

            public void setPrize(String prize) {
                this.prize = prize;
            }

            public String getTeam_name() {
                return team_name;
            }

            public void setTeam_name(String team_name) {
                this.team_name = team_name;
            }

            public String getTeam_logo() {
                return team_logo;
            }

            public void setTeam_logo(String team_logo) {
                this.team_logo = team_logo;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
