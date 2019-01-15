package com.daxiang.lottery.common;

import com.daxiang.lottery.entity.ChoosedContentFormBean;

import java.util.ArrayList;

/**
 * @author yudonghui
 * @date 2017/8/21
 * @describe May the Buddha bless bug-free!!!
 */
public class MaxBunch {
    private ArrayList<String> playMethodList;

    public MaxBunch() {
        playMethodList = new ArrayList<>();
    }

    public int getMaxBunch(String type, ArrayList<ArrayList<ChoosedContentFormBean>> mList) {
        playMethodList.clear();
        for (int i = 0; i < mList.size(); i++) {
            ArrayList<ChoosedContentFormBean> mChildList = mList.get(i);
            for (int j = 0; j < mChildList.size(); j++) {
                ChoosedContentFormBean choosedContentFormBean = mChildList.get(j);
                String content = choosedContentFormBean.getContent();
                String playMethod;
                if ("jczq".equals(type))
                    playMethod = JcPlayMethod.getPlayMethod(content);
                else playMethod = JcPlayMethod.getPlayMethodJl(content);
                playMethodList.add(playMethod);
            }
        }
        if (playMethodList.contains("BQC") || playMethodList.contains("CBF") || playMethodList.contains("SFC"))
            return 4;
        if (playMethodList.contains("JQS")) return 6;
        return 8;
    }
}
