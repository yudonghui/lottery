package com.daxiang.lottery.entity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/9/25
 * @describe May the Buddha bless bug-free!!!
 */
public class BankCardBean {

    /**
     * sign : null
     * msg : 成功
     * code : 0
     * data : {"cardInfo":[{"bank_code":"03080000","card_no":"0829","no_agree":"2017092551817897","bind_mobile":"13162821161","bank_name":"招商银行","card_type":"2"}]}
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
        private List<CardInfoBean> cardInfo;

        public List<CardInfoBean> getCardInfo() {
            return cardInfo;
        }

        public void setCardInfo(List<CardInfoBean> cardInfo) {
            this.cardInfo = cardInfo;
        }

        public static class CardInfoBean {
            /**
             * bank_code : 03080000
             * card_no : 0829
             * no_agree : 2017092551817897
             * bind_mobile : 13162821161
             * bank_name : 招商银行
             * card_type : 2
             */

            private String bank_code;
            private String card_no;
            private String no_agree;
            private String bind_mobile;
            private String bank_name;
            private String card_type;

            public String getBank_code() {
                return bank_code;
            }

            public void setBank_code(String bank_code) {
                this.bank_code = bank_code;
            }

            public String getCard_no() {
                return card_no;
            }

            public void setCard_no(String card_no) {
                this.card_no = card_no;
            }

            public String getNo_agree() {
                return no_agree;
            }

            public void setNo_agree(String no_agree) {
                this.no_agree = no_agree;
            }

            public String getBind_mobile() {
                return bind_mobile;
            }

            public void setBind_mobile(String bind_mobile) {
                this.bind_mobile = bind_mobile;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getCard_type() {
                return card_type;
            }

            public void setCard_type(String card_type) {
                this.card_type = card_type;
            }
        }
    }
}
