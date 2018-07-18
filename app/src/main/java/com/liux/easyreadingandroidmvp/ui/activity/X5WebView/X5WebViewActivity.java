package com.liux.easyreadingandroidmvp.ui.activity.X5WebView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.ui.activity.X5WebView
 * 项目日期：2018/7/12
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class X5WebViewActivity extends BaseSwipeBackActivity {

    @BindView(R.id.webView)
    X5WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;

    private String url;
    private String title;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        toolbarHelper.setTitle("轻松阅");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setSwipeBackEnable(false); //默认为true：使用侧滑返回，false：关闭侧滑返回
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_x5_webview;
    }

    @Override
    protected void initView() {
        //视频为了避免闪屏和透明问题，需要如下设置
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        initSetWeb();
    }

    private void initSetWeb() {
        webView.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback< Uri > uploadMsg, String acceptType) {
                Log.i("test", "openFileChooser 1");
                uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
                uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.e("test", "openFileChooser 3");
                uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                Log.e("test", "openFileChooser 4:" + filePathCallback.toString());
                uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }

        });
        webView.getSettings().setUseWideViewPort(true); //自适应屏幕
        webView.loadUrl(url);

        //webView加载监听
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }
        });
        //webView加载百分比监听
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                Log.e("---TEST---", String.valueOf(newProgress));
                if (newProgress == 100) {
                    //网页加载完毕
//                    dissMissNormalDialog();
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        progressBar.setVisibility(View.GONE);
                    }else
                    {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                    //网页正在加载
//                    showNormalDialog("正在加载数据.....");
                }
            }
        });

    }

    @Override
    protected void detachView() {

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
            return true;
        }
    }

    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                        }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                break;
            default:
                break;
            }
        } else if (resultCode == RESULT_CANCELED) {
                if (null != uploadFile) {
                    uploadFile.onReceiveValue(null);
                    uploadFile = null;
                }
            }
        }

    /**
     * 确保注销配置能够被释放
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (this.webView != null) {
            webView.destroy();
        }
            super.onDestroy();
        }
}
