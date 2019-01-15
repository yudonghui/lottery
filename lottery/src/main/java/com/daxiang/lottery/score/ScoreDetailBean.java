package com.daxiang.lottery.score;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/9
 * @describe May the Buddha bless bug-free!!!
 */
public class ScoreDetailBean {


    /**
     * sign : e34d5501b8373f51c08fd186fecd0b62
     * msg : 成功
     * code : 0
     * data : {"items":{"guest_name":"莫迪纳摩","league_name":"俄超","rank_h":null,"full_score":"1:2","a_pic":"https://m.api.iuliao.com/teamlogo/pic_1038.jpg","minute":"","date_time":"04-10 00:30","h_pic":"https://m.api.iuliao.com/teamlogo/pic_215.jpg","home_name":"莫陆军","half_score":"0:1","let":-1,"rank_a":null,"event":[{"eventId":5812,"matchId":1075251,"eventTime":"77'","homeEvent":7,"homeDetail":"库查耶夫|施琴尼科夫","awayEvent":null,"awayDetail":null},{"eventId":5811,"matchId":1075251,"eventTime":"67'","homeEvent":4,"homeDetail":"维恩布鲁姆","awayEvent":null,"awayDetail":null},{"eventId":5791,"matchId":1075251,"eventTime":"83'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"卡什耶夫"},{"eventId":5790,"matchId":1075251,"eventTime":"80'","homeEvent":4,"homeDetail":"扎戈耶夫","awayEvent":null,"awayDetail":null},{"eventId":5789,"matchId":1075251,"eventTime":"73'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"特尼科夫|劳施"},{"eventId":5788,"matchId":1075251,"eventTime":"85'","homeEvent":4,"homeDetail":"扎戈耶夫","awayEvent":null,"awayDetail":null},{"eventId":5783,"matchId":1075251,"eventTime":"78'","homeEvent":7,"homeDetail":"库查耶夫|施琴尼科夫","awayEvent":null,"awayDetail":null},{"eventId":5782,"matchId":1075251,"eventTime":"74'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"特尼科夫|劳施"},{"eventId":5781,"matchId":1075251,"eventTime":"72'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"尼古拉|卢森科"},{"eventId":5780,"matchId":1075251,"eventTime":"60'","homeEvent":1,"homeDetail":"查路夫","awayEvent":null,"awayDetail":null},{"eventId":5779,"matchId":1075251,"eventTime":"68'","homeEvent":4,"homeDetail":"维恩布鲁姆","awayEvent":null,"awayDetail":null},{"eventId":5778,"matchId":1075251,"eventTime":"66'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"安顿·索斯宁|特沙拉哥夫"},{"eventId":5777,"matchId":1075251,"eventTime":"61'","homeEvent":1,"homeDetail":"查路夫","awayEvent":null,"awayDetail":null},{"eventId":5776,"matchId":1075251,"eventTime":"58'","homeEvent":7,"homeDetail":"查路夫|穆萨","awayEvent":null,"awayDetail":null},{"eventId":5773,"matchId":1075251,"eventTime":"59'","homeEvent":7,"homeDetail":"查路夫|穆萨","awayEvent":null,"awayDetail":null},{"eventId":5772,"matchId":1075251,"eventTime":"57'","homeEvent":7,"homeDetail":"戈洛温|维蒂尼奥","awayEvent":null,"awayDetail":null},{"eventId":5761,"matchId":1075251,"eventTime":"49'","homeEvent":null,"homeDetail":null,"awayEvent":1,"awayDetail":"卡什耶夫"},{"eventId":5758,"matchId":1075251,"eventTime":"45'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"格里戈里·莫罗佐夫"},{"eventId":5757,"matchId":1075251,"eventTime":"39'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"桑巴·索"},{"eventId":5752,"matchId":1075251,"eventTime":"11'","homeEvent":null,"homeDetail":null,"awayEvent":1,"awayDetail":"卡什耶夫"}],"status":"Played"},"timeStamp":1523337647798}
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
         * items : {"guest_name":"莫迪纳摩","league_name":"俄超","rank_h":null,"full_score":"1:2","a_pic":"https://m.api.iuliao.com/teamlogo/pic_1038.jpg","minute":"","date_time":"04-10 00:30","h_pic":"https://m.api.iuliao.com/teamlogo/pic_215.jpg","home_name":"莫陆军","half_score":"0:1","let":-1,"rank_a":null,"event":[{"eventId":5812,"matchId":1075251,"eventTime":"77'","homeEvent":7,"homeDetail":"库查耶夫|施琴尼科夫","awayEvent":null,"awayDetail":null},{"eventId":5811,"matchId":1075251,"eventTime":"67'","homeEvent":4,"homeDetail":"维恩布鲁姆","awayEvent":null,"awayDetail":null},{"eventId":5791,"matchId":1075251,"eventTime":"83'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"卡什耶夫"},{"eventId":5790,"matchId":1075251,"eventTime":"80'","homeEvent":4,"homeDetail":"扎戈耶夫","awayEvent":null,"awayDetail":null},{"eventId":5789,"matchId":1075251,"eventTime":"73'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"特尼科夫|劳施"},{"eventId":5788,"matchId":1075251,"eventTime":"85'","homeEvent":4,"homeDetail":"扎戈耶夫","awayEvent":null,"awayDetail":null},{"eventId":5783,"matchId":1075251,"eventTime":"78'","homeEvent":7,"homeDetail":"库查耶夫|施琴尼科夫","awayEvent":null,"awayDetail":null},{"eventId":5782,"matchId":1075251,"eventTime":"74'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"特尼科夫|劳施"},{"eventId":5781,"matchId":1075251,"eventTime":"72'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"尼古拉|卢森科"},{"eventId":5780,"matchId":1075251,"eventTime":"60'","homeEvent":1,"homeDetail":"查路夫","awayEvent":null,"awayDetail":null},{"eventId":5779,"matchId":1075251,"eventTime":"68'","homeEvent":4,"homeDetail":"维恩布鲁姆","awayEvent":null,"awayDetail":null},{"eventId":5778,"matchId":1075251,"eventTime":"66'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"安顿·索斯宁|特沙拉哥夫"},{"eventId":5777,"matchId":1075251,"eventTime":"61'","homeEvent":1,"homeDetail":"查路夫","awayEvent":null,"awayDetail":null},{"eventId":5776,"matchId":1075251,"eventTime":"58'","homeEvent":7,"homeDetail":"查路夫|穆萨","awayEvent":null,"awayDetail":null},{"eventId":5773,"matchId":1075251,"eventTime":"59'","homeEvent":7,"homeDetail":"查路夫|穆萨","awayEvent":null,"awayDetail":null},{"eventId":5772,"matchId":1075251,"eventTime":"57'","homeEvent":7,"homeDetail":"戈洛温|维蒂尼奥","awayEvent":null,"awayDetail":null},{"eventId":5761,"matchId":1075251,"eventTime":"49'","homeEvent":null,"homeDetail":null,"awayEvent":1,"awayDetail":"卡什耶夫"},{"eventId":5758,"matchId":1075251,"eventTime":"45'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"格里戈里·莫罗佐夫"},{"eventId":5757,"matchId":1075251,"eventTime":"39'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"桑巴·索"},{"eventId":5752,"matchId":1075251,"eventTime":"11'","homeEvent":null,"homeDetail":null,"awayEvent":1,"awayDetail":"卡什耶夫"}],"status":"Played"}
         * timeStamp : 1523337647798
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
             * guest_name : 莫迪纳摩
             * league_name : 俄超
             * rank_h : null
             * full_score : 1:2
             * a_pic : https://m.api.iuliao.com/teamlogo/pic_1038.jpg
             * minute :
             * date_time : 04-10 00:30
             * h_pic : https://m.api.iuliao.com/teamlogo/pic_215.jpg
             * home_name : 莫陆军
             * half_score : 0:1
             * let : -1
             * rank_a : null
             * event : [{"eventId":5812,"matchId":1075251,"eventTime":"77'","homeEvent":7,"homeDetail":"库查耶夫|施琴尼科夫","awayEvent":null,"awayDetail":null},{"eventId":5811,"matchId":1075251,"eventTime":"67'","homeEvent":4,"homeDetail":"维恩布鲁姆","awayEvent":null,"awayDetail":null},{"eventId":5791,"matchId":1075251,"eventTime":"83'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"卡什耶夫"},{"eventId":5790,"matchId":1075251,"eventTime":"80'","homeEvent":4,"homeDetail":"扎戈耶夫","awayEvent":null,"awayDetail":null},{"eventId":5789,"matchId":1075251,"eventTime":"73'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"特尼科夫|劳施"},{"eventId":5788,"matchId":1075251,"eventTime":"85'","homeEvent":4,"homeDetail":"扎戈耶夫","awayEvent":null,"awayDetail":null},{"eventId":5783,"matchId":1075251,"eventTime":"78'","homeEvent":7,"homeDetail":"库查耶夫|施琴尼科夫","awayEvent":null,"awayDetail":null},{"eventId":5782,"matchId":1075251,"eventTime":"74'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"特尼科夫|劳施"},{"eventId":5781,"matchId":1075251,"eventTime":"72'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"尼古拉|卢森科"},{"eventId":5780,"matchId":1075251,"eventTime":"60'","homeEvent":1,"homeDetail":"查路夫","awayEvent":null,"awayDetail":null},{"eventId":5779,"matchId":1075251,"eventTime":"68'","homeEvent":4,"homeDetail":"维恩布鲁姆","awayEvent":null,"awayDetail":null},{"eventId":5778,"matchId":1075251,"eventTime":"66'","homeEvent":null,"homeDetail":null,"awayEvent":7,"awayDetail":"安顿·索斯宁|特沙拉哥夫"},{"eventId":5777,"matchId":1075251,"eventTime":"61'","homeEvent":1,"homeDetail":"查路夫","awayEvent":null,"awayDetail":null},{"eventId":5776,"matchId":1075251,"eventTime":"58'","homeEvent":7,"homeDetail":"查路夫|穆萨","awayEvent":null,"awayDetail":null},{"eventId":5773,"matchId":1075251,"eventTime":"59'","homeEvent":7,"homeDetail":"查路夫|穆萨","awayEvent":null,"awayDetail":null},{"eventId":5772,"matchId":1075251,"eventTime":"57'","homeEvent":7,"homeDetail":"戈洛温|维蒂尼奥","awayEvent":null,"awayDetail":null},{"eventId":5761,"matchId":1075251,"eventTime":"49'","homeEvent":null,"homeDetail":null,"awayEvent":1,"awayDetail":"卡什耶夫"},{"eventId":5758,"matchId":1075251,"eventTime":"45'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"格里戈里·莫罗佐夫"},{"eventId":5757,"matchId":1075251,"eventTime":"39'","homeEvent":null,"homeDetail":null,"awayEvent":4,"awayDetail":"桑巴·索"},{"eventId":5752,"matchId":1075251,"eventTime":"11'","homeEvent":null,"homeDetail":null,"awayEvent":1,"awayDetail":"卡什耶夫"}]
             * status : Played
             */

