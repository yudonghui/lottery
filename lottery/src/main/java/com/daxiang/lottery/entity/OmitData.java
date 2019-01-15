package com.daxiang.lottery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/16 0016.
 */
public class OmitData {

    /**
     * code : 0
     * msg : 获取成功
     * data : {"BLUE":{"10":"3","11":"1","12":"0","01":"1","02":"6","03":"2","04":"0","05":"3","06":"4","07":"4","08":"2","09":"6"},"RED":{"10":"6","11":"2","12":"6","13":"3","14":"6","15":"6","16":"6","17":"2","18":"6","19":"0","20":"0","21":"1","22":"1","23":"5","24":"6","25":"1","26":"5","27":"4","28":"0","29":"1","30":"0","31":"3","32":"6","33":"6","34":"1","35":"6","01":"2","02":"6","03":"6","04":"3","05":"3","06":"4","07":"0","08":"6","09":"2"}}
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

    public static class DataBean {
        /**
         * BLUE : {"10":"3","11":"1","12":"0","01":"1","02":"6","03":"2","04":"0","05":"3","06":"4","07":"4","08":"2","09":"6"}
         * RED : {"10":"6","11":"2","12":"6","13":"3","14":"6","15":"6","16":"6","17":"2","18":"6","19":"0","20":"0","21":"1","22":"1","23":"5","24":"6","25":"1","26":"5","27":"4","28":"0","29":"1","30":"0","31":"3","32":"6","33":"6","34":"1","35":"6","01":"2","02":"6","03":"6","04":"3","05":"3","06":"4","07":"0","08":"6","09":"2"}
         */

        private BLUEBean BLUE;
        private REDBean RED;
        private ZHI1Bean ZHI1;
        private ZHI2Bean ZHI2;
        private ZHI3Bean ZHI3;
        private ZHI4Bean ZHI4;
        private ZHI5Bean ZHI5;
        private ZHI6Bean ZHI6;
        private ZHI7Bean ZHI7;
        private ZHUBean ZHU;
        private ZHU2Bean ZHU2;
        private ZHU3Bean ZHU3;
        public BLUEBean getBLUE() {
            return BLUE;
        }

        public void setBLUE(BLUEBean BLUE) {
            this.BLUE = BLUE;
        }

        public REDBean getRED() {
            return RED;
        }

        public void setRED(REDBean RED) {
            this.RED = RED;
        }
        public ZHI1Bean getZHI1() {
            return ZHI1;
        }

        public void setZHI1(ZHI1Bean ZHI1) {
            this.ZHI1 = ZHI1;
        }

        public ZHI2Bean getZHI2() {
            return ZHI2;
        }

        public void setZHI2(ZHI2Bean ZHI2) {
            this.ZHI2 = ZHI2;
        }

        public ZHI3Bean getZHI3() {
            return ZHI3;
        }

        public void setZHI3(ZHI3Bean ZHI3) {
            this.ZHI3 = ZHI3;
        }

        public ZHI4Bean getZHI4() {
            return ZHI4;
        }

        public void setZHI4(ZHI4Bean ZHI4) {
            this.ZHI4 = ZHI4;
        }

        public ZHI5Bean getZHI5() {
            return ZHI5;
        }

        public void setZHI5(ZHI5Bean ZHI5) {
            this.ZHI5 = ZHI5;
        }

        public ZHI6Bean getZHI6() {
            return ZHI6;
        }

        public void setZHI6(ZHI6Bean ZHI6) {
            this.ZHI6 = ZHI6;
        }

        public ZHI7Bean getZHI7() {
            return ZHI7;
        }

        public void setZHI7(ZHI7Bean ZHI7) {
            this.ZHI7 = ZHI7;
        }
        public ZHUBean getZHU() {
            return ZHU;
        }

        public void setZHU(ZHUBean ZHU) {
            this.ZHU = ZHU;
        }
        public ZHU2Bean getZHU2() {
            return ZHU2;
        }

        public void setZHU2(ZHU2Bean ZHU2) {
            this.ZHU2 = ZHU2;
        }

        public ZHU3Bean getZHU3() {
            return ZHU3;
        }

        public void setZHU3(ZHU3Bean ZHU3) {
            this.ZHU3 = ZHU3;
        }
        public static class BLUEBean {
            /**
             * 10 : 3
             * 11 : 1
             * 12 : 0
             * 01 : 1
             * 02 : 6
             * 03 : 2
             * 04 : 0
             * 05 : 3
             * 06 : 4
             * 07 : 4
             * 08 : 2
             * 09 : 6
             */

