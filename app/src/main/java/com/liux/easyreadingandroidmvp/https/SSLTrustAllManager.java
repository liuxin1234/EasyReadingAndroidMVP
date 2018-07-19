package com.liux.easyreadingandroidmvp.https;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Created by
 * 项目名称：com.liux.nbdpcoaandroid.http
 * 项目日期：2018/6/1
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class SSLTrustAllManager implements X509TrustManager {
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
}