            private String guest_name;
            private String league_name;
            private String rank_h;
            private String full_score;
            private String a_pic;
            private String minute;
            private String date_time;
            private String h_pic;
            private String home_name;
            private String half_score;
            private int let;
            private String rank_a;
            private String status;
            private String uMid;
            private List<EventBean> event;

            public String getuMid() {
                return uMid;
            }

            public void setuMid(String uMid) {
                this.uMid = uMid;
            }

            public String getGuest_name() {
                return guest_name;
            }

            public void setGuest_name(String guest_name) {
                this.guest_name = guest_name;
            }

            public String getLeague_name() {
                return league_name;
            }

            public void setLeague_name(String league_name) {
                this.league_name = league_name;
            }

            public String getRank_h() {
                return rank_h;
            }

            public void setRank_h(String rank_h) {
                this.rank_h = rank_h;
            }

            public String getFull_score() {
                return full_score;
            }

            public void setFull_score(String full_score) {
                this.full_score = full_score;
            }

            public String getA_pic() {
                return a_pic;
            }

            public void setA_pic(String a_pic) {
                this.a_pic = a_pic;
            }

            public String getMinute() {
                return minute;
            }

            public void setMinute(String minute) {
                this.minute = minute;
            }

            public String getDate_time() {
                return date_time;
            }

