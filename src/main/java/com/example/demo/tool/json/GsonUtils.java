package com.example.demo.tool.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * Created by raise.yang on 18/08/01.
 */
public class GsonUtils {

    private static Gson sGson;

    public static Gson createGson() {
        if (sGson == null) {
            synchronized (GsonUtils.class) {
                if (sGson == null) {
                    sGson = new GsonBuilder()
                            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                            .create();
                }
            }
        }
        return sGson;
    }

    public static String toJson(Object obj) {
        return createGson().toJson(obj);
    }
}
