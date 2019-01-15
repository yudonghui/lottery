package com.daxiang.lottery.utils;


import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import com.daxiang.lottery.R;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String hintString(String name) {
        if (name == null) return "--";
        String userName = "";
        switch (name.length()) {
            case 1:
                userName = name;
                break;
            case 2:
                String c = String.valueOf(name.charAt(0));
                userName = c + "***";
                break;
            default:
                String c1 = String.valueOf(name.charAt(0));
                String c2 = String.valueOf(name.charAt(1));
                String c3 = String.valueOf(name.charAt(2));
                int count = isChinese(name.substring(0, 2));
                if (count == 2) {
                    userName = c1 + c2 + "***";
                } else
                    userName = c1 + c2 + c3 + "***";
                break;
        }
        return userName;
    }

    public static int isChinese(String str) {//判断是否是中文。有几个中文字符
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }

    /**
     * check the string is null or just contains the empty character
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNullOr0Size(String str) {
        return str == null || str.length() == 0;
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize =
                (arraySize == 0 ? 0
                        : ((array[0] == null ? 16 : array[0].toString().length()) + separator
                        .length()) * arraySize);
        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(String separator, Object... args) {
        return join(args, separator);
    }

    public static String getMD5(String str) {
        return Hashing.md5().hashString(str, Charsets.UTF_8).toString().toLowerCase();
    }

    public static int nthOccurrence(String str, char c, int n) {
        int pos = str.indexOf(c);
        while (--n > 0 && pos != -1) {
            pos = str.indexOf(c, pos + 1);
        }
        return pos;
    }

    public static boolean isLetterOrDigitOrUnderScore(String str) {
        for (int i = 0, len = str.length(); i < len; ++i) {
            char c = str.charAt(i);
            if (!((c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A') || (c <= '9' && c >= '0') || c == '_')) {
                return false;
            }
        }

        return true;
    }


    public static String trim(String source) {
        if (source != null) {
            return source.trim();
        }
        return null;
    }

    public static boolean isBlank(String str) {
        return (str == null) || ("".equalsIgnoreCase(str.trim()));
    }

    /*
     * if str is null or "" return null;
     */
    public static String getConent(String str) {
        String result = null;
        if ((str == null) || ("".equalsIgnoreCase(str.trim()))) {

        } else {
            result = str.trim();
        }
        return result;
    }

    public static int getInt(String source) {
        int result = -1;
        if (source == null || "".equalsIgnoreCase(source.trim())) {
            return result;
        }

        try {
            result = Integer.valueOf(source.trim());
        } catch (Exception e) {
        }
        return result;
    }

    public static long getLong(String source) {
        long result = -1;
        if (source == null || "".equalsIgnoreCase(source.trim())) {
            return result;
        }

        try {
            result = Long.valueOf(source.trim());
        } catch (Exception e) {
        }
        return result;
    }

    public static String generateRandString(Integer numOfChars) {
        String randString = "";
        String charPool = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();

        if (numOfChars == 4 || numOfChars == null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb.append(charPool.charAt(random.nextInt(charPool.length())));
            }
            randString = sb.toString();
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < numOfChars; i++) {
                sb.append(charPool.charAt(random.nextInt(10)));
            }
            randString = sb.toString();
        }

        return randString;
    }

    public static String getCheckcode(int type) throws Exception {
        // phone checkcode
        if (type == 1) {
            String phonecheckCode = StringUtil.generateRandString(6);
            return phonecheckCode;
        }
        // webpage checkcode
        else if (type == 2) {
            String checkCode = StringUtil.generateRandString(4);
            return checkCode;
        }
        // email checkcode
        else if (type == 3) {

        }
        return null;
    }

    public static boolean intConvertable(String str) {
        int temp = 0;
        try {
            temp = Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean longConvertable(String str) {
        Long temp = 0L;
        try {
            temp = Long.parseLong(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String convertObjectToString(Object object) {
        String rtn = "";
        if (object instanceof Integer) {
            rtn = String.valueOf(((Integer) object).intValue());
        } else if (object instanceof String) {
            rtn = (String) object;
        } else if (object instanceof Double) {
            rtn = String.valueOf(((Double) object).doubleValue());
        } else if (object instanceof BigDecimal) {
            rtn = String.valueOf(((BigDecimal) object).doubleValue());
        } else if (object instanceof Float) {
            rtn = String.valueOf(((Float) object).floatValue());
        } else if (object instanceof Long) {
            rtn = String.valueOf(((Long) object).longValue());
        } else if (object instanceof Boolean) {
            rtn = String.valueOf(((Boolean) object).booleanValue());
        } else if (object instanceof Date) {
            rtn = object.toString();
        }
        return rtn;
    }

    //判断用户名是否合法，必须是字母，数字，下划线的组合。合法true   不合法flase
    public static boolean isLegalUsername(String username) {
        String regex = "(?:\\d.*_)|(?:_.*\\d)|(?:[A-Za-z].*_)|(?:_.*[A-Za-z])|(?:[A-Za-z].*\\d)|(?:\\d.*[A-Za-z])";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        boolean log = m.matches() && username.length() >= 6 && username.length() <= 24;
        return log;
    }

    //判断密码是否合法。字母，数字，或者特殊符号。（6-24位）合法true   不合法flase
    public static boolean isLegalPassword(String pwd) {
        String regex = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_`~!@#$%^*&()_-+=?";
        for (int i = 0; i < pwd.length(); i++) {
            if (!regex.contains(String.valueOf(pwd.charAt(i))))
                return false;
        }
        return pwd != null && pwd.length() >= 6
                && pwd.length() <= 24;
    }

    //判断是否为手机号   合法true   不合法flase
    public static boolean isLegalPhone(String phone) {
        String reg = "^1[3-9][0-9]{9}$";
        return Pattern.matches(reg, phone);
    }

    //type为0时候判断手机号是否合法。1时候判断是否是6-24位
    public static boolean isLegalPhoneNew(String phone, int type) {
        if (type == 0) {
            String reg = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";
            return Pattern.matches(reg, phone);
        } else {
            return phone != null && phone.length() >= 6
                    && phone.length() <= 24;
        }
    }

    //校验用户名的格式是否是默认格式（CX_123456abc）
    public static boolean isChang(String userName) {
        if (TextUtils.isEmpty(userName))
            return false;
        String reg = "CX_[0-9]{6}[a-z]{3}";
        return Pattern.matches(reg, userName);
    }

    //转换成三位一个逗号
    public static String getString(String str) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(Double.parseDouble(str));
    }

    public static JSONObject string2JSON(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static String toJSONString(Object obj) {
        JSONObject json = new JSONObject();
        try {
            Map<String, String> map = bean2Parameters(obj);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 将bean转换成键值对列表
     *
     * @param bean
     * @return
     */
    public static Map<String, String> bean2Parameters(Object bean) {
        if (bean == null) {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();

        if (null != parameters) {
            // 取得bean所有public 方法
            Method[] Methods = bean.getClass().getMethods();
            for (Method method : Methods) {
                if (method != null && method.getName().startsWith("get")
                        && !method.getName().startsWith("getClass")) {
                    // 得到属性的类名
                    String value = "";
                    // 得到属性值
                    try {
                        String className = method.getReturnType().getSimpleName();
                        if (className.equalsIgnoreCase("int")) {
                            int val = 0;
                            try {
                                val = (Integer) method.invoke(bean);
                            } catch (InvocationTargetException e) {
                                Log.e("InvotionTargetException", e.getMessage(),
                                        e);
                            }
                            value = String.valueOf(val);
                        } else if (className.equalsIgnoreCase("String")) {
                            try {
                                value = (String) method.invoke(bean);
                            } catch (InvocationTargetException e) {
                                Log.e("InvotionTargetException", e.getMessage(), e);
                            }
                        }
                        if (value != null && value != "") {
                            // 添加参数
                            // 将方法名称转化为id，去除get，将方法首字母改为小写
                            String param = method.getName().replaceFirst("get", "");
                            if (param.length() > 0) {
                                String first = String.valueOf(param.charAt(0)).toLowerCase();
                                param = first + param.substring(1);
                            }
                            parameters.put(param, value);
                        }
                    } catch (IllegalArgumentException e) {
                        Log.e("IllegargumentException", e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        Log.e("IllegalAccessException", e.getMessage(), e);
                    }
                }
            }
        }

        return parameters;
    }

    public static String phoneHint(String phone) {
        if (phone == null) return "";
        if (phone.length() < 8) {
            return phone;
        }
        String start = phone.substring(0, 3);
        String end = phone.substring(phone.length() - 4, phone.length());
        return start + "****" + end;
    }

    public static SpannableString getSpannableString(Context mContext, String lotcode, String playMethod, int minNum) {
        String hint;
        SpannableString ss;
        if ("36".equals(lotcode)) {
            switch (playMethod) {
                case "1":
                    hint = "猜开奖号码的和\n猜中奖金9~240元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 4, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 6, hint.length() - 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
                case "2":
                    hint = "三同号单选\n猜3个相同的号  奖金240元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 4, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
                case "3":
                    hint = "猜3个不同的号  奖金40元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 3, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
                case "4":
                    hint = "开出任意1组即中奖  奖金10元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 3, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
                case "5":
                    hint = "同号\n猜同号不同号的组合  奖金80元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 3, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
                case "6":
                    hint = "猜2个相同号  奖金15元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 3, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
                case "7"://二不同号
                    hint = "至少选择2个号\n若猜中开奖号码任意2个中奖8元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 2, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
                case "8":
                    hint = "三同号通选\n猜中任意一组即中奖 奖金40元";
                    ss = new SpannableString(hint);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 3, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new RelativeSizeSpan(1.2f), 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    return ss;
            }
        } else if ("50".equals(lotcode)) {
            if ("1:1".equals(playMethod)) {
                hint = "至少选择1个号码，猜中开奖号码最后1位即中奖10元";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 3, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("2:1".equals(playMethod)) {
                hint = "每位至少选1个号码，按位猜中开奖号码后2位即中奖100元";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 4, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("6:1".equals(playMethod)) {
                hint = "选择2至6个号码，猜中开奖号码后2位即中奖50元(不包含对子)";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 10, hint.length() - 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("3:1".equals(playMethod)) {
                hint = "每位至少选1个号码，按位猜中开奖号码后3位即中奖1000元";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 5, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("7:3".equals(playMethod)) {
                hint = "至少选择2个号码，猜中开奖号码后3位(有2位相同)即中奖320元";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 4, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("8:3".equals(playMethod)) {
                hint = "至少选择3个号码，猜中开奖号码后3位(顺序不限)即中奖160元";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 4, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("5:1".equals(playMethod)) {//五星直选
                hint = "每位至少选1个号码，按位猜中开奖号码全部5个数即中奖100000元";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 7, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("9:1".equals(playMethod)) {//五星通选
                hint = "每位选1个号码, 与开奖号码完全一致中20440元; 与前三或后三位完全一致中220元; 与前二或后二位完全一致中20元";
                int indexOf1 = hint.indexOf("20440");
                int indexOf2 = hint.indexOf("220");
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf1, indexOf1 + 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf2, indexOf2 + 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 3, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("10:1".equals(playMethod)) {
                hint = "每位选择1个属性，猜中开奖号码后2位的大小单双属性即中奖4元";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), hint.length() - 2, hint.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            }
        } else if ("21406".equals(lotcode)) {
            if ("09:01".equals(playMethod) || "10:01".equals(playMethod)) {
                hint = "每位至少选1个号";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else {
                hint = "至少选择" + minNum + "个号";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 4, 4 + (minNum + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            }
        } else if ("33".equals(lotcode) || "52".equals(lotcode)) {
            if ("1:1".equals(playMethod)) {
                hint = "每位至少选1个号";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("2:3".equals(playMethod)) {//组三
                hint = "至少选择2个号";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else if ("3:3".equals(playMethod)) {// 组六
                hint = "至少选择2个号";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            }
        } else if ("23529".equals(lotcode)) {
            if ("1:1".equals(playMethod) || "2:1".equals(playMethod)) {
                hint = "至少选择5个红球2个蓝球";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_txt)), 8, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else {
                hint = "前区至少选择1个胆码5个拖码，后区至少选择2个拖码";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 10, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_txt)), hint.length() - 4, hint.length() - 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            }
        } else if ("51".equals(lotcode)) {
            if ("1:1".equals(playMethod)) {
                hint = "至少选择6个红球1个蓝球";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_txt)), 8, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else {
                hint = "前区至少选择1个胆码6个拖码，后区至少选择1个蓝球";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 10, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_txt)), hint.length() - 4, hint.length() - 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            }
        } else if ("35".equals(lotcode) || "10022".equals(lotcode)) {
            hint = "每位至少选择1个号码";
            ss = new SpannableString(hint);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return ss;
        } else if ("23528".equals(lotcode)) {
            if ("1:1".equals(playMethod)) {
                hint = "请至少选择7个号码";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            } else {
                hint = "至少选择1个胆码7个拖码";
                ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 8, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return ss;
            }

        } else {
            hint = "至少选择" + minNum + "个号";
            ss = new SpannableString(hint);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), 4, 4 + (minNum + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return ss;
        }
        return new SpannableString("");
    }

    public static String getNumber(String args) {
        if (TextUtils.isEmpty(args))return "";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(args);
        return m.replaceAll("").trim();
    }
}