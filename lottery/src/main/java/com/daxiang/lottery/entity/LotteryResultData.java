package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15 0015.
 */
public class LotteryResultData {

    /**
     * code : 0
     * msg : 根据彩种查询历史开奖记录成功
     * data : {"item":[{"leagueName":"世俱杯","score":"3:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214030","homeTeam":"鹿岛鹿角","guestTeam":"麦国民","let":"1"},{"leagueName":"世俱杯","score":"4:1","halfScore":"3:0","issue":"2016-12-14","session":"20161214029","homeTeam":"全北现代","guestTeam":"马姆洛迪","let":"-1"},{"leagueName":"智利杯","score":"4:0","halfScore":"3:0","issue":"2016-12-14","session":"20161214028","homeTeam":"科洛科洛","guestTeam":"埃弗顿VM","let":"-1"},{"leagueName":"葡萄牙杯","score":"0:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214027","homeTeam":"塞图巴尔","guestTeam":"里斯本","let":"1"},{"leagueName":"法联赛杯","score":"1:1","halfScore":"1:1","issue":"2016-12-14","session":"20161214026","homeTeam":"梅斯","guestTeam":"图卢兹","let":"1"},{"leagueName":"法联赛杯","score":"2:2","halfScore":"1:1","issue":"2016-12-14","session":"20161214025","homeTeam":"里昂","guestTeam":"甘冈","let":"-1"},{"leagueName":"法联赛杯","score":"3:1","halfScore":"1:0","issue":"2016-12-14","session":"20161214024","homeTeam":"巴黎圣曼","guestTeam":"里尔","let":"-1"},{"leagueName":"法联赛杯","score":"0:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214023","homeTeam":"圣埃蒂安","guestTeam":"南锡","let":"-1"},{"leagueName":"法联赛杯","score":"7:0","halfScore":"3:0","issue":"2016-12-14","session":"20161214022","homeTeam":"摩纳哥","guestTeam":"雷恩","let":"-1"},{"leagueName":"葡萄牙杯","score":"1:2","halfScore":"1:1","issue":"2016-12-14","session":"20161214021","homeTeam":"布拉加","guestTeam":"科维良","let":"-1"},{"leagueName":"英超","score":"1:2","halfScore":"0:1","issue":"2016-12-14","session":"20161214020","homeTeam":"水晶宫","guestTeam":"曼联","let":"1"},{"leagueName":"英超","score":"3:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214019","homeTeam":"热刺","guestTeam":"赫尔城","let":"-2"},{"leagueName":"英超","score":"0:0","halfScore":"0:0","issue":"2016-12-14","session":"20161214018","homeTeam":"斯托克城","guestTeam":"南安普敦","let":"1"},{"leagueName":"英超","score":"2:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214017","homeTeam":"曼城","guestTeam":"沃特福德","let":"-2"},{"leagueName":"英超","score":"3:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214016","homeTeam":"西布罗姆","guestTeam":"斯旺西","let":"-1"},{"leagueName":"比利时杯","score":"1:0","halfScore":"0:0","issue":"2016-12-14","session":"20161214015","homeTeam":"奥斯坦德","guestTeam":"根特","let":"1"},{"leagueName":"荷兰杯","score":"5:1","halfScore":"1:0","issue":"2016-12-14","session":"20161214014","homeTeam":"费耶诺德","guestTeam":"海牙","let":"-1"},{"leagueName":"英冠","score":"0:2","halfScore":"0:1","issue":"2016-12-14","session":"20161214013","homeTeam":"维冈","guestTeam":"纽卡斯尔","let":"1"},{"leagueName":"英冠","score":"0:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214012","homeTeam":"女王巡游","guestTeam":"德比郡","let":"1"},{"leagueName":"英冠","score":"1:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214011","homeTeam":"诺丁汉","guestTeam":"普雷斯顿","let":"-1"},{"leagueName":"英超","score":"0:3","halfScore":"0:1","issue":"2016-12-14","session":"20161214010","homeTeam":"米堡","guestTeam":"利物浦","let":"1"},{"leagueName":"英超","score":"1:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214009","homeTeam":"西汉姆联","guestTeam":"伯恩利","let":"-1"},{"leagueName":"英超","score":"0:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214008","homeTeam":"桑德兰","guestTeam":"切尔西","let":"1"},{"leagueName":"比利时杯","score":"1:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214007","homeTeam":"沙勒罗瓦","guestTeam":"亨克","let":"-1"},{"leagueName":"比利时杯","score":"2:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214006","homeTeam":"瓦雷赫姆","guestTeam":"圣图尔登","let":"-1"},{"leagueName":"葡萄牙杯","score":"1:0","halfScore":"0:0","issue":"2016-12-14","session":"20161214005","homeTeam":"科英布拉","guestTeam":"佩纳菲耶","let":"-1"},{"leagueName":"法联赛杯","score":"3:2","halfScore":"2:1","issue":"2016-12-14","session":"20161214004","homeTeam":"波尔多","guestTeam":"尼斯","let":"-1"},{"leagueName":"荷兰杯","score":"1:2","halfScore":"1:0","issue":"2016-12-14","session":"20161214003","homeTeam":"兹沃勒","guestTeam":"乌德勒支","let":"1"},{"leagueName":"葡萄牙杯","score":"2:1","halfScore":"1:1","issue":"2016-12-14","session":"20161214002","homeTeam":"雷克斯欧","guestTeam":"通德拉","let":"1"},{"leagueName":"东南亚锦","score":"2:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214001","homeTeam":"印尼","guestTeam":"泰国","let":"1"},{"leagueName":"法联赛杯","score":"1:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213021","homeTeam":"索肖","guestTeam":"马赛","let":"1"},{"leagueName":"苏超","score":"1:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213020","homeTeam":"凯尔特人","guestTeam":"汉密尔顿","let":"-2"},{"leagueName":"英锦标赛","score":"1:3","halfScore":"1:0","issue":"2016-12-13","session":"20161213018","homeTeam":"沃尔索尔","guestTeam":"奥德汉姆","let":"-1"},{"leagueName":"英足总杯","score":"3:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213017","homeTeam":"牛津联","guestTeam":"麦克尔斯","let":"-1"},{"leagueName":"英足总杯","score":"1:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213016","homeTeam":"米尔顿","guestTeam":"查尔顿","let":"-1"},{"leagueName":"英足总杯","score":"3:2","halfScore":"0:1","issue":"2016-12-13","session":"20161213015","homeTeam":"福利特","guestTeam":"什鲁斯","let":"-1"},{"leagueName":"英冠","score":"1:0","halfScore":"0:0","issue":"2016-12-13","session":"20161213014","homeTeam":"诺维奇","guestTeam":"维拉","let":"-1"},{"leagueName":"英冠","score":"0:1","halfScore":"0:0","issue":"2016-12-13","session":"20161213013","homeTeam":"伯顿","guestTeam":"哈德斯","let":"1"},{"leagueName":"英冠","score":"2:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213012","homeTeam":"谢周三","guestTeam":"巴恩斯利","let":"-1"},{"leagueName":"英冠","score":"2:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213011","homeTeam":"利兹联","guestTeam":"雷丁","let":"-1"},{"leagueName":"英冠","score":"2:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213010","homeTeam":"富勒姆","guestTeam":"罗瑟汉姆","let":"-1"},{"leagueName":"英冠","score":"2:1","halfScore":"0:1","issue":"2016-12-13","session":"20161213009","homeTeam":"加的夫城","guestTeam":"狼队","let":"-1"},{"leagueName":"英冠","score":"0:1","halfScore":"0:0","issue":"2016-12-13","session":"20161213008","homeTeam":"布城","guestTeam":"布伦特","let":"-1"},{"leagueName":"英冠","score":"2:3","halfScore":"0:1","issue":"2016-12-13","session":"20161213007","homeTeam":"布莱克本","guestTeam":"布赖顿","let":"1"},{"leagueName":"英冠","score":"2:1","halfScore":"1:0","issue":"2016-12-13","session":"20161213006","homeTeam":"伯明翰","guestTeam":"伊普斯","let":"-1"},{"leagueName":"英超","score":"2:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213005","homeTeam":"埃弗顿","guestTeam":"阿森纳","let":"1"},{"leagueName":"英超","score":"1:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213004","homeTeam":"伯恩茅斯","guestTeam":"莱切斯特","let":"-1"},{"leagueName":"比利时杯","score":"4:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213003","homeTeam":"欧本","guestTeam":"科特赖克","let":"-1"},{"leagueName":"法联赛杯","score":"3:1","halfScore":"2:1","issue":"2016-12-13","session":"20161213002","homeTeam":"南特","guestTeam":"蒙彼利埃","let":"-1"},{"leagueName":"荷兰杯","score":"4:1","halfScore":"3:1","issue":"2016-12-13","session":"20161213001","homeTeam":"福伦丹","guestTeam":"圣巴夫","let":"-2"}],"totalNu":0}
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
         * item : [{"leagueName":"世俱杯","score":"3:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214030","homeTeam":"鹿岛鹿角","guestTeam":"麦国民","let":"1"},{"leagueName":"世俱杯","score":"4:1","halfScore":"3:0","issue":"2016-12-14","session":"20161214029","homeTeam":"全北现代","guestTeam":"马姆洛迪","let":"-1"},{"leagueName":"智利杯","score":"4:0","halfScore":"3:0","issue":"2016-12-14","session":"20161214028","homeTeam":"科洛科洛","guestTeam":"埃弗顿VM","let":"-1"},{"leagueName":"葡萄牙杯","score":"0:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214027","homeTeam":"塞图巴尔","guestTeam":"里斯本","let":"1"},{"leagueName":"法联赛杯","score":"1:1","halfScore":"1:1","issue":"2016-12-14","session":"20161214026","homeTeam":"梅斯","guestTeam":"图卢兹","let":"1"},{"leagueName":"法联赛杯","score":"2:2","halfScore":"1:1","issue":"2016-12-14","session":"20161214025","homeTeam":"里昂","guestTeam":"甘冈","let":"-1"},{"leagueName":"法联赛杯","score":"3:1","halfScore":"1:0","issue":"2016-12-14","session":"20161214024","homeTeam":"巴黎圣曼","guestTeam":"里尔","let":"-1"},{"leagueName":"法联赛杯","score":"0:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214023","homeTeam":"圣埃蒂安","guestTeam":"南锡","let":"-1"},{"leagueName":"法联赛杯","score":"7:0","halfScore":"3:0","issue":"2016-12-14","session":"20161214022","homeTeam":"摩纳哥","guestTeam":"雷恩","let":"-1"},{"leagueName":"葡萄牙杯","score":"1:2","halfScore":"1:1","issue":"2016-12-14","session":"20161214021","homeTeam":"布拉加","guestTeam":"科维良","let":"-1"},{"leagueName":"英超","score":"1:2","halfScore":"0:1","issue":"2016-12-14","session":"20161214020","homeTeam":"水晶宫","guestTeam":"曼联","let":"1"},{"leagueName":"英超","score":"3:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214019","homeTeam":"热刺","guestTeam":"赫尔城","let":"-2"},{"leagueName":"英超","score":"0:0","halfScore":"0:0","issue":"2016-12-14","session":"20161214018","homeTeam":"斯托克城","guestTeam":"南安普敦","let":"1"},{"leagueName":"英超","score":"2:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214017","homeTeam":"曼城","guestTeam":"沃特福德","let":"-2"},{"leagueName":"英超","score":"3:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214016","homeTeam":"西布罗姆","guestTeam":"斯旺西","let":"-1"},{"leagueName":"比利时杯","score":"1:0","halfScore":"0:0","issue":"2016-12-14","session":"20161214015","homeTeam":"奥斯坦德","guestTeam":"根特","let":"1"},{"leagueName":"荷兰杯","score":"5:1","halfScore":"1:0","issue":"2016-12-14","session":"20161214014","homeTeam":"费耶诺德","guestTeam":"海牙","let":"-1"},{"leagueName":"英冠","score":"0:2","halfScore":"0:1","issue":"2016-12-14","session":"20161214013","homeTeam":"维冈","guestTeam":"纽卡斯尔","let":"1"},{"leagueName":"英冠","score":"0:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214012","homeTeam":"女王巡游","guestTeam":"德比郡","let":"1"},{"leagueName":"英冠","score":"1:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214011","homeTeam":"诺丁汉","guestTeam":"普雷斯顿","let":"-1"},{"leagueName":"英超","score":"0:3","halfScore":"0:1","issue":"2016-12-14","session":"20161214010","homeTeam":"米堡","guestTeam":"利物浦","let":"1"},{"leagueName":"英超","score":"1:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214009","homeTeam":"西汉姆联","guestTeam":"伯恩利","let":"-1"},{"leagueName":"英超","score":"0:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214008","homeTeam":"桑德兰","guestTeam":"切尔西","let":"1"},{"leagueName":"比利时杯","score":"1:1","halfScore":"0:0","issue":"2016-12-14","session":"20161214007","homeTeam":"沙勒罗瓦","guestTeam":"亨克","let":"-1"},{"leagueName":"比利时杯","score":"2:0","halfScore":"1:0","issue":"2016-12-14","session":"20161214006","homeTeam":"瓦雷赫姆","guestTeam":"圣图尔登","let":"-1"},{"leagueName":"葡萄牙杯","score":"1:0","halfScore":"0:0","issue":"2016-12-14","session":"20161214005","homeTeam":"科英布拉","guestTeam":"佩纳菲耶","let":"-1"},{"leagueName":"法联赛杯","score":"3:2","halfScore":"2:1","issue":"2016-12-14","session":"20161214004","homeTeam":"波尔多","guestTeam":"尼斯","let":"-1"},{"leagueName":"荷兰杯","score":"1:2","halfScore":"1:0","issue":"2016-12-14","session":"20161214003","homeTeam":"兹沃勒","guestTeam":"乌德勒支","let":"1"},{"leagueName":"葡萄牙杯","score":"2:1","halfScore":"1:1","issue":"2016-12-14","session":"20161214002","homeTeam":"雷克斯欧","guestTeam":"通德拉","let":"1"},{"leagueName":"东南亚锦","score":"2:1","halfScore":"0:1","issue":"2016-12-14","session":"20161214001","homeTeam":"印尼","guestTeam":"泰国","let":"1"},{"leagueName":"法联赛杯","score":"1:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213021","homeTeam":"索肖","guestTeam":"马赛","let":"1"},{"leagueName":"苏超","score":"1:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213020","homeTeam":"凯尔特人","guestTeam":"汉密尔顿","let":"-2"},{"leagueName":"英锦标赛","score":"1:3","halfScore":"1:0","issue":"2016-12-13","session":"20161213018","homeTeam":"沃尔索尔","guestTeam":"奥德汉姆","let":"-1"},{"leagueName":"英足总杯","score":"3:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213017","homeTeam":"牛津联","guestTeam":"麦克尔斯","let":"-1"},{"leagueName":"英足总杯","score":"1:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213016","homeTeam":"米尔顿","guestTeam":"查尔顿","let":"-1"},{"leagueName":"英足总杯","score":"3:2","halfScore":"0:1","issue":"2016-12-13","session":"20161213015","homeTeam":"福利特","guestTeam":"什鲁斯","let":"-1"},{"leagueName":"英冠","score":"1:0","halfScore":"0:0","issue":"2016-12-13","session":"20161213014","homeTeam":"诺维奇","guestTeam":"维拉","let":"-1"},{"leagueName":"英冠","score":"0:1","halfScore":"0:0","issue":"2016-12-13","session":"20161213013","homeTeam":"伯顿","guestTeam":"哈德斯","let":"1"},{"leagueName":"英冠","score":"2:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213012","homeTeam":"谢周三","guestTeam":"巴恩斯利","let":"-1"},{"leagueName":"英冠","score":"2:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213011","homeTeam":"利兹联","guestTeam":"雷丁","let":"-1"},{"leagueName":"英冠","score":"2:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213010","homeTeam":"富勒姆","guestTeam":"罗瑟汉姆","let":"-1"},{"leagueName":"英冠","score":"2:1","halfScore":"0:1","issue":"2016-12-13","session":"20161213009","homeTeam":"加的夫城","guestTeam":"狼队","let":"-1"},{"leagueName":"英冠","score":"0:1","halfScore":"0:0","issue":"2016-12-13","session":"20161213008","homeTeam":"布城","guestTeam":"布伦特","let":"-1"},{"leagueName":"英冠","score":"2:3","halfScore":"0:1","issue":"2016-12-13","session":"20161213007","homeTeam":"布莱克本","guestTeam":"布赖顿","let":"1"},{"leagueName":"英冠","score":"2:1","halfScore":"1:0","issue":"2016-12-13","session":"20161213006","homeTeam":"伯明翰","guestTeam":"伊普斯","let":"-1"},{"leagueName":"英超","score":"2:1","halfScore":"1:1","issue":"2016-12-13","session":"20161213005","homeTeam":"埃弗顿","guestTeam":"阿森纳","let":"1"},{"leagueName":"英超","score":"1:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213004","homeTeam":"伯恩茅斯","guestTeam":"莱切斯特","let":"-1"},{"leagueName":"比利时杯","score":"4:0","halfScore":"1:0","issue":"2016-12-13","session":"20161213003","homeTeam":"欧本","guestTeam":"科特赖克","let":"-1"},{"leagueName":"法联赛杯","score":"3:1","halfScore":"2:1","issue":"2016-12-13","session":"20161213002","homeTeam":"南特","guestTeam":"蒙彼利埃","let":"-1"},{"leagueName":"荷兰杯","score":"4:1","halfScore":"3:1","issue":"2016-12-13","session":"20161213001","homeTeam":"福伦丹","guestTeam":"圣巴夫","let":"-2"}]
         * totalNu : 0
         */

