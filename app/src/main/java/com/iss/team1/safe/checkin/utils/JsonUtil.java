package com.iss.team1.safe.checkin.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

public class JsonUtil {
    public static Object convertJsonToObj(String jsonStr, Class objClass) {
        if(TextUtils.isEmpty(jsonStr)){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonStr,objClass);
    }

    public static String convertObjToStr(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
