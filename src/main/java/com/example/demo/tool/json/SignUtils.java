package com.example.demo.tool.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签名生产和校验的类
 *
 * @author WuXL
 * @create 2018-11-21 16:13
 */

public class SignUtils {

    private static Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 转换json拼接为全字段字符串
     *
     * @param json
     * @return
     */
    public static Map<String, String> getConvertContent(String json) {
        // 将json字符串转化为一个单层Map
        Map params = new HashMap<>();
        Map<String, String> result = new HashMap<>(2);
        parseJSON2Map(params, checkFormat(json), null);
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));
            if (!"sign".equalsIgnoreCase(key)) {
                if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                    content.append((index == 0 ? "" : "&") + key + "=" + value);
                    index++;
                }
            } else {
                result.put("sign", value.replaceAll("\"", ""));
            }
        }
        result.put("content", content.toString().replaceAll("\"", ""));
        return result;
    }

    /**
     * 递归，转换json字符串为单层map<string,string>
     *
     * @param jsonMap
     * @param jsonStr
     * @param parentKey
     */
    public static void parseJSON2Map(Map jsonMap, String jsonStr, String parentKey) {
        //字符串转换成JSON对象
        JsonObject json = new JsonParser().parse(jsonStr).getAsJsonObject();
        json.remove("logFiles");
        //最外层JSON解析
        for (Object k : json.keySet()) {
            Object v = json.get((String) k);
            //构造一个包含上层keyName的完整keyName
            String fullKey = (null == parentKey || parentKey.trim().equals("") ? k.toString() : parentKey + "." + k);
            if (v instanceof JsonArray) {
                //如果内层还是数组的话，继续解析


                Gson gson = GsonUtils.createGson();

                List listV = gson.fromJson(gson.toJson(v), new TypeToken<List>() {
                    }.getType());


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

    //    /**
    //     *
    //     * @param strJson
    //     * @param secret
    //     * @return
    //     */
    //    public static boolean validSign(String strJson, String secret){
    //
    //        //获得签名生成前的字符串
    //
    //        Map<String, String> result = getConvertContent(strJson);
    //        String sign = result.get("sign");
    //        System.out.println(sign);
    //        String content = result.get("content");
    //        //生成的签名sign
    //        String generateSign = Codec2.hexString(secret, content);
    //        //比较签名
    //        if(generateSign.equals(sign)){
    //            return true;
    //        }else{
    //            logger.info("验签失败;状态码:{},签名的content:{},secret:{},服务端生成的sign:{},上报的sign:{}",
    //                    ResultCode.SIGN_AUTHENTICATION_FAIL_CODE,content,secret,generateSign,sign);
    //            throw new BusinessException(ResultCode.SIGN_AUTHENTICATION_FAIL_CODE,ResultCode.SIGN_AUTHENTICATION_FAIL_MSG
    //                    +":服务端生成的sign:"+generateSign+",上报的sign:"+sign);
    //        }
    //    }
    //
    //    public static void main(String[] args) {
    //        String strJson = "{\"ecus\": [{\"ecuDid\": \"6C9\",\"ecuHsn\": \"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\",\"ecuPartNum\": \"2136100XNZ01A\",\"ecuSVer\": \"SW.S.3\",\"ecuSwid\": \"VSG\",\"tieroneName\": \"CADTZ\"}],\"sdkVersion\": \"ec01-1.0.58\",\"sign\": \"f861b4393eb0376e151a2b4db00b852a\",\"timestamp\": 1514740860,\"vin\": \"oraupdate12345677\"}";
    //        validSign(strJson, "b2ac1ec1ae77b60910dfca290e6c406a");
    //
    //    }


    public static void main(String[] args) {
        String strJson = "{\n" +
                "    \"deviceId\": \"2cd3a703eb9a7c3bf4979dbf66831733\", \n" +
                "    \"event\": [\n" +
                "        {\n" +
                "            \"createTime\": 1555472317, \n" +
                "            \"debugLevel\": \"INFO\", \n" +
                "            \"eventDescription\": \"注册准备成功\", \n" +
                "            \"eventId\": \"REGISTER_PREPARE_OK\", \n" +
                "            \"subEvent\": \"subEvent\"\n" +
                "        }\n" +
                "    ], \n" +
                "    \"sdkVersion\": \"v1\", \n" +
                "    \"sessionId\": \"1555472317461ukd2\", \n" +
                "    \"timestamp\": 15500\n" +
                "}\n";


        Map<String, String> convertContent = getConvertContent(strJson);
        System.out.println(convertContent);

    }
}
