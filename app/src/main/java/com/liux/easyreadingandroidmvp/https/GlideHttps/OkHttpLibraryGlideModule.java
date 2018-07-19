package com.liux.easyreadingandroidmvp.https.GlideHttps;

/**
 * Created by
 * 项目名称：com.liux.nbdpcoaandroid.https.GlideHttps
 * 项目日期：2018/6/5
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

import android.content.Context;

import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;

public final class OkHttpLibraryGlideModule extends LibraryGlideModule {
    public OkHttpLibraryGlideModule() {
    }

    public void registerComponents(Context context, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
