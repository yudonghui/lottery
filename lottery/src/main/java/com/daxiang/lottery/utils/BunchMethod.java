package com.daxiang.lottery.utils;

import android.text.TextUtils;

import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.entity.JclqData;
import com.daxiang.lottery.entity.JczqData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/13.
 */

public class BunchMethod {
    public static String getPlayMethod(String content, JczqData.DataBean bean) {
        //playMethod=1支持单关
        String playMethod;
        if (content.equals("主胜") || content.equals("平") || content.equals("主负")) {
            playMethod = "spf-" + bean.getSpfDg();
        } else if (content.equals("让胜") || content.equals("让平") || content.equals("让负")) {
            playMethod = "rqspf-" + bean.getRqspfDg();
        } else if (content.equals("胜胜") || content.equals("胜平") || content.equals("胜负")
                || content.equals("平胜") || content.equals("平平") || content.equals("平负")
                || content.equals("负胜") || content.equals("负平") || content.equals("负负")) {
            playMethod = "bqc-" + bean.getBqcDg();
        } else if (Strings.isNumeric(content) || content.contains("\\+")) {
            playMethod = "jqs-" + bean.getJqsDg();
        } else {
            playMethod = "bf-" + bean.getBfDg();
        }
        return playMethod;
    }

    public static boolean getPlayMethodBoolean(HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentMap) {
        boolean isDg = true;
        for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            HashMap<String, String> value = entry.getValue();
            JczqData.DataBean dataBean = entry.getKey();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                String content = entry1.getKey();
                if (content.equals("主胜") || content.equals("平") || content.equals("主负")) {
                    if (dataBean.getSpfDg() == 0) return false;
                } else if (content.equals("让胜") || content.equals("让平") || content.equals("让负")) {
                    if (dataBean.getRqspfDg() == 0) return false;
                } else if (content.equals("胜胜") || content.equals("胜平") || content.equals("胜负")
                        || content.equals("平胜") || content.equals("平平") || content.equals("平负")
                        || content.equals("负胜") || content.equals("负平") || content.equals("负负")) {
                    if (dataBean.getBqcDg() == 0) return false;
                } else if (Strings.isNumeric(content) || content.contains("\\+")) {
                    if (dataBean.getJqsDg() == 0) return false;
                } else {
                    if (dataBean.getBfDg() == 0) return false;
                }
            }
        }
        return isDg;
    }
    public static boolean getPlayMethodLqBoolean(HashMap<JclqData.DataBean, HashMap<String, String>> mChoosedContentMap) {
        boolean isDg = true;
        HashMap<String, Integer> isBunch = new HashMap<>();
        //遍历外层的hashmap
        for (Map.Entry<JclqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            HashMap<String, String> value = entry.getValue();
            //遍历内层的hashmap
            JclqData.DataBean dataBean = entry.getKey();
            String awary = dataBean.getGuestShortCn();
            String home = dataBean.getHomeShortCn();
            String let1 = dataBean.getLet();
            float let = Float.parseFloat(TextUtils.isEmpty(let1)?"0":let1);
            String mid = dataBean.getSession();
            ArrayList<ChoosedContentFormBean> list = new ArrayList<>();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                ChoosedContentFormBean mContentBean = new ChoosedContentFormBean();
                mContentBean.setAwary(awary);
                mContentBean.setHome(home);
                mContentBean.setContent(entry1.getKey());
                mContentBean.setLet(let);
                mContentBean.setMid(mid);
                mContentBean.setOdds(entry1.getValue());
                list.add(mContentBean);
                isBunch.put(BunchMethod.getPlayMethodLq(entry1.getKey(), dataBean), 0);
            }
        }
                    /*
                    * 如果isBunch只有一个元素，并且支持单关那么就可以显示单关。
                    * */
        if (isBunch.size() == 1) {
            for (Map.Entry<String, Integer> entry : isBunch.entrySet()) {
                String key = entry.getKey();
                String[] split = key.split("\\-");
                if (split[1].equals("1")) {
                    isDg = true;
                } else isDg = false;
            }
        } else isDg = false;
        return isDg;
    }
    public static String getPlayMethodLq(String content, JclqData.DataBean bean) {
        //playMethod=1支持单关
        String playMethod;
        if (content.equals("主胜[让]") || content.equals("客胜[让]")) {
            playMethod = "rfsf-" + bean.getRfsfDg();
        } else if (content.equals("大分") || content.equals("小分")) {
            playMethod = "dxf-" + bean.getDxfDg();
        } else if (content.equals("客胜") || content.equals("主胜")) {
            playMethod = "sf-" + bean.getSfDg();
        } else {
            playMethod = "sfc-" + bean.getSfcDg();
        }
        return playMethod;
    }
}