        private int totalNu;
        private List<ItemBean> item;

        public int getTotalNu() {
            return totalNu;
        }

        public void setTotalNu(int totalNu) {
            this.totalNu = totalNu;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * leagueName : 世俱杯
             * score : 3:0
             * halfScore : 1:0
             * issue : 2016-12-14
             * session : 20161214030
             * homeTeam : 鹿岛鹿角
             * guestTeam : 麦国民
             * let : 1
             */

            private String leagueName;
            private String score;
            private String halfScore;
            private String issue;
            private String session;
            private String homeTeam;
            private String guestTeam;
            private String let;
            private String presetScore;

            public String getPresetScore() {
                return presetScore;
            }

            public void setPresetScore(String presetScore) {
                this.presetScore = presetScore;
            }

            public String getLeagueName() {
                return leagueName;
            }

            public void setLeagueName(String leagueName) {
                this.leagueName = leagueName;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getHalfScore() {
                return halfScore;
            }

            public void setHalfScore(String halfScore) {
                this.halfScore = halfScore;
            }

            public String getIssue() {
                return issue;
            }

            public void setIssue(String issue) {
                this.issue = issue;
            }

            public String getSession() {
                return session;
            }

            public void setSession(String session) {
                this.session = session;
            }

            public String getHomeTeam() {
                return homeTeam;
            }

            public void setHomeTeam(String homeTeam) {
                this.homeTeam = homeTeam;
            }

            public String getGuestTeam() {
                return guestTeam;
            }

            public void setGuestTeam(String guestTeam) {
                this.guestTeam = guestTeam;
            }

            public String getLet() {
                return let;
            }

            public void setLet(String let) {
                this.let = let;
            }
        }
    }
}