            @SerializedName("10")
            private String value10;
            @SerializedName("11")
            private String value11;
            @SerializedName("12")
            private String value12;
            @SerializedName("01")
            private String value01;
            @SerializedName("02")
            private String value02;
            @SerializedName("03")
            private String value03;
            @SerializedName("04")
            private String value04;
            @SerializedName("05")
            private String value05;
            @SerializedName("06")
            private String value06;
            @SerializedName("07")
            private String value07;
            @SerializedName("08")
            private String value08;
            @SerializedName("09")
            private String value09;

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

            public String getValue01() {
                return value01;
            }

            public void setValue01(String value01) {
                this.value01 = value01;
            }

            public String getValue02() {
                return value02;
            }

            public void setValue02(String value02) {
                this.value02 = value02;
            }

            public String getValue03() {
                return value03;
            }

            public void setValue03(String value03) {
                this.value03 = value03;
            }

            public String getValue04() {
                return value04;
            }

            public void setValue04(String value04) {
                this.value04 = value04;
            }

            public String getValue05() {
                return value05;
            }

            public void setValue05(String value05) {
                this.value05 = value05;
            }

            public String getValue06() {
                return value06;
            }

            public void setValue06(String value06) {
                this.value06 = value06;
            }

            public String getValue07() {
                return value07;
            }

            public void setValue07(String value07) {
                this.value07 = value07;
            }

            public String getValue08() {
                return value08;
            }

            public void setValue08(String value08) {
                this.value08 = value08;
            }

            public String getValue09() {
                return value09;
            }

            public void setValue09(String value09) {
                this.value09 = value09;
            }
        }

        public static class REDBean {
            /**
             * 10 : 6
             * 11 : 2
             * 12 : 6
             * 13 : 3
             * 14 : 6
             * 15 : 6
             * 16 : 6
             * 17 : 2
             * 18 : 6
             * 19 : 0
             * 20 : 0
             * 21 : 1
             * 22 : 1
             * 23 : 5
             * 24 : 6
             * 25 : 1
             * 26 : 5
             * 27 : 4
             * 28 : 0
             * 29 : 1
             * 30 : 0
             * 31 : 3
             * 32 : 6
             * 33 : 6
             * 34 : 1
             * 35 : 6
             * 01 : 2
             * 02 : 6
             * 03 : 6
             * 04 : 3
             * 05 : 3
             * 06 : 4
             * 07 : 0
             * 08 : 6
             * 09 : 2
             */

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
            @SerializedName("01")
            private String value01;
            @SerializedName("02")
            private String value02;
            @SerializedName("03")
            private String value03;
            @SerializedName("04")
            private String value04;
            @SerializedName("05")
            private String value05;
            @SerializedName("06")
            private String value06;
            @SerializedName("07")
            private String value07;
            @SerializedName("08")
            private String value08;
            @SerializedName("09")
            private String value09;

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

            public String getValue01() {
                return value01;
            }

            public void setValue01(String value01) {
                this.value01 = value01;
            }

            public String getValue02() {
                return value02;
            }

            public void setValue02(String value02) {
                this.value02 = value02;
            }

            public String getValue03() {
                return value03;
            }

            public void setValue03(String value03) {
                this.value03 = value03;
            }

            public String getValue04() {
                return value04;
            }

            public void setValue04(String value04) {
                this.value04 = value04;
            }

            public String getValue05() {
                return value05;
            }

            public void setValue05(String value05) {
                this.value05 = value05;
            }

            public String getValue06() {
                return value06;
            }

            public void setValue06(String value06) {
                this.value06 = value06;
            }

            public String getValue07() {
                return value07;
            }

            public void setValue07(String value07) {
                this.value07 = value07;
            }

            public String getValue08() {
                return value08;
            }

            public void setValue08(String value08) {
                this.value08 = value08;
            }

            public String getValue09() {
                return value09;
            }

