package com.daxiang.lottery.utils.jcmath;

import java.math.BigInteger;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class Calculator {
    private Calculator() {}

    private static final BigInteger[] FACT_RESULT_POOL = new BigInteger[1024];
    public static BigInteger factorial(int number) {

        if( number < 0 )
            throw new IllegalArgumentException();

        BigInteger result = null;

        if( number < FACT_RESULT_POOL.length )
            result = FACT_RESULT_POOL[number];

        if( result == null ) {

            result = BigInteger.ONE;
            for(int i = 2; i <= number; i++)
                result = result.multiply(BigInteger.valueOf(i));
            if( number < FACT_RESULT_POOL.length )
                FACT_RESULT_POOL[number] = result;
        }
        return result;
    }
}
