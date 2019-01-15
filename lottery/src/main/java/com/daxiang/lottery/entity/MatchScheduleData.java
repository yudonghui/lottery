package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Android on 2018/3/28.
 */

public class MatchScheduleData {


    /**
     * sign : 19b337d01db86d2d2eb61190fcafb224
     * msg : 成功
     * code : 0
     * data : {"items":{"rounds_type":1,"round_list":["第1轮","第2轮","第3轮","第4轮","第5轮","第6轮","第7轮","第8轮","第9轮","第10轮","第11轮","第12轮","第13轮","第14轮","第15轮","第16轮","第17轮","第18轮","第19轮","第20轮","第21轮","第22轮","第23轮","第24轮","第25轮","第26轮","第27轮","第28轮","第29轮","第30轮"],"rounds_total":"30","list":[{"date":"2018-04-28","away_id":84,"score":"VS","away_name":"天津泰达","home_name":"北京人和","match_id":1164780,"home_id":141,"message":0},{"date":"2018-04-28","away_id":1012,"score":"VS","away_name":"江苏苏宁易购","home_name":"广州恒大淘宝","match_id":1164671,"home_id":1011,"message":0},{"date":"2018-04-28","away_id":16392,"score":"VS","away_name":"河北华夏幸福","home_name":"天津权健","match_id":1164779,"home_id":8691,"message":0},{"date":"2018-04-29","away_id":7723,"score":"VS","away_name":"上海上港集团","home_name":"长春亚泰","match_id":1164589,"home_id":1013,"message":0},{"date":"2018-04-29","away_id":13768,"score":"VS","away_name":"大连一方","home_name":"上海绿地申花","match_id":1164581,"home_id":143,"message":0},{"date":"2018-04-29","away_id":994,"score":"VS","away_name":"河南建业","home_name":"山东鲁能泰山","match_id":1164658,"home_id":146,"message":0},{"date":"2018-04-29","away_id":8692,"score":"VS","away_name":"贵州恒丰","home_name":"北京中赫国安","match_id":1164681,"home_id":490,"message":0},{"date":"2018-04-29","away_id":137,"score":"VS","away_name":"广州富力","home_name":"重庆斯威","match_id":1164682,"home_id":4,"message":0}],"rounds":"8"},"timeStamp":1524556269698}
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
         * items : {"rounds_type":1,"round_list":["第1轮","第2轮","第3轮","第4轮","第5轮","第6轮","第7轮","第8轮","第9轮","第10轮","第11轮","第12轮","第13轮","第14轮","第15轮","第16轮","第17轮","第18轮","第19轮","第20轮","第21轮","第22轮","第23轮","第24轮","第25轮","第26轮","第27轮","第28轮","第29轮","第30轮"],"rounds_total":"30","list":[{"date":"2018-04-28","away_id":84,"score":"VS","away_name":"天津泰达","home_name":"北京人和","match_id":1164780,"home_id":141,"message":0},{"date":"2018-04-28","away_id":1012,"score":"VS","away_name":"江苏苏宁易购","home_name":"广州恒大淘宝","match_id":1164671,"home_id":1011,"message":0},{"date":"2018-04-28","away_id":16392,"score":"VS","away_name":"河北华夏幸福","home_name":"天津权健","match_id":1164779,"home_id":8691,"message":0},{"date":"2018-04-29","away_id":7723,"score":"VS","away_name":"上海上港集团","home_name":"长春亚泰","match_id":1164589,"home_id":1013,"message":0},{"date":"2018-04-29","away_id":13768,"score":"VS","away_name":"大连一方","home_name":"上海绿地申花","match_id":1164581,"home_id":143,"message":0},{"date":"2018-04-29","away_id":994,"score":"VS","away_name":"河南建业","home_name":"山东鲁能泰山","match_id":1164658,"home_id":146,"message":0},{"date":"2018-04-29","away_id":8692,"score":"VS","away_name":"贵州恒丰","home_name":"北京中赫国安","match_id":1164681,"home_id":490,"message":0},{"date":"2018-04-29","away_id":137,"score":"VS","away_name":"广州富力","home_name":"重庆斯威","match_id":1164682,"home_id":4,"message":0}],"rounds":"8"}
         * timeStamp : 1524556269698
         */

