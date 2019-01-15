package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/9.
 */

public class HotMatchData {

    /**
     * code : 0
     * msg : 获取热门赛事成功
     * data : [{"league_short_cn":"意甲","rate":{"lose":"19.55%","draw":"24.45%","win":"56.00%"},"h_pic":"http://static.sporttery.cn/images/2017/02/14/11_1405517101.png","session":"20180309024","guest_short_cn":"都灵","odds":{"odds3":"1.57","odds1":"3.70","odds0":"4.50"},"home_short_cn":"罗马","stop_sale_time":1520610600000,"a_pic":"http://static.sporttery.com/images/2013/01/14/23_1427079524.png"},{"league_short_cn":"英超","rate":{"lose":"35.37%","draw":"28.99%","win":"35.64%"},"h_pic":"http://static.sporttery.com/images/2013/01/12/11_1335347161.png","session":"20180310023","guest_short_cn":"利物浦","odds":{"odds3":"2.48","odds1":"3.15","odds0":"2.45"},"home_short_cn":"曼联","stop_sale_time":1520684400000,"a_pic":"http://static.sporttery.com/images/2013/01/12/11_1334022591.png"},{"league_short_cn":"英超","rate":{"lose":"27.28%","draw":"30.53%","win":"42.19%"},"h_pic":"http://static.sporttery.cn/images/2017/04/21/11_1614179331.png","session":"20180310034","guest_short_cn":"伯恩利","odds":{"odds3":"2.12","odds1":"2.91","odds0":"3.18"},"home_short_cn":"西汉姆联","stop_sale_time":1520693400000,"a_pic":"http://static.sporttery.com/images/2013/01/12/11_1351387341.png"},{"league_short_cn":"英超","rate":{"lose":"9.09%","draw":"17.77%","win":"73.14%"},"h_pic":"http://static.sporttery.com/images/2013/01/09/11_1542417551.png","session":"20180310066","guest_short_cn":"水晶宫","odds":{"odds3":"1.20","odds1":"5.10","odds0":"10.00"},"home_short_cn":"切尔西","stop_sale_time":1520700600000,"a_pic":"http://static.sporttery.cn/images/2017/02/14/11_1401247631.png"},{"league_short_cn":"英超","rate":{"lose":"15.38%","draw":"21.05%","win":"63.57%"},"h_pic":"http://static.sporttery.com/images/2013/01/12/11_1315467991.png","session":"20180311023","guest_short_cn":"沃特福德","odds":{"odds3":"1.37","odds1":"4.30","odds0":"6.00"},"home_short_cn":"阿森纳","stop_sale_time":1520774400000,"a_pic":"http://static.sporttery.com/images/2013/01/12/11_1430518611.png"},{"league_short_cn":"英超","rate":{"lose":"62.81%","draw":"21.09%","win":"16.10%"},"h_pic":"http://static.sporttery.com/images/2013/01/12/11_1436458911.png","session":"20180311045","guest_short_cn":"热刺","odds":{"odds3":"5.50","odds1":"4.20","odds0":"1.41"},"home_short_cn":"伯恩茅斯","stop_sale_time":1520783400000,"a_pic":"http://static.sporttery.com/images/2013/01/12/11_1347358361.png"},{"league_short_cn":"英超","rate":{"lose":"77.72%","draw":"14.89%","win":"7.38%"},"h_pic":"http://static.sporttery.com/images/2013/01/12/11_1428147381.png","session":"20180312021","guest_short_cn":"曼城","odds":{"odds3":"12.00","odds1":"5.95","odds0":"1.14"},"home_short_cn":"斯托克城","stop_sale_time":1520869800000,"a_pic":"http://static.sporttery.cn/images/2017/01/25/9_0953136371.png"}]
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
         * league_short_cn : 意甲
         * rate : {"lose":"19.55%","draw":"24.45%","win":"56.00%"}
         * h_pic : http://static.sporttery.cn/images/2017/02/14/11_1405517101.png
         * session : 20180309024
         * guest_short_cn : 都灵
         * odds : {"odds3":"1.57","odds1":"3.70","odds0":"4.50"}
         * home_short_cn : 罗马
         * stop_sale_time : 1520610600000
         * a_pic : http://static.sporttery.com/images/2013/01/14/23_1427079524.png
         */

        private String league_short_cn;
        private RateBean rate;
        private String h_pic;
        private String session;
        private String guest_short_cn;
        private OddsBean odds;
        private String home_short_cn;
        private long stop_sale_time;
        private long kick_off_time;
        private String a_pic;

        public String getLeague_short_cn() {
            return league_short_cn;
        }

        public void setLeague_short_cn(String league_short_cn) {
            this.league_short_cn = league_short_cn;
        }

        public RateBean getRate() {
            return rate;
        }

        public void setRate(RateBean rate) {
            this.rate = rate;
        }

        public String getH_pic() {
            return h_pic;
        }

        public void setH_pic(String h_pic) {
            this.h_pic = h_pic;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public String getGuest_short_cn() {
            return guest_short_cn;
        }

        public void setGuest_short_cn(String guest_short_cn) {
            this.guest_short_cn = guest_short_cn;
        }

        public OddsBean getOdds() {
            return odds;
        }

        public void setOdds(OddsBean odds) {
            this.odds = odds;
        }

        public String getHome_short_cn() {
            return home_short_cn;
        }

        public void setHome_short_cn(String home_short_cn) {
            this.home_short_cn = home_short_cn;
        }

        public long getStop_sale_time() {
            return stop_sale_time;
        }

        public void setStop_sale_time(long stop_sale_time) {
            this.stop_sale_time = stop_sale_time;
        }

        public long getKick_off_time() {
            return kick_off_time;
        }

        public void setKick_off_time(long kick_off_time) {
            this.kick_off_time = kick_off_time;
        }

        public String getA_pic() {
            return a_pic;
        }

        public void setA_pic(String a_pic) {
            this.a_pic = a_pic;
        }

        public static class RateBean {
            /**
             * lose : 19.55%
             * draw : 24.45%
             * win : 56.00%
             */

            private String lose;
            private String draw;
            private String win;

            public String getLose() {
                return lose;
            }

            public void setLose(String lose) {
                this.lose = lose;
            }

            public String getDraw() {
                return draw;
            }

            public void setDraw(String draw) {
                this.draw = draw;
            }

            public String getWin() {
                return win;
            }

            public void setWin(String win) {
                this.win = win;
            }
        }

        public static class OddsBean {
            /**
             * odds3 : 1.57
             * odds1 : 3.70
             * odds0 : 4.50
             */

            private String odds3;
            private String odds1;
            private String odds0;

            public String getOdds3() {
                return odds3;
            }

            public void setOdds3(String odds3) {
                this.odds3 = odds3;
            }

            public String getOdds1() {
                return odds1;
            }

            public void setOdds1(String odds1) {
                this.odds1 = odds1;
            }

            public String getOdds0() {
                return odds0;
            }

            public void setOdds0(String odds0) {
                this.odds0 = odds0;
            }
        }
    }
}
