package com.daxiang.lottery.cxinterface;


import com.daxiang.lottery.entity.JczqData;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public interface OnClickJczqListener {
    void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap);
}