        private ItemsBean items;
        private String timeStamp;

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public static class ItemsBean {
            /**
             * rounds_type : 1
             * round_list : ["第1轮","第2轮","第3轮","第4轮","第5轮","第6轮","第7轮","第8轮","第9轮","第10轮","第11轮","第12轮","第13轮","第14轮","第15轮","第16轮","第17轮","第18轮","第19轮","第20轮","第21轮","第22轮","第23轮","第24轮","第25轮","第26轮","第27轮","第28轮","第29轮","第30轮"]
             * rounds_total : 30
             * list : [{"date":"2018-04-28","away_id":84,"score":"VS","away_name":"天津泰达","home_name":"北京人和","match_id":1164780,"home_id":141,"message":0},{"date":"2018-04-28","away_id":1012,"score":"VS","away_name":"江苏苏宁易购","home_name":"广州恒大淘宝","match_id":1164671,"home_id":1011,"message":0},{"date":"2018-04-28","away_id":16392,"score":"VS","away_name":"河北华夏幸福","home_name":"天津权健","match_id":1164779,"home_id":8691,"message":0},{"date":"2018-04-29","away_id":7723,"score":"VS","away_name":"上海上港集团","home_name":"长春亚泰","match_id":1164589,"home_id":1013,"message":0},{"date":"2018-04-29","away_id":13768,"score":"VS","away_name":"大连一方","home_name":"上海绿地申花","match_id":1164581,"home_id":143,"message":0},{"date":"2018-04-29","away_id":994,"score":"VS","away_name":"河南建业","home_name":"山东鲁能泰山","match_id":1164658,"home_id":146,"message":0},{"date":"2018-04-29","away_id":8692,"score":"VS","away_name":"贵州恒丰","home_name":"北京中赫国安","match_id":1164681,"home_id":490,"message":0},{"date":"2018-04-29","away_id":137,"score":"VS","away_name":"广州富力","home_name":"重庆斯威","match_id":1164682,"home_id":4,"message":0}]
             * rounds : 8
             */

            private int rounds_type;
            private int rounds_total;
            private int rounds;
            private List<String> round_list;
            private List<ListBean> list;

            public int getRounds_type() {
                return rounds_type;
            }

            public void setRounds_type(int rounds_type) {
                this.rounds_type = rounds_type;
            }

            public int getRounds_total() {
                return rounds_total;
            }

            public void setRounds_total(int rounds_total) {
                this.rounds_total = rounds_total;
            }

            public int getRounds() {
                return rounds;
            }

            public void setRounds(int rounds) {
                this.rounds = rounds;
            }

            public List<String> getRound_list() {
                return round_list;
            }

            public void setRound_list(List<String> round_list) {
                this.round_list = round_list;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * date : 2018-04-28
                 * away_id : 84
                 * score : VS
                 * away_name : 天津泰达
                 * home_name : 北京人和
                 * match_id : 1164780
                 * home_id : 141
                 * message : 0
                 */

                private String date;
                private String away_id;
                private String score;
                private String away_name;
                private String home_name;
                private String match_id;
                private String home_id;
                private String message;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getAway_id() {
                    return away_id;
                }

                public void setAway_id(String away_id) {
                    this.away_id = away_id;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getAway_name() {
                    return away_name;
                }

                public void setAway_name(String away_name) {
                    this.away_name = away_name;
                }

                public String getHome_name() {
                    return home_name;
                }

                public void setHome_name(String home_name) {
                    this.home_name = home_name;
                }

                public String getMatch_id() {
                    return match_id;
                }

                public void setMatch_id(String match_id) {
                    this.match_id = match_id;
                }

                public String getHome_id() {
                    return home_id;
                }

                public void setHome_id(String home_id) {
                    this.home_id = home_id;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }
            }
        }
    }
}
