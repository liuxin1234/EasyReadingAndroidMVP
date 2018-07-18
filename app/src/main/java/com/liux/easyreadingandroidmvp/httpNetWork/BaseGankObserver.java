package com.liux.easyreadingandroidmvp.httpNetWork;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.liux.easyreadingandroidmvp.httpNetWork.bean.BaseEntity;
import com.liux.module.GankBean.BaseBean;
import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.httpNetWork
 * 项目日期：2018/7/3
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public abstract class BaseGankObserver<T> implements Observer<BaseBean<T>> {
    protected Context mContext;

    public BaseGankObserver(){

    }

    public BaseGankObserver(Context context){
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(BaseBean<T> tBaseBean) {
        onRequestEnd();
        if (!tBaseBean.isError()){
            try {
                onSuccess(tBaseBean);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {
                onCodeError(tBaseBean);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException){
                onFailure(e, true);
            }else {
                onFailure(e, false);
            }
        }catch (Exception et){
            et.printStackTrace();
        }
    }


    @Override
    public void onComplete() {

    }

    /**
     *  返回成功
     * @param tBaseBean
     * @throws Exception
     */
    protected abstract void onSuccess(BaseBean<T> tBaseBean) throws Exception;

    /**
     *  返回成功了,但是code错误
     * @param tBaseBean
     * @throws Exception
     */
    protected  void onCodeError(BaseBean<T> tBaseBean) throws Exception{

    }

    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart(){

    };

    protected void onRequestEnd(){
        closeProgressDialog();
    }

    public void showProgressDialog(){
//        ProgressDialog.show(mContext,false,"请稍后");
    }

    private void closeProgressDialog() {
//        ProgressDialog.cancle();
    }

    ;


}
