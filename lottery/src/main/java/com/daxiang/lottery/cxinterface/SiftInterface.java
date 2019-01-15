package com.daxiang.lottery.cxinterface;


import com.daxiang.lottery.entity.JczqData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public interface SiftInterface {
    void siftCallBack(ArrayList<ArrayList<JczqData.DataBean>> mListData, ArrayList<String> clickList, boolean siftFlag);
}
