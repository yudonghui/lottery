package com.daxiang.lottery.cxinterface;


import com.daxiang.lottery.entity.JclqData;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public interface OnClickJclqListener {
    void onClickJcListener(HashMap<JclqData.DataBean, HashMap<String, String>> clickMap);
}
