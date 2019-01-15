package com.daxiang.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class JczqData implements Serializable{

    /**
     * code : 0
     * msg : 获取竞彩销售场次成功
     * data : [{"id":3777,"session":"20161207001","leagueId":"59","leagueName":"东南亚锦标赛","leagueShortCn":"东南亚锦","homeId":"926","homeTeam":"越南","homeShortCn":"越南","guestId":"429","guestTeam":"印度尼西亚","guestShortCn":"印尼","kickOffTime":1481112000000,"stopSaleTime":1481111700000,"let":-1,"bgColor":"#660000","issue":"2016-12-07","fullScore":null,"halfScore":null,"status":0,"checkState":0,"clearState":0,"oh":"1.60","od":"3.73","oa":"4.75","score00":"14.00","score01":"14.00","score02":"24.00","score03":"60.00","score04":"150.0","score05":"500.0","score10":"8.00","score11":"8.00","score12":"12.00","score13":"30.00","score14":"80.00","score15":"300.0","score20":"8.00","score21":"6.50","score22":"12.00","score23":"30.00","score24":"80.00","score25":"300.0","score30":"12.00","score31":"10.50","score32":"17.00","score33":"40.00","score40":"23.00","score41":"20.00","score42":"35.00","score50":"50.00","score51":"40.00","score52":"70.00","score3x":"28.00","score1x":"250.0","score0x":"80.00","halfFull00":"7.25","halfFull01":"13.00","halfFull03":"20.00","halfFull10":"10.00","halfFull11":"6.25","halfFull13":"4.30","halfFull30":"40.00","halfFull31":"13.00","halfFull33":"2.20","goal0":"14.00","goal1":"5.80","goal2":"3.80","goal3":"3.50","goal4":"4.60","goal5":"7.00","goal6":"11.00","goal7":"15.00","odds3":"1.52","odds1":"3.85","odds0":"4.75","letOdds3":"2.64","letOdds1":"3.40","letOdds0":"2.19","spfDg":0,"spfFs":1,"rqspfDg":0,"rqspfFs":1,"bqcDg":1,"bqcFs":1,"jqsDg":1,"jqsFs":1,"bfDg":1,"bfFs":1,"updateFlag":0,"updateTime":1481078405000}]
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

    public static class DataBean implements Serializable{
        /**
         * id : 3777
         * session : 20161207001
         * leagueId : 59
         * leagueName : 东南亚锦标赛
         * leagueShortCn : 东南亚锦
         * homeId : 926
         * homeTeam : 越南
         * homeShortCn : 越南
         * guestId : 429
         * guestTeam : 印度尼西亚
         * guestShortCn : 印尼
         * kickOffTime : 1481112000000
         * stopSaleTime : 1481111700000
         * let : -1
         * bgColor : #660000
         * issue : 2016-12-07
         * fullScore : null
         * halfScore : null
         * status : 0
         * checkState : 0
         * clearState : 0
         * oh : 1.60
         * od : 3.73
         * oa : 4.75
         * score00 : 14.00
         * score01 : 14.00
         * score02 : 24.00
         * score03 : 60.00
         * score04 : 150.0
         * score05 : 500.0
         * score10 : 8.00
         * score11 : 8.00
         * score12 : 12.00
         * score13 : 30.00
         * score14 : 80.00
         * score15 : 300.0
         * score20 : 8.00
         * score21 : 6.50
         * score22 : 12.00
         * score23 : 30.00
         * score24 : 80.00
         * score25 : 300.0
         * score30 : 12.00
         * score31 : 10.50
         * score32 : 17.00
         * score33 : 40.00
         * score40 : 23.00
         * score41 : 20.00
         * score42 : 35.00
         * score50 : 50.00
         * score51 : 40.00
         * score52 : 70.00
         * score3x : 28.00
         * score1x : 250.0
         * score0x : 80.00
         * halfFull00 : 7.25
         * halfFull01 : 13.00
         * halfFull03 : 20.00
         * halfFull10 : 10.00
         * halfFull11 : 6.25
         * halfFull13 : 4.30
         * halfFull30 : 40.00
         * halfFull31 : 13.00
         * halfFull33 : 2.20
         * goal0 : 14.00
         * goal1 : 5.80
         * goal2 : 3.80
         * goal3 : 3.50
         * goal4 : 4.60
         * goal5 : 7.00
         * goal6 : 11.00
         * goal7 : 15.00
         * odds3 : 1.52
         * odds1 : 3.85
         * odds0 : 4.75
         * letOdds3 : 2.64
         * letOdds1 : 3.40
         * letOdds0 : 2.19
         * spfDg : 0
         * spfFs : 1
         * rqspfDg : 0
         * rqspfFs : 1
         * bqcDg : 1
         * bqcFs : 1
         * jqsDg : 1
         * jqsFs : 1
         * bfDg : 1
         * bfFs : 1
         * updateFlag : 0
         * updateTime : 1481078405000
         */

       private String uMid;
        private String away;//客队名称（精彩预测）
        private String home;//主队名称（精彩预测）
        private String content;//为赛事推荐投注项
        private long forecastId;
        private String score;
        private String lotCode;
        private String rate;//rate为预测概率
        private String matchId;//场次id

        private String id;
        private String session;
        private String leagueId;
        private String leagueName;
        private String leagueShortCn;
        private String homeId;
        private String homeTeam;
        private String homeShortCn;
        private String guestId;
        private String guestTeam;
        private String guestShortCn;
        private String kickOffTime;
        private String stopSaleTime;
        private int let;
        private String bgColor;
        private String issue;
        private String fullScore;
        private String halfScore;
        private int status;
        private String checkStatus;
        private String clearStatus;
        private String oh;
        private String od;
        private String oa;
        private String score00;
        private String score01;
        private String score02;
        private String score03;
        private String score04;
        private String score05;
        private String score10;
        private String score11;
        private String score12;
        private String score13;
        private String score14;
        private String score15;
        private String score20;
        private String score21;
        private String score22;
        private String score23;
        private String score24;
        private String score25;
        private String score30;
        private String score31;
        private String score32;
        private String score33;
        private String score40;
        private String score41;
        private String score42;
        private String score50;
        private String score51;
        private String score52;
        private String score3x;
        private String score1x;
        private String score0x;
        private String halfFull00;
        private String halfFull01;
        private String halfFull03;
        private String halfFull10;
        private String halfFull11;
        private String halfFull13;
        private String halfFull30;
        private String halfFull31;
        private String halfFull33;
        private String goal0;
        private String goal1;
        private String goal2;
        private String goal3;
        private String goal4;
        private String goal5;
        private String goal6;
        private String goal7;
        private String odds3;
        private String odds1;
        private String odds0;
        private String letOdds3;
        private String letOdds1;
        private String letOdds0;
        private int spfDg;
        private int spfFs;
        private int rqspfDg;
        private int rqspfFs;
        private int bqcDg;
        private int bqcFs;
        private int jqsDg;
        private int jqsFs;
        private int bfDg;
        private int bfFs;
        private int updateFlag;
        private String updateTime;
        private String rankH;//主队排名
        private String rankA;//客队排名
        private String homeLogo;
        private String awayLogo;

        public String getHomeLogo() {
            return homeLogo;
        }

        public void setHomeLogo(String homeLogo) {
            this.homeLogo = homeLogo;
        }

        public String getAwayLogo() {
            return awayLogo;
        }

        public void setAwayLogo(String awayLogo) {
            this.awayLogo = awayLogo;
        }

        public String getuMid() {
            return uMid;
        }

        public void setuMid(String uMid) {
            this.uMid = uMid;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getAway() {
            return away;
        }

        public void setAway(String away) {
            this.away = away;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getForecastId() {
            return forecastId;
        }

        public void setForecastId(long forecastId) {
            this.forecastId = forecastId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLotCode() {
            return lotCode;
        }

        public void setLotCode(String lotCode) {
            this.lotCode = lotCode;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getRankH() {
            return rankH;
        }

        public void setRankH(String rankH) {
            this.rankH = rankH;
        }

        public String getRankA() {
            return rankA;
        }

        public void setRankA(String rankA) {
            this.rankA = rankA;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public String getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(String leagueId) {
            this.leagueId = leagueId;
        }

        public String getLeagueName() {
            return leagueName;
        }

        public void setLeagueName(String leagueName) {
            this.leagueName = leagueName;
        }

        public String getLeagueShortCn() {
            return leagueShortCn;
        }

        public void setLeagueShortCn(String leagueShortCn) {
            this.leagueShortCn = leagueShortCn;
        }

        public String getHomeId() {
            return homeId;
        }

        public void setHomeId(String homeId) {
            this.homeId = homeId;
        }

        public String getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(String homeTeam) {
            this.homeTeam = homeTeam;
        }

        public String getHomeShortCn() {
            return homeShortCn;
        }

        public void setHomeShortCn(String homeShortCn) {
            this.homeShortCn = homeShortCn;
        }

        public String getGuestId() {
            return guestId;
        }

        public void setGuestId(String guestId) {
            this.guestId = guestId;
        }

        public String getGuestTeam() {
            return guestTeam;
        }

        public void setGuestTeam(String guestTeam) {
            this.guestTeam = guestTeam;
        }

        public String getGuestShortCn() {
            return guestShortCn;
        }

        public void setGuestShortCn(String guestShortCn) {
            this.guestShortCn = guestShortCn;
        }

        public String getKickOffTime() {
            return kickOffTime;
        }

        public void setKickOffTime(String kickOffTime) {
            this.kickOffTime = kickOffTime;
        }

        public String getStopSaleTime() {
            return stopSaleTime;
        }

        public void setStopSaleTime(String stopSaleTime) {
            this.stopSaleTime = stopSaleTime;
        }

        public int getLet() {
            return let;
        }

        public void setLet(int let) {
            this.let = let;
        }

        public String getBgColor() {
            return bgColor;
        }

        public void setBgColor(String bgColor) {
            this.bgColor = bgColor;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getFullScore() {
            return fullScore;
        }

        public void setFullScore(String fullScore) {
            this.fullScore = fullScore;
        }

        public String getHalfScore() {
            return halfScore;
        }

        public void setHalfScore(String halfScore) {
            this.halfScore = halfScore;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(String checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getClearStatus() {
            return clearStatus;
        }

        public void setClearStatus(String clearStatus) {
            this.clearStatus = clearStatus;
        }

        public String getOh() {
            return oh;
        }

        public void setOh(String oh) {
            this.oh = oh;
        }

        public String getOd() {
            return od;
        }

        public void setOd(String od) {
            this.od = od;
        }

        public String getOa() {
            return oa;
        }

        public void setOa(String oa) {
            this.oa = oa;
        }

        public String getScore00() {
            return score00;
        }

        public void setScore00(String score00) {
            this.score00 = score00;
        }

        public String getScore01() {
            return score01;
        }

        public void setScore01(String score01) {
            this.score01 = score01;
        }

        public String getScore02() {
            return score02;
        }

        public void setScore02(String score02) {
            this.score02 = score02;
        }

        public String getScore03() {
            return score03;
        }

        public void setScore03(String score03) {
            this.score03 = score03;
        }

        public String getScore04() {
            return score04;
        }

        public void setScore04(String score04) {
            this.score04 = score04;
        }

        public String getScore05() {
            return score05;
        }

        public void setScore05(String score05) {
            this.score05 = score05;
        }

        public String getScore10() {
            return score10;
        }

        public void setScore10(String score10) {
            this.score10 = score10;
        }

        public String getScore11() {
            return score11;
        }

        public void setScore11(String score11) {
            this.score11 = score11;
        }

        public String getScore12() {
            return score12;
        }

        public void setScore12(String score12) {
            this.score12 = score12;
        }

        public String getScore13() {
            return score13;
        }

        public void setScore13(String score13) {
            this.score13 = score13;
        }

        public String getScore14() {
            return score14;
        }

        public void setScore14(String score14) {
            this.score14 = score14;
        }

        public String getScore15() {
            return score15;
        }

        public void setScore15(String score15) {
            this.score15 = score15;
        }

        public String getScore20() {
            return score20;
        }

        public void setScore20(String score20) {
            this.score20 = score20;
        }

        public String getScore21() {
            return score21;
        }

        public void setScore21(String score21) {
            this.score21 = score21;
        }

        public String getScore22() {
            return score22;
        }

        public void setScore22(String score22) {
            this.score22 = score22;
        }

        public String getScore23() {
            return score23;
        }

        public void setScore23(String score23) {
            this.score23 = score23;
        }

        public String getScore24() {
            return score24;
        }

        public void setScore24(String score24) {
            this.score24 = score24;
        }

        public String getScore25() {
            return score25;
        }

        public void setScore25(String score25) {
            this.score25 = score25;
        }

        public String getScore30() {
            return score30;
        }

        public void setScore30(String score30) {
            this.score30 = score30;
        }

        public String getScore31() {
            return score31;
        }

        public void setScore31(String score31) {
            this.score31 = score31;
        }

        public String getScore32() {
            return score32;
        }

        public void setScore32(String score32) {
            this.score32 = score32;
        }

        public String getScore33() {
            return score33;
        }

        public void setScore33(String score33) {
            this.score33 = score33;
        }

        public String getScore40() {
            return score40;
        }

        public void setScore40(String score40) {
            this.score40 = score40;
        }

        public String getScore41() {
            return score41;
        }

        public void setScore41(String score41) {
            this.score41 = score41;
        }

        public String getScore42() {
            return score42;
        }

        public void setScore42(String score42) {
            this.score42 = score42;
        }

        public String getScore50() {
            return score50;
        }

        public void setScore50(String score50) {
            this.score50 = score50;
        }

        public String getScore51() {
            return score51;
        }

        public void setScore51(String score51) {
            this.score51 = score51;
        }

        public String getScore52() {
            return score52;
        }

        public void setScore52(String score52) {
            this.score52 = score52;
        }

        public String getScore3x() {
            return score3x;
        }

        public void setScore3x(String score3x) {
            this.score3x = score3x;
        }

        public String getScore1x() {
            return score1x;
        }

        public void setScore1x(String score1x) {
            this.score1x = score1x;
        }

        public String getScore0x() {
            return score0x;
        }

        public void setScore0x(String score0x) {
            this.score0x = score0x;
        }

        public String getHalfFull00() {
            return halfFull00;
        }

        public void setHalfFull00(String halfFull00) {
            this.halfFull00 = halfFull00;
        }

        public String getHalfFull01() {
            return halfFull01;
        }

        public void setHalfFull01(String halfFull01) {
            this.halfFull01 = halfFull01;
        }

        public String getHalfFull03() {
            return halfFull03;
        }

        public void setHalfFull03(String halfFull03) {
            this.halfFull03 = halfFull03;
        }

        public String getHalfFull10() {
            return halfFull10;
        }

        public void setHalfFull10(String halfFull10) {
            this.halfFull10 = halfFull10;
        }

        public String getHalfFull11() {
            return halfFull11;
        }

        public void setHalfFull11(String halfFull11) {
            this.halfFull11 = halfFull11;
        }

        public String getHalfFull13() {
            return halfFull13;
        }

        public void setHalfFull13(String halfFull13) {
            this.halfFull13 = halfFull13;
        }

        public String getHalfFull30() {
            return halfFull30;
        }

        public void setHalfFull30(String halfFull30) {
            this.halfFull30 = halfFull30;
        }

        public String getHalfFull31() {
            return halfFull31;
        }

        public void setHalfFull31(String halfFull31) {
            this.halfFull31 = halfFull31;
        }

        public String getHalfFull33() {
            return halfFull33;
        }

        public void setHalfFull33(String halfFull33) {
            this.halfFull33 = halfFull33;
        }

        public String getGoal0() {
            return goal0;
        }

        public void setGoal0(String goal0) {
            this.goal0 = goal0;
        }

        public String getGoal1() {
            return goal1;
        }

        public void setGoal1(String goal1) {
            this.goal1 = goal1;
        }

        public String getGoal2() {
            return goal2;
        }

        public void setGoal2(String goal2) {
            this.goal2 = goal2;
        }

        public String getGoal3() {
            return goal3;
        }

        public void setGoal3(String goal3) {
            this.goal3 = goal3;
        }

        public String getGoal4() {
            return goal4;
        }

        public void setGoal4(String goal4) {
            this.goal4 = goal4;
        }

        public String getGoal5() {
            return goal5;
        }

        public void setGoal5(String goal5) {
            this.goal5 = goal5;
        }

        public String getGoal6() {
            return goal6;
        }

        public void setGoal6(String goal6) {
            this.goal6 = goal6;
        }

        public String getGoal7() {
            return goal7;
        }

        public void setGoal7(String goal7) {
            this.goal7 = goal7;
        }

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

        public String getLetOdds3() {
            return letOdds3;
        }

        public void setLetOdds3(String letOdds3) {
            this.letOdds3 = letOdds3;
        }

        public String getLetOdds1() {
            return letOdds1;
        }

        public void setLetOdds1(String letOdds1) {
            this.letOdds1 = letOdds1;
        }

        public String getLetOdds0() {
            return letOdds0;
        }

        public void setLetOdds0(String letOdds0) {
            this.letOdds0 = letOdds0;
        }

        public int getSpfDg() {
            return spfDg;
        }

        public void setSpfDg(int spfDg) {
            this.spfDg = spfDg;
        }

        public int getSpfFs() {
            return spfFs;
        }

        public void setSpfFs(int spfFs) {
            this.spfFs = spfFs;
        }

        public int getRqspfDg() {
            return rqspfDg;
        }

        public void setRqspfDg(int rqspfDg) {
            this.rqspfDg = rqspfDg;
        }

        public int getRqspfFs() {
            return rqspfFs;
        }

        public void setRqspfFs(int rqspfFs) {
            this.rqspfFs = rqspfFs;
        }

        public int getBqcDg() {
            return bqcDg;
        }

        public void setBqcDg(int bqcDg) {
            this.bqcDg = bqcDg;
        }

        public int getBqcFs() {
            return bqcFs;
        }

        public void setBqcFs(int bqcFs) {
            this.bqcFs = bqcFs;
        }

        public int getJqsDg() {
            return jqsDg;
        }

        public void setJqsDg(int jqsDg) {
            this.jqsDg = jqsDg;
        }

        public int getJqsFs() {
            return jqsFs;
        }

        public void setJqsFs(int jqsFs) {
            this.jqsFs = jqsFs;
        }

        public int getBfDg() {
            return bfDg;
        }

        public void setBfDg(int bfDg) {
            this.bfDg = bfDg;
        }

        public int getBfFs() {
            return bfFs;
        }

        public void setBfFs(int bfFs) {
            this.bfFs = bfFs;
        }

        public int getUpdateFlag() {
            return updateFlag;
        }

        public void setUpdateFlag(int updateFlag) {
            this.updateFlag = updateFlag;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
