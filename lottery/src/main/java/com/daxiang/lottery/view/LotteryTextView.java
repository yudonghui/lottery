package com.daxiang.lottery.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.entity.ChampionBean;
import com.daxiang.lottery.entity.ChampionRunnerBean;
import com.daxiang.lottery.utils.JsonToMapUtils;
import com.daxiang.lottery.utils.Lists;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.Maps;
import com.daxiang.lottery.utils.Strings;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android on 2018/1/2.
 */

public class LotteryTextView extends TextView {
    private String mLotId;         // 彩种
    private String mAwardNum;   // 开奖号码
    private String mBuyContent; // 投注内容
    private Context mContext;

    public LotteryTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LotteryTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
        final TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.LotteryTextView);
        // mLotId = a.getInt(R.styleable.LotteryTextView_ltv_lotteryId, -1);
        mAwardNum = a.getString(R.styleable.LotteryTextView_ltv_awardNum);
        mBuyContent = a.getString(R.styleable.LotteryTextView_ltv_buyContent);
        a.recycle();

        init();
    }

    private int RED_COLOR;
    private int BLACK_COLOR;
    private int GRAY_COLOR;
    private int BLUE_COLOR;

    private void init() {
        setAwardNum(mAwardNum, mLotId);//开奖号码
        setBuyContent(mAwardNum, mBuyContent, mLotId);//购买号码
        RED_COLOR = mContext.getResources().getColor(R.color.red_txt);
        BLACK_COLOR = mContext.getResources().getColor(R.color.deep_txt);
        GRAY_COLOR = mContext.getResources().getColor(R.color.gray_txt);
        BLUE_COLOR = mContext.getResources().getColor(R.color.blue_txt);
    }

    public void setLotId(String lotId) {
        mLotId = lotId;
    }

    public void setAwardNum(String mAwardNum, String mLotId) {
        if (TextUtils.isEmpty(mAwardNum)) {//中奖号码为空，
            return;
        }
        this.mAwardNum = mAwardNum;
        this.mLotId = mLotId;
        this.model = 1;
       /* Lottery lottery = newLottery(mLotId);
        lottery.spliteContent(mAwardNum);*/
        if (!TextUtils.isEmpty(setSpanAward()))
            setText(setSpanAward());
        else setText(TextUtils.isEmpty(mAwardNum) ? "--" : mAwardNum);


    }

    public void setAwardNum(String mAwardNum, String mBuyContent, String mLotId) {//冠亚军的开奖
        if (TextUtils.isEmpty(mAwardNum) || TextUtils.isEmpty(mBuyContent)) {//开奖号码为空，
            return;
        }
        String[] split = mBuyContent.split("\\|");
        String name = getName(mAwardNum, split[0]);
        if (!TextUtils.isEmpty(name)) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(name);
            ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            append(ssb);
        }
    }

    private int model;//1,中奖号码。2，购彩号码。

    public void setBuyContent(String mAwardNum, String mBuyContent, String mLotId) {
        if (TextUtils.isEmpty(mBuyContent)) {
            return;
        }
        this.mAwardNum = mAwardNum;
        this.mBuyContent = mBuyContent;
        this.mLotId = mLotId;
        this.model = 2;
        if (LotCode.GJ_CODE.equals(mLotId)) {//冠亚军
            gjformat();
        } else {
            Lottery lottery = newLottery(mLotId);
            lottery.spliteContent(mBuyContent);
        }

    }

    private void gjformat() {
        setText("");
        StringBuilder sb = new StringBuilder();
        String[] split = mBuyContent.split("\\|");
        String[] split1 = split[1].split("\\,");
        String header = "GJ".equals(split[0]) ? "[冠军]" : "[冠亚军]";
        int startIndex = 0;
        int endIndex = 0;
        boolean isWin = false;
        for (int i = 0; i < split1.length; i++) {
            sb.append(header);
            String name = getName(split1[i], split[0]);
            if (!TextUtils.isEmpty(mAwardNum)) {
                if (split1[i].equals(mAwardNum)) {
                    isWin = true;
                    startIndex = sb.length() - header.length();
                    endIndex = sb.length() + name.length();
                }
            }
            sb.append(name);
            if (i < split1.length - 1)
                sb.append("\n");
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(sb);
        if (isWin) {
            ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        append(ssb);
    }

    public String getName(String seria, String method) {//队伍编号，冠亚军或者冠军
        if (TextUtils.isEmpty(seria)) return "";
        String serial = Integer.parseInt(seria) + "";
        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        if ("GJ".equals(method)) {
            String gj = sp.getString("gj", "");
            if (TextUtils.isEmpty(gj)) {
                gj = JsonToMapUtils.getJson(mContext, "gj.json");
            }
            ChampionBean gjBean = gson.fromJson(gj, ChampionBean.class);
            int code = gjBean.getCode();
            if (code == 0) {
                ChampionBean.DataBean data = gjBean.getData();
                List<ChampionBean.DataBean.ItemsBean> items = data.getItems();
                for (int i = 0; i < items.size(); i++) {
                    ChampionBean.DataBean.ItemsBean itemsBean = items.get(i);
                    if (serial.equals(itemsBean.getSerial())) {
                        return itemsBean.getTeam_name();
                    }
                }
            }
        } else {
            String gyj = sp.getString("gyj", "");
            if (TextUtils.isEmpty(gyj)) {
                gyj = JsonToMapUtils.getJson(mContext, "gyj.json");
            }
            ChampionRunnerBean gyjBean = gson.fromJson(gyj, ChampionRunnerBean.class);
            int code = gyjBean.getCode();
            if (code == 0) {
                ChampionRunnerBean.DataBean data = gyjBean.getData();
                List<ChampionRunnerBean.DataBean.ItemsBean> items = data.getItems();
                for (int i = 0; i < items.size(); i++) {
                    ChampionRunnerBean.DataBean.ItemsBean itemsBean = items.get(i);
                    if (serial.equals(itemsBean.getSerial())) {
                        return itemsBean.getHome_name() + "VS" + itemsBean.getAway_name();
                    }
                }
            }
        }
        return "";
    }

    private Lottery newLottery(String mLotId) {
        switch (mLotId) {
            case LotCode.SSQ_CODE:
                return new SSQ();
            case LotCode.DLT_CODE:
                return new DLT();
            case LotCode.QLC_CODE:
                return new QLC();
            case LotCode.SD11X5_CODE:
                return new SD11X5();
            case LotCode.FC3D_CODE:
                return new FC3D();
            case LotCode.QXC_CODE:
                return new QXC();
            case LotCode.PL3_CODE:
                return new PL3();
            case LotCode.PL5_CODE:
                return new PL5();
            case LotCode.R9C_CODE:
                return new R9C();
            case LotCode.SFC_CODE:
                return new SFC();
            case LotCode.SSC_CODE:
                return new SSC();
            case LotCode.K3_CODE:
                return new K3();
            case LotCode.GJ_CODE:
                return new GJ();
        }
        return null;
    }

    private abstract class Lottery {
        protected abstract SpannableStringBuilder parse(String text);

        protected String buyContentFmt(String buyContent) {
            String s;
            if (shouldFmtBuyContent())
                s = buyContent.replaceAll(":.*$", "");
            else
                s = buyContent;
            if (replaceVerWithColon())
                s = s.replaceAll("\\|", ":");
            return s;
        }

        protected void spliteContent(String content) {
            setText("");
            String[] texts = buyContentFmtFirst(content);
            if (texts == null || texts.length == 0) return;
            Log.e("长度", texts.length + "");
            for (int i = 0; i < texts.length; i++) {
                if (TextUtils.isEmpty(texts[i])) continue;
                Log.e("第一次格式后", texts[i]);
                String fmt;
              /*  if (mLotId.equals(LotCode.SSQ_CODE) && model == 1) {//双色球并且是开奖的
                    fmt = texts[i];
                } else*/
                fmt = buyContentFmt(texts[i]);
                String finalStr = TextUtils.isEmpty(fmt) ? texts[i] : fmt;
                SpannableStringBuilder spannableStringBuilder = parse(finalStr);
                if (spannableStringBuilder == null) continue;
                Log.e("最终结果", spannableStringBuilder.toString());
                append(spannableStringBuilder);
                if (i < texts.length - 1) append("\n");
            }
        }

        protected boolean replaceVerWithColon() {
            return true;
        }

        protected boolean shouldFmtBuyContent() {
            return true;
        }

    }

    private class SSQ extends Lottery {
        Map<String, String> sGroupInfo = Maps.newHashMap();

        public SSQ() {
            sGroupInfo.put("135:5", "");
        }

        @Override
        protected SpannableStringBuilder parse(String text) {
            if (text.indexOf('$') != -1) {
                String finalStr = getStrWithGroupInfo(text, sGroupInfo);
                finalStr = TextUtils.isEmpty(finalStr) ? text : finalStr;
                return parseToRedBlue(finalStr);
            }
            return parseToRedBlue(text);
        }
    }

    private class DLT extends Lottery {
        private Map<String, String> sGroupInfo = Maps.newHashMap();

        public DLT() {
            sGroupInfo.put("1:1", "[普通] ");
            sGroupInfo.put("2:1", "[追加] ");
            sGroupInfo.put("135:1", "[追加] ");
            sGroupInfo.put("135:5", "[普通] ");
        }

        @Override
        protected SpannableStringBuilder parse(String text) {
            String finalStr = getStrWithGroupInfo(text, sGroupInfo);
            finalStr = TextUtils.isEmpty(finalStr) ? text : finalStr;
            return parseToRedBlue(finalStr);
        }

        @Override
        protected boolean shouldFmtBuyContent() {
            return false;
        }
    }

    private class QLC extends Lottery {
        @Override
        protected SpannableStringBuilder parse(String text) {
            return parseToRedBlue(text);
        }
    }

    private class SD11X5 extends Lottery {
        private Map<String, String> sGroupInfo = Maps.newHashMap();

        public SD11X5() {
            sGroupInfo.put("01:01", "[前一] ");
            sGroupInfo.put("02:01", "[任选二] ");
            sGroupInfo.put("02:05", "[任选二] ");
            sGroupInfo.put("03:01", "[任选三] ");
            sGroupInfo.put("03:05", "[任选三] ");
            sGroupInfo.put("04:01", "[任选四] ");
            sGroupInfo.put("04:05", "[任选四] ");
            sGroupInfo.put("05:01", "[任选五] ");
            sGroupInfo.put("05:05", "[任选五] ");
            sGroupInfo.put("06:01", "[任选六] ");
            sGroupInfo.put("06:05", "[任选六] ");
            sGroupInfo.put("07:01", "[任选七] ");
            sGroupInfo.put("07:05", "[任选七] ");
            sGroupInfo.put("08:01", "[任选八] ");
            sGroupInfo.put("08:05", "[任选八] ");
            sGroupInfo.put("09:01", "[前二直选] ");
            sGroupInfo.put("10:01", "[前三直选] ");
            sGroupInfo.put("11:01", "[前二组选] ");
            sGroupInfo.put("11:05", "[前二组选] ");
            sGroupInfo.put("12:01", "[前三组选] ");
            sGroupInfo.put("12:05", "[前三组选] ");
        }

        private Pattern EMPTY_VER_PATTERN = Pattern.compile("\\d+[|]");

        @Override
        protected SpannableStringBuilder parse(String text) {
            String finalStr = getStrWithGroupInfo(text, sGroupInfo);
            final Matcher matcher = EMPTY_VER_PATTERN.matcher(finalStr);
            if (matcher.find()) {
                finalStr = finalStr.replaceAll("\\|", " | ");
            }
            finalStr = TextUtils.isEmpty(finalStr) ? text : finalStr;
            return parseToRedBlue(finalStr);
        }

        @Override
        protected boolean replaceVerWithColon() {
            return false;
        }

        @Override
        protected boolean shouldFmtBuyContent() {
            return false;
        }

    }

    private class FC3D extends Lottery {
        private Map<String, String> sGroupInfo = Maps.newHashMap();

        public FC3D() {
            sGroupInfo.put("3:3", "[组六] ");
            sGroupInfo.put("2:3", "[组三] ");
            sGroupInfo.put("1:1", "[直选] ");
        }


        @Override
        public SpannableStringBuilder parse(String text) {
            final String[] scoreContents = getGroupContent(text);
            if (scoreContents == null || scoreContents.length != 2) return null;
            final String group = sGroupInfo.get(scoreContents[1]) + " ";
            final String splitText = getStrSplitWithVerAndComma(scoreContents[0]);
            final String finalText = group + splitText;
            return setSpanWithVer(finalText);
        }

        @Override
        protected boolean shouldFmtBuyContent() {
            return false;
        }
    }

    private class QXC extends Lottery {
        @Override
        protected SpannableStringBuilder parse(String text) {
            return setSpanWithVer(getStrSplitWithVerAndComma(text));
        }
    }

    private class PL3 extends Lottery {
        private Map<String, String> sGroupInfo = Maps.newHashMap();

        public PL3() {
            sGroupInfo.put("3:3", "[组六] ");
            sGroupInfo.put("2:3", "[组三] ");
            sGroupInfo.put("1:1", "[直选] ");
        }

        @Override
        protected SpannableStringBuilder parse(String text) {
            final String[] scoreContents = getGroupContent(text);

            if (scoreContents == null || scoreContents.length != 2) return null;
            final String group = sGroupInfo.get(scoreContents[1]) + " ";
            final String splitText = getStrSplitWithVerAndComma(scoreContents[0]);
            final String finalText = group + splitText;
            return setSpanWithVer(finalText);
        }

        @Override
        protected boolean shouldFmtBuyContent() {
            return false;
        }
    }

    private class PL5 extends Lottery {
        @Override
        protected SpannableStringBuilder parse(String text) {
            return setSpanWithVer(getStrSplitWithVerAndComma(text));
        }
    }

    private class R9C extends Lottery {
        @Override
        protected SpannableStringBuilder parse(String text) {
            return setSpanWithVer(getStrSplitWithVerAndComma(text));
        }
    }

    private class SFC extends Lottery {
        @Override
        protected SpannableStringBuilder parse(String text) {

            return setSpanWithVer(getStrSplitWithVerAndComma(text));
        }
    }

    private class SSC extends Lottery {

        @Override
        protected SpannableStringBuilder parse(String text) {
            return setSpanWithVer(getStrSplitWithVerAndComma(text));
        }
    }

    private class K3 extends Lottery {
        private Map<String, String> sGroupInfo = Maps.newHashMap();

        public K3() {
            sGroupInfo.put("1:4", "[和值] ");
            sGroupInfo.put("1:3", "[三同号通选] ");
            sGroupInfo.put("1:1", "[三同号单选] ");
            sGroupInfo.put("3:2", "[三不同号] ");
            sGroupInfo.put("3:3", "[三连号通选] ");
            sGroupInfo.put("2:5", "[二同号复选] ");
            sGroupInfo.put("2:1", "[二同号单选] ");
            sGroupInfo.put("2:2", "[二不同号] ");
        }

        @Override
        protected SpannableStringBuilder parse(String text) {
            String strWithGroupInfo = getStrWithGroupInfo(text, sGroupInfo);
            SpannableStringBuilder ssb = setSpanWithVer(strWithGroupInfo.replaceAll(",", "  "));
            return ssb;
        }

        @Override
        protected boolean shouldFmtBuyContent() {
            return false;
        }
    }

    private class GJ extends Lottery {

        @Override
        protected SpannableStringBuilder parse(String text) {
            return null;
        }
    }

    //开奖号码的显示
    private SpannableStringBuilder setSpanAward() {
        if (TextUtils.isEmpty(mAwardNum)) return null;
        String content = mAwardNum.replaceAll(",", " ");
        if (LotCode.SSQ_CODE.equals(mLotId) || LotCode.DLT_CODE.equals(mLotId) || LotCode.QLC_CODE.equals(mLotId)) {
            String s = content.replaceAll(":", " | ");
            int indexOf = s.indexOf("|");
            SpannableStringBuilder ss = new SpannableStringBuilder(s);
            if (indexOf != -1) {
                setSpan(s, ss, RED_COLOR, 0, indexOf);
                setSpan(s, ss, GRAY_COLOR, indexOf, indexOf + 1);
                setSpan(s, ss, BLUE_COLOR, indexOf + 1, s.length());
            } else setSpan(s, ss, RED_COLOR, 0, s.length());
            return ss;
        } else {
            SpannableStringBuilder ss = new SpannableStringBuilder(content);
            for (int i = 0; i < content.length(); i++) {
                final char c = content.charAt(i);
                switch (c) {
                    case '|':
                        setSpan(content, ss, GRAY_COLOR, i, i + 1);
                        break;
                    case ' ':
                        break;
                    default:
                        setSpan(content, ss, RED_COLOR, i, i + 1);
                        break;
                }
            }
            return ss;
        }
    }

    public SpannableStringBuilder setSpanWithVer(String text) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        final int len = text.length();
        for (int i = 0; i < len; i++) {
            final char c = text.charAt(i);
            switch (c) {
                case '|':
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(GRAY_COLOR), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
               /* case ' ':
                    break;
                default:
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(BLACK_COLOR), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;*/
            }
        }
        if (!TextUtils.isEmpty(mAwardNum)) {
            //直选，或者排列五，七星彩，胜负彩，任九场。都是有顺序的
            if (text.contains("直") || "35".equals(mLotId) || "10022".equals(mLotId) || "11".equals(mLotId) || "19".equals(mLotId)) {
                String buy = text;
                int start = 0;
                if (text.contains("]")) {
                    buy = text.split("\\]  ")[1];
                    start = text.split("\\]  ")[0].length() + 3;
                }
                String[] buyContent = buy.split("\\|");
                String[] awardNum = mAwardNum.split("\\,");
                //排列三 直选  3,7,8|2,4|2
                for (int i = 0; i < awardNum.length; i++) {
                    LogUtils.e("award[" + i + "]=", awardNum[i]);
                    LogUtils.e("buyContent[" + i + "]=", buyContent[i]);
                    if (buyContent[i].contains(awardNum[i])) {
                        int testStart = start + buyContent[i].indexOf(awardNum[i]);
                        int testEnd = start + buyContent[i].indexOf(awardNum[i]) + awardNum[i].length();
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(RED_COLOR), testStart, testEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        start = start + buyContent[i].length() + 1;
                    } else if ("*".equals(awardNum[i])) {
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(RED_COLOR), start, start + buyContent[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        start = start + buyContent[i].length() + 1;
                    } else {
                        start = start + buyContent[i].length() + 1;
                    }
                }
            } else {
                String buy = text;
                int start = 0;
                if (text.contains("]")) {
                    buy = text.split("\\] ")[1];
                    start = text.split("\\] ")[0].length() + 2;
                }
                String[] buycontent = buy.split("\\ | ");
                for (int j = 0; j < buycontent.length; j++) {
                    if (mAwardNum.contains(buycontent[j])) {
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(RED_COLOR), start, start + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        start = start + buycontent[j].length() + 1;
                    } else {
                        start = start + buycontent[j].length() + 1;
                    }
                }
            }

        }
        return spannableStringBuilder;
    }

    /**
     * 字符串以逗号和竖线分割
     * 03,1,2,4这种
     *
     * @param text
     * @return
     */
    public String getStrSplitWithVerAndComma(String text) {
        final String string = text.replaceAll(",", "|");
        final String[] array = string.split("\\|");
        final StringBuilder sb = new StringBuilder();
        for (String entry : array) {
            final String e = Strings.join(entry, ",");
            sb.append(e).append("|");
        }
        if (sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());

        final String finalStr = sb.toString().replaceAll("\\|", " | ").replaceAll(",", " , ");
        return finalStr;
    }

    /**
     * 购买内容标准化
     * 1. 将04,17,24,33,34|03,04:1:1;04,08,28,29,32|04,06:1:1转换成
     * 04,17,24,33,34:03:04
     * 04,08,28,29,32:04:06
     *
     * @param buyContent
     * @return
     */
    private String[] buyContentFmtFirst(String buyContent) {
        if (TextUtils.isEmpty(buyContent)) return new String[]{buyContent};
        if (buyContent.indexOf(';') == -1) return new String[]{buyContent};
        final String[] array = buyContent.split(";");
        final List<String> stringList = Lists.newArrayList();
        for (String s : array) {
            stringList.add(s);
        }

        final String[] result = new String[stringList.size()];
        Lists.toArray(stringList, result);
        return result;
    }

    /**
     * 双色球和大乐透有红球和篮球区分
     *
     * @param text,该text必须这样的格式出现: 01,02,03,04,05,06:01,11 前面为红球，后面为蓝球
     * @return
     */
    protected SpannableStringBuilder parseToRedBlue(String text) {
        if (text.indexOf(':') == -1) {
            final String textContainsDt;
            if (text.indexOf('[') != -1 && text.indexOf(']') != -1 && text.indexOf('|') != -1)
                textContainsDt = parseDt(text);
            else
                textContainsDt = parseDt(text).replaceAll(",", " ");
            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textContainsDt);
            setSpan(textContainsDt, spannableStringBuilder, BLACK_COLOR, 0, textContainsDt.length());

            if (!TextUtils.isEmpty(mAwardNum)) {
                    /*直选和组选判断一个号码是否中奖需要分开处理
                    * */
                if (!textContainsDt.contains("直")) {
                    String[] awardnum = mAwardNum.split("\\,|\\:");
                    for (int i = 0; i < awardnum.length; i++) {
                        if ((spannableStringBuilder.toString()).contains(awardnum[i])) {
                            setSpan(textContainsDt, spannableStringBuilder, RED_COLOR, (spannableStringBuilder.toString()).indexOf(awardnum[i]), (spannableStringBuilder.toString()).indexOf(awardnum[i]) + awardnum[i].length());
                        }
                    }
                } else {
                    String buy = text;
                    int start = 0;
                    if (text.contains("]")) {
                        buy = text.split("\\]  ")[1];
                        start = text.split("\\]  ")[0].length() + 3;
                    }
                    String[] buyContent = buy.split("\\|");
                    String[] awardNum = mAwardNum.split("\\,|\\:");
                    //
                    for (int i = 0; i < buyContent.length; i++) {
                        LogUtils.e("award[" + i + "]=", awardNum[i]);
                        LogUtils.e("buyContent[" + i + "]=", buyContent[i]);
                        if (buyContent[i].contains(awardNum[i])) {
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(RED_COLOR), start + buyContent[i].indexOf(awardNum[i]), start + buyContent[i].indexOf(awardNum[i]) + awardNum[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            start = start + buyContent[i].length() + 1;
                        } else {
                            start = start + buyContent[i].length() + 1;
                        }
                    }
                }
            }


            return spannableStringBuilder;
        }
        final String[] array = text.split(":");
        if (array.length != 2) return null;

        final String red = parseDt(array[0]);

        final String[] reds = red.split(",");
        //final String[] blue  = array[1].split(":")[0].split(",");
        final String hasDt = parseDt(array[1]);
        final String[] blue;
        if (hasDt.contains("胆码")) {
            blue = hasDt.split(",");
        } else {
            blue = hasDt.split(":")[0].split(",");
        }
        final String redStr = Strings.join(reds, " ");
        final String blueStr = Strings.join(blue, " ");
        final String finalText = new StringBuilder().append(redStr).append(" ").append("|").append(" ").append(blueStr).toString();

        final String resultLotNum = finalText;
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(resultLotNum);

        // ----set first part--------
        final String ver = "|";
        final int index = resultLotNum.indexOf(ver);
        setSpan(resultLotNum, spannableStringBuilder, GRAY_COLOR, index, index + 1);
        if (TextUtils.isEmpty(mAwardNum)) {
            setSpan(resultLotNum, spannableStringBuilder, BLACK_COLOR, 0, index - 1);
            setSpan(resultLotNum, spannableStringBuilder, BLACK_COLOR, index + 2, resultLotNum.length());
        } else {
            String[] resultlot = resultLotNum.split("\\|");
            //将中奖号码的前面红球和篮球分开处理
            String[] split = mAwardNum.split("\\:");
            String[] awardNum0 = split[0].split("\\,");
            for (int i = 0; i < awardNum0.length; i++) {
                if (resultlot[0].contains(awardNum0[i])) {
                    setSpan(resultLotNum, spannableStringBuilder, RED_COLOR, resultlot[0].indexOf(awardNum0[i]), resultlot[0].indexOf(awardNum0[i]) + awardNum0[i].length());
                }
            }
            String[] awardNum1 = split[1].split("\\,");
            for (int i = 0; i < awardNum1.length; i++) {
                if (resultlot[1].contains(awardNum1[i])) {
                    setSpan(resultLotNum, spannableStringBuilder, BLUE_COLOR, index + 1 + resultlot[1].indexOf(awardNum1[i]), index + 1 + resultlot[1].indexOf(awardNum1[i]) + awardNum1[i].length());
                }
            }
        }
        return spannableStringBuilder;
    }

    /**
     * 解析胆拖
     *
     * @param text
     * @return //05,12,19,26$03,10,17,33|05$04,06:135:5
     */
    protected String parseDt(String text) {
        final String[] dt = text.split("\\$");
        if (dt.length == 2) {
            final StringBuilder sb = new StringBuilder();
            final Pattern pattern = Pattern.compile("(\\[.*?\\])(.*$)");
            final Matcher matcher = pattern.matcher(text);
            if (matcher.find() && matcher.groupCount() == 2) {
                final String group = matcher.group(1);
                if (!TextUtils.isEmpty(group)) sb.append(matcher.group(1));
                sb.append("胆码: ").append(matcher.group(2));
            } else {
                sb.append("胆码: ").append(dt[0]).append(" 拖码: ").append(dt[1]);
            }
            return sb.toString().replaceAll("\\$", " 拖码: ");
        } else {
            return text;
        }
    }

    private String sTripGroupStr;
    private Pattern sGgroupPattern = Pattern.compile("(.*):(\\d+:\\d+)$");

    public String getStrWithGroupInfo(String text, Map<String, String> map) {
        final StringBuilder sb = new StringBuilder();
        final String groupInfo = getGroupInfo(text, map);
        if (!TextUtils.isEmpty(groupInfo)) {
            sb.append(groupInfo).append(" ");
            if (!TextUtils.isEmpty(sTripGroupStr))
                sb.append(sTripGroupStr);
        }
        return sb.toString();
    }

    public String[] getGroupContent(String text) {
        final String[] result = new String[2];
        final Matcher matcher = sGgroupPattern.matcher(text);
        if (matcher.find() && matcher.groupCount() == 2) {
            result[0] = matcher.group(1);
            result[1] = matcher.group(2);
            return result;
        }
        return null;
    }

    public String getGroupInfo(String text, Map<String, String> map) {
        if (TextUtils.isEmpty(text) || map == null) return null;
        final Matcher matcher = sGgroupPattern.matcher(text);
        if (matcher.find() && matcher.groupCount() == 2) {
            sTripGroupStr = matcher.group(1);
            final String key = matcher.group(2);
            if (!TextUtils.isEmpty(key)) {
                return map.get(key);
            }
        }
        return null;
    }

    protected void setSpan(String src, SpannableStringBuilder spannableStringBuilder, int color, int start, int len) {
      /*  for (int i = start; i < len; i++) {
            if (src.charAt(i) != ' ')
                spannableStringBuilder.setSpan(new ForegroundColorSpan(color),
                        i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }*/
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), start, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
