package com.daxiang.lottery.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class OrderFormDetailData {

    /**
     * code : 0
     * msg : 获取方案详情成功
     * data : [{"id":491,"session":"20161207310","leagueId":"1","leagueName":"美国职业篮球联盟","leagueShortCn":"美职篮","homeId":"9","homeTeam":"菲尼克斯太阳","homeShortCn":"太阳","guestId":"24","guestTeam":"印第安那步行者","guestShortCn":"步行者","kickOffTime":1481162400000,"stopSaleTime":1481161920000,"let":"+5.5","bgColor":"#000000","issue":"2016-12-07","score":null,"score1":null,"score2":null,"score3":null,"score4":null,"presetScore":"+215.5","status":0,"checkState":null,"clearState":null,"oh":null,"oa":null,"odds3":"2.60","odds0":"1.31","letOdds3":"1.75","letOdds0":"1.75","homeWin15":"5.35","homeWin610":"6.60","homeWin1115":"12.00","homeWin1620":"28.00","homeWin2125":"62.00","homeWin26":"80.00","guestWin15":"4.10","guestWin610":"3.40","guestWin1115":"5.15","guestWin1620":"8.50","guestWin2125":"15.00","guestWin26":"17.50","largeScore":"1.70","smallScore":"1.81","sfDg":0,"rfsfDg":0,"sfcDg":1,"dxfDg":0,"sfFs":1,"rfsfFs":1,"sfcFs":1,"dxfFs":1,"updateFlag":0,"updateTime":1481237655000},{"id":490,"session":"20161207311","leagueId":"1","leagueName":"美国职业篮球联盟","leagueShortCn":"美职篮","homeId":"7","homeTeam":"洛杉矶快船","homeShortCn":"快船","guestId":"6","guestTeam":"金州勇士","guestShortCn":"勇士","kickOffTime":1481167800000,"stopSaleTime":1481167320000,"let":"+4.5","bgColor":"#000000","issue":"2016-12-07","score":null,"score1":null,"score2":null,"score3":null,"score4":null,"presetScore":"+225.5","status":0,"checkState":null,"clearState":null,"oh":null,"oa":null,"odds3":"2.40","odds0":"1.37","letOdds3":"1.82","letOdds0":"1.69","homeWin15":"5.35","homeWin610":"5.80","homeWin1115":"11.00","homeWin1620":"24.00","homeWin2125":"40.00","homeWin26":"50.00","guestWin15":"4.35","guestWin610":"3.75","guestWin1115":"5.60","guestWin1620":"9.00","guestWin2125":"14.50","guestWin26":"15.00","largeScore":"1.75","smallScore":"1.75","sfDg":0,"rfsfDg":0,"sfcDg":1,"dxfDg":0,"sfFs":1,"rfsfFs":1,"sfcFs":1,"dxfFs":1,"updateFlag":0,"updateTime":1481244255000}]
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

    public static class DataBean extends OrderDetailBase{


    }
}
