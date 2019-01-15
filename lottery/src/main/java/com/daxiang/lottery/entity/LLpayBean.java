package com.daxiang.lottery.entity;

/**
 * @author yudonghui
 * @date 2017/9/25
 * @describe May the Buddha bless bug-free!!!
 */
public class LLpayBean  {

    /**
     * sign : 7ce509517af3ef4590471ce93fe28120
     * msg : 成功
     * code : 0
     * data : {"llParams":{"oid_partner":"201705121001728461","risk_item":"{\"frms_ware_categroy\":\"1002\",\"user_info_mercht_userno\":\"7081421480900018\",\"user_info_dt_register\":\"20170815100813\"}","sign":"cXhIr6/PtlXoG/LR17ccDpsgaKdZaDIIBVmGz9xffPza/lHWD2Y9FF9Yuh6f7Q5MecjC6dtDF80NJO8BN9/3jcNg0xwEaQx/p7+Zza9RVTW+4jOvZo+ltShZ931HjMxD2LS8eBQr1JBgyQRR/HP0TmHaaoDaWL5zu4gwBEgVnEM=","dt_order":"20170925175033","name_goods":"充值","notify_url":"http://140.206.115.66:16690/pay/servlet/llpPay","busi_partner":"101001","flag_modify":"1","no_order":"7092517264300002","id_no":"420984199407286318","card_no":"6214832135020829","user_id":"7081421480900018","money_order":"0.01","id_type":"0","acct_name":"董琳","sign_type":"RSA","info_order":"http://192.168.1.81:16689/account/notify/llpPay"},"timeStamp":1506333033331}
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
         * llParams : {"oid_partner":"201705121001728461","risk_item":"{\"frms_ware_categroy\":\"1002\",\"user_info_mercht_userno\":\"7081421480900018\",\"user_info_dt_register\":\"20170815100813\"}","sign":"cXhIr6/PtlXoG/LR17ccDpsgaKdZaDIIBVmGz9xffPza/lHWD2Y9FF9Yuh6f7Q5MecjC6dtDF80NJO8BN9/3jcNg0xwEaQx/p7+Zza9RVTW+4jOvZo+ltShZ931HjMxD2LS8eBQr1JBgyQRR/HP0TmHaaoDaWL5zu4gwBEgVnEM=","dt_order":"20170925175033","name_goods":"充值","notify_url":"http://140.206.115.66:16690/pay/servlet/llpPay","busi_partner":"101001","flag_modify":"1","no_order":"7092517264300002","id_no":"420984199407286318","card_no":"6214832135020829","user_id":"7081421480900018","money_order":"0.01","id_type":"0","acct_name":"董琳","sign_type":"RSA","info_order":"http://192.168.1.81:16689/account/notify/llpPay"}
         * timeStamp : 1506333033331
         */

        private LlParamsBean llParams;
        private long timeStamp;

        public LlParamsBean getLlParams() {
            return llParams;
        }

        public void setLlParams(LlParamsBean llParams) {
            this.llParams = llParams;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public static class LlParamsBean {
            /**
             * oid_partner : 201705121001728461
             * risk_item : {"frms_ware_categroy":"1002","user_info_mercht_userno":"7081421480900018","user_info_dt_register":"20170815100813"}
             * sign : cXhIr6/PtlXoG/LR17ccDpsgaKdZaDIIBVmGz9xffPza/lHWD2Y9FF9Yuh6f7Q5MecjC6dtDF80NJO8BN9/3jcNg0xwEaQx/p7+Zza9RVTW+4jOvZo+ltShZ931HjMxD2LS8eBQr1JBgyQRR/HP0TmHaaoDaWL5zu4gwBEgVnEM=
             * dt_order : 20170925175033
             * name_goods : 充值
             * notify_url : http://140.206.115.66:16690/pay/servlet/llpPay
             * busi_partner : 101001
             * flag_modify : 1
             * no_order : 7092517264300002
             * id_no : 420984199407286318
             * card_no : 6214832135020829
             * user_id : 7081421480900018
             * money_order : 0.01
             * id_type : 0
             * acct_name : 董琳
             * sign_type : RSA
             * info_order : http://192.168.1.81:16689/account/notify/llpPay
             */

            private String oid_partner;
            private String risk_item;
            private String sign;
            private String dt_order;
            private String name_goods;
            private String notify_url;
            private String busi_partner;
            private String flag_modify;
            private String no_order;
            private String id_no;
            private String card_no;
            private String user_id;
            private String money_order;
            private String id_type;
            private String acct_name;
            private String sign_type;
            private String info_order;

            public String getOid_partner() {
                return oid_partner;
            }

            public void setOid_partner(String oid_partner) {
                this.oid_partner = oid_partner;
            }

            public String getRisk_item() {
                return risk_item;
            }

            public void setRisk_item(String risk_item) {
                this.risk_item = risk_item;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getDt_order() {
                return dt_order;
            }

            public void setDt_order(String dt_order) {
                this.dt_order = dt_order;
            }

            public String getName_goods() {
                return name_goods;
            }

            public void setName_goods(String name_goods) {
                this.name_goods = name_goods;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getBusi_partner() {
                return busi_partner;
            }

            public void setBusi_partner(String busi_partner) {
                this.busi_partner = busi_partner;
            }

            public String getFlag_modify() {
                return flag_modify;
            }

            public void setFlag_modify(String flag_modify) {
                this.flag_modify = flag_modify;
            }

            public String getNo_order() {
                return no_order;
            }

            public void setNo_order(String no_order) {
                this.no_order = no_order;
            }

            public String getId_no() {
                return id_no;
            }

            public void setId_no(String id_no) {
                this.id_no = id_no;
            }

            public String getCard_no() {
                return card_no;
            }

            public void setCard_no(String card_no) {
                this.card_no = card_no;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getMoney_order() {
                return money_order;
            }

            public void setMoney_order(String money_order) {
                this.money_order = money_order;
            }

            public String getId_type() {
                return id_type;
            }

            public void setId_type(String id_type) {
                this.id_type = id_type;
            }

            public String getAcct_name() {
                return acct_name;
            }

            public void setAcct_name(String acct_name) {
                this.acct_name = acct_name;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }

            public String getInfo_order() {
                return info_order;
            }

            public void setInfo_order(String info_order) {
                this.info_order = info_order;
            }
        }
    }
}
