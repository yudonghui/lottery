package com.daxiang.lottery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class Test {

    /**
     * 1 : 中国银行
     * 2 : 中国农业银行
     * 3 : 中国工商银行
     * 4 : 中国建设银行
     * 5 : 中国邮政储蓄银行
     * 6 : 交通银行
     * 7 : 中国光大银行
     * 8 : 中国民生银行
     * 9 : 中信银行
     * 10 : 招商银行
     * 11 : 广发银行
     * 12 : 恒丰银行
     * 13 : 华夏银行
     * 14 : 平安银行
     * 15 : 上海浦东发展银行
     * 16 : 兴业银行
     * 17 : 浙商银行
     * 18 : 渤海银行
     * 19 : 自贡市商业银行
     * 20 : 驻马店银行
     * 21 : 周口银行
     * 22 : 重庆银行
     * 23 : 重庆三峡银行
     * 24 : 重庆农村商业银行
     * 25 : 中山小榄村镇银行
     * 26 : 郑州银行
     * 27 : 浙江泰隆商业银行
     * 28 : 浙江农信
     * 29 : 浙江民泰商业银行
     * 30 : 浙江稠州商业银行
     * 31 : 长沙银行
     * 32 : 张家口市商业银行
     * 33 : 张家港农村商业银行
     * 34 : 玉溪市商业银行
     * 35 : 营口银行
     * 36 : 鄞州银行
     * 37 : 宜宾市商业银行
     * 38 : 尧都农商银行
     * 39 : 阳泉市商业银行
     * 40 : 许昌银行
     * 41 : 邢台银行
     * 42 : 信阳银行
     * 43 : 新乡银行
     * 44 : 西安银行
     * 45 : 武汉农村商业银行
     * 46 : 吴江农村商业银行
     * 47 : 乌鲁木齐市商业银行
     * 48 : 温州银行
     * 49 : 潍坊银行
     * 50 : 威海市商业银行
     * 51 : 天津银行
     * 52 : 天津农商银行
     * 53 : 泰安市商业银行
     * 54 : 太仓农村商业银行
     * 55 : 台州银行
     * 56 : 苏州银行
     * 57 : 顺德农商银行
     * 58 : 盛京银行
     * 59 : 深圳农村商业银行
     * 60 : 绍兴银行
     * 61 : 上饶银行
     * 62 : 上海银行
     * 63 : 上海农商银行
     * 64 : 陕西信合
     * 65 : 山东省农村信用社
     * 66 : 三门峡银行
     * 67 : 青海银行
     * 68 : 青岛银行
     * 69 : 齐商银行
     * 70 : 齐鲁银行
     * 71 : 平顶山银行
     * 72 : 宁夏银行
     * 73 : 宁波银行
     * 74 : 内蒙古银行
     * 75 : 南京银行
     * 76 : 南海农商银行
     * 77 : 南充市商业银行
     * 78 : 南昌银行
     * 79 : 龙江银行
     * 80 : 临商银行
     * 81 : 辽阳银行
     * 82 : 乐山市商业银行
     * 83 : 廊坊银行
     * 84 : 兰州银行
     * 85 : 莱商银行
     * 86 : 昆山农村商业银行
     * 87 : 昆仑银行
     * 88 : 库尔勒市商业银行
     * 89 : 开封市商业银行
     * 90 : 九江银行
     * 91 : 晋中银行
     * 92 : 晋商银行
     * 93 : 晋城银行
     * 94 : 锦州银行
     * 95 : 金华银行
     * 96 : 江苏银行
     * 97 : 江苏省农村信用社联合社
     * 98 : 江苏江阴农村商业银行
     * 99 : 江南农村商业银行
     * 100 : 嘉兴银行
     * 101 : 济宁银行
     * 102 : 吉林银行
     * 103 : 吉林农村信用社
     * 104 : 徽商银行
     * 105 : 黄河农村商业银行
     * 106 : 华融湘江银行
     * 107 : 湖州银行
     * 108 : 湖南省农村信用社
     * 109 : 湖北银行
     * 110 : 湖北省农村信用社
     * 111 : 衡水银行
     * 112 : 河南省农村信用社
     * 113 : 河北银行
     * 114 : 杭州银行
     * 115 : 汉口银行
     * 116 : 韩亚银行
     * 117 : 邯郸银行
     * 118 : 国家开发银行
     * 119 : 桂林银行
     * 120 : 贵州银行
     * 121 : 贵阳银行
     * 122 : 广州银行
     * 123 : 广州农商银行
     * 124 : 广西北部湾银行
     * 125 : 广东农信
     * 126 : 赣州银行
     * 127 : 甘肃省农村信用社
     * 128 : 富滇银行
     * 129 : 阜新银行
     * 130 : 抚顺银行
     * 131 : 福州海峡银行
     * 132 : 鄂尔多斯银行
     * 133 : 东营银行
     * 134 : 东亚银行
     * 135 : 东莞银行
     * 136 : 东莞农村商业银行
     * 137 : 德州银行
     * 138 : 德阳银行
     * 139 : 丹东银行
     * 140 : 大连银行
     * 141 : 承德银行
     * 142 : 成都银行
     * 143 : 成都农商银行
     * 144 : 朝阳银行
     * 145 : 常熟农商银行
     * 146 : 北京银行
     * 147 : 北京农商银行
     * 148 : 包商银行
     * 149 : 鞍山银行
     * 150 : 安阳银行
     * 151 : 安徽省农村信用社
     * 152 : 石嘴山银行
     */

    private DataBean data;
    /**
     * data : {"1":"中国银行","2":"中国农业银行","3":"中国工商银行","4":"中国建设银行","5":"中国邮政储蓄银行","6":"交通银行","7":"中国光大银行","8":"中国民生银行","9":"中信银行","10":"招商银行","11":"广发银行","12":"恒丰银行","13":"华夏银行","14":"平安银行","15":"上海浦东发展银行","16":"兴业银行","17":"浙商银行","18":"渤海银行","19":"自贡市商业银行","20":"驻马店银行","21":"周口银行","22":"重庆银行","23":"重庆三峡银行","24":"重庆农村商业银行","25":"中山小榄村镇银行","26":"郑州银行","27":"浙江泰隆商业银行","28":"浙江农信","29":"浙江民泰商业银行","30":"浙江稠州商业银行","31":"长沙银行","32":"张家口市商业银行","33":"张家港农村商业银行","34":"玉溪市商业银行","35":"营口银行","36":"鄞州银行","37":"宜宾市商业银行","38":"尧都农商银行","39":"阳泉市商业银行","40":"许昌银行","41":"邢台银行","42":"信阳银行","43":"新乡银行","44":"西安银行","45":"武汉农村商业银行","46":"吴江农村商业银行","47":"乌鲁木齐市商业银行","48":"温州银行","49":"潍坊银行","50":"威海市商业银行","51":"天津银行","52":"天津农商银行","53":"泰安市商业银行","54":"太仓农村商业银行","55":"台州银行","56":"苏州银行","57":"顺德农商银行","58":"盛京银行","59":"深圳农村商业银行","60":"绍兴银行","61":"上饶银行","62":"上海银行","63":"上海农商银行","64":"陕西信合","65":"山东省农村信用社","66":"三门峡银行","67":"青海银行","68":"青岛银行","69":"齐商银行","70":"齐鲁银行","71":"平顶山银行","72":"宁夏银行","73":"宁波银行","74":"内蒙古银行","75":"南京银行","76":"南海农商银行","77":"南充市商业银行","78":"南昌银行","79":"龙江银行","80":"临商银行","81":"辽阳银行","82":"乐山市商业银行","83":"廊坊银行","84":"兰州银行","85":"莱商银行","86":"昆山农村商业银行","87":"昆仑银行","88":"库尔勒市商业银行","89":"开封市商业银行","90":"九江银行","91":"晋中银行","92":"晋商银行","93":"晋城银行","94":"锦州银行","95":"金华银行","96":"江苏银行","97":"江苏省农村信用社联合社","98":"江苏江阴农村商业银行","99":"江南农村商业银行","100":"嘉兴银行","101":"济宁银行","102":"吉林银行","103":"吉林农村信用社","104":"徽商银行","105":"黄河农村商业银行","106":"华融湘江银行","107":"湖州银行","108":"湖南省农村信用社","109":"湖北银行","110":"湖北省农村信用社","111":"衡水银行","112":"河南省农村信用社","113":"河北银行","114":"杭州银行","115":"汉口银行","116":"韩亚银行","117":"邯郸银行","118":"国家开发银行","119":"桂林银行","120":"贵州银行","121":"贵阳银行","122":"广州银行","123":"广州农商银行","124":"广西北部湾银行","125":"广东农信","126":"赣州银行","127":"甘肃省农村信用社","128":"富滇银行","129":"阜新银行","130":"抚顺银行","131":"福州海峡银行","132":"鄂尔多斯银行","133":"东营银行","134":"东亚银行","135":"东莞银行","136":"东莞农村商业银行","137":"德州银行","138":"德阳银行","139":"丹东银行","140":"大连银行","141":"承德银行","142":"成都银行","143":"成都农商银行","144":"朝阳银行","145":"常熟农商银行","146":"北京银行","147":"北京农商银行","148":"包商银行","149":"鞍山银行","150":"安阳银行","151":"安徽省农村信用社","152":"石嘴山银行"}
     * msg :
     * code : 0
     */

    private String msg;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        @SerializedName("1")
        private String value1;
        @SerializedName("2")
        private String value2;
        @SerializedName("3")
        private String value3;
        @SerializedName("4")
        private String value4;
        @SerializedName("5")
        private String value5;
        @SerializedName("6")
        private String value6;
        @SerializedName("7")
        private String value7;
        @SerializedName("8")
        private String value8;
        @SerializedName("9")
        private String value9;
        @SerializedName("10")
        private String value10;
        @SerializedName("11")
        private String value11;
        @SerializedName("12")
        private String value12;
        @SerializedName("13")
        private String value13;
        @SerializedName("14")
        private String value14;
        @SerializedName("15")
        private String value15;
        @SerializedName("16")
        private String value16;
        @SerializedName("17")
        private String value17;
        @SerializedName("18")
        private String value18;
        @SerializedName("19")
        private String value19;
        @SerializedName("20")
        private String value20;
        @SerializedName("21")
        private String value21;
        @SerializedName("22")
        private String value22;
        @SerializedName("23")
        private String value23;
        @SerializedName("24")
        private String value24;
        @SerializedName("25")
        private String value25;
        @SerializedName("26")
        private String value26;
        @SerializedName("27")
        private String value27;
        @SerializedName("28")
        private String value28;
        @SerializedName("29")
        private String value29;
        @SerializedName("30")
        private String value30;
        @SerializedName("31")
        private String value31;
        @SerializedName("32")
        private String value32;
        @SerializedName("33")
        private String value33;
        @SerializedName("34")
        private String value34;
        @SerializedName("35")
        private String value35;
        @SerializedName("36")
        private String value36;
        @SerializedName("37")
        private String value37;
        @SerializedName("38")
        private String value38;
        @SerializedName("39")
        private String value39;
        @SerializedName("40")
        private String value40;
        @SerializedName("41")
        private String value41;
        @SerializedName("42")
        private String value42;
        @SerializedName("43")
        private String value43;
        @SerializedName("44")
        private String value44;
        @SerializedName("45")
        private String value45;
        @SerializedName("46")
        private String value46;
        @SerializedName("47")
        private String value47;
        @SerializedName("48")
        private String value48;
        @SerializedName("49")
        private String value49;
        @SerializedName("50")
        private String value50;
        @SerializedName("51")
        private String value51;
        @SerializedName("52")
        private String value52;
        @SerializedName("53")
        private String value53;
        @SerializedName("54")
        private String value54;
        @SerializedName("55")
        private String value55;
        @SerializedName("56")
        private String value56;
        @SerializedName("57")
        private String value57;
        @SerializedName("58")
        private String value58;
        @SerializedName("59")
        private String value59;
        @SerializedName("60")
        private String value60;
        @SerializedName("61")
        private String value61;
        @SerializedName("62")
        private String value62;
        @SerializedName("63")
        private String value63;
        @SerializedName("64")
        private String value64;
        @SerializedName("65")
        private String value65;
        @SerializedName("66")
        private String value66;
        @SerializedName("67")
        private String value67;
        @SerializedName("68")
        private String value68;
        @SerializedName("69")
        private String value69;
        @SerializedName("70")
        private String value70;
        @SerializedName("71")
        private String value71;
        @SerializedName("72")
        private String value72;
        @SerializedName("73")
        private String value73;
        @SerializedName("74")
        private String value74;
        @SerializedName("75")
        private String value75;
        @SerializedName("76")
        private String value76;
        @SerializedName("77")
        private String value77;
        @SerializedName("78")
        private String value78;
        @SerializedName("79")
        private String value79;
        @SerializedName("80")
        private String value80;
        @SerializedName("81")
        private String value81;
        @SerializedName("82")
        private String value82;
        @SerializedName("83")
        private String value83;
        @SerializedName("84")
        private String value84;
        @SerializedName("85")
        private String value85;
        @SerializedName("86")
        private String value86;
        @SerializedName("87")
        private String value87;
        @SerializedName("88")
        private String value88;
        @SerializedName("89")
        private String value89;
        @SerializedName("90")
        private String value90;
        @SerializedName("91")
        private String value91;
        @SerializedName("92")
        private String value92;
        @SerializedName("93")
        private String value93;
        @SerializedName("94")
        private String value94;
        @SerializedName("95")
        private String value95;
        @SerializedName("96")
        private String value96;
        @SerializedName("97")
        private String value97;
        @SerializedName("98")
        private String value98;
        @SerializedName("99")
        private String value99;
        @SerializedName("100")
        private String value100;
        @SerializedName("101")
        private String value101;
        @SerializedName("102")
        private String value102;
        @SerializedName("103")
        private String value103;
        @SerializedName("104")
        private String value104;
        @SerializedName("105")
        private String value105;
        @SerializedName("106")
        private String value106;
        @SerializedName("107")
        private String value107;
        @SerializedName("108")
        private String value108;
        @SerializedName("109")
        private String value109;
        @SerializedName("110")
        private String value110;
        @SerializedName("111")
        private String value111;
        @SerializedName("112")
        private String value112;
        @SerializedName("113")
        private String value113;
        @SerializedName("114")
        private String value114;
        @SerializedName("115")
        private String value115;
        @SerializedName("116")
        private String value116;
        @SerializedName("117")
        private String value117;
        @SerializedName("118")
        private String value118;
        @SerializedName("119")
        private String value119;
        @SerializedName("120")
        private String value120;
        @SerializedName("121")
        private String value121;
        @SerializedName("122")
        private String value122;
        @SerializedName("123")
        private String value123;
        @SerializedName("124")
        private String value124;
        @SerializedName("125")
        private String value125;
        @SerializedName("126")
        private String value126;
        @SerializedName("127")
        private String value127;
        @SerializedName("128")
        private String value128;
        @SerializedName("129")
        private String value129;
        @SerializedName("130")
        private String value130;
        @SerializedName("131")
        private String value131;
        @SerializedName("132")
        private String value132;
        @SerializedName("133")
        private String value133;
        @SerializedName("134")
        private String value134;
        @SerializedName("135")
        private String value135;
        @SerializedName("136")
        private String value136;
        @SerializedName("137")
        private String value137;
        @SerializedName("138")
        private String value138;
        @SerializedName("139")
        private String value139;
        @SerializedName("140")
        private String value140;
        @SerializedName("141")
        private String value141;
        @SerializedName("142")
        private String value142;
        @SerializedName("143")
        private String value143;
        @SerializedName("144")
        private String value144;
        @SerializedName("145")
        private String value145;
        @SerializedName("146")
        private String value146;
        @SerializedName("147")
        private String value147;
        @SerializedName("148")
        private String value148;
        @SerializedName("149")
        private String value149;
        @SerializedName("150")
        private String value150;
        @SerializedName("151")
        private String value151;
        @SerializedName("152")
        private String value152;

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public String getValue3() {
            return value3;
        }

        public void setValue3(String value3) {
            this.value3 = value3;
        }

        public String getValue4() {
            return value4;
        }

        public void setValue4(String value4) {
            this.value4 = value4;
        }

        public String getValue5() {
            return value5;
        }

        public void setValue5(String value5) {
            this.value5 = value5;
        }

        public String getValue6() {
            return value6;
        }

        public void setValue6(String value6) {
            this.value6 = value6;
        }

        public String getValue7() {
            return value7;
        }

        public void setValue7(String value7) {
            this.value7 = value7;
        }

        public String getValue8() {
            return value8;
        }

        public void setValue8(String value8) {
            this.value8 = value8;
        }

        public String getValue9() {
            return value9;
        }

        public void setValue9(String value9) {
            this.value9 = value9;
        }

        public String getValue10() {
            return value10;
        }

        public void setValue10(String value10) {
            this.value10 = value10;
        }

        public String getValue11() {
            return value11;
        }

        public void setValue11(String value11) {
            this.value11 = value11;
        }

        public String getValue12() {
            return value12;
        }

        public void setValue12(String value12) {
            this.value12 = value12;
        }

        public String getValue13() {
            return value13;
        }

        public void setValue13(String value13) {
            this.value13 = value13;
        }

        public String getValue14() {
            return value14;
        }

        public void setValue14(String value14) {
            this.value14 = value14;
        }

        public String getValue15() {
            return value15;
        }

        public void setValue15(String value15) {
            this.value15 = value15;
        }

        public String getValue16() {
            return value16;
        }

        public void setValue16(String value16) {
            this.value16 = value16;
        }

        public String getValue17() {
            return value17;
        }

        public void setValue17(String value17) {
            this.value17 = value17;
        }

        public String getValue18() {
            return value18;
        }

        public void setValue18(String value18) {
            this.value18 = value18;
        }

        public String getValue19() {
            return value19;
        }

        public void setValue19(String value19) {
            this.value19 = value19;
        }

        public String getValue20() {
            return value20;
        }

        public void setValue20(String value20) {
            this.value20 = value20;
        }

        public String getValue21() {
            return value21;
        }

        public void setValue21(String value21) {
            this.value21 = value21;
        }

        public String getValue22() {
            return value22;
        }

        public void setValue22(String value22) {
            this.value22 = value22;
        }

        public String getValue23() {
            return value23;
        }

        public void setValue23(String value23) {
            this.value23 = value23;
        }

        public String getValue24() {
            return value24;
        }

        public void setValue24(String value24) {
            this.value24 = value24;
        }

        public String getValue25() {
            return value25;
        }

        public void setValue25(String value25) {
            this.value25 = value25;
        }

        public String getValue26() {
            return value26;
        }

        public void setValue26(String value26) {
            this.value26 = value26;
        }

        public String getValue27() {
            return value27;
        }

        public void setValue27(String value27) {
            this.value27 = value27;
        }

        public String getValue28() {
            return value28;
        }

        public void setValue28(String value28) {
            this.value28 = value28;
        }

        public String getValue29() {
            return value29;
        }

        public void setValue29(String value29) {
            this.value29 = value29;
        }

        public String getValue30() {
            return value30;
        }

        public void setValue30(String value30) {
            this.value30 = value30;
        }

        public String getValue31() {
            return value31;
        }

        public void setValue31(String value31) {
            this.value31 = value31;
        }

        public String getValue32() {
            return value32;
        }

        public void setValue32(String value32) {
            this.value32 = value32;
        }

        public String getValue33() {
            return value33;
        }

        public void setValue33(String value33) {
            this.value33 = value33;
        }

        public String getValue34() {
            return value34;
        }

        public void setValue34(String value34) {
            this.value34 = value34;
        }

        public String getValue35() {
            return value35;
        }

        public void setValue35(String value35) {
            this.value35 = value35;
        }

        public String getValue36() {
            return value36;
        }

        public void setValue36(String value36) {
            this.value36 = value36;
        }

        public String getValue37() {
            return value37;
        }

        public void setValue37(String value37) {
            this.value37 = value37;
        }

        public String getValue38() {
            return value38;
        }

        public void setValue38(String value38) {
            this.value38 = value38;
        }

        public String getValue39() {
            return value39;
        }

        public void setValue39(String value39) {
            this.value39 = value39;
        }

        public String getValue40() {
            return value40;
        }

        public void setValue40(String value40) {
            this.value40 = value40;
        }

        public String getValue41() {
            return value41;
        }

        public void setValue41(String value41) {
            this.value41 = value41;
        }

        public String getValue42() {
            return value42;
        }

        public void setValue42(String value42) {
            this.value42 = value42;
        }

        public String getValue43() {
            return value43;
        }

        public void setValue43(String value43) {
            this.value43 = value43;
        }

        public String getValue44() {
            return value44;
        }

        public void setValue44(String value44) {
            this.value44 = value44;
        }

        public String getValue45() {
            return value45;
        }

        public void setValue45(String value45) {
            this.value45 = value45;
        }

        public String getValue46() {
            return value46;
        }

        public void setValue46(String value46) {
            this.value46 = value46;
        }

        public String getValue47() {
            return value47;
        }

        public void setValue47(String value47) {
            this.value47 = value47;
        }

        public String getValue48() {
            return value48;
        }

        public void setValue48(String value48) {
            this.value48 = value48;
        }

        public String getValue49() {
            return value49;
        }

        public void setValue49(String value49) {
            this.value49 = value49;
        }

        public String getValue50() {
            return value50;
        }

        public void setValue50(String value50) {
            this.value50 = value50;
        }

        public String getValue51() {
            return value51;
        }

        public void setValue51(String value51) {
            this.value51 = value51;
        }

        public String getValue52() {
            return value52;
        }

        public void setValue52(String value52) {
            this.value52 = value52;
        }

        public String getValue53() {
            return value53;
        }

        public void setValue53(String value53) {
            this.value53 = value53;
        }

        public String getValue54() {
            return value54;
        }

        public void setValue54(String value54) {
            this.value54 = value54;
        }

        public String getValue55() {
            return value55;
        }

        public void setValue55(String value55) {
            this.value55 = value55;
        }

        public String getValue56() {
            return value56;
        }

        public void setValue56(String value56) {
            this.value56 = value56;
        }

        public String getValue57() {
            return value57;
        }

        public void setValue57(String value57) {
            this.value57 = value57;
        }

        public String getValue58() {
            return value58;
        }

        public void setValue58(String value58) {
            this.value58 = value58;
        }

        public String getValue59() {
            return value59;
        }

        public void setValue59(String value59) {
            this.value59 = value59;
        }

        public String getValue60() {
            return value60;
        }

        public void setValue60(String value60) {
            this.value60 = value60;
        }

        public String getValue61() {
            return value61;
        }

        public void setValue61(String value61) {
            this.value61 = value61;
        }

        public String getValue62() {
            return value62;
        }

        public void setValue62(String value62) {
            this.value62 = value62;
        }

        public String getValue63() {
            return value63;
        }

        public void setValue63(String value63) {
            this.value63 = value63;
        }

        public String getValue64() {
            return value64;
        }

        public void setValue64(String value64) {
            this.value64 = value64;
        }

        public String getValue65() {
            return value65;
        }

        public void setValue65(String value65) {
            this.value65 = value65;
        }

        public String getValue66() {
            return value66;
        }

        public void setValue66(String value66) {
            this.value66 = value66;
        }

        public String getValue67() {
            return value67;
        }

        public void setValue67(String value67) {
            this.value67 = value67;
        }

        public String getValue68() {
            return value68;
        }

        public void setValue68(String value68) {
            this.value68 = value68;
        }

        public String getValue69() {
            return value69;
        }

        public void setValue69(String value69) {
            this.value69 = value69;
        }

        public String getValue70() {
            return value70;
        }

        public void setValue70(String value70) {
            this.value70 = value70;
        }

        public String getValue71() {
            return value71;
        }

        public void setValue71(String value71) {
            this.value71 = value71;
        }

        public String getValue72() {
            return value72;
        }

        public void setValue72(String value72) {
            this.value72 = value72;
        }

        public String getValue73() {
            return value73;
        }

        public void setValue73(String value73) {
            this.value73 = value73;
        }

        public String getValue74() {
            return value74;
        }

        public void setValue74(String value74) {
            this.value74 = value74;
        }

        public String getValue75() {
            return value75;
        }

        public void setValue75(String value75) {
            this.value75 = value75;
        }

        public String getValue76() {
            return value76;
        }

        public void setValue76(String value76) {
            this.value76 = value76;
        }

        public String getValue77() {
            return value77;
        }

        public void setValue77(String value77) {
            this.value77 = value77;
        }

        public String getValue78() {
            return value78;
        }

        public void setValue78(String value78) {
            this.value78 = value78;
        }

        public String getValue79() {
            return value79;
        }

        public void setValue79(String value79) {
            this.value79 = value79;
        }

        public String getValue80() {
            return value80;
        }

        public void setValue80(String value80) {
            this.value80 = value80;
        }

        public String getValue81() {
            return value81;
        }

        public void setValue81(String value81) {
            this.value81 = value81;
        }

        public String getValue82() {
            return value82;
        }

        public void setValue82(String value82) {
            this.value82 = value82;
        }

        public String getValue83() {
            return value83;
        }

        public void setValue83(String value83) {
            this.value83 = value83;
        }

        public String getValue84() {
            return value84;
        }

        public void setValue84(String value84) {
            this.value84 = value84;
        }

        public String getValue85() {
            return value85;
        }

        public void setValue85(String value85) {
            this.value85 = value85;
        }

        public String getValue86() {
            return value86;
        }

        public void setValue86(String value86) {
            this.value86 = value86;
        }

        public String getValue87() {
            return value87;
        }

        public void setValue87(String value87) {
            this.value87 = value87;
        }

        public String getValue88() {
            return value88;
        }

        public void setValue88(String value88) {
            this.value88 = value88;
        }

        public String getValue89() {
            return value89;
        }

        public void setValue89(String value89) {
            this.value89 = value89;
        }

        public String getValue90() {
            return value90;
        }

        public void setValue90(String value90) {
            this.value90 = value90;
        }

        public String getValue91() {
            return value91;
        }

        public void setValue91(String value91) {
            this.value91 = value91;
        }

        public String getValue92() {
            return value92;
        }

        public void setValue92(String value92) {
            this.value92 = value92;
        }

        public String getValue93() {
            return value93;
        }

        public void setValue93(String value93) {
            this.value93 = value93;
        }

        public String getValue94() {
            return value94;
        }

        public void setValue94(String value94) {
            this.value94 = value94;
        }

        public String getValue95() {
            return value95;
        }

        public void setValue95(String value95) {
            this.value95 = value95;
        }

        public String getValue96() {
            return value96;
        }

        public void setValue96(String value96) {
            this.value96 = value96;
        }

        public String getValue97() {
            return value97;
        }

        public void setValue97(String value97) {
            this.value97 = value97;
        }

        public String getValue98() {
            return value98;
        }

        public void setValue98(String value98) {
            this.value98 = value98;
        }

        public String getValue99() {
            return value99;
        }

        public void setValue99(String value99) {
            this.value99 = value99;
        }

        public String getValue100() {
            return value100;
        }

        public void setValue100(String value100) {
            this.value100 = value100;
        }

        public String getValue101() {
            return value101;
        }

        public void setValue101(String value101) {
            this.value101 = value101;
        }

        public String getValue102() {
            return value102;
        }

        public void setValue102(String value102) {
            this.value102 = value102;
        }

        public String getValue103() {
            return value103;
        }

        public void setValue103(String value103) {
            this.value103 = value103;
        }

        public String getValue104() {
            return value104;
        }

        public void setValue104(String value104) {
            this.value104 = value104;
        }

        public String getValue105() {
            return value105;
        }

        public void setValue105(String value105) {
            this.value105 = value105;
        }

        public String getValue106() {
            return value106;
        }

        public void setValue106(String value106) {
            this.value106 = value106;
        }

        public String getValue107() {
            return value107;
        }

        public void setValue107(String value107) {
            this.value107 = value107;
        }

        public String getValue108() {
            return value108;
        }

        public void setValue108(String value108) {
            this.value108 = value108;
        }

        public String getValue109() {
            return value109;
        }

        public void setValue109(String value109) {
            this.value109 = value109;
        }

        public String getValue110() {
            return value110;
        }

        public void setValue110(String value110) {
            this.value110 = value110;
        }

        public String getValue111() {
            return value111;
        }

        public void setValue111(String value111) {
            this.value111 = value111;
        }

        public String getValue112() {
            return value112;
        }

        public void setValue112(String value112) {
            this.value112 = value112;
        }

        public String getValue113() {
            return value113;
        }

        public void setValue113(String value113) {
            this.value113 = value113;
        }

        public String getValue114() {
            return value114;
        }

        public void setValue114(String value114) {
            this.value114 = value114;
        }

        public String getValue115() {
            return value115;
        }

        public void setValue115(String value115) {
            this.value115 = value115;
        }

        public String getValue116() {
            return value116;
        }

        public void setValue116(String value116) {
            this.value116 = value116;
        }

        public String getValue117() {
            return value117;
        }

        public void setValue117(String value117) {
            this.value117 = value117;
        }

        public String getValue118() {
            return value118;
        }

        public void setValue118(String value118) {
            this.value118 = value118;
        }

        public String getValue119() {
            return value119;
        }

        public void setValue119(String value119) {
            this.value119 = value119;
        }

        public String getValue120() {
            return value120;
        }

        public void setValue120(String value120) {
            this.value120 = value120;
        }

        public String getValue121() {
            return value121;
        }

        public void setValue121(String value121) {
            this.value121 = value121;
        }

        public String getValue122() {
            return value122;
        }

        public void setValue122(String value122) {
            this.value122 = value122;
        }

        public String getValue123() {
            return value123;
        }

        public void setValue123(String value123) {
            this.value123 = value123;
        }

        public String getValue124() {
            return value124;
        }

        public void setValue124(String value124) {
            this.value124 = value124;
        }

        public String getValue125() {
            return value125;
        }

        public void setValue125(String value125) {
            this.value125 = value125;
        }

        public String getValue126() {
            return value126;
        }

        public void setValue126(String value126) {
            this.value126 = value126;
        }

        public String getValue127() {
            return value127;
        }

        public void setValue127(String value127) {
            this.value127 = value127;
        }

        public String getValue128() {
            return value128;
        }

        public void setValue128(String value128) {
            this.value128 = value128;
        }

        public String getValue129() {
            return value129;
        }

        public void setValue129(String value129) {
            this.value129 = value129;
        }

        public String getValue130() {
            return value130;
        }

        public void setValue130(String value130) {
            this.value130 = value130;
        }

        public String getValue131() {
            return value131;
        }

        public void setValue131(String value131) {
            this.value131 = value131;
        }

        public String getValue132() {
            return value132;
        }

        public void setValue132(String value132) {
            this.value132 = value132;
        }

        public String getValue133() {
            return value133;
        }

        public void setValue133(String value133) {
            this.value133 = value133;
        }

        public String getValue134() {
            return value134;
        }

        public void setValue134(String value134) {
            this.value134 = value134;
        }

        public String getValue135() {
            return value135;
        }

        public void setValue135(String value135) {
            this.value135 = value135;
        }

        public String getValue136() {
            return value136;
        }

        public void setValue136(String value136) {
            this.value136 = value136;
        }

        public String getValue137() {
            return value137;
        }

        public void setValue137(String value137) {
            this.value137 = value137;
        }

        public String getValue138() {
            return value138;
        }

        public void setValue138(String value138) {
            this.value138 = value138;
        }

        public String getValue139() {
            return value139;
        }

        public void setValue139(String value139) {
            this.value139 = value139;
        }

        public String getValue140() {
            return value140;
        }

        public void setValue140(String value140) {
            this.value140 = value140;
        }

        public String getValue141() {
            return value141;
        }

        public void setValue141(String value141) {
            this.value141 = value141;
        }

        public String getValue142() {
            return value142;
        }

        public void setValue142(String value142) {
            this.value142 = value142;
        }

        public String getValue143() {
            return value143;
        }

        public void setValue143(String value143) {
            this.value143 = value143;
        }

        public String getValue144() {
            return value144;
        }

        public void setValue144(String value144) {
            this.value144 = value144;
        }

        public String getValue145() {
            return value145;
        }

        public void setValue145(String value145) {
            this.value145 = value145;
        }

        public String getValue146() {
            return value146;
        }

        public void setValue146(String value146) {
            this.value146 = value146;
        }

        public String getValue147() {
            return value147;
        }

        public void setValue147(String value147) {
            this.value147 = value147;
        }

        public String getValue148() {
            return value148;
        }

        public void setValue148(String value148) {
            this.value148 = value148;
        }

        public String getValue149() {
            return value149;
        }

        public void setValue149(String value149) {
            this.value149 = value149;
        }

        public String getValue150() {
            return value150;
        }

        public void setValue150(String value150) {
            this.value150 = value150;
        }

        public String getValue151() {
            return value151;
        }

        public void setValue151(String value151) {
            this.value151 = value151;
        }

        public String getValue152() {
            return value152;
        }

        public void setValue152(String value152) {
            this.value152 = value152;
        }
    }
}
