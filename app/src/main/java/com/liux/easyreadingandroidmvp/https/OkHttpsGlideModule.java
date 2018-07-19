package com.liux.easyreadingandroidmvp.https;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.liux.easyreadingandroidmvp.https.GlideHttps.OkHttpUrlLoader;
import com.liux.easyreadingandroidmvp.utils.CertifiUtils;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by
 * 项目名称：com.liux.nbdpcoaandroid.https
 * 项目日期：2018/6/4
 * 作者：liux
 * 功能： 自定义Glide请求https连接图片
 *
 * @author 750954283(qq)
 */
@GlideModule
public class OkHttpsGlideModule extends AppGlideModule {

    /**
     *  通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //重新设置内存限制
//        builder.setMemoryCache(new LruResourceCache(10*1024*1024));

    }
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        OkHttpClient client = CertifiUtils.setCertificates(new OkHttpClient.Builder()).build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
    /**
     * 清单解析的开启
     *
     * 这里不开启，避免添加相同的modules两次
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
