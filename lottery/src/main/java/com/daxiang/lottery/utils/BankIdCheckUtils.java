package com.daxiang.lottery.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yudonghui
 * @date 2017/10/9
 * @describe May the Buddha bless bug-free!!!
 */
public class BankIdCheckUtils {
    /**
     * 银行卡号校验
     *
     * @param cardNo
     * @return
     */
    public static boolean creditCardCheck(String cardNo) {
        String number = cardNo.replace(" ", "").trim();
        int sumOdd = 0, sumEven = 0;
        int length = number.length();
        int[] elem = new int[length];
        for (int i = 0; i < number.length(); i++) {
            elem[i] = Integer.parseInt(number.substring(length - i - 1, length - i));// 从最末一位开始提取，每一位上的数值
            Log.e("第" + i + "位数字是：", "" + elem[i]);
        }
        for (int i = 0; i < length / 2; i++) {
            sumOdd += elem[2 * i];
            if ((elem[2 * i + 1] * 2) > 9)
                elem[2 * i + 1] = elem[2 * i + 1] * 2 - 9;
            else
                elem[2 * i + 1] *= 2;
            sumEven += elem[2 * i + 1];
        }
        Log.e("奇数位的和是：", "" + sumOdd);
        Log.e("偶数位的和是：", "" + sumEven);
        if ((sumOdd + sumEven) % 10 == 0)
            return true;
        else
            return false;
    }

    /**
     * 银行卡号校验
     * Luhn校验规则：16位银行卡号(19位通用)
     * 1.将未带校验位的 15(或18)位卡号从右依次编号 1 到 15(18)，位于奇数位号上的数字乘以 2。
     * 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
     * 3.将加法和加上校验位能被 10 整除。
     *
     * @param cardNo
     * @return
     */
    public static boolean luhnCheck(String cardNo) {
        if (cardNo.length() < 16 || cardNo.length() > 19) {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(cardNo);
        if (match.matches() == false) {
            return false;
        }

        String NumExcludeEnd = cardNo.substring(0, cardNo.length() - 1); // 前15或18位
        char[] ExcludeEnd = new char[NumExcludeEnd.length()]; // ExcludeEnd
        char[] tempArr = NumExcludeEnd.toCharArray();
        for (int i = 0; i < tempArr.length; i++) {
            ExcludeEnd[tempArr.length - 1 - i] = tempArr[i];
        }

        int[] oddNumSmall = new int[ExcludeEnd.length]; // 奇数位 * 2的积 <9
        int[] oddNumLarge = new int[ExcludeEnd.length]; // 奇数位 * 2的积 >9
        int[] evenNum = new int[ExcludeEnd.length]; // 偶数位数组
        for (int j = 0; j < ExcludeEnd.length; j++) {
            if ((j + 1) % 2 == 1) { // 奇数位
                if ((ExcludeEnd[j] - 48) * 2 < 9)
                    oddNumSmall[j] = (ExcludeEnd[j] - 48) * 2;
                else
                    oddNumLarge[j] = (ExcludeEnd[j] - 48) * 2;
            } else // 偶数位
                evenNum[j] = ExcludeEnd[j] - 48;
        }

        int[] oddNumChild = new int[ExcludeEnd.length]; // 奇数位 * 2 >9 的分割之后的数组个位数
        int[] oddNum2Child = new int[ExcludeEnd.length]; // 奇数位 * 2 >9 的分割之后的数组十位数
        for (int h = 0; h < oddNumLarge.length; h++) {
            oddNumChild[h] = (oddNumLarge[h]) % 10;
            oddNum2Child[h] = (oddNumLarge[h]) / 10;
        }

        int sumOddNum = 0; // 奇数位*2 < 9 的数组之和
        for (int m = 0; m < oddNumSmall.length; m++) {
            sumOddNum = sumOddNum + oddNumSmall[m];
        }

        int sumEvenNum = 0; // 偶数位数组之和
        for (int n = 0; n < evenNum.length; n++) {
            sumEvenNum = sumEvenNum + evenNum[n];
        }

        int sumOddNumChild = 0; // 奇数位 * 2 >9 的分割之后的数组个位数之和
        int sumOddNum2Child = 0; // 奇数位 * 2 >9 的分割之后的数组十位数之和
        for (int p = 0; p < oddNumChild.length; p++) {
            sumOddNumChild = sumOddNumChild + oddNumChild[p];
            sumOddNum2Child = sumOddNum2Child + oddNum2Child[p];
        }

        int sumTotal = sumOddNum + sumEvenNum + sumOddNumChild + sumOddNum2Child;

        // 计算Luhn值
        int k = sumTotal % 10 == 0 ? 10 : sumTotal % 10;
        int luhn = 10 - k;
        // 取出最后一位(与luhn进行比较)
        int lastNum = Integer.parseInt(cardNo.substring(cardNo.length() - 1, cardNo.length()));
        if (lastNum == luhn) {
            return true; // 验证通过
        } else {
            return false;
        }
    }
}
