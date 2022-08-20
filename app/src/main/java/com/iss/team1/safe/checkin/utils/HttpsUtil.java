package com.iss.team1.safe.checkin.utils;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Author: zhang rongze
 * Date: 11/08/2022
 */

public class HttpsUtil {
    /*
     * Method Name：jsonPost(URL url, JSONObject param)
     * Function：Send http request to server through post way. The params is saved on json data. And post the data to server.
     * Params：URL url - http request URL
     *         JSONObject param - the json params will be sent to server
     * Return：String
     */
    public static String jsonPost(String urlStr, String param) throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[] {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                }
        }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(new AllowAllHostnameVerifier());
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
