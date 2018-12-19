package com.example.demo.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 签名生产和校验的类
 *
 * @author WuXL
 * @create 2018-11-21 16:13
 */

public class SignUtils {

    private static Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 将json字符串转化为一个单层map
     *
     * @param json
     * @return
     */
    public static Map<String, String> parseJSONMap(String json) {
        Map jsonMap = new HashMap<>();
        parseJSON2Map(jsonMap, checkFormat(json), null);
        return jsonMap;
    }

    /**
     * 递归查询
     *
     * @param jsonMap
     * @param jsonStr
     * @param parentKey
     */
    public static void parseJSON2Map(Map jsonMap, String jsonStr, String parentKey) {
        //字符串转换成JSON对象
        JSONObject json = JSON.parseObject(jsonStr);
        json.remove("logFiles");
        //最外层JSON解析
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //构造一个包含上层keyName的完整keyName
            String fullKey = (null == parentKey || parentKey.trim().equals("") ? k.toString() : parentKey + "." + k);
            if (v instanceof JSONArray) {
                //如果内层还是数组的话，继续解析
                List listV = JSONArray.toJavaObject((JSONArray) v, List.class);
                for (int i = 0; i < listV.size(); i++) {
                    String fullKey1 = fullKey + '[' + i + ']';
                    parseJSON2Map(jsonMap, listV.get(i).toString(), fullKey1);
                }
            } else if (isNested(v)) {
                parseJSON2Map(jsonMap, v.toString(), fullKey);
            } else {
                jsonMap.put(fullKey, v);
            }
        }
    }

    public static String checkFormat(String str) {
        return str.trim();
    }

    public static boolean isNested(Object jsonObj) {
        return jsonObj.toString().contains("{");
    }

    public static String getApiSignContent(Map<String, String> params) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));
            if (!"sign".equalsIgnoreCase(key)) {
                if (StringUtils.isNotEmpty(key)
                        && StringUtils.isNotEmpty(value)) {
                    content.append((index == 0 ? "" : "&") + key + "=" + value);
                    index++;
                }
            }
        }
        return content.toString();
    }


    /**
     * @param strJson
     * @param secret
     * @param vin
     * @return
     */
    public static boolean validSign(String strJson, String secret, String vin) {

        //将jsonStr转换为Map
        Map<String, String> map = parseJSONMap(strJson);

        //请求参数中的签名
        String sign = map.get("sign");

        //获得签名生成前的字符串
        String content = getApiSignContent(map);

        //生成的签名sign
        String generateSign = "";
        //Codec2.hexString(secret, content);

        //比较签名
        if (generateSign.equals(sign)) {
            return true;
        } else {
            logger.info("vin:{}验签失败;状态码:{},签名的content:{},secret:{},生成的sign:{},上报的sign:{}", vin, content, secret, generateSign, sign);
            return false;
        }

    }


    public static void main(String[] args) {
        String strJson = "{\"timestamp\":1529724024,\"sign\":\"769ecc15719c556e8ebe1f2a1f2d4240\",\"networkInfo\":{\"netType\":\"wifi\",\"lac\":\"0210\",\"cid\":\"\"},\"ecus\":[{\"ecuPartNum\":\"ecu1\",\"swId\":\"\",\"ecuSVer\":\"v1\"},{\"ecuPartNum\":\"ecu2\",\"swId\":\"3333\",\"productDate\":\"2018-05-22\"}]}\n";
        validSign(strJson, "123", "dddd");
    }


}
