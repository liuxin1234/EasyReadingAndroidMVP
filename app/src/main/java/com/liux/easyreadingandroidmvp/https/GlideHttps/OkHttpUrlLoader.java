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

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private final okhttp3.Call.Factory client;

    public OkHttpUrlLoader(okhttp3.Call.Factory client) {
        this.client = client;
    }

    public boolean handles(GlideUrl url) {
        return true;
    }

    public LoadData<InputStream> buildLoadData(GlideUrl model, int width, int height, Options options) {
        return new LoadData(model, new OkHttpStreamFetcher(this.client, model));
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private static volatile okhttp3.Call.Factory internalClient;
        private okhttp3.Call.Factory client;

        private static okhttp3.Call.Factory getInternalClient() {
            if(internalClient == null) {
                Class var0 = OkHttpUrlLoader.Factory.class;
                synchronized(OkHttpUrlLoader.Factory.class) {
                    if(internalClient == null) {
                        internalClient = new OkHttpClient();
                    }
                }
            }

            return internalClient;
        }

        public Factory() {
            this(getInternalClient());
        }

        public Factory(okhttp3.Call.Factory client) {
            this.client = client;
        }

        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(this.client);
        }

        public void teardown() {
        }
    }
}
