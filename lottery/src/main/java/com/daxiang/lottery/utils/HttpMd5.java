package com.daxiang.lottery.utils;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class HttpMd5 {
    private static String key="ODjmrKIPfu8wlCGLyYtNDK4T8Atk5PUm0DKGQDfAbKj//hfNeDlNGbQqsV9Kp4eDMAQJxr9lCilevUvY5tYmyg==";
    public static String buildSign(Map<String, Object> map) {
       /* if (!map.containsKey("timeStamp"))
            map.put("timeStamp", System.currentTimeMillis());*/
        StringBuffer buf = new StringBuffer();
        String sign;
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                buf.append(entry.getKey() + "=" + entry.getValue() + "&");
                
                
            }
            buf.delete(buf.length() - 1, buf.length());
            if (StringUtil.isNullOrEmpty(key)) {
                return null;
            } else {
                sign = StringUtil.getMD5(key + buf);
            }
        } catch (Exception e) {
            return null;
        }
        return sign;
    }
}
