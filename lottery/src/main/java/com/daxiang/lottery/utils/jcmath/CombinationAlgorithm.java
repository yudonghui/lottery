package com.daxiang.lottery.utils.jcmath;

import java.math.BigInteger;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public interface CombinationAlgorithm {
    public int getMaxSupportedSize();

    public BigInteger getCombinationCount(int numberOfElements, int numberToPick);

    public void fetchCombination(Object[] source, Object[] target, BigInteger ordinal);
}
