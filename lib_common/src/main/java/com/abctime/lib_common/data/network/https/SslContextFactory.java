package com.abctime.lib_common.data.network.https;


import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by xmren on 2017/8/7.
 */

public class SslContextFactory {
    private static final String CLIENT_TRUST_PASSWORD = "";//信任证书密码，该证书默认密码是
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
    SSLContext sslContext = null;

//    public SSLContext getSslSocket() {
//        try {
//            //取得SSL的SSLContext实例
//            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
//            //取得TrustManagerFactory的X509密钥管理器实例
//            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
//            //取得BKS密库实例
//            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
//            InputStream is = XesAPP.getInstance().getResources().openRawResource(R.raw.cert);
//            try {
//                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
//            } finally {
//                is.close();
//            }
//            //初始化密钥管理器
//            trustManager.init(tks);
//            //初始化SSLContext
//            sslContext.init(null, trustManager.getTrustManagers(), null);
//        } catch (Exception e) {
//            LogUtil.e("SslContextFactory", e.getMessage());
//        }
//        return sslContext;
//    }

    public static X509TrustManager createTrustManager() {
        X509TrustManager trustAllCerts = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        return trustAllCerts;
    }


    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{createTrustManager()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
            ssfFactory = new Tls12SocketFactory(ssfFactory);

        } catch (Exception e) {
        }

        return ssfFactory;
    }
}
