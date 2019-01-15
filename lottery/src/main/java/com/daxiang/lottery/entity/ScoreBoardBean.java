package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/4/18.
 */

public class ScoreBoardBean {

    /**
     * sign : b33a3c7f6868d805f60a941e8d762266
     * msg : 成功
     * code : 0
     * data : {"items":[{"gln":"90/24/66","rank":1,"team_name":"曼彻斯特城","wdl":"27/3/2","point":84,"match_completed ":32},{"gln":"63/25/38","rank":2,"team_name":"曼彻斯特联","wdl":"22/5/5","point":71,"match_completed ":32},{"gln":"75/35/40","rank":3,"team_name":"利物浦","wdl":"19/10/4","point":67,"match_completed ":33},{"gln":"64/27/37","rank":4,"team_name":"托特纳姆热刺","wdl":"20/7/5","point":67,"match_completed ":32},{"gln":"54/31/23","rank":5,"team_name":"切尔西","wdl":"17/6/9","point":57,"match_completed ":32},{"gln":"61/43/18","rank":6,"team_name":"阿森纳","wdl":"16/6/10","point":54,"match_completed ":32},{"gln":"31/28/3","rank":7,"team_name":"伯恩利","wdl":"13/10/9","point":49,"match_completed ":32},{"gln":"48/45/3","rank":8,"team_name":"莱斯特城","wdl":"11/10/11","point":43,"match_completed ":32},{"gln":"38/53/-15","rank":9,"team_name":"埃弗顿","wdl":"11/8/14","point":41,"match_completed ":33},{"gln":"41/53/-12","rank":10,"team_name":"伯恩茅斯","wdl":"9/11/13","point":38,"match_completed ":33},{"gln":"33/41/-8","rank":11,"team_name":"纽卡斯尔联","wdl":"10/8/14","point":38,"match_completed ":32},{"gln":"42/59/-17","rank":12,"team_name":"沃特福德","wdl":"10/7/16","point":37,"match_completed ":33},{"gln":"29/43/-14","rank":13,"team_name":"布莱顿","wdl":"8/11/13","point":35,"match_completed ":32},{"gln":"40/58/-18","rank":14,"team_name":"西汉姆联","wdl":"8/10/14","point":34,"match_completed ":32},{"gln":"36/54/-18","rank":15,"team_name":"水晶宫","wdl":"8/10/16","point":34,"match_completed ":34},{"gln":"26/45/-19","rank":16,"team_name":"斯旺西","wdl":"8/8/16","point":32,"match_completed ":32},{"gln":"26/54/-28","rank":17,"team_name":"哈德斯菲尔德","wdl":"8/8/17","point":32,"match_completed ":33},{"gln":"33/53/-20","rank":18,"team_name":"南安普顿","wdl":"5/13/15","point":28,"match_completed ":33},{"gln":"30/63/-33","rank":19,"team_name":"斯托克城","wdl":"6/9/18","point":27,"match_completed ":33},{"gln":"26/52/-26","rank":20,"team_name":"西布罗姆维奇","wdl":"3/12/18","point":21,"match_completed ":33}],"timeStamp":1524034822300}
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
         * items : [{"gln":"90/24/66","rank":1,"team_name":"曼彻斯特城","wdl":"27/3/2","point":84,"match_completed ":32},{"gln":"63/25/38","rank":2,"team_name":"曼彻斯特联","wdl":"22/5/5","point":71,"match_completed ":32},{"gln":"75/35/40","rank":3,"team_name":"利物浦","wdl":"19/10/4","point":67,"match_completed ":33},{"gln":"64/27/37","rank":4,"team_name":"托特纳姆热刺","wdl":"20/7/5","point":67,"match_completed ":32},{"gln":"54/31/23","rank":5,"team_name":"切尔西","wdl":"17/6/9","point":57,"match_completed ":32},{"gln":"61/43/18","rank":6,"team_name":"阿森纳","wdl":"16/6/10","point":54,"match_completed ":32},{"gln":"31/28/3","rank":7,"team_name":"伯恩利","wdl":"13/10/9","point":49,"match_completed ":32},{"gln":"48/45/3","rank":8,"team_name":"莱斯特城","wdl":"11/10/11","point":43,"match_completed ":32},{"gln":"38/53/-15","rank":9,"team_name":"埃弗顿","wdl":"11/8/14","point":41,"match_completed ":33},{"gln":"41/53/-12","rank":10,"team_name":"伯恩茅斯","wdl":"9/11/13","point":38,"match_completed ":33},{"gln":"33/41/-8","rank":11,"team_name":"纽卡斯尔联","wdl":"10/8/14","point":38,"match_completed ":32},{"gln":"42/59/-17","rank":12,"team_name":"沃特福德","wdl":"10/7/16","point":37,"match_completed ":33},{"gln":"29/43/-14","rank":13,"team_name":"布莱顿","wdl":"8/11/13","point":35,"match_completed ":32},{"gln":"40/58/-18","rank":14,"team_name":"西汉姆联","wdl":"8/10/14","point":34,"match_completed ":32},{"gln":"36/54/-18","rank":15,"team_name":"水晶宫","wdl":"8/10/16","point":34,"match_completed ":34},{"gln":"26/45/-19","rank":16,"team_name":"斯旺西","wdl":"8/8/16","point":32,"match_completed ":32},{"gln":"26/54/-28","rank":17,"team_name":"哈德斯菲尔德","wdl":"8/8/17","point":32,"match_completed ":33},{"gln":"33/53/-20","rank":18,"team_name":"南安普顿","wdl":"5/13/15","point":28,"match_completed ":33},{"gln":"30/63/-33","rank":19,"team_name":"斯托克城","wdl":"6/9/18","point":27,"match_completed ":33},{"gln":"26/52/-26","rank":20,"team_name":"西布罗姆维奇","wdl":"3/12/18","point":21,"match_completed ":33}]
         * timeStamp : 1524034822300
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
             * gln : 90/24/66
             * rank : 1
             * team_name : 曼彻斯特城
             * wdl : 27/3/2
             * point : 84
             * match_completed  : 32
             */

            private String gln;
            private String rank;
            private String team_id;
            private String team_name;
            private String wdl;
            private String point;
            private String match_completed;
            private int itemType;
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getItemType() {
                return itemType;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }

            public String getTeam_id() {
                return team_id;
            }

            public void setTeam_id(String team_id) {
                this.team_id = team_id;
            }

            public String getGln() {
                return gln;
            }

            public void setGln(String gln) {
                this.gln = gln;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getTeam_name() {
                return team_name;
            }

            public void setTeam_name(String team_name) {
                this.team_name = team_name;
            }

            public String getWdl() {
                return wdl;
            }

            public void setWdl(String wdl) {
                this.wdl = wdl;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public String getMatch_completed() {
                return match_completed;
            }

            public void setMatch_completed(String match_completed) {
                this.match_completed = match_completed;
            }
        }
    }
}
