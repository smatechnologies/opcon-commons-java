package com.smatechnologies.opcon.commons.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


/**
 * @author Pierre PINON
 */
public class UrlUtil {

    private UrlUtil() {
    }

    public static Integer getUrlResponseCode(String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (url == null || url.isEmpty()) {
            return null;
        }

        return getUrlResponseCode(new URL(url));
    }

    public static Integer getUrlResponseCode(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        if (url == null) {
            return null;
        }

        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setHostnameVerifier((s, sslSession) -> true);
        urlConnection.setSSLSocketFactory(createTrustAllCertsSSLContext("SSL").getSocketFactory());
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(3000);
        urlConnection.connect();

        return urlConnection.getResponseCode();
    }

    public static SSLContext createTrustAllCertsSSLContext(String type) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance(type);
        sslContext.init(null, new TrustManager[]{createTrustAllCertsTrustManager()}, new SecureRandom());

        return sslContext;
    }

    private static TrustManager createTrustAllCertsTrustManager() throws NoSuchAlgorithmException, KeyManagementException {
        return new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        };
    }
}
