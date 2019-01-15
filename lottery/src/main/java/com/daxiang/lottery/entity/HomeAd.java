package com.daxiang.lottery.entity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/12/19
 * @describe May the Buddha bless bug-free!!!
 */
public class HomeAd {

    /**
     * code : 0
     * msg : 获取最新中奖记录成功
     * data : [{"userName":"好心情","prize":"187.50"},{"userName":"海棠依旧","prize":"675.51"},{"userName":"妄为","prize":"106.25"},{"userName":"Sjj宋先森","prize":"212.50"},{"userName":"Sjj宋先森","prize":"850.00"},{"userName":"CX_319666wvp","prize":"2125.00"},{"userName":"CX_875457iue","prize":"850.00"},{"userName":"CX_875457iue","prize":"2125.00"},{"userName":"CX_875457iue","prize":"4250.00"},{"userName":"潇洒哥","prize":"2550.00"},{"userName":"CX_629086jjr","prize":"850.00"},{"userName":"CX_047094nzn","prize":"4250.00"},{"userName":"對酒當歌 發","prize":"170.00"},{"userName":"phinney","prize":"212.50"},{"userName":"小薛薛","prize":"425.00"},{"userName":"wayking","prize":"2550.00"},{"userName":"逢彩必中","prize":"1700.00"},{"userName":"清風","prize":"106.25"},{"userName":"汪小二","prize":"425.00"},{"userName":"历史第一人","prize":"860.80"}]
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
         * userName : 好心情
         * prize : 187.50
         */

        private String userName;
        private String prize;
        private String lotCode;
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPrize() {
            return prize;
        }

        public void setPrize(String prize) {
            this.prize = prize;
        }

        public String getLotCode() {
            return lotCode;
        }

        public void setLotCode(String lotCode) {
            this.lotCode = lotCode;
        }
    }
}
