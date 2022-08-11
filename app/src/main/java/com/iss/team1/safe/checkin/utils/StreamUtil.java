package com.iss.team1.safe.checkin.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Author: zhang rongze
 * Date: 11/08/2022
 */
public class StreamUtil {
    /*
     * Method Name：readStream(InputStream is)
     * Function：Convert the the InputStream to String
     * Params：InputStream is - the InputStream from server side needs to be handled
     * Return：String
     */
    public static String readStream(InputStream is){
        byte[] result;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len = 0;
            while(( len = is.read(buffer))!=-1){
                baos.write(buffer, 0, len);
            }
            is.close();
            baos.close();
            result = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new String(result);
    }

    /*
     * Method Name：getJSONArryResult(InputStream is)
     * Function：Convert the the String to JSONObject.
     * Params：InputStream is - the InputStream from server side needs to be handled
     * Return：JSONArray
     */
    public static JSONArray getJSONArryResult(InputStream is) throws JSONException {

        String result = readStream(is);
        System.out.println("JSONString:" + result);
        JSONArray array = new JSONArray(result.toString());
        System.out.println("JSON:" + array);
        return array;
    }
}
