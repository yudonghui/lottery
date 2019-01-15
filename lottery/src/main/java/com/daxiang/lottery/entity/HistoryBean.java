package com.daxiang.lottery.entity;

/**
 * @author yudonghui
 * @date 2017/6/17
 * @describe May the Buddha bless bug-free!!!
 */
public class HistoryBean {

    /**
     * sign : 398b970bf52c954257d5fefbc2178ab3
     * msg : 成功
     * code : 0
     * data : {"items":{"ha_hda":{"a":"3胜3平4负","h":"7胜1平2负"},"rate":{"lose":"29.59%","draw":"38.47%","win":"31.94%"},"rank_h":"4","odds":{"oa":"3.12","od":"2.4","oh":"2.89"},"ha":"近10次交锋，主队 2胜 4平 4负","rank_a":"12"},"timeStamp":1523432765530}
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
         * items : {"ha_hda":{"a":"3胜3平4负","h":"7胜1平2负"},"rate":{"lose":"29.59%","draw":"38.47%","win":"31.94%"},"rank_h":"4","odds":{"oa":"3.12","od":"2.4","oh":"2.89"},"ha":"近10次交锋，主队 2胜 4平 4负","rank_a":"12"}
         * timeStamp : 1523432765530
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
             * ha_hda : {"a":"3胜3平4负","h":"7胜1平2负"}
             * rate : {"lose":"29.59%","draw":"38.47%","win":"31.94%"}
             * rank_h : 4
             * odds : {"oa":"3.12","od":"2.4","oh":"2.89"}
             * ha : 近10次交锋，主队 2胜 4平 4负
             * rank_a : 12
             */

            private HaHdaBean ha_hda;
            private RateBean rate;
            private String rank_h;
            private OddsBean odds;
            private String ha;
            private String rank_a;

            public HaHdaBean getHa_hda() {
                return ha_hda;
            }

            public void setHa_hda(HaHdaBean ha_hda) {
                this.ha_hda = ha_hda;
            }

            public RateBean getRate() {
                return rate;
            }

            public void setRate(RateBean rate) {
                this.rate = rate;
            }

            public String getRank_h() {
                return rank_h;
            }

            public void setRank_h(String rank_h) {
                this.rank_h = rank_h;
            }

            public OddsBean getOdds() {
                return odds;
            }

            public void setOdds(OddsBean odds) {
                this.odds = odds;
            }

            public String getHa() {
                return ha;
            }

            public void setHa(String ha) {
                this.ha = ha;
            }

            public String getRank_a() {
                return rank_a;
            }

            public void setRank_a(String rank_a) {
                this.rank_a = rank_a;
            }

            public static class HaHdaBean {
                /**
                 * a : 3胜3平4负
                 * h : 7胜1平2负
                 */

                private String a;
                private String h;

                public String getA() {
                    return a;
                }

                public void setA(String a) {
                    this.a = a;
                }

                public String getH() {
                    return h;
                }

                public void setH(String h) {
                    this.h = h;
                }
            }

            public static class RateBean {
                /**
                 * lose : 29.59%
                 * draw : 38.47%
                 * win : 31.94%
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
                 * oa : 3.12
                 * od : 2.4
                 * oh : 2.89
                 */

                private String oa;
                private String od;
                private String oh;

                public String getOa() {
                    return oa;
                }

                public void setOa(String oa) {
                    this.oa = oa;
                }

                public String getOd() {
                    return od;
                }

                public void setOd(String od) {
                    this.od = od;
                }

                public String getOh() {
                    return oh;
                }

                public void setOh(String oh) {
                    this.oh = oh;
                }
            }
        }
    }
}
