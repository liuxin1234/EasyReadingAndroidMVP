package com.liux.easyreadingandroidmvp.https;



import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * https的校验器
 * verify方法中对比了请求的IP和服务器的IP是否一致，一致则返回true表示校验通过，否则返回false，
 * 检验不通过，断开连接。对于网上有些处理是直接返回true，即不对请求的服务器IP做校验，我们不推荐这样使用。
 * 而且现在谷歌应用商店已经对此种做法做了限制，禁止在verify方法中直接返回true的App上线。
 */

public class SafeHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        if ("这里填写域名IP".equals(hostname)) {//校验hostname是否正确，如果正确则建立连接
            return true;
        }
        return false;
    }
}
