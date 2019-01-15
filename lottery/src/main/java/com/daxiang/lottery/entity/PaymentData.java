package com.daxiang.lottery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author yudonghui
 * @date 2017/5/11
 * @describe May the Buddha bless bug-free!!!
 */
public class PaymentData {

    /**
     * sign : 59c36d1cd2b89c28ea577d6c89a166f5
     * msg : 成功
     * code : 0
     * data : {"items":{"charset":"UTF-8","code_img_url":"https://pay.swiftpass.cn/pay/qrcode?uuid=https://qr.alipay.com/bax05790fkkjxyitb3bh80bc","nonce_str":"dsddadada","orderId":7051020335000063,"sign":"83E5D79EC5DB1E597B2198BA628DBDEA","mch_id":"102510186679","version":"2.0","code_url":"https://qr.alipay.com/bax05790fkkjxyitb3bh80bc","appid":"2016092301958221","result_code":"0","sign_type":"MD5","respCode":"0000","status":"0"},"timeStamp":1494493658904}
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
         * items : {"charset":"UTF-8","code_img_url":"https://pay.swiftpass.cn/pay/qrcode?uuid=https://qr.alipay.com/bax05790fkkjxyitb3bh80bc","nonce_str":"dsddadada","orderId":7051020335000063,"sign":"83E5D79EC5DB1E597B2198BA628DBDEA","mch_id":"102510186679","version":"2.0","code_url":"https://qr.alipay.com/bax05790fkkjxyitb3bh80bc","appid":"2016092301958221","result_code":"0","sign_type":"MD5","respCode":"0000","status":"0"}
         * timeStamp : 1494493658904
         */

        private ItemsBean items;
        private long timeStamp;
        private String uPayReturn;

        public String getuPayReturn() {
            return uPayReturn;
        }

        public void setuPayReturn(String uPayReturn) {
            this.uPayReturn = uPayReturn;
        }

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
             * charset : UTF-8
             * code_img_url : https://pay.swiftpass.cn/pay/qrcode?uuid=https://qr.alipay.com/bax05790fkkjxyitb3bh80bc
             * nonce_str : dsddadada
             * orderId : 7051020335000063
             * sign : 83E5D79EC5DB1E597B2198BA628DBDEA
             * mch_id : 102510186679
             * version : 2.0
             * code_url : https://qr.alipay.com/bax05790fkkjxyitb3bh80bc
             * appid : 2016092301958221
             * result_code : 0
             * sign_type : MD5
             * respCode : 0000
             * status : 0
             * package : Sign=WXPay
             * orderId : 7051020335000062
             * sign : A04370ED584B7B43DB738B8A6EFAED90
             * partnerid : 17586772
             * prepayid : wx201705111705351bc1dbd1220819318458
             * noncestr : 56v1el21ce9dwlfpr91w81tiu78vmcjb
             * respCode : 0000
             * timestamp : 1494493531
             */
            private String charset;
            private String code_img_url;//支付宝支付
            private String nonce_str;
            private String mch_id;
            private String version;
            private String code_url;
            private String appid;
            private String result_code;
            private String sign_type;
            private String respCode;
            private String status;
            @SerializedName("package")
            private String packageX;
            private String orderId;
            private String sign;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private String timestamp;



            private String oid_partner;
            private String risk_item;
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
            private String info_order;

            private String mwebUrl;//打开qq支付的链接

            public String getMwebUrl() {
                return mwebUrl;
            }

            public void setMwebUrl(String mwebUrl) {
                this.mwebUrl = mwebUrl;
            }

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

            public String getInfo_order() {
                return info_order;
            }

            public void setInfo_order(String info_order) {
                this.info_order = info_order;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }
            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }


            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
            public String getCharset() {
                return charset;
            }

            public void setCharset(String charset) {
                this.charset = charset;
            }

            public String getCode_img_url() {
                return code_img_url;
            }

            public void setCode_img_url(String code_img_url) {
                this.code_img_url = code_img_url;
            }

            public String getNonce_str() {
                return nonce_str;
            }

            public void setNonce_str(String nonce_str) {
                this.nonce_str = nonce_str;
            }

            public String  getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getMch_id() {
                return mch_id;
            }

            public void setMch_id(String mch_id) {
                this.mch_id = mch_id;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getCode_url() {
                return code_url;
            }

            public void setCode_url(String code_url) {
                this.code_url = code_url;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getResult_code() {
                return result_code;
            }

            public void setResult_code(String result_code) {
                this.result_code = result_code;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }

            public String getRespCode() {
                return respCode;
            }

            public void setRespCode(String respCode) {
                this.respCode = respCode;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
