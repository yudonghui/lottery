package com.daxiang.lottery.entity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/21
 * @describe May the Buddha bless bug-free!!!
 */
public class AwardDetailData {

    /**
     * sign : 10f20a1d10c58d159177d5315abb6c51
     * msg : 成功
     * code : 0
     * data : {"items":{"sale":207995131,"pool":3663920573,"detail":[{"number":4,"amount":10000000,"itemName":"一等奖"},{"number":69,"amount":136834,"itemName":"二等奖"},{"number":643,"amount":5904,"itemName":"三等奖"},{"number":26402,"amount":200,"itemName":"四等奖"},{"number":520791,"amount":10,"itemName":"五等奖"},{"number":5034342,"amount":5,"itemName":"六等奖"},{"number":0,"amount":0,"itemName":"一等奖(追加)"},{"number":199,"amount":3542,"itemName":"三等奖(追加)"},{"number":192348,"amount":5,"itemName":"五等奖(追加)"},{"number":26,"amount":82100,"itemName":"二等奖(追加)"},{"number":9838,"amount":100,"itemName":"四等奖(追加)"}]},"timeStamp":1498034269825}
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
         * items : {"sale":207995131,"pool":3663920573,"detail":[{"number":4,"amount":10000000,"itemName":"一等奖"},{"number":69,"amount":136834,"itemName":"二等奖"},{"number":643,"amount":5904,"itemName":"三等奖"},{"number":26402,"amount":200,"itemName":"四等奖"},{"number":520791,"amount":10,"itemName":"五等奖"},{"number":5034342,"amount":5,"itemName":"六等奖"},{"number":0,"amount":0,"itemName":"一等奖(追加)"},{"number":199,"amount":3542,"itemName":"三等奖(追加)"},{"number":192348,"amount":5,"itemName":"五等奖(追加)"},{"number":26,"amount":82100,"itemName":"二等奖(追加)"},{"number":9838,"amount":100,"itemName":"四等奖(追加)"}]}
         * timeStamp : 1498034269825
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
             * sale : 207995131
             * pool : 3663920573
             * detail : [{"number":4,"amount":10000000,"itemName":"一等奖"},{"number":69,"amount":136834,"itemName":"二等奖"},{"number":643,"amount":5904,"itemName":"三等奖"},{"number":26402,"amount":200,"itemName":"四等奖"},{"number":520791,"amount":10,"itemName":"五等奖"},{"number":5034342,"amount":5,"itemName":"六等奖"},{"number":0,"amount":0,"itemName":"一等奖(追加)"},{"number":199,"amount":3542,"itemName":"三等奖(追加)"},{"number":192348,"amount":5,"itemName":"五等奖(追加)"},{"number":26,"amount":82100,"itemName":"二等奖(追加)"},{"number":9838,"amount":100,"itemName":"四等奖(追加)"}]
             */

            private String sale;
            private String pool;
            private List<DetailBean> detail;

            public String getSale() {
                return sale;
            }

            public void setSale(String sale) {
                this.sale = sale;
            }

            public String getPool() {
                return pool;
            }

            public void setPool(String pool) {
                this.pool = pool;
            }

            public List<DetailBean> getDetail() {
                return detail;
            }

            public void setDetail(List<DetailBean> detail) {
                this.detail = detail;
            }

            public static class DetailBean {
                /**
                 * number : 4
                 * amount : 10000000
                 * itemName : 一等奖
                 */

                private String number;
                private String amount;
                private String itemName;

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }
            }
        }
    }
}
