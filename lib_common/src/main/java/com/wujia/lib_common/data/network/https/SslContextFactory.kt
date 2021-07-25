package com.wujia.lib_common.data.network.https


import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by xmren on 2017/8/7.
 */

class SslContextFactory {
    internal var sslContext: SSLContext? = null

    companion object {

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

        private fun createTrustManager(): X509TrustManager {

            return object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {

                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }
        }


        fun createSSLSocketFactory(): SSLSocketFactory? {
            var ssfFactory: SSLSocketFactory? = null

            try {
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, arrayOf<TrustManager>(createTrustManager()), SecureRandom())

                ssfFactory = sc.socketFactory
                ssfFactory = Tls12SocketFactory(ssfFactory)

            } catch (e: Exception) {
            }

            return ssfFactory
        }
    }
}
