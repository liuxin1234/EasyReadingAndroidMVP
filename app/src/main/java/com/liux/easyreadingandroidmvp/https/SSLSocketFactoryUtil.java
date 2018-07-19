package com.liux.easyreadingandroidmvp.https;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by
 * 项目名称：com.liux.nbdpcoaandroid.http
 * 项目日期：2018/6/4
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class SSLSocketFactoryUtil {

    /**
     * 获取bks文件的sslsocketfactory
     * @param context
     * @return
     */
//    public static SSLSocketFactory getSSLSocketFactory(Context context) {
//        final String CLIENT_TRUST_PASSWORD = "15728006854";//信任证书密码，该证书默认密码是123456
//        final String CLIENT_AGREEMENT = "TLS";//使用协议
//        final String CLIENT_TRUST_KEYSTORE = "BKS";
//        SSLContext sslContext = null;
//        try {
//            //取得SSL的SSLContext实例
//            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
//            //取得TrustManagerFactory的X509密钥管理器实例
//            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            //取得BKS密库实例
//            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
//            InputStream is = context.getResources().openRawResource(R.raw.cabks);
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
//            e.printStackTrace();
//            Log.e("SslContextFactory", e.getMessage());
//        }
//        return sslContext.getSocketFactory();
//    }

    /**
     * 全部信任
     * @return
     * @throws Exception
     */
    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext
                .getSocketFactory();
    }
}
