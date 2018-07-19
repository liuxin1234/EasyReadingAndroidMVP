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

import android.annotation.SuppressLint;
import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Call.Factory;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpStreamFetcher implements DataFetcher<InputStream> {
    private static final String TAG = "OkHttpFetcher";
    private final Factory client;
    private final GlideUrl url;
    InputStream stream;
    ResponseBody responseBody;
    private volatile Call call;

    public OkHttpStreamFetcher(Factory client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    public void loadData(Priority priority, final DataCallback<? super InputStream> callback) {
        Builder requestBuilder = (new Builder()).url(this.url.toStringUrl());
        Iterator var4 = this.url.getHeaders().entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, String> headerEntry = (Entry)var4.next();
            String key = (String)headerEntry.getKey();
            requestBuilder.addHeader(key, (String)headerEntry.getValue());
        }

        Request request = requestBuilder.build();
        this.call = this.client.newCall(request);
        this.call.enqueue(new Callback() {
            @SuppressLint("WrongConstant")
            public void onFailure(Call call, IOException e) {
                if(Log.isLoggable("OkHttpFetcher", 3)) {
                    Log.d("OkHttpFetcher", "OkHttp failed to obtain result", e);
                }

                callback.onLoadFailed(e);
            }

            public void onResponse(Call call, Response response) throws IOException {
                responseBody = response.body();
                if(response.isSuccessful()) {
                    long contentLength = responseBody.contentLength();
                    stream = ContentLengthInputStream.obtain(responseBody.byteStream(), contentLength);
                    callback.onDataReady(stream);
                } else {
                    callback.onLoadFailed(new HttpException(response.message(), response.code()));
                }

            }
        });
    }

    public void cleanup() {
        try {
            if(this.stream != null) {
                this.stream.close();
            }
        } catch (IOException var2) {
            ;
        }

        if(this.responseBody != null) {
            this.responseBody.close();
        }

    }

    public void cancel() {
        Call local = this.call;
        if(local != null) {
            local.cancel();
        }

    }

    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
