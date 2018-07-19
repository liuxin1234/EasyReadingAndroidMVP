package com.liux.easyreadingandroidmvp.utils;

import android.support.annotation.NonNull;
import android.webkit.SslErrorHandler;

import com.liux.easyreadingandroidmvp.https.SafeHostnameVerifier;
import com.liux.easyreadingandroidmvp.https.SslContextFactory;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 * Created by
 * 项目名称：com.liux.nbdpcoaandroid.utils
 * 项目日期：2018/6/4
 * 作者：liux
 * 功能：证书验证的方法
 *
 * @author 750954283(qq)
 */

public class CertifiUtils {
    // 验证证书
    public static void OnCertificateOfVerification(final SslErrorHandler handler, String url) {
        OkHttpClient.Builder builder = setCertificates(new OkHttpClient.Builder());
        Request request = new Request.Builder().url(url)
                .build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Logger.e("证书验证失败", e.getMessage());
                handler.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Logger.e("证书验证成功", response.body().string());
                handler.proceed();
            }
        });
    }

    public static OkHttpClient.Builder setCertificates(OkHttpClient.Builder client) {
        try {
            InputStream certificate = Util.getContext().getResources().openRawResource(Util.getRawResource("这里填写https证书名字"));
            SSLSocketFactory sslSocketFactory = SslContextFactory.getSSLSocketFactoryForOneWay(certificate);
            X509TrustManager trustManager = Platform.get().trustManager(sslSocketFactory);
            if (sslSocketFactory != null) {
                client.sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(new SafeHostnameVerifier());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}

