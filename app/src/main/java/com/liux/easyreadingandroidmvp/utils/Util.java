package com.liux.easyreadingandroidmvp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Base64;

import com.liux.easyreadingandroidmvp.application.EasyReadApplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：WeVolunteer
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/8/21 23:11
 * 修改备注：
 * 正式环境拼接的URL：http://www.nbzyz.org
 * 测试环境凭借的URL：http://115.238.150.174:5018/admin
 *
 * @author 75095
 */
public class Util {
    private static final String TAG = "Util";

    public static final String IMG_URL = "http://www.nbzyz.org";

    private static Context context;

    private Util() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Util.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }


    //二进制流转换为图片
    public static Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }

    //二进制流转换为图片
    public static Bitmap byteToBitmap(String img) {
        InputStream input = null;
        Bitmap bitmap = null;
        byte[] temp = Base64.decode(img.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (temp.length > (1024 * 512)) {
            options.inSampleSize = 2;
        }
        if (temp.length > (1024 * 1024)) {
            options.inSampleSize = 4;
        }
        if (temp.length > (1024 * 2048)) {
            options.inSampleSize = 8;
        }
        input = new ByteArrayInputStream(temp);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (temp != null) {
            temp = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }



    /**
     * 获取当前的时间
     *
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @param formatStr 时间日期格式
     * @return
     */
    public static String getNowDate(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSDcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPhoneNumber(String number) {
        String pattern = "0?(11|12|13|14|15|16|17|18|19)[0-9]{9}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(number);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        String pattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        return m.matches();
    }

    public static boolean isID(String id) {
        String pattern = "\\d{17}[\\d|X|x]|\\d{15}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(id);
        return m.matches();
    }


    public static String html2String(String html) {
        html = html.replace("&nbsp;", " ");
        html = html.replace("<br />", "\n");
        return html;
    }

    public static String getAppVersion(Context context) {
        String version = "";
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();

        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       /* info.versionCode;
        info.versionName;
        info.packageName;
        info.signatures;*/

        return version;
    }



    /**
     * 获取图片名称获取图片的资源id的方法
     * @param imageName
     * @return
     */
    public static int  getResource(String imageName){
        Context ctx= EasyReadApplication.getContext();
        //注意这里获取getResources()时 前面一定要带有context的，不然会报空指针。
        int resId = ctx.getResources().getIdentifier(imageName, "mipmap" , ctx.getPackageName());
        return resId;
    }

    /**
     * 获取raw资源id的方法
     * @param rawName
     * @return
     */
    public static int  getRawResource(String rawName){
        Context ctx= EasyReadApplication.getContext();
        //注意这里获取getResources()时 前面一定要带有context的，不然会报空指针。
        int resId = ctx.getResources().getIdentifier(rawName, "raw" , ctx.getPackageName());
        return resId;
    }

    /**
     * Android获取获取当前手机IP地址
     * @param context 上下文
     * @return String
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * @param file 需要被安装的文件
     * @param author 标识信息
     * @return 被创建的uri
     */
    public static Uri getUriByFile (File file, String author) {
        String path;
        try {
            path = file.getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
        }
        Uri uri =  new Uri.Builder().scheme("content")
                .authority(author).encodedPath(path).build();
//        mCache.put(uri,file);

        return uri;
    }
}
