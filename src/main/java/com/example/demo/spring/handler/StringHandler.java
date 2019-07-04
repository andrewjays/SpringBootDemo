package com.example.demo.spring.handler;

import java.util.ArrayList;
import java.util.List;

public class StringHandler {
    /**
     * 将字符串按charsPerSeg等分成n段
     * @param str
     * @param charsPerSeg
     * @return
     */
    public static List<String> splitString(String str, int charsPerSeg) {
        List<String> strList = new ArrayList<String>();
        int totalSegments = 0;//总段数
        int totalLength = str.length();//文本长度
        int remainder=totalLength%charsPerSeg;//余数
        totalSegments = totalLength/charsPerSeg+(remainder>0?1:0);
        for(int i=0;i<totalSegments;i++){
            strList.add((i==totalSegments-1)?str.substring(i*charsPerSeg):str.substring(i*charsPerSeg,(i+1)*charsPerSeg));
        }
        return strList;
    }
}
