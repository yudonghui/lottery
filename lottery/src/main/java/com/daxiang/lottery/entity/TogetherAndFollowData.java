package com.daxiang.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class TogetherAndFollowData implements Serializable {

    /**
     * code : 0
     * msg : 获取合买大厅列表成功
     * data : {"totalRecords":3,"totalPages":1,"pageIndex":1,"pageSize":10,"list":[{"orderId":2016120717151800000,"orderCode":"HM20161207184015_5595832","userId":2016092210470301000,"userName":"13162821161","channel":"6000001","declaration":"跟着成功人的脚步走","openType":1,"commissionType":1,"commissionScale":2,"pretaxBonus":null,"aftertaxBonus":null,"commission":null,"joinNum":1,"theoreticalPrize":442,"totalNum":10,"buyedNum":8,"buyRatio":80,"oneMoney":1,"totalMoney":10,"isFull":0,"fullTime":null,"isGuaranteed":1,"guaranteedMoney":1,"guaranteedRatio":10,"sendFlag":0,"lotCode":"42"},{"orderId":2016120717151800000,"orderCode":"HM20161207181841_1916863","userId":2016092210470301000,"userName":"13162821161","channel":"6000001","declaration":"跟着成功人的脚步走","openType":1,"commissionType":1,"commissionScale":10,"pretaxBonus":null,"aftertaxBonus":null,"commission":null,"joinNum":1,"theoreticalPrize":53.55,"totalNum":10,"buyedNum":8,"buyRatio":80,"oneMoney":1,"totalMoney":10,"isFull":1,"fullTime":null,"isGuaranteed":1,"guaranteedMoney":2,"guaranteedRatio":20,"sendFlag":0,"lotCode":"42"},{"orderId":2016120717151800000,"orderCode":"HM20161207172109_2550701","userId":2016092210470301000,"userName":"15136778","channel":"10000000","declaration":"这个人很聪明，就等着中大奖吧~","openType":1,"commissionType":1,"commissionScale":1,"pretaxBonus":null,"aftertaxBonus":null,"commission":null,"joinNum":1,"theoreticalPrize":110.18,"totalNum":30,"buyedNum":2,"buyRatio":6.67,"oneMoney":1,"totalMoney":30,"isFull":0,"fullTime":null,"isGuaranteed":0,"guaranteedMoney":0,"guaranteedRatio":0,"sendFlag":0,"lotCode":"42"}]}
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

    public static class DataBean implements Serializable {
        /**
         * totalRecords : 3
         * totalPages : 1
         * pageIndex : 1
         * pageSize : 10
         * list : [{"orderId":2016120717151800000,"orderCode":"HM20161207184015_5595832","userId":2016092210470301000,"userName":"13162821161","channel":"6000001","declaration":"跟着成功人的脚步走","openType":1,"commissionType":1,"commissionScale":2,"pretaxBonus":null,"aftertaxBonus":null,"commission":null,"joinNum":1,"theoreticalPrize":442,"totalNum":10,"buyedNum":8,"buyRatio":80,"oneMoney":1,"totalMoney":10,"isFull":0,"fullTime":null,"isGuaranteed":1,"guaranteedMoney":1,"guaranteedRatio":10,"sendFlag":0,"lotCode":"42"},{"orderId":2016120717151800000,"orderCode":"HM20161207181841_1916863","userId":2016092210470301000,"userName":"13162821161","channel":"6000001","declaration":"跟着成功人的脚步走","openType":1,"commissionType":1,"commissionScale":10,"pretaxBonus":null,"aftertaxBonus":null,"commission":null,"joinNum":1,"theoreticalPrize":53.55,"totalNum":10,"buyedNum":8,"buyRatio":80,"oneMoney":1,"totalMoney":10,"isFull":1,"fullTime":null,"isGuaranteed":1,"guaranteedMoney":2,"guaranteedRatio":20,"sendFlag":0,"lotCode":"42"},{"orderId":2016120717151800000,"orderCode":"HM20161207172109_2550701","userId":2016092210470301000,"userName":"15136778","channel":"10000000","declaration":"这个人很聪明，就等着中大奖吧~","openType":1,"commissionType":1,"commissionScale":1,"pretaxBonus":null,"aftertaxBonus":null,"commission":null,"joinNum":1,"theoreticalPrize":110.18,"totalNum":30,"buyedNum":2,"buyRatio":6.67,"oneMoney":1,"totalMoney":30,"isFull":0,"fullTime":null,"isGuaranteed":0,"guaranteedMoney":0,"guaranteedRatio":0,"sendFlag":0,"lotCode":"42"}]
         */

        private int totalRecords;
        private int totalPages;
        private int pageIndex;
        private int pageSize;
        private List<ListBean> list;

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean extends RecommendBaseData {

        }
    }
}
