package com.iss.team1.safe.checkin.utils;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: zhang rongze
 * Date: 11/08/2022
 */

public class HttpUtil {
    /*
     * Method Name：jsonPost(URL url, JSONObject param)
     * Function：Send http request to server through post way. The params is saved on json data. And post the data to server.
     * Params：URL url - http request URL
     *         JSONObject param - the json params will be sent to server
     * Return：String
     */
    public static String jsonPost(String urlStr, String param) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(3000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

//        String request = param.toString();
        OutputStream out = conn.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

        bw.write(param);
        bw.flush();
        out.close();
        bw.close();

        int code = conn.getResponseCode();
        //If app connect to server successfully, get the response from server.
        if(code == HttpURLConnection.HTTP_OK){
            InputStream is = conn.getInputStream();
            String result = StreamUtil.readStream(is);
            conn.disconnect();
            return result;
        }else {
            InputStream errorIs = conn.getErrorStream();
            conn.disconnect();
            String result = StreamUtil.readStream(errorIs);
            System.out.println("Server error");
            return result;
        }
    }
}
