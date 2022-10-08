package com.iss.team1.safe.checkin.utils;

import android.content.Context;

import com.iss.team1.safe.checkin.R;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class CustomCAHttpsProvider {

    /**
     * Creates a {@link javax.net.ssl.HttpsURLConnection} which is configured to work with a custom authority
     * certificate.
     *
     * @param urlString     remote url string.
     * @param context       Application Context
     * @return Http url connection.
     * @throws Exception If there is an error initializing the connection.
     */
    public static HttpsURLConnection getHttpsUrlConnection(String urlString, Context context) throws Exception {

        // build key store with ca certificate
        KeyStore keyStore = buildKeyStore(context);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        // Create a connection from url
        URL url = new URL(urlString);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        // skip hostname security check if specified
        urlConnection.setHostnameVerifier(new AllowAllHostnameVerifier());

        return urlConnection;
    }

    private static KeyStore buildKeyStore(Context context) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        // init a default key store
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);

        // read and add certificate authority
        Certificate cert = readCert(context);
        keyStore.setCertificateEntry("ca", cert);

        return keyStore;
    }

    private static Certificate readCert(Context context) throws CertificateException, IOException {
//        int id = context.getResources().getIdentifier("checkin.crt", "raw", context.getPackageName());
        // read certificate resource
        InputStream caInput = context.getResources().openRawResource(R.raw.checkin);

        Certificate ca;
        try {
            // generate a certificate
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ca = cf.generateCertificate(caInput);
        } finally {
            caInput.close();
        }

        return ca;
    }
}