            public void setValue09(String value09) {
                this.value09 = value09;
            }
        }
    }
    public static class ZHI1Bean {
        /**
         * 00 : 1
         * 01 : 5
         * 02 : 5
         * 03 : 5
         * 04 : 0
         * 05 : 5
         * 06 : 4
         * 07 : 2
         * 08 : 5
         * 09 : 5
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }

    public static class ZHI2Bean {
        /**
         * 00 : 5
         * 01 : 4
         * 02 : 5
         * 03 : 2
         * 04 : 5
         * 05 : 1
         * 06 : 5
         * 07 : 0
         * 08 : 5
         * 09 : 5
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }

    public static class ZHI3Bean {
        /**
         * 00 : 5
         * 01 : 2
         * 02 : 5
         * 03 : 5
         * 04 : 3
         * 05 : 0
         * 06 : 5
         * 07 : 4
         * 08 : 1
         * 09 : 5
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }

    public static class ZHI4Bean {
        /**
         * 00 : 5
         * 01 : 4
         * 02 : 0
         * 03 : 2
         * 04 : 5
         * 05 : 5
         * 06 : 5
         * 07 : 3
         * 08 : 5
         * 09 : 5
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }

    public static class ZHI5Bean {
        /**
         * 00 : 1
         * 01 : 3
         * 02 : 5
         * 03 : 5
         * 04 : 5
         * 05 : 5
         * 06 : 5
         * 07 : 5
         * 08 : 0
         * 09 : 4
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }

    public static class ZHI6Bean {
        /**
         * 00 : 1
         * 01 : 5
         * 02 : 2
         * 03 : 5
         * 04 : 5
         * 05 : 4
         * 06 : 5
         * 07 : 0
         * 08 : 3
         * 09 : 5
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }

    public static class ZHI7Bean {
        /**
         * 00 : 5
         * 01 : 2
         * 02 : 1
         * 03 : 5
         * 04 : 5
         * 05 : 5
         * 06 : 3
         * 07 : 0
         * 08 : 5
         * 09 : 5
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }
    public static class ZHUBean {
        /**
         * 00 : 0
         * 01 : 3
         * 02 : 3
         * 03 : 1
         * 04 : 3
         * 05 : 3
         * 06 : 1
         * 07 : 0
         * 08 : 0
         * 09 : 2
         */

        @SerializedName("00")
        private String value00;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

        public String getValue00() {
            return value00;
        }

        public void setValue00(String value00) {
            this.value00 = value00;
        }

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }
    public static class ZHU2Bean {
        /**
         * 10 : 1
         * 11 : 0
         * 01 : 3
         * 02 : 10
         * 03 : 6
         * 04 : 0
         * 05 : 15
         * 06 : 4
         * 07 : 5
         * 08 : 2
         * 09 : 1
         */

        @SerializedName("10")
        private String value10;
        @SerializedName("11")
        private String value11;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

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

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }

    public static class ZHU3Bean {
        /**
         * 10 : 0
         * 11 : 0
         * 01 : 3
         * 02 : 10
         * 03 : 3
         * 04 : 0
         * 05 : 15
         * 06 : 4
         * 07 : 2
         * 08 : 1
         * 09 : 1
         */

        @SerializedName("10")
        private String value10;
        @SerializedName("11")
        private String value11;
        @SerializedName("01")
        private String value01;
        @SerializedName("02")
        private String value02;
        @SerializedName("03")
        private String value03;
        @SerializedName("04")
        private String value04;
        @SerializedName("05")
        private String value05;
        @SerializedName("06")
        private String value06;
        @SerializedName("07")
        private String value07;
        @SerializedName("08")
        private String value08;
        @SerializedName("09")
        private String value09;

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

        public String getValue01() {
            return value01;
        }

        public void setValue01(String value01) {
            this.value01 = value01;
        }

        public String getValue02() {
            return value02;
        }

        public void setValue02(String value02) {
            this.value02 = value02;
        }

        public String getValue03() {
            return value03;
        }

        public void setValue03(String value03) {
            this.value03 = value03;
        }

        public String getValue04() {
            return value04;
        }

        public void setValue04(String value04) {
            this.value04 = value04;
        }

        public String getValue05() {
            return value05;
        }

        public void setValue05(String value05) {
            this.value05 = value05;
        }

        public String getValue06() {
            return value06;
        }

        public void setValue06(String value06) {
            this.value06 = value06;
        }

        public String getValue07() {
            return value07;
        }

        public void setValue07(String value07) {
            this.value07 = value07;
        }

        public String getValue08() {
            return value08;
        }

        public void setValue08(String value08) {
            this.value08 = value08;
        }

        public String getValue09() {
            return value09;
        }

        public void setValue09(String value09) {
            this.value09 = value09;
        }
    }
}
