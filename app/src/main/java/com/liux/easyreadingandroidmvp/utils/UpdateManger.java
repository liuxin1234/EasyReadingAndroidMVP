package com.liux.easyreadingandroidmvp.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.application.EasyReadApplication;
import com.liux.easyreadingandroidmvp.https.SafeHostnameVerifier;
import com.liux.easyreadingandroidmvp.https.SslContextFactory;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * 项目名称：Register
 * 类描述：用于更新apk的类
 * 创建人：liuxin
 * 创建时间：2016/3/15 18:38
 * 修改人：renhao
 * 修改时间：2016/3/15 18:38
 * 修改备注：
 */
public class UpdateManger {

    // 应用程序Context
    private Context mContext;
    // 提示消息
    private String updateMsg = "有最新的软件包，请下载！";
    // 下载安装包的网络路径
    private String apkUrl = "";
    private Dialog noticeDialog;// 提示有软件更新的对话框
    private Dialog downloadDialog;// 下载对话框
    private static final String savePageName = EasyReadApplication.getContext().getPackageName();
    private static final String savePath = Environment.getExternalStorageDirectory() +"/"+savePageName;// 保存apk的文件夹
    private static final String saveFileName = savePath + "/EasyReadingAndroid.apk";
    // 进度条与通知UI刷新的handler和msg常量
    private ProgressBar mProgress;
    private TextView mtextview;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;// 当前进度
    private Thread downLoadThread; // 下载线程
    private boolean interceptFlag = false;// 用户取消下载

    // 通知处理刷新界面的handler
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    mtextview.setText(progress + "%");
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public UpdateManger(Context context, String apkUrl, String updateMsg) {
        this.mContext = context;
        this.apkUrl = apkUrl;
        this.updateMsg = updateMsg;
    }

    // 显示更新程序对话框，供主程序调用
    public void checkUpdateInfo() {
        showNoticeDialog();
    }

    private void showNoticeDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                mContext);// Builder，可以通过此builder设置改变AleartDialog的默认的主题样式及属性相关信息
        builder.setTitle("软件版本更新");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();// 当取消对话框后进行操作一定的代码？取消对话框
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_progress, null);

        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        mtextview = (TextView) v.findViewById(R.id.progress_textview);
        builder.setView(v);// 设置对话框的内容为一个View
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
        downloadApk();
    }

    private void downloadApk() {
        Logger.v("download", "downloadApk");
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        try {
            Uri contentUri = FileProvider.getUriForFile(mContext, EasyReadApplication.getContext().getPackageName() + ".fileProvider", apkfile);
            Logger.e(contentUri.getPath()+"\n"+contentUri.toString());
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //在下载更新之后,并没有打开安装成功界面,闪退情况,想了许久,从新开一个任务栈
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.putExtra("return-data", false); //这里是为了适配小米部分机型，限制了intent的传值数据量大小限制，造成的崩溃
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
            mContext.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            URL url;
            try {
                url = new URL(apkUrl);
                Logger.v("download", apkUrl);
                //这里是验证https的证书验证
                InputStream certificate = Util.getContext().getResources().openRawResource(Util.getRawResource("这里是htps证书的名字"));
                SSLSocketFactory sslSocketFactory = SslContextFactory.getSSLSocketFactoryForOneWay(certificate);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(sslSocketFactory);
                conn.setHostnameVerifier(new SafeHostnameVerifier());
                //这里是http的请求 如果没有证书验证 可以还下面这个
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(15000);
                conn.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, " +
                                "application/x-shockwave-flash, application/xaml+xml," +
                                " application/vnd.ms-xpsdocument, application/x-ms-xbap, " +
                                "application/x-ms-application, application/vnd.ms-excel, " +
                                "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1;Trident/4.0; .NET CLR 2.0.50727)");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.connect();
                int length = conn.getContentLength();
//                Logger.v("download", "apk length " + length);
                InputStream ins = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
//                Logger.e(apkFile);
                File ApkFile = new File(apkFile);
                FileOutputStream outStream = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = ins.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 下载进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    outStream.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消停止下载
                outStream.close();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}
