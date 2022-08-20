package com.iss.team1.safe.checkin.utils;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;
import android.util.Base64;

/**
 * Author: rongze
 * Date: 16/08/2022
 */
public class HashUtil {
    public static final String SHA256 = "SHA-256";
    private static final String SALT = "dshfiweuhrwekfh923bsdkjbsd";

    private HashUtil() {
        // do nothing
    }

    /**
     * Hashes the {@link InputStream} based on the <code>algorithm</code> passed.
     * @param algorithm
     * @param fis the InputStream to calculate the hash
     * @return the hash in hex format.
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String hash(String algorithm, InputStream fis) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);

        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();

    }

    /**
     * A lazy method to calculate the hash-256 of the {@link InputStream}
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String hashSha256(String str) throws NoSuchAlgorithmException, IOException {
        InputStream is = new ByteArrayInputStream(str.getBytes());
        return hash(SHA256, is);
    }

    public static String deCrypt(String encryptStr) {
        byte[] data = encryptStr.getBytes();
        data = Base64.decode(data, Base64.DEFAULT);
        return new String(data);
    }

}
