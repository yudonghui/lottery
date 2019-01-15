package com.daxiang.lottery.utils;

import java.util.Random;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class RandomUtils {
    public static String createSalt() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int time = 0;
        while (time < 6) {
            switch (random.nextInt(3)) {
                case 0:
                    int num = random.nextInt(10);
                    sb.append(num);
                    break;
                case 1:
                    sb.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    sb.append((char) (random.nextInt(26) + 97));
                    break;
            }
            time++;
        }
        return sb.toString();

    }

}