            public void setDate_time(String date_time) {
                this.date_time = date_time;
            }

            public String getH_pic() {
                return h_pic;
            }

            public void setH_pic(String h_pic) {
                this.h_pic = h_pic;
            }

            public String getHome_name() {
                return home_name;
            }

            public void setHome_name(String home_name) {
                this.home_name = home_name;
            }

            public String getHalf_score() {
                return half_score;
            }

            public void setHalf_score(String half_score) {
                this.half_score = half_score;
            }

            public int getLet() {
                return let;
            }

            public void setLet(int let) {
                this.let = let;
            }

            public String getRank_a() {
                return rank_a;
            }

            public void setRank_a(String rank_a) {
                this.rank_a = rank_a;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public List<EventBean> getEvent() {
                return event;
            }

            public void setEvent(List<EventBean> event) {
                this.event = event;
            }

            public static class EventBean {
                /**
                 * eventId : 5812
                 * matchId : 1075251
                 * eventTime : 77'
                 * homeEvent : 7
                 * homeDetail : 库查耶夫|施琴尼科夫
                 * awayEvent : null
                 * awayDetail : null
                 */

                private int eventId;
                private int matchId;
                private String eventTime;
                private String homeEvent;
                private String homeDetail;
                private String awayEvent;
                private String awayDetail;

                public int getEventId() {
                    return eventId;
                }

                public void setEventId(int eventId) {
                    this.eventId = eventId;
                }

                public int getMatchId() {
                    return matchId;
                }

                public void setMatchId(int matchId) {
                    this.matchId = matchId;
                }

                public String getEventTime() {
                    return eventTime;
                }

                public void setEventTime(String eventTime) {
                    this.eventTime = eventTime;
                }

                public String getHomeEvent() {
                    return homeEvent;
                }

                public void setHomeEvent(String homeEvent) {
                    this.homeEvent = homeEvent;
                }

                public String getHomeDetail() {
                    return homeDetail;
                }

                public void setHomeDetail(String homeDetail) {
                    this.homeDetail = homeDetail;
                }

                public String getAwayEvent() {
                    return awayEvent;
                }

                public void setAwayEvent(String awayEvent) {
                    this.awayEvent = awayEvent;
                }

                public String getAwayDetail() {
                    return awayDetail;
                }

                public void setAwayDetail(String awayDetail) {
                    this.awayDetail = awayDetail;
                }
            }
        }
    }
}
